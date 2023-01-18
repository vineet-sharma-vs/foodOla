package com.group11.fooddelivery.model.request;

import com.group11.fooddelivery.model.Item;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddItemRequest {
    private Item item;
    private long restaurantId;
}
