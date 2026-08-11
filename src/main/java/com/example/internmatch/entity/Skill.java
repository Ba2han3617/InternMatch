package com.example.internmatch.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "skills")
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(length = 50)
    private String category;

    public Skill() {
    }

    public Skill(Long id, String name, String category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }

    public static SkillBuilder builder() {
        return new SkillBuilder();
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public static class SkillBuilder {
        private Long id;
        private String name;
        private String category;

        SkillBuilder() {
        }

        public SkillBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public SkillBuilder name(String name) {
            this.name = name;
            return this;
        }

        public SkillBuilder category(String category) {
            this.category = category;
            return this;
        }

        public Skill build() {
            return new Skill(this.id, this.name, this.category);
        }
    }
}
