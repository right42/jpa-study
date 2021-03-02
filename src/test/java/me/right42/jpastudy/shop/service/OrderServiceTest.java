package me.right42.jpastudy.shop.service;

import me.right42.jpastudy.shop.domain.Address;
import me.right42.jpastudy.shop.domain.Member;
import me.right42.jpastudy.shop.domain.Order;
import me.right42.jpastudy.shop.domain.OrderStatus;
import me.right42.jpastudy.shop.domain.item.Book;
import me.right42.jpastudy.shop.exception.NotEnoughStockException;
import me.right42.jpastudy.shop.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;


    @Test
    void 상품주문() {
        Member member = createMember();

        Book book = createBook("JPA", 10000, 10);

        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        Order getOrder = orderRepository.findOne(orderId);
        assertThat(getOrder.getOrderStatus()).isEqualTo(OrderStatus.ORDER);
        assertThat(getOrder.getOrderItems().size()).isEqualTo(1);
        assertThat(getOrder.getTotalPrice()).isEqualTo(10000 * orderCount);
        assertThat(book.getStockQuantity()).isEqualTo(8);
    }

    @Test
    void 상품주문_재고수량초과() {
        Member member = createMember();
        Book book = createBook("JPA", 10000, 10);

        int orderCount = 11;

        assertThrows(NotEnoughStockException.class, () -> orderService.order(member.getId(), book.getId(), orderCount));
    }


    @Test
    void 주문취소() {
        // given
        Member member = createMember();
        Book book = createBook("JPA", 10000, 10);

        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //when
        orderService.cancelOrder(orderId);

        // then
        Order findOrder = orderRepository.findOne(orderId);
        assertThat(findOrder.getOrderStatus()).isEqualTo(OrderStatus.CANCEL);
        assertThat(book.getStockQuantity()).isEqualTo(10);

    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        entityManager.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "경기", "123"));
        ;
        entityManager.persist(member);
        return member;
    }
}