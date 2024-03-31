package org.library.config.database;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(basePackages = "org.library.databases.library_control_system.repository",
        entityManagerFactoryRef = "libraryControlSystemEntityManagerFactory",
        transactionManagerRef = "libraryControlSystemTransactionManager")
@EnableConfigurationProperties
public class LibraryControlSystemJpaConfig {

    @Bean
    @ConfigurationProperties("databases.library-control-system")
    public DataSource dsLibraryControlSystem() {
        return DataSourceBuilder.create().build();
    }

    private Map<String, String> additionalProperties() {
        Map<String, String> configuration = new HashMap<>();
        configuration.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");

        return configuration;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean libraryControlSystemEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("dsLibraryControlSystem") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("org.library.databases.library_control_system.entity")
                .properties(additionalProperties())
                .build();
    }

    @Bean
    public EntityManagerFactoryBuilder entityManagerFactoryBuilder() {
        return new EntityManagerFactoryBuilder(
                new HibernateJpaVendorAdapter(),
                new HashMap<>(),
                null);
    }

    @Bean
    public PlatformTransactionManager libraryControlSystemTransactionManager(
            @Qualifier("libraryControlSystemEntityManagerFactory") EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}
