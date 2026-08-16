package com.example.internmatch.config;

import com.example.internmatch.entity.Role;
import com.example.internmatch.enums.RoleName;
import com.example.internmatch.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

@Component
public class RoleDataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(RoleDataInitializer.class);

    private final RoleRepository roleRepository;
    private final JdbcTemplate jdbcTemplate;

    public RoleDataInitializer(RoleRepository roleRepository, JdbcTemplate jdbcTemplate) {
        this.roleRepository = roleRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) {
        try {
            jdbcTemplate.execute("ALTER TABLE match_scores DROP COLUMN IF EXISTS overall_score");
        } catch (Exception e) {
            logger.warn("Could not alter match_scores table: {}", e.getMessage());
        }

        Arrays.stream(RoleName.values()).forEach(roleName -> {
            if (roleRepository.findByName(roleName).isEmpty()) {
                Role role = Role.builder()
                        .name(roleName)
                        .build();
                roleRepository.save(Objects.requireNonNull(role));
                logger.info("Seeded role: {}", roleName);
            }
        });
    }
}
