package com.ajay.inventorymanagement.Service;

import com.ajay.inventorymanagement.Entity.Role;
import com.ajay.inventorymanagement.Entity.User;
import com.ajay.inventorymanagement.dto.*;
import com.ajay.inventorymanagement.enums.UserStatus;
import com.ajay.inventorymanagement.exception.DuplicateResourceException;
import com.ajay.inventorymanagement.exception.ResourceNotFoundException;
import com.ajay.inventorymanagement.repository.RoleRepository;
import com.ajay.inventorymanagement.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository= roleRepository;
        this.passwordEncoder = passwordEncoder;
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
                .password(passwordEncoder.encode(request.password()))
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

    public LoginResponse login(LoginRequest request) {
        return null;
    }


    public UserResponse getUserById(Long id) {

        User user = userRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getRole().getName()
        );
    }

    public Page<UserResponse> getAllUsers(Pageable pageable) {

        return userRepository.findByIsDeletedFalse(pageable)
                .map(this::mapToUserResponse);
    }
    public UserResponse updateUser(Long id, UpdateUserRequest request) {

        User user = userRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        if (request.firstName() != null) {
            user.setFirstName(request.firstName());
        }

        if (request.lastName() != null) {
            user.setLastName(request.lastName());
        }

        if (request.phoneNumber() != null) {
            user.setPhoneNumber(request.phoneNumber());
        }

        if (request.roleId() != null) {

            Role role = roleRepository.findById(request.roleId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Role not found"));

            user.setRole(role);
        }

        User updatedUser = userRepository.save(user);

        return mapToUserResponse(updatedUser);
    }

    public void deleteUser(Long id) {

        User user = userRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        user.setDeleted(true);

        userRepository.save(user);
    }

    public Page<UserResponse> searchUsers(
            String keyword,
            Pageable pageable) {

        return userRepository
                .searchUsers(keyword, pageable)
                .map(this::mapToUserResponse);
    }
    private UserResponse mapToUserResponse(User user) {

        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getRole().getName()
        );
    }



}
