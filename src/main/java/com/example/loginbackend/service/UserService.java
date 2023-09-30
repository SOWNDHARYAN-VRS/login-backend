package com.example.loginbackend.service;

import com.example.loginbackend.dto.ApiResponse;
import com.example.loginbackend.dto.LoginDto;
import com.example.loginbackend.model.User;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

import java.security.PublicKey;

public interface UserService {
    public ResponseEntity<ApiResponse> profileDetails(String auth)throws Exception;
    public ResponseEntity<ApiResponse> registerUser(User user);
    public ResponseEntity<ApiResponse> loginUser(LoginDto loginDto);

    ResponseEntity<ApiResponse> userDetails(String auth) throws Exception;

}
