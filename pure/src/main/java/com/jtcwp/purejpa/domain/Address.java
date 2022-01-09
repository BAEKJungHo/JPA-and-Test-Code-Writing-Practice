package com.jtcwp.purejpa.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Embeddable
public class Address {

    private String city;
    private String street;
    private String zipcode;

    public Address copy() {
        return new Address(city, street, zipcode);
    }
}
