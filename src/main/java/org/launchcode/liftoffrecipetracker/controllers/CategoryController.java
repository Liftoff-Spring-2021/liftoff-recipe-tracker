package org.launchcode.liftoffrecipetracker.controllers;

import org.launchcode.liftoffrecipetracker.data.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("category")
public class CategoryController {

@Autowired
private CategoryRepository categoryRepository;

    @GetMapping
    public String displayAllCategories(Model model) {
        model.addAttribute("title", "All Categories");
        model.addAttribute("category", categoryRepository.findAll());
        return "category/index";
    }
}
