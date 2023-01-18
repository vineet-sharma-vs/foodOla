package com.group11.fooddelivery.controller;

import com.group11.fooddelivery.model.request.*;
import com.group11.fooddelivery.model.response.AdminResponse;
import com.group11.fooddelivery.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class AdminController {
    @Autowired
    AdminService adminService;

    @GetMapping(value = "/getUser", consumes = "application/json", produces = "application/json")
    public ResponseEntity<AdminResponse> getUsers(@RequestBody GetAllUserRequest getAllUserRequest) {
        AdminResponse adminResponse = adminService.getUsers(getAllUserRequest);
        return new ResponseEntity<>(adminResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/getAllRestaurants", consumes = "application/json", produces = "application/json")
    public ResponseEntity<AdminResponse> getAllRestaurants(@RequestBody GetAllRestaurantsRequest getAllRestaurantsRequest) {
        AdminResponse adminResponse = adminService.getAllRestaurants(getAllRestaurantsRequest);
        return new ResponseEntity<>(adminResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/getOrdersAdmin", consumes = "application/json", produces = "application/json")
    public ResponseEntity<AdminResponse> getOrders(@RequestBody GetOrdersRequest getOrdersRequest) {
        AdminResponse adminResponse = adminService.getOrdersAdmin(getOrdersRequest);
        return new ResponseEntity<>(adminResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/banRestaurant", consumes = "application/json", produces = "application/json")
    public ResponseEntity<AdminResponse> banRestaurants(@RequestBody BanRestaurantRequest banRestaurantRequest) {
        AdminResponse adminResponse = adminService.banRestaurant(banRestaurantRequest);
        return new ResponseEntity<>(adminResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/banUser", consumes = "application/json", produces = "application/json")
    public ResponseEntity<AdminResponse> banUser(@RequestBody BanUserRequest banUserRequest) {
        AdminResponse adminResponse = adminService.banUser(banUserRequest);
        return new ResponseEntity<>(adminResponse, HttpStatus.OK);
    }
}
