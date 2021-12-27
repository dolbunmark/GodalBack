package com.dolbunmark.godal.service.impl;

import com.dolbunmark.godal.domain.Basket;
import com.dolbunmark.godal.domain.Order;
import com.dolbunmark.godal.domain.Product;
import com.dolbunmark.godal.domain.User;
import com.dolbunmark.godal.dto.ProductDto;
import com.dolbunmark.godal.repository.BasketsRepository;
import com.dolbunmark.godal.repository.OrderRepository;
import com.dolbunmark.godal.repository.ProductRepository;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class BasketServiceImplTest {

    @Mock private BasketsRepository basketsRepository;
    @Mock private ProductRepository productRepository;
    @Mock private UserRepository userRepository;
    @Mock private OrderRepository orderRepository;
    @Mock private Authentication authentication;

    @InjectMocks private BasketServiceImpl basketService;

    @Test
    void ADD_TEST() {
        ProductDto productDto = ProductDto.builder().title("book").build();

        List<Basket> testList =
                List.of(
                        new Basket(2L, new Product(), 1, BigDecimal.valueOf(22), null),
                        new Basket(3L, new Product(), 1, BigDecimal.valueOf(22), null));

        ArrayList<Basket> basketList = new ArrayList<>(testList);

        when(authentication.getName()).thenReturn("Alisa");
        when(userRepository.findByLogin("Alisa")).thenReturn(Optional.of(new User()));
        when(productRepository.findProductByTitle("book"))
                .thenReturn(Optional.of(Product.builder().price(BigDecimal.valueOf(123)).build()));
        when(orderRepository.findByUserAndActivatedFalse(any()))
                .thenReturn(
                        Optional.of(
                                new Order(0, null, new User(), BigDecimal.ZERO, true, basketList)));
        when(basketsRepository.findByOrderAndProduct(any(), any()))
                .thenReturn(
                        Optional.of(
                                new Basket(1L, new Product(), 1, BigDecimal.valueOf(11), null)));

        basketService.add(productDto, authentication);

        verify(userRepository, times(1)).findByLogin("Alisa");
        verify(productRepository, times(1)).findProductByTitle("book");
        verify(orderRepository, times(1)).findByUserAndActivatedFalse(any());
        verify(basketsRepository, times(1)).findByOrderAndProduct(any(), any());
    }

    @Test
    void UPDATE_TEST() {
        Product product =
                Product.builder().id(1).price(BigDecimal.valueOf(12)).title("book").build();

        Basket basket =
                Basket.builder()
                        .id(1L)
                        .product(product)
                        .quantity(2)
                        .total(BigDecimal.valueOf(12))
                        .build();

        Order order =
                Order.builder()
                        .baskets(Collections.singletonList(basket))
                        .orderPrice(BigDecimal.valueOf(12))
                        .build();

        when(authentication.getName()).thenReturn("Alisa");
        when(productRepository.findProductByTitle(any())).thenReturn(Optional.of(product));
        when(userRepository.findByLogin("Alisa"))
                .thenReturn(Optional.ofNullable(User.builder().login("Alisa").build()));
        when(orderRepository.findByUserAndActivatedFalse(any())).thenReturn(Optional.of(order));

        basketService.update(authentication, new ProductDto());

        verify(authentication, times(1)).getName();
        verify(productRepository, times(1)).findProductByTitle(any());
        verify(userRepository, times(1)).findByLogin("Alisa");
        verify(orderRepository, times(1)).findByUserAndActivatedFalse(any());
    }

    @Test
    void DELETE_TEST() {

        List<Basket> testList =
                List.of(
                        new Basket(
                                2L,
                                Product.builder().title("book").build(),
                                1,
                                BigDecimal.valueOf(22),
                                null),
                        new Basket(3L, new Product(), 1, BigDecimal.valueOf(22), null));

        when(productRepository.findProductByTitle(any()))
                .thenReturn(Optional.of(Product.builder().title("book").build()));
        when(userRepository.findByLogin(any())).thenReturn(Optional.of(new User()));
        when(orderRepository.findByUserAndActivatedFalse(any()))
                .thenReturn(
                        Optional.of(
                                Order.builder()
                                        .baskets(testList)
                                        .orderPrice(BigDecimal.valueOf(12))
                                        .build()));

        basketService.delete(authentication, new ProductDto());

        verify(orderRepository, times(1)).save(any());
        verify(basketsRepository, times(1)).deleteById(any());
    }
}
