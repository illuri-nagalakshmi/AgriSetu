/**
 * AgriSetu Reusable Navigation Bar Renderer
 */

function renderNavbar() {
  const navContainer = document.getElementById('navbar-container');
  if (!navContainer) return;

  const user = window.auth ? window.auth.getUser() : null;
  const currentLang = window.i18n ? window.i18n.getCurrentLanguage() : 'en';

  let dashboardUrl = 'login.html';
  if (user) {
    if (user.role === 'FARMER') dashboardUrl = 'farmer-dashboard.html';
    else if (user.role === 'LABOURER') dashboardUrl = 'labourer-dashboard.html';
    else if (user.role === 'ADMIN') dashboardUrl = 'admin-dashboard.html';
  }

  const html = `
    <nav class="navbar">
      <div class="container nav-container">
        <a href="index.html" class="logo-link">
          <img src="images/logo.svg" alt="AgriSetu Logo" height="46">
        </a>

        <ul class="nav-links" id="nav-links">
          <li><a href="index.html" data-i18n="nav_home">Home</a></li>
          <li><a href="jobs.html" data-i18n="nav_find_work">Find Work</a></li>
          <li><a href="${user && user.role === 'FARMER' ? 'create-job.html' : 'login.html?role=farmer'}" data-i18n="nav_hire_workers">Hire Workers</a></li>
          <li><a href="about.html" data-i18n="nav_about">About</a></li>
          
          ${user ? `
            <li><a href="${dashboardUrl}" class="active" data-i18n="nav_dashboard">Dashboard</a></li>
            ${user.role === 'FARMER' ? `<li><a href="manage-jobs.html" data-i18n="nav_manage_jobs">Manage Jobs</a></li>` : ''}
            ${user.role === 'LABOURER' ? `<li><a href="my-applications.html" data-i18n="nav_my_applications">My Applications</a></li>` : ''}
            <li><a href="assignments.html" data-i18n="nav_assignments">Assignments</a></li>
            ${user.role === 'ADMIN' ? `<li><a href="admin-dashboard.html" style="color:var(--color-danger);" data-i18n="nav_admin">Admin</a></li>` : ''}
          ` : `
            <li><a href="login.html" data-i18n="nav_login">Login</a></li>
          `}
        </ul>

        <div class="nav-right">
          <!-- Language Selector -->
          <select class="lang-selector" aria-label="Select Preferred Language">
            <option value="en" ${currentLang === 'en' ? 'selected' : ''}>English</option>
            <option value="te" ${currentLang === 'te' ? 'selected' : ''}>తెలుగు</option>
            <option value="hi" ${currentLang === 'hi' ? 'selected' : ''}>हिन्दी</option>
          </select>

          ${user ? `
            <a href="notifications.html" class="btn btn-secondary btn-sm" title="Notifications" style="position:relative;">
              🔔 <span id="nav-notif-badge" class="badge badge-danger" style="display:none; font-size:0.65rem; padding:2px 5px;">0</span>
            </a>
            <a href="profile.html" class="btn btn-outline btn-sm" title="Profile">
              👤 ${user.fullName.split(' ')[0]}
            </a>
            <button onclick="window.auth.logout()" class="btn btn-secondary btn-sm" data-i18n="nav_logout">Logout</button>
          ` : `
            <a href="register.html" class="btn btn-primary btn-sm" data-i18n="nav_register">Register</a>
          `}

          <button class="mobile-menu-btn" aria-label="Toggle Navigation Menu" onclick="toggleMobileMenu()">☰</button>
        </div>
      </div>
    </nav>
  `;

  navContainer.innerHTML = html;

  // Load unread notification count if user is logged in
  if (user && window.api) {
    window.api.get('/api/notifications/unread-count')
      .then(res => {
        if (res.success && res.data > 0) {
          const badge = document.getElementById('nav-notif-badge');
          if (badge) {
            badge.textContent = res.data;
            badge.style.display = 'inline-block';
          }
        }
      })
      .catch(() => {});
  }

  // Re-bind language switcher
  const sel = navContainer.querySelector('.lang-selector');
  if (sel && window.i18n) {
    sel.addEventListener('change', (e) => window.i18n.setLanguage(e.target.value));
  }

  // Ensure newly inserted navigation elements are translated in current language
  if (window.i18n && typeof window.i18n.setLanguage === 'function') {
    navContainer.querySelectorAll('[data-i18n]').forEach(el => {
      const key = el.getAttribute('data-i18n');
      el.textContent = window.i18n.t(key, el.textContent);
    });
  }
}

function toggleMobileMenu() {
  const links = document.getElementById('nav-links');
  if (links) {
    if (links.style.display === 'flex') {
      links.style.display = 'none';
    } else {
      links.style.display = 'flex';
      links.style.flexDirection = 'column';
      links.style.position = 'absolute';
      links.style.top = '72px';
      links.style.left = '0';
      links.style.width = '100%';
      links.style.background = 'white';
      links.style.padding = '20px';
      links.style.boxShadow = 'var(--shadow-md)';
      links.style.zIndex = '999';
    }
  }
}

document.addEventListener('DOMContentLoaded', renderNavbar);
