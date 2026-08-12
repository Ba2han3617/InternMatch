package com.example.internmatch.dto.request;

import com.example.internmatch.enums.SkillLevel;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class AddStudentSkillRequest {

    @NotNull(message = "Beceri ID boş olamaz")
    private Long skillId;

    @NotNull(message = "Beceri seviyesi boş olamaz")
    private SkillLevel level;

    @Min(value = 0, message = "Deneyim yılı 0'dan küçük olamaz")
    @Max(value = 50, message = "Deneyim yılı 50'den fazla olamaz")
    private Integer yearsOfExperience;

    public AddStudentSkillRequest() {
    }

    public Long getSkillId() { return skillId; }
    public void setSkillId(Long skillId) { this.skillId = skillId; }

    public SkillLevel getLevel() { return level; }
    public void setLevel(SkillLevel level) { this.level = level; }

    public Integer getYearsOfExperience() { return yearsOfExperience; }
    public void setYearsOfExperience(Integer yearsOfExperience) { this.yearsOfExperience = yearsOfExperience; }
}
