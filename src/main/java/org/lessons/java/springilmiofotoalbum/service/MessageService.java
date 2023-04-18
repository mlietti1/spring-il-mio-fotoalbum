package org.lessons.java.springilmiofotoalbum.service;

import org.lessons.java.springilmiofotoalbum.model.Message;
import org.lessons.java.springilmiofotoalbum.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public List<Message> getAllMessages(){
        return messageRepository.findAll(Sort.by("id"));
    }

    public Message getById(Integer id) throws ResponseStatusException {
        return messageRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Message send(Message formMessage){
        Message messageToSave = new Message();
        messageToSave.setEmail(formMessage.getEmail());
        messageToSave.setText(formMessage.getText());
        return messageRepository.save(messageToSave);
    }

    public void delete(Integer id){
        Message messageToDelete = getById(id);
        messageRepository.delete(messageToDelete);
    }
}
