package org.lessons.java.springilmiofotoalbum.api;


import jakarta.validation.Valid;
import org.lessons.java.springilmiofotoalbum.exception.PostNotFoundException;
import org.lessons.java.springilmiofotoalbum.model.Post;
import org.lessons.java.springilmiofotoalbum.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/posts")
public class PostRestController {
    @Autowired
    private PostService postService;

    @GetMapping
    public List<Post> list(@RequestParam(name = "q")Optional<String> search){
        if(search.isPresent()){
            return postService.getFilteredPosts(search.get());
        }
        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    public Post getById(@PathVariable Integer id){
        try{
            return postService.getById(id);
        } catch (PostNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public Post create(@Valid @RequestBody Post post){
        return postService.createPost(post);
    }

    @PutMapping("/{id}")
    public Post update (@PathVariable Integer id, @Valid @RequestBody Post post){
        try{
            return postService.updatePost(post, id);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        try {
            boolean success = postService.deleteById(id);
            if(!success){
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Unable to delete post.");
            }
        } catch (PostNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
