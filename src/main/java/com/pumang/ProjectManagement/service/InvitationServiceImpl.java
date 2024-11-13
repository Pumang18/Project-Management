package com.pumang.ProjectManagement.service;

import com.pumang.ProjectManagement.model.Invitation;
import com.pumang.ProjectManagement.repository.InvitationRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InvitationServiceImpl implements InvitationService{

    @Autowired
    private InvitationRepository invitationRepository;

    @Autowired
    EmailService emailService;


    @Override
    public void sendInvitation(String email, Long prijectId) throws MessagingException {
        String invitationToken= UUID.randomUUID().toString();

        Invitation invitation=new Invitation();
        invitation.setEmail(email);
        invitation.setProjectId(prijectId);
        invitation.setToken(invitationToken);

        invitationRepository.save(invitation);

        String invitationLink="http://localhost:8081/accept_invitation?token="+invitationToken;

        emailService.sendEmailWithToken(email,invitationLink);

    }

    @Override
    public Invitation acceptInvitation(String token, Long userId) throws Exception {
        Invitation invitation=invitationRepository.findByToken(token);

        if(invitation==null){
            throw new Exception("Invalid Invitation Token");
        }
        return  invitation;
    }

    @Override
    public String getTokenByUserMail(String userEmail) {
        Invitation invitation=invitationRepository.findByEmail(userEmail);
        return invitation.getToken();
    }

    @Override
    public void deleteToken(String token) {
        Invitation invitation=invitationRepository.findByToken(token);

        invitationRepository.delete(invitation);
    }
}
