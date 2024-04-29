**Spring Boot REST API Client with Spring Security**

This client application is designed to interact with a Spring Boot REST API secured with Spring Security. It provides functionalities for user authentication, registration, and administrative tasks. 

### Features:

- **User Authentication**: Users can log in using their username and password. Invalid login attempts are handled gracefully.
- **User Registration**: New users can register with the application by providing their details. Registration is secured and validated.
- **Admin Panel Access**: Administrators can access the admin panel to perform administrative tasks.

### How to Use:

1. **User Authentication**:
   - Send a POST request to `/auth/login` endpoint with the user's credentials (username and password) as JSON payload.
   - Upon successful authentication, receive a JWT token in the response.

2. **User Registration**:
   - Send a POST request to `/auth/registration` endpoint with the user's details (username, password, etc.) as JSON payload.
   - Receive a JWT token upon successful registration.

3. **Admin Panel Access**:
   - Send a GET request to `/auth/admin` endpoint to access the admin panel. Ensure that the user has appropriate admin privileges.

### Security:

- The application uses JWT (JSON Web Tokens) for user authentication. 
- Passwords are hashed using BCryptPasswordEncoder before storing in the database.
- Access to certain endpoints is restricted based on user roles and authorities.

### Dependencies:

- HTTP Client library (e.g., Apache HttpClient, OkHttp)
- JSON parsing library (e.g., Gson, Jackson)

### License:

This project is licensed under the MIT license. See the LICENSE file for details.

### Support:

For any inquiries or issues, please contact azimjon.works@gmail.com. Bug reports and feature requests are welcome.
