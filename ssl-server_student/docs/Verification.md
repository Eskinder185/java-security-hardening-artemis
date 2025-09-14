# Security Verification Playbook

## Overview

This document provides a step-by-step validation playbook to verify that the Artemis Financial application has been properly secured. Follow these procedures to ensure all security controls are functioning correctly.

## Prerequisites

- Application running on HTTPS (https://localhost:8443)
- curl command-line tool installed
- OpenSSL installed (for TLS verification)
- Browser with developer tools
- Access to security scan reports

## 🔍 Step 1: TLS/SSL Verification

### 1.1 Check TLS Certificate
```bash
# Verify certificate details
openssl s_client -connect localhost:8443 -servername localhost

# Check certificate expiration
echo | openssl s_client -connect localhost:8443 2>/dev/null | openssl x509 -noout -dates

# Verify certificate chain
openssl s_client -connect localhost:8443 -showcerts
```

**Expected Results**:
- Certificate is valid and not expired
- Certificate chain is complete
- No certificate warnings

### 1.2 Verify TLS Protocol Versions
```bash
# Test TLS 1.2
openssl s_client -connect localhost:8443 -tls1_2

# Test TLS 1.3 (if supported)
openssl s_client -connect localhost:8443 -tls1_3

# Test TLS 1.0/1.1 (should fail)
openssl s_client -connect localhost:8443 -tls1
openssl s_client -connect localhost:8443 -tls1_1
```

**Expected Results**:
- TLS 1.2 and 1.3 connections succeed
- TLS 1.0 and 1.1 connections fail
- Strong cipher suites are used

### 1.3 Browser Verification
1. Open https://localhost:8443 in browser
2. Click on the lock icon in address bar
3. Verify certificate details
4. Check TLS version in developer tools (Security tab)

**Expected Results**:
- Lock icon shows "Secure" or "Valid"
- Certificate is issued to localhost
- TLS version is 1.2 or 1.3

## 🛡️ Step 2: Security Headers Verification

### 2.1 Check Security Headers with curl
```bash
# Get all headers
curl -Ik https://localhost:8443

# Check specific headers
curl -Ik https://localhost:8443 | grep -i "strict-transport-security"
curl -Ik https://localhost:8443 | grep -i "content-security-policy"
curl -Ik https://localhost:8443 | grep -i "x-frame-options"
curl -Ik https://localhost:8443 | grep -i "x-content-type-options"
curl -Ik https://localhost:8443 | grep -i "referrer-policy"
curl -Ik https://localhost:8443 | grep -i "permissions-policy"
```

**Expected Headers**:
```
Strict-Transport-Security: max-age=31536000; includeSubDomains
Content-Security-Policy: default-src 'self'; object-src 'none'; frame-ancestors 'none'
X-Frame-Options: DENY
X-Content-Type-Options: nosniff
Referrer-Policy: no-referrer
Permissions-Policy: geolocation=(), microphone=(), camera=()
```

### 2.2 Browser Developer Tools Verification
1. Open browser developer tools (F12)
2. Go to Network tab
3. Reload the page
4. Click on the main request
5. Check Response Headers section

**Expected Results**:
- All security headers are present
- Header values match expected configuration
- No security warnings in console

### 2.3 Security Headers Testing Tools
```bash
# Using online security headers checker
curl -s "https://securityheaders.com/?q=https://localhost:8443" | grep -i "grade"

# Using Mozilla Observatory (if accessible)
curl -s "https://observatory.mozilla.org/api/v1/analyze?host=localhost:8443"
```

## 🔒 Step 3: Application Security Testing

### 3.1 Basic Security Tests
```bash
# Test for common vulnerabilities
curl -Ik https://localhost:8443/admin
curl -Ik https://localhost:8443/../etc/passwd
curl -Ik https://localhost:8443/robots.txt
curl -Ik https://localhost:8443/.env
curl -Ik https://localhost:8443/config.json
```

**Expected Results**:
- Admin endpoints return 404 or require authentication
- Path traversal attempts are blocked
- Sensitive files are not accessible

### 3.2 CSRF Protection Test
```bash
# Test CSRF protection (if applicable)
curl -X POST https://localhost:8443/api/endpoint \
  -H "Content-Type: application/json" \
  -d '{"test": "data"}'
```

**Expected Results**:
- CSRF token required for state-changing operations
- Requests without valid CSRF tokens are rejected

### 3.3 Input Validation Test
```bash
# Test SQL injection protection
curl -X GET "https://localhost:8443/api/search?q='; DROP TABLE users; --"

# Test XSS protection
curl -X GET "https://localhost:8443/api/search?q=<script>alert('xss')</script>"
```

**Expected Results**:
- Malicious inputs are sanitized or rejected
- No SQL injection vulnerabilities
- XSS attempts are blocked

## 🔍 Step 4: Automated Security Scanning

### 4.1 Run Security Scans
```bash
# Run all security scans
scripts/scan.sh

# Check if reports were generated
ls -la reports/
```

**Expected Results**:
- OWASP Dependency-Check report generated
- SpotBugs/FindSecBugs report generated
- No critical or high-severity vulnerabilities

### 4.2 Review Security Reports
```bash
# View dependency check report
open reports/dependency-check-report.html

# View SpotBugs report
cat reports/spotbugsXml.xml | grep -i "error\|warning"
```

**Expected Results**:
- No high or critical vulnerabilities in dependencies
- No high-severity static analysis findings
- All findings are documented and addressed

### 4.3 OWASP ZAP Baseline Scan (Optional)
```bash
# Install OWASP ZAP (if not already installed)
# Download from: https://www.zaproxy.org/download/

# Run baseline scan
zap-baseline.py -t https://localhost:8443 -r baseline-report.html
```

**Expected Results**:
- No high or medium-risk findings
- All low-risk findings are documented
- Scan completes successfully

## 📊 Step 5: Performance and Load Testing

### 5.1 Basic Performance Test
```bash
# Test response time
curl -w "@curl-format.txt" -o /dev/null -s https://localhost:8443

# Create curl-format.txt file
cat > curl-format.txt << EOF
     time_namelookup:  %{time_namelookup}\n
        time_connect:  %{time_connect}\n
     time_appconnect:  %{time_appconnect}\n
    time_pretransfer:  %{time_pretransfer}\n
       time_redirect:  %{time_redirect}\n
  time_starttransfer:  %{time_starttransfer}\n
                     ----------\n
          time_total:  %{time_total}\n
EOF
```

**Expected Results**:
- Response time under 2 seconds
- SSL handshake completes quickly
- No timeout errors

### 5.2 Load Testing (Optional)
```bash
# Using Apache Bench (if available)
ab -n 100 -c 10 https://localhost:8443/

# Using curl for simple load test
for i in {1..10}; do
  curl -s https://localhost:8443/ > /dev/null &
done
wait
```

**Expected Results**:
- Application handles concurrent requests
- No errors under normal load
- Response times remain acceptable

## 🔐 Step 6: Authentication and Authorization Testing

### 6.1 Authentication Testing
```bash
# Test login endpoint (if available)
curl -X POST https://localhost:8443/login \
  -H "Content-Type: application/json" \
  -d '{"username": "test", "password": "test"}'

# Test with invalid credentials
curl -X POST https://localhost:8443/login \
  -H "Content-Type: application/json" \
  -d '{"username": "invalid", "password": "invalid"}'
```

**Expected Results**:
- Valid credentials result in successful authentication
- Invalid credentials are rejected
- Account lockout after multiple failed attempts

### 6.2 Authorization Testing
```bash
# Test protected endpoints without authentication
curl -Ik https://localhost:8443/api/protected

# Test with valid authentication
curl -Ik https://localhost:8443/api/protected \
  -H "Authorization: Bearer <valid-token>"
```

**Expected Results**:
- Protected endpoints require authentication
- Valid tokens provide access
- Invalid tokens are rejected

## 📋 Step 7: Compliance Verification

### 7.1 Check Security Configuration
```bash
# Verify application properties
grep -i ssl src/main/resources/application.yml
grep -i security src/main/resources/application.yml

# Check for hardcoded secrets
grep -r "password\|secret\|key" src/ --exclude-dir=target
```

**Expected Results**:
- No hardcoded secrets in source code
- SSL/TLS properly configured
- Security settings are appropriate

### 7.2 Verify Logging Configuration
```bash
# Check if security events are logged
tail -f logs/application.log | grep -i "security\|auth\|error"

# Test error handling
curl -X GET https://localhost:8443/nonexistent
```

**Expected Results**:
- Security events are logged
- No sensitive information in logs
- Error handling is secure

## ✅ Step 8: Final Validation Checklist

### 8.1 Security Controls Verification
- [ ] TLS 1.2/1.3 is properly configured
- [ ] Security headers are present and correct
- [ ] No critical vulnerabilities in dependencies
- [ ] No high-severity static analysis findings
- [ ] Authentication and authorization work correctly
- [ ] Input validation is implemented
- [ ] Error handling is secure
- [ ] Logging is properly configured

### 8.2 Performance Verification
- [ ] Application responds within acceptable time
- [ ] No memory leaks or resource issues
- [ ] SSL handshake is efficient
- [ ] Application handles concurrent users

### 8.3 Documentation Verification
- [ ] Security documentation is complete
- [ ] Threat model is up to date
- [ ] Security procedures are documented
- [ ] Incident response procedures are ready

## 🚨 Troubleshooting Common Issues

### TLS Certificate Issues
```bash
# If certificate is not trusted
# Add certificate to trust store or use -k flag for testing
curl -k https://localhost:8443

# If certificate is expired
# Regenerate keystore
scripts/https-dev.sh
```

### Security Headers Missing
```bash
# Check if Spring Security is properly configured
grep -r "SecurityConfig\|SecurityFilterChain" src/

# Verify security configuration is loaded
curl -Ik https://localhost:8443 | grep -i "security"
```

### Dependency Vulnerabilities
```bash
# Update dependencies
./mvnw versions:display-dependency-updates

# Run dependency check with specific format
./mvnw org.owasp:dependency-check-maven:check -Dformat=ALL
```

## 📞 Support and Escalation

If issues are found during verification:

1. **Document the issue** with steps to reproduce
2. **Check the logs** for error messages
3. **Review security configuration** for misconfigurations
4. **Escalate to security team** if critical issues are found
5. **Update documentation** with any new findings

**Security Team Contact**: security@artemis-financial.com
**Incident Response**: incident@artemis-financial.com

---

**Last Updated**: September 2024
**Next Review**: December 2024
**Document Owner**: Security Team
