package org.launchcode.liftoffrecipetracker.controllers;

import org.launchcode.liftoffrecipetracker.data.CategoryRepository;
import org.launchcode.liftoffrecipetracker.models.Category;
import org.launchcode.liftoffrecipetracker.models.Recipe;
import org.launchcode.liftoffrecipetracker.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
// categories is the URL path
@RequestMapping("categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    AuthenticationController authenticationController;

    @GetMapping
    public String displayAllCategories(Model model, HttpSession userSession) {
        User user = authenticationController.getUserFromSession(userSession);
        model.addAttribute("title", "All Categories");
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("user", user);
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
                                            Errors errors, Model model, HttpSession userSession) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Create Category");
            model.addAttribute(new Category());
            // category/create is the file path in the project structure
            return "category/create";
        }

        User user = authenticationController.getUserFromSession(userSession);
        category.setUser(user);

        categoryRepository.save(category);
        // redirect: is the URL path from RequestMapping (The main mapping from the controller)
        return "redirect:/categories";
    }

    //delete category
    @GetMapping("delete")
    public String displayDeleteCategoryForm(Model model, HttpSession userSession) {
        User user = authenticationController.getUserFromSession(userSession);
        model.addAttribute("title", "Delete Category");
        model.addAttribute("categories", user.getCategories());
        return "category/delete";
    }

    @PostMapping("delete")
    public String processDeleteCategoryForm(@RequestParam(required = false)
                                                    int[] categoryId) {

        if (categoryId != null) {
            for (int id : categoryId) {
                Optional<Category> optionalCategory = categoryRepository.findById(id);
                if (optionalCategory.isPresent()) {
                    Category category = optionalCategory.get();
                    List<Recipe> recipes = category.getRecipes();
                    for (Recipe recipe : recipes) {
                        recipe.removeCategory(category);
                    }
                    recipes.clear();
                    categoryRepository.deleteById(id);
                }
            }
        }
        return "redirect:";
    }

    //edit category
    @GetMapping("edit/{categoryId}")
    public String displayEditCategoryForm(@PathVariable int categoryId, Model model) {
        Optional<Category> result = categoryRepository.findById(categoryId);
        if (result.isEmpty()) {
            model.addAttribute("title", "Invalid Category ID: " + categoryId);
        } else {
            Category category = result.get();
            model.addAttribute("title", "Edit Category: " + category.getName());
            model.addAttribute("category", category);
        }
        return "category/edit";
    }

    @PostMapping("edit")
    public String processEditCategoryForm(int categoryId, String name) {
        Optional<Category> optCategory = categoryRepository.findById(categoryId);
        if (optCategory.isPresent()) {
            Category category = optCategory.get();
            category.setName(name);
            categoryRepository.save(category);
        }
        return "redirect:/categories";
    }

    // create a customizable copy
    @GetMapping("copy/{categoryId}")
    public String displayCopyCategoryForm(@PathVariable int categoryId, Model model) {
        Optional<Category> result = categoryRepository.findById(categoryId);
        if (result.isEmpty()) {
            model.addAttribute("title", "Invalid Category ID: " + categoryId);
        } else {
            Category category = result.get();
            model.addAttribute("title", "Copy Category: " + category.getName());
            model.addAttribute("category", category);
        }
        return "category/copy";
    }

    @PostMapping("copy")
    public String processCopyCategoryForm(@Valid @ModelAttribute Category category,
                                          Errors errors, Model model, HttpSession userSession) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Copy Category");
            model.addAttribute(new Category());
            // category/copy is the file path in the project structure
            return "category/copy";
        }

        User user = authenticationController.getUserFromSession(userSession);
        category.setUser(user);

        categoryRepository.save(category);
        // redirect: is the URL path from RequestMapping (The main mapping from the controller)
        return "redirect:/categories";
    }
}