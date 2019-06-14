package com.github.reubuisnessgame.gamebank.adminservice.repository;

import com.github.reubuisnessgame.gamebank.adminservice.model.AdminModel;
import com.github.reubuisnessgame.gamebank.adminservice.model.Role;
import com.github.reubuisnessgame.gamebank.adminservice.model.UserModel;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DbInit implements CommandLineRunner {
    private final AdminRepository adminRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public DbInit(UserRepository userRepository, PasswordEncoder passwordEncoder, AdminRepository adminRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminRepository = adminRepository;
    }


    @Override
    public void run(String... args) {


        UserModel adminUser = new UserModel("admin", passwordEncoder.encode("admin"), "MODERATOR");
        AdminModel adminModel = new AdminModel(adminUser.getId(), "admin", Role.MODERATOR, 0D, 0D);

        userRepository.save(adminUser);
        adminRepository.save(adminModel);

    }
}
