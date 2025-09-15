# Java Security Hardening Starter - Artemis Financial

A production-ready Spring Boot starter template with comprehensive security hardening for financial applications. This template enforces HTTPS/TLS, implements secure headers, provides automated security scanning, and includes CI/CD workflows.

## 🚀 Quick Start

### Prerequisites
- Java 17+ (upgraded from Java 8)
- Maven 3.6+
- Git

### 1. Create Dev TLS Keystore & Start App
```bash
# Generate development keystore and run security scans
scripts/https-dev.sh

# Start the application with HTTPS
./mvnw spring-boot:run
```

The app will be available at: **https://localhost:8443**

### 2. Verify TLS & Security Headers
```bash
# Check TLS and security headers
curl -Ik https://localhost:8443 | sed -n '1,20p'
```

Expected headers:
- `Strict-Transport-Security` (HSTS prevents protocol downgrades/MITM)
- `Content-Security-Policy` (prevents XSS attacks)
- `X-Frame-Options: DENY` (prevents clickjacking)
- `X-Content-Type-Options: nosniff` (prevents MIME sniffing)
- `Referrer-Policy: no-referrer` (prevents information leakage)

### 3. Run Security Scans
```bash
# Run all security scans (OWASP Dependency-Check + SpotBugs/FindSecBugs)
scripts/scan.sh
```

Reports will be generated in `/reports` directory.

## 🔒 Security Features

### TLS/HTTPS Configuration
- **Development**: Auto-generated PKCS12 keystore with 4096-bit RSA keys
- **Production**: Use Let's Encrypt, AWS Certificate Manager, or corporate PKI
- **Protocols**: TLS 1.2 and 1.3 only (TLS 1.0/1.1 disabled)
- **Ciphers**: Strong cipher suites with perfect forward secrecy

### Security Headers
- **Content Security Policy (CSP)**: Prevents XSS attacks
- **HTTP Strict Transport Security (HSTS)**: Enforces HTTPS
- **X-Frame-Options**: Prevents clickjacking attacks
- **X-Content-Type-Options**: Prevents MIME sniffing
- **Referrer-Policy**: Controls referrer information
- **Permissions-Policy**: Restricts browser features

### Automated Security Scanning
- **OWASP Dependency-Check**: Scans for known vulnerabilities in dependencies
- **SpotBugs + FindSecBugs**: Static analysis for security issues
- **GitHub Actions**: Automated scanning on every push/PR
- **Dependabot**: Automated dependency updates

## 📁 Project Structure

```
ssl-server_student/
├── docs/                          # Security documentation
│   ├── ThreatModel.md            # STRIDE threat analysis
│   ├── PracticesForSecureSoftware.md  # Security checklist
│   └── Verification.md           # Validation playbook
├── reports/                       # Security scan reports
├── scripts/                       # Automation scripts
│   ├── https-dev.sh              # Generate dev keystore
│   └── scan.sh                   # Run security scans
├── .github/
│   ├── workflows/
│   │   └── security-scan.yml     # CI security pipeline
│   └── dependabot.yml            # Automated dependency updates
└── src/main/java/com/snhu/sslserver/
    ├── SecurityConfig.java       # Spring Security configuration
    └── Sha256.java              # SHA-256 utility
```

## 🛠️ Development

### Local Development Setup
1. **Generate Development Keystore**:
   ```bash
   scripts/https-dev.sh
   ```

2. **Start Application**:
   ```bash
   ./mvnw spring-boot:run
   ```

3. **Access Application**:
   - HTTPS: https://localhost:8443
   - Accept self-signed certificate in browser

### Production Deployment

#### TLS Certificate Setup
**Never commit private keys to the repository!**

For production, use one of these options:
- **Let's Encrypt**: Free, automated certificates
- **AWS Certificate Manager**: Managed certificates
- **Corporate PKI**: Enterprise certificate authority

Update `application.yml`:
```yaml
server:
  ssl:
    key-store: /path/to/production-keystore.p12
    key-store-password: ${SSL_KEYSTORE_PASSWORD}
    key-store-type: PKCS12
```

#### Environment Variables
```bash
export SSL_KEYSTORE_PASSWORD="your-secure-password"
export PASS="your-keystore-password"
```

## 🔍 Security Validation

### Manual Verification Steps
1. **TLS Verification**:
   ```bash
   # Check TLS version and cipher
   openssl s_client -connect localhost:8443 -tls1_2
   ```

2. **Header Verification**:
   ```bash
   # Verify security headers
   curl -Ik https://localhost:8443
   ```

3. **Browser Verification**:
   - Check for lock icon in address bar
   - Verify TLS version in browser dev tools
   - Test CSP violations in console

### Automated Validation
```bash
# Run complete security scan suite
scripts/scan.sh

# Check reports
ls -la reports/
```

## 📊 Security Reports

After running scans, check these reports:
- `reports/dependency-check-report.html` - OWASP vulnerability scan
- `reports/spotbugsXml.xml` - Static analysis results

## 🚨 CI/CD Integration

### GitHub Actions
The repository includes automated security scanning:
- Runs on every push and pull request
- Scans dependencies for vulnerabilities
- Performs static code analysis
- Uploads security reports as artifacts

### Dependabot
Automated dependency updates:
- Weekly security updates
- Pull requests for outdated dependencies
- Automatic vulnerability patches

## 📚 Documentation

- **[SECURITY.md](SECURITY.md)**: Vulnerability reporting and security policies
- **[docs/ThreatModel.md](docs/ThreatModel.md)**: STRIDE threat analysis
- **[docs/PracticesForSecureSoftware.md](docs/PracticesForSecureSoftware.md)**: Security development checklist
- **[docs/Verification.md](docs/Verification.md)**: Step-by-step validation guide

## 🔧 Customization

### Adding New Security Headers
Update `SecurityConfig.java`:
```java
.headers(headers -> headers
    .contentSecurityPolicy(csp -> csp.policyDirectives(
        "default-src 'self'; script-src 'self' 'unsafe-inline'"))
    .httpStrictTransportSecurity(hsts -> hsts
        .maxAgeInSeconds(31536000)
        .includeSubdomains(true)))
```

### Custom Security Scans
Add new Maven plugins to `pom.xml`:
```xml
<plugin>
    <groupId>com.github.spotbugs</groupId>
    <artifactId>spotbugs-maven-plugin</artifactId>
    <version>4.8.6.5</version>
</plugin>
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Run security scans: `scripts/scan.sh`
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🆘 Support

- **Security Issues**: See [SECURITY.md](SECURITY.md)
- **Documentation**: Check the `docs/` directory
- **Validation**: Follow [docs/Verification.md](docs/Verification.md)

---

**⚠️ Security Notice**: This is a starter template. Always review and customize security configurations for your specific use case and compliance requirements.
