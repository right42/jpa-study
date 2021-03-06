package me.right42.jpastudy.shop.api;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import me.right42.jpastudy.shop.domain.Address;
import me.right42.jpastudy.shop.domain.Order;
import me.right42.jpastudy.shop.domain.OrderStatus;
import me.right42.jpastudy.shop.repository.OrderRepository;
import me.right42.jpastudy.shop.repository.OrderSearch;
import me.right42.jpastudy.shop.repository.order.simplequery.OrderSimpleQueryDto;
import me.right42.jpastudy.shop.repository.order.simplequery.OrderSimpleQueryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 *   xToOne
 *   Order
 *   Order -> Member
 *   Order -> Delivery
 **/
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        return orderRepository.findAll(new OrderSearch());
    }

    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2() {
        List<Order> orders = orderRepository.findAll(new OrderSearch());

        return orders.stream()
                    .map(SimpleOrderDto::new)
                    .collect(toList());
    }

    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> ordersV3() {
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        return orders.stream()
                    .map(SimpleOrderDto::new)
                    .collect(toList());
    }

    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4() {
        return orderSimpleQueryRepository.findOrderDtos();
    }

    @Data
    static class SimpleOrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order) {
            this.orderId = order.getId();
            this.name = order.getMember().getName();
            this.orderDate = order.getOrderDate();
            this.orderStatus = order.getOrderStatus();
            this.address = order.getDelivery().getAddress();
        }
    }
}
