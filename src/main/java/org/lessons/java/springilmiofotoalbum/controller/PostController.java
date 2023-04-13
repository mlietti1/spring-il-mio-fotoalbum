package org.lessons.java.springilmiofotoalbum.controller;

import org.lessons.java.springilmiofotoalbum.model.Post;
import org.lessons.java.springilmiofotoalbum.service.CategoryService;
import org.lessons.java.springilmiofotoalbum.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
}
