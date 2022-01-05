package com.jtcwp.purejpa.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "orders", indexes = @Index(name = "idx_member_id", columnList = "member_id"))
public class Order extends BaseEntity {

    @Column(name = "order_id", length = 20)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @JoinColumn(name = "member_id")
    @ManyToOne
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    @Column(name = "order_date", length = 6)
    private LocalDateTime orderDate;

    @Column(length = 100)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
