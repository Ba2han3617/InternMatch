package com.example.internmatch.service.impl;

import com.example.internmatch.dto.response.CriterionResultDetailDto;
import com.example.internmatch.dto.response.MatchScoreResponseDto;
import com.example.internmatch.entity.InternshipPosting;
import com.example.internmatch.entity.MatchScore;
import com.example.internmatch.entity.PostingCriterion;
import com.example.internmatch.entity.StudentProfile;
import com.example.internmatch.entity.User;
import com.example.internmatch.enums.CriterionType;
import com.example.internmatch.enums.RoleName;
import com.example.internmatch.enums.WorkMode;
import com.example.internmatch.exception.BadRequestException;
import com.example.internmatch.exception.ResourceNotFoundException;
import com.example.internmatch.repository.InternshipPostingRepository;
import com.example.internmatch.repository.MatchScoreRepository;
import com.example.internmatch.repository.PostingCriterionRepository;
import com.example.internmatch.repository.StudentProfileRepository;
import com.example.internmatch.service.MatchScoreService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MatchScoreServiceImpl implements MatchScoreService {

    private final MatchScoreRepository matchScoreRepository;
    private final InternshipPostingRepository postingRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final PostingCriterionRepository criterionRepository;
    private final ObjectMapper objectMapper;

    public MatchScoreServiceImpl(MatchScoreRepository matchScoreRepository,
                                 InternshipPostingRepository postingRepository,
                                 StudentProfileRepository studentProfileRepository,
                                 PostingCriterionRepository criterionRepository,
                                 ObjectMapper objectMapper) {
        this.matchScoreRepository = matchScoreRepository;
        this.postingRepository = postingRepository;
        this.studentProfileRepository = studentProfileRepository;
        this.criterionRepository = criterionRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional
    public MatchScoreResponseDto calculateMatchScore(Long postingId, User currentUser) {
        if (postingId == null) {
            throw new BadRequestException("Staj ilanı ID boş olamaz.");
        }

        if (!hasRole(currentUser, RoleName.ROLE_STUDENT)) {
            throw new AccessDeniedException("Şirket veya Admin öğrenci yerine skor hesaplayamaz.");
        }

        StudentProfile studentProfile = studentProfileRepository.findByUserId(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Öğrenci profili bulunamadı. Lütfen önce profil oluşturun."));

        InternshipPosting posting = postingRepository.findById(postingId)
                .orElseThrow(() -> new ResourceNotFoundException("Staj ilanı bulunamadı. ID: " + postingId));

        List<PostingCriterion> criteria = criterionRepository.findByPostingIdOrderByIdAsc(postingId);
        if (criteria.isEmpty()) {
            throw new BadRequestException("Bu ilana ait kriter bulunmamaktadır.");
        }

        BigDecimal totalCriteriaWeight = BigDecimal.ZERO;
        BigDecimal totalEarnedWeight = BigDecimal.ZERO;
        int matchedCount = 0;
        List<CriterionResultDetailDto> detailsList = new ArrayList<>();

        for (PostingCriterion criterion : criteria) {
            BigDecimal weight = criterion.getWeight() != null ? criterion.getWeight() : BigDecimal.ZERO;
            totalCriteriaWeight = totalCriteriaWeight.add(weight);

            CriterionResultDetailDto detail = evaluateCriterion(criterion, studentProfile, weight);
            detailsList.add(detail);

            if (Boolean.TRUE.equals(detail.getMatched())) {
                matchedCount++;
                totalEarnedWeight = totalEarnedWeight.add(weight);
            }
        }

        BigDecimal totalScorePercentage = BigDecimal.ZERO;
        if (totalCriteriaWeight.compareTo(BigDecimal.ZERO) > 0) {
            totalScorePercentage = totalEarnedWeight
                    .divide(totalCriteriaWeight, 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100))
                    .setScale(2, RoundingMode.HALF_UP);
        }

        String detailsJson;
        try {
            detailsJson = objectMapper.writeValueAsString(detailsList);
        } catch (Exception e) {
            detailsJson = "[]";
        }

        Optional<MatchScore> existingScoreOpt = matchScoreRepository
                .findByStudentProfileIdAndInternshipPostingId(studentProfile.getId(), posting.getId());

        MatchScore matchScore;
        if (existingScoreOpt.isPresent()) {
            matchScore = existingScoreOpt.get();
            matchScore.setTotalScore(totalScorePercentage);
            matchScore.setMatchedCriteriaCount(matchedCount);
            matchScore.setTotalCriteriaCount(criteria.size());
            matchScore.setDetailsJson(detailsJson);
            matchScore.setCalculatedAt(LocalDateTime.now());
        } else {
            matchScore = MatchScore.builder()
                    .studentProfile(studentProfile)
                    .internshipPosting(posting)
                    .totalScore(totalScorePercentage)
                    .matchedCriteriaCount(matchedCount)
                    .totalCriteriaCount(criteria.size())
                    .detailsJson(detailsJson)
                    .calculatedAt(LocalDateTime.now())
                    .build();
        }

        MatchScore saved = matchScoreRepository.save(matchScore);
        return mapToDto(saved, detailsList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MatchScoreResponseDto> getMyMatchScores(User currentUser) {
        if (!hasRole(currentUser, RoleName.ROLE_STUDENT)) {
            throw new AccessDeniedException("Sadece öğrenciler kendi skorlarını listeleyebilir.");
        }

        StudentProfile studentProfile = studentProfileRepository.findByUserId(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Öğrenci profili bulunamadı."));

        return matchScoreRepository.findByStudentProfileIdOrderByCalculatedAtDesc(studentProfile.getId())
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MatchScoreResponseDto> getPostingMatchScores(Long postingId, User currentUser) {
        if (postingId == null) {
            throw new BadRequestException("Staj ilanı ID boş olamaz.");
        }

        InternshipPosting posting = postingRepository.findById(postingId)
                .orElseThrow(() -> new ResourceNotFoundException("Staj ilanı bulunamadı. ID: " + postingId));

        validateCompanyOrAdminAccess(posting, currentUser);

        return matchScoreRepository.findByInternshipPostingIdOrderByTotalScoreDesc(postingId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public MatchScoreResponseDto getMatchScoreById(Long id, User currentUser) {
        if (id == null) {
            throw new BadRequestException("Skor ID boş olamaz.");
        }

        MatchScore matchScore = matchScoreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skor bulunamadı. ID: " + id));

        validateScoreAccess(matchScore, currentUser);
        return mapToDto(matchScore);
    }

    private CriterionResultDetailDto evaluateCriterion(PostingCriterion criterion, StudentProfile studentProfile, BigDecimal weight) {
        CriterionType type = criterion.getType();
        boolean matched = false;
        String criterionName = "";
        String description = "";

        if (type == null) {
            return CriterionResultDetailDto.builder()
                    .criterionType(null)
                    .criterionName("Bilinmeyen")
                    .weight(weight)
                    .matched(false)
                    .earnedScore(BigDecimal.ZERO)
                    .description("Kriter tipi tanımlanmamış.")
                    .build();
        }

        switch (type) {
            case SKILL:
                criterionName = criterion.getSkill() != null ? criterion.getSkill().getName() : "Belirtilmedi";
                String targetSkillName = criterionName;
                Long targetSkillId = criterion.getSkill() != null ? criterion.getSkill().getId() : null;

                boolean hasSkill = studentProfile.getStudentSkills() != null && studentProfile.getStudentSkills().stream().anyMatch(ss -> {
                    if (ss.getSkill() == null) return false;
                    if (targetSkillId != null && targetSkillId.equals(ss.getSkill().getId())) return true;
                    return targetSkillName.equalsIgnoreCase(ss.getSkill().getName());
                });

                if (hasSkill) {
                    matched = true;
                    description = "Öğrenci " + targetSkillName + " becerisine sahip.";
                } else {
                    description = "Öğrenci " + targetSkillName + " becerisine sahip değil.";
                }
                break;

            case LOCATION:
                criterionName = criterion.getStringValue() != null ? criterion.getStringValue().trim() : "";
                String studentCity = studentProfile.getCity();
                if (studentCity != null && !studentCity.trim().isEmpty() && studentCity.trim().equalsIgnoreCase(criterionName)) {
                    matched = true;
                    description = "Öğrencinin şehir bilgisi (" + studentCity + ") ilan kriteriyle (" + criterionName + ") uyuşuyor.";
                } else {
                    description = "Öğrencinin şehir bilgisi (" + (studentCity != null ? studentCity : "Belirtilmedi") + ") ilan kriteriyle (" + criterionName + ") uyuşmuyor.";
                }
                break;

            case WORK_MODE:
                criterionName = criterion.getStringValue() != null ? criterion.getStringValue().trim() : "";
                WorkMode studentWorkMode = studentProfile.getPreferredWorkMode();
                if (studentWorkMode != null && studentWorkMode.name().equalsIgnoreCase(criterionName)) {
                    matched = true;
                    description = "Öğrencinin tercih ettiği çalışma modeli (" + studentWorkMode + ") ilan kriteriyle (" + criterionName + ") uyuşuyor.";
                } else {
                    description = "Öğrencinin tercih ettiği çalışma modeli (" + (studentWorkMode != null ? studentWorkMode : "Belirtilmedi") + ") ilan kriteriyle (" + criterionName + ") uyuşmuyor.";
                }
                break;

            case GPA:
                BigDecimal minGpa = criterion.getNumericValue();
                criterionName = minGpa != null ? minGpa.toString() : "0.00";
                BigDecimal studentGpa = studentProfile.getGpa();
                if (studentGpa != null && minGpa != null && studentGpa.compareTo(minGpa) >= 0) {
                    matched = true;
                    description = "Öğrencinin GPA değeri (" + studentGpa + ") minimum kriteri (" + minGpa + ") karşılıyor.";
                } else {
                    description = "Öğrencinin GPA değeri (" + (studentGpa != null ? studentGpa : "Belirtilmedi") + ") minimum kriteri (" + minGpa + ") karşılamıyor.";
                }
                break;

            case GRADE_LEVEL:
                criterionName = criterion.getStringValue() != null ? criterion.getStringValue().trim() : "";
                Integer studentGrade = studentProfile.getGradeLevel();
                if (studentGrade != null && !criterionName.isEmpty()) {
                    if (studentGrade.toString().equalsIgnoreCase(criterionName) || criterionName.contains(studentGrade.toString())) {
                        matched = true;
                    } else {
                        try {
                            int reqGrade = Integer.parseInt(criterionName);
                            if (studentGrade >= reqGrade) {
                                matched = true;
                            }
                        } catch (NumberFormatException ignored) {
                        }
                    }
                }
                if (matched) {
                    description = "Öğrencinin sınıf seviyesi (" + studentGrade + ") ilan kriterini (" + criterionName + ") karşılıyor.";
                } else {
                    description = "Öğrencinin sınıf seviyesi (" + (studentGrade != null ? studentGrade : "Belirtilmedi") + ") ilan kriterini (" + criterionName + ") karşılamıyor.";
                }
                break;

            case CUSTOM:
                criterionName = criterion.getStringValue() != null ? criterion.getStringValue().trim() : "Özel Kriter";
                matched = false;
                description = "CUSTOM kriteri (" + criterionName + ") otomatik puanlamaya dahil edilmedi, detay olarak saklandı.";
                break;

            default:
                criterionName = type.name();
                description = type.name() + " kriteri için otomatik eşleştirme kuralı uygulanmadı.";
                break;
        }

        BigDecimal earnedScore = matched ? weight : BigDecimal.ZERO;
        return CriterionResultDetailDto.builder()
                .criterionType(type)
                .criterionName(criterionName)
                .weight(weight)
                .matched(matched)
                .earnedScore(earnedScore)
                .description(description)
                .build();
    }

    private void validateCompanyOrAdminAccess(InternshipPosting posting, User currentUser) {
        if (hasRole(currentUser, RoleName.ROLE_ADMIN)) {
            return;
        }
        if (hasRole(currentUser, RoleName.ROLE_COMPANY)) {
            if (currentUser.getCompany() != null
                    && posting.getCompany() != null
                    && currentUser.getCompany().getId().equals(posting.getCompany().getId())) {
                return;
            }
        }
        throw new AccessDeniedException("Bu ilana ait skorları görüntüleme yetkiniz yok.");
    }

    private void validateScoreAccess(MatchScore matchScore, User currentUser) {
        if (hasRole(currentUser, RoleName.ROLE_ADMIN)) {
            return;
        }
        if (hasRole(currentUser, RoleName.ROLE_STUDENT)) {
            if (matchScore.getStudentProfile() != null
                    && matchScore.getStudentProfile().getUser() != null
                    && currentUser.getId().equals(matchScore.getStudentProfile().getUser().getId())) {
                return;
            }
        }
        if (hasRole(currentUser, RoleName.ROLE_COMPANY)) {
            if (currentUser.getCompany() != null
                    && matchScore.getInternshipPosting() != null
                    && matchScore.getInternshipPosting().getCompany() != null
                    && currentUser.getCompany().getId().equals(matchScore.getInternshipPosting().getCompany().getId())) {
                return;
            }
        }
        throw new AccessDeniedException("Bu skor detayını görüntüleme yetkiniz yok.");
    }

    private boolean hasRole(User user, RoleName roleName) {
        return user.getRoles().stream().anyMatch(role -> role.getName() == roleName);
    }

    private MatchScoreResponseDto mapToDto(MatchScore entity) {
        List<CriterionResultDetailDto> details = parseDetailsJson(entity.getDetailsJson());
        return mapToDto(entity, details);
    }

    private MatchScoreResponseDto mapToDto(MatchScore entity, List<CriterionResultDetailDto> details) {
        StudentProfile student = entity.getStudentProfile();
        User studentUser = student != null ? student.getUser() : null;
        String studentName = studentUser != null
                ? ((studentUser.getFirstName() != null ? studentUser.getFirstName() : "") + " " + (studentUser.getLastName() != null ? studentUser.getLastName() : "")).trim()
                : null;

        InternshipPosting posting = entity.getInternshipPosting();

        return MatchScoreResponseDto.builder()
                .id(entity.getId())
                .studentProfileId(student != null ? student.getId() : null)
                .studentName(studentName)
                .postingId(posting != null ? posting.getId() : null)
                .postingTitle(posting != null ? posting.getTitle() : null)
                .companyName((posting != null && posting.getCompany() != null) ? posting.getCompany().getName() : null)
                .totalScore(entity.getTotalScore())
                .matchedCriteriaCount(entity.getMatchedCriteriaCount())
                .totalCriteriaCount(entity.getTotalCriteriaCount())
                .detailsJson(entity.getDetailsJson())
                .details(details)
                .calculatedAt(entity.getCalculatedAt())
                .build();
    }

    private List<CriterionResultDetailDto> parseDetailsJson(String detailsJson) {
        if (detailsJson == null || detailsJson.trim().isEmpty()) {
            return Collections.emptyList();
        }
        try {
            return objectMapper.readValue(detailsJson, new TypeReference<List<CriterionResultDetailDto>>() {});
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}
