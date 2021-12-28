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

@DisplayName("IDENTITY 전략 테스트")
@SpringBootTest
class IdentityStrategyTest {

    @Value("${persistence.unitname}")
    private String persistenceUnitName;

    @DisplayName("em.persist() 시 INSERT 쿼리가 나가는지 테스트")
    @Test
    void insertQueryIsWorkAtPersist() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            Member member = new Member();
            member.setUsername("BAEK");

            // 이 시점에, INSERT QUERY 가 실행되고
            // 내부적으로 DB 에 등록된 ID 값을 가져와서 MEMBER 엔티티에 설정한다.
            // 따라서, MEMBER 엔티티에 ID(PK) 값이 있기 때문에 1차 캐시(영속성 엔티티)에서 관리될 수 있다.
            em.persist(member);

            // MEMBER 엔티티에서 ID 값을 꺼내는 순간 ID(PK) 값이 바인딩 되는것이 아니다.
            System.out.println(member.getId());

            // 1차 캐시에서 조회
            Member findMember = em.find(Member.class, 1L);
            System.out.println(findMember.getId());
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
            emf.close();
        }
    }
}
