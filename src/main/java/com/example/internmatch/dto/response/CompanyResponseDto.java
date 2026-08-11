package com.example.internmatch.dto.response;

import java.time.LocalDateTime;

public class CompanyResponseDto {
    private Long id;
    private String name;
    private String taxNumber;
    private String description;
    private String website;
    private String industry;
    private String location;
    private LocalDateTime createdAt;

    public CompanyResponseDto() {
    }

    public CompanyResponseDto(Long id, String name, String taxNumber, String description, String website, String industry, String location, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.taxNumber = taxNumber;
        this.description = description;
        this.website = website;
        this.industry = industry;
        this.location = location;
        this.createdAt = createdAt;
    }

    public static CompanyResponseDtoBuilder builder() {
        return new CompanyResponseDtoBuilder();
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

    public static class CompanyResponseDtoBuilder {
        private Long id;
        private String name;
        private String taxNumber;
        private String description;
        private String website;
        private String industry;
        private String location;
        private LocalDateTime createdAt;

        CompanyResponseDtoBuilder() {
        }

        public CompanyResponseDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CompanyResponseDtoBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CompanyResponseDtoBuilder taxNumber(String taxNumber) {
            this.taxNumber = taxNumber;
            return this;
        }

        public CompanyResponseDtoBuilder description(String description) {
            this.description = description;
            return this;
        }

        public CompanyResponseDtoBuilder website(String website) {
            this.website = website;
            return this;
        }

        public CompanyResponseDtoBuilder industry(String industry) {
            this.industry = industry;
            return this;
        }

        public CompanyResponseDtoBuilder location(String location) {
            this.location = location;
            return this;
        }

        public CompanyResponseDtoBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public CompanyResponseDto build() {
            return new CompanyResponseDto(this.id, this.name, this.taxNumber, this.description, this.website, this.industry, this.location, this.createdAt);
        }
    }
}
