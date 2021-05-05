package org.launchcode.liftoffrecipetracker.controllers;

import org.launchcode.liftoffrecipetracker.data.CategoryRepository;
import org.launchcode.liftoffrecipetracker.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
// categories is the URL path
@RequestMapping("categories")
public class CategoryController {

@Autowired
private CategoryRepository categoryRepository;

    @GetMapping
    public String displayAllCategories(Model model) {
        model.addAttribute("title", "All Categories");
        model.addAttribute("categories", categoryRepository.findAll());
        // category/index is the file path in the project structure
        return "category/index";
    }

    // create is the URL path categories/create
    @GetMapping("create")
    public String displayCreateCategoryForm(Model model) {
        model.addAttribute("title", "Create Category");
        model.addAttribute(new Category());
        // category/create is the file path in the project structure
        return "category/create";
    }

    @PostMapping("create")
    public String processCreateCategoryForm(@Valid @ModelAttribute Category category,
                                                 Errors errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Create Category");
            model.addAttribute(new Category());
            // category/create is the file path in the project structure
            return "category/create";
        }

        categoryRepository.save(category);
        // redirect: is the URL path from RequestMapping (The main mapping from the controller)
        return "redirect:";
    }

    @GetMapping("delete")
    public String displayDeleteCategoryForm(Model model){
        model.addAttribute("title", "Delete Category");
        model.addAttribute("categories", categoryRepository.findAll());
        return"category/delete";
    }

    @PostMapping("delete")
    public String processDeleteCategoryForm( @RequestParam(required = false)
                                                   int[] categoryId){

        if(categoryId != null){
            for(int id : categoryId){
                categoryRepository.deleteById(id);
            }
        }
        return "redirect:";
    }


}
