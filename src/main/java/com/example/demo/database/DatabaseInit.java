package com.example.demo.database;

import com.example.demo.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DatabaseInit implements CommandLineRunner {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public DatabaseInit(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Delete All
        //this.userRepository.deleteAll();


        User guest = new User("user@gbc.ca", passwordEncoder.encode("user"), "USER", "");
        User admin = new User("admin@gbc.ca", passwordEncoder.encode("admin"), "ADMIN", "ACCESS_TEST1, ACCESS_TEST2");
        User manager = new User("manager@gbc.ca", passwordEncoder.encode("manager"), "MANAGER", "ACCESS_TEST1");

        List<User> users = Arrays.asList(guest, admin, manager);
        //Save to database
        this.userRepository.saveAll(users);
    }
}
