package com.example.internmatch.config;

import com.example.internmatch.entity.*;
import com.example.internmatch.enums.CriterionType;
import com.example.internmatch.enums.PostingStatus;
import com.example.internmatch.enums.RoleName;
import com.example.internmatch.enums.CompanyVerificationStatus;
import com.example.internmatch.enums.WorkMode;
import com.example.internmatch.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Component
public class RoleDataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(RoleDataInitializer.class);

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final CompanyRepository companyRepository;
    private final InternshipPostingRepository postingRepository;
    private final PostingCriterionRepository criterionRepository;
    private final PasswordEncoder passwordEncoder;
    private final JdbcTemplate jdbcTemplate;

    public RoleDataInitializer(RoleRepository roleRepository,
                               UserRepository userRepository,
                               StudentProfileRepository studentProfileRepository,
                               CompanyRepository companyRepository,
                               InternshipPostingRepository postingRepository,
                               PostingCriterionRepository criterionRepository,
                               PasswordEncoder passwordEncoder,
                               JdbcTemplate jdbcTemplate) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.studentProfileRepository = studentProfileRepository;
        this.companyRepository = companyRepository;
        this.postingRepository = postingRepository;
        this.criterionRepository = criterionRepository;
        this.passwordEncoder = passwordEncoder;
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

        seedDemoData();
    }

    private void seedDemoData() {
        Role studentRole = roleRepository.findByName(RoleName.ROLE_STUDENT).orElse(null);

        // Demo Student: batuhan@example.com
        if (userRepository.findByEmail("batuhan@example.com").isEmpty()) {
            User studentUser = User.builder()
                    .email("batuhan@example.com")
                    .password(passwordEncoder.encode("password123"))
                    .firstName("Batuhan")
                    .lastName("Yılmaz")
                    .phone("+905551112233")
                    .isActive(true)
                    .roles(studentRole != null ? Set.of(studentRole) : Set.of())
                    .build();

            userRepository.save(studentUser);

            StudentProfile profile = StudentProfile.builder()
                    .user(studentUser)
                    .university("İstanbul Teknik Üniversitesi")
                    .department("Yazılım Mühendisliği")
                    .gradeLevel(4)
                    .gpa(BigDecimal.valueOf(3.50))
                    .city("İstanbul")
                    .preferredWorkMode(WorkMode.HYBRID)
                    .summary("Tutkulu ve yenilikçi yazılım geliştirici stajyer adayı.")
                    .build();

            studentProfileRepository.save(profile);
            logger.info("Seeded demo user batuhan@example.com and student profile.");
        }

        // Demo Company & Postings
        if (postingRepository.count() == 0) {
            Company company = Company.builder()
                    .name("TechNova Yazılım A.Ş.")
                    .industry("Yazılım ve Teknoloji")
                    .city("İstanbul")
                    .location("Maslak")
                    .address("Büyükdere Cad. No:100 Maslak/İstanbul")
                    .description("Yazılım çözümleri ve bulut teknolojileri.")
                    .contactEmail("info@technova.com.tr")
                    .contactPhone("+902125550100")
                    .verificationStatus(CompanyVerificationStatus.VERIFIED)
                    .isActive(true)
                    .build();

            companyRepository.save(company);

            InternshipPosting posting1 = InternshipPosting.builder()
                    .title("Backend Developer Stajyeri Aranıyor")
                    .positionName("Backend Developer Intern")
                    .department("Software Engineering")
                    .city("İstanbul")
                    .location("Maslak")
                    .workMode(WorkMode.HYBRID)
                    .status(PostingStatus.PUBLISHED)
                    .minGpa(BigDecimal.valueOf(2.50))
                    .quota(3)
                    .startDate(LocalDate.now().plusDays(15))
                    .endDate(LocalDate.now().plusDays(105))
                    .applicationDeadline(LocalDate.now().plusDays(30))
                    .company(company)
                    .description("Spring Boot, Java ve REST API geliştirmede deneyim kazanmak isteyen stajyerler aranıyor.")
                    .build();

            postingRepository.save(posting1);

            PostingCriterion crit1 = PostingCriterion.builder()
                    .posting(posting1)
                    .type(CriterionType.LOCATION)
                    .stringValue("İstanbul")
                    .weight(BigDecimal.valueOf(30))
                    .isMandatory(false)
                    .build();

            PostingCriterion crit2 = PostingCriterion.builder()
                    .posting(posting1)
                    .type(CriterionType.WORK_MODE)
                    .stringValue("HYBRID")
                    .weight(BigDecimal.valueOf(30))
                    .isMandatory(false)
                    .build();

            PostingCriterion crit3 = PostingCriterion.builder()
                    .posting(posting1)
                    .type(CriterionType.GPA)
                    .numericValue(BigDecimal.valueOf(2.50))
                    .weight(BigDecimal.valueOf(40))
                    .isMandatory(false)
                    .build();

            criterionRepository.saveAll(List.of(crit1, crit2, crit3));

            InternshipPosting posting2 = InternshipPosting.builder()
                    .title("Java Developer Intern")
                    .positionName("Java Developer Intern")
                    .department("Software Engineering")
                    .city("Ankara")
                    .location("Çankaya")
                    .workMode(WorkMode.REMOTE)
                    .status(PostingStatus.PUBLISHED)
                    .minGpa(BigDecimal.valueOf(3.00))
                    .quota(2)
                    .startDate(LocalDate.now().plusDays(10))
                    .endDate(LocalDate.now().plusDays(100))
                    .applicationDeadline(LocalDate.now().plusDays(25))
                    .company(company)
                    .description("Remote çalışacak tutkulu Java stajyeri.")
                    .build();

            postingRepository.save(posting2);

            PostingCriterion crit4 = PostingCriterion.builder()
                    .posting(posting2)
                    .type(CriterionType.LOCATION)
                    .stringValue("Ankara")
                    .weight(BigDecimal.valueOf(50))
                    .isMandatory(false)
                    .build();

            PostingCriterion crit5 = PostingCriterion.builder()
                    .posting(posting2)
                    .type(CriterionType.GPA)
                    .numericValue(BigDecimal.valueOf(3.00))
                    .weight(BigDecimal.valueOf(50))
                    .isMandatory(false)
                    .build();

            criterionRepository.saveAll(List.of(crit4, crit5));

            logger.info("Seeded demo postings and criteria.");
        }
    }
}
