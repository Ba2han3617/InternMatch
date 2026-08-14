package com.example.internmatch.service;

import com.example.internmatch.dto.request.CreatePostingCriterionRequest;
import com.example.internmatch.dto.response.PostingCriterionResponseDto;
import com.example.internmatch.entity.Company;
import com.example.internmatch.entity.InternshipPosting;
import com.example.internmatch.entity.PostingCriterion;
import com.example.internmatch.entity.Role;
import com.example.internmatch.entity.Skill;
import com.example.internmatch.entity.User;
import com.example.internmatch.enums.CriterionType;
import com.example.internmatch.enums.PostingStatus;
import com.example.internmatch.enums.RoleName;
import com.example.internmatch.enums.SkillLevel;
import com.example.internmatch.enums.WorkMode;
import com.example.internmatch.exception.BadRequestException;
import com.example.internmatch.repository.InternshipPostingRepository;
import com.example.internmatch.repository.PostingCriterionRepository;
import com.example.internmatch.repository.SkillRepository;
import com.example.internmatch.service.impl.PostingCriterionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostingCriterionServiceTest {

    @Mock
    private PostingCriterionRepository criterionRepository;

    @Mock
    private InternshipPostingRepository postingRepository;

    @Mock
    private SkillRepository skillRepository;

    private PostingCriterionService criterionService;

    @BeforeEach
    void setUp() {
        criterionService = new PostingCriterionServiceImpl(criterionRepository, postingRepository, skillRepository);
    }

    @Test
    void createCriterionShouldCreateSuccessfully() {
        Company company = company(1L);
        User companyUser = user(10L, RoleName.ROLE_COMPANY, company);
        InternshipPosting posting = posting(100L, company, PostingStatus.DRAFT);
        Skill skill = Skill.builder().id(5L).name("Java").category("Backend").build();

        CreatePostingCriterionRequest request = new CreatePostingCriterionRequest();
        request.setType(CriterionType.SKILL);
        request.setSkillId(5L);
        request.setRequiredSkillLevel(SkillLevel.INTERMEDIATE);
        request.setIsMandatory(true);
        request.setWeight(30);

        when(postingRepository.findById(100L)).thenReturn(Optional.of(posting));
        when(criterionRepository.sumWeightByPostingId(100L)).thenReturn(BigDecimal.valueOf(40));
        when(skillRepository.findById(5L)).thenReturn(Optional.of(skill));
        when(criterionRepository.save(any(PostingCriterion.class))).thenAnswer(invocation -> {
            PostingCriterion criterion = invocation.getArgument(0);
            criterion.setId(1L);
            return criterion;
        });

        PostingCriterionResponseDto response = criterionService.createCriterion(100L, request, companyUser);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(CriterionType.SKILL, response.getType());
        assertEquals(5L, response.getSkillId());
        assertEquals(30, response.getWeight());
        verify(criterionRepository).save(any(PostingCriterion.class));
    }

    @Test
    void createCriterionShouldThrowWhenTotalWeightExceedsOneHundred() {
        Company company = company(1L);
        User companyUser = user(10L, RoleName.ROLE_COMPANY, company);
        InternshipPosting posting = posting(100L, company, PostingStatus.DRAFT);

        CreatePostingCriterionRequest request = new CreatePostingCriterionRequest();
        request.setType(CriterionType.GPA);
        request.setNumericValue(BigDecimal.valueOf(3.20));
        request.setWeight(30);

        when(postingRepository.findById(100L)).thenReturn(Optional.of(posting));
        when(criterionRepository.sumWeightByPostingId(100L)).thenReturn(BigDecimal.valueOf(80));

        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> criterionService.createCriterion(100L, request, companyUser));

        assertEquals(true, exception.getMessage().contains("toplam ağırlığı 100'ü geçemez"));
        verify(criterionRepository, never()).save(any());
    }

    @Test
    void createCriterionShouldDenyCompanyUserForAnotherCompanyPosting() {
        Company ownerCompany = company(1L);
        Company otherCompany = company(2L);
        User otherCompanyUser = user(20L, RoleName.ROLE_COMPANY, otherCompany);
        InternshipPosting posting = posting(100L, ownerCompany, PostingStatus.DRAFT);

        CreatePostingCriterionRequest request = new CreatePostingCriterionRequest();
        request.setType(CriterionType.LOCATION);
        request.setStringValue("Istanbul");
        request.setWeight(20);

        when(postingRepository.findById(100L)).thenReturn(Optional.of(posting));

        assertThrows(AccessDeniedException.class,
                () -> criterionService.createCriterion(100L, request, otherCompanyUser));
        verify(criterionRepository, never()).save(any());
    }

    @Test
    void getCriteriaByPostingShouldListSuccessfullyForStudentOnPublishedPosting() {
        Company company = company(1L);
        User student = user(30L, RoleName.ROLE_STUDENT, null);
        InternshipPosting posting = posting(100L, company, PostingStatus.PUBLISHED);

        PostingCriterion locationCriterion = PostingCriterion.builder()
                .id(1L)
                .posting(posting)
                .type(CriterionType.LOCATION)
                .stringValue("Istanbul")
                .isMandatory(true)
                .weight(BigDecimal.valueOf(25))
                .build();

        PostingCriterion gpaCriterion = PostingCriterion.builder()
                .id(2L)
                .posting(posting)
                .type(CriterionType.GPA)
                .numericValue(BigDecimal.valueOf(3.00))
                .isMandatory(false)
                .weight(BigDecimal.valueOf(35))
                .build();

        when(postingRepository.findById(100L)).thenReturn(Optional.of(posting));
        when(criterionRepository.findByPostingIdOrderByIdAsc(100L)).thenReturn(List.of(locationCriterion, gpaCriterion));

        List<PostingCriterionResponseDto> response = criterionService.getCriteriaByPosting(100L, student);

        assertEquals(2, response.size());
        assertEquals(CriterionType.LOCATION, response.get(0).getType());
        assertEquals("Istanbul", response.get(0).getStringValue());
        assertEquals(CriterionType.GPA, response.get(1).getType());
    }

    private Company company(Long id) {
        return Company.builder()
                .id(id)
                .name("Company " + id)
                .build();
    }

    private InternshipPosting posting(Long id, Company company, PostingStatus status) {
        return InternshipPosting.builder()
                .id(id)
                .title("Backend Intern")
                .workMode(WorkMode.HYBRID)
                .status(status)
                .company(company)
                .build();
    }

    private User user(Long id, RoleName roleName, Company company) {
        Role role = Role.builder().id(id).name(roleName).build();
        return User.builder()
                .id(id)
                .email("user" + id + "@example.com")
                .roles(Set.of(role))
                .company(company)
                .isActive(true)
                .build();
    }
}
