/**
 * AgriSetu Fetch API Client
 * Automatically handles baseURL, session cookies, CSRF tokens, and error handling.
 */

const API_CONFIG = {
  baseUrl: (window.location.port === '8080') ? '' : 'http://localhost:8080'
};

function getCsrfToken() {
  const match = document.cookie.match(new RegExp('(^|;\\s*)XSRF-TOKEN=([^;]*)'));
  return match ? decodeURIComponent(match[2]) : null;
}

async function request(endpoint, options = {}) {
  const url = `${API_CONFIG.baseUrl}${endpoint}`;
  
  const headers = {
    'Accept': 'application/json',
    ...(options.headers || {})
  };

  // Add Content-Type if body exists and is not FormData
  if (options.body && !(options.body instanceof FormData)) {
    headers['Content-Type'] = 'application/json';
  }

  // Attach CSRF token on mutating requests
  const method = (options.method || 'GET').toUpperCase();
  if (method !== 'GET' && method !== 'HEAD') {
    const csrfToken = getCsrfToken();
    if (csrfToken) {
      headers['X-XSRF-TOKEN'] = csrfToken;
    }
  }

  const fetchOptions = {
    ...options,
    headers,
    credentials: 'include' // Crucial for Spring Security session cookies
  };

  try {
    const response = await fetch(url, fetchOptions);

    // Handle CSV or text responses
    const contentType = response.headers.get('content-type') || '';
    if (contentType.includes('text/csv') || contentType.includes('text/plain')) {
      if (!response.ok) throw new Error('Failed to download report');
      return await response.text();
    }

    const data = await response.json();

    if (!response.ok) {
      let errorMessage = data.message || `Request failed with status ${response.status}`;
      if (data.data && typeof data.data === 'object') {
        // Field validation errors
        const fieldErrors = Object.values(data.data).join(', ');
        if (fieldErrors) errorMessage += `: ${fieldErrors}`;
      }

      if (response.status === 401 && !endpoint.includes('/api/auth/')) {
        // Session expired or unauthenticated
        sessionStorage.removeItem('agrisetu_user');
      }

      const error = new Error(errorMessage);
      error.status = response.status;
      error.response = data;
      throw error;
    }

    return data;
  } catch (err) {
    console.error(`API Error on [${method}] ${endpoint}:`, err);
    throw err;
  }
}

window.api = {
  get: (endpoint) => request(endpoint, { method: 'GET' }),
  post: (endpoint, body) => request(endpoint, { method: 'POST', body: JSON.stringify(body) }),
  put: (endpoint, body) => request(endpoint, { method: 'PUT', body: JSON.stringify(body) }),
  delete: (endpoint) => request(endpoint, { method: 'DELETE' }),
  request
};
