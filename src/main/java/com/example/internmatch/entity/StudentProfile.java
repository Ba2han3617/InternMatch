package com.example.internmatch.entity;

import com.example.internmatch.enums.WorkMode;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "student_profiles")
public class StudentProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "student_number", length = 30)
    private String studentNumber;

    @Column(length = 150)
    private String university;

    @Column(length = 100)
    private String department;

    @Column(name = "grade_level")
    private Integer gradeLevel;

    @Column(precision = 3, scale = 2)
    private BigDecimal gpa;

    @Column(name = "cv_url", length = 255)
    private String cvUrl;

    @Column(columnDefinition = "TEXT")
    private String summary;

    @Column(name = "github_url", length = 255)
    private String githubUrl;

    @Column(name = "linkedin_url", length = 255)
    private String linkedinUrl;

    @Column(name = "portfolio_url", length = 255)
    private String portfolioUrl;

    @Column(length = 100)
    private String city;

    @Column(name = "graduation_year")
    private Integer graduationYear;

    @Enumerated(EnumType.STRING)
    @Column(name = "preferred_work_mode", length = 20)
    private WorkMode preferredWorkMode;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "studentProfile", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<StudentSkill> studentSkills = new ArrayList<>();

    @OneToMany(mappedBy = "studentProfile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Application> applications = new ArrayList<>();

    public StudentProfile() {
    }

    public StudentProfile(Long id, User user, String studentNumber, String university, String department, Integer gradeLevel, BigDecimal gpa, String cvUrl, String summary, String githubUrl, String linkedinUrl, String portfolioUrl, String city, Integer graduationYear, WorkMode preferredWorkMode, LocalDateTime createdAt, LocalDateTime updatedAt, List<StudentSkill> studentSkills, List<Application> applications) {
        this.id = id;
        this.user = user;
        this.studentNumber = studentNumber;
        this.university = university;
        this.department = department;
        this.gradeLevel = gradeLevel;
        this.gpa = gpa;
        this.cvUrl = cvUrl;
        this.summary = summary;
        this.githubUrl = githubUrl;
        this.linkedinUrl = linkedinUrl;
        this.portfolioUrl = portfolioUrl;
        this.city = city;
        this.graduationYear = graduationYear;
        this.preferredWorkMode = preferredWorkMode;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.studentSkills = studentSkills != null ? studentSkills : new ArrayList<>();
        this.applications = applications != null ? applications : new ArrayList<>();
    }

    public static StudentProfileBuilder builder() {
        return new StudentProfileBuilder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(Integer gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public BigDecimal getGpa() {
        return gpa;
    }

    public void setGpa(BigDecimal gpa) {
        this.gpa = gpa;
    }

    public String getCvUrl() {
        return cvUrl;
    }

    public void setCvUrl(String cvUrl) {
        this.cvUrl = cvUrl;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getGithubUrl() {
        return githubUrl;
    }

    public void setGithubUrl(String githubUrl) {
        this.githubUrl = githubUrl;
    }

    public String getLinkedinUrl() {
        return linkedinUrl;
    }

    public void setLinkedinUrl(String linkedinUrl) {
        this.linkedinUrl = linkedinUrl;
    }

    public String getPortfolioUrl() {
        return portfolioUrl;
    }

    public void setPortfolioUrl(String portfolioUrl) {
        this.portfolioUrl = portfolioUrl;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getGraduationYear() {
        return graduationYear;
    }

    public void setGraduationYear(Integer graduationYear) {
        this.graduationYear = graduationYear;
    }

    public WorkMode getPreferredWorkMode() {
        return preferredWorkMode;
    }

    public void setPreferredWorkMode(WorkMode preferredWorkMode) {
        this.preferredWorkMode = preferredWorkMode;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<StudentSkill> getStudentSkills() {
        return studentSkills;
    }

    public void setStudentSkills(List<StudentSkill> studentSkills) {
        this.studentSkills = studentSkills;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }

    public static class StudentProfileBuilder {
        private Long id;
        private User user;
        private String studentNumber;
        private String university;
        private String department;
        private Integer gradeLevel;
        private BigDecimal gpa;
        private String cvUrl;
        private String summary;
        private String githubUrl;
        private String linkedinUrl;
        private String portfolioUrl;
        private String city;
        private Integer graduationYear;
        private WorkMode preferredWorkMode;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private List<StudentSkill> studentSkills = new ArrayList<>();
        private List<Application> applications = new ArrayList<>();

        StudentProfileBuilder() {
        }

        public StudentProfileBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public StudentProfileBuilder user(User user) {
            this.user = user;
            return this;
        }

        public StudentProfileBuilder studentNumber(String studentNumber) {
            this.studentNumber = studentNumber;
            return this;
        }

        public StudentProfileBuilder university(String university) {
            this.university = university;
            return this;
        }

        public StudentProfileBuilder department(String department) {
            this.department = department;
            return this;
        }

        public StudentProfileBuilder gradeLevel(Integer gradeLevel) {
            this.gradeLevel = gradeLevel;
            return this;
        }

        public StudentProfileBuilder gpa(BigDecimal gpa) {
            this.gpa = gpa;
            return this;
        }

        public StudentProfileBuilder cvUrl(String cvUrl) {
            this.cvUrl = cvUrl;
            return this;
        }

        public StudentProfileBuilder summary(String summary) {
            this.summary = summary;
            return this;
        }

        public StudentProfileBuilder githubUrl(String githubUrl) {
            this.githubUrl = githubUrl;
            return this;
        }

        public StudentProfileBuilder linkedinUrl(String linkedinUrl) {
            this.linkedinUrl = linkedinUrl;
            return this;
        }

        public StudentProfileBuilder portfolioUrl(String portfolioUrl) {
            this.portfolioUrl = portfolioUrl;
            return this;
        }

        public StudentProfileBuilder city(String city) {
            this.city = city;
            return this;
        }

        public StudentProfileBuilder graduationYear(Integer graduationYear) {
            this.graduationYear = graduationYear;
            return this;
        }

        public StudentProfileBuilder preferredWorkMode(WorkMode preferredWorkMode) {
            this.preferredWorkMode = preferredWorkMode;
            return this;
        }

        public StudentProfileBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public StudentProfileBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public StudentProfileBuilder studentSkills(List<StudentSkill> studentSkills) {
            this.studentSkills = studentSkills;
            return this;
        }

        public StudentProfileBuilder applications(List<Application> applications) {
            this.applications = applications;
            return this;
        }

        public StudentProfile build() {
            return new StudentProfile(this.id, this.user, this.studentNumber, this.university, this.department, this.gradeLevel, this.gpa, this.cvUrl, this.summary, this.githubUrl, this.linkedinUrl, this.portfolioUrl, this.city, this.graduationYear, this.preferredWorkMode, this.createdAt, this.updatedAt, this.studentSkills, this.applications);
        }
    }
}
