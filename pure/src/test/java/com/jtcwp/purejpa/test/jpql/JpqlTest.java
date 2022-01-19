package com.jtcwp.purejpa.test.jpql;

import com.jtcwp.purejpa.domain.Member;
import com.jtcwp.purejpa.domainforjpql.MemberDTO;
import com.jtcwp.purejpa.domainforjpql.MemberJpql;
import com.jtcwp.purejpa.domainforjpql.TeamJpql;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@SpringBootTest
class JpqlTest {

    @Value("${persistence.unitname}")
    private String persistenceUnitName;

    @DisplayName("TypeQuery 와 Query 테스트")
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

    @DisplayName("조회 결과를 DTO 로 받기")
    @Test
    void selectAsDTOTest() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            MemberJpql member = new MemberJpql();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            em.flush();
            em.clear();

            List<MemberDTO> result = em.createQuery(
                    "select new com.jtcwp.purejpa.domainforjpql.MemberDTO(m.username, m.age) from MemberJpql m", MemberDTO.class
                    ).getResultList();

            MemberDTO memberDTO = result.get(0);
            System.out.println(memberDTO.getUsername());
            System.out.println(memberDTO.getAge());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    @DisplayName("페이징 테스트")
    @Test
    void pagingTest() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {

            for (int i = 0; i < 100; i++) {
                MemberJpql member = new MemberJpql();
                member.setUsername("member" + i);
                member.setAge(i);
                em.persist(member);
            }

            em.flush();
            em.clear();

            List<MemberJpql> result = em.createQuery("select m from MemberJpql m order by m.age desc", MemberJpql.class)
                    .setFirstResult(1)
                    .setMaxResults(10)
                    .getResultList();

            System.out.println("result = " + result.size());
            for (MemberJpql memberJpql : result) {
                System.out.println(memberJpql);
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    @DisplayName("조인 테스트")
    @Test
    void joinTest() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {

            TeamJpql teamJpql = new TeamJpql();
            teamJpql.setName("teamA");
            em.persist(teamJpql);

            MemberJpql member = new MemberJpql();
            member.setUsername("member1");
            member.setAge(10);

            member.setTeam(teamJpql);
            em.persist(member);

            em.flush();
            em.clear();

            String query = "select m from MemberJpql m join m.team t";
            List<MemberJpql> result = em.createQuery(query, MemberJpql.class)
                    .getResultList();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    @DisplayName("단일 값 연관 경로 테스트 : 묵시적 내부조인 발생")
    @Test
    void pathSearchTest1() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            MemberJpql member1 = new MemberJpql();
            member1.setUsername("member1");
            em.persist(member1);

            MemberJpql member2 = new MemberJpql();
            member2.setUsername("member2");
            em.persist(member2);

            em.flush();
            em.clear();

            // 해당 쿼리를 돌리면 묵시적 내부 조인이 발생
            // inner join Team ~~
            String query = "select m.team from MemberJpql m";

            List<MemberJpql> result = em.createQuery(query, MemberJpql.class)
                    .getResultList();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    @DisplayName("컬렉션 값 연관 경로 테스트 : 묵시적 내부조인 발생")
    @Test
    void pathSearchTest2() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            MemberJpql member1 = new MemberJpql();
            member1.setUsername("member1");
            em.persist(member1);

            MemberJpql member2 = new MemberJpql();
            member2.setUsername("member2");
            em.persist(member2);

            em.flush();
            em.clear();

            // 해당 쿼리를 돌리면 묵시적 내부 조인이 발생
            // inner join Member ~~
            // t.members 에서 더 이상 탐색 불가능
            String query = "select t.members from TeamJpql t";

            Collection result = em.createQuery(query, Collection.class)
                            .getResultList();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    @DisplayName("페치 조인 테스트")
    @Test
    void fetchJoinTest() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            TeamJpql teamA = new TeamJpql();
            teamA.setName("팀A");
            em.persist(teamA);

            TeamJpql teamB = new TeamJpql();
            teamB.setName("팀B");
            em.persist(teamB);

            TeamJpql teamC = new TeamJpql();
            teamC.setName("팀C");
            em.persist(teamC);

            MemberJpql member1 = new MemberJpql();
            member1.setUsername("회원1");
            member1.setTeam(teamA);
            em.persist(member1);

            MemberJpql member2 = new MemberJpql();
            member2.setUsername("회원2");
            member2.setTeam(teamB);
            em.persist(member2);

            MemberJpql member3 = new MemberJpql();
            member3.setUsername("회원3");
            member3.setTeam(teamC);
            em.persist(member3);

            em.flush();
            em.clear();

            String query = "select m from Member m join fetch m.team";

            List<MemberJpql> result = em.createQuery(query, MemberJpql.class)
                    .getResultList();

            for (MemberJpql memberJpql : result) {
                System.out.println(memberJpql.getUsername() + " --" + memberJpql.getTeam().getName());
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    @DisplayName("네임드 쿼리 테스트")
    @Test
    void namedQueryTest() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            TeamJpql teamA = new TeamJpql();
            teamA.setName("팀A");
            em.persist(teamA);

            TeamJpql teamB = new TeamJpql();
            teamB.setName("팀B");
            em.persist(teamB);

            TeamJpql teamC = new TeamJpql();
            teamC.setName("팀C");
            em.persist(teamC);

            MemberJpql member1 = new MemberJpql();
            member1.setUsername("회원1");
            member1.setTeam(teamA);
            em.persist(member1);

            MemberJpql member2 = new MemberJpql();
            member2.setUsername("회원2");
            member2.setTeam(teamB);
            em.persist(member2);

            MemberJpql member3 = new MemberJpql();
            member3.setUsername("회원3");
            member3.setTeam(teamC);
            em.persist(member3);

            em.flush();
            em.clear();

            List<MemberJpql> result = em.createNamedQuery("Member.findByUsername", MemberJpql.class)
                    .setParameter("username", "회원1")
                    .getResultList();

            for (MemberJpql memberJpql : result) {
                System.out.println(memberJpql.getUsername() + " --" + memberJpql.getTeam().getName());
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

}
