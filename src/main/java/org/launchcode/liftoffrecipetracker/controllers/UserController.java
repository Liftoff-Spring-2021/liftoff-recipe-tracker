package org.launchcode.liftoffrecipetracker.controllers;

import org.launchcode.liftoffrecipetracker.data.UserRepository;
import org.launchcode.liftoffrecipetracker.models.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.Errors;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import javax.validation.Valid;
//
//@Controller
//// URL path for users.
//@RequestMapping("users")
//public class UserController {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @GetMapping
//    public String displayAllOwners(Model model){
//
//        model.addAttribute("title", "All Owners");
//        model.addAttribute("users", userRepository.findAll());
//        return "user/index";
//    }
//
//    @GetMapping("create")
//    public String displayCreateOwnerInCreateRecipe(Model model){
//
//        model.addAttribute("title", "Create Recipe Owner");
//        model.addAttribute(new User());
//        return"user/create";
//    }
//
//    @PostMapping("create")
//    public String processCreateOwnerForm(@Valid @ModelAttribute User user,
//                                         Errors errors, Model model){
//
//        if(errors.hasErrors()){
//            model.addAttribute("title", "Create Recipe Owner");
//            model.addAttribute(new User());
//            return "user/create";
//        }
//
//        userRepository.save(user);
//        // once owner typed his/her name then he/she is redirected back to crate recipe form to complete his/her recipe.
//        return "../recipe/create";
//    }
//
//
//
//}
