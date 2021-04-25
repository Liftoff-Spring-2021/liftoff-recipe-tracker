package org.launchcode.liftoffrecipetracker.controllers;

import org.launchcode.liftoffrecipetracker.data.UserRepository;
import org.launchcode.liftoffrecipetracker.models.User;
import org.launchcode.liftoffrecipetracker.models.dto.LoginFormDTO;
import org.launchcode.liftoffrecipetracker.models.dto.RegisterFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class AuthenticationController {

	@Autowired
	private UserRepository userRepository;

	private static final String userSessionKey = "user";

	public User getUserFromSession(HttpSession session) {
		//sets userId to the user session key
		Integer userId = (Integer) session.getAttribute(userSessionKey);

		if (userId == null) {
			return null;
		}

		//initializes a variable for the user using the repository method findById using the ID got from the session
		Optional<User> user = userRepository.findById(userId);

		if (user.isEmpty()) {
			return null;
		}

		//returns the user from the optional wrapper
		return user.get();
	}

	private static void setUserInSession(HttpSession session, User user) {
		//sets the session key to the user ID. The session operates as a key-value pair
		session.setAttribute(userSessionKey, user.getId());
	}

	@GetMapping("/register")
	public String displayRegistrationForm(Model model) {
		model.addAttribute(new RegisterFormDTO());
		model.addAttribute("title", "Register New User");
		return "register";
	}

	@PostMapping("/register")
	public String processRegistrationForm(@ModelAttribute @Valid RegisterFormDTO registerFormDTO,
	                                      Errors errors,
	                                      HttpServletRequest request,
	                                      Model model) {
		//checks for form errors
		if (errors.hasErrors()) {
			model.addAttribute("title", "Register New User");
			return "register";
		}

		User existingUser = userRepository.findByUsername(registerFormDTO.getUsername());

		//checks if there is a user in the repository with the provided username. If there is returns to the form
		if (existingUser != null) {
			errors.rejectValue("username", "username.alreadyExists", "A user with this username already exists.");
			model.addAttribute("title", "Register New User");
			return "register";
		}

		String password = registerFormDTO.getPassword();
		String verifyPassword = registerFormDTO.getVerifyPassword();

		//checks that the provided passwords match and returns to form if not
		if (!password.equals(verifyPassword)) {
			errors.rejectValue("password", "password.mismatch", "Passwords do not match.");
			model.addAttribute("title", "Register New User");
			return "register";
		}
		String email = registerFormDTO.getEmail();
		String verifyEmail = registerFormDTO.getVerifyEmail();

		//checks that the provided emails match and returns to form if not
		if (!email.equals(verifyEmail)) {
			errors.rejectValue("email", "email.mismatch", "Emails do not match.");
			model.addAttribute("title", "Register New User");
			return "register";
		}
		User newUser = new User(registerFormDTO.getUsername(), registerFormDTO.getPassword(),
				registerFormDTO.getEmail());

		//saves the new user to the repository, sets them in session, and redirects home
		userRepository.save(newUser);
		setUserInSession(request.getSession(), newUser);

		return "redirect:/home";
	}

	@GetMapping("/login")
	public String displayLoginForm(Model model) {
		model.addAttribute(new LoginFormDTO());
		model.addAttribute("title", "Log In");
		return "login";
	}

	@PostMapping("/login")
	public String processRegistrationForm(@ModelAttribute @Valid LoginFormDTO loginFormDTO,
	                                      Errors errors,
	                                      HttpServletRequest request,
	                                      Model model) {
		//checks for form errors first
		if (errors.hasErrors()) {
			model.addAttribute("title", "Log In");
			return "login";
		}
		//uses the provided info to find the user in the repository
		User theUser = userRepository.findByUsername(loginFormDTO.getUsername());

		//This checks to ensure there is a user in the repository. If theUser returns null there isn't a user with
		//that username and they are returned to the form
		if (theUser == null) {
			errors.rejectValue("username", "user.invalid", "Username does not exist.");
			model.addAttribute("title", "Log In");
			return "login";
		}

		String password = loginFormDTO.getPassword();

		//uses the built in method to validate the provided password with the password hash stored in the repository
		if (!theUser.isMatchingPassword(password)) {
			errors.rejectValue("password", "password.invalid", "Invalid password.");
			model.addAttribute("title", "Log In");
			return "login";
		}

		//sets the user in the session and redirects home
		setUserInSession(request.getSession(), theUser);

		return "redirect:/home";
	}

	@GetMapping("logout")
	public String logout(HttpServletRequest request) {
		//invalidates the session, clearing it, and redirects to the login page
		request.getSession().invalidate();
		return "redirect:/login";
	}
}
