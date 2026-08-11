package com.example.internmatch.entity;

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
    @Column(nullable = false, length = 150)
    private String name;

    @Column(name = "tax_number", length = 50)
    private String taxNumber;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 255)
    private String website;

    @Column(length = 100)
    private String industry;

    @Column(length = 150)
    private String location;

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

    public Company() {
    }

    public Company(Long id, String name, String taxNumber, String description, String website, String industry, String location, LocalDateTime createdAt, LocalDateTime updatedAt, List<User> officials, List<InternshipPosting> postings) {
        this.id = id;
        this.name = name;
        this.taxNumber = taxNumber;
        this.description = description;
        this.website = website;
        this.industry = industry;
        this.location = location;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.officials = officials != null ? officials : new ArrayList<>();
        this.postings = postings != null ? postings : new ArrayList<>();
    }

    public static CompanyBuilder builder() {
        return new CompanyBuilder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public List<User> getOfficials() {
        return officials;
    }

    public void setOfficials(List<User> officials) {
        this.officials = officials;
    }

    public List<InternshipPosting> getPostings() {
        return postings;
    }

    public void setPostings(List<InternshipPosting> postings) {
        this.postings = postings;
    }

    public static class CompanyBuilder {
        private Long id;
        private String name;
        private String taxNumber;
        private String description;
        private String website;
        private String industry;
        private String location;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private List<User> officials = new ArrayList<>();
        private List<InternshipPosting> postings = new ArrayList<>();

        CompanyBuilder() {
        }

        public CompanyBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CompanyBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CompanyBuilder taxNumber(String taxNumber) {
            this.taxNumber = taxNumber;
            return this;
        }

        public CompanyBuilder description(String description) {
            this.description = description;
            return this;
        }

        public CompanyBuilder website(String website) {
            this.website = website;
            return this;
        }

        public CompanyBuilder industry(String industry) {
            this.industry = industry;
            return this;
        }

        public CompanyBuilder location(String location) {
            this.location = location;
            return this;
        }

        public CompanyBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public CompanyBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public CompanyBuilder officials(List<User> officials) {
            this.officials = officials;
            return this;
        }

        public CompanyBuilder postings(List<InternshipPosting> postings) {
            this.postings = postings;
            return this;
        }

        public Company build() {
            return new Company(this.id, this.name, this.taxNumber, this.description, this.website, this.industry, this.location, this.createdAt, this.updatedAt, this.officials, this.postings);
        }
    }
}
