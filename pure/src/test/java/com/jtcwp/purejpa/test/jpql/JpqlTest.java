package com.jtcwp.purejpa.test.jpql;

import com.jtcwp.purejpa.domain.Member;
import com.jtcwp.purejpa.domainforjpql.MemberJpql;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.*;

@SpringBootTest
class JpqlTest {

    @Value("${persistence.unitname}")
    private String persistenceUnitName;

    @Test
    void typeQueryAndQueryTest() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            MemberJpql member = new MemberJpql();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            // 반환 타입이 명확
            TypedQuery<MemberJpql> query1 = em.createQuery("select m from MemberJpql m", MemberJpql.class);
            MemberJpql result = query1.getSingleResult(); // Exception 발생
            System.out.println("query1 result : " + result);

            TypedQuery<MemberJpql> query4 = em.createQuery("select m from MemberJpql m where m.username = :username", MemberJpql.class);
            query4.setParameter("username", "member1");
            MemberJpql result4 = query4.getSingleResult();
            System.out.println("query4 result : " + result4);

            TypedQuery<String> query2 = em.createQuery("select m.username from MemberJpql m", String.class);

            // 반환 타입이 명확하지 않음
            Query query3 = em.createQuery("select m.username, m.age from MemberJpql m", String.class);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

}
