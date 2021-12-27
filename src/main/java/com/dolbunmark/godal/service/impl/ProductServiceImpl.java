package com.dolbunmark.godal.service.impl;

import com.dolbunmark.godal.converter.ProductConverter;
import com.dolbunmark.godal.dto.ProductDto;
import com.dolbunmark.godal.repository.ProductRepository;
import com.dolbunmark.godal.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductConverter productConverter;

    @Autowired
    public ProductServiceImpl(
            ProductRepository productRepository, ProductConverter productConverter) {
        this.productRepository = productRepository;
        this.productConverter = productConverter;
    }

    @Override
    @Transactional
    public List<ProductDto> getProducts() {
        return productConverter.toProductDto(productRepository.findAll());
    }

}
