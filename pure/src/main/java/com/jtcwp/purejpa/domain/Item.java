package com.jtcwp.purejpa.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@DiscriminatorColumn
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity
@Table(name = "item")
public abstract class Item extends BaseEntity {

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
