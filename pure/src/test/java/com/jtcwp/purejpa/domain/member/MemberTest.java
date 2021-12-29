package com.jtcwp.purejpa.domain.member;

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


@DisplayName("Member 도메인 테스트")
@SpringBootTest
class MemberTest {

    @Value("${persistence.unitname}")
    private String persistenceUnitName;

    @DisplayName("Member 등록 후 조회 테스트")
    @Test
    void insertMemberAfterFindMemberTest() {
        // given
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        // when
        tx.begin();

        // insert
        Member member = new Member();
        member.setUsername("Jungho");
        em.persist(member);
        tx.commit();
        em.close();

        // find
        EntityManager em2 = emf.createEntityManager();
        Member findMember = em2.find(Member.class, 1L);
        emf.close();

        // then
        assertThat(findMember.getUsername()).isEqualTo("Jungho");
    }

}