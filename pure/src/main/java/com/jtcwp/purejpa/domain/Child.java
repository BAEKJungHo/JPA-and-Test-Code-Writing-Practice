package com.jtcwp.purejpa.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@Entity
public class Child {

    @Column(name = "child_id", length = 20)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String name;

    @JoinColumn(name = "parent_id")
    @ManyToOne
    private Parent parent;

}
