package com.dolbunmark.godal.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder(toBuilder = true)
@Entity
@Table(name = "BASKET")
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne(optional = false, cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @Column(name = "QUANTITY")
    private long quantity;

    @Column(name = "TOTAL")
    private BigDecimal total;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "ORDER_ID")
    private Order order;
}
