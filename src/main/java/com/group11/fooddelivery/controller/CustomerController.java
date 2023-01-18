package com.group11.fooddelivery.controller;

import com.group11.fooddelivery.model.request.LatLongRequest;
import com.group11.fooddelivery.model.request.MakePaymentRequest;
import com.group11.fooddelivery.model.request.PlaceOrderRequest;
import com.group11.fooddelivery.model.request.TrackRequest;
import com.group11.fooddelivery.model.response.LatLongResponse;
import com.group11.fooddelivery.model.response.MakePaymentResponse;
import com.group11.fooddelivery.model.response.PlaceOrderResponse;
import com.group11.fooddelivery.model.response.TrackResponse;
import com.group11.fooddelivery.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @PostMapping(value = "/placeOrder", consumes = "application/json", produces = "application/json")
    public ResponseEntity<PlaceOrderResponse> placeOrder(@RequestBody PlaceOrderRequest placeOrderRequest) {
        PlaceOrderResponse placeOrderResponse = customerService.placeOrder(placeOrderRequest);
        return new ResponseEntity<>(placeOrderResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/getFeed", consumes = "application/json", produces = "application/json")
    public ResponseEntity<LatLongResponse> getRestaurants(@RequestBody LatLongRequest latLongRequest) {
        LatLongResponse latLongResponse = customerService.getFeed(latLongRequest);
        return new ResponseEntity<>(latLongResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/makePayment", consumes = "application/json", produces = "application/json")
    public ResponseEntity<MakePaymentResponse> makePayment(@RequestBody MakePaymentRequest makePaymentRequest) {
        MakePaymentResponse makePaymentResponse = customerService.makePayment(makePaymentRequest);
        return new ResponseEntity<>(makePaymentResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/trackOrder", produces = "application/json")
    public ResponseEntity<TrackResponse> trackOrder(@RequestBody TrackRequest trackRequest) {
        TrackResponse trackResponse = customerService.track(trackRequest);
        return new ResponseEntity<>(trackResponse, HttpStatus.OK);
    }
}
