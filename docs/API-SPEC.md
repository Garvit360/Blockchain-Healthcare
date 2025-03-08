# API Specification

This document outlines the RESTful API endpoints for the Medical Blockchain Platform.

## OpenAPI 3.0 Specification

All APIs adhere to the OpenAPI 3.0 specification.

## User Management Service

### Authentication

#### Login
```
POST /api/auth/login
```
Request:
```json
{
  "username": "string",
  "password": "string"
}
```
Response:
```json
{
  "accessToken": "string",
  "refreshToken": "string",
  "expiresIn": 3600
}
```

#### Register
```
POST /api/auth/register
```
Request:
```json
{
  "username": "string",
  "email": "string",
  "password": "string",
  "firstName": "string",
  "lastName": "string",
  "role": "PATIENT | DOCTOR | ADMIN"
}
```
Response:
```json
{
  "id": "uuid",
  "username": "string",
  "email": "string",
  "role": "string",
  "created": "timestamp"
}
```

### User Management

#### Get User Profile
```
GET /api/users/{userId}
```
Response:
```json
{
  "id": "uuid",
  "username": "string",
  "email": "string",
  "firstName": "string",
  "lastName": "string",
  "role": "string",
  "created": "timestamp",
  "lastModified": "timestamp"
}
```

#### Update User Profile
```
PUT /api/users/{userId}
```
Request:
```json
{
  "firstName": "string",
  "lastName": "string",
  "email": "string"
}
```
Response:
```json
{
  "id": "uuid",
  "username": "string",
  "email": "string",
  "firstName": "string",
  "lastName": "string",
  "role": "string",
  "lastModified": "timestamp"
}
```

## Document Service

### Document Management

#### Upload Document
```
POST /api/documents
```
Request:
```
multipart/form-data
- file: binary
- metadata: JSON object
  {
    "title": "string",
    "description": "string",
    "patientId": "uuid",
    "doctorId": "uuid",
    "documentType": "MEDICAL_RECORD | PRESCRIPTION | LAB_RESULT"
  }
```
Response:
```json
{
  "id": "uuid",
  "title": "string",
  "description": "string",
  "ipfsHash": "string",
  "blockchainTxId": "string",
  "createdBy": "uuid",
  "created": "timestamp"
}
```

#### Get Document
```
GET /api/documents/{documentId}
```
Response:
```json
{
  "id": "uuid",
  "title": "string",
  "description": "string",
  "ipfsHash": "string",
  "blockchainTxId": "string",
  "createdBy": "uuid",
  "created": "timestamp",
  "downloadUrl": "string"
}
```

#### List Documents
```
GET /api/documents?patientId={patientId}&documentType={documentType}
```
Response:
```json
{
  "total": 0,
  "page": 0,
  "size": 20,
  "documents": [
    {
      "id": "uuid",
      "title": "string",
      "description": "string",
      "documentType": "string",
      "createdBy": "uuid",
      "created": "timestamp"
    }
  ]
}
```

## Blockchain Service

### Transaction Management

#### Get Transaction
```
GET /api/blockchain/transactions/{txId}
```
Response:
```json
{
  "txId": "string",
  "timestamp": "timestamp",
  "channel": "string",
  "chaincodeId": "string",
  "creator": "string",
  "type": "DOCUMENT_UPLOAD | ACCESS_GRANT | REVOCATION",
  "status": "VALID | INVALID",
  "payload": {
    "key": "string",
    "value": "string"
  }
}
```

#### Query Ledger
```
GET /api/blockchain/ledger?key={key}
```
Response:
```json
{
  "key": "string",
  "value": "string",
  "txId": "string",
  "timestamp": "timestamp"
}
```

#### Document Audit Trail
```
GET /api/blockchain/audit/{documentId}
```
Response:
```json
{
  "documentId": "string",
  "events": [
    {
      "txId": "string",
      "timestamp": "timestamp",
      "action": "UPLOAD | VIEW | GRANT_ACCESS | REVOKE_ACCESS",
      "performedBy": "uuid",
      "details": "string"
    }
  ]
}
``` 