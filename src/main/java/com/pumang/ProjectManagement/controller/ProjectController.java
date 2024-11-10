package com.pumang.ProjectManagement.controller;

import com.pumang.ProjectManagement.model.Chat;
import com.pumang.ProjectManagement.model.Invitation;
import com.pumang.ProjectManagement.model.Project;
import com.pumang.ProjectManagement.model.User;
import com.pumang.ProjectManagement.repository.InviteRequest;
import com.pumang.ProjectManagement.response.MessageResponse;
import com.pumang.ProjectManagement.service.InvitationService;
import com.pumang.ProjectManagement.service.ProjectService;
import com.pumang.ProjectManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Autowired
    private InvitationService invitationService;

    @GetMapping
    public ResponseEntity<List<Project>>getProjects(@RequestParam(required = false)String category,
                                                    @RequestParam(required = false)String tag,
                                                    @RequestHeader("Authorization")String jwt) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        List<Project> projects=projectService.getProjectByTeam(user,category,tag);
        return new ResponseEntity<>(projects, HttpStatus.OK);



    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Project>getProjectById(@PathVariable Long projectId,
                                                    @RequestHeader("Authorization")String jwt) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        Project projects=projectService.getProjectById(projectId);
        return new ResponseEntity<>(projects, HttpStatus.OK);



    }

    @PostMapping
    public ResponseEntity<Project>createProject(
                                                 @RequestHeader("Authorization")String jwt,
                                                @RequestBody Project project
    ) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        Project createdProjects=projectService.createProject(project,user);
        return new ResponseEntity<>(createdProjects, HttpStatus.OK);



    }


    @PatchMapping("/api/{projectId}")
    public ResponseEntity<Project>updateProject(@PathVariable Long projectId,
                                                @RequestHeader("Authorization")String jwt,
                                                @RequestBody Project project
    ) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        Project updatedProjects=projectService.updateProject(project,projectId);
        return new ResponseEntity<>(updatedProjects, HttpStatus.OK);



    }

    @DeleteMapping("/api/{projectId}")
    public ResponseEntity<MessageResponse>deleteProject(@PathVariable Long projectId,
                                                @RequestHeader("Authorization")String jwt
    ) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        projectService.deleteProject(projectId,user.getId());
        MessageResponse response=new MessageResponse("Project Deleted Successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);



    }


    @GetMapping("/search")
    public ResponseEntity<List<Project>>searchProject(@RequestParam(required = false) String keyword,
                                                        @RequestHeader("Authorization")String jwt
    ) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        List<Project> projects=projectService.searchProject(keyword,user);
        return new ResponseEntity<>(projects, HttpStatus.OK);



    }

    @GetMapping("/{projectId}/chat")
    public ResponseEntity<Chat>getChatProjectById(@PathVariable Long projectId,
                                                 @RequestHeader("Authorization")String jwt) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        Chat chat=projectService.getChatByProjectId(projectId);
        return new ResponseEntity<>(chat, HttpStatus.OK);



    }



    @PostMapping("/invite")
    public ResponseEntity<MessageResponse>inviteProject(
            @RequestBody InviteRequest req,
            @RequestHeader("Authorization")String jwt,
            @RequestBody Project project
    ) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        invitationService.sendInvitation(req.getEmail(),req.getProjectId());
        MessageResponse res=new MessageResponse("User Invitation sent");
        return new ResponseEntity<>(res, HttpStatus.OK);



    }
    @GetMapping("/accept_invitation")
    public ResponseEntity<Invitation>acceptInviteProject(
            @RequestParam String token,
            @RequestHeader("Authorization")String jwt,
            @RequestBody Project project
    ) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        Invitation invitation=invitationService.acceptInvitation(token,user.getId());
        projectService.addUserToProject(invitation.getProjectId(),user.getId());
        return new ResponseEntity<>(invitation, HttpStatus.OK);



    }



}
