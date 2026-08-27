# InternMatch

InternMatch is an internship eligibility scoring and matching platform developed as an individual internship project. The project helps students create profiles, define their skills, view internship postings, and calculate their eligibility scores for available internship opportunities.

The system consists of a Spring Boot-based backend and a lightweight frontend developed with HTML, CSS, and Vanilla JavaScript.

## Overview

InternMatch aims to reduce the inefficiency of internship search processes by bringing student profile information, internship postings, posting criteria, and eligibility score calculation into a single digital workflow.

In the current version, the platform mainly focuses on the student-oriented workflow. Students can register, log in, create a profile, add skills, view internship postings, and calculate their eligibility score for postings. Employer-side posting and criteria operations are supported through backend API operations, while a complete company-facing frontend management panel is considered a future improvement.

## Key Features

- User registration and login
- JWT-based authentication
- Role-based access control
- Student profile management
- Skill catalog and student skill association
- Internship posting creation and listing
- Posting criteria management
- Eligibility score calculation
- Swagger UI API documentation
- PostgreSQL database support
- H2 database support for local development
- Automated tests for selected backend components

## Technology Stack

| Category | Technologies |
|---|---|
| Backend | Java, Spring Boot, Spring Web, Spring Security, Spring Data JPA, Hibernate |
| Frontend | HTML5, CSS3, Vanilla JavaScript, Fetch API, localStorage |
| Database | PostgreSQL, H2 Database |
| Security | JWT, BCrypt, Stateless Session |
| Documentation | Swagger UI, OpenAPI |
| Testing | JUnit 5, Mockito, MockMvc, Spring Security Test |
| Build Tool | Apache Maven |

## Project Architecture

### Backend Modules

| Module | Purpose |
|---|---|
| Authentication | User registration, login, JWT token generation, and current user retrieval |
| Student Profile | Student profile creation, retrieval, and update operations |
| Skills | Skill catalog listing and student skill association |
| Company Profile | Company-related data structure and backend profile operations |
| Internship Postings | Internship posting creation, listing, retrieval, update, and status management |
| Posting Criteria | Criteria definition and management for internship postings |
| Match Score | Eligibility score calculation and score result management |
| Health Controller | Basic backend health check endpoint |

### Frontend Views

| View | Purpose |
|---|---|
| Home Page | Presents the main purpose of the InternMatch platform |
| Registration View | Allows students to create an account |
| Login View | Allows users to authenticate and obtain access |
| Student Profile Tab | Allows students to manage profile information |
| Skills Tab | Allows students to add and manage their skills |
| Internship Postings & Score Tab | Allows students to view postings and calculate eligibility scores |

## Project Scope

The current implementation focuses on:

- Authentication and authorization
- Student profile management
- Skill management
- Internship posting operations
- Posting criteria definition
- Eligibility score calculation
- API documentation and manual API verification through Swagger UI

The database includes `applications` and `audit_logs` structures for future extensibility. However, these are not implemented as complete active modules in the current version. The `applications` structure exists at the entity, repository, DTO, and enum levels, but there is no active `ApplicationController` or `ApplicationService`. Similarly, `audit_logs` exists in the schema, but automatic audit logging is not actively integrated into service methods.

## Getting Started

### Prerequisites

- Java 17 or higher
- Apache Maven
- PostgreSQL 12+ for persistent database usage
- Modern web browser

## Backend Setup

Clone the repository:

```bash
git clone https://github.com/Ba2han3617/InternMatch.git
cd InternMatch
```

Run the backend application:

```bash
mvn spring-boot:run
```

The backend runs on:

```text
http://localhost:8081
```

Health check endpoint:

```text
http://localhost:8081/api/health
```

Expected response:

```text
InternMatch API is running
```

## Frontend Setup

The frontend was developed using HTML, CSS, and Vanilla JavaScript. During development, it was served through a local static server.

Frontend development port used in the project:

```text
http://localhost:5500
```

The frontend communicates with the backend by using the Fetch API. After successful login, the JWT token is stored in `localStorage` and attached to protected API requests.

## API Documentation

Swagger UI is available after running the backend:

```text
http://localhost:8081/swagger-ui/index.html
```

Swagger UI was used to inspect endpoint groups, request parameters, request bodies, response models, and HTTP status codes.

## Main API Groups

| Endpoint Group | Purpose |
|---|---|
| Authentication | Registration, login, and current user information |
| Student Profile | Student profile operations |
| Skills | Skill catalog and student skill operations |
| Internship Postings | Posting creation, listing, update, and status operations |
| Posting Criteria | Criteria management for postings |
| Match Score | Eligibility score calculation and score retrieval |
| Company Profile | Company profile-related backend operations |
| Health Controller | Backend running status check |

## Database Schema

The database schema includes active workflow tables and future extension structures.

| Table Name | Description |
|---|---|
| users | Stores user account information |
| roles | Stores role definitions |
| user_roles | Associates users with roles |
| student_profiles | Stores student academic and profile information |
| student_skills | Associates students with skills |
| skills | Stores the system-wide skill catalog |
| companies | Stores company information |
| internship_postings | Stores internship posting information |
| posting_criteria | Stores evaluation criteria for postings |
| match_scores | Stores calculated eligibility score results |
| applications | Preparatory structure for future application tracking |
| audit_logs | Preparatory structure for future event logging |

## Eligibility Score Calculation

Eligibility score calculation is performed by comparing student profile and skill data with criteria assigned to internship postings.

| Criterion Type | Evaluation Logic |
|---|---|
| SKILL | Checks whether the student has the required skill |
| GPA | Checks whether the student's GPA is greater than or equal to the minimum GPA criterion |
| GRADE_LEVEL | Checks whether the student's grade level satisfies the posting criterion |
| LOCATION | Compares the student's city with the posting location criterion |
| WORK_MODE | Compares the student's preferred work model with the posting work mode criterion |
| CUSTOM | Stored as a custom criterion but not included in automatic scoring |

## Testing

The project includes automated tests for selected backend components.

Implemented test files include:

- AuthControllerTest
- HealthControllerTest
- MatchScoreControllerTest
- AuthServiceTest
- MatchScoreServiceTest
- PostingCriterionServiceTest

These tests verify selected authentication, health check, posting criteria, match score, controller-level, and service-level behaviors. Other modules such as StudentProfile, Skill, InternshipPosting, Company, and Application were primarily verified through Swagger UI and frontend interactions rather than dedicated automated test classes.

Run tests:

```bash
mvn test
```

## Security

The project uses Spring Security with JWT-based stateless authentication.

Security-related features include:

- BCrypt password hashing
- JWT token generation and validation
- Stateless session management
- Role-based access control
- Protected API endpoints
- CORS configuration for local frontend-backend communication

## Future Improvements

Possible future improvements include:

- Complete internship application tracking workflow
- Active audit logging integration
- Company-facing frontend management panel
- Advanced filtering and search for postings
- Notification system
- Resume upload support
- Advanced recommendation logic
- Mobile application support

## Repository

| Item | Information |
|---|---|
| Project Name | InternMatch |
| Repository Platform | GitHub |
| Repository URL | https://github.com/Ba2han3617/InternMatch |
| Included Contents | Backend source code, frontend files, test files, database structures, Maven configuration, and project documentation |

## Author

Batuhan Kocamanoğlu

Developed as an individual internship project.

## Version

Version: 1.0.0  
Last Updated: August 2026  
Status: Internship Project Prototype
