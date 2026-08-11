package com.example.internmatch.dto.response;

import com.example.internmatch.enums.PostingStatus;
import com.example.internmatch.enums.WorkMode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class InternshipPostingResponseDto {
    private Long id;
    private String title;
    private String description;
    private String location;
    private WorkMode workMode;
    private PostingStatus status;
    private BigDecimal minGpa;
    private String departmentRequirement;
    private LocalDate applicationDeadline;
    private Long companyId;
    private String companyName;
    private LocalDateTime createdAt;

    public InternshipPostingResponseDto() {
    }

    public InternshipPostingResponseDto(Long id, String title, String description, String location, WorkMode workMode, PostingStatus status, BigDecimal minGpa, String departmentRequirement, LocalDate applicationDeadline, Long companyId, String companyName, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.workMode = workMode;
        this.status = status;
        this.minGpa = minGpa;
        this.departmentRequirement = departmentRequirement;
        this.applicationDeadline = applicationDeadline;
        this.companyId = companyId;
        this.companyName = companyName;
        this.createdAt = createdAt;
    }

    public static InternshipPostingResponseDtoBuilder builder() {
        return new InternshipPostingResponseDtoBuilder();
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

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public static class InternshipPostingResponseDtoBuilder {
        private Long id;
        private String title;
        private String description;
        private String location;
        private WorkMode workMode;
        private PostingStatus status;
        private BigDecimal minGpa;
        private String departmentRequirement;
        private LocalDate applicationDeadline;
        private Long companyId;
        private String companyName;
        private LocalDateTime createdAt;

        InternshipPostingResponseDtoBuilder() {
        }

        public InternshipPostingResponseDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public InternshipPostingResponseDtoBuilder title(String title) {
            this.title = title;
            return this;
        }

        public InternshipPostingResponseDtoBuilder description(String description) {
            this.description = description;
            return this;
        }

        public InternshipPostingResponseDtoBuilder location(String location) {
            this.location = location;
            return this;
        }

        public InternshipPostingResponseDtoBuilder workMode(WorkMode workMode) {
            this.workMode = workMode;
            return this;
        }

        public InternshipPostingResponseDtoBuilder status(PostingStatus status) {
            this.status = status;
            return this;
        }

        public InternshipPostingResponseDtoBuilder minGpa(BigDecimal minGpa) {
            this.minGpa = minGpa;
            return this;
        }

        public InternshipPostingResponseDtoBuilder departmentRequirement(String departmentRequirement) {
            this.departmentRequirement = departmentRequirement;
            return this;
        }

        public InternshipPostingResponseDtoBuilder applicationDeadline(LocalDate applicationDeadline) {
            this.applicationDeadline = applicationDeadline;
            return this;
        }

        public InternshipPostingResponseDtoBuilder companyId(Long companyId) {
            this.companyId = companyId;
            return this;
        }

        public InternshipPostingResponseDtoBuilder companyName(String companyName) {
            this.companyName = companyName;
            return this;
        }

        public InternshipPostingResponseDtoBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public InternshipPostingResponseDto build() {
            return new InternshipPostingResponseDto(this.id, this.title, this.description, this.location, this.workMode, this.status, this.minGpa, this.departmentRequirement, this.applicationDeadline, this.companyId, this.companyName, this.createdAt);
        }
    }
}
