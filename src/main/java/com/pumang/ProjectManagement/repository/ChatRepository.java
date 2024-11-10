package com.pumang.ProjectManagement.repository;

import com.pumang.ProjectManagement.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat,Long> {
}
