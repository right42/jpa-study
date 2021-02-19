package me.right42.jpastudy.shop.repository;

import lombok.RequiredArgsConstructor;
import me.right42.jpastudy.shop.domain.Order;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager entityManager;

    public void save(Order order) {
        entityManager.persist(order);
    }

    public Order findOne(Long orderId) {
        return entityManager.find(Order.class, orderId);
    }

}
