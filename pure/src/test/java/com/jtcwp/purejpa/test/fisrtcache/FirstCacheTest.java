package com.jtcwp.purejpa.test.fisrtcache;

import com.jtcwp.purejpa.domain.member.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

@DisplayName("1차 캐시 테스트")
@SpringBootTest
public class FirstCacheTest {

    @Value("${persistence.unitname}")
    private String persistenceUnitName;

    @DisplayName("EntityManger 를 스레드간 공유하면 1차 캐시의 내용을 공유하는지 테스트")
    @Test
    void oneEntityManagerAndTwoThreadIsSharedFirstCache() throws Exception {
        // given
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        insertDummyData(emf);

        // when
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        /*
         * 서로 다른 스레드 두개 생성 : 클라이언트의 요청이 두 개 들어왔다고 가정
         * threadA -> threadB 순서로 한다고 가정
         */
        tx.begin();
        Thread threadA = new Thread(() -> {
            // 데이터베이스에서 조회하여 1차 캐시에 내용 저장
            Member member = em.find(Member.class, 1L);
        });

        Thread threadB = new Thread(() -> {
            // 여기서 1차 캐시에서 조회되는지 데이터베이스에서 조회되는지 확인
            Member member = em.find(Member.class, 1L);
            member.setUsername("Test");
            em.persist(member);
        });

        // 2초 뒤에 스레드 B 시작
        threadA.start();
        Thread.sleep(2000);
        threadB.start();

        // 3초 뒤에 커밋하고 종료
        Thread.sleep(3000);
        tx.commit();
        em.close();
        emf.close();
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
