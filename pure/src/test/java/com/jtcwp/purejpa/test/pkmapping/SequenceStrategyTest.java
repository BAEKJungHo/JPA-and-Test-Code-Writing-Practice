package com.jtcwp.purejpa.test.pkmapping;

import com.jtcwp.purejpa.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

@DisplayName("SEQUENCE 전략 테스트")
@SpringBootTest
class SequenceStrategyTest {

    @Value("${persistence.unitname}")
    private String persistenceUnitName;

    @DisplayName("시퀀스를 통한 기본키 생성 전략을 사용하는 경우 INSERT 쿼리가 트랜잭션 커밋 시점에 나가는지 테스트")
    @Test
    void insertQueryIsWorkAtTransactionCommit() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            Member member = new Member();
            member.setUsername("BAEK");

            // 이 시점에, 시퀀스가 호출되어서(call next value for hibernate_sequence)
            // 데이터베이스에 생성된 시퀀스 값을 MEMBER 엔티티 ID 에 넣어서, 영속성 엔티티로 등록 한다.
            em.persist(member);

            // MEMBER 엔티티에서 ID 값을 꺼내는 순간 ID(PK) 값이 바인딩 되는것이 아니다.
            System.out.println(member.getId());

            // 1차 캐시에서 조회
            Member findMember = em.find(Member.class, 1L);
            System.out.println(findMember.getId());

            // 이 시점에 실제 INSERT QUERY 가 나간다.
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
            emf.close();
        }
    }
}
