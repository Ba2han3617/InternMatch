package com.example.internmatch.entity;

import com.example.internmatch.enums.CriterionType;
import com.example.internmatch.enums.SkillLevel;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "posting_criteria")
public class PostingCriterion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posting_id", nullable = false)
    private InternshipPosting posting;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private CriterionType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id")
    private Skill skill;

    @Enumerated(EnumType.STRING)
    @Column(name = "required_skill_level", length = 20)
    private SkillLevel requiredSkillLevel;

    @Column(name = "string_value", length = 255)
    private String stringValue;

    @Column(name = "numeric_value", precision = 5, scale = 2)
    private BigDecimal numericValue;

    @Column(name = "is_mandatory", nullable = false)
    private Boolean isMandatory = false;

    @Column(precision = 5, scale = 2)
    private BigDecimal weight;

    public PostingCriterion() {
    }

    public PostingCriterion(Long id, InternshipPosting posting, CriterionType type, Skill skill, SkillLevel requiredSkillLevel, String stringValue, BigDecimal numericValue, Boolean isMandatory, BigDecimal weight) {
        this.id = id;
        this.posting = posting;
        this.type = type;
        this.skill = skill;
        this.requiredSkillLevel = requiredSkillLevel;
        this.stringValue = stringValue;
        this.numericValue = numericValue;
        this.isMandatory = isMandatory != null ? isMandatory : false;
        this.weight = weight;
    }

    public static PostingCriterionBuilder builder() {
        return new PostingCriterionBuilder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InternshipPosting getPosting() {
        return posting;
    }

    public void setPosting(InternshipPosting posting) {
        this.posting = posting;
    }

    public CriterionType getType() {
        return type;
    }

    public void setType(CriterionType type) {
        this.type = type;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public SkillLevel getRequiredSkillLevel() {
        return requiredSkillLevel;
    }

    public void setRequiredSkillLevel(SkillLevel requiredSkillLevel) {
        this.requiredSkillLevel = requiredSkillLevel;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public BigDecimal getNumericValue() {
        return numericValue;
    }

    public void setNumericValue(BigDecimal numericValue) {
        this.numericValue = numericValue;
    }

    public Boolean getIsMandatory() {
        return isMandatory;
    }

    public void setIsMandatory(Boolean isMandatory) {
        this.isMandatory = isMandatory;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public static class PostingCriterionBuilder {
        private Long id;
        private InternshipPosting posting;
        private CriterionType type;
        private Skill skill;
        private SkillLevel requiredSkillLevel;
        private String stringValue;
        private BigDecimal numericValue;
        private Boolean isMandatory = false;
        private BigDecimal weight;

        PostingCriterionBuilder() {
        }

        public PostingCriterionBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public PostingCriterionBuilder posting(InternshipPosting posting) {
            this.posting = posting;
            return this;
        }

        public PostingCriterionBuilder type(CriterionType type) {
            this.type = type;
            return this;
        }

        public PostingCriterionBuilder skill(Skill skill) {
            this.skill = skill;
            return this;
        }

        public PostingCriterionBuilder requiredSkillLevel(SkillLevel requiredSkillLevel) {
            this.requiredSkillLevel = requiredSkillLevel;
            return this;
        }

        public PostingCriterionBuilder stringValue(String stringValue) {
            this.stringValue = stringValue;
            return this;
        }

        public PostingCriterionBuilder numericValue(BigDecimal numericValue) {
            this.numericValue = numericValue;
            return this;
        }

        public PostingCriterionBuilder isMandatory(Boolean isMandatory) {
            this.isMandatory = isMandatory;
            return this;
        }

        public PostingCriterionBuilder weight(BigDecimal weight) {
            this.weight = weight;
            return this;
        }

        public PostingCriterion build() {
            return new PostingCriterion(this.id, this.posting, this.type, this.skill, this.requiredSkillLevel, this.stringValue, this.numericValue, this.isMandatory, this.weight);
        }
    }
}
