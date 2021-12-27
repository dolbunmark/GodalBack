package com.dolbunmark.godal.controller;

import com.dolbunmark.godal.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/orders")
    public void approveOrder(Authentication authentication) {
        orderService.approve(authentication);
    }

    @GetMapping("/orders")
    public ResponseEntity getOrder(Authentication authentication) {
        return ResponseEntity.ok(orderService.get(authentication));
    }

    @GetMapping("/ordersCur")
    public ResponseEntity getCurrentOrder(Authentication authentication) {
        return ResponseEntity.ok(orderService.getCurrentOrder(authentication));
    }
}
