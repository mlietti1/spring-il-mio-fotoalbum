package org.lessons.java.springilmiofotoalbum.controller;

import jakarta.validation.Valid;
import org.lessons.java.springilmiofotoalbum.model.Category;
import org.lessons.java.springilmiofotoalbum.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/posts/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String index(@RequestParam("id")Optional<Integer> idParam, Model model){
        model.addAttribute("list", categoryService.getAll());
        if(idParam.isPresent()){
            model.addAttribute("categoryObj", categoryService.getById(idParam.get()));
        } else {
            model.addAttribute("categoryObj", new Category());
        }

        return "categories/index";
    }

    @PostMapping("save")
    public String doSave(@Valid @ModelAttribute(name = "categoryObj") Category category, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("list", categoryService.getAll());
            return "categories/index";
        }
        if(category.getId() != null){
            categoryService.update(category);
        } else {
            categoryService.create(category);
        }
        categoryService.create(category);
        return "redirect:/posts/categories";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable Integer id){
        categoryService.deleteById(id);
        return "redirect:/posts/categories";
    }
}
