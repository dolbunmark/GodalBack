package com.dolbunmark.godal.converter;

import com.dolbunmark.godal.domain.Order;
import com.dolbunmark.godal.dto.BasketDto;
import com.dolbunmark.godal.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderConvertor {

    private final BasketConvertor basketConvertor;

    @Autowired
    public OrderConvertor(BasketConvertor basketConvertor) {
        this.basketConvertor = basketConvertor;
    }

    public OrderDto toDto(Order order){

        List<BasketDto> basketDtoList =
                order.getBaskets().stream()
                        .map(basketConvertor::toDto)
                        .toList();

        return OrderDto.builder()
                .id(order.getId())
                .orderPrice(order.getOrderPrice())
                .dateCreate(order.getTimeCreate())
                .basketDtoList(basketDtoList)
                .build();
    }
}
