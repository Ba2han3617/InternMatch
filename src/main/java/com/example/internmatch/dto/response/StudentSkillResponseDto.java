package com.example.internmatch.dto.response;

import com.example.internmatch.enums.SkillLevel;

public class StudentSkillResponseDto {
    private Long id;
    private Long skillId;
    private String skillName;
    private String skillCategory;
    private SkillLevel level;
    private Integer yearsOfExperience;

    public StudentSkillResponseDto() {
    }

    public StudentSkillResponseDto(Long id, Long skillId, String skillName, String skillCategory,
                                   SkillLevel level, Integer yearsOfExperience) {
        this.id = id;
        this.skillId = skillId;
        this.skillName = skillName;
        this.skillCategory = skillCategory;
        this.level = level;
        this.yearsOfExperience = yearsOfExperience;
    }

    public static StudentSkillResponseDtoBuilder builder() {
        return new StudentSkillResponseDtoBuilder();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getSkillId() { return skillId; }
    public void setSkillId(Long skillId) { this.skillId = skillId; }

    public String getSkillName() { return skillName; }
    public void setSkillName(String skillName) { this.skillName = skillName; }

    public String getSkillCategory() { return skillCategory; }
    public void setSkillCategory(String skillCategory) { this.skillCategory = skillCategory; }

    public SkillLevel getLevel() { return level; }
    public void setLevel(SkillLevel level) { this.level = level; }

    public Integer getYearsOfExperience() { return yearsOfExperience; }
    public void setYearsOfExperience(Integer yearsOfExperience) { this.yearsOfExperience = yearsOfExperience; }

    public static class StudentSkillResponseDtoBuilder {
        private Long id;
        private Long skillId;
        private String skillName;
        private String skillCategory;
        private SkillLevel level;
        private Integer yearsOfExperience;

        StudentSkillResponseDtoBuilder() {}

        public StudentSkillResponseDtoBuilder id(Long id) { this.id = id; return this; }
        public StudentSkillResponseDtoBuilder skillId(Long skillId) { this.skillId = skillId; return this; }
        public StudentSkillResponseDtoBuilder skillName(String skillName) { this.skillName = skillName; return this; }
        public StudentSkillResponseDtoBuilder skillCategory(String skillCategory) { this.skillCategory = skillCategory; return this; }
        public StudentSkillResponseDtoBuilder level(SkillLevel level) { this.level = level; return this; }
        public StudentSkillResponseDtoBuilder yearsOfExperience(Integer yearsOfExperience) { this.yearsOfExperience = yearsOfExperience; return this; }

        public StudentSkillResponseDto build() {
            return new StudentSkillResponseDto(id, skillId, skillName, skillCategory, level, yearsOfExperience);
        }
    }
}
