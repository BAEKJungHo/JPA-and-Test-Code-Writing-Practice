package com.jtcwp.purejpa.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "locker")
public class Locker extends BaseEntity {

    @Column(name = "locker_id", length = 20)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String name;

//    @OneToOne(mappedBy = "locker")
//    private Member member;
}
