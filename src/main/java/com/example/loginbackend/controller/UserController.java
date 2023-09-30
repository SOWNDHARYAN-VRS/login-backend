package com.example.loginbackend.controller;

import com.example.loginbackend.dto.ApiResponse;
import com.example.loginbackend.dto.LoginDto;
import com.example.loginbackend.model.User;
import com.example.loginbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/")
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping("register")
    public ResponseEntity<ApiResponse> register(@RequestBody User user){
        return userService.registerUser(user);
    }
    @GetMapping("login")
    public ResponseEntity<ApiResponse> loginUser(@RequestBody LoginDto loginDto){
        return userService.loginUser(loginDto);
    }

    @GetMapping("userdetails")
    public ResponseEntity<ApiResponse> userDetails(@RequestHeader(value = AUTHORIZATION ,defaultValue = "") String auth) throws Exception {
        return userService.userDetails(auth);
    }
    @GetMapping("profile")
    public ResponseEntity<ApiResponse> profile(@RequestHeader(value = AUTHORIZATION,defaultValue = "")String auth) throws Exception{
        return userService.profileDetails(auth);
    }

}
