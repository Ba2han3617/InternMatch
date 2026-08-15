package com.example.internmatch.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "match_scores", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"student_profile_id", "posting_id"})
})
public class MatchScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_profile_id", nullable = false)
    private StudentProfile studentProfile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posting_id", nullable = false)
    private InternshipPosting internshipPosting;

    @Column(name = "total_score", precision = 5, scale = 2, nullable = false)
    private BigDecimal totalScore;

    @Column(name = "matched_criteria_count", nullable = false)
    private Integer matchedCriteriaCount;

    @Column(name = "total_criteria_count", nullable = false)
    private Integer totalCriteriaCount;

    @Column(name = "details_json", columnDefinition = "TEXT")
    private String detailsJson;

    @CreationTimestamp
    @Column(name = "calculated_at", nullable = false)
    private LocalDateTime calculatedAt;

    public MatchScore() {
    }

    public MatchScore(Long id, StudentProfile studentProfile, InternshipPosting internshipPosting, BigDecimal totalScore, Integer matchedCriteriaCount, Integer totalCriteriaCount, String detailsJson, LocalDateTime calculatedAt) {
        this.id = id;
        this.studentProfile = studentProfile;
        this.internshipPosting = internshipPosting;
        this.totalScore = totalScore;
        this.matchedCriteriaCount = matchedCriteriaCount;
        this.totalCriteriaCount = totalCriteriaCount;
        this.detailsJson = detailsJson;
        this.calculatedAt = calculatedAt;
    }

    public static MatchScoreBuilder builder() {
        return new MatchScoreBuilder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StudentProfile getStudentProfile() {
        return studentProfile;
    }

    public void setStudentProfile(StudentProfile studentProfile) {
        this.studentProfile = studentProfile;
    }

    public InternshipPosting getInternshipPosting() {
        return internshipPosting;
    }

    public void setInternshipPosting(InternshipPosting internshipPosting) {
        this.internshipPosting = internshipPosting;
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

    public LocalDateTime getCalculatedAt() {
        return calculatedAt;
    }

    public void setCalculatedAt(LocalDateTime calculatedAt) {
        this.calculatedAt = calculatedAt;
    }

    public static class MatchScoreBuilder {
        private Long id;
        private StudentProfile studentProfile;
        private InternshipPosting internshipPosting;
        private BigDecimal totalScore;
        private Integer matchedCriteriaCount;
        private Integer totalCriteriaCount;
        private String detailsJson;
        private LocalDateTime calculatedAt;

        MatchScoreBuilder() {
        }

        public MatchScoreBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public MatchScoreBuilder studentProfile(StudentProfile studentProfile) {
            this.studentProfile = studentProfile;
            return this;
        }

        public MatchScoreBuilder internshipPosting(InternshipPosting internshipPosting) {
            this.internshipPosting = internshipPosting;
            return this;
        }

        public MatchScoreBuilder totalScore(BigDecimal totalScore) {
            this.totalScore = totalScore;
            return this;
        }

        public MatchScoreBuilder matchedCriteriaCount(Integer matchedCriteriaCount) {
            this.matchedCriteriaCount = matchedCriteriaCount;
            return this;
        }

        public MatchScoreBuilder totalCriteriaCount(Integer totalCriteriaCount) {
            this.totalCriteriaCount = totalCriteriaCount;
            return this;
        }

        public MatchScoreBuilder detailsJson(String detailsJson) {
            this.detailsJson = detailsJson;
            return this;
        }

        public MatchScoreBuilder calculatedAt(LocalDateTime calculatedAt) {
            this.calculatedAt = calculatedAt;
            return this;
        }

        public MatchScore build() {
            return new MatchScore(this.id, this.studentProfile, this.internshipPosting, this.totalScore, this.matchedCriteriaCount, this.totalCriteriaCount, this.detailsJson, this.calculatedAt);
        }
    }
}

