
package com.asitc.mongodbapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableMongoRepositories
@SpringBootApplication
// @EnableCaching
@EnableDiscoveryClient
// @Configuration
public class Application {

	public static void main(final String[] args) {
		SpringApplication.run(Application.class, args);
	}

	// @Bean
	// public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
	// return new
	// Jackson2ObjectMapperBuilder().propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
	// }

}
