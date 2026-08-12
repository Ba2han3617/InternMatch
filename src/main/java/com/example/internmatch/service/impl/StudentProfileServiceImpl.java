package com.example.internmatch.service.impl;

import com.example.internmatch.dto.request.AddStudentSkillRequest;
import com.example.internmatch.dto.request.CreateStudentProfileRequest;
import com.example.internmatch.dto.request.UpdateStudentProfileRequest;
import com.example.internmatch.dto.request.UpdateStudentSkillRequest;
import com.example.internmatch.dto.response.StudentProfileResponseDto;
import com.example.internmatch.dto.response.StudentSkillResponseDto;
import com.example.internmatch.entity.Skill;
import com.example.internmatch.entity.StudentProfile;
import com.example.internmatch.entity.StudentSkill;
import com.example.internmatch.entity.User;
import com.example.internmatch.exception.DuplicateResourceException;
import com.example.internmatch.exception.ResourceNotFoundException;
import com.example.internmatch.repository.SkillRepository;
import com.example.internmatch.repository.StudentProfileRepository;
import com.example.internmatch.repository.StudentSkillRepository;
import com.example.internmatch.service.StudentProfileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentProfileServiceImpl implements StudentProfileService {

    private final StudentProfileRepository studentProfileRepository;
    private final StudentSkillRepository studentSkillRepository;
    private final SkillRepository skillRepository;

    public StudentProfileServiceImpl(
            StudentProfileRepository studentProfileRepository,
            StudentSkillRepository studentSkillRepository,
            SkillRepository skillRepository) {
        this.studentProfileRepository = studentProfileRepository;
        this.studentSkillRepository = studentSkillRepository;
        this.skillRepository = skillRepository;
    }

    // ─── Profile CRUD ────────────────────────────────────────────────────────────

    @Override
    @Transactional
    public StudentProfileResponseDto createProfile(CreateStudentProfileRequest request, User currentUser) {
        if (studentProfileRepository.existsByUserId(currentUser.getId())) {
            throw new DuplicateResourceException(
                    "Bu kullanıcı için zaten bir öğrenci profili mevcut. Profil güncellemek için PUT /api/students/profile kullanın.");
        }

        StudentProfile profile = StudentProfile.builder()
                .user(currentUser)
                .studentNumber(request.getStudentNumber())
                .university(request.getUniversity())
                .department(request.getDepartment())
                .gradeLevel(request.getGradeLevel())
                .gpa(request.getGpa())
                .cvUrl(request.getCvUrl())
                .summary(request.getSummary())
                .githubUrl(request.getGithubUrl())
                .linkedinUrl(request.getLinkedinUrl())
                .portfolioUrl(request.getPortfolioUrl())
                .city(request.getCity())
                .graduationYear(request.getGraduationYear())
                .preferredWorkMode(request.getPreferredWorkMode())
                .build();

        StudentProfile savedProfile = studentProfileRepository.save(profile);
        return mapToDto(savedProfile);
    }

    @Override
    @Transactional(readOnly = true)
    public StudentProfileResponseDto getMyProfile(User currentUser) {
        StudentProfile profile = studentProfileRepository.findByUserId(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Öğrenci profili bulunamadı. Önce profil oluşturun: POST /api/students/profile"));
        return mapToDto(profile);
    }

    @Override
    @Transactional
    public StudentProfileResponseDto updateProfile(UpdateStudentProfileRequest request, User currentUser) {
        StudentProfile profile = studentProfileRepository.findByUserId(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Öğrenci profili bulunamadı. Önce profil oluşturun: POST /api/students/profile"));

        if (request.getStudentNumber() != null) profile.setStudentNumber(request.getStudentNumber());
        if (request.getUniversity() != null) profile.setUniversity(request.getUniversity());
        if (request.getDepartment() != null) profile.setDepartment(request.getDepartment());
        if (request.getGradeLevel() != null) profile.setGradeLevel(request.getGradeLevel());
        if (request.getGpa() != null) profile.setGpa(request.getGpa());
        if (request.getCvUrl() != null) profile.setCvUrl(request.getCvUrl());
        if (request.getSummary() != null) profile.setSummary(request.getSummary());
        if (request.getGithubUrl() != null) profile.setGithubUrl(request.getGithubUrl());
        if (request.getLinkedinUrl() != null) profile.setLinkedinUrl(request.getLinkedinUrl());
        if (request.getPortfolioUrl() != null) profile.setPortfolioUrl(request.getPortfolioUrl());
        if (request.getCity() != null) profile.setCity(request.getCity());
        if (request.getGraduationYear() != null) profile.setGraduationYear(request.getGraduationYear());
        if (request.getPreferredWorkMode() != null) profile.setPreferredWorkMode(request.getPreferredWorkMode());

        StudentProfile updatedProfile = studentProfileRepository.save(profile);
        return mapToDto(updatedProfile);
    }

    @Override
    @Transactional(readOnly = true)
    public StudentProfileResponseDto getStudentProfileById(Long profileId) {
        StudentProfile profile = studentProfileRepository.findById(profileId)
                .orElseThrow(() -> new ResourceNotFoundException("StudentProfile", "id", profileId));
        return mapToDto(profile);
    }

    // ─── Skill CRUD ───────────────────────────────────────────────────────────────

    @Override
    @Transactional(readOnly = true)
    public List<StudentSkillResponseDto> getMySkills(User currentUser) {
        StudentProfile profile = studentProfileRepository.findByUserId(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Öğrenci profili bulunamadı. Önce profil oluşturun: POST /api/students/profile"));

        return studentSkillRepository.findByStudentProfileId(profile.getId())
                .stream()
                .map(this::mapSkillToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public StudentSkillResponseDto addSkill(AddStudentSkillRequest request, User currentUser) {
        StudentProfile profile = studentProfileRepository.findByUserId(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Öğrenci profili bulunamadı. Önce profil oluşturun: POST /api/students/profile"));

        Skill skill = skillRepository.findById(request.getSkillId())
                .orElseThrow(() -> new ResourceNotFoundException("Skill", "id", request.getSkillId()));

        if (studentSkillRepository.existsByStudentProfileIdAndSkillId(profile.getId(), skill.getId())) {
            throw new DuplicateResourceException(
                    "'" + skill.getName() + "' becerisi profilinize zaten eklenmiş.");
        }

        StudentSkill studentSkill = StudentSkill.builder()
                .studentProfile(profile)
                .skill(skill)
                .level(request.getLevel())
                .yearsOfExperience(request.getYearsOfExperience())
                .build();

        StudentSkill savedSkill = studentSkillRepository.save(studentSkill);
        return mapSkillToDto(savedSkill);
    }

    @Override
    @Transactional
    public StudentSkillResponseDto updateSkill(Long studentSkillId, UpdateStudentSkillRequest request, User currentUser) {
        StudentProfile profile = studentProfileRepository.findByUserId(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Öğrenci profili bulunamadı. Önce profil oluşturun: POST /api/students/profile"));

        StudentSkill studentSkill = studentSkillRepository
                .findByIdAndStudentProfileId(studentSkillId, profile.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Bu beceri kaydı bulunamadı veya size ait değil. ID: " + studentSkillId));

        studentSkill.setLevel(request.getLevel());
        if (request.getYearsOfExperience() != null) {
            studentSkill.setYearsOfExperience(request.getYearsOfExperience());
        }

        StudentSkill updatedSkill = studentSkillRepository.save(studentSkill);
        return mapSkillToDto(updatedSkill);
    }

    @Override
    @Transactional
    public void deleteSkill(Long studentSkillId, User currentUser) {
        StudentProfile profile = studentProfileRepository.findByUserId(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Öğrenci profili bulunamadı. Önce profil oluşturun: POST /api/students/profile"));

        StudentSkill studentSkill = studentSkillRepository
                .findByIdAndStudentProfileId(studentSkillId, profile.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Bu beceri kaydı bulunamadı veya size ait değil. ID: " + studentSkillId));

        studentSkillRepository.delete(studentSkill);
    }

    // ─── Mapping Helpers ─────────────────────────────────────────────────────────

    private StudentProfileResponseDto mapToDto(StudentProfile profile) {
        User user = profile.getUser();
        String fullName = (user.getFirstName() != null ? user.getFirstName() : "")
                + " "
                + (user.getLastName() != null ? user.getLastName() : "");
        return StudentProfileResponseDto.builder()
                .id(profile.getId())
                .userId(user.getId())
                .userEmail(user.getEmail())
                .userFullName(fullName.trim())
                .studentNumber(profile.getStudentNumber())
                .university(profile.getUniversity())
                .department(profile.getDepartment())
                .gradeLevel(profile.getGradeLevel())
                .gpa(profile.getGpa())
                .cvUrl(profile.getCvUrl())
                .summary(profile.getSummary())
                .githubUrl(profile.getGithubUrl())
                .linkedinUrl(profile.getLinkedinUrl())
                .portfolioUrl(profile.getPortfolioUrl())
                .city(profile.getCity())
                .graduationYear(profile.getGraduationYear())
                .preferredWorkMode(profile.getPreferredWorkMode())
                .createdAt(profile.getCreatedAt())
                .updatedAt(profile.getUpdatedAt())
                .build();
    }

    private StudentSkillResponseDto mapSkillToDto(StudentSkill studentSkill) {
        Skill skill = studentSkill.getSkill();
        return StudentSkillResponseDto.builder()
                .id(studentSkill.getId())
                .skillId(skill.getId())
                .skillName(skill.getName())
                .skillCategory(skill.getCategory())
                .level(studentSkill.getLevel())
                .yearsOfExperience(studentSkill.getYearsOfExperience())
                .build();
    }
}
