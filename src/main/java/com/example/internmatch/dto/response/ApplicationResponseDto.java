package com.example.internmatch.dto.response;

import com.example.internmatch.enums.ApplicationStatus;

import java.time.LocalDateTime;

public class ApplicationResponseDto {
    private Long id;
    private Long studentProfileId;
    private String studentName;
    private Long postingId;
    private String postingTitle;
    private String companyName;
    private ApplicationStatus status;
    private LocalDateTime appliedAt;
    private MatchScoreResponseDto matchScore;
    private String notes;

    public ApplicationResponseDto() {
    }

    public ApplicationResponseDto(Long id, Long studentProfileId, String studentName, Long postingId, String postingTitle, String companyName, ApplicationStatus status, LocalDateTime appliedAt, MatchScoreResponseDto matchScore, String notes) {
        this.id = id;
        this.studentProfileId = studentProfileId;
        this.studentName = studentName;
        this.postingId = postingId;
        this.postingTitle = postingTitle;
        this.companyName = companyName;
        this.status = status;
        this.appliedAt = appliedAt;
        this.matchScore = matchScore;
        this.notes = notes;
    }

    public static ApplicationResponseDtoBuilder builder() {
        return new ApplicationResponseDtoBuilder();
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

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public LocalDateTime getAppliedAt() {
        return appliedAt;
    }

    public void setAppliedAt(LocalDateTime appliedAt) {
        this.appliedAt = appliedAt;
    }

    public MatchScoreResponseDto getMatchScore() {
        return matchScore;
    }

    public void setMatchScore(MatchScoreResponseDto matchScore) {
        this.matchScore = matchScore;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public static class ApplicationResponseDtoBuilder {
        private Long id;
        private Long studentProfileId;
        private String studentName;
        private Long postingId;
        private String postingTitle;
        private String companyName;
        private ApplicationStatus status;
        private LocalDateTime appliedAt;
        private MatchScoreResponseDto matchScore;
        private String notes;

        ApplicationResponseDtoBuilder() {
        }

        public ApplicationResponseDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ApplicationResponseDtoBuilder studentProfileId(Long studentProfileId) {
            this.studentProfileId = studentProfileId;
            return this;
        }

        public ApplicationResponseDtoBuilder studentName(String studentName) {
            this.studentName = studentName;
            return this;
        }

        public ApplicationResponseDtoBuilder postingId(Long postingId) {
            this.postingId = postingId;
            return this;
        }

        public ApplicationResponseDtoBuilder postingTitle(String postingTitle) {
            this.postingTitle = postingTitle;
            return this;
        }

        public ApplicationResponseDtoBuilder companyName(String companyName) {
            this.companyName = companyName;
            return this;
        }

        public ApplicationResponseDtoBuilder status(ApplicationStatus status) {
            this.status = status;
            return this;
        }

        public ApplicationResponseDtoBuilder appliedAt(LocalDateTime appliedAt) {
            this.appliedAt = appliedAt;
            return this;
        }

        public ApplicationResponseDtoBuilder matchScore(MatchScoreResponseDto matchScore) {
            this.matchScore = matchScore;
            return this;
        }

        public ApplicationResponseDtoBuilder notes(String notes) {
            this.notes = notes;
            return this;
        }

        public ApplicationResponseDto build() {
            return new ApplicationResponseDto(this.id, this.studentProfileId, this.studentName, this.postingId, this.postingTitle, this.companyName, this.status, this.appliedAt, this.matchScore, this.notes);
        }
    }
}
