package com.cecere.config;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.ejb.HibernatePersistence;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.teleal.cling.support.model.item.Item;

import com.cecere.cling.converter.ClingItemToContentConverter;
import com.cecere.converter.Converter;
import com.cecere.domain.dlna.Content;
import com.cecere.repository.ReadOnlyTreeRepository;
import com.cecere.repository.dlna.DlnaContentRepository;
import com.cecere.springdemo.repository.DemoObjectRepository;
import com.cecere.springdemo.domain.DemoObject;

@Configuration
//dont need database stuff or spring data yet
//@EnableTransactionManagement
//@ImportResource( "classpath*:*springDataConfig.xml") //has to be xml until supported in javaconfig
public class RepositoryConfig {
	
	@Bean
	public ReadOnlyTreeRepository dlnaContentRepository() throws InterruptedException{
		ReadOnlyTreeRepository ret = new DlnaContentRepository(clingItemToContentConverter());
		((DlnaContentRepository)ret).initClingService();
		Thread.sleep(10000);//TODO: find better way than to just wait for registry to fill up
		((DlnaContentRepository)ret).initContents();
		return ret;
	}
	
	@Bean
	public Converter<Item,Content> clingItemToContentConverter(){
		return new ClingItemToContentConverter();
	}
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
