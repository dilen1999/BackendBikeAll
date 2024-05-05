package com.example.CycleSharingSystemBackend.service;

import com.example.CycleSharingSystemBackend.holder.LiveRideUser;
import com.example.CycleSharingSystemBackend.model.User;

import javax.xml.stream.Location;
import java.util.List;

public interface UserService {
    User register(User registerRequest);

    void verify(String email, String otp);

    User login(String emailOrMobile);

    void verifyLogin(String email, String otp);

    List<User> getInRideUsers();

    User UserLocationUpdate(Long userId, Location userLocationRequest) throws Exception;

    User updateUserLoc(Long userId, Double latitude, Double longitude) throws Exception;

    List<LiveRideUser> getCurrentOnRideUsersWithLocation();

}
