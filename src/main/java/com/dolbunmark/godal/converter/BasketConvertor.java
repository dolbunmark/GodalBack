package com.dolbunmark.godal.converter;

import com.dolbunmark.godal.domain.Basket;
import com.dolbunmark.godal.dto.BasketDto;
import org.springframework.stereotype.Component;

@Component
public class BasketConvertor {

    public BasketDto toDto(Basket basket) {
        return BasketDto.builder()
                .title(basket.getProduct().getTitle())
                .quantity(basket.getQuantity())
                .bigDecimal(basket.getTotal())
                .build();
    }
}
