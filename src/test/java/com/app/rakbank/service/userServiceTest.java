package com.app.rakbank.service;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.app.rakbank.dto.UserDto;
import com.app.rakbank.entity.User;
import com.app.rakbank.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        User user = new User(1, "John", "john@example.com", "password123");
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.save(user);

        assertThat(result.getPassword()).isEqualTo("encodedPassword");
        verify(passwordEncoder, times(1)).encode("password123");
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testUpdate() {
        User existingUser = new User(1, "John", "john@example.com", "password123");
        UserDto updatedDetails = new UserDto(1, "John Updated", "john_updated@example.com");
        when(userRepository.findById(1)).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.encode("newpassword")).thenReturn("encodedNewPassword");
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        User result = userService.update(updatedDetails, 1);

        assertThat(result.getName()).isEqualTo("John Updated");
        assertThat(result.getEmail()).isEqualTo("john_updated@example.com");
        assertThat(result.getPassword()).isEqualTo("password123");
        verify(userRepository, times(1)).findById(1);
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    void testGetAll() {
        List<User> users = Arrays.asList(new User(1, "John", "john@example.com", "password123"), new User(1, "Test", "test@example.com", "password123"));
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAll();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("John");
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUserById() {
        User user = new User(1, "John", "john@example.com", "password123");
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        User result = userService.getUserById(1);

        assertThat(result).isEqualTo(user);
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    void testDelete() {
        doNothing().when(userRepository).deleteById(1);

        userService.delete(1);

        verify(userRepository, times(1)).deleteById(1);
    }

    @Test
    void testChangePassword() {
        User existingUser = new User(1, "John", "john@example.com", "password123");
        when(userRepository.findById(1)).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.encode("newpassword")).thenReturn("encodedNewPassword");
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        userService.changePassword(1, "newpassword");

        assertThat(existingUser.getPassword()).isEqualTo("encodedNewPassword");
        verify(userRepository, times(1)).findById(1);
        verify(userRepository, times(1)).save(existingUser);
        verify(passwordEncoder, times(1)).encode("newpassword");
    }
}
