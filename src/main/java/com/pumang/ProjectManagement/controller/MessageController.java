package com.pumang.ProjectManagement.controller;

import com.pumang.ProjectManagement.model.Chat;
import com.pumang.ProjectManagement.model.Message;
import com.pumang.ProjectManagement.model.User;
import com.pumang.ProjectManagement.request.CreateMessageRequest;
import com.pumang.ProjectManagement.service.MessageService;
import com.pumang.ProjectManagement.service.ProjectService;
import com.pumang.ProjectManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestBody CreateMessageRequest request)throws Exception{

        User user=userService.findUserById(request.getSenderId());

        Chat chats=projectService.getProjectById(request.getProjectId()).getChat();

        if(chats==null) throw new Exception("Chat Not Found");

        Message sentMessage=messageService.sendMessage(request.getSenderId(), request.getProjectId(), request.getContent());
        return ResponseEntity.ok(sentMessage);
    }


    @GetMapping("/chat/{projectId}")
    public ResponseEntity<List<Message>> getMessagesByChatId(@PathVariable Long projectId) throws Exception{
        List<Message> messages=messageService.getMessagesByProjectId(projectId);
        return ResponseEntity.ok(messages);
    }
}
