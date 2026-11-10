/**
 * AgriSetu Authentication and User Session Management
 */

const auth = {
  getUser: () => {
    try {
      const data = sessionStorage.getItem('agrisetu_user');
      return data ? JSON.parse(data) : null;
    } catch {
      return null;
    }
  },

  setUser: (user) => {
    if (user) {
      sessionStorage.setItem('agrisetu_user', JSON.stringify(user));
      if (user.preferredLanguage && window.i18n) {
        window.i18n.setLanguage(user.preferredLanguage);
      }
    } else {
      sessionStorage.removeItem('agrisetu_user');
    }
  },

  isLoggedIn: () => {
    return auth.getUser() !== null;
  },

  hasRole: (role) => {
    const user = auth.getUser();
    return user && user.role === role;
  },

  syncSession: async () => {
    try {
      const res = await window.api.get('/api/auth/current');
      if (res.success && res.data) {
        auth.setUser(res.data);
        return res.data;
      } else {
        auth.setUser(null);
        return null;
      }
    } catch {
      auth.setUser(null);
      return null;
    }
  },

  checkSession: async (allowedRoles = []) => {
    // 1. Fast preliminary check from local storage
    const localUser = auth.getUser();
    if (!localUser) {
      window.location.href = `login.html?redirect=${encodeURIComponent(window.location.pathname)}`;
      return null;
    }

    // 2. Synchronize and confirm with active backend session
    const verifiedUser = await auth.syncSession();
    if (!verifiedUser) {
      window.location.href = `login.html?redirect=${encodeURIComponent(window.location.pathname)}`;
      return null;
    }

    // 3. Verify role permissions
    if (allowedRoles.length > 0 && !allowedRoles.includes(verifiedUser.role)) {
      if (verifiedUser.role === 'FARMER') {
        window.location.href = 'farmer-dashboard.html';
      } else if (verifiedUser.role === 'LABOURER') {
        window.location.href = 'labourer-dashboard.html';
      } else if (verifiedUser.role === 'ADMIN') {
        window.location.href = 'admin-dashboard.html';
      } else {
        window.location.href = 'index.html';
      }
      return null;
    }

    return verifiedUser;
  },

  login: async (email, password) => {
    const res = await window.api.post('/api/auth/login', { email, password });
    if (res.success && res.data) {
      auth.setUser(res.data);
      window.toast.success(res.message || 'Login successful');
      return res.data;
    }
    throw new Error(res.message || 'Login failed');
  },

  register: async (registerData) => {
    const res = await window.api.post('/api/auth/register', registerData);
    if (res.success && res.data) {
      auth.setUser(res.data);
      window.toast.success(res.message || 'Account registered successfully');
      return res.data;
    }
    throw new Error(res.message || 'Registration failed');
  },

  logout: async () => {
    try {
      await window.api.post('/api/auth/logout', {});
    } catch (e) {
      console.warn('Logout API call finished', e);
    }
    auth.setUser(null);
    window.location.href = 'login.html';
  },

  requireAuth: (allowedRoles = []) => {
    const user = auth.getUser();
    if (!user) {
      window.location.href = `login.html?redirect=${encodeURIComponent(window.location.pathname)}`;
      return false;
    }

    if (allowedRoles.length > 0 && !allowedRoles.includes(user.role)) {
      if (user.role === 'FARMER') {
        window.location.href = 'farmer-dashboard.html';
      } else if (user.role === 'LABOURER') {
        window.location.href = 'labourer-dashboard.html';
      } else if (user.role === 'ADMIN') {
        window.location.href = 'admin-dashboard.html';
      } else {
        window.location.href = 'index.html';
      }
      return false;
    }
    return true;
  }
};

window.auth = auth;

// Automatically check session on load
document.addEventListener('DOMContentLoaded', () => {
  if (auth.isLoggedIn()) {
    auth.syncSession();
  }
});
