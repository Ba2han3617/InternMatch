package com.example.internmatch.entity;

import com.example.internmatch.enums.ApplicationStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "applications", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"student_profile_id", "posting_id"})
})
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_profile_id", nullable = false)
    private StudentProfile studentProfile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posting_id", nullable = false)
    private InternshipPosting posting;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ApplicationStatus status;

    @CreationTimestamp
    @Column(name = "applied_at", nullable = false, updatable = false)
    private LocalDateTime appliedAt;

    @Column(columnDefinition = "TEXT")
    private String notes;

    public Application() {
    }

    public Application(Long id, StudentProfile studentProfile, InternshipPosting posting, ApplicationStatus status, LocalDateTime appliedAt, String notes) {
        this.id = id;
        this.studentProfile = studentProfile;
        this.posting = posting;
        this.status = status;
        this.appliedAt = appliedAt;
        this.notes = notes;
    }

    public static ApplicationBuilder builder() {
        return new ApplicationBuilder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StudentProfile getStudentProfile() {
        return studentProfile;
    }

    public void setStudentProfile(StudentProfile studentProfile) {
        this.studentProfile = studentProfile;
    }

    public InternshipPosting getPosting() {
        return posting;
    }

    public void setPosting(InternshipPosting posting) {
        this.posting = posting;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public LocalDateTime getAppliedAt() {
        return appliedAt;
    }

    public void setAppliedAt(LocalDateTime appliedAt) {
        this.appliedAt = appliedAt;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public static class ApplicationBuilder {
        private Long id;
        private StudentProfile studentProfile;
        private InternshipPosting posting;
        private ApplicationStatus status;
        private LocalDateTime appliedAt;
        private String notes;

        ApplicationBuilder() {
        }

        public ApplicationBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ApplicationBuilder studentProfile(StudentProfile studentProfile) {
            this.studentProfile = studentProfile;
            return this;
        }

        public ApplicationBuilder posting(InternshipPosting posting) {
            this.posting = posting;
            return this;
        }

        public ApplicationBuilder status(ApplicationStatus status) {
            this.status = status;
            return this;
        }

        public ApplicationBuilder appliedAt(LocalDateTime appliedAt) {
            this.appliedAt = appliedAt;
            return this;
        }

        public ApplicationBuilder notes(String notes) {
            this.notes = notes;
            return this;
        }

        public Application build() {
            return new Application(this.id, this.studentProfile, this.posting, this.status, this.appliedAt, this.notes);
        }
    }
}
