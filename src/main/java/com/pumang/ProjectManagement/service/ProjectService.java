package com.pumang.ProjectManagement.service;

import com.pumang.ProjectManagement.model.Chat;
import com.pumang.ProjectManagement.model.Project;
import com.pumang.ProjectManagement.model.User;
import org.springframework.security.core.parameters.P;

import java.util.List;

public interface ProjectService {

    Project createProject(Project project,User user) throws Exception;

    List<Project> getProjectByTeam(User user, String category, String tag) throws Exception;

    Project getProjectById(Long projectyId) throws Exception;

    void deleteProject(Long projectId,Long userId) throws Exception;

    Project updateProject(Project updatedProject,Long id) throws Exception;

    void addUserToProject(Long projectId,Long userId) throws Exception;

    void removeUserFromProject(Long projectId,Long userId) throws Exception;

    Chat getChatByProjectId(Long projectId) throws Exception;

    List<Project> searchProject(String keyword,User user)throws Exception;


}
