/**
 * AgriSetu Jobs Rendering, Filtering, and Application Module
 */

function renderJobCard(job) {
  const percentFilled = Math.min(100, Math.round((job.confirmedWorkers / job.requiredWorkers) * 100));
  const isFull = job.remainingVacancies <= 0;
  const user = window.auth ? window.auth.getUser() : null;
  const isLabourer = user && user.role === 'LABOURER';

  return `
    <div class="job-card" id="job-card-${job.id}">
      <div>
        <div class="job-header">
          <div>
            <span class="badge badge-info">${escapeHtml(job.category)}</span>
            <h3 style="margin-top:6px;">
              <a href="job-details.html?id=${job.id}" class="job-title">${escapeHtml(job.title)}</a>
            </h3>
          </div>
          <div class="job-wage">
            ₹${job.wageAmount} <span style="font-size:0.8rem; font-weight:600; color:var(--color-text-secondary);">${job.wageBasis === 'DAILY' ? '/ day' : '/ hr'}</span>
          </div>
        </div>

        <div class="job-meta" style="margin-top:12px;">
          <span>📍 ${escapeHtml(job.village)}, ${escapeHtml(job.district)}</span>
          <span>📅 ${job.startDate} to ${job.endDate}</span>
          <span>⏰ ${escapeHtml(job.workingHours)}</span>
          ${job.mealsProvided ? '<span title="Meals arranged">🍲 Meals Provided</span>' : ''}
          ${job.transportationProvided ? '<span title="Transport arranged">🚜 Transport</span>' : ''}
        </div>
      </div>

      <!-- Vacancy Progress Bar -->
      <div style="background:var(--color-offwhite); padding:10px 14px; border-radius:var(--radius-sm); border:1px solid var(--color-border);">
        <div style="display:flex; justify-content:space-between; font-size:0.85rem; font-weight:700; margin-bottom:6px;">
          <span>Capacity: ${job.confirmedWorkers} / ${job.requiredWorkers} Workers</span>
          <span class="${isFull ? 'text-danger' : 'text-success'}">
            ${isFull ? 'FULL (0 vacancies)' : `${job.remainingVacancies} vacancies open`}
          </span>
        </div>
        <div class="progress-bar">
          <div class="progress-fill" style="width: ${percentFilled}%; background: ${isFull ? 'var(--color-danger)' : 'var(--color-fresh)'};"></div>
        </div>
      </div>

      <div style="display:flex; justify-content:space-between; align-items:center; gap:10px; margin-top:8px;">
        <a href="job-details.html?id=${job.id}" class="btn btn-outline btn-sm">View Details</a>

        <div style="display:flex; gap:8px;">
          ${isLabourer ? `
            <button onclick="toggleSaveJob(${job.id}, this)" class="btn btn-secondary btn-sm" title="Bookmark Job">
              ${job.saved ? '★ Saved' : '☆ Save'}
            </button>
          ` : ''}

          ${!isFull && job.status === 'PUBLISHED' ? `
            <button onclick="openApplyModal(${job.id}, '${escapeHtml(job.title)}')" class="btn btn-primary btn-sm">
              Apply Now
            </button>
          ` : `
            <button class="btn btn-secondary btn-sm" disabled style="opacity:0.6; cursor:not-allowed;">
              ${isFull ? 'Fully Staffed' : job.status}
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
  if (!window.auth.isLoggedIn()) {
    window.location.href = 'login.html';
    return;
  }
  const isSaved = btn.textContent.includes('Saved');
  try {
    if (isSaved) {
      await window.api.delete(`/api/saved-jobs/${jobId}`);
      btn.textContent = '☆ Save';
      window.toast.info('Job removed from saved list');
    } else {
      await window.api.post(`/api/saved-jobs/${jobId}`, {});
      btn.textContent = '★ Saved';
      window.toast.success('Job saved to your bookmarks');
    }
  } catch (err) {
    window.toast.error(err.message || 'Failed to update bookmark');
  }
}

// Apply Modal
function openApplyModal(jobId, jobTitle) {
  if (!window.auth.isLoggedIn()) {
    window.location.href = `login.html?redirect=job-details.html?id=${jobId}`;
    return;
  }
  const user = window.auth.getUser();
  if (user.role !== 'LABOURER' && user.role !== 'ADMIN') {
    window.toast.error('Only agricultural labourers can apply for jobs');
    return;
  }

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
        <h3 style="color:var(--color-forest);">Apply for Job</h3>
        <button class="modal-close" onclick="closeApplyModal()">&times;</button>
      </div>
      <p style="margin-bottom:14px; font-weight:600; color:var(--color-primary);">${jobTitle}</p>
      
      <div class="alert alert-info" style="font-size:0.85rem; padding:10px 14px;">
        🔒 <strong>Contact Privacy:</strong> Your mobile number will remain hidden from the farmer until they accept your application.
      </div>

      <form id="apply-form" onsubmit="submitApplication(event, ${jobId})">
        <div class="form-group">
          <label class="form-label">Cover Note / Message to Farmer (Optional)</label>
          <textarea id="apply-cover-note" class="form-control" rows="3" placeholder="Describe your experience with this crop, tools you can bring, or team availability..."></textarea>
        </div>
        <div style="display:flex; justify-content:flex-end; gap:10px; margin-top:20px;">
          <button type="button" class="btn btn-secondary" onclick="closeApplyModal()">Cancel</button>
          <button type="submit" id="btn-submit-app" class="btn btn-primary">Submit Application</button>
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
  const btn = document.getElementById('btn-submit-app');
  btn.disabled = true;
  btn.textContent = 'Submitting...';

  const coverNote = document.getElementById('apply-cover-note').value.trim();

  try {
    const res = await window.api.post('/api/applications/apply', {
      jobId,
      coverNote
    });
    closeApplyModal();
    window.toast.success('Application submitted successfully! The farmer has been notified.');
    setTimeout(() => window.location.reload(), 1200);
  } catch (err) {
    btn.disabled = false;
    btn.textContent = 'Submit Application';
    window.toast.error(err.message || 'Failed to submit application');
  }
}
