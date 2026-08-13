package com.example.internmatch.dto.response;

import com.example.internmatch.enums.PostingStatus;
import com.example.internmatch.enums.WorkMode;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "Staj ilanı özet response (liste görünümü)")
public class InternshipPostingSummaryResponseDto {

    @Schema(description = "İlan ID")
    private Long id;

    @Schema(description = "İlan başlığı")
    private String title;

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

    @Schema(description = "Son başvuru tarihi")
    private LocalDate applicationDeadline;

    @Schema(description = "Kontenjan sayısı")
    private Integer quota;

    @Schema(description = "Şirket ID")
    private Long companyId;

    @Schema(description = "Şirket adı")
    private String companyName;

    @Schema(description = "İlan oluşturulma tarihi")
    private LocalDateTime createdAt;

    // ─── Constructors ─────────────────────────────────────────────────────────────

    public InternshipPostingSummaryResponseDto() {}

    // ─── Getters & Setters ────────────────────────────────────────────────────────

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

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

    public LocalDate getApplicationDeadline() { return applicationDeadline; }
    public void setApplicationDeadline(LocalDate applicationDeadline) { this.applicationDeadline = applicationDeadline; }

    public Integer getQuota() { return quota; }
    public void setQuota(Integer quota) { this.quota = quota; }

    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    // ─── Builder ──────────────────────────────────────────────────────────────────

    public static InternshipPostingSummaryResponseDtoBuilder builder() {
        return new InternshipPostingSummaryResponseDtoBuilder();
    }

    public static class InternshipPostingSummaryResponseDtoBuilder {
        private Long id;
        private String title;
        private String positionName;
        private String department;
        private String city;
        private WorkMode workMode;
        private PostingStatus status;
        private BigDecimal minGpa;
        private LocalDate applicationDeadline;
        private Integer quota;
        private Long companyId;
        private String companyName;
        private LocalDateTime createdAt;

        InternshipPostingSummaryResponseDtoBuilder() {}

        public InternshipPostingSummaryResponseDtoBuilder id(Long id) { this.id = id; return this; }
        public InternshipPostingSummaryResponseDtoBuilder title(String title) { this.title = title; return this; }
        public InternshipPostingSummaryResponseDtoBuilder positionName(String positionName) { this.positionName = positionName; return this; }
        public InternshipPostingSummaryResponseDtoBuilder department(String department) { this.department = department; return this; }
        public InternshipPostingSummaryResponseDtoBuilder city(String city) { this.city = city; return this; }
        public InternshipPostingSummaryResponseDtoBuilder workMode(WorkMode workMode) { this.workMode = workMode; return this; }
        public InternshipPostingSummaryResponseDtoBuilder status(PostingStatus status) { this.status = status; return this; }
        public InternshipPostingSummaryResponseDtoBuilder minGpa(BigDecimal minGpa) { this.minGpa = minGpa; return this; }
        public InternshipPostingSummaryResponseDtoBuilder applicationDeadline(LocalDate applicationDeadline) { this.applicationDeadline = applicationDeadline; return this; }
        public InternshipPostingSummaryResponseDtoBuilder quota(Integer quota) { this.quota = quota; return this; }
        public InternshipPostingSummaryResponseDtoBuilder companyId(Long companyId) { this.companyId = companyId; return this; }
        public InternshipPostingSummaryResponseDtoBuilder companyName(String companyName) { this.companyName = companyName; return this; }
        public InternshipPostingSummaryResponseDtoBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public InternshipPostingSummaryResponseDto build() {
            InternshipPostingSummaryResponseDto dto = new InternshipPostingSummaryResponseDto();
            dto.id = this.id;
            dto.title = this.title;
            dto.positionName = this.positionName;
            dto.department = this.department;
            dto.city = this.city;
            dto.workMode = this.workMode;
            dto.status = this.status;
            dto.minGpa = this.minGpa;
            dto.applicationDeadline = this.applicationDeadline;
            dto.quota = this.quota;
            dto.companyId = this.companyId;
            dto.companyName = this.companyName;
            dto.createdAt = this.createdAt;
            return dto;
        }
    }
}
