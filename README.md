# RESTOCOMPRAS

This project includes Swagger UI for interactive API documentation and testing.

You can access the API documentation at: http://localhost:8080/swagger-ui.html

Domain model v 0.1:
![Domain Model](/docs/domain_version_0.1.jpg)

Postman Collection:
You can import the Postman collection from the following
file: [Postman Collection](docs/restocompras.postman_collection.json)

POST -> /login
body:
{
"name": "user@gmail.com",
"password": "password"
}

Authentication:
JWT (JSON Web Token) is used for authentication.