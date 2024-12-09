package com.sapients.product_catalog_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application.yml")
public class ProductCatalogApiApplication {

	public static void main(String[] args) {

		SpringApplication.run(ProductCatalogApiApplication.class, args);
	}
}
