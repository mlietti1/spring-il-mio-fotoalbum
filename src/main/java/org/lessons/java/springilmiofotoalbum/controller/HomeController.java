package org.lessons.java.springilmiofotoalbum.controller;

import org.lessons.java.springilmiofotoalbum.model.Post;
import org.lessons.java.springilmiofotoalbum.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private PostService postService;
    @GetMapping
    public String home(Model model){
        List<Post> posts = postService.getAllPosts();
        model.addAttribute("list", posts);
        return "home";
    }
}
