package com.jtcwp.purejpa.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "member")
public class Member extends BaseEntity {

    @Column(name = "member_id", length = 20)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @JoinColumn(name = "team_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

//    @JoinColumn(name = "locker_id")
//    @OneToOne
//    private Locker locker;

    @Column(name = "name", length = 255)
    private String username;

    @Column(name = "city", length = 500)
    private String city;

    @Column(name = "street", length = 500)
    private String street;

    @Column(name = "zipcode", length = 10)
    private String zipcode;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
}
