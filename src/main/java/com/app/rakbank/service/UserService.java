package com.app.rakbank.service;

import java.util.List;

import com.app.rakbank.dto.UserDto;
import com.app.rakbank.entity.User;

public interface UserService {
    public User save(User user);
    public User update(UserDto user, Integer userId);
	public List<User> getAll();
	public User getUserById(Integer userId);
	public void delete(Integer userId);
	public void changePassword(Integer userId, String password);
}
