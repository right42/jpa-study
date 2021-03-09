package me.right42.jpastudy.shop.repository.order.query;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class OrderItemQueryDto {

    @JsonIgnore
    private Long orderId;

    private String itemName;

    private int count;

    private int orderPrice;

    public OrderItemQueryDto(Long orderId, String itemName, int count, int orderPrice) {
        this.orderId = orderId;
        this.itemName = itemName;
        this.count = count;
        this.orderPrice = orderPrice;
    }
}
