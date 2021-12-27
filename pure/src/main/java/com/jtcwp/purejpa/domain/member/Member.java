package com.jtcwp.purejpa.domain.member;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter @Setter
@Entity
public class Member {

    @Id
    @GeneratedValue
    private Long id;
    private String userName;
    private String phoneFirst;
    private String phoneSecond;
    private String phoneThird;
}
