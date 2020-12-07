package org.test.api;

import com.ulisesbocchio.jasyptspringboot.annotation.EncryptablePropertySource;

import org.jasypt.encryption.StringEncryptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EncryptablePropertySource("application.yaml")
public class ApiApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ApiApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

	@Bean("encryptorBean")
	public StringEncryptor stringEncryptor() {
		return JasyptEncryptor.stringEncryptor();
	}

}
