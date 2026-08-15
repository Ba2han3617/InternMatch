package com.example.internmatch.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class MatchScoreResponseDto {

    private Long id;
    private Long studentProfileId;
    private String studentName;
    private Long postingId;
    private String postingTitle;
    private String companyName;
    private BigDecimal totalScore;
    private Integer matchedCriteriaCount;
    private Integer totalCriteriaCount;
    private String detailsJson;
    private List<CriterionResultDetailDto> details;
    private LocalDateTime calculatedAt;

    public MatchScoreResponseDto() {
    }

    public MatchScoreResponseDto(Long id, Long studentProfileId, String studentName, Long postingId, String postingTitle, String companyName, BigDecimal totalScore, Integer matchedCriteriaCount, Integer totalCriteriaCount, String detailsJson, List<CriterionResultDetailDto> details, LocalDateTime calculatedAt) {
        this.id = id;
        this.studentProfileId = studentProfileId;
        this.studentName = studentName;
        this.postingId = postingId;
        this.postingTitle = postingTitle;
        this.companyName = companyName;
        this.totalScore = totalScore;
        this.matchedCriteriaCount = matchedCriteriaCount;
        this.totalCriteriaCount = totalCriteriaCount;
        this.detailsJson = detailsJson;
        this.details = details;
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

    public Long getStudentProfileId() {
        return studentProfileId;
    }

    public void setStudentProfileId(Long studentProfileId) {
        this.studentProfileId = studentProfileId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Long getPostingId() {
        return postingId;
    }

    public void setPostingId(Long postingId) {
        this.postingId = postingId;
    }

    public String getPostingTitle() {
        return postingTitle;
    }

    public void setPostingTitle(String postingTitle) {
        this.postingTitle = postingTitle;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public BigDecimal getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(BigDecimal totalScore) {
        this.totalScore = totalScore;
    }

    public Integer getMatchedCriteriaCount() {
        return matchedCriteriaCount;
    }

    public void setMatchedCriteriaCount(Integer matchedCriteriaCount) {
        this.matchedCriteriaCount = matchedCriteriaCount;
    }

    public Integer getTotalCriteriaCount() {
        return totalCriteriaCount;
    }

    public void setTotalCriteriaCount(Integer totalCriteriaCount) {
        this.totalCriteriaCount = totalCriteriaCount;
    }

    public String getDetailsJson() {
        return detailsJson;
    }

    public void setDetailsJson(String detailsJson) {
        this.detailsJson = detailsJson;
    }

    public List<CriterionResultDetailDto> getDetails() {
        return details;
    }

    public void setDetails(List<CriterionResultDetailDto> details) {
        this.details = details;
    }

    public LocalDateTime getCalculatedAt() {
        return calculatedAt;
    }

    public void setCalculatedAt(LocalDateTime calculatedAt) {
        this.calculatedAt = calculatedAt;
    }

    public static class MatchScoreResponseDtoBuilder {
        private Long id;
        private Long studentProfileId;
        private String studentName;
        private Long postingId;
        private String postingTitle;
        private String companyName;
        private BigDecimal totalScore;
        private Integer matchedCriteriaCount;
        private Integer totalCriteriaCount;
        private String detailsJson;
        private List<CriterionResultDetailDto> details;
        private LocalDateTime calculatedAt;

        MatchScoreResponseDtoBuilder() {
        }

        public MatchScoreResponseDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public MatchScoreResponseDtoBuilder studentProfileId(Long studentProfileId) {
            this.studentProfileId = studentProfileId;
            return this;
        }

        public MatchScoreResponseDtoBuilder studentName(String studentName) {
            this.studentName = studentName;
            return this;
        }

        public MatchScoreResponseDtoBuilder postingId(Long postingId) {
            this.postingId = postingId;
            return this;
        }

        public MatchScoreResponseDtoBuilder postingTitle(String postingTitle) {
            this.postingTitle = postingTitle;
            return this;
        }

        public MatchScoreResponseDtoBuilder companyName(String companyName) {
            this.companyName = companyName;
            return this;
        }

        public MatchScoreResponseDtoBuilder totalScore(BigDecimal totalScore) {
            this.totalScore = totalScore;
            return this;
        }

        public MatchScoreResponseDtoBuilder matchedCriteriaCount(Integer matchedCriteriaCount) {
            this.matchedCriteriaCount = matchedCriteriaCount;
            return this;
        }

        public MatchScoreResponseDtoBuilder totalCriteriaCount(Integer totalCriteriaCount) {
            this.totalCriteriaCount = totalCriteriaCount;
            return this;
        }

        public MatchScoreResponseDtoBuilder detailsJson(String detailsJson) {
            this.detailsJson = detailsJson;
            return this;
        }

        public MatchScoreResponseDtoBuilder details(List<CriterionResultDetailDto> details) {
            this.details = details;
            return this;
        }

        public MatchScoreResponseDtoBuilder calculatedAt(LocalDateTime calculatedAt) {
            this.calculatedAt = calculatedAt;
            return this;
        }

        public MatchScoreResponseDto build() {
            return new MatchScoreResponseDto(this.id, this.studentProfileId, this.studentName, this.postingId, this.postingTitle, this.companyName, this.totalScore, this.matchedCriteriaCount, this.totalCriteriaCount, this.detailsJson, this.details, this.calculatedAt);
        }
    }
}

