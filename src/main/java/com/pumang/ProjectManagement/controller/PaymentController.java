package com.pumang.ProjectManagement.controller;

import com.pumang.ProjectManagement.model.PlanType;
import com.pumang.ProjectManagement.model.User;
import com.pumang.ProjectManagement.response.PaymentLinkResponse;
import com.pumang.ProjectManagement.service.UserService;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import netscape.javascript.JSObject;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    
    @Value("${razorpay.api.key}")
    private  String apiKey;
    
    @Value("${razorpay.api.secret}")
    private String apiSecret;
    
    @Autowired
    private UserService userService;
    
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(
            @PathVariable PlanType planType,
            @RequestHeader("Authorization") String jwt
            )throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
        int amount=799*100;
        if(planType.equals(PlanType.ANNUALLY)){
            amount=amount*12;
            amount=(int)(amount*0.7);
        }
        

            RazorpayClient razorpayClient=new RazorpayClient(apiKey,apiSecret);
            JSONObject paymentLinkRequest=new JSONObject();
            paymentLinkRequest.put("amount",amount);
            paymentLinkRequest.put("currency","INR");

            JSONObject customer=new JSONObject();
            customer.put("name",user.getFullName());
            customer.put("email",user.getEmail());
            paymentLinkRequest.put("customer",customer);

            JSONObject notify=new JSONObject();
            notify.put("email",true);
            paymentLinkRequest.put("notify",notify);

            paymentLinkRequest.put("callback_url","http://localhost:8081/upgrade_plan/success?planType"+planType);

            PaymentLink paymentLink=razorpayClient.paymentLink.create(paymentLinkRequest);
            
            String paymentLinkId=paymentLink.get("id");
            String paymentLinkUrl=paymentLink.get("short_url");

            PaymentLinkResponse res=new PaymentLinkResponse();
            res.setPayment_link_id(paymentLinkId);
            res.setPayment_link_url(paymentLinkUrl);

            return new ResponseEntity<>(res, HttpStatus.CREATED);
            
            
        }
}
