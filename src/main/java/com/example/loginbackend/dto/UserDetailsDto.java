package com.example.loginbackend.dto;

import lombok.Data;

@Data
public class UserDetailsDto {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String address;
    private String country;
    private String city;
    private String state;
}
