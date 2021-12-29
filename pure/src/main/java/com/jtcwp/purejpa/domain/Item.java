package com.jtcwp.purejpa.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "item")
public class Item {

    @Column(name = "item_id", length = 20)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(length = 255)
    private String name;

    @Column(length = 11)
    private int price;

    @Column(name = "stock_quantity", length = 11)
    private int stockQuantity;
}
