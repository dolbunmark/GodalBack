package com.dolbunmark.godal.config;

import com.dolbunmark.godal.domain.Basket;
import com.dolbunmark.godal.domain.Order;
import com.dolbunmark.godal.domain.Product;
import com.dolbunmark.godal.domain.User;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
public class HibernateConfig {

    private Environment env;

    @Autowired
    public HibernateConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
        dataSource.setUrl(env.getProperty("db.url"));
        dataSource.setUsername(env.getProperty("db.login"));
        dataSource.setPassword(env.getProperty("db.password"));

        return dataSource;
    }

    @Bean(name = "entityManagerFactory")
    public LocalSessionFactoryBean getSessionFactory() {

        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
        factoryBean.setAnnotatedClasses(Product.class, User.class, Order.class, Basket.class);
        factoryBean.setHibernateProperties(hibernateProperties());
        factoryBean.setDataSource(dataSource());

        return factoryBean;
    }

    @Bean
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(getSessionFactory().getObject());
        return transactionManager;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
        properties.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        properties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        properties.setProperty(
                "hibernate.hbm2ddl.import_files",
                env.getProperty("hibernate.hbm2ddl.import_files"));
        return properties;
    }
}
