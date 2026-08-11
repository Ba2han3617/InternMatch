package com.example.internmatch.entity;

import com.example.internmatch.enums.SkillLevel;
import jakarta.persistence.*;

@Entity
@Table(name = "student_skills", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"student_profile_id", "skill_id"})
})
public class StudentSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_profile_id", nullable = false)
    private StudentProfile studentProfile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SkillLevel level;

    @Column(name = "years_of_experience")
    private Integer yearsOfExperience;

    public StudentSkill() {
    }

    public StudentSkill(Long id, StudentProfile studentProfile, Skill skill, SkillLevel level, Integer yearsOfExperience) {
        this.id = id;
        this.studentProfile = studentProfile;
        this.skill = skill;
        this.level = level;
        this.yearsOfExperience = yearsOfExperience;
    }

    public static StudentSkillBuilder builder() {
        return new StudentSkillBuilder();
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

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public SkillLevel getLevel() {
        return level;
    }

    public void setLevel(SkillLevel level) {
        this.level = level;
    }

    public Integer getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(Integer yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public static class StudentSkillBuilder {
        private Long id;
        private StudentProfile studentProfile;
        private Skill skill;
        private SkillLevel level;
        private Integer yearsOfExperience;

        StudentSkillBuilder() {
        }

        public StudentSkillBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public StudentSkillBuilder studentProfile(StudentProfile studentProfile) {
            this.studentProfile = studentProfile;
            return this;
        }

        public StudentSkillBuilder skill(Skill skill) {
            this.skill = skill;
            return this;
        }

        public StudentSkillBuilder level(SkillLevel level) {
            this.level = level;
            return this;
        }

        public StudentSkillBuilder yearsOfExperience(Integer yearsOfExperience) {
            this.yearsOfExperience = yearsOfExperience;
            return this;
        }

        public StudentSkill build() {
            return new StudentSkill(this.id, this.studentProfile, this.skill, this.level, this.yearsOfExperience);
        }
    }
}
