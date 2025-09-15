Secure Spring Boot Demo (Artemis)

A tiny Spring Boot project showing HTTPS/TLS, basic auth, secure headers, and simple hash/checksum endpoints.

Quick Start

Windows (PowerShell):

cd ssl-server_student
.\mvnw.cmd spring-boot:run
 open: https://localhost:8443
 login: user / <password printed in console>


macOS/Linux:

cd ssl-server_student
./mvnw spring-boot:run
 open: https://localhost:8443
 login: user / <password printed in console>


Dev uses a self-signed cert. Your browser will warn — that’s expected.

Endpoints

/ → small JSON index

/profile → sample secured endpoint

/hash → returns SHA-256 of demo text (optionally ?text=your+text)

/checksum → returns CRC32 (optionally ?text=your+text)

/index.html → simple UI to click/test endpoints

<img width="2507" height="1227" alt="image" src="https://github.com/user-attachments/assets/54a38c2d-04b4-44d5-9034-1ff376536d16" />
