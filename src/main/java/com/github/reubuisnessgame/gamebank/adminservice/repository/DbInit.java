package com.github.reubuisnessgame.gamebank.adminservice.repository;

import com.github.reubuisnessgame.gamebank.adminservice.dao.RepositoryComponent;
import com.github.reubuisnessgame.gamebank.adminservice.model.AdminModel;
import com.github.reubuisnessgame.gamebank.adminservice.model.Role;
import com.github.reubuisnessgame.gamebank.adminservice.model.TeamModel;
import com.github.reubuisnessgame.gamebank.adminservice.model.UserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DbInit implements CommandLineRunner {
    private final AdminRepository adminRepository;
    private final TeamsRepository teamsRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    private Logger LOGGER = LoggerFactory.getLogger(DbInit.class.getSimpleName());

    public DbInit(UserRepository userRepository, PasswordEncoder passwordEncoder, AdminRepository adminRepository, TeamsRepository teamsRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminRepository = adminRepository;
        this.teamsRepository = teamsRepository;
    }


    @Override
    public void run(String... args) {
        LOGGER.warn("DbInit");

        if (!userRepository.findByUsername("admin").isPresent()) {
            try {

                UserModel adminUser = new UserModel("admin", passwordEncoder.encode("admin"), "MODERATOR");

                userRepository.save(adminUser);

                AdminModel adminModel = new AdminModel(adminUser.getId(), "admin", Role.MODERATOR, 0D, 0D);

                adminRepository.save(adminModel);
            } catch (DataIntegrityViolationException ignore) {
            }
        }
    }
}
