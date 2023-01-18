package com.group11.fooddelivery.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse extends Response {
    private String token;
}
