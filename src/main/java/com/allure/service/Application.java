package com.allure.service;

import com.allure.service.framework.repository.BaseRepositoryFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.system.ApplicationPidFileWriter;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditingAwareProvider")
@EnableJpaRepositories(repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class, basePackages = "com.allure.service")
public class Application {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplicationBuilder()
                .sources(Application.class)
                .listeners(new ApplicationPidFileWriter())
                .build();
        application.run(args);
    }
}
