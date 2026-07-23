package com.ajay.inventorymanagement.dto;

public record UpdateUserRequest(
        String firstName,
        String lastName,
        String phoneNumber,
        Long roleId
) {
}