package com.group11.fooddelivery.model.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LatLongRequest extends Request {
    private double latitude;
    private double longitude;
}
