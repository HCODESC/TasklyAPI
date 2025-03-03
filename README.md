# Taskly - Spring Boot Task Management API

## Overview
Taskly is a robust RESTful API built with Spring Boot that provides comprehensive task management capabilities. This application enables seamless task organization through a set of intuitive endpoints for creating, reading, updating, and deleting tasks.

## Features
- Create and manage tasks
- Update task status and details
- Delete unwanted tasks
- Retrieve task information
- RESTful API architecture
- Built with Spring Boot for reliability and scalability

## Prerequisites
- Java 11 or higher
- Maven 3.6+
- Git

## Installation

### Quick Start
1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/taskly.git
   cd taskly
   ```

2. Build the project:
   ```bash
   mvn clean install
   ```

3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

The API will be available at `http://localhost:8080`

## API Endpoints

The API provides the following core endpoints:

### Tasks
- `GET /api/tasks` - List all tasks
- `POST /api/tasks` - Create a new task
- `GET /api/tasks/{id}` - Get task details
- `PUT /api/tasks/{id}` - Update a task
- `DELETE /api/tasks/{id}` - Delete a task


## Contributing
Contributions are welcome! Please feel free to submit a Pull Request.



