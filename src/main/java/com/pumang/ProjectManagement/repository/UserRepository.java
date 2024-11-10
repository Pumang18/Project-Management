package com.pumang.ProjectManagement.repository;

import com.pumang.ProjectManagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmail(String email);
}
