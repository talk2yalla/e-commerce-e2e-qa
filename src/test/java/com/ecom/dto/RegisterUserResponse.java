package com.ecom.dto;

import lombok.Data;

@Data
public class RegisterUserResponse {

    private Long id;
    private String email;
    private String fullName;
    private String phone;
    private String role;
    private String status;
    private String createdAt;
}
