# fullstack-react-spring-security

A simple fullstack application built using React and Spring Boot + Spring Security.

This can be used as a simple boilerplate or reference for future projects with this stack 😁

The backend uses H2 as a in memory database for local testing purposes.

## Features

- Account registration
- Login
- Password recovery
- API to API communication (backend calls to public API https://random-data-api.com/)
- Token authentication (JWT) with Spring Security

## Folders

- <b>frontend</b>: React application
- <b>backend</b>: Spring Boot application

## Requirements

- NodeJS + npm
- Java 11

## Usage

To start the <b>frontend</b>, execute the ```npm install``` command on the frontend folder, followed by ```npm start```. The aplication will start on ```http://localhost:3000```.

For the <b>backend</b>, execute ```./mvnw spring-boot:run``` on the backend folder. The application will start on ```http://localhost:8080```.
