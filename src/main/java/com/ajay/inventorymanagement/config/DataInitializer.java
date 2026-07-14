package com.ajay.inventorymanagement.config;

import com.ajay.inventorymanagement.Entity.Role;
import com.ajay.inventorymanagement.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {

        if (roleRepository.findByName("ADMIN").isEmpty()) {

            Role admin = new Role();
            admin.setName("ADMIN");

            roleRepository.save(admin);
        }

        if (roleRepository.findByName("MANAGER").isEmpty()) {

            Role manager = new Role();
            manager.setName("MANAGER");

            roleRepository.save(manager);
        }

        System.out.println("Roles Initialized Successfully");
    }
}