package com.example.internmatch.service.impl;

import com.example.internmatch.dto.request.CreatePostingCriterionRequest;
import com.example.internmatch.dto.request.UpdatePostingCriterionRequest;
import com.example.internmatch.dto.response.PostingCriterionResponseDto;
import com.example.internmatch.entity.InternshipPosting;
import com.example.internmatch.entity.PostingCriterion;
import com.example.internmatch.entity.Skill;
import com.example.internmatch.entity.User;
import com.example.internmatch.enums.CriterionType;
import com.example.internmatch.enums.PostingStatus;
import com.example.internmatch.enums.RoleName;
import com.example.internmatch.enums.WorkMode;
import com.example.internmatch.exception.BadRequestException;
import com.example.internmatch.exception.ResourceNotFoundException;
import com.example.internmatch.repository.InternshipPostingRepository;
import com.example.internmatch.repository.PostingCriterionRepository;
import com.example.internmatch.repository.SkillRepository;
import com.example.internmatch.service.PostingCriterionService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PostingCriterionServiceImpl implements PostingCriterionService {

    private static final BigDecimal MAX_TOTAL_WEIGHT = BigDecimal.valueOf(100);
    private static final EnumSet<CriterionType> SUPPORTED_TYPES = EnumSet.of(
            CriterionType.SKILL,
            CriterionType.LOCATION,
            CriterionType.WORK_MODE,
            CriterionType.GPA,
            CriterionType.GRADE_LEVEL,
            CriterionType.CUSTOM
    );

    private final PostingCriterionRepository criterionRepository;
    private final InternshipPostingRepository postingRepository;
    private final SkillRepository skillRepository;

    public PostingCriterionServiceImpl(PostingCriterionRepository criterionRepository,
                                       InternshipPostingRepository postingRepository,
                                       SkillRepository skillRepository) {
        this.criterionRepository = criterionRepository;
        this.postingRepository = postingRepository;
        this.skillRepository = skillRepository;
    }

    @Override
    @Transactional
    public PostingCriterionResponseDto createCriterion(Long postingId, CreatePostingCriterionRequest request, User currentUser) {
        InternshipPosting posting = getPosting(postingId);
        validateCompanyCanManage(posting, currentUser);
        validateSupportedType(request.getType());
        validateFields(request.getType(), request.getSkillId(), request.getStringValue(), request.getNumericValue());

        BigDecimal newWeight = BigDecimal.valueOf(request.getWeight());
        validateTotalWeight(postingId, null, newWeight);

        PostingCriterion criterion = PostingCriterion.builder()
                .posting(posting)
                .type(request.getType())
                .skill(resolveSkill(request.getType(), request.getSkillId()))
                .requiredSkillLevel(request.getRequiredSkillLevel())
                .stringValue(normalizeStringValue(request.getType(), request.getStringValue()))
                .numericValue(request.getNumericValue())
                .isMandatory(request.getIsMandatory())
                .weight(newWeight)
                .build();
        clearIrrelevantFields(criterion);

        PostingCriterion savedCriterion = criterionRepository.save(Objects.requireNonNull(criterion));
        return mapToDto(savedCriterion);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostingCriterionResponseDto> getCriteriaByPosting(Long postingId, User currentUser) {
        InternshipPosting posting = getPosting(postingId);
        validateCanView(posting, currentUser);

        return criterionRepository.findByPostingIdOrderByIdAsc(postingId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PostingCriterionResponseDto updateCriterion(Long criteriaId, UpdatePostingCriterionRequest request, User currentUser) {
        PostingCriterion criterion = getCriterion(criteriaId);
        InternshipPosting posting = criterion.getPosting();
        validateCompanyCanManage(posting, currentUser);

        CriterionType nextType = request.getType() != null ? request.getType() : criterion.getType();
        Long nextSkillId = request.getSkillId() != null
                ? request.getSkillId()
                : (criterion.getSkill() != null ? criterion.getSkill().getId() : null);
        String nextStringValue = request.getStringValue() != null ? request.getStringValue() : criterion.getStringValue();
        BigDecimal nextNumericValue = request.getNumericValue() != null ? request.getNumericValue() : criterion.getNumericValue();
        BigDecimal nextWeight = request.getWeight() != null ? BigDecimal.valueOf(request.getWeight()) : criterion.getWeight();

        validateSupportedType(nextType);
        validateFields(nextType, nextSkillId, nextStringValue, nextNumericValue);
        validateTotalWeight(posting.getId(), criteriaId, nextWeight);

        criterion.setType(nextType);
        criterion.setSkill(resolveSkill(nextType, nextSkillId));
        criterion.setRequiredSkillLevel(request.getRequiredSkillLevel() != null ? request.getRequiredSkillLevel() : criterion.getRequiredSkillLevel());
        criterion.setStringValue(normalizeStringValue(nextType, nextStringValue));
        criterion.setNumericValue(nextNumericValue);
        clearIrrelevantFields(criterion);
        if (request.getIsMandatory() != null) {
            criterion.setIsMandatory(request.getIsMandatory());
        }
        criterion.setWeight(nextWeight);

        PostingCriterion updatedCriterion = criterionRepository.save(Objects.requireNonNull(criterion));
        return mapToDto(updatedCriterion);
    }

    @Override
    @Transactional
    public void deleteCriterion(Long criteriaId, User currentUser) {
        PostingCriterion criterion = getCriterion(criteriaId);
        validateCompanyCanManage(criterion.getPosting(), currentUser);
        criterionRepository.delete(Objects.requireNonNull(criterion));
    }

    private InternshipPosting getPosting(Long postingId) {
        return postingRepository.findById(Objects.requireNonNull(postingId))
                .orElseThrow(() -> new ResourceNotFoundException("Staj ilanı bulunamadı. ID: " + postingId));
    }

    private PostingCriterion getCriterion(Long criteriaId) {
        return criterionRepository.findById(Objects.requireNonNull(criteriaId))
                .orElseThrow(() -> new ResourceNotFoundException("Kriter bulunamadı. ID: " + criteriaId));
    }

    private void validateCompanyCanManage(InternshipPosting posting, User currentUser) {
        if (!hasRole(currentUser, RoleName.ROLE_COMPANY) || !isCompanyOwner(currentUser, posting)) {
            throw new AccessDeniedException("Bu ilan için kriter yönetme yetkiniz yok.");
        }
    }

    private void validateCanView(InternshipPosting posting, User currentUser) {
        if (hasRole(currentUser, RoleName.ROLE_ADMIN)) {
            return;
        }
        if (hasRole(currentUser, RoleName.ROLE_COMPANY) && isCompanyOwner(currentUser, posting)) {
            return;
        }
        if (hasRole(currentUser, RoleName.ROLE_STUDENT) && posting.getStatus() == PostingStatus.PUBLISHED) {
            return;
        }
        throw new AccessDeniedException("Bu ilana ait kriterleri görüntüleme yetkiniz yok.");
    }

    private boolean hasRole(User user, RoleName roleName) {
        return user.getRoles().stream().anyMatch(role -> role.getName() == roleName);
    }

    private boolean isCompanyOwner(User user, InternshipPosting posting) {
        return user.getCompany() != null
                && posting.getCompany() != null
                && user.getCompany().getId().equals(posting.getCompany().getId());
    }

    private void validateSupportedType(CriterionType type) {
        if (type == null || !SUPPORTED_TYPES.contains(type)) {
            throw new BadRequestException("Geçersiz kriter tipi. Desteklenen tipler: SKILL, LOCATION, WORK_MODE, GPA, GRADE_LEVEL, CUSTOM.");
        }
    }

    private void validateFields(CriterionType type, Long skillId, String stringValue, BigDecimal numericValue) {
        switch (type) {
            case SKILL:
                if (skillId == null) {
                    throw new BadRequestException("SKILL kriteri için skillId alanı zorunludur.");
                }
                break;
            case LOCATION:
                requireText(stringValue, "LOCATION kriteri için şehir değeri stringValue alanında gönderilmelidir.");
                break;
            case WORK_MODE:
                requireText(stringValue, "WORK_MODE kriteri için çalışma modeli stringValue alanında gönderilmelidir.");
                try {
                    WorkMode.valueOf(stringValue.trim().toUpperCase());
                } catch (IllegalArgumentException ex) {
                    throw new BadRequestException("Geçersiz çalışma modeli. Desteklenen değerler: REMOTE, ONSITE, HYBRID.");
                }
                break;
            case GPA:
                if (numericValue == null) {
                    throw new BadRequestException("GPA kriteri için numericValue alanı zorunludur.");
                }
                if (numericValue.compareTo(BigDecimal.ZERO) < 0 || numericValue.compareTo(BigDecimal.valueOf(4)) > 0) {
                    throw new BadRequestException("GPA kriteri 0.0 ile 4.0 arasında olmalıdır.");
                }
                break;
            case GRADE_LEVEL:
                requireText(stringValue, "GRADE_LEVEL kriteri için sınıf seviyesi stringValue alanında gönderilmelidir.");
                break;
            case CUSTOM:
                requireText(stringValue, "CUSTOM kriteri için özel kriter açıklaması stringValue alanında gönderilmelidir.");
                break;
            default:
                validateSupportedType(type);
        }
    }

    private void requireText(String value, String message) {
        if (value == null || value.trim().isEmpty()) {
            throw new BadRequestException(message);
        }
    }

    private Skill resolveSkill(CriterionType type, Long skillId) {
        if (type != CriterionType.SKILL || skillId == null) {
            return null;
        }
        return skillRepository.findById(Objects.requireNonNull(skillId))
                .orElseThrow(() -> new ResourceNotFoundException("Beceri bulunamadı. ID: " + skillId));
    }

    private String normalizeStringValue(CriterionType type, String stringValue) {
        if (stringValue == null) {
            return null;
        }
        String trimmed = stringValue.trim();
        if (type == CriterionType.WORK_MODE) {
            return WorkMode.valueOf(trimmed.toUpperCase()).name();
        }
        return trimmed;
    }

    private void clearIrrelevantFields(PostingCriterion criterion) {
        if (criterion.getType() != CriterionType.SKILL) {
            criterion.setSkill(null);
            criterion.setRequiredSkillLevel(null);
        }
        if (criterion.getType() != CriterionType.GPA) {
            criterion.setNumericValue(null);
        }
        if (criterion.getType() == CriterionType.SKILL || criterion.getType() == CriterionType.GPA) {
            criterion.setStringValue(null);
        }
    }

    private void validateTotalWeight(Long postingId, Long criteriaIdToExclude, BigDecimal newWeight) {
        BigDecimal currentTotal = criteriaIdToExclude == null
                ? criterionRepository.sumWeightByPostingId(postingId)
                : criterionRepository.sumWeightByPostingIdExcludingCriterion(postingId, criteriaIdToExclude);

        BigDecimal nextTotal = currentTotal.add(newWeight);
        if (nextTotal.compareTo(MAX_TOTAL_WEIGHT) > 0) {
            throw new BadRequestException("Aynı ilana ait kriterlerin toplam ağırlığı 100'ü geçemez. Mevcut toplam: "
                    + currentTotal.stripTrailingZeros().toPlainString()
                    + ", eklenmek/güncellenmek istenen ağırlık: "
                    + newWeight.stripTrailingZeros().toPlainString()
                    + ", oluşacak toplam: "
                    + nextTotal.stripTrailingZeros().toPlainString() + ".");
        }
    }

    private PostingCriterionResponseDto mapToDto(PostingCriterion criterion) {
        Skill skill = criterion.getSkill();
        return PostingCriterionResponseDto.builder()
                .id(criterion.getId())
                .postingId(criterion.getPosting() != null ? criterion.getPosting().getId() : null)
                .type(criterion.getType())
                .skillId(skill != null ? skill.getId() : null)
                .skillName(skill != null ? skill.getName() : null)
                .requiredSkillLevel(criterion.getRequiredSkillLevel())
                .stringValue(criterion.getStringValue())
                .numericValue(criterion.getNumericValue())
                .isMandatory(criterion.getIsMandatory())
                .weight(criterion.getWeight() != null ? criterion.getWeight().intValue() : null)
                .build();
    }
}
