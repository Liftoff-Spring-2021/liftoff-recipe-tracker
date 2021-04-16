package org.launchcode.liftoffrecipetracker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/")
public class HomeController {

	@GetMapping
	public String displayIndex() {
		return "redirect:/home";
	}

	@GetMapping("/home")
	public String displayHome() {
		return "index";
	}

}
