package com.group11.fooddelivery.service;


import com.group11.fooddelivery.model.*;
import com.group11.fooddelivery.model.request.AddItemRequest;
import com.group11.fooddelivery.model.request.GetOrdersRequest;
import com.group11.fooddelivery.model.request.UpdateStatusRequest;
import com.group11.fooddelivery.model.response.*;
import com.group11.fooddelivery.model.submodel.ItemDetails;
import com.group11.fooddelivery.model.submodel.OrderDetails;
import com.group11.fooddelivery.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ManagerService {

    @Autowired
    OrdersByUsersRepository ordersByUsersRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ItemsByRestaurantRepository itemsByRestaurantRepository;

    public UpdateStatusManagerResponse updateStatus(UpdateStatusRequest updateStatusRequest) {

        try {
            UpdateStatusManagerResponse updateStatusManagerResponse = new UpdateStatusManagerResponse();
            OrdersByUser ordersByUsers = ordersByUsersRepository.findByOrderId(updateStatusRequest.getOrderId());

            if (ordersByUsers == null) {
                updateStatusManagerResponse.setSuccess(false);
                updateStatusManagerResponse.setMessage("orderId is invalid");
                return updateStatusManagerResponse;
            }

            ordersByUsers.setStatus(updateStatusRequest.getStatus());
            ordersByUsersRepository.save(ordersByUsers);

            updateStatusManagerResponse.setSuccess(true);
            updateStatusManagerResponse.setMessage("status is updated");
            return updateStatusManagerResponse;

        } catch (Exception e) {
            UpdateStatusManagerResponse updateStatusManagerResponse = new UpdateStatusManagerResponse();
            updateStatusManagerResponse.setSuccess(false);
            updateStatusManagerResponse.setMessage("Something went wrong");
            return updateStatusManagerResponse;

        }
    }

    //@todo: This can be moved into common service. since admin and manager, both can use it to get orders for a given restaurant.
    public GetOrdersResponse getOrdersManager(GetOrdersRequest getOrdersRequest) {

        try {
            GetOrdersResponse getOrdersResponse = new GetOrdersResponse();
            List<Order> orders = orderRepository.findAllByRestaurantId(getOrdersRequest.getRestaurantId());

            if (orders == null || orders.size() == 0) {
                getOrdersResponse.setSuccess(false);
                getOrdersResponse.setMessage("No active order is found");
                return getOrdersResponse;
            }

            Set<String> uniqueOrderIds = new LinkedHashSet<>();
            for (Order order : orders) uniqueOrderIds.add(order.getOrderId());
            List<OrderDetails> orderDetailsList = new ArrayList<>();

            for (String orderId : uniqueOrderIds) {
                OrderDetails orderDetails = new OrderDetails();
                orderDetails.setOrderId(orderId);
                OrdersByUser ordersByUser = ordersByUsersRepository.findByOrderId(orderId);
                orderDetails.setStatus(ordersByUser.getStatus());
                List<ItemDetails> itemDetailsList = new ArrayList<>();
                for (Order order : orders) {
                    if (Objects.equals(order.getOrderId(), orderId)) {
                        ItemDetails itemDetails = new ItemDetails();
                        Item item = itemRepository.findById(order.getItemId()).orElse(null);
                        assert item != null;
                        itemDetails.setName(item.getName());
                        itemDetails.setItemId(order.getItemId());
                        itemDetails.setQuantity(order.getQuantity());
                        itemDetailsList.add(itemDetails);
                    }
                }
                orderDetails.setItemDetailsList(itemDetailsList);
                orderDetailsList.add(orderDetails);
            }
            getOrdersResponse.setOrderDetailsList(orderDetailsList);
            getOrdersResponse.setRestaurantId(getOrdersRequest.getRestaurantId());
            getOrdersResponse.setSuccess(true);
            getOrdersResponse.setMessage("Order details for the given restaurant.");
            return getOrdersResponse;
        } catch (Exception e) {
            GetOrdersResponse getOrdersResponse = new GetOrdersResponse();
            getOrdersResponse.setSuccess(false);
            getOrdersResponse.setMessage("Something went wrong");
            return getOrdersResponse;

        }
    }

    public UpdateRestaurantDetails updateRestaurantDetails(Restaurant restaurant) {
        try {
            UpdateRestaurantDetails updateRestaurantDetails = new UpdateRestaurantDetails();
            Restaurant newRestaurant = restaurantRepository.findByRestaurantId(restaurant.getRestaurantId());

            if (newRestaurant == null) {
                updateRestaurantDetails.setSuccess(false);
                updateRestaurantDetails.setMessage("Invalid restaurant Id");
                return updateRestaurantDetails;
            }

            restaurantRepository.save(restaurant);

            updateRestaurantDetails.setSuccess(true);
            updateRestaurantDetails.setMessage("restaurant details are updated");

            return updateRestaurantDetails;

        } catch (Exception e) {
            UpdateRestaurantDetails updateRestaurantDetails = new UpdateRestaurantDetails();
            updateRestaurantDetails.setSuccess(false);
            updateRestaurantDetails.setMessage("Something went wrong");
            return updateRestaurantDetails;
        }
    }

    // ADD ITEM
    public AddItemResponse addItem(AddItemRequest addItemRequest) {
        AddItemResponse addItemResponse = new AddItemResponse();

        Item currentItem = itemRepository.findByitemId(addItemRequest.getItem().getItemId());
        if (currentItem == null) {
            itemRepository.save(addItemRequest.getItem());
            ItemsByRestaurant itemsByRestaurant = new ItemsByRestaurant();
            itemsByRestaurant.setItemId(addItemRequest.getItem().getItemId());
            itemsByRestaurant.setRestaurantId(addItemRequest.getRestaurantId());
            itemsByRestaurantRepository.save(itemsByRestaurant);
            addItemResponse.setMessage("Item Added");
            addItemResponse.setSuccess(true);
        } else {
            addItemResponse.setSuccess(false);
            addItemResponse.setMessage("Item already Exists");
        }
        return addItemResponse;
    }

    // EDIT ITEM
    public EditItemResponse editItem(Item item) {
        EditItemResponse editItemResponse = new EditItemResponse();
        Item currentItem = itemRepository.findByitemId(item.getItemId());
        if (currentItem == null) {
            editItemResponse.setSuccess(false);
            editItemResponse.setMessage("Item doesn't Exist");
        } else {
            currentItem.setName(item.getName());
            currentItem.setVeg(item.isVeg());
            currentItem.setPrice(item.getPrice());
            currentItem.setDescription(item.getDescription());
            itemRepository.save(currentItem);
            editItemResponse.setSuccess(true);
            editItemResponse.setMessage("Updated Successfully");
        }
        return editItemResponse;
    }

    public GetItemResponse getAllItems(long id) {
        GetItemResponse getItemResponse = new GetItemResponse();
        List<ItemsByRestaurant> itemIdList = itemsByRestaurantRepository.findAllByRestaurantId(id);
        if (itemIdList.isEmpty()) {
            getItemResponse.setAllItems(null);
            getItemResponse.setSuccess(false);
            getItemResponse.setMessage("No Item exists for this restaurant");
        } else {
            List<Item> resultList = new ArrayList<>();
            for (ItemsByRestaurant i : itemIdList) {
                resultList.add(itemRepository.findByitemId(i.getItemId()));
            }
            getItemResponse.setAllItems(resultList);
            getItemResponse.setSuccess(true);
            getItemResponse.setMessage("Item Fetched");
        }
        return getItemResponse;
    }
}
