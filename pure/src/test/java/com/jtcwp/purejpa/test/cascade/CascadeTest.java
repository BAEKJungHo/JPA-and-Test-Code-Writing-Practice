package com.jtcwp.purejpa.test.cascade;

import com.jtcwp.purejpa.domain.Child;
import com.jtcwp.purejpa.domain.Member;
import com.jtcwp.purejpa.domain.Parent;
import com.jtcwp.purejpa.domain.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

@SpringBootTest
class CascadeTest {

    @Value("${persistence.unitname}")
    private String persistenceUnitName;

    @Test
    void cascadeTest() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Child child1 = new Child();
            Child child2 = new Child();

            Parent parent = new Parent();
            parent.addChild(child1);
            parent.addChild(child2);

            // not using cascade
//            em.persist(parent);
//            em.persist(child1);
//            em.persist(child2);

            // using cascade : cascade = CascadeType.ALL
            em.persist(parent);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    @Test
    void orphanTest() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Child child1 = new Child();
            Child child2 = new Child();

            Parent parent = new Parent();
            parent.addChild(child1);
            parent.addChild(child2);

            em.persist(parent);

            em.flush();
            em.close();

            Parent findParent = em.find(Parent.class, parent.getId());
            findParent.getChildList().remove(0); // 자식 엔티티를 컬렉션에서 제거

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
