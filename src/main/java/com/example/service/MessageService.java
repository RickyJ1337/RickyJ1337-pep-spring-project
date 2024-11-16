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
        int postedBy = message.getPostedBy();

        if (message.getMessageText() == "") {throw new IllegalArgumentException("Blank message");}
        if (message.getMessageText().length() > 255) {throw new IllegalArgumentException("Character length over 255");}
        List<Message> messages = messageRepository.findMessageByPostedBy(postedBy);
        if (messages.isEmpty()) {throw new IllegalArgumentException("User does not exist");}

        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageByID(int message_id) {
        return messageRepository.findMessageByID(message_id);
    }

    public boolean deleteMessage(int message_id) {
        Message messageToDelete = messageRepository.findMessageByID(message_id);
        if (messageToDelete != null) {
            messageRepository.delete(messageToDelete);
            return true;
        }
        return false;
    }

    //Update message by ID
    public int updateMessage(int message_id, Message message) {
        Message messageToUpdate = messageRepository.findMessageByID(message_id);
        String messageText = message.getMessageText();
        if (messageToUpdate == null) {throw new IllegalArgumentException("Invalid message id");}
        if (messageText == "") {throw new IllegalArgumentException("Blank message");}
        if (messageText.length() > 255) {throw new IllegalArgumentException("Character length over 255");}

        messageToUpdate.setMessageText(messageText);
        messageRepository.save(messageToUpdate);
        return 1;
    }

    //Retrieve messages by user
    public List<Message> getMessagesByUser(int account_id) {
        return messageRepository.findMessageByPostedBy(account_id);
    }
}
