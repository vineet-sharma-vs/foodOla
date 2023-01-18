package com.group11.fooddelivery.model.response;

import com.group11.fooddelivery.model.Order;
import com.group11.fooddelivery.model.Restaurant;
import com.group11.fooddelivery.model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AdminResponse extends Response{
   private List<User> users;
   private List<Restaurant> restaurants;
   private List<Order> orders;
}
