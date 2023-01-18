package com.group11.fooddelivery.model.response;

import com.group11.fooddelivery.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetProfileResponse extends Response{
//    private User profile;
    private String email;
    private String name;
    private String role;
}
