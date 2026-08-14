package com.example.internmatch.dto.request;

import com.example.internmatch.enums.CriterionType;
import com.example.internmatch.enums.SkillLevel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Schema(description = "Staj ilanı kriteri güncelleme isteği")
public class UpdatePostingCriterionRequest {

    @Schema(description = "Kriter tipi. Desteklenen değerler: SKILL, LOCATION, WORK_MODE, GPA, GRADE_LEVEL, CUSTOM", example = "GPA")
    private CriterionType type;

    @Schema(description = "SKILL kriteri için beceri ID'si", example = "2")
    private Long skillId;

    @Schema(description = "SKILL kriteri için beklenen beceri seviyesi", example = "ADVANCED")
    private SkillLevel requiredSkillLevel;

    @Size(max = 255, message = "Metin değeri en fazla 255 karakter olabilir")
    @Schema(description = "LOCATION için şehir, WORK_MODE için REMOTE/ONSITE/HYBRID, GRADE_LEVEL için sınıf seviyesi, CUSTOM için açıklama", example = "3. sınıf")
    private String stringValue;

    @DecimalMin(value = "0.0", inclusive = true, message = "Sayısal değer 0'dan küçük olamaz")
    @DecimalMax(value = "4.0", inclusive = true, message = "GPA kriteri için değer 4.0'dan büyük olamaz")
    @Schema(description = "GPA kriteri için minimum not ortalaması", example = "3.20")
    private BigDecimal numericValue;

    @Schema(description = "Kriter zorunlu mu?", example = "false")
    private Boolean isMandatory;

    @Min(value = 1, message = "Ağırlık değeri en az 1 olmalıdır")
    @Max(value = 100, message = "Ağırlık değeri en fazla 100 olabilir")
    @Schema(description = "Kriter ağırlığı. Aynı ilandaki kriterlerin toplamı 100'ü geçemez.", example = "25")
    private Integer weight;

    public CriterionType getType() { return type; }
    public void setType(CriterionType type) { this.type = type; }

    public Long getSkillId() { return skillId; }
    public void setSkillId(Long skillId) { this.skillId = skillId; }

    public SkillLevel getRequiredSkillLevel() { return requiredSkillLevel; }
    public void setRequiredSkillLevel(SkillLevel requiredSkillLevel) { this.requiredSkillLevel = requiredSkillLevel; }

    public String getStringValue() { return stringValue; }
    public void setStringValue(String stringValue) { this.stringValue = stringValue; }

    public BigDecimal getNumericValue() { return numericValue; }
    public void setNumericValue(BigDecimal numericValue) { this.numericValue = numericValue; }

    public Boolean getIsMandatory() { return isMandatory; }
    public void setIsMandatory(Boolean isMandatory) { this.isMandatory = isMandatory; }

    public Integer getWeight() { return weight; }
    public void setWeight(Integer weight) { this.weight = weight; }
}
