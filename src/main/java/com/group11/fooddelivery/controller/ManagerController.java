package com.group11.fooddelivery.controller;

import com.group11.fooddelivery.model.Item;
import com.group11.fooddelivery.model.Restaurant;
import com.group11.fooddelivery.model.request.AddItemRequest;
import com.group11.fooddelivery.model.request.GetItemRequest;
import com.group11.fooddelivery.model.request.GetOrdersRequest;
import com.group11.fooddelivery.model.request.UpdateStatusRequest;
import com.group11.fooddelivery.model.response.*;
import com.group11.fooddelivery.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class ManagerController {

    @Autowired
    ManagerService managerService;

    @PostMapping(value = "/updateStatus", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UpdateStatusManagerResponse> updateStatus(@RequestBody UpdateStatusRequest updateStatusRequest) {

        try {
            UpdateStatusManagerResponse updateStatusManagerResponse = managerService.updateStatus(updateStatusRequest);
            if (updateStatusManagerResponse.isSuccess()) {
                return new ResponseEntity<>(updateStatusManagerResponse, HttpStatus.OK);

            } else {
                return new ResponseEntity<>(updateStatusManagerResponse, HttpStatus.BAD_REQUEST);

            }
        } catch (Exception e) {
            UpdateStatusManagerResponse updateStatusManagerResponse = null;
            return new ResponseEntity<>(updateStatusManagerResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/getOrdersManager", consumes = "application/json", produces = "application/json")
    public ResponseEntity<GetOrdersResponse> getOrders(@RequestBody GetOrdersRequest getOrdersRequest) {

        try {
            GetOrdersResponse getOrdersResponse = managerService.getOrdersManager(getOrdersRequest);
            if (getOrdersResponse.isSuccess()) {
                return new ResponseEntity<>(getOrdersResponse, HttpStatus.OK);

            } else {
                return new ResponseEntity<>(getOrdersResponse, HttpStatus.BAD_REQUEST);

            }
        } catch (Exception e) {
            GetOrdersResponse getOrdersResponse = null;
            return new ResponseEntity<>(getOrdersResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/updateRestaurantDetails", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UpdateRestaurantDetails> updateRestaurantDetails(@RequestBody Restaurant restaurant) {

        try {
            UpdateRestaurantDetails updateRestaurantDetails = managerService.updateRestaurantDetails(restaurant);
            if (updateRestaurantDetails.isSuccess()) {
                return new ResponseEntity<>(updateRestaurantDetails, HttpStatus.OK);

            } else {
                return new ResponseEntity<>(updateRestaurantDetails, HttpStatus.BAD_REQUEST);

            }
        } catch (Exception e) {
            UpdateRestaurantDetails updateRestaurantDetails = null;
            return new ResponseEntity<>(updateRestaurantDetails, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/addItem", consumes = "application/json", produces = "application/json")
    public ResponseEntity<AddItemResponse> AddItem(@RequestBody AddItemRequest addItemRequest) {
        AddItemResponse addItemResponse = managerService.addItem(addItemRequest);
        return new ResponseEntity<>(addItemResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/editItem", consumes = "application/json", produces = "application/json")
    public ResponseEntity<EditItemResponse> EditItem(@RequestBody Item item) {
        EditItemResponse editItemResponse = managerService.editItem(item);
        return new ResponseEntity<>(editItemResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/getItems", consumes = "application/json", produces = "application/json")
    public ResponseEntity<GetItemResponse> getItems(@RequestBody GetItemRequest getItemRequest) {
        GetItemResponse getItemResponse = managerService.getAllItems(getItemRequest.getRestaurantId());
        return new ResponseEntity<>(getItemResponse, HttpStatus.OK);
    }
}
