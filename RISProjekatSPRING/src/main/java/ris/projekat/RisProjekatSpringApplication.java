package ris.projekat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import ris.projekat.repository.ServiceUserRepository;

@SpringBootApplication
@EntityScan("model")
public class RisProjekatSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(RisProjekatSpringApplication.class, args);
	}

}
