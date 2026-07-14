package com.ajay.inventorymanagement.dto;

import jakarta.validation.constraints.*;

public record CreateUserRequest(

        @NotBlank
        String firstName,

        @NotBlank
        String lastName,

        @Email
        String email,

        @Size(min = 8)
        String password,

        @NotBlank
        String phoneNumber,

        Long roleId
) {
}