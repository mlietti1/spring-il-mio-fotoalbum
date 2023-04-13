package org.lessons.java.springilmiofotoalbum.service;

import org.lessons.java.springilmiofotoalbum.exception.PostNotFoundException;
import org.lessons.java.springilmiofotoalbum.model.Post;
import org.lessons.java.springilmiofotoalbum.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;

    public Post createPost(Post formPost){
        Post postToPersist = new Post();
        postToPersist.setTitle(formPost.getTitle());
        postToPersist.setDescription(formPost.getDescription());
        postToPersist.setUrl(formPost.getUrl());
        postToPersist.setCategories(formPost.getCategories());
        postToPersist.setCreatedAt(LocalDateTime.now());
        postToPersist.setUpdatedAt(LocalDateTime.now());
        return postRepository.save(postToPersist);
    }

    public Post updatePost(Post formPost, Integer id) throws PostNotFoundException{
        Post postToUpdate = getById(id);
        postToUpdate.setTitle(formPost.getTitle());
        postToUpdate.setDescription(formPost.getDescription());
        postToUpdate.setUrl(formPost.getUrl());
        postToUpdate.setCategories(formPost.getCategories());
        postToUpdate.setUpdatedAt(LocalDateTime.now());
        return postRepository.save(postToUpdate);
    }

    public List<Post> getAllPosts(){
        return postRepository.findAll(Sort.by("id"));
    }

    public List<Post> getFilteredPosts(String keyword){
        return postRepository.findByTitleContainingIgnoreCaseOrderByTitle(keyword);
    }

    public Post getById(Integer id) throws PostNotFoundException{
        Optional<Post> result = postRepository.findById(id);
        if(result.isPresent()){
            return result.get();
        } else {
            throw new PostNotFoundException(Integer.toString(id));
        }
    }

    public boolean deleteById(Integer id) throws PostNotFoundException{
        postRepository.findById(id).orElseThrow(()-> new PostNotFoundException(Integer.toString(id)));
        try{
            postRepository.deleteById(id);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
