package com.dolbunmark.godal.service;

import com.dolbunmark.godal.dto.OrderDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface OrderService {

    void approve(Authentication authentication);

    List<OrderDto> get(Authentication authentication);

    OrderDto getCurrentOrder(Authentication authentication);
}
