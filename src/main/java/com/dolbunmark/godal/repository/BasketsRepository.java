package com.dolbunmark.godal.repository;

import com.dolbunmark.godal.domain.Basket;
import com.dolbunmark.godal.domain.Order;
import com.dolbunmark.godal.domain.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BasketsRepository extends CrudRepository<Basket, Long> {

    void deleteById(Long id);

    Optional<Basket> findByOrderAndProduct(Order order, Product product);
}
