package com.group11.fooddelivery.service;

import com.group11.fooddelivery.clients.AuthenticationClient;
import com.group11.fooddelivery.configure.Constants;
import com.group11.fooddelivery.model.User;
import com.group11.fooddelivery.model.request.EditProfileRequest;
import com.group11.fooddelivery.model.request.GetProfileRequest;
import com.group11.fooddelivery.model.request.LoginRequest;
import com.group11.fooddelivery.model.request.SignOutRequest;
import com.group11.fooddelivery.model.response.*;
import com.group11.fooddelivery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CommonService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthenticationClient authenticationClient;

    String pepper = "SomethingIsHappening";

    public LoginResponse authenticate(LoginRequest loginRequest) {
        User presentUser = userRepository.findByEmail(loginRequest.getEmail());
        LoginResponse loginResponse = new LoginResponse();
        if (presentUser == null) {
            loginResponse.setSuccess(false);
            loginResponse.setMessage("User Not Found! Invalid Email! Please Signup first");
        } else {
            String hashedPassword = BCrypt.hashpw(loginRequest.getPassword() + pepper, presentUser.getSalt());
            if (hashedPassword.equals(presentUser.getPassword())) {
                if (!presentUser.isActive()) {
                    loginResponse.setSuccess(false);
                    loginResponse.setMessage("User is banned.");
                } else {
                    String uuid = UUID.randomUUID().toString();
                    System.out.println("Token for this login session = "+uuid);
                    loginResponse.setSuccess(true);
                    loginResponse.setMessage("Login Successful!!");
                    loginResponse.setToken(uuid);
                    presentUser.setToken(uuid);
                    userRepository.save(presentUser);
                }
            } else {
                loginResponse.setSuccess(false);
                loginResponse.setMessage("Password is incorrect!! Please enter correct credentials!!");
            }
        }
        return loginResponse;
    }

    public SignUpResponse register(User user) {
        User currentUser = userRepository.findByEmail(user.getEmail());

        SignUpResponse signUpResponse = new SignUpResponse();
        if (currentUser != null && currentUser.getEmail().equals(user.getEmail())) {
            signUpResponse.setSuccess(false);
            signUpResponse.setMessage("User Already Exists!! Please login!!");
        } else {
            String salt = BCrypt.gensalt();
            String hashedPassword = BCrypt.hashpw(user.getPassword() + pepper, salt);

            user.setPassword(hashedPassword);
            user.setSalt(salt);

            userRepository.save(user);
            signUpResponse.setSuccess(true);
            signUpResponse.setMessage("SignUp Successful!! Please login to Order Food!! ");
        }
        return signUpResponse;
    }

    public GetProfileResponse getProfile(GetProfileRequest getProfileRequest) {
        GetProfileResponse getProfileResponse = new GetProfileResponse();

        //Verify session token.
        if (!authenticationClient.verifyToken(getProfileRequest)) {
            getProfileResponse.setSuccess(false);
            getProfileResponse.setMessage("User session expired.");
            return getProfileResponse;
        }

        String email = getProfileRequest.getEmail();
        User user = userRepository.findByEmail(email);
        if (user == null) {
            getProfileResponse.setSuccess(false);
            getProfileResponse.setMessage("User not found");
            return getProfileResponse;
        }
        getProfileResponse.setName(user.getName());
        getProfileResponse.setEmail(user.getEmail());
        getProfileResponse.setRole(user.getRole());
        getProfileResponse.setSuccess(true);
        getProfileResponse.setMessage("User found!");
        return getProfileResponse;
    }

    public EditProfileResponse editProfile(EditProfileRequest editProfileRequest) {
        EditProfileResponse editProfileResponse = new EditProfileResponse();

        //Verify session token.
        if (!authenticationClient.verifyToken(editProfileRequest)) {
            editProfileResponse.setSuccess(false);
            editProfileResponse.setMessage("User session expired.");
            return editProfileResponse;
        }

        User user = userRepository.findByEmail(editProfileRequest.getEmail());
        if (user == null) {
            editProfileResponse.setSuccess(false);
            editProfileResponse.setMessage("User not found");
            return editProfileResponse;
        }
        if (Constants.name.equals(editProfileRequest.getField())) {
            //Change name
            editProfileResponse.setOldValue(user.getName());
            user.setName(editProfileRequest.getNewValue());
        } else if (Constants.password.equals(editProfileRequest.getField())) {
            //Change password
            editProfileResponse.setOldValue(user.getPassword());
            user.setPassword(editProfileRequest.getNewValue());
        }
        userRepository.save(user);
        editProfileResponse.setSuccess(true);
        editProfileResponse.setMessage(editProfileRequest.getField() + " updated successfully.");
        editProfileResponse.setEmail(editProfileRequest.getEmail());
        editProfileResponse.setField(editProfileRequest.getField());
        editProfileResponse.setNewValue(editProfileRequest.getNewValue());
        return editProfileResponse;
    }

    public SignOutResponse logout(SignOutRequest signOutRequest) {
        SignOutResponse signOutResponse = new SignOutResponse();

        //Verify session token.
        if (!authenticationClient.verifyToken(signOutRequest)) {
            signOutResponse.setSuccess(false);
            signOutResponse.setMessage("User session expired.");
            return signOutResponse;
        }

        User currentUser = userRepository.findByEmail(signOutRequest.getEmail());

        if (currentUser != null) {
            currentUser.setToken(null);
            userRepository.save(currentUser);
            signOutResponse.setSuccess(true);
            signOutResponse.setMessage("Logged out successfully.");
        } else {
            signOutResponse.setSuccess(false);
            signOutResponse.setMessage("Something went wrong!!");
        }
        return signOutResponse;

    }
}
