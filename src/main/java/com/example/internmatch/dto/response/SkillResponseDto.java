package com.example.internmatch.dto.response;

public class SkillResponseDto {
    private Long id;
    private String name;
    private String category;

    public SkillResponseDto() {
    }

    public SkillResponseDto(Long id, String name, String category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }

    public static SkillResponseDtoBuilder builder() {
        return new SkillResponseDtoBuilder();
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

    public static class SkillResponseDtoBuilder {
        private Long id;
        private String name;
        private String category;

        SkillResponseDtoBuilder() {
        }

        public SkillResponseDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public SkillResponseDtoBuilder name(String name) {
            this.name = name;
            return this;
        }

        public SkillResponseDtoBuilder category(String category) {
            this.category = category;
            return this;
        }

        public SkillResponseDto build() {
            return new SkillResponseDto(this.id, this.name, this.category);
        }
    }
}
