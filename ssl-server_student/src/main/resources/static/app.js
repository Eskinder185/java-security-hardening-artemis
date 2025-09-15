function setTLSBadge() {
  const el = document.getElementById('tlsStatus');
  const https = window.location.protocol === 'https:';
  el.textContent = https ? 'HTTPS ✓' : 'Not HTTPS';
  el.style.color = https ? '#8fd3ff' : '#ff7b7b';
}
async function call(path) {
  const u = document.getElementById('u').value.trim();
  const p = document.getElementById('p').value;
  const t = (document.getElementById('t')?.value || '').trim();
  const out = document.getElementById('out');
  out.textContent = 'Loading…';
  const headers = new Headers();
  if (u && p) {
    headers.set('Authorization', 'Basic ' + btoa(u + ':' + p));
  }
  const url = t ? (path + '?text=' + encodeURIComponent(t)) : path;
  try {
    const resp = await fetch(url, { method:'GET', headers, cache:'no-store' });
    const text = await resp.text();
    out.textContent = 'HTTP ' + resp.status + '\\n\\n' + text;
  } catch (e) {
    out.textContent = 'Request failed: ' + e.message;
  }
}
async function showHeaders() {
  const box = document.getElementById('headersBox');
  try {
    const resp = await fetch('/', { method: 'GET', cache: 'no-store' });
    const wanted = ['strict-transport-security','content-security-policy','x-frame-options','x-content-type-options','referrer-policy','permissions-policy'];
    const lines = [];
    for (const k of wanted) {
      lines.push(k + ': ' + (resp.headers.get(k) || '(missing)'));
    }
    box.textContent = lines.join('\\n');
  } catch (e) {
    box.textContent = 'Failed to fetch headers: ' + e.message;
  }
}

window.addEventListener('DOMContentLoaded', () => {
  setTLSBadge();
  document.getElementById('btnProfile').addEventListener('click', () => call('/profile'));
  document.getElementById('btnHash').addEventListener('click', () => call('/hash'));
  document.getElementById('btnChecksum').addEventListener('click', () => call('/checksum'));
  showHeaders();
});
