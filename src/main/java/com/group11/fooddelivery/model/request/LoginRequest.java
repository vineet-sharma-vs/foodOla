package com.group11.fooddelivery.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class LoginRequest extends Request{
    @NotEmpty(message = "Name Should be non-empty!!")
    private String email;
    @NotEmpty(message = "Password Should be non-empty!!")
    private String password;
}
