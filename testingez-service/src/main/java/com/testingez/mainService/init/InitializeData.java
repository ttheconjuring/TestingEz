package com.testingez.mainService.init;

import com.testingez.mainService.model.entities.Role;
import com.testingez.mainService.model.enums.UserRole;
import com.testingez.mainService.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class InitializeData implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        seedRoles();
    }

    private void seedRoles() {
        if (this.roleRepository.count() == 0) {
            Role adminRole = new Role();
            adminRole.setRole(UserRole.ADMINISTRATOR);
            this.roleRepository.saveAndFlush(adminRole);
            Role standartRole = new Role();
            standartRole.setRole(UserRole.STANDARD);
            this.roleRepository.saveAndFlush(standartRole);
        }
    }

}
