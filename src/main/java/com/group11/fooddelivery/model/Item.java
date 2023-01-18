package com.group11.fooddelivery.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "ItemInfo")
@Getter
@Setter
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long itemId;
    private String name;
    private int price;
    @JsonProperty("isVeg")
    private boolean isVeg;
    private String description;
    private String url;
}