package com.group11.fooddelivery.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditProfileResponse extends Response{
    private String email;
    private String field;
    private String oldValue;
    private String newValue;
}
