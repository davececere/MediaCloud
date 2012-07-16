package com.cecere.config;

import org.springframework.context.annotation.Configuration;

@Configuration
//dont need database stuff yet
//@EnableTransactionManagement
public class PersistenceConfig {
	
	/* dont need database yet
    @Bean
    public DataSource dataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUsername("root");
        ds.setPassword("");
        ds.setUrl("jdbc:mysql://localhost:3306/restdemo");
        return ds;
    }
    
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    	LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
    	entityManagerFactory.setDataSource(dataSource());
    	entityManagerFactory.setPackagesToScan(new String[] { "com.cecere.springdemo.domain" });
    	entityManagerFactory.setPersistenceProvider(new HibernatePersistence());
    	entityManagerFactory.afterPropertiesSet();
    	return entityManagerFactory;
    }
    
    //for transaction support
    @Bean
    public PlatformTransactionManager transactionManager() {
    	return new JpaTransactionManager(entityManagerFactory().getObject());
    }
    */
}
