package com.group11.fooddelivery.service;

import com.group11.fooddelivery.clients.AuthenticationClient;
import com.group11.fooddelivery.clients.CustomerClient;
import com.group11.fooddelivery.configure.Constants;
import com.group11.fooddelivery.model.Order;
import com.group11.fooddelivery.model.OrdersByUser;
import com.group11.fooddelivery.model.Restaurant;
import com.group11.fooddelivery.model.request.LatLongRequest;
import com.group11.fooddelivery.model.request.MakePaymentRequest;
import com.group11.fooddelivery.model.request.PlaceOrderRequest;
import com.group11.fooddelivery.model.request.TrackRequest;
import com.group11.fooddelivery.model.response.LatLongResponse;
import com.group11.fooddelivery.model.response.MakePaymentResponse;
import com.group11.fooddelivery.model.response.PlaceOrderResponse;
import com.group11.fooddelivery.model.response.TrackResponse;
import com.group11.fooddelivery.repository.OrdersByUsersRepository;
import com.group11.fooddelivery.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.group11.fooddelivery.configure.Constants.*;

@Service
public class CustomerService {
    @Autowired
    OrdersByUsersRepository ordersByUsersRepository;
    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    CustomerClient customerClient;
    @Autowired
    AuthenticationClient authenticationClient;

    public PlaceOrderResponse placeOrder(PlaceOrderRequest placeOrderRequest) {
        PlaceOrderResponse placeOrderResponse = new PlaceOrderResponse();

        //Verify session token.
//        if (!authenticationClient.verifyToken(placeOrderRequest)) {
//            placeOrderResponse.setSuccess(false);
//            placeOrderResponse.setMessage("User session expired.");
//            return placeOrderResponse;
//        }

        Restaurant restaurant = restaurantRepository.findById(placeOrderRequest.getRestaurantId()).orElse(null);
        assert restaurant != null;
        List<String> acceptableMethods = new ArrayList<>();
        acceptableMethods.add(Constants.paymentStatusCod);
        acceptableMethods.add(Constants.paymentStatusUPI);
        acceptableMethods.add(Constants.paymentStatusCards);
        placeOrderResponse.setAcceptableMethods(acceptableMethods);
        placeOrderResponse.setSelectedItems(customerClient.buildSelectedItems(placeOrderRequest.getSelectedItems()));
        placeOrderResponse.setTotalPrice(customerClient.calculateTotalPrice(placeOrderRequest.getSelectedItems()));
        placeOrderResponse.setSuccess(true);
        placeOrderResponse.setMessage("Order placed. Please find the summary!");
        return placeOrderResponse;
    }

    public MakePaymentResponse makePayment(MakePaymentRequest makePaymentRequest) {
        MakePaymentResponse makePaymentResponse = new MakePaymentResponse();

        Restaurant restaurant = restaurantRepository.findById(makePaymentRequest.getRestaurantId()).orElse(null);
        assert restaurant != null;

        //Generate random orderId
        String orderId = UUID.randomUUID().toString();

        //Populate ordersByUser table
        OrdersByUser ordersByUser = new OrdersByUser();
        ordersByUser.setOrderId(orderId);
        ordersByUser.setEmail(makePaymentRequest.getEmail());
        ordersByUser.setStatus(Constants.orderPlaced);
        ordersByUsersRepository.save(ordersByUser);

        //Populate orders_info table
        Order order = new Order();
        order.setOrderId(orderId);
        order.setRestaurantId(makePaymentRequest.getRestaurantId());
        customerClient.populateOrderInfo(order, makePaymentRequest.getSelectedItems());

        makePaymentResponse.setOrderId(orderId);
        makePaymentResponse.setRestaurantName(restaurant.getName());
        makePaymentResponse.setSelectedItems(customerClient.buildSelectedItems(makePaymentRequest.getSelectedItems()));
        makePaymentResponse.setSuccess(true);
        makePaymentResponse.setMessage("Payment Successful");
        return makePaymentResponse;
    }

    public LatLongResponse getFeed(LatLongRequest latLongRequest) {
        LatLongResponse latLongResponse = new LatLongResponse();

        //Verify session token.
//        if (!authenticationClient.verifyToken(latLongRequest)) {
//            latLongResponse.setSuccess(false);
//            latLongResponse.setMessage("User session expired.");
//            return latLongResponse;
//        }

        List<Restaurant> restaurantByName = new ArrayList<>();
        List<Restaurant> restaurantList = restaurantRepository.findAll();
        double lat2 = latLongRequest.getLatitude();
        double lon2 = latLongRequest.getLongitude();

        for (Restaurant restaurant : restaurantList) {
            double lat1 = restaurant.getLatitude();
            double lon1 = restaurant.getLongitude();

            if (customerClient.distance(lat1, lon1, lat2, lon2, "K") <= 5) {
                restaurantByName.add(restaurant);
            }
        }
        latLongResponse.setListOfRestaurant(restaurantByName);
        latLongResponse.setSuccess(true);
        latLongResponse.setMessage("Please find your nearest restaurants!");
        return latLongResponse;
    }

    public TrackResponse track(TrackRequest trackRequest) {
        TrackResponse trackResponse = new TrackResponse();

        //Verify session token.
        if (!authenticationClient.verifyToken(trackRequest)) {
            trackResponse.setSuccess(false);
            trackResponse.setMessage("User session expired.");
            return trackResponse;
        }

        OrdersByUser ordersByUser = ordersByUsersRepository.findByOrderId(trackRequest.getOrderId());

        if (ordersByUser == null) {
            trackResponse.setSuccess(false);
            trackResponse.setMessage("No order is placed !");
        } else if (ordersByUser.getStatus().equals(orderPlaced)) {
            trackResponse.setSuccess(true);
            trackResponse.setMessage("Ordered is placed. We will prepare it soon.");
        } else if (ordersByUser.getStatus().equals(orderPreparing)) {
            trackResponse.setSuccess(true);
            trackResponse.setMessage("The order is getting prepared!");
        } else if(ordersByUser.getStatus().equals(orderCompleted))  {
            trackResponse.setSuccess(true);
            trackResponse.setMessage("The order is completed! It will be delivered soon.");
        }
        else if (ordersByUser.getStatus().equals(orderDelivered)) {
            trackResponse.setSuccess(true);
            trackResponse.setMessage("Ordered is Delivered");
        } else {
            trackResponse.setSuccess(false);
            trackResponse.setMessage("Something went wrong");
        }
        return trackResponse;
    }
}
