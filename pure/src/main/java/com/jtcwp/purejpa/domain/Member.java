package com.jtcwp.purejpa.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.bytebuddy.asm.Advice;

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

    @Embedded
    private Period period;

    @Embedded
    private Address homeAddress;

    @AttributeOverrides({
            @AttributeOverride(name = "city", column = @Column(name = "WORK_CITY")),
            @AttributeOverride(name = "street", column = @Column(name = "WORK_STREET")),
            @AttributeOverride(name = "zipcode", column = @Column(name = "WORK_ZIPCODE"))
    })
    @Embedded
    private Address workAddress;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
}
