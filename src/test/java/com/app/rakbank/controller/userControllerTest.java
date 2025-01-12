package com.app.rakbank.controller;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.app.rakbank.dto.UserDto;
import com.app.rakbank.entity.User;
import com.app.rakbank.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUsers() {
        List<User> users = Arrays.asList(new User(1, "John", "test@mail.com","Test@123"), new User(1, "John1", "test1@mail.com","Test1@123"));
        when(userService.getAll()).thenReturn(users);

        List<User> result = userController.getUsers();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("John");
        verify(userService, times(1)).getAll();
    }

    @Test
    void testSaveUser() {
        User user = new User(1, "John", "test@mail.com","Test@123");
        when(userService.save(any(User.class))).thenReturn(user);

        User result = userController.save(user);

        assertThat(result.getName()).isEqualTo("John");
        verify(userService, times(1)).save(user);
    }

    @Test
    void testUpdateEmp() {
        UserDto user = new UserDto(1, "John", "test@mail.com");
        User updatedUser = new User(1, "Test User", "test@mail.com","Test@123");
        when(userService.update(user, 1)).thenReturn(updatedUser);
        when(userService.save(updatedUser)).thenReturn(updatedUser);

        ResponseEntity<?> result = userController.updateEmp(1, user);

        assertThat(result.getBody()).isEqualTo(updatedUser);
        verify(userService, times(1)).update(user, 1);
        verify(userService, times(1)).save(updatedUser);
    }

    @Test
    void testUpdatePassword() {
        doNothing().when(userService).changePassword(1, "newpassword");

        ResponseEntity<?> result = userController.updatePassword(1, "newpassword");

        Map<String, Boolean> expectedResponse = new LinkedHashMap<>();
        expectedResponse.put("passwordChanged", Boolean.TRUE);

        assertThat(result.getBody()).isEqualTo(expectedResponse);
        verify(userService, times(1)).changePassword(1, "newpassword");
    }

    @Test
    void testGetEmpById() {
        User user = new User(1, "John", "test@mail.com","Test@123");
        when(userService.getUserById(1)).thenReturn(user);

        ResponseEntity<?> result = userController.getEmpById(1);

        assertThat(result.getBody()).isEqualTo(user);
        verify(userService, times(1)).getUserById(1);
    }

    @Test
    void testDeleteEmp() {
        doNothing().when(userService).delete(1);

        ResponseEntity<?> result = userController.deleteEmp(1);

        Map<String, Boolean> expectedResponse = new LinkedHashMap<>();
        expectedResponse.put("deleted", Boolean.TRUE);

        assertThat(result.getBody()).isEqualTo(expectedResponse);
        verify(userService, times(1)).delete(1);
    }
}
