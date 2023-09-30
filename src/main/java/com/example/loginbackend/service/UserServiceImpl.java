package com.example.loginbackend.service;

import com.example.loginbackend.Util.JwtUtils;
import com.example.loginbackend.dto.ApiResponse;
import com.example.loginbackend.dto.LoginDto;
import com.example.loginbackend.dto.UserDetailsDto;
import com.example.loginbackend.exception.InvalidTokenException;
import com.example.loginbackend.model.User;
import com.example.loginbackend.repository.UserRepository;
import com.example.loginbackend.security.PasswordHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserServiceImpl implements UserService{
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordHash passwordHash;

    @Override
    public ResponseEntity<ApiResponse> profileDetails(String auth) throws Exception {
        User user=userRepository.findByEmail(jwtUtils.metaEmail(auth));
        jwtUtils.validateToken(auth);
        UserDetailsDto details=new UserDetailsDto();
        details.setAddress(user.getAddress());
        details.setCity(user.getCity());
        details.setCountry(user.getCountry());
        details.setPhoneNumber(user.getPhoneNumber());
        details.setEmail(user.getEmail());
        details.setState(user.getState());
        details.setFirstName(user.getFirstName());
        details.setLastName(user.getLastName());
        List<UserDetailsDto> detailsDtos = new ArrayList<>();
        detailsDtos.add(details);
        return ResponseEntity.ok(new ApiResponse("profile",true,detailsDtos));
    }

    @Override
    public ResponseEntity<ApiResponse> registerUser(User user) {
        if(user.getPassword().equals(user.getConfirmPassword())){
        user.setPassword(passwordHash.getPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok(new ApiResponse("Registered",true,null));
        }else{
        return ResponseEntity.badRequest().body(new ApiResponse("Not Registered",false,null));
        }
    }

    @Override
    public ResponseEntity<ApiResponse> loginUser(LoginDto loginDto) {
        User user=userRepository.findByEmail(loginDto.getEmail());
        if(passwordHash.getPasswordEncoder().matches(loginDto.getPassword(),user.getPassword())) {
            return ResponseEntity.ok(new ApiResponse(jwtUtils.generateJwt(loginDto.getEmail()),true, null));
        }
        else {
            return ResponseEntity.badRequest().body(new ApiResponse("Wrong Password",false,null));
        }
    }

    @Override
    public ResponseEntity<ApiResponse> userDetails(String auth) throws InvalidTokenException {
        try {
                jwtUtils.validateToken(auth);
            List<User> users=userRepository.findAll();
            List<UserDetailsDto> userDetails=new ArrayList<>();
            for(User user:users){
                UserDetailsDto details=new UserDetailsDto();
                details.setAddress(user.getAddress());
                details.setCity(user.getCity());
                details.setCountry(user.getCountry());
                details.setPhoneNumber(user.getPhoneNumber());
                details.setEmail(user.getEmail());
                details.setState(user.getState());
                details.setFirstName(user.getFirstName());
                details.setLastName(user.getLastName());
                userDetails.add(details);
            }

            return ResponseEntity.ok(new ApiResponse("user details", true, userDetails));
        }
        catch (InvalidTokenException e){
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(),false,null));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}