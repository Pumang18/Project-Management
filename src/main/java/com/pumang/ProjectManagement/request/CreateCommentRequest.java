package com.pumang.ProjectManagement.request;


import lombok.Data;

@Data
public class CreateCommentRequest {

    private Long issueId;
    private String content;
}
