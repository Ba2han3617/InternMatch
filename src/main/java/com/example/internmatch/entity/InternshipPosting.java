package com.example.internmatch.entity;

import com.example.internmatch.enums.PostingStatus;
import com.example.internmatch.enums.WorkMode;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "internship_postings")
public class InternshipPosting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 150)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    /** Pozisyon adı (ör. "Backend Developer Intern") */
    @Column(name = "position_name", length = 150)
    private String positionName;

    /** Departman (ör. "Software Engineering") */
    @Column(length = 100)
    private String department;

    /** Şehir (ör. "Istanbul") */
    @Column(length = 100)
    private String city;

    /** Eski 'location' alanı: geriye dönük uyumluluk için korundu */
    @Column(length = 150)
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(name = "work_mode", nullable = false, length = 20)
    private WorkMode workMode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PostingStatus status;

    /** Minimum not ortalaması */
    @Column(name = "min_gpa", precision = 3, scale = 2)
    private BigDecimal minGpa;

    /** Eski 'departmentRequirement' alanı: geriye dönük uyumluluk için korundu */
    @Column(name = "department_requirement", length = 100)
    private String departmentRequirement;

    /** Tercih edilen sınıf seviyesi (ör. "3", "4", "Tüm") */
    @Column(name = "preferred_grade_level", length = 50)
    private String preferredGradeLevel;

    /** Staj başlangıç tarihi */
    @Column(name = "start_date")
    private LocalDate startDate;

    /** Staj bitiş tarihi */
    @Column(name = "end_date")
    private LocalDate endDate;

    /** Son başvuru tarihi */
    @Column(name = "application_deadline")
    private LocalDate applicationDeadline;

    /** Kontenjan sayısı */
    @Column(name = "quota")
    private Integer quota;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "posting", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PostingCriterion> criteria = new ArrayList<>();

    @OneToMany(mappedBy = "posting", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Application> applications = new ArrayList<>();

    public InternshipPosting() {
    }

    // ─── Getters & Setters ────────────────────────────────────────────────────────

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getPositionName() { return positionName; }
    public void setPositionName(String positionName) { this.positionName = positionName; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public WorkMode getWorkMode() { return workMode; }
    public void setWorkMode(WorkMode workMode) { this.workMode = workMode; }

    public PostingStatus getStatus() { return status; }
    public void setStatus(PostingStatus status) { this.status = status; }

    public BigDecimal getMinGpa() { return minGpa; }
    public void setMinGpa(BigDecimal minGpa) { this.minGpa = minGpa; }

    public String getDepartmentRequirement() { return departmentRequirement; }
    public void setDepartmentRequirement(String departmentRequirement) { this.departmentRequirement = departmentRequirement; }

    public String getPreferredGradeLevel() { return preferredGradeLevel; }
    public void setPreferredGradeLevel(String preferredGradeLevel) { this.preferredGradeLevel = preferredGradeLevel; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public LocalDate getApplicationDeadline() { return applicationDeadline; }
    public void setApplicationDeadline(LocalDate applicationDeadline) { this.applicationDeadline = applicationDeadline; }

    public Integer getQuota() { return quota; }
    public void setQuota(Integer quota) { this.quota = quota; }

    public Company getCompany() { return company; }
    public void setCompany(Company company) { this.company = company; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public List<PostingCriterion> getCriteria() { return criteria; }
    public void setCriteria(List<PostingCriterion> criteria) { this.criteria = criteria; }

    public List<Application> getApplications() { return applications; }
    public void setApplications(List<Application> applications) { this.applications = applications; }

    // ─── Builder ──────────────────────────────────────────────────────────────────

    public static InternshipPostingBuilder builder() {
        return new InternshipPostingBuilder();
    }

    public static class InternshipPostingBuilder {
        private Long id;
        private String title;
        private String description;
        private String positionName;
        private String department;
        private String city;
        private String location;
        private WorkMode workMode;
        private PostingStatus status;
        private BigDecimal minGpa;
        private String departmentRequirement;
        private String preferredGradeLevel;
        private LocalDate startDate;
        private LocalDate endDate;
        private LocalDate applicationDeadline;
        private Integer quota;
        private Company company;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private List<PostingCriterion> criteria = new ArrayList<>();
        private List<Application> applications = new ArrayList<>();

        InternshipPostingBuilder() {}

        public InternshipPostingBuilder id(Long id) { this.id = id; return this; }
        public InternshipPostingBuilder title(String title) { this.title = title; return this; }
        public InternshipPostingBuilder description(String description) { this.description = description; return this; }
        public InternshipPostingBuilder positionName(String positionName) { this.positionName = positionName; return this; }
        public InternshipPostingBuilder department(String department) { this.department = department; return this; }
        public InternshipPostingBuilder city(String city) { this.city = city; return this; }
        public InternshipPostingBuilder location(String location) { this.location = location; return this; }
        public InternshipPostingBuilder workMode(WorkMode workMode) { this.workMode = workMode; return this; }
        public InternshipPostingBuilder status(PostingStatus status) { this.status = status; return this; }
        public InternshipPostingBuilder minGpa(BigDecimal minGpa) { this.minGpa = minGpa; return this; }
        public InternshipPostingBuilder departmentRequirement(String departmentRequirement) { this.departmentRequirement = departmentRequirement; return this; }
        public InternshipPostingBuilder preferredGradeLevel(String preferredGradeLevel) { this.preferredGradeLevel = preferredGradeLevel; return this; }
        public InternshipPostingBuilder startDate(LocalDate startDate) { this.startDate = startDate; return this; }
        public InternshipPostingBuilder endDate(LocalDate endDate) { this.endDate = endDate; return this; }
        public InternshipPostingBuilder applicationDeadline(LocalDate applicationDeadline) { this.applicationDeadline = applicationDeadline; return this; }
        public InternshipPostingBuilder quota(Integer quota) { this.quota = quota; return this; }
        public InternshipPostingBuilder company(Company company) { this.company = company; return this; }
        public InternshipPostingBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public InternshipPostingBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }
        public InternshipPostingBuilder criteria(List<PostingCriterion> criteria) { this.criteria = criteria; return this; }
        public InternshipPostingBuilder applications(List<Application> applications) { this.applications = applications; return this; }

        public InternshipPosting build() {
            InternshipPosting posting = new InternshipPosting();
            posting.id = this.id;
            posting.title = this.title;
            posting.description = this.description;
            posting.positionName = this.positionName;
            posting.department = this.department;
            posting.city = this.city;
            posting.location = this.location;
            posting.workMode = this.workMode;
            posting.status = this.status;
            posting.minGpa = this.minGpa;
            posting.departmentRequirement = this.departmentRequirement;
            posting.preferredGradeLevel = this.preferredGradeLevel;
            posting.startDate = this.startDate;
            posting.endDate = this.endDate;
            posting.applicationDeadline = this.applicationDeadline;
            posting.quota = this.quota;
            posting.company = this.company;
            posting.createdAt = this.createdAt;
            posting.updatedAt = this.updatedAt;
            posting.criteria = this.criteria != null ? this.criteria : new ArrayList<>();
            posting.applications = this.applications != null ? this.applications : new ArrayList<>();
            return posting;
        }
    }
}
