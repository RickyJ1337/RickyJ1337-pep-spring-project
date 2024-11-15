package com.example.service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public Message postMessage(Message message) {
        if (message.getMessageText() == "") {return null;}
        return messageRepository.save(message);
    }


}
