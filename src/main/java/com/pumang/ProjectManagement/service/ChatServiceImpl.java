package com.pumang.ProjectManagement.service;

import com.pumang.ProjectManagement.model.Chat;
import com.pumang.ProjectManagement.repository.ChatRepository;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService{

    private ChatRepository chatRepository;
    @Override
    public Chat createChat(Chat chat) {
        return chatRepository.save(chat);
    }
}
