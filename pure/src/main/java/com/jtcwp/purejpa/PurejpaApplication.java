package com.jtcwp.purejpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@SpringBootApplication
public class PurejpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(PurejpaApplication.class, args);
    }

}
