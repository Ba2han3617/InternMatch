/* ==========================================
   InternMatch - Full Student Dashboard Client
   ========================================== */

const API_BASE_URL = 'http://localhost:8081';

// App State
let activeTab = 'auth';
let availableSkills = [];

// DOM Elements
let apiMessageBox, tokenStatusBadge, loggedInUserSpan, authActionsBox;
let registerForm, loginForm, btnLogout;
let profileForm, btnSaveProfile, profileStatusBadge;
let skillForm, skillSelect, skillLevelSelect, skillYearsInput, userSkillsList;
let postingsContainer, btnFetchPostings;
let scoreResultCard, resultPostingTitle, resultTotalScore, resultScoreBar;
let resultMatchedCount, resultTotalCount, resultDetailsList;

document.addEventListener('DOMContentLoaded', () => {
  initDOM();
  initEventListeners();
  updateAuthUI();
  
  if (getToken()) {
    onUserLoggedIn();
  }
});

function initDOM() {
  apiMessageBox = document.getElementById('api-message-box');
  tokenStatusBadge = document.getElementById('token-status-badge');
  loggedInUserSpan = document.getElementById('logged-in-user-info');
  authActionsBox = document.getElementById('auth-actions-box');

  registerForm = document.getElementById('register-form');
  loginForm = document.getElementById('login-form');
  btnLogout = document.getElementById('btn-logout');

  profileForm = document.getElementById('profile-form');
  btnSaveProfile = document.getElementById('btn-save-profile');
  profileStatusBadge = document.getElementById('profile-status-badge');

  skillForm = document.getElementById('skill-form');
  skillSelect = document.getElementById('skill-select');
  skillLevelSelect = document.getElementById('skill-level-select');
  skillYearsInput = document.getElementById('skill-years-input');
  userSkillsList = document.getElementById('user-skills-list');

  postingsContainer = document.getElementById('postings-container');
  btnFetchPostings = document.getElementById('btn-fetch-postings');

  scoreResultCard = document.getElementById('score-result-card');
  resultPostingTitle = document.getElementById('result-posting-title');
  resultTotalScore = document.getElementById('result-total-score');
  resultScoreBar = document.getElementById('result-score-bar');
  resultMatchedCount = document.getElementById('result-matched-count');
  resultTotalCount = document.getElementById('result-total-count');
  resultDetailsList = document.getElementById('result-details-list');
}

function initEventListeners() {
  // Tab Switching
  document.querySelectorAll('.tab-btn').forEach(btn => {
    btn.addEventListener('click', (e) => {
      const targetTab = e.currentTarget.getAttribute('data-tab');
      switchTab(targetTab);
    });
  });

  // Auth Forms
  if (registerForm) registerForm.addEventListener('submit', handleRegister);
  if (loginForm) loginForm.addEventListener('submit', handleLogin);
  if (btnLogout) btnLogout.addEventListener('click', handleLogout);

  // Profile Form
  if (profileForm) profileForm.addEventListener('submit', handleSaveProfile);

  // Skill Form
  if (skillForm) skillForm.addEventListener('submit', handleAddSkill);

  // Postings Fetch Button
  if (btnFetchPostings) btnFetchPostings.addEventListener('click', () => {
    clearMessage();
    fetchPostings();
  });
}

// Helper: Token Storage
function getToken() {
  return localStorage.getItem('jwtToken');
}

function setToken(token, userEmail = '', userName = '') {
  if (token) {
    localStorage.setItem('jwtToken', token);
    if (userEmail) localStorage.setItem('userEmail', userEmail);
    if (userName) localStorage.setItem('userName', userName);
  } else {
    localStorage.removeItem('jwtToken');
    localStorage.removeItem('userEmail');
    localStorage.removeItem('userName');
  }
  updateAuthUI();
}

// UI State Update
function updateAuthUI() {
  const token = getToken();
  const userEmail = localStorage.getItem('userEmail') || '';
  const userName = localStorage.getItem('userName') || '';

  if (token) {
    if (tokenStatusBadge) {
      tokenStatusBadge.textContent = 'Giriş Yapıldı - Oturum Aktif';
      tokenStatusBadge.className = 'badge badge-success';
    }
    if (loggedInUserSpan) {
      loggedInUserSpan.textContent = userName ? `${userName} (${userEmail})` : userEmail;
    }
    if (authActionsBox) authActionsBox.classList.remove('hidden');
    if (btnLogout) btnLogout.classList.remove('hidden');
  } else {
    if (tokenStatusBadge) {
      tokenStatusBadge.textContent = 'Giriş Yapılmadı';
      tokenStatusBadge.className = 'badge badge-error';
    }
    if (loggedInUserSpan) {
      loggedInUserSpan.textContent = 'Ziyaretçi';
    }
    if (authActionsBox) authActionsBox.classList.add('hidden');
    if (btnLogout) btnLogout.classList.add('hidden');
  }
}

function switchTab(tabName) {
  activeTab = tabName;
  document.querySelectorAll('.tab-btn').forEach(btn => {
    btn.classList.toggle('active', btn.getAttribute('data-tab') === tabName);
  });
  document.querySelectorAll('.tab-pane').forEach(pane => {
    pane.classList.toggle('active', pane.id === `tab-${tabName}`);
  });

  clearMessage();

  // Load data for specific tabs if authenticated
  if (getToken()) {
    if (tabName === 'profile') loadProfile();
    if (tabName === 'skills') {
      loadSkillCatalog();
      loadUserSkills();
    }
    if (tabName === 'postings') fetchPostings();
  }
}

function onUserLoggedIn() {
  loadProfile();
  loadSkillCatalog();
  loadUserSkills();
  fetchPostings();
}

// Notification Banner
function showMessage(message, type = 'info') {
  if (!apiMessageBox) return;
  apiMessageBox.className = `api-message-box message-${type}`;
  apiMessageBox.textContent = message;
  apiMessageBox.classList.remove('hidden');
}

function clearMessage() {
  if (!apiMessageBox) return;
  apiMessageBox.className = 'api-message-box hidden';
  apiMessageBox.textContent = '';
}

// Error Handler (Requirements 7)
function handleApiError(status, customMsg = '') {
  let text = '';
  switch (status) {
    case 401:
      text = 'Token yok veya süresi dolmuş, tekrar giriş yapmalısınız.';
      setToken(null);
      switchTab('auth');
      break;
    case 403:
      text = 'Bu işlem için yetkiniz bulunmamaktadır.';
      break;
    case 404:
      text = 'İstenen kayıt bulunamadı.';
      break;
    case 409:
      text = 'Bu kayıt zaten mevcut. Güncelleme işlemi deneniyor...';
      break;
    case 500:
      text = 'Sunucu hatası oluştu. Lütfen tekrar deneyin.';
      break;
    default:
      text = customMsg || `Hata oluştu (Status Code: ${status}).`;
  }
  showMessage(text, 'error');
}

// 1. REGISTRATION (Requirement 1)
async function handleRegister(e) {
  e.preventDefault();
  clearMessage();

  const firstName = document.getElementById('reg-firstname').value.trim();
  const lastName = document.getElementById('reg-lastname').value.trim();
  const email = document.getElementById('reg-email').value.trim();
  const password = document.getElementById('reg-password').value.trim();
  const phone = document.getElementById('reg-phone').value.trim();

  if (!firstName || !lastName || !email || !password) {
    showMessage('Lütfen ad, soyad, e-posta ve şifre alanlarını eksiksiz doldurun.', 'error');
    return;
  }

  const btnSubmit = registerForm.querySelector('button[type="submit"]');
  btnSubmit.disabled = true;
  btnSubmit.textContent = 'Kayıt Yapılıyor...';

  try {
    const response = await fetch(`${API_BASE_URL}/api/auth/register`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        firstName,
        lastName,
        email,
        password,
        phone,
        role: 'ROLE_STUDENT'
      })
    });

    if (!response.ok) {
      if (response.status === 409 || response.status === 400) {
        showMessage('Bu e-posta adresi zaten kayıtlı veya bilgiler geçersiz.', 'error');
      } else {
        handleApiError(response.status);
      }
      return;
    }

    const data = await response.json();
    if (data && data.token) {
      const user = data.user || {};
      setToken(data.token, user.email || email, `${user.firstName || firstName} ${user.lastName || lastName}`);
      showMessage('Kayıt başarılı! Hesabınız oluşturuldu.', 'success');
      registerForm.reset();
      onUserLoggedIn();
      switchTab('profile');
    } else {
      showMessage('Kayıt yapıldı ancak oturum açma token\'ı alınamadı.', 'info');
    }
  } catch (err) {
    console.error('Register Error:', err);
    showMessage('Sunucuya ulaşılamadı. (http://localhost:8081)', 'error');
  } finally {
    btnSubmit.disabled = false;
    btnSubmit.textContent = 'Kayıt Ol (POST /api/auth/register)';
  }
}

// 2. LOGIN (Requirement 2)
async function handleLogin(e) {
  e.preventDefault();
  clearMessage();

  const email = document.getElementById('login-email').value.trim();
  const password = document.getElementById('login-password').value.trim();

  if (!email || !password) {
    showMessage('Lütfen e-posta ve şifre girin.', 'error');
    return;
  }

  const btnSubmit = loginForm.querySelector('button[type="submit"]');
  btnSubmit.disabled = true;
  btnSubmit.textContent = 'Giriş Yapılıyor...';

  try {
    const response = await fetch(`${API_BASE_URL}/api/auth/login`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ email, password })
    });

    if (!response.ok) {
      showMessage('Giriş başarısız. E-posta veya şifre hatalı.', 'error');
      return;
    }

    const data = await response.json();
    if (data && data.token) {
      const user = data.user || {};
      setToken(data.token, user.email || email, `${user.firstName || ''} ${user.lastName || ''}`.trim() || email);
      showMessage('Giriş başarılı! Hoş geldiniz.', 'success');
      onUserLoggedIn();
      switchTab('postings');
    } else {
      showMessage('Geçersiz token yanıtı alındı.', 'error');
    }
  } catch (err) {
    console.error('Login Error:', err);
    showMessage('Sunucuya erişilemedi.', 'error');
  } finally {
    btnSubmit.disabled = false;
    btnSubmit.textContent = 'Giriş Yap (POST /api/auth/login)';
  }
}

function handleLogout() {
  setToken(null);
  showMessage('Oturum kapatıldı.', 'info');
  switchTab('auth');
  if (userSkillsList) userSkillsList.innerHTML = '<div class="empty-state">Önce giriş yapmalısınız.</div>';
  if (postingsContainer) postingsContainer.innerHTML = '<div class="empty-state">Önce giriş yapmalısınız.</div>';
  if (scoreResultCard) scoreResultCard.classList.add('hidden');
}

// 3. STUDENT PROFILE (Requirement 3)
async function loadProfile() {
  const token = getToken();
  if (!token) return;

  try {
    const response = await fetch(`${API_BASE_URL}/api/students/profile/me`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      }
    });

    if (response.status === 404) {
      if (profileStatusBadge) {
        profileStatusBadge.textContent = 'Profil Henüz Oluşturulmadı';
        profileStatusBadge.className = 'badge badge-error';
      }
      return;
    }

    if (!response.ok) {
      return;
    }

    const profile = await response.json();
    populateProfileForm(profile);
    if (profileStatusBadge) {
      profileStatusBadge.textContent = 'Profil Kayıtlı & Aktif';
      profileStatusBadge.className = 'badge badge-success';
    }
  } catch (err) {
    console.error('Load Profile Error:', err);
  }
}

function populateProfileForm(p) {
  if (!profileForm) return;
  if (p.studentNumber) document.getElementById('prof-student-number').value = p.studentNumber;
  if (p.university) document.getElementById('prof-university').value = p.university;
  if (p.department) document.getElementById('prof-department').value = p.department;
  if (p.gradeLevel) document.getElementById('prof-grade-level').value = p.gradeLevel;
  if (p.gpa != null) document.getElementById('prof-gpa').value = p.gpa;
  if (p.cvUrl) document.getElementById('prof-cv-url').value = p.cvUrl;
  if (p.summary) document.getElementById('prof-summary').value = p.summary;
  if (p.githubUrl) document.getElementById('prof-github-url').value = p.githubUrl;
  if (p.linkedinUrl) document.getElementById('prof-linkedin-url').value = p.linkedinUrl;
  if (p.portfolioUrl) document.getElementById('prof-portfolio-url').value = p.portfolioUrl;
  if (p.city) document.getElementById('prof-city').value = p.city;
  if (p.graduationYear) document.getElementById('prof-graduation-year').value = p.graduationYear;
  if (p.preferredWorkMode) document.getElementById('prof-work-mode').value = p.preferredWorkMode;
}

function getProfileFormData() {
  return {
    studentNumber: document.getElementById('prof-student-number').value.trim() || null,
    university: document.getElementById('prof-university').value.trim() || null,
    department: document.getElementById('prof-department').value.trim() || null,
    gradeLevel: parseInt(document.getElementById('prof-grade-level').value) || null,
    gpa: parseFloat(document.getElementById('prof-gpa').value) || null,
    cvUrl: document.getElementById('prof-cv-url').value.trim() || null,
    summary: document.getElementById('prof-summary').value.trim() || null,
    githubUrl: document.getElementById('prof-github-url').value.trim() || null,
    linkedinUrl: document.getElementById('prof-linkedin-url').value.trim() || null,
    portfolioUrl: document.getElementById('prof-portfolio-url').value.trim() || null,
    city: document.getElementById('prof-city').value.trim() || null,
    graduationYear: parseInt(document.getElementById('prof-graduation-year').value) || null,
    preferredWorkMode: document.getElementById('prof-work-mode').value || null
  };
}

async function handleSaveProfile(e) {
  e.preventDefault();
  clearMessage();

  const token = getToken();
  if (!token) {
    showMessage('Önce giriş yapmalısınız.', 'error');
    switchTab('auth');
    return;
  }

  const profileData = getProfileFormData();
  btnSaveProfile.disabled = true;
  btnSaveProfile.textContent = 'Kaydediliyor...';

  try {
    // Check if profile exists first
    const checkRes = await fetch(`${API_BASE_URL}/api/students/profile/me`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      }
    });

    let saveRes;

    if (checkRes.ok) {
      // Update with PUT
      saveRes = await fetch(`${API_BASE_URL}/api/students/profile`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify(profileData)
      });
    } else {
      // Create with POST
      saveRes = await fetch(`${API_BASE_URL}/api/students/profile`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify(profileData)
      });

      // Handle 409 Fallback to PUT
      if (saveRes.status === 409) {
        showMessage('Profil sistemde mevcut. Güncelleniyor...', 'info');
        saveRes = await fetch(`${API_BASE_URL}/api/students/profile`, {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
          },
          body: JSON.stringify(profileData)
        });
      }
    }

    if (!saveRes.ok) {
      handleApiError(saveRes.status, 'Profil kaydedilemedi.');
      return;
    }

    const updatedProfile = await saveRes.json();
    populateProfileForm(updatedProfile);
    if (profileStatusBadge) {
      profileStatusBadge.textContent = 'Profil Kayıtlı & Aktif';
      profileStatusBadge.className = 'badge badge-success';
    }
    showMessage('Profil bilgileriniz başarıyla kaydedildi!', 'success');
  } catch (err) {
    console.error('Save Profile Error:', err);
    showMessage('Sunucu bağlantı hatası.', 'error');
  } finally {
    btnSaveProfile.disabled = false;
    btnSaveProfile.textContent = 'Profil Bilgilerini Kaydet / Güncelle';
  }
}

// 4. SKILL MANAGEMENT (Requirement 4)
async function loadSkillCatalog() {
  const token = getToken();
  if (!token || !skillSelect) return;

  try {
    const response = await fetch(`${API_BASE_URL}/api/skills`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      }
    });

    if (!response.ok) return;

    availableSkills = await response.json();
    
    let html = '<option value="">-- Beceri Seçin --</option>';
    availableSkills.forEach(s => {
      html += `<option value="${s.id}">${escapeHtml(s.name)} (${escapeHtml(s.category || 'Genel')})</option>`;
    });
    skillSelect.innerHTML = html;
  } catch (err) {
    console.error('Load Skills Catalogue Error:', err);
  }
}

async function loadUserSkills() {
  const token = getToken();
  if (!token || !userSkillsList) return;

  userSkillsList.innerHTML = '<div class="empty-state">Beceriler yükleniyor...</div>';

  try {
    const response = await fetch(`${API_BASE_URL}/api/students/profile/me/skills`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      }
    });

    if (response.status === 404) {
      userSkillsList.innerHTML = '<div class="empty-state">Profil henüz oluşturulmadığı için beceriler listelenemiyor. Lütfen "Öğrenci Profili" sekmesinden profili kaydedin.</div>';
      return;
    }

    if (!response.ok) {
      handleApiError(response.status);
      userSkillsList.innerHTML = '<div class="empty-state">Beceriler yüklenemedi.</div>';
      return;
    }

    const skills = await response.json();
    renderUserSkills(skills);
  } catch (err) {
    console.error('Load User Skills Error:', err);
    userSkillsList.innerHTML = '<div class="empty-state">Sunucu bağlantı hatası.</div>';
  }
}

function renderUserSkills(skills) {
  if (!userSkillsList) return;

  if (!Array.isArray(skills) || skills.length === 0) {
    userSkillsList.innerHTML = '<div class="empty-state">Henüz eklenmiş bir beceri bulunmuyor.</div>';
    return;
  }

  let html = '<div class="skills-grid-list">';
  skills.forEach(s => {
    const levelClass = s.level === 'ADVANCED' ? 'badge-success' : (s.level === 'INTERMEDIATE' ? 'badge-onsite' : 'badge-hybrid');

    html += `
      <div class="skill-item-card">
        <div class="skill-item-main">
          <span class="skill-item-name">${escapeHtml(s.skillName || 'Beceri')}</span>
          <span class="badge ${levelClass}">${s.level || 'BEGINNER'}</span>
        </div>
        <div class="skill-item-sub">
          <span>Kategori: ${escapeHtml(s.skillCategory || 'Genel')}</span>
          <span>Deneyim: ${s.yearsOfExperience || 0} Yıl</span>
        </div>
        <button class="btn btn-outline btn-xs btn-delete-skill" onclick="deleteUserSkill(${s.id})">Sil</button>
      </div>
    `;
  });
  html += '</div>';

  userSkillsList.innerHTML = html;
}

async function handleAddSkill(e) {
  e.preventDefault();
  clearMessage();

  const token = getToken();
  if (!token) {
    showMessage('Önce giriş yapmalısınız.', 'error');
    switchTab('auth');
    return;
  }

  const skillId = parseInt(skillSelect.value);
  const level = skillLevelSelect.value;
  const yearsOfExperience = parseInt(skillYearsInput.value) || 0;

  if (!skillId || !level) {
    showMessage('Lütfen bir beceri ve seviye seçin.', 'error');
    return;
  }

  const btnSubmit = skillForm.querySelector('button[type="submit"]');
  btnSubmit.disabled = true;
  btnSubmit.textContent = 'Ekleniyor...';

  try {
    const response = await fetch(`${API_BASE_URL}/api/students/profile/me/skills`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      },
      body: JSON.stringify({ skillId, level, yearsOfExperience })
    });

    if (!response.ok) {
      if (response.status === 409) {
        showMessage('Bu beceri profilinize zaten eklenmiş.', 'error');
      } else if (response.status === 404) {
        showMessage('Lütfen önce "Öğrenci Profili" sekmesinden profilinizi oluşturun.', 'error');
      } else {
        handleApiError(response.status);
      }
      return;
    }

    showMessage('Beceri başarıyla eklendi!', 'success');
    skillForm.reset();
    loadUserSkills();
  } catch (err) {
    console.error('Add Skill Error:', err);
    showMessage('Beceri eklenirken sunucu hatası oluştu.', 'error');
  } finally {
    btnSubmit.disabled = false;
    btnSubmit.textContent = 'Beceriyi Profilime Ekle';
  }
}

async function deleteUserSkill(studentSkillId) {
  const token = getToken();
  if (!token) return;

  if (!confirm('Bu beceriyi silmek istediğinizden emin misiniz?')) return;

  try {
    const response = await fetch(`${API_BASE_URL}/api/students/profile/me/skills/${studentSkillId}`, {
      method: 'DELETE',
      headers: {
        'Authorization': `Bearer ${token}`
      }
    });

    if (response.ok || response.status === 204) {
      showMessage('Beceri profilinizden silindi.', 'success');
      loadUserSkills();
    } else {
      handleApiError(response.status);
    }
  } catch (err) {
    console.error('Delete Skill Error:', err);
    showMessage('Beceri silinirken hata oluştu.', 'error');
  }
}

// 5. POSTINGS LIST (Requirement 5)
async function fetchPostings() {
  const token = getToken();
  if (!token) {
    showMessage('İlanları görüntülemek için önce giriş yapmalısınız.', 'error');
    if (postingsContainer) postingsContainer.innerHTML = '<div class="empty-state">İlanları görüntülemek için giriş yapmalısınız.</div>';
    return;
  }

  if (btnFetchPostings) {
    btnFetchPostings.disabled = true;
    btnFetchPostings.textContent = 'Yükleniyor...';
  }
  if (postingsContainer) {
    postingsContainer.innerHTML = '<div class="empty-state">Yayındaki staj ilanları yükleniyor...</div>';
  }

  try {
    const response = await fetch(`${API_BASE_URL}/api/internship-postings`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      }
    });

    if (!response.ok) {
      handleApiError(response.status);
      if (postingsContainer) postingsContainer.innerHTML = '<div class="empty-state">İlanlar yüklenemedi.</div>';
      return;
    }

    const postings = await response.json();
    renderPostings(postings);
  } catch (err) {
    console.error('Fetch Postings Error:', err);
    showMessage('Sunucuya ulaşılamadı. (http://localhost:8081)', 'error');
    if (postingsContainer) postingsContainer.innerHTML = '<div class="empty-state">Sunucu bağlantı hatası.</div>';
  } finally {
    if (btnFetchPostings) {
      btnFetchPostings.disabled = false;
      btnFetchPostings.textContent = 'İlanları Yenile (GET /api/internship-postings)';
    }
  }
}

function renderPostings(postings) {
  if (!postingsContainer) return;

  if (!Array.isArray(postings) || postings.length === 0) {
    postingsContainer.innerHTML = '<div class="empty-state">Henüz yayınlanmış bir staj ilanı bulunmuyor.</div>';
    return;
  }

  let html = '';
  postings.forEach(item => {
    const workModeBadgeClass = item.workMode === 'HYBRID' ? 'badge-hybrid' : (item.workMode === 'REMOTE' ? 'badge-remote' : 'badge-onsite');
    const statusBadgeClass = item.status === 'PUBLISHED' ? 'badge-success' : 'badge-error';
    const deadline = item.applicationDeadline ? formatDate(item.applicationDeadline) : 'Belirtilmedi';

    html += `
      <div class="posting-item-card">
        <div class="posting-item-header">
          <div>
            <span class="posting-id">#ID: ${item.id}</span>
            <h4 class="posting-title-text">${escapeHtml(item.title || item.positionName || 'Staj İlanı')}</h4>
          </div>
          <span class="badge ${statusBadgeClass}">${item.status || 'PUBLISHED'}</span>
        </div>
        
        <div class="posting-details-grid">
          <div class="posting-info-item">
            <span class="info-label">Şirket:</span>
            <span class="info-val font-bold">${escapeHtml(item.companyName || 'Belirtilmedi')}</span>
          </div>
          <div class="posting-info-item">
            <span class="info-label">Departman:</span>
            <span class="info-val">${escapeHtml(item.department || 'Yazılım')}</span>
          </div>
          <div class="posting-info-item">
            <span class="info-label">Şehir:</span>
            <span class="info-val">${escapeHtml(item.city || 'Belirtilmedi')}</span>
          </div>
          <div class="posting-info-item">
            <span class="info-label">Çalışma Modeli:</span>
            <span class="badge ${workModeBadgeClass}">${item.workMode || 'Belirtilmedi'}</span>
          </div>
          <div class="posting-info-item">
            <span class="info-label">Son Başvuru:</span>
            <span class="info-val">${deadline}</span>
          </div>
        </div>

        <div class="posting-item-footer">
          <button class="btn btn-primary btn-sm btn-calculate-score" data-posting-id="${item.id}" onclick="calculateScore(${item.id}, '${escapeJsString(item.title || item.positionName)}', '${escapeJsString(item.companyName)}')">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"/><polyline points="22 4 12 14.01 9 11.01"/></svg>
            Uygunluk Skoru Hesapla
          </button>
        </div>
      </div>
    `;
  });

  postingsContainer.innerHTML = html;
}

// 6. MATCH SCORE CALCULATION (Requirement 6)
async function calculateScore(postingId, postingTitle, companyName) {
  clearMessage();
  const token = getToken();

  if (!token) {
    showMessage('Uygunluk skoru hesaplamak için önce giriş yapmalısınız.', 'error');
    switchTab('auth');
    return;
  }

  const targetBtn = document.querySelector(`button[data-posting-id="${postingId}"]`);
  let origText = '';
  if (targetBtn) {
    origText = targetBtn.innerHTML;
    targetBtn.disabled = true;
    targetBtn.innerHTML = 'Hesaplanıyor...';
  }

  try {
    const response = await fetch(`${API_BASE_URL}/api/match-scores/calculate/${postingId}`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      }
    });

    if (!response.ok) {
      if (response.status === 404) {
        showMessage('Önce "Öğrenci Profili" sekmesinden profilinizi oluşturmalısınız.', 'error');
      } else {
        handleApiError(response.status);
      }
      return;
    }

    const result = await response.json();
    renderScoreResult(result, postingTitle, companyName);
    showMessage(`"${postingTitle}" ilanı için uygunluk skorunuz başarıyla hesaplandı!`, 'success');
  } catch (err) {
    console.error('Calculate Score Error:', err);
    showMessage('Backend sunucusuna erişilemedi.', 'error');
  } finally {
    if (targetBtn) {
      targetBtn.disabled = false;
      targetBtn.innerHTML = origText;
    }
  }
}

function renderScoreResult(data, postingTitle, companyName) {
  if (!scoreResultCard) return;

  scoreResultCard.classList.remove('hidden');

  if (resultPostingTitle) {
    const pTitle = data.postingTitle || postingTitle || 'Staj İlanı';
    const cName = data.companyName || companyName || '';
    resultPostingTitle.textContent = cName ? `${pTitle} - ${cName} (#ID: ${data.postingId || ''})` : `${pTitle} (#ID: ${data.postingId || ''})`;
  }

  const scoreVal = data.totalScore != null ? parseFloat(data.totalScore) : 0;
  if (resultTotalScore) {
    resultTotalScore.textContent = `%${scoreVal.toFixed(1)}`;
  }
  if (resultScoreBar) {
    resultScoreBar.style.width = `${Math.min(100, Math.max(0, scoreVal))}%`;
    if (scoreVal >= 80) {
      resultScoreBar.className = 'progress-bar match-bar-fill success';
    } else if (scoreVal >= 50) {
      resultScoreBar.className = 'progress-bar match-bar-fill primary';
    } else {
      resultScoreBar.className = 'progress-bar match-bar-fill teal';
    }
  }

  if (resultMatchedCount) {
    resultMatchedCount.textContent = data.matchedCriteriaCount != null ? data.matchedCriteriaCount : 0;
  }
  if (resultTotalCount) {
    resultTotalCount.textContent = data.totalCriteriaCount != null ? data.totalCriteriaCount : 0;
  }

  // Parse Details
  let details = data.details;
  if (!details && data.detailsJson) {
    try {
      details = JSON.parse(data.detailsJson);
    } catch (e) {
      details = [];
    }
  }

  if (resultDetailsList) {
    if (Array.isArray(details) && details.length > 0) {
      let detailsHtml = '';
      details.forEach(item => {
        const isMatched = item.matched === true || item.matched === 'true';
        const badgeClass = isMatched ? 'badge-success' : 'badge-error';
        const badgeText = isMatched ? 'Eşleşti' : 'Uyuşmadı';

        detailsHtml += `
          <div class="detail-item ${isMatched ? 'matched' : 'not-matched'}">
            <div class="detail-item-header">
              <span class="detail-name">${escapeHtml(item.criterionName || item.criterionType || 'Kriter')}</span>
              <span class="badge ${badgeClass}">${badgeText}</span>
            </div>
            <div class="detail-meta">
              <span>Kriter Tipi: ${escapeHtml(item.criterionType || 'Genel')}</span> | 
              <span>Kriter Ağırlığı: %${item.weight != null ? item.weight : 0}</span> | 
              <span>Kazanılan Skor: ${item.earnedScore != null ? item.earnedScore : 0}</span>
            </div>
            ${item.description ? `<div class="detail-desc">${escapeHtml(item.description)}</div>` : ''}
          </div>
        `;
      });
      resultDetailsList.innerHTML = detailsHtml;
    } else {
      resultDetailsList.innerHTML = '<div class="empty-state">Kriter detay bilgisi bulunmuyor.</div>';
    }
  }

  scoreResultCard.scrollIntoView({ behavior: 'smooth', block: 'center' });
}

// Utility Escapers & Formatters
function escapeHtml(str) {
  if (!str) return '';
  return String(str)
    .replace(/&/g, '&amp;')
    .replace(/</g, '&lt;')
    .replace(/>/g, '&gt;')
    .replace(/"/g, '&quot;')
    .replace(/'/g, '&#039;');
}

function escapeJsString(str) {
  if (!str) return '';
  return String(str).replace(/'/g, "\\'").replace(/"/g, '\\"');
}

function formatDate(dateStr) {
  if (!dateStr) return '';
  try {
    const parts = dateStr.split('-');
    if (parts.length === 3) {
      return `${parts[2]}.${parts[1]}.${parts[0]}`;
    }
  } catch (e) {}
  return dateStr;
}
