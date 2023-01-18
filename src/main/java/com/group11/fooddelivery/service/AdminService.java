package com.group11.fooddelivery.service;

import com.group11.fooddelivery.clients.AuthenticationClient;
import com.group11.fooddelivery.model.Order;
import com.group11.fooddelivery.model.Restaurant;
import com.group11.fooddelivery.model.User;
import com.group11.fooddelivery.model.request.*;
import com.group11.fooddelivery.model.response.AdminResponse;
import com.group11.fooddelivery.repository.OrderRepository;
import com.group11.fooddelivery.repository.RestaurantRepository;
import com.group11.fooddelivery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    AuthenticationClient authenticationClient;

    public AdminResponse getUsers(GetAllUserRequest getAllUserRequest) {
        AdminResponse adminResponse = new AdminResponse();

        //Verify session token.
        if (!authenticationClient.verifyToken(getAllUserRequest)) {
            adminResponse.setSuccess(false);
            adminResponse.setMessage("User session expired.");
            return adminResponse;
        }

        List<User> users = userRepository.findAll();
        if (users == null) {
            adminResponse.setUsers(null);
            adminResponse.setSuccess(false);
            adminResponse.setMessage("No User Exist in DataBase!!");
        } else {
            adminResponse.setUsers(users);
            adminResponse.setSuccess(true);
            adminResponse.setMessage("Users Fetched Successfully!!");
        }
        return adminResponse;
    }

    public AdminResponse getAllRestaurants(GetAllRestaurantsRequest getAllRestaurantsRequest) {
        AdminResponse adminResponse = new AdminResponse();

        //Verify session token.
        if (!authenticationClient.verifyToken(getAllRestaurantsRequest)) {
            adminResponse.setSuccess(false);
            adminResponse.setMessage("User session expired.");
            return adminResponse;
        }

        List<Restaurant> restaurants = restaurantRepository.findAll();
        if (restaurants == null) {
            adminResponse.setRestaurants(null);
            adminResponse.setSuccess(false);
            adminResponse.setMessage("No Restaurants Exist in DataBase!!");
        } else {
            adminResponse.setRestaurants(restaurants);
            adminResponse.setSuccess(true);
            adminResponse.setMessage("Restaurants Fetched Successfully!!");
        }
        return adminResponse;
    }

    public AdminResponse getOrdersAdmin(GetOrdersRequest getOrdersRequest) {
        AdminResponse adminResponse = new AdminResponse();

        //Verify session token.
        if (!authenticationClient.verifyToken(getOrdersRequest)) {
            adminResponse.setSuccess(false);
            adminResponse.setMessage("User session expired.");
            return adminResponse;
        }

        List<Order> orders = orderRepository.findAllByRestaurantId(getOrdersRequest.getRestaurantId());
        if(orders == null){
            adminResponse.setOrders(null);
            adminResponse.setSuccess(false);
            adminResponse.setMessage("No Orders Exist in DataBase!!");
        } else {
            adminResponse.setOrders(orders);
            adminResponse.setSuccess(true);
            adminResponse.setMessage("Orders Fetched Successfully!!");
        }
        return adminResponse;
    }

    public AdminResponse banRestaurant(BanRestaurantRequest banRestaurantRequest) {
        AdminResponse adminResponse = new AdminResponse();

        //Verify session token.
        if (!authenticationClient.verifyToken(banRestaurantRequest)) {
            adminResponse.setSuccess(false);
            adminResponse.setMessage("User session expired.");
            return adminResponse;
        }

        Restaurant currentRestaurant = restaurantRepository.findByRestaurantId(banRestaurantRequest.getRestaurantId());
        if (currentRestaurant == null) {
            adminResponse.setSuccess(false);
            adminResponse.setMessage("Restaurant Doesn't exist!!!");
        } else if (!currentRestaurant.isActive()) {
            adminResponse.setSuccess(false);
            adminResponse.setMessage("Restaurant is already banned!!");
        } else {

            restaurantRepository.delete(currentRestaurant);
            currentRestaurant.setActive(false);
            restaurantRepository.save(currentRestaurant);

            adminResponse.setSuccess(true);
            adminResponse.setMessage("Restaurant banned Successfully!!");
        }
        return adminResponse;
    }

    public AdminResponse banUser(BanUserRequest banUserRequest) {
        AdminResponse adminResponse = new AdminResponse();

        //Verify session token.
        if (!authenticationClient.verifyToken(banUserRequest)) {
            adminResponse.setSuccess(false);
            adminResponse.setMessage("User session expired.");
            return adminResponse;
        }

        User currentUser = userRepository.findByEmail(banUserRequest.getUserEmail());
        if (currentUser == null) {
            adminResponse.setSuccess(false);
            adminResponse.setMessage("User Doesn't exist!!!");
        } else if (!currentUser.isActive()) {
            adminResponse.setSuccess(false);
            adminResponse.setMessage("User is already banned!!");
        } else {
            userRepository.delete(currentUser);
            currentUser.setActive(false);
            userRepository.save(currentUser);

            adminResponse.setSuccess(true);
            adminResponse.setMessage("User banned Successfully!!");
        }
        return adminResponse;
    }
}
