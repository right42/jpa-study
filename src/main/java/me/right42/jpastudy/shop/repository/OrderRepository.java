package me.right42.jpastudy.shop.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.right42.jpastudy.shop.domain.Order;
import me.right42.jpastudy.shop.domain.OrderStatus;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static me.right42.jpastudy.shop.domain.QMember.member;
import static me.right42.jpastudy.shop.domain.QOrder.order;

@Repository
public class OrderRepository {

    private final EntityManager entityManager;
    private final JPAQueryFactory query;

    public OrderRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.query = new JPAQueryFactory(entityManager);
    }

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

    public List<Order> findAllQueryDsl(OrderSearch orderSearch) {

        return query
                .select(order)
                .from(order)
                .join(order.member, member)
                .where(
                        statusEq(orderSearch.getOrderStatus()),
                        nameLike(orderSearch.getMemberName())
                )
                .limit(1000)
                .fetch();
    }

    private BooleanExpression nameLike(String memberName) {
        if(!StringUtils.hasText(memberName)) {
            return null;
        }
        return member.name.like(memberName);
    }

    private BooleanExpression statusEq(OrderStatus orderStatus) {
        if(orderStatus == null) {
            return null;
        }
        return order.orderStatus.eq(orderStatus);
    }
}
