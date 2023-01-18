package com.group11.fooddelivery.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;


@Entity
@Table(name = "OrdersByUser")
@Getter
@Setter
public class OrdersByUser {
    @Id
    private String orderId;
    private String email;
    private String status; //Status will have values like placed, in transit or delivered.
}
