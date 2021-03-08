package me.right42.jpastudy.shop.repository;

import lombok.RequiredArgsConstructor;
import me.right42.jpastudy.shop.domain.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

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

    public List<Order> findAll(OrderSearch orderSearch) {
        String qlString = "select o from Order o join o.member m";
        return entityManager.createQuery(qlString, Order.class)
                .setMaxResults(1000)
                .getResultList()
        ;
    }


    public List<Order> findAllWithMemberDelivery() {
        return entityManager.createQuery(
                "select o from Order o" +
                        " join fetch o.member m " +
                        " join fetch o.delivery d", Order.class
        ).getResultList();
    }


    public List<Order> findAllWithItem() {
        return entityManager.createQuery(
                "select distinct o from Order o" +
                        " join fetch o.member m" +
                        " join fetch o.delivery d" +
                        " join fetch o.orderItems oi " +
                        " join fetch oi.item ", Order.class
        ).getResultList();
    }

    public List<Order> findAllWithMemberDelivery(int offset, int limit) {
        return entityManager.createQuery(
                "select o from Order o" +
                        " join fetch o.member m" +
                        " join fetch o.delivery d", Order.class
            )
            .setFirstResult(offset)
            .setMaxResults(limit)
            .getResultList();
    }
}
