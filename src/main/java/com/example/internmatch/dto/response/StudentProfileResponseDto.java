package com.example.internmatch.dto.response;

import com.example.internmatch.enums.WorkMode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class StudentProfileResponseDto {
    private Long id;
    private Long userId;
    private String userEmail;
    private String userFullName;
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

    public StudentProfileResponseDto() {
    }

    public StudentProfileResponseDto(Long id, Long userId, String userEmail, String userFullName,
                                     String studentNumber, String university, String department,
                                     Integer gradeLevel, BigDecimal gpa, String cvUrl, String summary,
                                     String githubUrl, String linkedinUrl, String portfolioUrl,
                                     String city, Integer graduationYear, WorkMode preferredWorkMode,
                                     LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.userEmail = userEmail;
        this.userFullName = userFullName;
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
    }

    public static StudentProfileResponseDtoBuilder builder() {
        return new StudentProfileResponseDtoBuilder();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public String getUserFullName() { return userFullName; }
    public void setUserFullName(String userFullName) { this.userFullName = userFullName; }

    public String getStudentNumber() { return studentNumber; }
    public void setStudentNumber(String studentNumber) { this.studentNumber = studentNumber; }

    public String getUniversity() { return university; }
    public void setUniversity(String university) { this.university = university; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public Integer getGradeLevel() { return gradeLevel; }
    public void setGradeLevel(Integer gradeLevel) { this.gradeLevel = gradeLevel; }

    public BigDecimal getGpa() { return gpa; }
    public void setGpa(BigDecimal gpa) { this.gpa = gpa; }

    public String getCvUrl() { return cvUrl; }
    public void setCvUrl(String cvUrl) { this.cvUrl = cvUrl; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public String getGithubUrl() { return githubUrl; }
    public void setGithubUrl(String githubUrl) { this.githubUrl = githubUrl; }

    public String getLinkedinUrl() { return linkedinUrl; }
    public void setLinkedinUrl(String linkedinUrl) { this.linkedinUrl = linkedinUrl; }

    public String getPortfolioUrl() { return portfolioUrl; }
    public void setPortfolioUrl(String portfolioUrl) { this.portfolioUrl = portfolioUrl; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public Integer getGraduationYear() { return graduationYear; }
    public void setGraduationYear(Integer graduationYear) { this.graduationYear = graduationYear; }

    public WorkMode getPreferredWorkMode() { return preferredWorkMode; }
    public void setPreferredWorkMode(WorkMode preferredWorkMode) { this.preferredWorkMode = preferredWorkMode; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public static class StudentProfileResponseDtoBuilder {
        private Long id;
        private Long userId;
        private String userEmail;
        private String userFullName;
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

        StudentProfileResponseDtoBuilder() {}

        public StudentProfileResponseDtoBuilder id(Long id) { this.id = id; return this; }
        public StudentProfileResponseDtoBuilder userId(Long userId) { this.userId = userId; return this; }
        public StudentProfileResponseDtoBuilder userEmail(String userEmail) { this.userEmail = userEmail; return this; }
        public StudentProfileResponseDtoBuilder userFullName(String userFullName) { this.userFullName = userFullName; return this; }
        public StudentProfileResponseDtoBuilder studentNumber(String studentNumber) { this.studentNumber = studentNumber; return this; }
        public StudentProfileResponseDtoBuilder university(String university) { this.university = university; return this; }
        public StudentProfileResponseDtoBuilder department(String department) { this.department = department; return this; }
        public StudentProfileResponseDtoBuilder gradeLevel(Integer gradeLevel) { this.gradeLevel = gradeLevel; return this; }
        public StudentProfileResponseDtoBuilder gpa(BigDecimal gpa) { this.gpa = gpa; return this; }
        public StudentProfileResponseDtoBuilder cvUrl(String cvUrl) { this.cvUrl = cvUrl; return this; }
        public StudentProfileResponseDtoBuilder summary(String summary) { this.summary = summary; return this; }
        public StudentProfileResponseDtoBuilder githubUrl(String githubUrl) { this.githubUrl = githubUrl; return this; }
        public StudentProfileResponseDtoBuilder linkedinUrl(String linkedinUrl) { this.linkedinUrl = linkedinUrl; return this; }
        public StudentProfileResponseDtoBuilder portfolioUrl(String portfolioUrl) { this.portfolioUrl = portfolioUrl; return this; }
        public StudentProfileResponseDtoBuilder city(String city) { this.city = city; return this; }
        public StudentProfileResponseDtoBuilder graduationYear(Integer graduationYear) { this.graduationYear = graduationYear; return this; }
        public StudentProfileResponseDtoBuilder preferredWorkMode(WorkMode preferredWorkMode) { this.preferredWorkMode = preferredWorkMode; return this; }
        public StudentProfileResponseDtoBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public StudentProfileResponseDtoBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public StudentProfileResponseDto build() {
            return new StudentProfileResponseDto(id, userId, userEmail, userFullName, studentNumber, university,
                    department, gradeLevel, gpa, cvUrl, summary, githubUrl, linkedinUrl, portfolioUrl,
                    city, graduationYear, preferredWorkMode, createdAt, updatedAt);
        }
    }
}
