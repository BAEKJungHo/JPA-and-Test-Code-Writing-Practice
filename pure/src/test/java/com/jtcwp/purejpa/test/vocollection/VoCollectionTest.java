package com.jtcwp.purejpa.test.vocollection;

import com.jtcwp.purejpa.domain.Address;
import com.jtcwp.purejpa.domain.AddressEntity;
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
class VoCollectionTest {

    @Value("${persistence.unitname}")
    private String persistenceUnitName;

    @Test
    void voCollectionTest() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Member member = new Member();
            member.setUsername("mem1");
            member.setHomeAddress(new Address("homeCity", "street", "1234"));

            // insert 세 번
            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("피자");
            member.getFavoriteFoods().add("족발");

            // insert 두 번
//            member.getAddressHistory().add(new Address("old1", "street", "12341"));
//            member.getAddressHistory().add(new Address("old2", "street", "12342"));

//            member.getAddressHistory().add(new AddressEntity("old1", "street", "12341"));
//            member.getAddressHistory().add(new AddressEntity("old2", "street", "12342"));

            em.persist(member);

            em.flush();
            em.clear();


            // Member 만 조회 : 값 타입 컬렉션들은 지연 로딩으로 조회
            Member findMember = em.find(Member.class, member.getId());

            // 지연 로딩으로 조회
//            List<Address> addressHistory = findMember.getAddressHistory();
//            for(Address address : addressHistory) {
//                System.out.println("address = " + address.getCity());
//            }

            // 값 타입 컬렉션 업데이트

            // 치킨 -> 한식
            findMember.getFavoriteFoods().remove("치킨");
            findMember.getFavoriteFoods().add("한식");

            // old1 -> newCity1
            // delete from 주소컬렉션 where member_id = ?
            // 즉, member_id 기준으로 전체를 삭제한다.
//            findMember.getAddressHistory().remove(new Address("old1", "street", "12341"));

            // 여기서 newCity1 을 하나만 등록했지만, 위에서 old2 가 아직 컬렉션에 존재하기 때문에
            // insert 쿼리가 두 번 나간다.
//            findMember.getAddressHistory().add(new Address("newCity1", "street", "12341"));

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
