package com.dolbunmark.godal.service.impl;

import com.dolbunmark.godal.converter.OrderConvertor;
import com.dolbunmark.godal.domain.Basket;
import com.dolbunmark.godal.domain.Order;
import com.dolbunmark.godal.domain.Product;
import com.dolbunmark.godal.domain.User;
import com.dolbunmark.godal.dto.OrderDto;
import com.dolbunmark.godal.repository.OrderRepository;
import com.dolbunmark.godal.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class OrderServiceImplTest {

    @Mock private OrderConvertor orderConvertor;
    @Mock private UserRepository userRepository;
    @Mock private OrderRepository orderRepository;
    @Mock private Authentication authentication;

    @InjectMocks OrderServiceImpl orderService;

    @Test
    void APPROVE_TEST() {
        when(userRepository.findByLogin(any())).thenReturn(Optional.of(new User()));
        when(orderRepository.findByUserAndActivatedFalse(any()))
                .thenReturn(Optional.of(new Order()));

        orderService.approve(authentication);

        verify(orderRepository, times(1)).save(any());
    }

    @Test
    void GET_TEST() {

        List<Basket> testList =
                List.of(
                        new Basket(2L, new Product(), 1, BigDecimal.valueOf(22), null),
                        new Basket(3L, new Product(), 1, BigDecimal.valueOf(22), null));

        Order order =
                Order.builder()
                        .activated(false)
                        .orderPrice(BigDecimal.valueOf(12))
                        .user(new User())
                        .baskets(testList)
                        .build();

        when(userRepository.findByLogin(any())).thenReturn(Optional.of(new User()));
        when(orderRepository.findByUserAndActivatedTrue(any())).thenReturn(List.of(order));
        when(orderConvertor.toDto(any())).thenReturn(OrderDto.builder().id(123L).build());

        List<OrderDto> actual = orderService.get(authentication);

        assertEquals(List.of(OrderDto.builder().id(123L).build()), actual);
    }

    @Test
    void getCurrentOrder_TEST(){

        List<Basket> testList =
                List.of(
                        new Basket(2L, new Product(), 1, BigDecimal.valueOf(22), null),
                        new Basket(3L, new Product(), 1, BigDecimal.valueOf(22), null));

        Order order =
                Order.builder()
                        .activated(false)
                        .orderPrice(BigDecimal.valueOf(12))
                        .user(new User())
                        .baskets(testList)
                        .build();

        when(userRepository.findByLogin(any())).thenReturn(Optional.of(new User()));
        when(orderRepository.findByUserAndActivatedFalse(any())).thenReturn(Optional.ofNullable(order));
        when(orderConvertor.toDto(any())).thenReturn(OrderDto.builder().id(123L).build());


        OrderDto actual = orderService.getCurrentOrder(authentication);

        assertEquals(OrderDto.builder().id(123L).build(), actual);
    }
}
