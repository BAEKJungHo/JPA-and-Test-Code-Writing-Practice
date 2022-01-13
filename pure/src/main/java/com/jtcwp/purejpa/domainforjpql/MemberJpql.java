package com.jtcwp.purejpa.domainforjpql;

import com.jtcwp.purejpa.domain.Team;
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
    @ManyToOne(fetch = FetchType.LAZY)
    private TeamJpql team;

    public void changeTeam(TeamJpql team) {
        this.team = team;
        team.getMembers().add(this);
    }

    @Override
    public String toString() {
        return "MemberJpql{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", age=" + age +
                '}';
    }
}
