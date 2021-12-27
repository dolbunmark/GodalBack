package com.dolbunmark.godal.service.impl;

import com.dolbunmark.godal.converter.ProductConverter;
import com.dolbunmark.godal.dto.ProductDto;
import com.dolbunmark.godal.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class ProductServiceImplTest {

    @Mock private ProductRepository productRepository;
    @Mock private ProductConverter productConverter;
    @Mock private Authentication authentication;

    @InjectMocks ProductServiceImpl productService;

    @Test
    void getProducts() {
        when(productConverter.toProductDto(any()))
                .thenReturn(List.of(ProductDto.builder().id(123).build()));

        List<ProductDto> actual = productService.getProducts();

        assertEquals(List.of(ProductDto.builder().id(123).build()), actual);
    }
}
