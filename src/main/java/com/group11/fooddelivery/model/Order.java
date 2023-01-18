package com.group11.fooddelivery.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "OrderInfo")
@Getter
@Setter
public class Order {
    private String orderId;
    private long restaurantId;
    @Id
    private long itemId;
    private int quantity;
}
