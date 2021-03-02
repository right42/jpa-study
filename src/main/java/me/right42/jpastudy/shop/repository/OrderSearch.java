package me.right42.jpastudy.shop.repository;

import lombok.Getter;
import lombok.Setter;
import me.right42.jpastudy.shop.domain.OrderStatus;

@Getter @Setter
public class OrderSearch {

    private String memberName;
    private OrderStatus orderStatus;
}
