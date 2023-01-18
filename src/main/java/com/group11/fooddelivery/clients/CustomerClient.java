package com.group11.fooddelivery.clients;

import com.group11.fooddelivery.model.Item;
import com.group11.fooddelivery.model.Order;
import com.group11.fooddelivery.model.submodel.ItemDetails;
import com.group11.fooddelivery.model.submodel.PlaceOrderResponseItems;
import com.group11.fooddelivery.repository.ItemRepository;
import com.group11.fooddelivery.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerClient {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    OrderRepository orderRepository;

    public List<PlaceOrderResponseItems> buildSelectedItems(List<ItemDetails> itemDetailsList) {
        List<PlaceOrderResponseItems> placeOrderResponseItemsList = new ArrayList<>();
        for(ItemDetails itemDetails : itemDetailsList)  {
            PlaceOrderResponseItems placeOrderResponseItems = new PlaceOrderResponseItems();
            int quantity = itemDetails.getQuantity();
            long itemId = itemDetails.getItemId();

            Item item = itemRepository.findById(itemId).orElse(null);
            assert item != null;
            placeOrderResponseItems.setName(item.getName());
            placeOrderResponseItems.setQuantity(quantity);
            placeOrderResponseItems.setPrice(item.getPrice()*quantity);
            placeOrderResponseItemsList.add(placeOrderResponseItems);
        }
        return placeOrderResponseItemsList;
    }

    public int calculateTotalPrice(List<ItemDetails> selectedItems) {
        int totalPrice = 0;
        for(ItemDetails orderedItems:selectedItems)    {
            long itemId = orderedItems.getItemId();
            int quantity = orderedItems.getQuantity();
            Item item = itemRepository.findById(itemId).orElse(null);
            assert item != null;
            int price = item.getPrice();
            totalPrice = totalPrice + quantity * price;


        }
        return totalPrice;
    }

    public void populateOrderInfo(Order order, List<ItemDetails> selectedItems) {
        for(ItemDetails itemDetails :selectedItems)  {
            order.setItemId(itemDetails.getItemId());
            order.setQuantity(itemDetails.getQuantity());
            orderRepository.save(order);
        }
    }

    public double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        } else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            if (unit.equals("K")) {
                dist = dist * 1.609344;
            } else if (unit.equals("N")) {
                dist = dist * 0.8684;
            }
            return (dist);
        }
    }
}
