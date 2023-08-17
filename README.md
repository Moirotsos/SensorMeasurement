# SensorMeasurement

This is a simple web application and Restful API implementation in Spring REST APIs in Spring Framework. It will act as a reporting service for an IoT platform by storing information and handling requests for sensor readings.

# Prerequisites

Before you begin, make sure you have the following software installed on your machine:

- Java JDK 17: This application is built using Java, so ensure you have Java Development Kit 17 installed. You can download it from the [official OpenJDK website](https://adoptium.net/) or use a package manager compatible with your operating system.
- Docker 24.0.5: Docker is essential for containerizing your application. Install Docker by following the instructions for your specific platform on the [official Docker website](https://docs.docker.com/get-docker/).
- Docker Compose 1.29.2: Docker Compose simplifies multi-container application deployment. Install Docker Compose by following the instructions provided in the [official documentation](https://docs.docker.com/compose/install/).

# Installation

  Follow these steps to set up and run the Spring Boot application on your local machine:

  1. Clone the Repository:
    Open your terminal and navigate to the directory where you want to clone the repository. 
    Run the following command:
    git clone https://github.com/Moirotsos/SensorMeasurement.git
 
  1. Navigate to Project Directory:
     Change your working directory to the cloned repository:
     cd your-repo
  1. Build the Application:
     Build the Spring Boot application using Maven:
     mvn clean install
  1. Run the Application:
     After the build is successful, you can run the application using the following command:
     java -jar target/your-app-name.jar
     
     Replace your-app-name with the actual name of your application's JAR file.

  1. Access the Application:
     Once the application is up and running, open your web browser and navigate to 'http://localhost:8080' to access the application.



# Configuration
  
  Your Spring Boot application uses PostgreSQL as its database backend. To configure the database connection, follow these steps:

  #### Database Configuration:

  In the 'application.properties' or 'application.yml' file in your Spring Boot project, make sure to specify the database connection details. Here's    an example configuration for PostgreSQL:

    spring.datasource.url=jdbc:postgresql://localhost:5432/your-database-name
  
    spring.datasource.username=your-username
  
    spring.datasource.password=your-password
  
    spring.datasource.driver-class-name=org.postgresql.Driver
  
 
# Dockerization

 We've prepared Docker containers for seamless deployment of our Spring Boot application. Follow these steps to get your application up and running     using Docker and Docker Compose:

 1. Building Docker Images:
    Your project's Dockerfile is already set up. Open a terminal and navigate to your project directory. Build the Docker image by running the       following command:
    
    docker build -t your-app-name .

 1. Running with Docker Compose:
    Your docker-compose.yml file is configured to start your application and its required services. In the terminal, navigate to your project           directory containing the 'docker-compose.yml' file, and execute the following command:

    docker-compose up
    
    Docker Compose will start the Spring Boot application and the associated PostgreSQL database container. You can access your app at       'http://localhost:8080'.

# Authors     
- @[Moirotsos](https://github.com/Moirotsos/SensorProject)
