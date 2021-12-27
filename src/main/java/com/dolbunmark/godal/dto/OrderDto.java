package com.dolbunmark.godal.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
@Builder(toBuilder = true)
public class OrderDto {

    private Long id;
    private Timestamp dateCreate;
    private BigDecimal orderPrice;
    private List<BasketDto> basketDtoList;
}
