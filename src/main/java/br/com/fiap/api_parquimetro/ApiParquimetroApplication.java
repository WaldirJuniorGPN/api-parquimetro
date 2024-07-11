package br.com.fiap.api_parquimetro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@EnableScheduling
public class ApiParquimetroApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiParquimetroApplication.class, args);
	}

}
