package org.launchcode.liftoffrecipetracker.controllers;

import org.launchcode.liftoffrecipetracker.data.BeverageRepository;
import org.launchcode.liftoffrecipetracker.models.Beverage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

    @Controller
// beverages is the URL path
    @RequestMapping("beverages")
    public class BeverageController {

        @Autowired
        private BeverageRepository beverageRepository;

        @GetMapping
        public String displayAllBeverages(Model model) {
            model.addAttribute("title", "All Beverages");
            model.addAttribute("beverages", beverageRepository.findAll());
            // beverage/index is the file path in the project structure
            return "beverage/index";
        }

        // create is the URL path beverages/create
        @GetMapping("create")
        public String displayCreateBeveragesForm(Model model) {
            model.addAttribute("title", "Create Beverage");
            model.addAttribute(new Beverage());
            // beverage/create is the file path in the project structure
            return "beverage/create";
        }

        @PostMapping("create")
        public String processCreateBeverageForm(@Valid @ModelAttribute Beverage beverage,
                                                Errors errors, Model model) {

            if (errors.hasErrors()) {
                model.addAttribute("title", "Create Beverage");
                model.addAttribute(new Beverage());
                // beverage/create is the file path in the project structure
                return "beverage/create";
            }

            beverageRepository.save(beverage);
            // redirect: is the URL path from RequestMapping (The main mapping from the controller)
            return "redirect:";
        }
}
