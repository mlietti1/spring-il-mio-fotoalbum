package org.lessons.java.springilmiofotoalbum.api;

import jakarta.validation.Valid;
import org.lessons.java.springilmiofotoalbum.model.Message;
import org.lessons.java.springilmiofotoalbum.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("api/messages")
public class MessageRestController {

    @Autowired
    private MessageService messageService;

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody Message formMessage, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getAllErrors().stream().map(err -> err.getDefaultMessage()).collect(Collectors.toList()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(messageService.send(formMessage), HttpStatus.OK);
    }
}
