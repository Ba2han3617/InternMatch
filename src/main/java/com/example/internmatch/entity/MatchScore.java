package com.example.internmatch.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "match_scores")
public class MatchScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", nullable = false, unique = true)
    private Application application;

    @Column(name = "overall_score", precision = 5, scale = 2, nullable = false)
    private BigDecimal overallScore;

    @Column(name = "skill_score", precision = 5, scale = 2)
    private BigDecimal skillScore;

    @Column(name = "gpa_score", precision = 5, scale = 2)
    private BigDecimal gpaScore;

    @Column(name = "department_score", precision = 5, scale = 2)
    private BigDecimal departmentScore;

    @Column(name = "language_score", precision = 5, scale = 2)
    private BigDecimal languageScore;

    @Column(name = "details_json", columnDefinition = "TEXT")
    private String detailsJson;

    @CreationTimestamp
    @Column(name = "calculated_at", nullable = false, updatable = false)
    private LocalDateTime calculatedAt;

    public MatchScore() {
    }

    public MatchScore(Long id, Application application, BigDecimal overallScore, BigDecimal skillScore, BigDecimal gpaScore, BigDecimal departmentScore, BigDecimal languageScore, String detailsJson, LocalDateTime calculatedAt) {
        this.id = id;
        this.application = application;
        this.overallScore = overallScore;
        this.skillScore = skillScore;
        this.gpaScore = gpaScore;
        this.departmentScore = departmentScore;
        this.languageScore = languageScore;
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

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
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

    public static class MatchScoreBuilder {
        private Long id;
        private Application application;
        private BigDecimal overallScore;
        private BigDecimal skillScore;
        private BigDecimal gpaScore;
        private BigDecimal departmentScore;
        private BigDecimal languageScore;
        private String detailsJson;
        private LocalDateTime calculatedAt;

        MatchScoreBuilder() {
        }

        public MatchScoreBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public MatchScoreBuilder application(Application application) {
            this.application = application;
            return this;
        }

        public MatchScoreBuilder overallScore(BigDecimal overallScore) {
            this.overallScore = overallScore;
            return this;
        }

        public MatchScoreBuilder skillScore(BigDecimal skillScore) {
            this.skillScore = skillScore;
            return this;
        }

        public MatchScoreBuilder gpaScore(BigDecimal gpaScore) {
            this.gpaScore = gpaScore;
            return this;
        }

        public MatchScoreBuilder departmentScore(BigDecimal departmentScore) {
            this.departmentScore = departmentScore;
            return this;
        }

        public MatchScoreBuilder languageScore(BigDecimal languageScore) {
            this.languageScore = languageScore;
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
            return new MatchScore(this.id, this.application, this.overallScore, this.skillScore, this.gpaScore, this.departmentScore, this.languageScore, this.detailsJson, this.calculatedAt);
        }
    }
}
