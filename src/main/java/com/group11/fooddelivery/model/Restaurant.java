package com.group11.fooddelivery.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "RestaurantInfo")
@Getter
@Setter
public class Restaurant {
    @Id
    private long restaurantId;
    private String name;
    private String address;
    private int rating;
    private boolean isActive = true;
    private double latitude;
    private double longitude;
    private String description;
    private String imageUrl;
}
