package com.app.rakbank.dto;

import lombok.Data;

@Data
public class ChangePasswordDTO {
    public String password;
    public String oldPassword;
}
