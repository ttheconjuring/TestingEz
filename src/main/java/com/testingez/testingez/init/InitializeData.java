package com.testingez.testingez.init;

import com.testingez.testingez.models.dtos.api.FunFactDTO;
import com.testingez.testingez.models.entities.Role;
import com.testingez.testingez.models.enums.UserRole;
import com.testingez.testingez.repositories.RoleRepository;
import com.testingez.testingez.services.FunFactService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class InitializeData implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final FunFactService funFactService;

    @Override
    public void run(String... args) {
        FunFactDTO[] fetch = this.funFactService.fetch();
        if (this.roleRepository.count() == 0) {
            Role standartRole = new Role();
            standartRole.setRole(UserRole.STANDARD);
            Role adminRole = new Role();
            adminRole.setRole(UserRole.ADMINISTRATOR);
            this.roleRepository.saveAndFlush(standartRole);
            this.roleRepository.saveAndFlush(adminRole);
        }
    }
}
