/* ==========================================
   InternMatch - Vanilla JS Backend API Client
   ========================================== */

const API_BASE_URL = 'http://localhost:8081';

// DOM Elements
let loginForm, emailInput, passwordInput, btnLogin, btnLogout, tokenStatusBadge;
let btnFetchPostings, postingsContainer, apiMessageBox;
let scoreResultCard, resultPostingTitle, resultTotalScore, resultScoreBar;
let resultMatchedCount, resultTotalCount, resultDetailsList;

// Initialize App
document.addEventListener('DOMContentLoaded', () => {
  loginForm = document.getElementById('login-form');
  emailInput = document.getElementById('login-email');
  passwordInput = document.getElementById('login-password');
  btnLogin = document.getElementById('btn-login');
  btnLogout = document.getElementById('btn-logout');
  tokenStatusBadge = document.getElementById('token-status-badge');
  btnFetchPostings = document.getElementById('btn-fetch-postings');
  postingsContainer = document.getElementById('postings-container');
  apiMessageBox = document.getElementById('api-message-box');

  scoreResultCard = document.getElementById('score-result-card');
  resultPostingTitle = document.getElementById('result-posting-title');
  resultTotalScore = document.getElementById('result-total-score');
  resultScoreBar = document.getElementById('result-score-bar');
  resultMatchedCount = document.getElementById('result-matched-count');
  resultTotalCount = document.getElementById('result-total-count');
  resultDetailsList = document.getElementById('result-details-list');

  updateAuthUI();

  if (loginForm) {
    loginForm.addEventListener('submit', handleLogin);
  }

  if (btnLogout) {
    btnLogout.addEventListener('click', handleLogout);
  }

  if (btnFetchPostings) {
    btnFetchPostings.addEventListener('click', () => {
      clearMessage();
      fetchPostings();
    });
  }

  // Auto-fetch if token already present
  if (getToken()) {
    fetchPostings();
  }
});

// Helper: Get stored JWT Token
function getToken() {
  return localStorage.getItem('jwtToken');
}

// Helper: Set stored JWT Token
function setToken(token) {
  if (token) {
    localStorage.setItem('jwtToken', token);
  } else {
    localStorage.removeItem('jwtToken');
  }
  updateAuthUI();
}

// Update UI based on auth state
function updateAuthUI() {
  const token = getToken();
  if (!tokenStatusBadge) return;

  if (token) {
    tokenStatusBadge.textContent = 'Giriş Yapıldı - Token Aktif';
    tokenStatusBadge.className = 'badge badge-success';
    if (btnLogout) btnLogout.classList.remove('hidden');
    if (btnLogin) btnLogin.textContent = 'Yeniden Giriş Yap';
  } else {
    tokenStatusBadge.textContent = 'Giriş Yapılmadı';
    tokenStatusBadge.className = 'badge badge-error';
    if (btnLogout) btnLogout.classList.add('hidden');
    if (btnLogin) btnLogin.textContent = 'Giriş Yap (POST /api/auth/login)';
  }
}

// Display Message Banner
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

// Handle Error Responses (Requirement 7)
function handleApiError(status, customMsg = '') {
  let text = '';
  switch (status) {
    case 401:
      text = 'Token yok veya süresi dolmuş, tekrar giriş yap.';
      setToken(null);
      break;
    case 403:
      text = 'Bu işlem için yetkin yok.';
      break;
    case 404:
      text = 'Kayıt bulunamadı.';
      break;
    case 500:
      text = 'Sunucu hatası oluştu.';
      break;
    default:
      text = customMsg || `Beklenmeyen bir hata oluştu (Status: ${status}).`;
  }
  showMessage(text, 'error');
}

// 1. LOGIN ACTION (Requirement 4)
async function handleLogin(e) {
  e.preventDefault();
  clearMessage();

  const email = emailInput.value.trim();
  const password = passwordInput.value.trim();

  if (!email || !password) {
    showMessage('Lütfen e-posta ve şifre alanlarını doldurun.', 'error');
    return;
  }

  btnLogin.disabled = true;
  btnLogin.textContent = 'Giriş Yapılıyor...';

  try {
    const response = await fetch(`${API_BASE_URL}/api/auth/login`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ email, password })
    });

    if (!response.ok) {
      handleApiError(response.status, 'Giriş yapılamadı. Kullanıcı adı veya şifre hatalı.');
      return;
    }

    const data = await response.json();
    if (data && data.token) {
      setToken(data.token);
      showMessage('Giriş başarılı! İlanlar getiriliyor...', 'success');
      fetchPostings();
    } else {
      showMessage('Sunucudan geçerli bir token alınamadı.', 'error');
    }
  } catch (err) {
    console.error('Login Error:', err);
    showMessage('Backend sunucusuna ulaşılamadı. Lütfen sunucunun (http://localhost:8081) çalıştığından emin olun.', 'error');
  } finally {
    btnLogin.disabled = false;
    updateAuthUI();
  }
}

// LOGOUT ACTION
function handleLogout() {
  setToken(null);
  showMessage('Çıkış yapıldı.', 'info');
  if (postingsContainer) {
    postingsContainer.innerHTML = '<div class="empty-state">İlanları listelemek için giriş yapın.</div>';
  }
  if (scoreResultCard) {
    scoreResultCard.classList.add('hidden');
  }
}

// 2. FETCH POSTINGS (Requirement 5)
async function fetchPostings() {
  const token = getToken();
  if (!token) {
    showMessage('İlanları listelemek için önce giriş yapmalısınız.', 'error');
    return;
  }

  if (btnFetchPostings) {
    btnFetchPostings.disabled = true;
    btnFetchPostings.textContent = 'Yükleniyor...';
  }
  if (postingsContainer) {
    postingsContainer.innerHTML = '<div class="empty-state">Yayındaki ilanlar yükleniyor...</div>';
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
      if (postingsContainer) {
        postingsContainer.innerHTML = '<div class="empty-state">İlanlar yüklenemedi.</div>';
      }
      return;
    }

    const postings = await response.json();
    renderPostings(postings);
  } catch (err) {
    console.error('Fetch Postings Error:', err);
    showMessage('Backend sunucusuna ulaşılamadı. (http://localhost:8081)', 'error');
    if (postingsContainer) {
      postingsContainer.innerHTML = '<div class="empty-state">Sunucu bağlantı hatası.</div>';
    }
  } finally {
    if (btnFetchPostings) {
      btnFetchPostings.disabled = false;
      btnFetchPostings.textContent = 'İlanları Yenile (GET /api/internship-postings)';
    }
  }
}

// Render Postings List (Card View)
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
            <span class="info-label">Şehir:</span>
            <span class="info-val">${escapeHtml(item.city || 'Belirtilmedi')}</span>
          </div>
          <div class="posting-info-item">
            <span class="info-label">Çalışma Modeli:</span>
            <span class="badge ${workModeBadgeClass}">${item.workMode || 'Belirtilmedi'}</span>
          </div>
          ${item.minGpa ? `
          <div class="posting-info-item">
            <span class="info-label">Min GPA:</span>
            <span class="info-val">${item.minGpa}</span>
          </div>` : ''}
        </div>

        <div class="posting-item-footer">
          <button class="btn btn-primary btn-sm btn-calculate-score" data-posting-id="${item.id}" onclick="calculateScore(${item.id}, '${escapeJsString(item.title || item.positionName)}')">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"/><polyline points="22 4 12 14.01 9 11.01"/></svg>
            Uygunluk Skoru Hesapla
          </button>
        </div>
      </div>
    `;
  });

  postingsContainer.innerHTML = html;
}

// 3. CALCULATE MATCH SCORE (Requirement 6)
async function calculateScore(postingId, postingTitle) {
  clearMessage();
  const token = getToken();

  if (!token) {
    showMessage('Uygunluk skoru hesaplamak için önce giriş yapmalısınız.', 'error');
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
      handleApiError(response.status);
      return;
    }

    const result = await response.json();
    renderScoreResult(result, postingTitle);
    showMessage(`İlan #${postingId} için uygunluk skoru başarıyla hesaplandı!`, 'success');
  } catch (err) {
    console.error('Calculate Score Error:', err);
    showMessage('Backend sunucusuna ulaşılamadı.', 'error');
  } finally {
    if (targetBtn) {
      targetBtn.disabled = false;
      targetBtn.innerHTML = origText;
    }
  }
}

// Render Score Calculation Result
function renderScoreResult(data, postingTitle) {
  if (!scoreResultCard) return;

  scoreResultCard.classList.remove('hidden');

  if (resultPostingTitle) {
    resultPostingTitle.textContent = postingTitle ? `${postingTitle} (#ID: ${data.postingId || ''})` : `İlan #${data.postingId || ''}`;
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

  // Parse & render details / detailsJson
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
              <span>Tip: ${escapeHtml(item.criterionType || 'Genel')}</span> | 
              <span>Ağırlık: %${item.weight != null ? item.weight : 0}</span> | 
              <span>Kazanılan Puan: ${item.earnedScore != null ? item.earnedScore : 0}</span>
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

// Utility Escapers
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
