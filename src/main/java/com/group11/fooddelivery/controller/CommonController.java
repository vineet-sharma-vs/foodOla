package com.group11.fooddelivery.controller;

import com.group11.fooddelivery.model.User;
import com.group11.fooddelivery.model.request.EditProfileRequest;
import com.group11.fooddelivery.model.request.GetProfileRequest;
import com.group11.fooddelivery.model.request.LoginRequest;
import com.group11.fooddelivery.model.request.SignOutRequest;
import com.group11.fooddelivery.model.response.*;
import com.group11.fooddelivery.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
public class CommonController {
    @Autowired
    CommonService commonService;

    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = commonService.authenticate(loginRequest);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/signup", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SignUpResponse> signup(@Valid @RequestBody User user) {
        SignUpResponse signUpResponse = commonService.register(user);
        return new ResponseEntity<>(signUpResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/getProfile", consumes = "application/json", produces = "application/json")
    public ResponseEntity<GetProfileResponse> getProfile(@RequestBody GetProfileRequest getProfileRequest) {
        GetProfileResponse getProfileResponse = commonService.getProfile(getProfileRequest);
        return new ResponseEntity<>(getProfileResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/editProfile", consumes = "application/json", produces = "application/json")
    public ResponseEntity<EditProfileResponse> editProfile(@RequestBody EditProfileRequest editProfileRequest) {
        EditProfileResponse editProfileResponse = commonService.editProfile(editProfileRequest);
        return new ResponseEntity<>(editProfileResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/logOut",consumes = "application/json",produces = "application/json")
    public ResponseEntity<SignOutResponse>logout(@RequestBody SignOutRequest signOutRequest){
        SignOutResponse signOutResponse= commonService.logout(signOutRequest);
        return new ResponseEntity<>(signOutResponse,HttpStatus.OK);
    }
}
