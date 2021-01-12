package me.right42.jpastudy;

import me.right42.jpastudy.shop.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootTest
class JpaStudyApplicationTests {

    @PersistenceContext
    EntityManager entityManager;

    @Test
    @Transactional
    void hibernateCollection() {
        Member member = new Member();

        System.out.println(member.getOrders().getClass());
        entityManager.persist(member);
        System.out.println(member.getOrders().getClass());

    }

}