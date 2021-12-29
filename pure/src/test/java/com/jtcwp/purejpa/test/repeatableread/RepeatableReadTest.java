package com.jtcwp.purejpa.test.repeatableread;

import com.jtcwp.purejpa.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Repeatable Read Test")
@SpringBootTest
class RepeatableReadTest {

    @Value("${persistence.unitname}")
    private String persistenceUnitName;

    @DisplayName("1차 캐시를 통한 Repeatable Read 를 지원하는지 테스트")
    @Test
    void repeatableReadByCache() throws Exception {
        // given
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        insertDummyData(emf);

        // when
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin(); // 선행 트랜잭션 시작
        Member findMember1 = em.find(Member.class, 1L);
        /**
         * findMember1 : Member 를 데이터베이스에서 조회
         * Member 를 다시 조회하기 전에 H2 데이터 베이스에서 UPDATE 문 실행
         * findMember2 : Member 를 데이터베이스가 아닌 1차 캐시에서 조회
         */
        Member findMember2 = em.find(Member.class, 1L);
        tx.commit(); // 선행 트랜잭션 종료료
        em.close();
        emf.close();

        // then
        assertThat(findMember1.getUsername()).isEqualTo(findMember2.getUsername());
    }

    @DisplayName("더미 데이터 삽입")
    private void insertDummyData(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        Member member = new Member();
        member.setUsername("JungHo");
        em.persist(member);
        tx.commit();

        em.close();
    }
}
