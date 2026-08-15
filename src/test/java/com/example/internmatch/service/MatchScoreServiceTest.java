package com.example.internmatch.service;

import com.example.internmatch.dto.response.MatchScoreResponseDto;
import com.example.internmatch.entity.Company;
import com.example.internmatch.entity.InternshipPosting;
import com.example.internmatch.entity.MatchScore;
import com.example.internmatch.entity.PostingCriterion;
import com.example.internmatch.entity.Role;
import com.example.internmatch.entity.Skill;
import com.example.internmatch.entity.StudentProfile;
import com.example.internmatch.entity.StudentSkill;
import com.example.internmatch.entity.User;
import com.example.internmatch.enums.CriterionType;
import com.example.internmatch.enums.PostingStatus;
import com.example.internmatch.enums.RoleName;
import com.example.internmatch.enums.SkillLevel;
import com.example.internmatch.enums.WorkMode;
import com.example.internmatch.exception.ResourceNotFoundException;
import com.example.internmatch.repository.InternshipPostingRepository;
import com.example.internmatch.repository.MatchScoreRepository;
import com.example.internmatch.repository.PostingCriterionRepository;
import com.example.internmatch.repository.StudentProfileRepository;
import com.example.internmatch.service.impl.MatchScoreServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MatchScoreServiceTest {

    @Mock
    private MatchScoreRepository matchScoreRepository;

    @Mock
    private InternshipPostingRepository postingRepository;

    @Mock
    private StudentProfileRepository studentProfileRepository;

    @Mock
    private PostingCriterionRepository criterionRepository;

    private ObjectMapper objectMapper;

    private MatchScoreService matchScoreService;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        matchScoreService = new MatchScoreServiceImpl(
                matchScoreRepository,
                postingRepository,
                studentProfileRepository,
                criterionRepository,
                objectMapper
        );
    }

    @Test
    void calculateMatchScoreShouldCalculateSuccessfully() {
        User studentUser = createStudentUser(1L);
        StudentProfile studentProfile = createStudentProfile(10L, studentUser, "Istanbul", BigDecimal.valueOf(3.50));
        Skill javaSkill = Skill.builder().id(100L).name("Java").build();
        addSkillToStudentProfile(studentProfile, javaSkill, SkillLevel.ADVANCED);

        Company company = createCompany(2L);
        InternshipPosting posting = createPosting(20L, company);

        PostingCriterion skillCriterion = PostingCriterion.builder()
                .id(1000L)
                .posting(posting)
                .type(CriterionType.SKILL)
                .skill(javaSkill)
                .weight(BigDecimal.valueOf(50))
                .build();

        PostingCriterion locationCriterion = PostingCriterion.builder()
                .id(1001L)
                .posting(posting)
                .type(CriterionType.LOCATION)
                .stringValue("Istanbul")
                .weight(BigDecimal.valueOf(50))
                .build();

        when(studentProfileRepository.findByUserId(1L)).thenReturn(Optional.of(studentProfile));
        when(postingRepository.findById(20L)).thenReturn(Optional.of(posting));
        when(criterionRepository.findByPostingIdOrderByIdAsc(20L)).thenReturn(List.of(skillCriterion, locationCriterion));
        when(matchScoreRepository.findByStudentProfileIdAndInternshipPostingId(10L, 20L)).thenReturn(Optional.empty());
        when(matchScoreRepository.save(any(MatchScore.class))).thenAnswer(inv -> {
            MatchScore score = inv.getArgument(0);
            score.setId(500L);
            return score;
        });

        MatchScoreResponseDto response = matchScoreService.calculateMatchScore(20L, studentUser);

        assertNotNull(response);
        assertEquals(500L, response.getId());
        assertEquals(BigDecimal.valueOf(100.00).setScale(2), response.getTotalScore());
        assertEquals(2, response.getMatchedCriteriaCount());
        assertEquals(2, response.getTotalCriteriaCount());
        assertEquals(2, response.getDetails().size());
        assertTrue(response.getDetails().get(0).getMatched());
        assertTrue(response.getDetails().get(1).getMatched());
        verify(matchScoreRepository).save(any(MatchScore.class));
    }

    @Test
    void calculateMatchScoreShouldAddScoreWhenSkillMatches() {
        User studentUser = createStudentUser(1L);
        StudentProfile studentProfile = createStudentProfile(10L, studentUser, "Ankara", BigDecimal.valueOf(2.80));
        Skill javaSkill = Skill.builder().id(100L).name("Java").build();
        addSkillToStudentProfile(studentProfile, javaSkill, SkillLevel.INTERMEDIATE);

        Company company = createCompany(2L);
        InternshipPosting posting = createPosting(20L, company);

        PostingCriterion skillCriterion = PostingCriterion.builder()
                .id(1000L)
                .posting(posting)
                .type(CriterionType.SKILL)
                .skill(javaSkill)
                .weight(BigDecimal.valueOf(40))
                .build();

        when(studentProfileRepository.findByUserId(1L)).thenReturn(Optional.of(studentProfile));
        when(postingRepository.findById(20L)).thenReturn(Optional.of(posting));
        when(criterionRepository.findByPostingIdOrderByIdAsc(20L)).thenReturn(List.of(skillCriterion));
        when(matchScoreRepository.findByStudentProfileIdAndInternshipPostingId(10L, 20L)).thenReturn(Optional.empty());
        when(matchScoreRepository.save(any(MatchScore.class))).thenAnswer(inv -> {
            MatchScore score = inv.getArgument(0);
            score.setId(501L);
            return score;
        });

        MatchScoreResponseDto response = matchScoreService.calculateMatchScore(20L, studentUser);

        assertNotNull(response);
        assertEquals(1, response.getMatchedCriteriaCount());
        assertEquals(BigDecimal.valueOf(100.00).setScale(2), response.getTotalScore());
        assertEquals(BigDecimal.valueOf(40), response.getDetails().get(0).getEarnedScore());
        assertTrue(response.getDetails().get(0).getMatched());
    }

    @Test
    void calculateMatchScoreShouldNotAddScoreWhenGpaNotMet() {
        User studentUser = createStudentUser(1L);
        StudentProfile studentProfile = createStudentProfile(10L, studentUser, "Istanbul", BigDecimal.valueOf(2.50));

        Company company = createCompany(2L);
        InternshipPosting posting = createPosting(20L, company);

        PostingCriterion gpaCriterion = PostingCriterion.builder()
                .id(1000L)
                .posting(posting)
                .type(CriterionType.GPA)
                .numericValue(BigDecimal.valueOf(3.00))
                .weight(BigDecimal.valueOf(50))
                .build();

        when(studentProfileRepository.findByUserId(1L)).thenReturn(Optional.of(studentProfile));
        when(postingRepository.findById(20L)).thenReturn(Optional.of(posting));
        when(criterionRepository.findByPostingIdOrderByIdAsc(20L)).thenReturn(List.of(gpaCriterion));
        when(matchScoreRepository.findByStudentProfileIdAndInternshipPostingId(10L, 20L)).thenReturn(Optional.empty());
        when(matchScoreRepository.save(any(MatchScore.class))).thenAnswer(inv -> inv.getArgument(0));

        MatchScoreResponseDto response = matchScoreService.calculateMatchScore(20L, studentUser);

        assertNotNull(response);
        assertEquals(0, response.getMatchedCriteriaCount());
        assertEquals(BigDecimal.valueOf(0.00).setScale(2), response.getTotalScore());
        assertFalse(response.getDetails().get(0).getMatched());
        assertEquals(BigDecimal.ZERO, response.getDetails().get(0).getEarnedScore());
    }

    @Test
    void calculateMatchScoreShouldUpdateExistingRecordWithoutDuplicate() {
        User studentUser = createStudentUser(1L);
        StudentProfile studentProfile = createStudentProfile(10L, studentUser, "Istanbul", BigDecimal.valueOf(3.50));

        Company company = createCompany(2L);
        InternshipPosting posting = createPosting(20L, company);

        PostingCriterion gpaCriterion = PostingCriterion.builder()
                .id(1000L)
                .posting(posting)
                .type(CriterionType.GPA)
                .numericValue(BigDecimal.valueOf(3.00))
                .weight(BigDecimal.valueOf(50))
                .build();

        MatchScore existingScore = MatchScore.builder()
                .id(999L)
                .studentProfile(studentProfile)
                .internshipPosting(posting)
                .totalScore(BigDecimal.valueOf(20))
                .matchedCriteriaCount(0)
                .totalCriteriaCount(1)
                .detailsJson("[]")
                .calculatedAt(LocalDateTime.now().minusDays(1))
                .build();

        when(studentProfileRepository.findByUserId(1L)).thenReturn(Optional.of(studentProfile));
        when(postingRepository.findById(20L)).thenReturn(Optional.of(posting));
        when(criterionRepository.findByPostingIdOrderByIdAsc(20L)).thenReturn(List.of(gpaCriterion));
        when(matchScoreRepository.findByStudentProfileIdAndInternshipPostingId(10L, 20L)).thenReturn(Optional.of(existingScore));
        when(matchScoreRepository.save(any(MatchScore.class))).thenAnswer(inv -> inv.getArgument(0));

        MatchScoreResponseDto response = matchScoreService.calculateMatchScore(20L, studentUser);

        assertNotNull(response);
        assertEquals(999L, response.getId());
        assertEquals(BigDecimal.valueOf(100.00).setScale(2), response.getTotalScore());
        assertEquals(1, response.getMatchedCriteriaCount());
        verify(matchScoreRepository, times(1)).save(existingScore);
    }

    @Test
    void getPostingMatchScoresShouldDenyCompanyForAnotherCompanyPosting() {
        Company company1 = createCompany(1L);
        Company company2 = createCompany(2L);

        User company2User = createCompanyUser(20L, company2);
        InternshipPosting company1Posting = createPosting(100L, company1);

        when(postingRepository.findById(100L)).thenReturn(Optional.of(company1Posting));

        assertThrows(AccessDeniedException.class,
                () -> matchScoreService.getPostingMatchScores(100L, company2User));

        verify(matchScoreRepository, never()).findByInternshipPostingIdOrderByTotalScoreDesc(any());
    }

    private User createStudentUser(Long id) {
        Role studentRole = Role.builder().id(1L).name(RoleName.ROLE_STUDENT).build();
        return User.builder()
                .id(id)
                .email("student" + id + "@example.com")
                .firstName("Ahmet")
                .lastName("Yilmaz")
                .roles(Set.of(studentRole))
                .isActive(true)
                .build();
    }

    private User createCompanyUser(Long id, Company company) {
        Role companyRole = Role.builder().id(2L).name(RoleName.ROLE_COMPANY).build();
        return User.builder()
                .id(id)
                .email("company" + id + "@example.com")
                .roles(Set.of(companyRole))
                .company(company)
                .isActive(true)
                .build();
    }

    private StudentProfile createStudentProfile(Long id, User user, String city, BigDecimal gpa) {
        StudentProfile profile = StudentProfile.builder()
                .id(id)
                .user(user)
                .city(city)
                .gpa(gpa)
                .gradeLevel(3)
                .preferredWorkMode(WorkMode.HYBRID)
                .studentSkills(new ArrayList<>())
                .build();
        user.setStudentProfile(profile);
        return profile;
    }

    private void addSkillToStudentProfile(StudentProfile profile, Skill skill, SkillLevel level) {
        StudentSkill ss = StudentSkill.builder()
                .id((long) (profile.getStudentSkills().size() + 1))
                .studentProfile(profile)
                .skill(skill)
                .level(level)
                .yearsOfExperience(2)
                .build();
        profile.getStudentSkills().add(ss);
    }

    private Company createCompany(Long id) {
        return Company.builder()
                .id(id)
                .name("Tech Corp " + id)
                .build();
    }

    private InternshipPosting createPosting(Long id, Company company) {
        return InternshipPosting.builder()
                .id(id)
                .title("Backend Intern " + id)
                .company(company)
                .status(PostingStatus.PUBLISHED)
                .build();
    }
}
