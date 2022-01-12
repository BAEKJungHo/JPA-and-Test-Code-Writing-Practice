package com.jtcwp.purejpa.domainforjpql;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "team_j")
public class TeamJpql {

    @Column(name = "team_id", length = 20)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String name;

    @OneToMany(mappedBy = "team")
    private List<MemberJpql> members = new ArrayList<>();
}
