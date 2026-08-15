package com.example.internmatch.dto.response;

import com.example.internmatch.enums.CriterionType;

import java.math.BigDecimal;

public class CriterionResultDetailDto {

    private CriterionType criterionType;
    private String criterionName;
    private BigDecimal weight;
    private Boolean matched;
    private BigDecimal earnedScore;
    private String description;

    public CriterionResultDetailDto() {
    }

    public CriterionResultDetailDto(CriterionType criterionType, String criterionName, BigDecimal weight, Boolean matched, BigDecimal earnedScore, String description) {
        this.criterionType = criterionType;
        this.criterionName = criterionName;
        this.weight = weight;
        this.matched = matched;
        this.earnedScore = earnedScore;
        this.description = description;
    }

    public static CriterionResultDetailDtoBuilder builder() {
        return new CriterionResultDetailDtoBuilder();
    }

    public CriterionType getCriterionType() {
        return criterionType;
    }

    public void setCriterionType(CriterionType criterionType) {
        this.criterionType = criterionType;
    }

    public String getCriterionName() {
        return criterionName;
    }

    public void setCriterionName(String criterionName) {
        this.criterionName = criterionName;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public Boolean getMatched() {
        return matched;
    }

    public void setMatched(Boolean matched) {
        this.matched = matched;
    }

    public BigDecimal getEarnedScore() {
        return earnedScore;
    }

    public void setEarnedScore(BigDecimal earnedScore) {
        this.earnedScore = earnedScore;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static class CriterionResultDetailDtoBuilder {
        private CriterionType criterionType;
        private String criterionName;
        private BigDecimal weight;
        private Boolean matched;
        private BigDecimal earnedScore;
        private String description;

        CriterionResultDetailDtoBuilder() {
        }

        public CriterionResultDetailDtoBuilder criterionType(CriterionType criterionType) {
            this.criterionType = criterionType;
            return this;
        }

        public CriterionResultDetailDtoBuilder criterionName(String criterionName) {
            this.criterionName = criterionName;
            return this;
        }

        public CriterionResultDetailDtoBuilder weight(BigDecimal weight) {
            this.weight = weight;
            return this;
        }

        public CriterionResultDetailDtoBuilder matched(Boolean matched) {
            this.matched = matched;
            return this;
        }

        public CriterionResultDetailDtoBuilder earnedScore(BigDecimal earnedScore) {
            this.earnedScore = earnedScore;
            return this;
        }

        public CriterionResultDetailDtoBuilder description(String description) {
            this.description = description;
            return this;
        }

        public CriterionResultDetailDto build() {
            return new CriterionResultDetailDto(this.criterionType, this.criterionName, this.weight, this.matched, this.earnedScore, this.description);
        }
    }
}
