package com.jtcwp.purejpa.domain;


import com.sun.nio.sctp.PeerAddressChangeNotification;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.bytebuddy.asm.Advice;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    // 테이블명 지정 및 MEMBER_ID 로 조인하겠다라는 의미. 즉, MEMBER_ID 를 FK 로 잡게된다.
    @CollectionTable(name = "FAVORITE_FOOD", joinColumns = @JoinColumn(name = "MEMBER_ID"))
    @ElementCollection
    @Column(name = "FOOD_NAME") // 타입이 String 이기 때문에 예외적으로 허용
    private Set<String> favoriteFoods = new HashSet<>();

//    @CollectionTable(name = "ADDRESS", joinColumns = @JoinColumn(name = "MEMBER_ID"))
//    @ElementCollection
//    private List<Address> addressHistory = new ArrayList<>();

//    @JoinColumn(name = "member_id")
//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<AddressEntity> addressHistory = new ArrayList<>();

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
