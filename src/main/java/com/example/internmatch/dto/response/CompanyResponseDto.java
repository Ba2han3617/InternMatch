package com.example.internmatch.dto.response;

import com.example.internmatch.enums.CompanyVerificationStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * Şirket profili tam detay yanıtı.
 * ROLE_COMPANY (kendi şirketi) ve ROLE_ADMIN tarafından görüntülenir.
 */
@Schema(description = "Şirket profili detay yanıtı")
public class CompanyResponseDto {

    @Schema(description = "Şirket ID")
    private Long id;

    @Schema(description = "Şirket adı")
    private String name;

    @Schema(description = "Vergi numarası")
    private String taxNumber;

    @Schema(description = "Şirket açıklaması")
    private String description;

    @Schema(description = "Web sitesi")
    private String website;

    @Schema(description = "Sektör")
    private String industry;

    @Schema(description = "Şehir")
    private String city;

    @Schema(description = "Bölge/ilçe")
    private String location;

    @Schema(description = "Tam adres")
    private String address;

    @Schema(description = "İletişim e-postası")
    private String contactEmail;

    @Schema(description = "İletişim telefonu")
    private String contactPhone;

    @Schema(description = "Doğrulama durumu")
    private CompanyVerificationStatus verificationStatus;

    @Schema(description = "Aktif/pasif durumu")
    private Boolean isActive;

    @Schema(description = "Şirket yetkilisi sayısı")
    private int officialCount;

    @Schema(description = "Oluşturulma tarihi")
    private LocalDateTime createdAt;

    @Schema(description = "Son güncellenme tarihi")
    private LocalDateTime updatedAt;

    // ─── Constructors ─────────────────────────────────────────────────────────────

    public CompanyResponseDto() {}

    public CompanyResponseDto(Long id, String name, String taxNumber, String description, String website,
                               String industry, String city, String location, String address,
                               String contactEmail, String contactPhone,
                               CompanyVerificationStatus verificationStatus, Boolean isActive,
                               int officialCount, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.taxNumber = taxNumber;
        this.description = description;
        this.website = website;
        this.industry = industry;
        this.city = city;
        this.location = location;
        this.address = address;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
        this.verificationStatus = verificationStatus;
        this.isActive = isActive;
        this.officialCount = officialCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // ─── Builder ──────────────────────────────────────────────────────────────────

    public static CompanyResponseDtoBuilder builder() {
        return new CompanyResponseDtoBuilder();
    }

    // ─── Getters & Setters ────────────────────────────────────────────────────────

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getTaxNumber() { return taxNumber; }
    public void setTaxNumber(String taxNumber) { this.taxNumber = taxNumber; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }

    public String getIndustry() { return industry; }
    public void setIndustry(String industry) { this.industry = industry; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getContactEmail() { return contactEmail; }
    public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }

    public String getContactPhone() { return contactPhone; }
    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }

    public CompanyVerificationStatus getVerificationStatus() { return verificationStatus; }
    public void setVerificationStatus(CompanyVerificationStatus verificationStatus) { this.verificationStatus = verificationStatus; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public int getOfficialCount() { return officialCount; }
    public void setOfficialCount(int officialCount) { this.officialCount = officialCount; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    // ─── Inner Builder Class ──────────────────────────────────────────────────────

    public static class CompanyResponseDtoBuilder {
        private Long id;
        private String name;
        private String taxNumber;
        private String description;
        private String website;
        private String industry;
        private String city;
        private String location;
        private String address;
        private String contactEmail;
        private String contactPhone;
        private CompanyVerificationStatus verificationStatus;
        private Boolean isActive;
        private int officialCount;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        CompanyResponseDtoBuilder() {}

        public CompanyResponseDtoBuilder id(Long id) { this.id = id; return this; }
        public CompanyResponseDtoBuilder name(String name) { this.name = name; return this; }
        public CompanyResponseDtoBuilder taxNumber(String taxNumber) { this.taxNumber = taxNumber; return this; }
        public CompanyResponseDtoBuilder description(String description) { this.description = description; return this; }
        public CompanyResponseDtoBuilder website(String website) { this.website = website; return this; }
        public CompanyResponseDtoBuilder industry(String industry) { this.industry = industry; return this; }
        public CompanyResponseDtoBuilder city(String city) { this.city = city; return this; }
        public CompanyResponseDtoBuilder location(String location) { this.location = location; return this; }
        public CompanyResponseDtoBuilder address(String address) { this.address = address; return this; }
        public CompanyResponseDtoBuilder contactEmail(String contactEmail) { this.contactEmail = contactEmail; return this; }
        public CompanyResponseDtoBuilder contactPhone(String contactPhone) { this.contactPhone = contactPhone; return this; }
        public CompanyResponseDtoBuilder verificationStatus(CompanyVerificationStatus verificationStatus) { this.verificationStatus = verificationStatus; return this; }
        public CompanyResponseDtoBuilder isActive(Boolean isActive) { this.isActive = isActive; return this; }
        public CompanyResponseDtoBuilder officialCount(int officialCount) { this.officialCount = officialCount; return this; }
        public CompanyResponseDtoBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public CompanyResponseDtoBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public CompanyResponseDto build() {
            return new CompanyResponseDto(id, name, taxNumber, description, website, industry,
                    city, location, address, contactEmail, contactPhone,
                    verificationStatus, isActive, officialCount, createdAt, updatedAt);
        }
    }
}
