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

    @Column(length = 150)
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(name = "work_mode", nullable = false, length = 20)
    private WorkMode workMode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PostingStatus status;

    @Column(name = "min_gpa", precision = 3, scale = 2)
    private BigDecimal minGpa;

    @Column(name = "department_requirement", length = 100)
    private String departmentRequirement;

    @Column(name = "application_deadline")
    private LocalDate applicationDeadline;

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

    public InternshipPosting(Long id, String title, String description, String location, WorkMode workMode, PostingStatus status, BigDecimal minGpa, String departmentRequirement, LocalDate applicationDeadline, Company company, LocalDateTime createdAt, LocalDateTime updatedAt, List<PostingCriterion> criteria, List<Application> applications) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.workMode = workMode;
        this.status = status;
        this.minGpa = minGpa;
        this.departmentRequirement = departmentRequirement;
        this.applicationDeadline = applicationDeadline;
        this.company = company;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.criteria = criteria != null ? criteria : new ArrayList<>();
        this.applications = applications != null ? applications : new ArrayList<>();
    }

    public static InternshipPostingBuilder builder() {
        return new InternshipPostingBuilder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public WorkMode getWorkMode() {
        return workMode;
    }

    public void setWorkMode(WorkMode workMode) {
        this.workMode = workMode;
    }

    public PostingStatus getStatus() {
        return status;
    }

    public void setStatus(PostingStatus status) {
        this.status = status;
    }

    public BigDecimal getMinGpa() {
        return minGpa;
    }

    public void setMinGpa(BigDecimal minGpa) {
        this.minGpa = minGpa;
    }

    public String getDepartmentRequirement() {
        return departmentRequirement;
    }

    public void setDepartmentRequirement(String departmentRequirement) {
        this.departmentRequirement = departmentRequirement;
    }

    public LocalDate getApplicationDeadline() {
        return applicationDeadline;
    }

    public void setApplicationDeadline(LocalDate applicationDeadline) {
        this.applicationDeadline = applicationDeadline;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
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

    public List<PostingCriterion> getCriteria() {
        return criteria;
    }

    public void setCriteria(List<PostingCriterion> criteria) {
        this.criteria = criteria;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }

    public static class InternshipPostingBuilder {
        private Long id;
        private String title;
        private String description;
        private String location;
        private WorkMode workMode;
        private PostingStatus status;
        private BigDecimal minGpa;
        private String departmentRequirement;
        private LocalDate applicationDeadline;
        private Company company;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private List<PostingCriterion> criteria = new ArrayList<>();
        private List<Application> applications = new ArrayList<>();

        InternshipPostingBuilder() {
        }

        public InternshipPostingBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public InternshipPostingBuilder title(String title) {
            this.title = title;
            return this;
        }

        public InternshipPostingBuilder description(String description) {
            this.description = description;
            return this;
        }

        public InternshipPostingBuilder location(String location) {
            this.location = location;
            return this;
        }

        public InternshipPostingBuilder workMode(WorkMode workMode) {
            this.workMode = workMode;
            return this;
        }

        public InternshipPostingBuilder status(PostingStatus status) {
            this.status = status;
            return this;
        }

        public InternshipPostingBuilder minGpa(BigDecimal minGpa) {
            this.minGpa = minGpa;
            return this;
        }

        public InternshipPostingBuilder departmentRequirement(String departmentRequirement) {
            this.departmentRequirement = departmentRequirement;
            return this;
        }

        public InternshipPostingBuilder applicationDeadline(LocalDate applicationDeadline) {
            this.applicationDeadline = applicationDeadline;
            return this;
        }

        public InternshipPostingBuilder company(Company company) {
            this.company = company;
            return this;
        }

        public InternshipPostingBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public InternshipPostingBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public InternshipPostingBuilder criteria(List<PostingCriterion> criteria) {
            this.criteria = criteria;
            return this;
        }

        public InternshipPostingBuilder applications(List<Application> applications) {
            this.applications = applications;
            return this;
        }

        public InternshipPosting build() {
            return new InternshipPosting(this.id, this.title, this.description, this.location, this.workMode, this.status, this.minGpa, this.departmentRequirement, this.applicationDeadline, this.company, this.createdAt, this.updatedAt, this.criteria, this.applications);
        }
    }
}
