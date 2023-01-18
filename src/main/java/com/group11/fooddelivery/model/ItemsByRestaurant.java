package com.group11.fooddelivery.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "ItemsByRestaurant")
@Getter
@Setter
public class ItemsByRestaurant {
    @Id
    private long itemId;
    private long restaurantId;
}
