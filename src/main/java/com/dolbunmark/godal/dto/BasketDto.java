package com.dolbunmark.godal.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder(toBuilder = true)
public class BasketDto {

    private String title;
    private long quantity;
    private BigDecimal bigDecimal;
}
