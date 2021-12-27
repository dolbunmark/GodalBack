package com.dolbunmark.godal.repository;

import com.dolbunmark.godal.domain.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, Long> {

    Optional<Product> findProductByTitle(String name);

    List<Product> findAll();
}
