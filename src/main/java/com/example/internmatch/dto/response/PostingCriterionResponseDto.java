package com.example.internmatch.dto.response;

import com.example.internmatch.enums.CriterionType;
import com.example.internmatch.enums.SkillLevel;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Staj ilanı değerlendirme kriteri response")
public class PostingCriterionResponseDto {

    @Schema(description = "Kriter ID")
    private Long id;

    @Schema(description = "İlan ID")
    private Long postingId;

    @Schema(description = "Kriter tipi")
    private CriterionType type;

    @Schema(description = "Beceri ID")
    private Long skillId;

    @Schema(description = "Beceri adı")
    private String skillName;

    @Schema(description = "Beklenen beceri seviyesi")
    private SkillLevel requiredSkillLevel;

    @Schema(description = "Metin kriter değeri")
    private String stringValue;

    @Schema(description = "Sayısal kriter değeri")
    private BigDecimal numericValue;

    @Schema(description = "Kriter zorunlu mu?")
    private Boolean isMandatory;

    @Schema(description = "Kriter ağırlığı")
    private Integer weight;

    public PostingCriterionResponseDto() {}

    public static PostingCriterionResponseDtoBuilder builder() {
        return new PostingCriterionResponseDtoBuilder();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPostingId() { return postingId; }
    public void setPostingId(Long postingId) { this.postingId = postingId; }

    public CriterionType getType() { return type; }
    public void setType(CriterionType type) { this.type = type; }

    public Long getSkillId() { return skillId; }
    public void setSkillId(Long skillId) { this.skillId = skillId; }

    public String getSkillName() { return skillName; }
    public void setSkillName(String skillName) { this.skillName = skillName; }

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

    public static class PostingCriterionResponseDtoBuilder {
        private Long id;
        private Long postingId;
        private CriterionType type;
        private Long skillId;
        private String skillName;
        private SkillLevel requiredSkillLevel;
        private String stringValue;
        private BigDecimal numericValue;
        private Boolean isMandatory;
        private Integer weight;

        PostingCriterionResponseDtoBuilder() {}

        public PostingCriterionResponseDtoBuilder id(Long id) { this.id = id; return this; }
        public PostingCriterionResponseDtoBuilder postingId(Long postingId) { this.postingId = postingId; return this; }
        public PostingCriterionResponseDtoBuilder type(CriterionType type) { this.type = type; return this; }
        public PostingCriterionResponseDtoBuilder skillId(Long skillId) { this.skillId = skillId; return this; }
        public PostingCriterionResponseDtoBuilder skillName(String skillName) { this.skillName = skillName; return this; }
        public PostingCriterionResponseDtoBuilder requiredSkillLevel(SkillLevel requiredSkillLevel) { this.requiredSkillLevel = requiredSkillLevel; return this; }
        public PostingCriterionResponseDtoBuilder stringValue(String stringValue) { this.stringValue = stringValue; return this; }
        public PostingCriterionResponseDtoBuilder numericValue(BigDecimal numericValue) { this.numericValue = numericValue; return this; }
        public PostingCriterionResponseDtoBuilder isMandatory(Boolean isMandatory) { this.isMandatory = isMandatory; return this; }
        public PostingCriterionResponseDtoBuilder weight(Integer weight) { this.weight = weight; return this; }

        public PostingCriterionResponseDto build() {
            PostingCriterionResponseDto dto = new PostingCriterionResponseDto();
            dto.id = this.id;
            dto.postingId = this.postingId;
            dto.type = this.type;
            dto.skillId = this.skillId;
            dto.skillName = this.skillName;
            dto.requiredSkillLevel = this.requiredSkillLevel;
            dto.stringValue = this.stringValue;
            dto.numericValue = this.numericValue;
            dto.isMandatory = this.isMandatory;
            dto.weight = this.weight;
            return dto;
        }
    }
}
