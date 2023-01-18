package com.group11.fooddelivery.model.response;

import com.group11.fooddelivery.model.submodel.OrderDetails;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetOrdersResponse extends Response {
    private Long restaurantId;
    private List<OrderDetails> orderDetailsList;
}
