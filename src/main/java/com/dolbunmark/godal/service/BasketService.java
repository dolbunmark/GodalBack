package com.dolbunmark.godal.service;

import com.dolbunmark.godal.dto.ProductDto;
import org.springframework.security.core.Authentication;

public interface BasketService {

    void add(ProductDto productDto, Authentication authentication);

    void update(Authentication authentication, ProductDto productDto);

    void delete(Authentication authentication, ProductDto productDto);
}
