package com.ecom.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterUserRequest {

    private String email;
    private String password;
    private String fullName;
    private String phone;
}
