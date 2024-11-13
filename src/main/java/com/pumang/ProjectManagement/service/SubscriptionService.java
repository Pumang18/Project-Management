package com.pumang.ProjectManagement.service;

import com.pumang.ProjectManagement.model.PlanType;
import com.pumang.ProjectManagement.model.Subscription;
import com.pumang.ProjectManagement.model.User;

public interface SubscriptionService {
    Subscription createSubscription(User user);
    Subscription getUserSubscription(Long userId) throws Exception;

    Subscription upgradeSubscription(Long userId, PlanType planType);

    boolean isValid(Subscription subscription);
}
