package com.example.demo.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Map;

/**
 * 主数据源配置
 *      注：shardingsphere有很多不支持的SQL，可以用该数据源管理
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
       entityManagerFactoryRef="entityManagerFactoryPrimary",
       transactionManagerRef="transactionManagerPrimary",
       //设置repository类所在位置
       basePackages={"com.example.*.repository"})
public class DataSourcePrimrayConfig {

    @Autowired
    @Qualifier("dataSourcePrimary")
    private DataSource dataSourcePrimary;

    @Autowired
    private JpaProperties jpaProperties;
    
    private Map<String, Object> getVendorProperties() {
    	HibernateSettings settings = new HibernateSettings();
        return jpaProperties.getHibernateProperties(settings);
    }
    
    @Primary
    @Bean(name="entityManagerFactoryPrimary")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryPrimary(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(dataSourcePrimary)
                .properties(getVendorProperties())
                //设置entity所在位置
                .packages("com.example.*.domain")
                .persistenceUnit("persistenceUnitPrimary")
                .build();
    }
    
    @Bean(name="dataSourceTransactionManager")
    public DataSourceTransactionManager dataSourceTransactionManager() {
        return new DataSourceTransactionManager(dataSourcePrimary);
    }

    @Primary
    @Bean(name="transactionManagerPrimary")
    public PlatformTransactionManager transactionManagerPrimary(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactoryPrimary(builder).getObject());
    }
    
    @Primary
    @Bean(name="dataSourcePrimary")
    @ConfigurationProperties(prefix="spring.datasource.primary")
    public DataSource dataSourcePrimary() {
        return DruidDataSourceBuilder.create().build();
    }
    
    @Primary
    @Bean(name="entityManagerPrimary")
    public EntityManager entityManagerPrimary(EntityManagerFactoryBuilder builder) {
        return entityManagerFactoryPrimary(builder).getObject().createEntityManager();
    }
}
