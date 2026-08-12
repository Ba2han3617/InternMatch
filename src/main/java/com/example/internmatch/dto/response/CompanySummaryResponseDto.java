package com.example.internmatch.dto.response;

import com.example.internmatch.enums.CompanyVerificationStatus;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Şirket listesi için hafif özet yanıtı.
 * Tüm kullanıcılar tarafından görüntülenebilir.
 */
@Schema(description = "Şirket özet bilgisi (liste görünümü için)")
public class CompanySummaryResponseDto {

    @Schema(description = "Şirket ID")
    private Long id;

    @Schema(description = "Şirket adı")
    private String name;

    @Schema(description = "Sektör")
    private String industry;

    @Schema(description = "Şehir")
    private String city;

    @Schema(description = "Doğrulama durumu")
    private CompanyVerificationStatus verificationStatus;

    @Schema(description = "Aktif/pasif durumu")
    private Boolean isActive;

    @Schema(description = "Web sitesi")
    private String website;

    // ─── Constructors ─────────────────────────────────────────────────────────────

    public CompanySummaryResponseDto() {}

    public CompanySummaryResponseDto(Long id, String name, String industry, String city,
                                      CompanyVerificationStatus verificationStatus,
                                      Boolean isActive, String website) {
        this.id = id;
        this.name = name;
        this.industry = industry;
        this.city = city;
        this.verificationStatus = verificationStatus;
        this.isActive = isActive;
        this.website = website;
    }

    // ─── Builder ──────────────────────────────────────────────────────────────────

    public static CompanySummaryResponseDtoBuilder builder() {
        return new CompanySummaryResponseDtoBuilder();
    }

    // ─── Getters & Setters ────────────────────────────────────────────────────────

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getIndustry() { return industry; }
    public void setIndustry(String industry) { this.industry = industry; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public CompanyVerificationStatus getVerificationStatus() { return verificationStatus; }
    public void setVerificationStatus(CompanyVerificationStatus verificationStatus) { this.verificationStatus = verificationStatus; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }

    // ─── Inner Builder Class ──────────────────────────────────────────────────────

    public static class CompanySummaryResponseDtoBuilder {
        private Long id;
        private String name;
        private String industry;
        private String city;
        private CompanyVerificationStatus verificationStatus;
        private Boolean isActive;
        private String website;

        CompanySummaryResponseDtoBuilder() {}

        public CompanySummaryResponseDtoBuilder id(Long id) { this.id = id; return this; }
        public CompanySummaryResponseDtoBuilder name(String name) { this.name = name; return this; }
        public CompanySummaryResponseDtoBuilder industry(String industry) { this.industry = industry; return this; }
        public CompanySummaryResponseDtoBuilder city(String city) { this.city = city; return this; }
        public CompanySummaryResponseDtoBuilder verificationStatus(CompanyVerificationStatus vs) { this.verificationStatus = vs; return this; }
        public CompanySummaryResponseDtoBuilder isActive(Boolean isActive) { this.isActive = isActive; return this; }
        public CompanySummaryResponseDtoBuilder website(String website) { this.website = website; return this; }

        public CompanySummaryResponseDto build() {
            return new CompanySummaryResponseDto(id, name, industry, city, verificationStatus, isActive, website);
        }
    }
}
