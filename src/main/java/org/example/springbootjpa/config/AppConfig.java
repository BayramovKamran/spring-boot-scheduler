package org.example.springbootjpa.config;

import org.springframework.context.annotation.*;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

import java.util.Objects;
import java.util.Properties;

@Configuration
@EnableAspectJAutoProxy
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
@ComponentScan(basePackages = "org.example")
public class AppConfig {

    private final Environment env;

    public AppConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(env.getProperty("spring.datasource.driver-class-name")));
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.password"));
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("org.example.springbootjpa.entity");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaProperties(properties());
        return em;
    }

    public Properties properties() {
        Properties properties = new Properties();
        properties.put("spring.jpa.properties.hibernate.cache.use_query_cache", "true");
        properties.put("spring.jpa.properties.hibernate.cache.use_second_level_cache", "true");
        properties.put("spring.jpa.properties.hibernate.cache.region.factory_class", "org.hibernate.cache.j cache.CaffeineRegionFactory");
        return properties;
    }
}