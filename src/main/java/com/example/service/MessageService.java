package com.example.service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public Message createNewMessage(Message message) {
        long postedBy = message.getPostedBy();
        if (message.getMessageText() == "") {throw new IllegalArgumentException("Blank message");}
        if (message.getMessageText().length() > 255) {throw new IllegalArgumentException("Character length over 255");}
        if (messageRepository.findMessageByPostedBy(postedBy) == null) {throw new IllegalArgumentException("User does not exist");}
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageByID(int account_id) {
        return messageRepository.findMessageByID(account_id);
    }


}
