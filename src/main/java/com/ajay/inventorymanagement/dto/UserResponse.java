package com.ajay.inventorymanagement.dto;

public record UserResponse(
        Long id,

        String firstName,

        String lastName,

        String email,

        String phoneNumber,

        String role
)  {
}
