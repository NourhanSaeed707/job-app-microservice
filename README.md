# Job Application Microservice
## Introduction:
- This project is a scalable and modular Job Application Microservices System designed using Java Spring Boot. It leverages a microservices architecture to ensure flexibility, maintainability, and ease of scaling as business requirements evolve. The system is powered by PostgreSQL for robust and efficient data storage and retrieval.
- Discovery Server: Built with Spring Cloud Netflix Eureka, this component enables seamless service registration and discovery, ensuring smooth communication between microservices.
- API Gateway: Developed using Spring Cloud Gateway, this component acts as a single entry point for all client requests, providing routing, load balancing, and security.

## Features
1. Job Service:
- Job Management: Enables CRUD operations (Create, Read, Update, Delete) for job postings, including job title, description, requirements, and salary.
- Search and Filter: Provides advanced search and filtering capabilities based on criteria such as job title, location, company, and salary range.
- Review Integration: Allows users to view and submit reviews related to specific jobs, contributing to a transparent hiring process.

2. Company Service:
- Company Profiles: Facilitates CRUD operations for company profiles, including name, industry, location, and description.
- Company Reviews: Enables users to submit and access reviews for companies, fostering informed decision-making for job seekers.
- Job Listings Association: Links job postings to the respective company profiles for easy navigation.

3. Service Registry:
- Service Discovery: Utilizes Spring Cloud Netflix Eureka for registering and discovering all microservices, ensuring seamless communication between them.
- Health Monitoring: Tracks the status of all registered services, providing visibility into the system's overall health.
- Load Balancing: Works in conjunction with the gateway to distribute traffic evenly across instances of a service.

4. Gateway Service:
- Centralized API Gateway: Serves as the single entry point for client requests, simplifying interaction with backend services.
- Dynamic Routing: Routes client requests to the appropriate microservice based on predefined rules.
- Security Enforcement: Implements authentication and authorization mechanisms, ensuring secure access to the system.
- Rate Limiting: Protects services from overloading by limiting the number of requests a client can make within a specified time frame.

## Getting Started
- pull clone git url project
- run mvn dependencies
- run project

