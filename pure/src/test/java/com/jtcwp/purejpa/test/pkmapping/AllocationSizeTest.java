package com.jtcwp.purejpa.test.pkmapping;


import com.jtcwp.purejpa.domain.member.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

@DisplayName("AllocationSize 테스트")
@SpringBootTest
class AllocationSizeTest {

    @Value("${persistence.unitname}")
    private String persistenceUnitName;

    @DisplayName("AllocationSize 를 통한 성능 최적화")
    @Test
    void optimizeByAllocationSize() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            Member member = new Member();
            member.setUsername("BAEK");

            // 처음 호출할 때는 값이 1이다. 하지만 설정값이 50으로 되어있기 때문에
            // 한번더 호출하여 SEQ 를 1에서 51로 변경시킨다.
            em.persist(member); // DB Sequence Call : 1, 51

            // DB SEQ = 1 | 1
            // DB SEQ = 51 | 2
            // DB SEQ = 51 | 3
            em.persist(member); // Memory Call
            em.persist(member); // Memory Call
            em.persist(member); // Memory Call

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
            emf.close();
        }
    }
}
