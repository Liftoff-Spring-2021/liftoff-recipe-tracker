package org.launchcode.liftoffrecipetracker;

import org.launchcode.liftoffrecipetracker.controllers.AuthenticationController;
import org.launchcode.liftoffrecipetracker.data.UserRepository;
import org.launchcode.liftoffrecipetracker.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AuthenticationFilter extends HandlerInterceptorAdapter {

	@Autowired
	UserRepository userRepository;

	@Autowired
	AuthenticationController authenticationController;

	private static final List<String> whitelist = Arrays.asList("/login", "/register", "/logout", "/styles.css");

	@Override
	public boolean preHandle(HttpServletRequest request,
	                         HttpServletResponse response,
	                         Object handler) throws IOException {

		//if there is no user but is trying to access a page on our whitelist returns true and allows access
		if (isWhiteListed(request.getRequestURI())) {
			return true;
		}

		//retrieves the current session from the request
		HttpSession session = request.getSession();
		//retrieves the user from the session we got from the request
		User user = authenticationController.getUserFromSession(session);

		//if there is a valid user, returns true and allows access to the site
		if (user != null) {
			return true;
		}

		//if none of the above, redirects access to login and returns false
		response.sendRedirect("/login");
		return false;
	}

	//checks the path against the whitelisted paths we established above
	private static boolean isWhiteListed(String path) {
		for (String pathRoot : whitelist) {
			if (path.startsWith(pathRoot)) {
				return true;
			}
		}
		return false;
	}
}
