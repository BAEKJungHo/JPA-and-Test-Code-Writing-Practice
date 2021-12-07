package com.jtcwp.purejpa.entitymanagerfactory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("EntityManagerFactory 와 EntityManager 생성 테스트")
@SpringBootTest
class EntityManagerFactoryTest {

    @Value("${persistence.unitname}")
    private String persistenceUnitName;

    @DisplayName("EntityManagerFactory 생성")
    @Test
    void createEntityManagerFactory() {
        // when
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        // then
        assertThat(emf).isNotNull();
    }

    @DisplayName("EntityManager 생성")
    @Test
    void createEntityManager() {
        // when
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        EntityManager em = emf.createEntityManager();
        // then
        assertThat(em).isNotNull();
    }

}
