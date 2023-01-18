package com.group11.fooddelivery.model.submodel;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderDetails {
    private String orderId;
    private List<ItemDetails> itemDetailsList;
    private String status;
}
