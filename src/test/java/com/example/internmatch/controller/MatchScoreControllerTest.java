package com.example.internmatch.controller;

import com.example.internmatch.dto.response.CriterionResultDetailDto;
import com.example.internmatch.dto.response.MatchScoreResponseDto;
import com.example.internmatch.entity.Company;
import com.example.internmatch.entity.Role;
import com.example.internmatch.entity.User;
import com.example.internmatch.enums.CriterionType;
import com.example.internmatch.enums.RoleName;
import com.example.internmatch.security.CustomUserDetails;
import com.example.internmatch.service.MatchScoreService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class MatchScoreControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Mock
    private MatchScoreService matchScoreService;

    @InjectMocks
    private MatchScoreController matchScoreController;

    private CustomUserDetails studentUserDetails;
    private CustomUserDetails companyUserDetails;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        mockMvc = MockMvcBuilders.standaloneSetup(matchScoreController)
                .setCustomArgumentResolvers(new AuthenticationPrincipalArgumentResolver())
                .build();

        Role studentRole = Role.builder().id(1L).name(RoleName.ROLE_STUDENT).build();
        User mockStudentUser = User.builder()
                .id(1L)
                .email("student@example.com")
                .roles(Set.of(studentRole))
                .build();
        studentUserDetails = new CustomUserDetails(mockStudentUser);

        Role companyRole = Role.builder().id(2L).name(RoleName.ROLE_COMPANY).build();
        Company company = Company.builder().id(10L).name("Tech Corp").build();
        User mockCompanyUser = User.builder()
                .id(2L)
                .email("company@example.com")
                .company(company)
                .roles(Set.of(companyRole))
                .build();
        companyUserDetails = new CustomUserDetails(mockCompanyUser);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void calculateMatchScoreShouldReturnCreatedAndMatchScoreDto() throws Exception {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                studentUserDetails, null, studentUserDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        CriterionResultDetailDto detail = CriterionResultDetailDto.builder()
                .criterionType(CriterionType.SKILL)
                .criterionName("Java")
                .weight(BigDecimal.valueOf(50))
                .matched(true)
                .earnedScore(BigDecimal.valueOf(50))
                .description("Öğrenci Java becerisine sahip.")
                .build();

        MatchScoreResponseDto responseDto = MatchScoreResponseDto.builder()
                .id(1L)
                .studentProfileId(10L)
                .studentName("Ali Yilmaz")
                .postingId(20L)
                .postingTitle("Java Developer Intern")
                .companyName("Tech Corp")
                .totalScore(BigDecimal.valueOf(100.00))
                .matchedCriteriaCount(1)
                .totalCriteriaCount(1)
                .details(List.of(detail))
                .calculatedAt(LocalDateTime.now())
                .build();

        when(matchScoreService.calculateMatchScore(eq(20L), any())).thenReturn(responseDto);

        mockMvc.perform(post("/api/match-scores/calculate/20")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.postingTitle").value("Java Developer Intern"))
                .andExpect(jsonPath("$.totalScore").value(100.00))
                .andExpect(jsonPath("$.details[0].criterionName").value("Java"));
    }

    @Test
    void getMyMatchScoresShouldReturnListOfMatchScores() throws Exception {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                studentUserDetails, null, studentUserDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        MatchScoreResponseDto responseDto = MatchScoreResponseDto.builder()
                .id(1L)
                .studentProfileId(10L)
                .postingId(20L)
                .totalScore(BigDecimal.valueOf(80.00))
                .build();

        when(matchScoreService.getMyMatchScores(any())).thenReturn(List.of(responseDto));

        mockMvc.perform(get("/api/match-scores/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].totalScore").value(80.00));
    }

    @Test
    void getPostingMatchScoresShouldReturnListOfMatchScores() throws Exception {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                companyUserDetails, null, companyUserDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        MatchScoreResponseDto responseDto = MatchScoreResponseDto.builder()
                .id(1L)
                .postingId(20L)
                .totalScore(BigDecimal.valueOf(90.00))
                .build();

        when(matchScoreService.getPostingMatchScores(eq(20L), any())).thenReturn(List.of(responseDto));

        mockMvc.perform(get("/api/match-scores/posting/20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].totalScore").value(90.00));
    }

    @Test
    void getMatchScoreByIdShouldReturnMatchScoreDetail() throws Exception {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                studentUserDetails, null, studentUserDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        MatchScoreResponseDto responseDto = MatchScoreResponseDto.builder()
                .id(1L)
                .totalScore(BigDecimal.valueOf(75.00))
                .build();

        when(matchScoreService.getMatchScoreById(eq(1L), any())).thenReturn(responseDto);

        mockMvc.perform(get("/api/match-scores/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.totalScore").value(75.00));
    }
}
