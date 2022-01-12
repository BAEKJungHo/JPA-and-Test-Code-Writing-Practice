package com.jtcwp.purejpa.domainforjpql;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@Entity
@Table(name = "member_j")
public class MemberJpql {

    @Column(name = "member_id", length = 20)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String username;

    private int age;

    @JoinColumn(name = "team_id")
    @ManyToOne
    private TeamJpql team;
}
