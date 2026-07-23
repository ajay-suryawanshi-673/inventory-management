package com.ajay.inventorymanagement.controller;

import com.ajay.inventorymanagement.Service.UserService;
import com.ajay.inventorymanagement.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody @Valid LoginRequest request) {

        return ResponseEntity.ok(userService.login(request));

    }
    @GetMapping("/search")
    public ResponseEntity<Page<UserResponse>> searchUsers(
            @RequestParam String keyword,
            Pageable pageable) {

        return ResponseEntity.ok(
                userService.searchUsers(keyword, pageable)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                userService.getUserById(id)
        );
    }

    @GetMapping
    public ResponseEntity<Page<UserResponse>> getAllUsers(
            Pageable pageable) {

        return ResponseEntity.ok(
                userService.getAllUsers(pageable)
        );
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @RequestBody UpdateUserRequest request) {

        return ResponseEntity.ok(
                userService.updateUser(id, request)
        );
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long id) {

        userService.deleteUser(id);

        return ResponseEntity.noContent().build();
    }


}
