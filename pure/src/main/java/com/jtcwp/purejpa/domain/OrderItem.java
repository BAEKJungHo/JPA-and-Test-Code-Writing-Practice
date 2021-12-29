package com.jtcwp.purejpa.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "order_item")
public class OrderItem {

    @Column(name = "order_item_id", length = 20)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @JoinColumn(name = "order_id")
    @ManyToOne
    private Order order;

    @JoinColumn(name = "item_id")
    @ManyToOne
    private Item item;

    @Column(name = "order_price", length = 11)
    private int orderPrice;

    @Column(length = 11)
    private int count;
}
