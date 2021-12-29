package com.jtcwp.purejpa.test.relationshipmapping;

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

@DisplayName("연관관계 매핑 테스트")
@SpringBootTest
class RelationShipMappingTest {

    @Value("${persistence.unitname}")
    private String persistenceUnitName;

    @DisplayName("ManyToOne 테스트")
    @Test
    void manyToOneTest() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Team team = new Team();
            team.setName("NKLCWDT");
            em.persist(team);

            Member member = new Member();
            member.setUsername("JungHo");
            member.setTeam(team);
            em.persist(member);

            /*
              em.flush();
              em.clear();
              위 코드를 작성하면 아래에서 1차 캐시가 아닌 데이터베이스에서 데이터를 조회한다.
             */

            // 1차 캐시에서 조회
            Member findMember = em.find(Member.class, member.getId());
            Team findTeam = findMember.getTeam();
            System.out.println("Team : " + findTeam.getName());
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    @DisplayName("양방향 연관관계 테스트")
    @Test
    void twoWaysRelationShipTest() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Team team = new Team();
            team.setName("ABC");
            em.persist(team);

            Member member = new Member();
            member.setUsername("BAEK");
            member.setTeam(team);
            em.persist(member);

            em.flush(); // 영속성 컨텍스트의 변경 내용을 DB 에 동기화
            em.clear(); // 영속성 컨텍스트를 비워줌으로써 준영속 상태가 된다.

            // 데이터베이스에서 조회
            Member findMember = em.find(Member.class, member.getId());
            List<Member> members = findMember.getTeam().getMembers();

            for (Member m : members) {
                System.out.println(m.getUsername());
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    @DisplayName("양방향 연관관계 주인이 아닌 곳에서만 값을 입력 : Team 에만 member 설정")
    @Test
    void onlySetNotOwner() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Member member = new Member();
            member.setUsername("Test");
            em.persist(member);

            // Team 에만 member 를 설정해도 데이터베이스에서 Member 테이블에 TEAM FK 가 설정되지 않는다.
            // mappedBy 는 읽기 전용이라. 등록, 수정, 삭제에서 아무런 영향을 끼치지 못한다.
            Team team = new Team();
            team.setName("ABC");
            team.getMembers().add(member);
            em.persist(team);

            em.flush(); // 영속성 컨텍스트의 변경 내용을 DB 에 동기화
            em.clear(); // 영속성 컨텍스트를 비워줌으로써 준영속 상태가 된다.

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    @DisplayName("양방향 연관관계 주인인 곳에서만 값을 입력 : Member 에 Team 을 설정")
    @Test
    void onlySetOwner() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Team team = new Team();
            team.setName("ABC");
            em.persist(team);

            // MEMBER 테이블에 TEAM FK 가 설정된다.
            Member member = new Member();
            member.setUsername("Test");
            member.setTeam(team);
            em.persist(member);

            em.flush(); // 영속성 컨텍스트의 변경 내용을 DB 에 동기화
            em.clear(); // 영속성 컨텍스트를 비워줌으로써 준영속 상태가 된다.

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    @DisplayName("양방향 연관관계에서 서로 값을 설정")
    @Test
    void setOwnerAndNotOwner() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Team team = new Team();
            team.setName("ABC");
            em.persist(team);

            Member member = new Member();
            member.setUsername("Test");
            member.setTeam(team);
            // mappedBy 가 지정된 것은 CUD Query 에 영향을 미치지 않는다.
            team.getMembers().add(member);
            em.persist(member);

            em.flush(); // 영속성 컨텍스트의 변경 내용을 DB 에 동기화
            em.clear(); // 영속성 컨텍스트를 비워줌으로써 준영속 상태가 된다.

            // Team 조회 쿼리 생성
            Team findTeam = em.find(Team.class, team.getId());
            // 지연 로딩에 의해서 실제 Member 객체를 꺼내 사용하는 시점에 조회 쿼리 생성
            List<Member> members = findTeam.getMembers();
            for (Member m : members) {
                System.out.println(m.getUsername());
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    @DisplayName("연관관계 편의 메서드 테스트")
    @Test
    void usingRelationShipConvenienceMethodTest() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Team team = new Team();
            team.setName("ABC");
            em.persist(team);

            Member member = new Member();
            member.setUsername("Test");
            em.persist(member);

            // 연관관계 편의 메서드 사용
            team.addMember(member);

            em.flush(); // 영속성 컨텍스트의 변경 내용을 DB 에 동기화
            em.clear(); // 영속성 컨텍스트를 비워줌으로써 준영속 상태가 된다.
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
