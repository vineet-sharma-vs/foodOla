package com.group11.fooddelivery.clients;

import com.group11.fooddelivery.model.User;
import com.group11.fooddelivery.model.request.Request;
import com.group11.fooddelivery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/*
    Except Signup and login, every other action will need token verification.
 */
@Component
public class AuthenticationClient {
    @Autowired
    UserRepository userRepository;

    public boolean verifyToken(Request request) {
//        return true;
        User user = userRepository.findByEmail(request.getEmail());
        if(user==null)  return false;
        String savedToken = user.getToken();
        if(savedToken==null)    return false;
        return Objects.equals(savedToken, request.getToken());
    }
}
