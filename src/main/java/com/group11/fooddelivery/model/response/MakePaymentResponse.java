package com.group11.fooddelivery.model.response;

import com.group11.fooddelivery.model.submodel.PlaceOrderResponseItems;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MakePaymentResponse extends Response {
    private String orderId;
    private String restaurantName;
    private List<PlaceOrderResponseItems> selectedItems;
}
