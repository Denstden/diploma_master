package ua.kiev.unicyb.diploma.infrastructure;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import ua.kiev.unicyb.diploma.infrastructure.jpa.ImprovedCamelCaseNamingStrategy;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(basePackages = "ua.kiev.unicyb.diploma.repositories", entityManagerFactoryRef = "postgresEmf", transactionManagerRef = "postgresTm")
public class PostgresDatabaseConfig {

    @Bean(name = "postgresDs")
    @ConfigurationProperties(prefix = "spring.postgres.datasource")
    public DataSource postgresDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "postgresEmf")
    public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean(EntityManagerFactoryBuilder builder) {
        return builder.dataSource(postgresDataSource())
                .packages("ua.kiev.unicyb.diploma.domain.entity")
                .persistenceUnit("postgres")
                .properties(jpaProperties())
                .build();
    }

    private Map<String, Object> jpaProperties() {
        Map<String, Object> props = new HashMap<>();
        props.put("hibernate.physical_naming_strategy", ImprovedCamelCaseNamingStrategy.class.getName());
        props.put("database-platform", org.hibernate.dialect.PostgreSQL94Dialect.class);
        props.put("hibernate.hbm2ddl.auto", "create");
        return props;
    }

    @Bean(name = "postgresTm")
    public PlatformTransactionManager transactionManager(@Qualifier("postgresEmf") EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager txManager = new JpaTransactionManager(entityManagerFactory);
        return txManager;
    }
}
