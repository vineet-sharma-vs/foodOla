package com.group11.fooddelivery.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditProfileRequest extends Request {
    private String field;
    private String newValue;
}
