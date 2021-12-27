package com.dolbunmark.godal.service.impl;

import com.dolbunmark.godal.converter.OrderConvertor;
import com.dolbunmark.godal.domain.Order;
import com.dolbunmark.godal.domain.User;
import com.dolbunmark.godal.dto.OrderDto;
import com.dolbunmark.godal.exception.NoOrderException;
import com.dolbunmark.godal.exception.NoUserFondException;
import com.dolbunmark.godal.repository.OrderRepository;
import com.dolbunmark.godal.repository.UserRepository;
import com.dolbunmark.godal.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderConvertor orderConvertor;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(
            OrderConvertor orderConvertor,
            UserRepository userRepository,
            OrderRepository orderRepository) {
        this.orderConvertor = orderConvertor;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public void approve(Authentication authentication) {
        User user =
                userRepository
                        .findByLogin(authentication.getName())
                        .orElseThrow(() -> new NoUserFondException("No user found"));
        Order order =
                orderRepository
                        .findByUserAndActivatedFalse(user)
                        .orElseThrow(NoOrderException::new);

        order.setActivated(true);
        order.setTimeCreate(new Timestamp(System.currentTimeMillis()));
        orderRepository.save(order);
    }

    @Override
    @Transactional
    public List<OrderDto> get(Authentication authentication) {
        User user =
                userRepository
                        .findByLogin(authentication.getName())
                        .orElseThrow(() -> new NoUserFondException("No user found"));

        List<Order> order = orderRepository.findByUserAndActivatedTrue(user);

        return order.stream().map(orderConvertor::toDto).toList();
    }

    @Override
    @Transactional
    public OrderDto getCurrentOrder(Authentication authentication) {
        User user =
                userRepository
                        .findByLogin(authentication.getName())
                        .orElseThrow(() -> new UsernameNotFoundException("No user found"));
        Order order =
                orderRepository
                        .findByUserAndActivatedFalse(user)
                        .orElseThrow(NoOrderException::new);

        return orderConvertor.toDto(order);
    }
}
