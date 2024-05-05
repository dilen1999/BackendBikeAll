package com.example.CycleSharingSystemBackend.repository;

import com.example.CycleSharingSystemBackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Userrepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByMobile(String emailOrMobile);

    List<User> findByInRideTrue();
}
