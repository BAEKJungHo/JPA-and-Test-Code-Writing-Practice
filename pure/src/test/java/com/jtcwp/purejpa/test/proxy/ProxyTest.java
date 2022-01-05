package com.jtcwp.purejpa.test.proxy;

import com.jtcwp.purejpa.domain.Member;
import com.jtcwp.purejpa.domain.Team;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

@DisplayName("프록시 테스트")
@SpringBootTest
class ProxyTest {

    @Value("${persistence.unitname}")
    private String persistenceUnitName;

    @DisplayName("em.getReference() 테스트")
    @Test
    void emGetReferenceTest() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Member member = new Member();
            member.setUsername("Member1");
            em.persist(member);

            em.flush(); // 영속성 컨텍스트의 변경 내용을 DB 에 동기화
            em.clear(); // 영속성 컨텍스트를 비워줌으로써 준영속 상태가 된다.

            Member findMember1 = em.getReference(Member.class, member.getId());
            System.out.println("findMember1 : " + findMember1.getClass());

            Member findMember2 = em.find(Member.class, member.getId());
            System.out.println("findMember2 : " + findMember2.getClass());
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }


}
