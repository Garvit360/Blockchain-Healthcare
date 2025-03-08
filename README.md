# Medical Blockchain Platform

A secure blockchain-based medical platform built with Spring Boot and Hyperledger Fabric for managing medical records, user authentication, and document storage.

## Architecture

The platform consists of several microservices:

- **User Management Service**: Handles authentication, authorization, and user profiles
- **Document Service**: Manages medical document storage and retrieval with IPFS integration
- **Blockchain Service**: Interfaces with the Hyperledger Fabric blockchain network

## Frontend

The frontend is built with React and TypeScript, providing role-based dashboards for:
- Patients
- Doctors
- Administrators

## Getting Started

### Prerequisites

- JDK 17+
- Maven 3.8+
- Docker and Docker Compose
- Node.js 16+
- PostgreSQL 13+
- Keycloak (for identity management)

### Setup Instructions

1. **Clone the repository**
   ```bash
   git clone https://github.com/[your-org]/medical-blockchain.git
   cd medical-blockchain
   ```

2. **Build the backend services**
   ```bash
   mvn clean install
   ```

3. **Start the infrastructure services**
   ```bash
   docker-compose up -d
   ```

4. **Run the backend services**
   ```bash
   java -jar backend/user-management-service/target/*.jar
   java -jar backend/document-service/target/*.jar
   java -jar backend/blockchain-service/target/*.jar
   ```

5. **Set up the frontend**
   ```bash
   cd frontend
   npm install
   npm start
   ```

## Security Features

- OAuth 2.0 / OpenID Connect with Keycloak
- Document encryption with patient-specific keys
- Immutable audit trail on blockchain
- IPFS-based document storage with encryption

## License

This project is licensed under the [MIT License](LICENSE). 