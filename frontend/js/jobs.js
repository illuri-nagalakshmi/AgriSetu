/**
 * AgriSetu Jobs Rendering, Filtering, and Application Module
 * Supports full internationalization across English, Telugu, and Hindi.
 */

function getTranslatedCategory(category) {
  if (!category) return '';
  const key = 'cat_' + category.toLowerCase().trim().replace(/ /g, '_');
  return window.i18n ? window.i18n.t(key, category) : category;
}

function renderJobCard(job) {
  const percentFilled = Math.min(100, Math.round((job.confirmedWorkers / job.requiredWorkers) * 100));
  const isFull = job.remainingVacancies <= 0;
  const user = window.auth ? window.auth.getUser() : null;
  const isLabourer = user && user.role === 'LABOURER';
  const t = window.i18n ? window.i18n.t : (k, f) => f || k;

  const wageUnit = job.wageBasis === 'DAILY' ? t('card_per_day', '/ day') : t('card_per_hr', '/ hr');
  const categoryLabel = getTranslatedCategory(job.category);
  const capacityLabel = `${t('label_capacity', 'Capacity:')} ${job.confirmedWorkers} / ${job.requiredWorkers} ${t('label_workers', 'Workers')}`;
  const vacanciesLabel = isFull ? t('card_full', 'FULL (0 vacancies)') : `${job.remainingVacancies} ${t('card_vacancies_open', 'vacancies open')}`;

  return `
    <div class="job-card" id="job-card-${job.id}">
      <div>
        <div class="job-header">
          <div>
            <span class="badge badge-info">${escapeHtml(categoryLabel)}</span>
            <h3 style="margin-top:8px;">
              <a href="job-details.html?id=${job.id}" class="job-title">${escapeHtml(job.title)}</a>
            </h3>
            <p style="font-size:0.85rem; color:var(--text-secondary); margin-top:2px;">
              👨‍🌾 ${escapeHtml(job.farmerName)}
            </p>
          </div>
          <div class="job-wage">
            ₹${job.wageAmount} <span style="font-size:0.85rem; font-weight:600; color:var(--text-secondary);">${wageUnit}</span>
          </div>
        </div>

        <div class="job-meta" style="margin-top:14px;">
          <span>📍 ${escapeHtml(job.village)}, ${escapeHtml(job.district)}</span>
          <span>📅 ${job.startDate} → ${job.endDate}</span>
          <span>⏰ ${escapeHtml(job.workingHours)}</span>
          ${job.mealsProvided ? `<span class="badge-tag" title="${t('card_meals', 'Meals Provided')}">🍲 ${t('card_meals', 'Meals Provided')}</span>` : ''}
          ${job.accommodationProvided ? `<span class="badge-tag" title="${t('card_accommodation', 'Accommodation Provided')}">🏠 ${t('card_accommodation', 'Accommodation')}</span>` : ''}
          ${job.transportationProvided ? `<span class="badge-tag" title="${t('card_transport', 'Transport Arranged')}">🚜 ${t('card_transport', 'Transport')}</span>` : ''}
        </div>
      </div>

      <!-- Vacancy Progress Bar -->
      <div style="background:var(--background); padding:10px 14px; border-radius:var(--border-radius-sm); border:1px solid var(--border-color); margin-top:14px;">
        <div style="display:flex; justify-content:space-between; font-size:0.85rem; font-weight:700; margin-bottom:6px;">
          <span style="color:var(--text-primary);">${capacityLabel}</span>
          <span class="${isFull ? 'text-danger' : 'text-success'}">
            ${vacanciesLabel}
          </span>
        </div>
        <div class="progress-bar">
          <div class="progress-fill" style="width: ${percentFilled}%; background: ${isFull ? 'var(--color-danger)' : 'var(--accent-green)'};"></div>
        </div>
      </div>

      <div style="display:flex; justify-content:space-between; align-items:center; gap:10px; margin-top:14px; padding-top:12px; border-top:1px solid var(--border-color);">
        <a href="job-details.html?id=${job.id}" class="btn btn-outline btn-sm">${t('btn_view_details', 'View Details')}</a>

        <div style="display:flex; gap:8px;">
          ${isLabourer ? `
            <button onclick="toggleSaveJob(${job.id}, this)" class="btn btn-secondary btn-sm" title="${t('btn_save', 'Save')}">
              ${job.saved ? t('btn_saved', '★ Saved') : t('btn_save', '☆ Save')}
            </button>
          ` : ''}

          ${!isFull && job.status === 'PUBLISHED' ? `
            <button onclick="openApplyModal(${job.id}, '${escapeHtml(job.title)}')" class="btn btn-primary btn-sm">
              ${t('btn_apply_now', 'Apply Now')}
            </button>
          ` : `
            <button class="btn btn-secondary btn-sm" disabled style="opacity:0.65; cursor:not-allowed;">
              ${isFull ? t('btn_fully_staffed', 'Fully Staffed') : job.status}
            </button>
          `}
        </div>
      </div>
    </div>
  `;
}

function escapeHtml(str) {
  if (!str) return '';
  return str.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/"/g, '&quot;');
}

async function toggleSaveJob(jobId, btn) {
  if (!window.auth || !window.auth.isLoggedIn()) {
    window.location.href = 'login.html';
    return;
  }
  const t = window.i18n ? window.i18n.t : (k, f) => f || k;
  const isSaved = btn.textContent.includes('★');
  try {
    if (isSaved) {
      await window.api.delete(`/api/saved-jobs/${jobId}`);
      btn.textContent = t('btn_save', '☆ Save');
      if (window.toast) window.toast.info('Job removed from saved list');
    } else {
      await window.api.post(`/api/saved-jobs/${jobId}`, {});
      btn.textContent = t('btn_saved', '★ Saved');
      if (window.toast) window.toast.success('Job saved to your bookmarks');
    }
  } catch (err) {
    if (window.toast) window.toast.error(err.message || 'Failed to update bookmark');
  }
}

// Apply Modal
function openApplyModal(jobId, jobTitle) {
  if (!window.auth || !window.auth.isLoggedIn()) {
    window.location.href = `login.html?redirect=job-details.html?id=${jobId}`;
    return;
  }
  const user = window.auth.getUser();
  if (user.role !== 'LABOURER' && user.role !== 'ADMIN') {
    if (window.toast) window.toast.error('Only agricultural labourers can apply for jobs');
    return;
  }
  const t = window.i18n ? window.i18n.t : (k, f) => f || k;

  let modal = document.getElementById('apply-modal');
  if (!modal) {
    modal = document.createElement('div');
    modal.id = 'apply-modal';
    modal.className = 'modal-overlay';
    document.body.appendChild(modal);
  }

  modal.innerHTML = `
    <div class="modal-content">
      <div class="modal-header">
        <h3 style="color:var(--primary-green); font-size:1.3rem;">${t('modal_apply_title', 'Apply for Agricultural Work')}</h3>
        <button class="modal-close" onclick="closeApplyModal()" aria-label="Close Modal">&times;</button>
      </div>
      <p style="margin-bottom:14px; font-weight:700; color:var(--secondary-green); font-size:1.05rem;">${escapeHtml(jobTitle)}</p>
      
      <div class="alert alert-info" style="font-size:0.88rem; padding:12px 14px; border-radius:8px; margin-bottom:16px;">
        🔒 <strong>${t('privacy_notice_title', 'Contact Privacy:')}</strong> ${t('privacy_notice_text', 'Your mobile number will remain hidden from the farmer until they review and accept your application.')}
      </div>

      <form id="apply-form" onsubmit="submitApplication(event, ${jobId})">
        <div class="form-group">
          <label class="form-label">${t('label_cover_note', 'Cover Note / Message to Farmer (Optional)')}</label>
          <textarea id="apply-cover-note" class="form-control" rows="3" placeholder="${t('placeholder_cover_note', 'Describe your experience with this crop, tools you can bring, or team availability...')}"></textarea>
        </div>
        <div style="display:flex; justify-content:flex-end; gap:10px; margin-top:20px;">
          <button type="button" class="btn btn-secondary" onclick="closeApplyModal()">${t('btn_cancel', 'Cancel')}</button>
          <button type="submit" id="btn-submit-app" class="btn btn-primary">${t('btn_submit_app', 'Submit Application')}</button>
        </div>
      </form>
    </div>
  `;

  modal.style.display = 'flex';
}

function closeApplyModal() {
  const modal = document.getElementById('apply-modal');
  if (modal) modal.style.display = 'none';
}

async function submitApplication(e, jobId) {
  e.preventDefault();
  const t = window.i18n ? window.i18n.t : (k, f) => f || k;
  const btn = document.getElementById('btn-submit-app');
  btn.disabled = true;
  btn.textContent = t('btn_submitting', 'Submitting...');

  const coverNote = document.getElementById('apply-cover-note').value.trim();

  try {
    await window.api.post('/api/applications/apply', {
      jobId,
      coverNote
    });
    closeApplyModal();
    if (window.toast) window.toast.success(t('app_submitted_success', 'Application submitted successfully! The farmer has been notified.'));
    setTimeout(() => window.location.reload(), 1200);
  } catch (err) {
    btn.disabled = false;
    btn.textContent = t('btn_submit_app', 'Submit Application');
    if (window.toast) window.toast.error(err.message || t('app_submitted_error', 'Failed to submit application'));
  }
}

// Re-render open modals or dynamic listings when language changes
window.addEventListener('languageChanged', () => {
  // If jobs are loaded on current page, trigger re-render if function exists
  if (typeof loadJobs === 'function') {
    loadJobs(typeof currentPage !== 'undefined' ? currentPage : 0);
  } else if (typeof loadFeaturedJobs === 'function') {
    loadFeaturedJobs();
  }
});
