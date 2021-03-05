package me.right42.jpastudy.shop;

import lombok.RequiredArgsConstructor;
import me.right42.jpastudy.shop.domain.*;
import me.right42.jpastudy.shop.domain.item.Book;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager entityManager;

        public void dbInit1() {
            Member member = createMember("user1", "서울", "1", "1111");
            entityManager.persist(member);

            Book book = createBook("JPA1", 10000, 100);
            entityManager.persist(book);

            Book book2 = createBook("JPA2", 20000, 100);
            entityManager.persist(book2);

            OrderItem orderItem = OrderItem.createOrderItem(book, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Delivery delivery = createDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItem, orderItem2);
            entityManager.persist(order);
        }

        public void dbInit2() {
            Member member = createMember("user2", "경기", "123", "1234");
            entityManager.persist(member);

            Book book = createBook("SPRING1", 20000, 100);
            entityManager.persist(book);

            Book book2 = createBook("SPRING2", 40000, 100);
            entityManager.persist(book2);

            OrderItem orderItem = OrderItem.createOrderItem(book, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Delivery delivery = createDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItem, orderItem2);
            entityManager.persist(order);
        }

        private Delivery createDelivery(Member member) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }

        private Member createMember(String name, String city, String street, String zipcode) {
            Member member = new Member();
            member.setName(name);
            member.setAddress(new Address(city, street, zipcode));
            return member;
        }

        private Book createBook(String name, int price, int stockQuantity) {
            Book book = new Book();
            book.setName(name);
            book.setPrice(price);
            book.setStockQuantity(stockQuantity);
            return book;
        }
    }
}

