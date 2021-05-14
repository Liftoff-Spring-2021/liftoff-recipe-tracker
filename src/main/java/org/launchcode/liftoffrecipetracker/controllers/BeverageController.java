package org.launchcode.liftoffrecipetracker.controllers;

import org.launchcode.liftoffrecipetracker.data.BeverageRepository;
import org.launchcode.liftoffrecipetracker.models.Beverage;
import org.launchcode.liftoffrecipetracker.models.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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
            return "redirect:/beverages";
        }


    //delete beverage

    @GetMapping("delete")
    public String displayDeleteBeverageForm(Model model) {
        model.addAttribute("title", "Delete Beverage");
        model.addAttribute("beverages", beverageRepository.findAll());
        return "beverage/delete";
    }

    @PostMapping("delete")
    public String processDeleteBeverageForm(@RequestParam(required = false)
                                                    int[] beverageId) {

        if (beverageId != null) {
            for (int id :beverageId) {
                Optional<Beverage> optionalBeverage = beverageRepository.findById(id);
                if (optionalBeverage.isPresent()) {
                    Beverage beverage = optionalBeverage.get();
                    List<Recipe> recipes = beverage.getRecipes();
                    for (Recipe recipe : recipes) {
                        recipe.removeBeverage(beverage);
                    }
                    recipes.clear();
                    beverageRepository.deleteById(id);
                }
            }
        }
        return "redirect:";
    }

    //edit beverage
    @GetMapping("edit/{beverageId}")
    public String displayEditBeverageForm(@PathVariable int beverageId, Model model) {
        Optional<Beverage> result = beverageRepository.findById(beverageId);
        if (result.isEmpty()) {
            model.addAttribute("title", "Invalid Beverage ID: " + beverageId);
        } else {
            Beverage beverage = result.get();
            model.addAttribute("title", "Edit Beverage" + beverage.getName());
            model.addAttribute("beverage", beverage);
        }
        return "beverage/edit";
    }

    @PostMapping("edit")
    public String processEditBeverageForm(int beverageId, String name) {
        Beverage beverage = beverageRepository.findById(beverageId).get();
        beverage.setName(name);
        beverageRepository.save(beverage);
        return "redirect:/beverages";
    }

    // create a customizable copy

    @GetMapping("copy/{beverageId}")
    public String displayCopyBeverageForm(@PathVariable int beverageId, Model model) {
        Optional<Beverage> result = beverageRepository.findById(beverageId);
        if (result.isEmpty()) {
            model.addAttribute("title", "Invalid Beverage ID: " + beverageId);
        } else {
            Beverage beverage = result.get();
            model.addAttribute("title", "Copy Beverage" + beverage.getName());
            model.addAttribute("beverage", beverage);
        }
        return "beverage/copy";
    }

    @PostMapping("copy")
    public String processCopyBeverageForm(@Valid @ModelAttribute Beverage beverage,
                                          Errors errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Edit Beverage");
            model.addAttribute(new Beverage());
            // beverage/edit is the file path in the project structure
            return "beverage/edit";
        }

        beverageRepository.save(beverage);
        // redirect: is the URL path from RequestMapping (The main mapping from the controller)
        return "redirect:/beverages";
    }
}
