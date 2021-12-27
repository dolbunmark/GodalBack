package com.dolbunmark.godal.service.impl;

import com.dolbunmark.godal.domain.Basket;
import com.dolbunmark.godal.domain.Order;
import com.dolbunmark.godal.domain.Product;
import com.dolbunmark.godal.domain.User;
import com.dolbunmark.godal.dto.ProductDto;
import com.dolbunmark.godal.exception.NoBasketProductException;
import com.dolbunmark.godal.exception.NoOrderException;
import com.dolbunmark.godal.exception.NoProductException;
import com.dolbunmark.godal.exception.NoUserFondException;
import com.dolbunmark.godal.repository.BasketsRepository;
import com.dolbunmark.godal.repository.OrderRepository;
import com.dolbunmark.godal.repository.ProductRepository;
import com.dolbunmark.godal.repository.UserRepository;
import com.dolbunmark.godal.service.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class BasketServiceImpl implements BasketService {

    private final BasketsRepository basketsRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public BasketServiceImpl(
            BasketsRepository basketsRepository,
            ProductRepository productRepository,
            UserRepository userRepository,
            OrderRepository orderRepository) {
        this.basketsRepository = basketsRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public void add(ProductDto productDto, Authentication authentication) {
        User user =
                userRepository
                        .findByLogin(authentication.getName())
                        .orElseThrow(() -> new NoUserFondException("No user"));
        Product product =
                productRepository
                        .findProductByTitle(productDto.getTitle())
                        .orElseThrow(
                                () -> new NoProductException("There is no product with that name"));

        Order order = getOrder(user);
        if (order.getId() == 0) {
            orderRepository.save(order);
        }

        Basket basket = getBasket(product, order);
        basket.setQuantity(basket.getQuantity() + 1);
        basket.setTotal(basket.getTotal().add(product.getPrice()));

        order.setOrderPrice(order.getOrderPrice().add(product.getPrice()));
        order.getBaskets().add(basket);
        orderRepository.save(order);
    }

    @Override
    @Transactional
    public void update(Authentication authentication, ProductDto productDto) {
        Product product =
                productRepository
                        .findProductByTitle(productDto.getTitle())
                        .orElseThrow(() -> new NoProductException(""));
        User user =
                userRepository
                        .findByLogin(authentication.getName())
                        .orElseThrow(() -> new NoUserFondException(""));
        Order order =
                orderRepository
                        .findByUserAndActivatedFalse(user)
                        .orElseThrow(NoOrderException::new);

        List<Basket> basketList = order.getBaskets();

        Basket basket =
                basketList.stream()
                        .filter(baskets -> baskets.getProduct().equals(product))
                        .findFirst()
                        .orElseThrow(NoBasketProductException::new);

        if (basket.getQuantity() > 1) {
            basket.setQuantity(basket.getQuantity() - 1);
            basket.setTotal(basket.getTotal().subtract(product.getPrice()));
            order.setOrderPrice(order.getOrderPrice().subtract(product.getPrice()));
        }
        orderRepository.save(order);
    }

    @Override
    @Transactional
    public void delete(Authentication authentication, ProductDto productDto) {
        Product product =
                productRepository
                        .findProductByTitle(productDto.getTitle())
                        .orElseThrow(() -> new NoProductException(""));
        User user =
                userRepository
                        .findByLogin(authentication.getName())
                        .orElseThrow(() -> new NoUserFondException(""));
        Order order =
                orderRepository
                        .findByUserAndActivatedFalse(user)
                        .orElseThrow(NoOrderException::new);

        List<Basket> basketList = order.getBaskets();

        Basket basket =
                basketList.stream()
                        .filter(baskets -> baskets.getProduct().equals(product))
                        .findFirst()
                        .orElseThrow(NoBasketProductException::new);

        order.setOrderPrice(order.getOrderPrice().subtract(basket.getTotal()));

        orderRepository.save(order);

        basketsRepository.deleteById(basket.getId());
    }

    private Order getOrder(User user) {
        return orderRepository
                .findByUserAndActivatedFalse(user)
                .orElseGet(
                        () ->
                                Order.builder()
                                        .user(user)
                                        .activated(false)
                                        .orderPrice(BigDecimal.ZERO)
                                        .baskets(new ArrayList<>())
                                        .build());
    }

    private Basket getBasket(Product product, Order order) {
        return basketsRepository
                .findByOrderAndProduct(order, product)
                .orElseGet(
                        () ->
                                Basket.builder()
                                        .product(product)
                                        .order(order)
                                        .total(BigDecimal.ZERO)
                                        .quantity(0)
                                        .build());
    }
}
