package com.ajay.inventorymanagement.controller;

import com.ajay.inventorymanagement.Service.UserService;
import com.ajay.inventorymanagement.dto.CreateUserRequest;
import com.ajay.inventorymanagement.dto.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    @PostMapping
   public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request){
       UserResponse response =userService.createUser(request);
       return ResponseEntity
               .status(HttpStatus.CREATED)
               .body(response);
   }

}
