package com.ajay.inventorymanagement.Service;

import com.ajay.inventorymanagement.Entity.Role;
import com.ajay.inventorymanagement.Entity.User;
import com.ajay.inventorymanagement.dto.CreateUserRequest;
import com.ajay.inventorymanagement.dto.UserResponse;
import com.ajay.inventorymanagement.enums.UserStatus;
import com.ajay.inventorymanagement.exception.DuplicateResourceException;
import com.ajay.inventorymanagement.exception.ResourceNotFoundException;
import com.ajay.inventorymanagement.repository.RoleRepository;
import com.ajay.inventorymanagement.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository= roleRepository;
    }

    public boolean emailExists(String email){

        return userRepository.existsByEmail(email);

    }
    public UserResponse createUser(CreateUserRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("Email already exists");
        }

        Role role = roleRepository.findById(request.roleId())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        User user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(request.password()) // Abhi plain text, JWT step me encrypt karenge
                .phoneNumber(request.phoneNumber())
                .status(UserStatus.ACTIVE)
                .role(role)
                .build();

        User savedUser = userRepository.save(user);

        return new UserResponse(
                savedUser.getId(),
                savedUser.getFirstName(),
                savedUser.getLastName(),
                savedUser.getEmail(),
                savedUser.getPhoneNumber(),
                savedUser.getRole().getName()
        );
    }

}
