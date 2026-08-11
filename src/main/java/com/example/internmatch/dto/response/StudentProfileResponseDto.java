package com.example.internmatch.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class StudentProfileResponseDto {
    private Long id;
    private Long userId;
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
    private LocalDateTime createdAt;

    public StudentProfileResponseDto() {
    }

    public StudentProfileResponseDto(Long id, Long userId, String userFullName, String studentNumber, String university, String department, Integer gradeLevel, BigDecimal gpa, String cvUrl, String summary, String githubUrl, String linkedinUrl, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
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
        this.createdAt = createdAt;
    }

    public static StudentProfileResponseDtoBuilder builder() {
        return new StudentProfileResponseDtoBuilder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public static class StudentProfileResponseDtoBuilder {
        private Long id;
        private Long userId;
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
        private LocalDateTime createdAt;

        StudentProfileResponseDtoBuilder() {
        }

        public StudentProfileResponseDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public StudentProfileResponseDtoBuilder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public StudentProfileResponseDtoBuilder userFullName(String userFullName) {
            this.userFullName = userFullName;
            return this;
        }

        public StudentProfileResponseDtoBuilder studentNumber(String studentNumber) {
            this.studentNumber = studentNumber;
            return this;
        }

        public StudentProfileResponseDtoBuilder university(String university) {
            this.university = university;
            return this;
        }

        public StudentProfileResponseDtoBuilder department(String department) {
            this.department = department;
            return this;
        }

        public StudentProfileResponseDtoBuilder gradeLevel(Integer gradeLevel) {
            this.gradeLevel = gradeLevel;
            return this;
        }

        public StudentProfileResponseDtoBuilder gpa(BigDecimal gpa) {
            this.gpa = gpa;
            return this;
        }

        public StudentProfileResponseDtoBuilder cvUrl(String cvUrl) {
            this.cvUrl = cvUrl;
            return this;
        }

        public StudentProfileResponseDtoBuilder summary(String summary) {
            this.summary = summary;
            return this;
        }

        public StudentProfileResponseDtoBuilder githubUrl(String githubUrl) {
            this.githubUrl = githubUrl;
            return this;
        }

        public StudentProfileResponseDtoBuilder linkedinUrl(String linkedinUrl) {
            this.linkedinUrl = linkedinUrl;
            return this;
        }

        public StudentProfileResponseDtoBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public StudentProfileResponseDto build() {
            return new StudentProfileResponseDto(this.id, this.userId, this.userFullName, this.studentNumber, this.university, this.department, this.gradeLevel, this.gpa, this.cvUrl, this.summary, this.githubUrl, this.linkedinUrl, this.createdAt);
        }
    }
}
