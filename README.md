<!-- README.md for NesterAPI -->

[![Build with Maven](https://img.shields.io/badge/build–Maven-blue?logo=apache-maven)](https://github.com/HumbertoFernandes7/NesterAPI/actions)  
[![Java 17](https://img.shields.io/badge/Java–17-orange?logo=java)](https://www.oracle.com/java/)  
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot–3.4.5-brightgreen?logo=spring)](https://spring.io/projects/spring-boot)  
[![License: MIT](https://img.shields.io/badge/License–MIT-yellow.svg)](./LICENSE)  

---

## 🔍 Visão Geral

**NesterAPI** é uma RESTful API em **Java 17** e **Spring Boot 3.4.5** para gerenciar usuários de uma rede social chamada “Nester”.  
Ela oferece endpoints simples para cadastro, listagem e remoção de usuários, usando JPA com PostgreSQL para persistência, ModelMapper para conversão entre entidades e DTOs, Lombok para reduzir boilerplate e Bean Validation para garantir dados válidos.

Repositório no GitHub:  
🔗 https://github.com/HumbertoFernandes7/NesterAPI

---

## 📦 Tecnologias

- **Spring Boot 3.4.5**  
- **Java 17**  
- **Spring Data JPA** + **PostgreSQL**  
- **ModelMapper**  
- **Lombok**  
- **Bean Validation** (javax.validation)  
- **Spring Boot DevTools** (hot reload)

---

## 🚀 Pré-requisitos

- **Java 17**  
- **Maven 3.6+**  
- **PostgreSQL** ativo em `localhost:5432`  
  - Database: `NesterApi`  
  - Usuário/Senha: configuráveis em `src/main/resources/application.properties`

---

