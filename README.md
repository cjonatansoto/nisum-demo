# Prueba Técnica Nisum - API de Usuarios 🧑‍💻

[![Java](https://img.shields.io/badge/Java-23-blue)](https://www.oracle.com/java/)
[![Maven](https://img.shields.io/badge/Maven-3.9.0-red)](https://maven.apache.org/)
[![Docker](https://img.shields.io/badge/Docker-Available-blue)](https://www.docker.com/)
[![OAS 3.0](https://img.shields.io/badge/OAS-3.0-green)](http://localhost:3000/swagger-ui/index.html)

**Versión:** 1.0.0
**Descripción:** Servicio para gestión de usuarios, prueba técnica realizada para NISUM.

---

## 📖 Descripción General
Esta API permite gestionar usuarios, incluyendo:
- Creación y registro de usuarios
- Inicio de sesión y autenticación con JWT
- Validación de tokens
- Documentación con Swagger (OAS 3.0)
- Base de datos H2 en memoria para pruebas rápidas

---

## 🌐 Enlaces Útiles
- **Documentación Swagger:** [http://localhost:3000/swagger-ui/index.html](http://localhost:3000/swagger-ui/index.html)
- **Consola H2 Database:** [http://localhost:3000/h2-console/login.jsp](http://localhost:3000/h2-console/login.jsp)

---

## 💻 Requisitos
- Java 21
- Maven instalado

---

## 🚀 Cómo Levantar el Proyecto

### 1️⃣ Forma Tradicional (Maven)
```bash
mvn spring-boot:run

mvn clean package
cd /target
java -jar nisum-demo-0.0.1-SNAPSHOT.jar


