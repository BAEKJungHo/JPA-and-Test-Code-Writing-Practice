package com.jtcwp.purejpa.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@DiscriminatorValue("A")
@Entity
public class Album extends Item {

    private String aritist;
    private String etc;
}
