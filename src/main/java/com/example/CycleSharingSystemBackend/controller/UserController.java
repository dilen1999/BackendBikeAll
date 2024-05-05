package com.example.CycleSharingSystemBackend.controller;

import com.example.CycleSharingSystemBackend.holder.LiveRideUser;
import com.example.CycleSharingSystemBackend.model.User;
import com.example.CycleSharingSystemBackend.repository.Userrepository;
import com.example.CycleSharingSystemBackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.xml.stream.Location;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/users")

public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User registerRequest) {
        User response = userService.register(registerRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PostMapping("/verify")
    public ResponseEntity<?> verifiedUser(@RequestParam String email, @RequestParam String otp) {
        try {
            userService.verify(email,otp);
            return new ResponseEntity<>("User verified successfully", HttpStatus.OK);
        } catch(RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/verify/login")
    public ResponseEntity<?> verifiedUserLogin(@RequestParam String email, @RequestParam String otp) {
        try {
            userService.verifyLogin(email,otp);
            return new ResponseEntity<>("User verified successfully", HttpStatus.OK);
        } catch(RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String emailOrMobile = loginRequest.get("emailOrMobile");
        User user = userService.login(emailOrMobile);

        if(user==null) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Unauthorized");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        Map<String, String> response = new HashMap<>();
        response.put("message", "Otp has sent to " + emailOrMobile);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/location/{userId}")
    public User UserLocationUpdate(@PathVariable Long userId, @RequestBody Location userLocationRequest) throws Exception {
        return userService.UserLocationUpdate(userId, userLocationRequest);
    }

    @PutMapping("/update/{userId}")
    public User UpdateUser(@PathVariable Long userId, @RequestParam Double latitude, @RequestParam Double longitude) throws Exception {

        return userService.updateUserLoc(userId, latitude, longitude);
    }


    @GetMapping("/inRide")
    public List<User> GetInRideUsers() {

        return userService.getInRideUsers();
    }

//    @GetMapping("/in-ride-locations")
//    public ResponseEntity<List<UserLocation>> getInRideUserLocations() {
//        List<UserLocation> inRideUserLocations = userService.getInRideUserLocations();
//        return ResponseEntity.ok(inRideUserLocations);
//    }


    @GetMapping("/onRideUsers")
    public List<LiveRideUser> getCurrentOnRideUsersWithLocation() {
        return userService.getCurrentOnRideUsersWithLocation();
    }
}
