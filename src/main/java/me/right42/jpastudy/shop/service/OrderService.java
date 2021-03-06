package me.right42.jpastudy.shop.service;

import lombok.RequiredArgsConstructor;
import me.right42.jpastudy.shop.domain.Delivery;
import me.right42.jpastudy.shop.domain.Member;
import me.right42.jpastudy.shop.domain.Order;
import me.right42.jpastudy.shop.domain.OrderItem;
import me.right42.jpastudy.shop.domain.item.Item;
import me.right42.jpastudy.shop.repository.ItemRepository;
import me.right42.jpastudy.shop.repository.MemberRepository;
import me.right42.jpastudy.shop.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;

    private final MemberRepository memberRepository;

    private final ItemRepository itemRepository;

    /*
        주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        // 엔티티 조회
        Member findMember = memberRepository.findOne(memberId);
        Item findItem = itemRepository.findOne(itemId);

        Delivery delivery = new Delivery();
        delivery.setAddress(findMember.getAddress());

        OrderItem orderItem = OrderItem.createOrderItem(findItem, findItem.getPrice(), count);

        Order order = Order.createOrder(findMember, delivery, orderItem);

        orderRepository.save(order);
        return order.getId();
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        // 조회
        Order order = orderRepository.findOne(orderId);
        order.orderCancel();
    }

}
