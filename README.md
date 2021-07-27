# url-shortener
url-shortener-service

# swagger-ui and api-docs
http://localhost:9090/swagger-ui
http://localhost:9090/api-docs


# Tools and Techniques

- Spring Boot 2 , Spring Web
- H2 - in memory database
- Hibernate as JPA Implementation
- Open API Specification - Swagger UI
- Lombok
- Guava Library - Using Probabilistic Data Structure (Bloom filters)
- Jacoco - for coverage reports
- Junit5 for Testing

# Features

1. We can create a short Url/shortCode by providing an alias for a specified amount of time.
2. If no alias is provided then a unique shortCode would be generated whose length is
decided based on the configured parameter.
url.shortener.short-code.length=6
This is configurable and it defaults to 8.
3. If we are providing an alias , then the membership is checked using a space efficient
data structure [Bloom filters] , which is prepopulated on startup and also aliases are fed
as new aliases are introduced.
4. Global Exception Handler is used inorder to handle the exceptions.
5. Custom exceptions are thrown based on the scenarios.
