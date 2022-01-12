package com.jtcwp.purejpa.domainforjpql;

import com.jtcwp.purejpa.domain.Address;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "order_j")
public class OrderJpql {

    @Column(name = "order_id", length = 20)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private int orderAmount;

    @Embedded
    private Address address;

    @JoinColumn(name = "product_id")
    @ManyToOne
    private Product product;
}
