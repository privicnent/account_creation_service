# Account Creation Service

This service is responsible for creating new customer accounts and providing login functionality.
## Endpoints

- `POST /register`: Creates a new customer account. The request body should contain customer details like name, birth date, address, and document details.
- `POST /login`: Authenticates an existing user. The request body should contain the username and password.

## Running the Application Locally

1. Ensure you have [JDK 17] and [Maven 3] installed.
2. Clone the repository to your local machine.
3. Navigate to the project directory and run `mvn spring-boot:run`.

IntelliJ Setup:
Import the Project: Open IntelliJ IDEA, click on File -> New -> Project from Existing Sources..., navigate to the directory where you cloned the repository, and select the pom.xml file. Click OK and follow the prompts to import the project.

## Running Tests

1. Navigate to the project directory.
2. Run `mvn test` to execute the unit tests.
3. Run `mvn verify` to execute the integration tests.

## Test Cases

The test cases are located in the `src/test/java` directory. They cover various scenarios including field validation, request handling, and service logic.

- `RegisterControllerTest`: Tests the `RegisterController` class, which handles the `/register` endpoint.
- `LogonServiceTest`: Tests the `LogonService` class, which handles the `/login` endpoint.
- `RegisterServiceTest`: Tests the `RegisterService` class, which handles the registration logic.
- `RegisterSaveServiceTest`: Tests the `RegisterSaveService` class, which handles the saving of registration data.


## Error Codes

The application distinguishes between technical and functional errors using error codes.

- Technical errors are represented by the `TechnicalErrorCodes` enum in the `nl.com.acs.common.exception` package. These errors are typically caused by system issues like database errors or network issues.
- Functional errors are represented by the `FunctionalErrorCodes` enum in the `nl.com.acs.common.exception` package. These errors are typically caused by business rule violations like invalid input data.

Each error code has a unique string code and a default error message. The error code and message are returned in the error response when an error occurs.

## Database Setup

The application uses an H2 in-memory database for development and testing purposes. You can access the H2 database console at http://localhost:8080/account/h2/login.jsp when the application is running. The JDBC URL is jdbc:h2:mem:testdb. The username is sa and there is no password.

## Running DDL Scripts

The DDL scripts are located in the `src/main/resources/schema.sql`.
The scripts are run automatically when the application starts. If you want to run the scripts manually, you can do so using any SQL client. Connect to the H2 database using the JDBC URL and credentials mentioned above, and then run the scripts in the SQL client.

## Resetting the Database

Since the H2 database is in-memory, all data is lost when the application is stopped. To reset the database, simply stop and restart the application.

##  Basic Authorization

The Basic Authorization is automatically applied to all incoming HTTP requests, so you do not need to do anything to use it. 
Just make sure to include the Basic Authorization header in your requests.For example, if the username is admin and the password is admin, 

## API Documentation

API documentation is available at http://localhost:8080/account/swagger-ui.html when the application is running. The documentation includes information about the request and response formats, as well as the possible error codes for each endpoint.


## Rate Limiter Configuration(Good to Know) 
- Limit for period: This is the number of permissions available during one limit refresh period. In this case, it's set to 2, which means the service can be called 2 times in one limit refresh period.  
- Limit refresh period: This is the duration of a limit refresh period. The service will gain its full quota back after each limit refresh period. In this case, it's set to 2 seconds.  
- Timeout duration: This is the maximum wait time a thread will wait to acquire permission. In this case, it's set to 10 seconds.  
