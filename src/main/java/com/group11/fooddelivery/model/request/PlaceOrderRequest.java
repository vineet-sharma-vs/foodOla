package com.group11.fooddelivery.model.request;

import com.group11.fooddelivery.model.submodel.ItemDetails;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PlaceOrderRequest extends Request {
    private Long restaurantId;
    private List<ItemDetails> selectedItems;
}