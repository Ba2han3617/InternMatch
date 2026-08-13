package com.example.internmatch.dto.response;

import com.example.internmatch.enums.PostingStatus;
import com.example.internmatch.enums.WorkMode;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "Staj ilanı tam detay response")
public class InternshipPostingResponseDto {

    @Schema(description = "İlan ID")
    private Long id;

    @Schema(description = "İlan başlığı")
    private String title;

    @Schema(description = "İlan açıklaması")
    private String description;

    @Schema(description = "Pozisyon adı")
    private String positionName;

    @Schema(description = "Departman")
    private String department;

    @Schema(description = "Şehir")
    private String city;

    @Schema(description = "Çalışma modeli")
    private WorkMode workMode;

    @Schema(description = "İlan durumu")
    private PostingStatus status;

    @Schema(description = "Minimum GPA")
    private BigDecimal minGpa;

    @Schema(description = "Tercih edilen sınıf seviyesi")
    private String preferredGradeLevel;

    @Schema(description = "Staj başlangıç tarihi")
    private LocalDate startDate;

    @Schema(description = "Staj bitiş tarihi")
    private LocalDate endDate;

    @Schema(description = "Son başvuru tarihi")
    private LocalDate applicationDeadline;

    @Schema(description = "Kontenjan sayısı")
    private Integer quota;

    @Schema(description = "Şirket ID")
    private Long companyId;

    @Schema(description = "Şirket adı")
    private String companyName;

    @Schema(description = "Şirket sektörü")
    private String companyIndustry;

    @Schema(description = "Şirket şehri")
    private String companyCity;

    @Schema(description = "İlan oluşturulma tarihi")
    private LocalDateTime createdAt;

    @Schema(description = "İlan güncellenme tarihi")
    private LocalDateTime updatedAt;

    // ─── Constructors ─────────────────────────────────────────────────────────────

    public InternshipPostingResponseDto() {}

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

    public WorkMode getWorkMode() { return workMode; }
    public void setWorkMode(WorkMode workMode) { this.workMode = workMode; }

    public PostingStatus getStatus() { return status; }
    public void setStatus(PostingStatus status) { this.status = status; }

    public BigDecimal getMinGpa() { return minGpa; }
    public void setMinGpa(BigDecimal minGpa) { this.minGpa = minGpa; }

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

    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getCompanyIndustry() { return companyIndustry; }
    public void setCompanyIndustry(String companyIndustry) { this.companyIndustry = companyIndustry; }

    public String getCompanyCity() { return companyCity; }
    public void setCompanyCity(String companyCity) { this.companyCity = companyCity; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    // ─── Builder ──────────────────────────────────────────────────────────────────

    public static InternshipPostingResponseDtoBuilder builder() {
        return new InternshipPostingResponseDtoBuilder();
    }

    public static class InternshipPostingResponseDtoBuilder {
        private Long id;
        private String title;
        private String description;
        private String positionName;
        private String department;
        private String city;
        private WorkMode workMode;
        private PostingStatus status;
        private BigDecimal minGpa;
        private String preferredGradeLevel;
        private LocalDate startDate;
        private LocalDate endDate;
        private LocalDate applicationDeadline;
        private Integer quota;
        private Long companyId;
        private String companyName;
        private String companyIndustry;
        private String companyCity;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        InternshipPostingResponseDtoBuilder() {}

        public InternshipPostingResponseDtoBuilder id(Long id) { this.id = id; return this; }
        public InternshipPostingResponseDtoBuilder title(String title) { this.title = title; return this; }
        public InternshipPostingResponseDtoBuilder description(String description) { this.description = description; return this; }
        public InternshipPostingResponseDtoBuilder positionName(String positionName) { this.positionName = positionName; return this; }
        public InternshipPostingResponseDtoBuilder department(String department) { this.department = department; return this; }
        public InternshipPostingResponseDtoBuilder city(String city) { this.city = city; return this; }
        public InternshipPostingResponseDtoBuilder workMode(WorkMode workMode) { this.workMode = workMode; return this; }
        public InternshipPostingResponseDtoBuilder status(PostingStatus status) { this.status = status; return this; }
        public InternshipPostingResponseDtoBuilder minGpa(BigDecimal minGpa) { this.minGpa = minGpa; return this; }
        public InternshipPostingResponseDtoBuilder preferredGradeLevel(String preferredGradeLevel) { this.preferredGradeLevel = preferredGradeLevel; return this; }
        public InternshipPostingResponseDtoBuilder startDate(LocalDate startDate) { this.startDate = startDate; return this; }
        public InternshipPostingResponseDtoBuilder endDate(LocalDate endDate) { this.endDate = endDate; return this; }
        public InternshipPostingResponseDtoBuilder applicationDeadline(LocalDate applicationDeadline) { this.applicationDeadline = applicationDeadline; return this; }
        public InternshipPostingResponseDtoBuilder quota(Integer quota) { this.quota = quota; return this; }
        public InternshipPostingResponseDtoBuilder companyId(Long companyId) { this.companyId = companyId; return this; }
        public InternshipPostingResponseDtoBuilder companyName(String companyName) { this.companyName = companyName; return this; }
        public InternshipPostingResponseDtoBuilder companyIndustry(String companyIndustry) { this.companyIndustry = companyIndustry; return this; }
        public InternshipPostingResponseDtoBuilder companyCity(String companyCity) { this.companyCity = companyCity; return this; }
        public InternshipPostingResponseDtoBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public InternshipPostingResponseDtoBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public InternshipPostingResponseDto build() {
            InternshipPostingResponseDto dto = new InternshipPostingResponseDto();
            dto.id = this.id;
            dto.title = this.title;
            dto.description = this.description;
            dto.positionName = this.positionName;
            dto.department = this.department;
            dto.city = this.city;
            dto.workMode = this.workMode;
            dto.status = this.status;
            dto.minGpa = this.minGpa;
            dto.preferredGradeLevel = this.preferredGradeLevel;
            dto.startDate = this.startDate;
            dto.endDate = this.endDate;
            dto.applicationDeadline = this.applicationDeadline;
            dto.quota = this.quota;
            dto.companyId = this.companyId;
            dto.companyName = this.companyName;
            dto.companyIndustry = this.companyIndustry;
            dto.companyCity = this.companyCity;
            dto.createdAt = this.createdAt;
            dto.updatedAt = this.updatedAt;
            return dto;
        }
    }
}
