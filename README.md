# Cabinet Comptable Al Amane - Document Management System

## Description
Application de gestion documentaire pour le Cabinet Comptable Al Amane permettant la centralisation, sécurisation et validation des pièces justificatives comptables conformément à la réglementation marocaine.

## Technologies
- Spring Boot 3.4.0+
- Spring Security 6 (OAuth2 Resource Server with JWT)
- H2 Database
- JUnit 5 & Mockito
- Maven

## Fonctionnalités

### Authentification
- JWT avec expiration 24h
- Deux rôles: COMPTABLE et SOCIETE

### Côté Société
- Upload de documents (PDF, JPG, PNG jusqu'à 10MB)
- Consultation de tous ses documents
- Visualisation du statut (EN_ATTENTE/VALIDE/REJETE)

### Côté Comptable
- Liste des documents en attente
- Validation avec commentaire optionnel
- Rejet avec motif obligatoire
- Vue par société cliente

## Utilisateurs par défaut
- **Comptable**: comptable@example.com / password
- **Société**: societe@example.com / password

## API Endpoints

### Authentication
- POST `/api/auth/login` - Connexion

### Documents (Société)
- POST `/api/documents` - Upload document (multipart/form-data)
- GET `/api/documents/my-documents` - Mes documents
- GET `/api/documents/{id}/download` - Télécharger document

### Documents (Comptable)
- GET `/api/documents/pending` - Documents en attente
- GET `/api/documents/societe/{societeId}` - Documents par société
- PUT `/api/documents/{id}/validate` - Valider document
- PUT `/api/documents/{id}/reject` - Rejeter document

## Lancer l'application
```bash
mvn spring-boot:run
```

## Tests
```bash
mvn test
```

## Conformité Légale
- Conservation des documents durant 10 ans (Loi N° 9-88)
- Protection contre perte/détérioration (Article 211 CGI)
