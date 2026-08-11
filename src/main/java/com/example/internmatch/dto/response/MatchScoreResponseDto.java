package com.example.internmatch.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MatchScoreResponseDto {
    private Long id;
    private Long applicationId;
    private BigDecimal overallScore;
    private BigDecimal skillScore;
    private BigDecimal gpaScore;
    private BigDecimal departmentScore;
    private BigDecimal languageScore;
    private String detailsJson;
    private LocalDateTime calculatedAt;

    public MatchScoreResponseDto() {
    }

    public MatchScoreResponseDto(Long id, Long applicationId, BigDecimal overallScore, BigDecimal skillScore, BigDecimal gpaScore, BigDecimal departmentScore, BigDecimal languageScore, String detailsJson, LocalDateTime calculatedAt) {
        this.id = id;
        this.applicationId = applicationId;
        this.overallScore = overallScore;
        this.skillScore = skillScore;
        this.gpaScore = gpaScore;
        this.departmentScore = departmentScore;
        this.languageScore = languageScore;
        this.detailsJson = detailsJson;
        this.calculatedAt = calculatedAt;
    }

    public static MatchScoreResponseDtoBuilder builder() {
        return new MatchScoreResponseDtoBuilder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public BigDecimal getOverallScore() {
        return overallScore;
    }

    public void setOverallScore(BigDecimal overallScore) {
        this.overallScore = overallScore;
    }

    public BigDecimal getSkillScore() {
        return skillScore;
    }

    public void setSkillScore(BigDecimal skillScore) {
        this.skillScore = skillScore;
    }

    public BigDecimal getGpaScore() {
        return gpaScore;
    }

    public void setGpaScore(BigDecimal gpaScore) {
        this.gpaScore = gpaScore;
    }

    public BigDecimal getDepartmentScore() {
        return departmentScore;
    }

    public void setDepartmentScore(BigDecimal departmentScore) {
        this.departmentScore = departmentScore;
    }

    public BigDecimal getLanguageScore() {
        return languageScore;
    }

    public void setLanguageScore(BigDecimal languageScore) {
        this.languageScore = languageScore;
    }

    public String getDetailsJson() {
        return detailsJson;
    }

    public void setDetailsJson(String detailsJson) {
        this.detailsJson = detailsJson;
    }

    public LocalDateTime getCalculatedAt() {
        return calculatedAt;
    }

    public void setCalculatedAt(LocalDateTime calculatedAt) {
        this.calculatedAt = calculatedAt;
    }

    public static class MatchScoreResponseDtoBuilder {
        private Long id;
        private Long applicationId;
        private BigDecimal overallScore;
        private BigDecimal skillScore;
        private BigDecimal gpaScore;
        private BigDecimal departmentScore;
        private BigDecimal languageScore;
        private String detailsJson;
        private LocalDateTime calculatedAt;

        MatchScoreResponseDtoBuilder() {
        }

        public MatchScoreResponseDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public MatchScoreResponseDtoBuilder applicationId(Long applicationId) {
            this.applicationId = applicationId;
            return this;
        }

        public MatchScoreResponseDtoBuilder overallScore(BigDecimal overallScore) {
            this.overallScore = overallScore;
            return this;
        }

        public MatchScoreResponseDtoBuilder skillScore(BigDecimal skillScore) {
            this.skillScore = skillScore;
            return this;
        }

        public MatchScoreResponseDtoBuilder gpaScore(BigDecimal gpaScore) {
            this.gpaScore = gpaScore;
            return this;
        }

        public MatchScoreResponseDtoBuilder departmentScore(BigDecimal departmentScore) {
            this.departmentScore = departmentScore;
            return this;
        }

        public MatchScoreResponseDtoBuilder languageScore(BigDecimal languageScore) {
            this.languageScore = languageScore;
            return this;
        }

        public MatchScoreResponseDtoBuilder detailsJson(String detailsJson) {
            this.detailsJson = detailsJson;
            return this;
        }

        public MatchScoreResponseDtoBuilder calculatedAt(LocalDateTime calculatedAt) {
            this.calculatedAt = calculatedAt;
            return this;
        }

        public MatchScoreResponseDto build() {
            return new MatchScoreResponseDto(this.id, this.applicationId, this.overallScore, this.skillScore, this.gpaScore, this.departmentScore, this.languageScore, this.detailsJson, this.calculatedAt);
        }
    }
}
