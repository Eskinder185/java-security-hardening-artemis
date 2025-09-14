# Security Policy

## Supported Versions

We actively support the following versions with security updates:

| Version | Supported          |
| ------- | ------------------ |
| Java 17+ | :white_check_mark: |
| Java 11  | :x:                |
| Java 8   | :x:                |
| Maven 3.6+ | :white_check_mark: |

## Reporting a Vulnerability

We take security vulnerabilities seriously. If you discover a security vulnerability, please follow these steps:

### 1. Do NOT create a public GitHub issue
Security vulnerabilities should be reported privately to prevent exploitation.

### 2. Email Security Team
Send details to: **security@artemis-financial.com** (placeholder - replace with actual email)

Include the following information:
- Description of the vulnerability
- Steps to reproduce
- Potential impact
- Suggested fix (if any)
- Your contact information

### 3. Response Timeline
- **Initial Response**: Within 48 hours
- **Status Update**: Within 7 days
- **Resolution**: Within 30 days (depending on severity)

### 4. Disclosure Process
- We will work with you to validate and reproduce the issue
- We will develop and test a fix
- We will coordinate the release of the fix
- We will credit you in our security advisories (unless you prefer to remain anonymous)

## Security Policies

### 1. No Secrets in Repository
- **Never commit** passwords, API keys, certificates, or private keys
- Use environment variables or secure secret management
- All secrets must be externalized from code

### 2. Credential Rotation
- Rotate all credentials regularly (minimum every 90 days)
- Use strong, unique passwords (minimum 16 characters)
- Implement multi-factor authentication where possible

### 3. TLS/SSL Requirements
- **TLS 1.2 and 1.3 only** - TLS 1.0 and 1.1 are prohibited
- Use strong cipher suites with perfect forward secrecy
- Implement HSTS (HTTP Strict Transport Security)
- Validate certificate chains properly

### 4. Input Validation
- Validate all user inputs on both client and server side
- Use parameterized queries to prevent SQL injection
- Implement proper output encoding to prevent XSS

### 5. Authentication & Authorization
- Implement strong authentication mechanisms
- Use role-based access control (RBAC)
- Implement session management best practices
- Use secure password hashing (bcrypt, Argon2, or PBKDF2)

### 6. Logging & Monitoring
- Log security-relevant events
- Implement intrusion detection
- Monitor for suspicious activities
- Never log sensitive information (passwords, tokens, PII)

### 7. Dependency Management
- Keep all dependencies up to date
- Use automated vulnerability scanning
- Remove unused dependencies
- Use dependency pinning for reproducible builds

### 8. Code Security
- Follow secure coding practices
- Use static analysis tools (SpotBugs, FindSecBugs)
- Implement code reviews for security issues
- Use automated security testing in CI/CD

## Security Best Practices

### Development
- Use HTTPS for all communications
- Implement Content Security Policy (CSP)
- Use secure headers (X-Frame-Options, X-Content-Type-Options, etc.)
- Implement rate limiting and DDoS protection
- Use secure session management

### Deployment
- Use container security scanning
- Implement network segmentation
- Use web application firewalls (WAF)
- Implement proper backup and recovery procedures
- Use infrastructure as code for consistent deployments

### Operations
- Implement security monitoring and alerting
- Use centralized logging
- Implement incident response procedures
- Regular security assessments and penetration testing
- Employee security training and awareness

## Compliance

This project follows security standards and frameworks:
- OWASP Top 10
- NIST Cybersecurity Framework
- PCI DSS (for payment processing)
- SOC 2 Type II (for service organizations)

## Security Tools

We use the following security tools:
- **OWASP Dependency-Check**: Vulnerability scanning
- **SpotBugs + FindSecBugs**: Static code analysis
- **GitHub Actions**: Automated security scanning
- **Dependabot**: Automated dependency updates

## Contact

For security-related questions or concerns:
- **Security Team**: security@artemis-financial.com
- **General Questions**: support@artemis-financial.com

---

**Last Updated**: September 2024
**Next Review**: December 2024
