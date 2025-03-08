# Medical Blockchain Platform Architecture

## Overview

The Medical Blockchain Platform is a secure, distributed system for storing and managing medical records using blockchain technology. The platform ensures data integrity, patient privacy, and compliant sharing of medical information between healthcare providers.

## System Components

### 1. Microservices Architecture

The backend is built as a set of independent microservices:

#### User Management Service
- **Role**: Authentication, authorization, and user profile management
- **Technologies**: Spring Boot, Spring Security, Keycloak
- **Key Features**:
  - OAuth 2.0/OpenID Connect integration
  - Role-based access control (RBAC)
  - User registration and profile management
  - Password policies and account recovery

#### Document Service
- **Role**: Storage, retrieval, and management of medical documents
- **Technologies**: Spring Boot, IPFS, PostgreSQL
- **Key Features**:
  - Document encryption and decryption
  - IPFS integration for distributed storage
  - Document metadata management
  - Version control and document history

#### Blockchain Service
- **Role**: Immutable audit trail and access control management
- **Technologies**: Spring Boot, Hyperledger Fabric
- **Key Features**:
  - Smart contracts for access control
  - Document hash storage on blockchain
  - Audit trail for all document actions
  - Consent management

### 2. Frontend

- **Role**: User interface for different stakeholders (patients, doctors, administrators)
- **Technologies**: React, TypeScript, Material-UI
- **Key Features**:
  - Role-based dashboards
  - Document upload/view/manage
  - Patient consent management
  - Secure communication

### 3. Blockchain Layer

- **Technology**: Hyperledger Fabric
- **Network Topology**:
  - Organizations: Hospitals, Insurance Providers, Regulatory Bodies
  - Channels: Separate channels for different data sensitivity levels
  - Orderers: Consensus service for transaction ordering
- **Smart Contracts (Chaincode)**:
  - Document Registry: Records document metadata and access rights
  - User Registry: Maps user identities to blockchain identities
  - Consent Management: Handles patient consent for data sharing

### 4. Storage Layer

- **Document Storage**: IPFS (InterPlanetary File System)
  - Content-addressable, versioned, peer-to-peer file system
  - All documents encrypted before storage
- **Metadata Storage**: PostgreSQL
  - Stores document metadata and relationships
  - Maintains user profiles and preferences

## Security Architecture

### 1. Authentication & Authorization

- **Identity Provider**: Keycloak
  - Single sign-on (SSO) across services
  - Multi-factor authentication
- **API Security**: OAuth 2.0 tokens
  - JWT with short expiration times
  - Fine-grained authorization with scopes

### 2. Data Encryption

- **Transport Layer**: TLS 1.3 for all communications
- **Document Encryption**: AES-256
  - Patient-specific encryption keys
  - Key sharing via blockchain smart contracts
- **Blockchain Identity**: X.509 certificates

### 3. Compliance Features

- **Audit Trails**: All actions recorded on blockchain
- **Data Residency**: Configurable storage policies
- **GDPR Compliance**: Right to be forgotten implemented via encryption key management

## Deployment Architecture

### 1. Container Orchestration

- **Containerization**: Docker
- **Orchestration**: Kubernetes
- **Service Mesh**: Istio for inter-service communication

### 2. Cloud Infrastructure

- **Deployable on**: AWS, Azure, or GCP
- **Database**: Managed PostgreSQL service
- **Monitoring**: Prometheus and Grafana
- **Logging**: ELK Stack (Elasticsearch, Logstash, Kibana)

### 3. DevOps

- **CI/CD**: GitHub Actions
- **Infrastructure as Code**: Terraform
- **Secret Management**: HashiCorp Vault
- **Configuration Management**: Spring Cloud Config 