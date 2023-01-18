package com.group11.fooddelivery.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateStatusRequest extends Request {
    private String orderId;
    private String status;
}
