package com.jtcwp.purejpa.test.lazyloading;

import com.jtcwp.purejpa.domain.Member;
import com.jtcwp.purejpa.domain.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

@SpringBootTest
class LazyLoadingTest {

    @Value("${persistence.unitname}")
    private String persistenceUnitName;

    @DisplayName("양방향 연관관계에서 서로 값을 설정")
    @Test
    void lazyLoadingTest() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.setTeam(team);
            em.persist(member);

            em.flush(); // 영속성 컨텍스트의 변경 내용을 DB 에 동기화
            em.clear(); // 영속성 컨텍스트를 비워줌으로써 준영속 상태가 된다.

            Member m = em.find(Member.class, member.getId());

            // Team 은 Proxy : class com.jtcwp.purejpa.domain.Team$HibernateProxy$N1Y9mJLt
            System.out.println(m.getTeam().getClass());

            // 이 시점에 쿼리가 나감
            System.out.println(m.getTeam().getName());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
