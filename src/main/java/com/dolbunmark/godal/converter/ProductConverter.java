package com.dolbunmark.godal.converter;

import com.dolbunmark.godal.domain.Product;
import com.dolbunmark.godal.dto.ProductDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductConverter {

    public List<ProductDto> toProductDto(List<Product> productList) {

        return productList.stream()
                .map(
                        product ->
                                new ProductDto(
                                        product.getId(),
                                        product.getTitle(),
                                        product.getPrice() + " $"))
                .toList();
    }
}
