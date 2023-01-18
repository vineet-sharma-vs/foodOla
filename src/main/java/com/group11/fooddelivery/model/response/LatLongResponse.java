package com.group11.fooddelivery.model.response;

import com.group11.fooddelivery.model.Restaurant;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LatLongResponse extends Response{

    private List<Restaurant> ListOfRestaurant;

}
