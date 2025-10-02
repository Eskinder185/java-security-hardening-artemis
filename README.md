# Secure Spring Boot Demo (Artemis)

![Java](https://img.shields.io/badge/Java-17+-red)
![Spring](https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F)
![Security](https://img.shields.io/badge/Security-HTTPS%20%7C%20Basic%20Auth%20%7C%20Secure%20Headers-5b5)
![Build](https://img.shields.io/badge/Build-Maven-blue)
![License](https://img.shields.io/badge/License-MIT-lightgrey)

A tiny Spring Boot project showing **HTTPS/TLS**, **HTTP Basic auth**, **secure headers**, and simple **hash/checksum** endpoints.

> 🔒 Dev uses a **self-signed cert** on `https://localhost:8443`. Your browser will warn — that’s expected for local demos.

---

## 🚀 Quick Start

### Windows (PowerShell)
```powershell
cd ssl-server_student
.\mvnw.cmd spring-boot:run
# open:
#   https://localhost:8443
# login:
#   user / <password printed in console>
<img width="2507" height="1227" alt="image" src="https://github.com/user-attachments/assets/54a38c2d-04b4-44d5-9034-1ff376536d16" />
