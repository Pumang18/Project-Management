package com.pumang.ProjectManagement.controller;

import com.pumang.ProjectManagement.model.PlanType;
import com.pumang.ProjectManagement.model.Subscription;
import com.pumang.ProjectManagement.model.User;
import com.pumang.ProjectManagement.service.SubscriptionService;
import com.pumang.ProjectManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscription")
public class SubscriptionController {
    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private UserService userService;


    @GetMapping("/user")
    public ResponseEntity<Subscription> getUserSubscription(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        Subscription subscription=subscriptionService.getUserSubscription(user.getId());

        return new ResponseEntity<>(subscription, HttpStatus.OK);
    }

    @GetMapping("/upgrade")
    public ResponseEntity<Subscription> upgradeUserSubscription(
            @RequestHeader("Authorization") String jwt,
            @RequestParam PlanType planType
            ) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        Subscription subscription=subscriptionService.upgradeSubscription(user.getId(),planType);

        return new ResponseEntity<>(subscription, HttpStatus.OK);
    }


}
