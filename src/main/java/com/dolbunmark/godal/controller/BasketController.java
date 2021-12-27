package com.dolbunmark.godal.controller;

import com.dolbunmark.godal.dto.ProductDto;
import com.dolbunmark.godal.service.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/baskets")
public class BasketController {

    private final BasketService basketService;

    @Autowired
    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    @PutMapping
    // decrement product
    public ResponseEntity update(
            @RequestBody ProductDto productDto, Authentication authentication) {
        basketService.update(authentication, productDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{title}")
    public ResponseEntity delete(@PathVariable String title, Authentication authentication) {
        basketService.delete(authentication, ProductDto.builder().title(title).build());
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity addBasket(
            @RequestBody ProductDto productDto, Authentication authentication) {
        basketService.add(productDto, authentication);
        return ResponseEntity.ok().build();
    }
}
