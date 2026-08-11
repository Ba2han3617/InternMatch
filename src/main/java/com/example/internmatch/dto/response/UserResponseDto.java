package com.example.internmatch.dto.response;

import java.time.LocalDateTime;
import java.util.Set;

public class UserResponseDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private Boolean isActive;
    private Set<String> roles;
    private Long companyId;
    private String companyName;
    private LocalDateTime createdAt;

    public UserResponseDto() {
    }

    public UserResponseDto(Long id, String email, String firstName, String lastName, String phone, Boolean isActive, Set<String> roles, Long companyId, String companyName, LocalDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.isActive = isActive;
        this.roles = roles;
        this.companyId = companyId;
        this.companyName = companyName;
        this.createdAt = createdAt;
    }

    public static UserResponseDtoBuilder builder() {
        return new UserResponseDtoBuilder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
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

    public static class UserResponseDtoBuilder {
        private Long id;
        private String email;
        private String firstName;
        private String lastName;
        private String phone;
        private Boolean isActive;
        private Set<String> roles;
        private Long companyId;
        private String companyName;
        private LocalDateTime createdAt;

        UserResponseDtoBuilder() {
        }

        public UserResponseDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserResponseDtoBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserResponseDtoBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserResponseDtoBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserResponseDtoBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public UserResponseDtoBuilder isActive(Boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public UserResponseDtoBuilder roles(Set<String> roles) {
            this.roles = roles;
            return this;
        }

        public UserResponseDtoBuilder companyId(Long companyId) {
            this.companyId = companyId;
            return this;
        }

        public UserResponseDtoBuilder companyName(String companyName) {
            this.companyName = companyName;
            return this;
        }

        public UserResponseDtoBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public UserResponseDto build() {
            return new UserResponseDto(this.id, this.email, this.firstName, this.lastName, this.phone, this.isActive, this.roles, this.companyId, this.companyName, this.createdAt);
        }
    }
}
