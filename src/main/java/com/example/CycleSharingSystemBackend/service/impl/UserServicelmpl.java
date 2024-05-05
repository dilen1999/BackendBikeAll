package com.example.CycleSharingSystemBackend.service.impl;

import com.example.CycleSharingSystemBackend.holder.LiveRideUser;
import com.example.CycleSharingSystemBackend.model.User;
import com.example.CycleSharingSystemBackend.repository.Userrepository;
import com.example.CycleSharingSystemBackend.service.LocationService;
import com.example.CycleSharingSystemBackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.stream.Location;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private Userrepository userRepository;
    @Autowired
    private LocationService locationService;

    private final EmailService emailService;


    @Override
    public User register(User registerRequest) {
        User existingUser = userRepository.findByEmail(registerRequest.getEmail());
        String otp = generateOtp();
        if(existingUser != null && existingUser.isVerified()) {
            throw new RuntimeException("user already registered.");
        } else if (existingUser != null && !existingUser.isVerified()) {
            existingUser.setVerified(true);
            existingUser.setOtp(otp);
            userRepository.save(existingUser);
            sendVerificationEmail(existingUser.getEmail(),otp);

            return createRegisterResponse(existingUser);
        }

        User user = User.builder()
                .email(registerRequest.getEmail())
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .mobile(registerRequest.getMobile())
                .locationId(registerRequest.getLocationId())
                .otp(otp)
                .inRide(registerRequest.isInRide())
                .build();

        User savedUser = userRepository.save(user);
        sendVerificationEmail(savedUser.getEmail(),otp);

        return createRegisterResponse(savedUser);
    }

    private User createRegisterResponse(User user) {
        return User.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .mobile(user.getMobile())
                .locationId(user.getLocationId())
                .inRide(user.isInRide())
                .build();
    }
    @Override
    public void verify(String email, String otp) {
        User user = userRepository.findByEmail(email);
        if(user == null) {
            throw new RuntimeException("User not found");
        } else if (user.isVerified()) {
            throw new RuntimeException("User is already verified");
        } else if (otp.equals(user.getOtp())) {
            user.setVerified(true);
            userRepository.save(user);
        } else {
            throw new RuntimeException("Internal Sever Error");
        }
    }

    @Override
    public User login(String emailOrMobile) {
        User existingUser;
        if(emailOrMobile.contains("@")) {
            existingUser = userRepository.findByEmail(emailOrMobile);
            if(existingUser == null && !existingUser.isVerified()) {
                throw new RuntimeException("user is not registered.");
            } else {
                String otp = generateOtp();
                existingUser.setOtp(otp);
                User savedUser = userRepository.save(existingUser);
                sendVerificationEmail(savedUser.getEmail(),otp);

            }

        } else {
            existingUser = userRepository.findByMobile(emailOrMobile);
            if(existingUser == null && !existingUser.isVerified()) {
                throw new RuntimeException("user is not registered.");
            } else {

            }

        }
        return  existingUser;
    }

    @Override
    public void verifyLogin(String email, String otp) {

        User user = userRepository.findByEmail(email);
        if(user == null) {
            throw new RuntimeException("User not found");
        } else if (otp.equals(user.getOtp())) {
            userRepository.save(user);
        } else {
            throw new RuntimeException("Internal Sever Error");
        }
    }




    @Override
    public List<User> getInRideUsers() {
        List<User> allUsers = userRepository.findAll();
        List<User> inRideUsers = new ArrayList<>();

        for(User user: allUsers) {
            if(user.isInRide()) {
                inRideUsers.add(user);
            }
        }
        return inRideUsers;
    }

    @Override
    public User UserLocationUpdate(Long userId, Location userLocationRequest) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("User not found with ID: " + userId));

        double latitude = userLocationRequest.getLatitude();
        double longitude = userLocationRequest.getLongitude();

        if (latitude >= -90 && latitude <= 90 && longitude >= -180 && longitude <= 180) {

            Location  existingLocation = locationService.getLocation(userLocationRequest.getLatitude(), userLocationRequest.getLongitude());
            if (existingLocation != null) {
                // If the location exists, use that locationId
                user.setLocationId(existingLocation.getLocationId());
            } else {
                // If the location doesn't exist, add it as a new location

                Location newLocation = locationService.addLocation(userLocationRequest);
                if (newLocation != null) {
                    user.setLocationId(newLocation.getLocationId());

                    userRepository.save(user);
                } else {
                    user.setLocationId(null);
//                throw new RuntimeException("Failed to add new location.");
                }
            }
        } else {
            throw  new RuntimeException("not in limit");
        }



        return user;
    }

    @Override
    public User updateUserLoc(Long userId, Double latitude, Double longitude) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("User not found with ID: " + userId));


        if (latitude >= -90 && latitude <= 90 && longitude >= -180 && longitude <= 180) {

            Location  existingLocation = locationService.getLocation(latitude, longitude);
            if (existingLocation != null) {
                // If the location exists, use that locationId
                user.setLocationId(existingLocation.getLocationId());
            } else {
                // If the location doesn't exist, add it as a new location

                Location newLocation = locationService.addLocation(Location.builder().latitude(latitude).longitude(longitude).build());
                if (newLocation != null) {
                    user.setLocationId(newLocation.getLocationId());

                    userRepository.save(user);
                } else {
                    user.setLocationId(null);

                }
            }
        } else {
            throw  new RuntimeException("not in limit");
        }



        return user;
    }

    @Override
    public List<LiveRideUser> getCurrentOnRideUsersWithLocation() {
        List<User> users = getInRideUsers();
        List<LiveRideUser>liveRideUsers = new ArrayList<>();
        for(User u: users){
            Location location = locationService.findByLocationId(u.getLocationId());
            LiveRideUser user = LiveRideUser.builder()
                    .userId(u.getUserId())
                    .latitude(location.getLatitude())
                    .longitude(location.getLongitude())
                    .build();
            liveRideUsers.add(user);
        }
        return liveRideUsers;
    }


    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(899999);
        return String.valueOf(otp);
    }

    private void sendVerificationEmail(String email, String otp) {
        String subject = "Email verification";
        String body = "Your verification otp is : "+ otp;
        emailService.sendEmail(email, subject, body);
    }




}

