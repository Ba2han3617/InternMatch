package com.example.internmatch.dto.request;

import com.example.internmatch.enums.WorkMode;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class UpdateStudentProfileRequest {

    @Size(max = 30, message = "Öğrenci numarası en fazla 30 karakter olabilir")
    private String studentNumber;

    @Size(max = 150, message = "Üniversite adı en fazla 150 karakter olabilir")
    private String university;

    @Size(max = 100, message = "Bölüm adı en fazla 100 karakter olabilir")
    private String department;

    @Min(value = 1, message = "Sınıf seviyesi en az 1 olabilir")
    @Max(value = 8, message = "Sınıf seviyesi en fazla 8 olabilir")
    private Integer gradeLevel;

    @DecimalMin(value = "0.00", message = "GPA en az 0.00 olabilir")
    @DecimalMax(value = "4.00", message = "GPA en fazla 4.00 olabilir")
    private BigDecimal gpa;

    @Size(max = 255, message = "CV URL en fazla 255 karakter olabilir")
    private String cvUrl;

    @Size(max = 1000, message = "Biyografi en fazla 1000 karakter olabilir")
    private String summary;

    @Size(max = 255, message = "GitHub URL en fazla 255 karakter olabilir")
    private String githubUrl;

    @Size(max = 255, message = "LinkedIn URL en fazla 255 karakter olabilir")
    private String linkedinUrl;

    @Size(max = 255, message = "Portfolyo URL en fazla 255 karakter olabilir")
    private String portfolioUrl;

    @Size(max = 100, message = "Şehir adı en fazla 100 karakter olabilir")
    private String city;

    @Min(value = 2000, message = "Mezuniyet yılı 2000'den önce olamaz")
    @Max(value = 2035, message = "Mezuniyet yılı 2035'ten sonra olamaz")
    private Integer graduationYear;

    private WorkMode preferredWorkMode;

    public UpdateStudentProfileRequest() {
    }

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
}
