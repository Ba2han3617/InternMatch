package com.example.internmatch.service;

import com.example.internmatch.dto.request.AddStudentSkillRequest;
import com.example.internmatch.dto.request.CreateStudentProfileRequest;
import com.example.internmatch.dto.request.UpdateStudentProfileRequest;
import com.example.internmatch.dto.request.UpdateStudentSkillRequest;
import com.example.internmatch.dto.response.StudentProfileResponseDto;
import com.example.internmatch.dto.response.StudentSkillResponseDto;
import com.example.internmatch.entity.User;

import java.util.List;

public interface StudentProfileService {

    /**
     * Giriş yapmış öğrenci için yeni profil oluşturur.
     * Zaten profil varsa DuplicateResourceException fırlatır.
     */
    StudentProfileResponseDto createProfile(CreateStudentProfileRequest request, User currentUser);

    /**
     * Giriş yapmış öğrencinin kendi profilini döndürür.
     */
    StudentProfileResponseDto getMyProfile(User currentUser);

    /**
     * Giriş yapmış öğrencinin kendi profilini günceller.
     */
    StudentProfileResponseDto updateProfile(UpdateStudentProfileRequest request, User currentUser);

    /**
     * Admin için ID ile belirli öğrenci profilini getirir.
     */
    StudentProfileResponseDto getStudentProfileById(Long profileId);

    /**
     * Giriş yapmış öğrencinin beceri listesini döndürür.
     */
    List<StudentSkillResponseDto> getMySkills(User currentUser);

    /**
     * Giriş yapmış öğrenciye yeni beceri ekler.
     * Aynı beceri tekrar eklenmeye çalışılırsa DuplicateResourceException fırlatır.
     */
    StudentSkillResponseDto addSkill(AddStudentSkillRequest request, User currentUser);

    /**
     * Öğrencinin belirtilen beceri kaydını günceller.
     * Öğrenci yalnızca kendi kayıtlarını güncelleyebilir.
     */
    StudentSkillResponseDto updateSkill(Long studentSkillId, UpdateStudentSkillRequest request, User currentUser);

    /**
     * Öğrencinin belirtilen beceri kaydını siler.
     * Öğrenci yalnızca kendi kayıtlarını silebilir.
     */
    void deleteSkill(Long studentSkillId, User currentUser);
}
