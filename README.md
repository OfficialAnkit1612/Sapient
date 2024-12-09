# Product Catalog API

## Description
A Spring Boot project to load data from an external dataset into an in-memory H2 database and provide REST endpoints to fetch products based on various user criteria.

## Build and Run
1. Clone the repository.
2. Navigate to the project directory.
3. Run `mvn clean install` to build the project.
4. Run `mvn spring-boot:run` to start the application.

## API Endpoints
- `GET /api/products`: Fetch all products.
- `GET /api/products/{id}`: Fetch a product by ID.
- `POST /api/products`: Create a new product.
- `PUT /api/products/{id}`: Update an existing product.
- `DELETE /api/products/{id}`: Delete a product by ID.
- `POST /api/products/load`: Load products into the system.
- `GET /api/products/search`: Search for products by a given keyword.
- `GET /api/products/find`: Find a product by its ID or SKU.

## Swagger Documentation
Access the API documentation at `http://localhost:8080/swagger-ui.html`.