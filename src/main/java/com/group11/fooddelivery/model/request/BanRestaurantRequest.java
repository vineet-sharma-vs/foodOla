package com.group11.fooddelivery.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BanRestaurantRequest extends Request{
    private Long restaurantId;
}
