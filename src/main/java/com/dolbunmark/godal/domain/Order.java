package com.dolbunmark.godal.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "ORDERS")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "TIME_CREATE")
    private Timestamp timeCreate;

    @ManyToOne(optional = false, cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name = "ORDER_PRICE")
    private BigDecimal orderPrice;

    @Column(name = "ACTIVATED")
    private boolean activated;

    @OneToMany(cascade = CascadeType.MERGE)
    @JoinColumn(name = "ORDER_ID")
    private List<Basket> baskets;
}
