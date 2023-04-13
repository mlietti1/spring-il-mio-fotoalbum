package org.lessons.java.springilmiofotoalbum.controller;

import jakarta.validation.Valid;
import org.lessons.java.springilmiofotoalbum.exception.PostNotFoundException;
import org.lessons.java.springilmiofotoalbum.model.AlertMessage;
import org.lessons.java.springilmiofotoalbum.model.AlertMessage.AlertMessageType;
import org.lessons.java.springilmiofotoalbum.model.Post;
import org.lessons.java.springilmiofotoalbum.service.CategoryService;
import org.lessons.java.springilmiofotoalbum.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String index(Model model, @RequestParam(name = "q")Optional<String> keyword){
        List<Post> posts;
        if(keyword.isEmpty()){
            posts = postService.getAllPosts();
        } else {
            posts = postService.getFilteredPosts(keyword.get());
            model.addAttribute("keyword", keyword.get());
        }
        model.addAttribute("list", posts);
        return "posts/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Integer id, Model model){
        try {
            Post post = postService.getById(id);
            model.addAttribute("post", post);
            return "posts/show";
        } catch (PostNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post with id " + id + " not found.");
        }
    }

    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("post", new Post());
        model.addAttribute("categoryList", categoryService.getAll());
        return "posts/create-edit";
    }

    @PostMapping("/create")
    public String save(@Valid @ModelAttribute("post") Post formPost, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("categoryList", categoryService.getAll());
            return "posts/create-edit";
        }
        postService.createPost(formPost);
        return "redirect:/posts";
    }

    @GetMapping("edit/{id}")
    public String edit(@PathVariable Integer id, Model model){
        try{
            Post post = postService.getById(id);
            model.addAttribute("post", post);
            model.addAttribute("categoryList", categoryService.getAll());
            return "posts/create-edit";
        } catch (PostNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post with id " + id + " not found.");
        }
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Integer id, @Valid @ModelAttribute("post") Post formPost, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("categoryList", categoryService.getAll());
            return "posts/create-edit";
        }
        try{
            Post updatedPost = postService.updatePost(formPost, id);
            return "redirect:/posts/" + Integer.toString(updatedPost.getId());
        } catch (PostNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post with id " + id + " not found.");
        }
    }

    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        try{
            boolean success = postService.deleteById(id);
            if(success){
                redirectAttributes.addFlashAttribute("message", new AlertMessage(AlertMessageType.SUCCESS, "Post with id " + id + " deleted successfully." ));
            } else {
                redirectAttributes.addFlashAttribute("message", new AlertMessage(AlertMessageType.ERROR, "Unable to delete post with id " + id + "."));
            }
        } catch (PostNotFoundException e){
            redirectAttributes.addAttribute("message", new AlertMessage(AlertMessageType.ERROR, "Post with id " + id + " not found."));
        }
        return "redirect:/posts";
    }

}
