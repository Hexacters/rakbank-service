package com.app.rakbank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.rakbank.dto.UserDto;
import com.app.rakbank.entity.User;
import com.app.rakbank.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService  {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User update(UserDto userDetails, Integer userId) {
        return userRepository.findById(userId)
                .map(user -> {
                    user.setName(userDetails.getName());
                    user.setEmail(userDetails.getEmail());
                    // Encrypt the password before updating
                    return userRepository.save(user);
                }).get();
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Integer userId) {
        return userRepository.findById(userId).get();
    }

    @Override
    public void delete(Integer userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public void changePassword(Integer userId, String password) {
        User user = userRepository.findById(userId).get();
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

}
