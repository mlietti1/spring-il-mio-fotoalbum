package org.lessons.java.springilmiofotoalbum.service;

import org.lessons.java.springilmiofotoalbum.model.Category;
import org.lessons.java.springilmiofotoalbum.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAll(){
        return categoryRepository.findAll(Sort.by("name"));
    }

    public Category create(Category formCategory){
        return categoryRepository.save(formCategory);
    }

    public Category update(Category formCategory){
        return categoryRepository.save(formCategory);
    }

    public Category getById(Integer id){
        return categoryRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public void deleteById(Integer id) {
        categoryRepository.deleteById(id);
    }
}
