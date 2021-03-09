package me.right42.jpastudy.shop.repository.order.query;

import lombok.Data;
import me.right42.jpastudy.shop.domain.Address;
import me.right42.jpastudy.shop.domain.OrderStatus;

import java.time.LocalDateTime;

@Data
public class OrderFlatDto {

    private Long orderId;

    private String name;

    private LocalDateTime orderDate;

    private OrderStatus orderStatus;

    private Address address;

    private String itemName;

    private int count;

    private int orderPrice;

    public OrderFlatDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus, Address address, String itemName, int count, int orderPrice) {
        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
        this.itemName = itemName;
        this.count = count;
        this.orderPrice = orderPrice;
    }
}
