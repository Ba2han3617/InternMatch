package com.example.internmatch.entity;

import com.example.internmatch.enums.CompanyVerificationStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true, length = 150)
    private String name;

    @Column(name = "tax_number", length = 50)
    private String taxNumber;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 255)
    private String website;

    @Column(length = 100)
    private String industry;

    /** Şehir bilgisi */
    @Column(length = 100)
    private String city;

    /** Tam adres bilgisi (eski 'location' alanı yerine ayrı tutuldu, eski kod uyumu için location da korundu) */
    @Column(length = 150)
    private String location;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(name = "contact_email", length = 100)
    private String contactEmail;

    @Column(name = "contact_phone", length = 20)
    private String contactPhone;

    @Enumerated(EnumType.STRING)
    @Column(name = "verification_status", nullable = false, length = 20)
    private CompanyVerificationStatus verificationStatus = CompanyVerificationStatus.PENDING;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    private List<User> officials = new ArrayList<>();

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<InternshipPosting> postings = new ArrayList<>();

    // ─── Constructors ─────────────────────────────────────────────────────────────

    public Company() {
    }

    public Company(Long id, String name, String taxNumber, String description, String website,
                   String industry, String city, String location, String address,
                   String contactEmail, String contactPhone,
                   CompanyVerificationStatus verificationStatus, Boolean isActive,
                   LocalDateTime createdAt, LocalDateTime updatedAt,
                   List<User> officials, List<InternshipPosting> postings) {
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
        this.verificationStatus = verificationStatus != null ? verificationStatus : CompanyVerificationStatus.PENDING;
        this.isActive = isActive != null ? isActive : true;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.officials = officials != null ? officials : new ArrayList<>();
        this.postings = postings != null ? postings : new ArrayList<>();
    }

    // ─── Builder ──────────────────────────────────────────────────────────────────

    public static CompanyBuilder builder() {
        return new CompanyBuilder();
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

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public List<User> getOfficials() { return officials; }
    public void setOfficials(List<User> officials) { this.officials = officials; }

    public List<InternshipPosting> getPostings() { return postings; }
    public void setPostings(List<InternshipPosting> postings) { this.postings = postings; }

    // ─── Inner Builder Class ──────────────────────────────────────────────────────

    public static class CompanyBuilder {
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
        private CompanyVerificationStatus verificationStatus = CompanyVerificationStatus.PENDING;
        private Boolean isActive = true;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private List<User> officials = new ArrayList<>();
        private List<InternshipPosting> postings = new ArrayList<>();

        CompanyBuilder() {}

        public CompanyBuilder id(Long id) { this.id = id; return this; }
        public CompanyBuilder name(String name) { this.name = name; return this; }
        public CompanyBuilder taxNumber(String taxNumber) { this.taxNumber = taxNumber; return this; }
        public CompanyBuilder description(String description) { this.description = description; return this; }
        public CompanyBuilder website(String website) { this.website = website; return this; }
        public CompanyBuilder industry(String industry) { this.industry = industry; return this; }
        public CompanyBuilder city(String city) { this.city = city; return this; }
        public CompanyBuilder location(String location) { this.location = location; return this; }
        public CompanyBuilder address(String address) { this.address = address; return this; }
        public CompanyBuilder contactEmail(String contactEmail) { this.contactEmail = contactEmail; return this; }
        public CompanyBuilder contactPhone(String contactPhone) { this.contactPhone = contactPhone; return this; }
        public CompanyBuilder verificationStatus(CompanyVerificationStatus verificationStatus) { this.verificationStatus = verificationStatus; return this; }
        public CompanyBuilder isActive(Boolean isActive) { this.isActive = isActive; return this; }
        public CompanyBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public CompanyBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }
        public CompanyBuilder officials(List<User> officials) { this.officials = officials; return this; }
        public CompanyBuilder postings(List<InternshipPosting> postings) { this.postings = postings; return this; }

        public Company build() {
            return new Company(id, name, taxNumber, description, website, industry,
                    city, location, address, contactEmail, contactPhone,
                    verificationStatus, isActive, createdAt, updatedAt, officials, postings);
        }
    }
}
