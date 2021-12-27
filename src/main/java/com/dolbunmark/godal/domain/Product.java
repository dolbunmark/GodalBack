package com.dolbunmark.godal.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Setter
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder(toBuilder = true)
@Entity
@Table(name = "PRODUCT")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "PRICE")
    private BigDecimal price;
}
