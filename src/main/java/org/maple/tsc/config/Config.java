/**
 * @author Maple
 *
 */
package org.maple.tsc.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableConfigurationProperties(ConfigProperties.class)
@EnableTransactionManagement
@MapperScan("org.maple.tsc.mappers")
public class Config {
	
	@Autowired
	ConfigProperties configProperties;
	
	/**
	 * The data source of PostgreSQL
	 * 
	 * @return
	 */
	@Bean
	@Scope(value = "singleton")
	public DataSource dataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName(configProperties.getDbDriverClassName());
		ds.setUrl(configProperties.getDbUrl());
		ds.setUsername(configProperties.getDbUserName());
		ds.setPassword(configProperties.getDbPassword());
		
		return ds;
	}
	
	/**
	 * The transaction manager
	 * 
	 * @return
	 */
	@Bean
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}
	
	/**
	 * The SQL Session Factory
	 * 
	 * @return
	 * @throws Exception
	 */
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(dataSource());
		
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Resource[] resources = resolver.getResources(configProperties.getDbMapperXml());
		factoryBean.setMapperLocations(resources);
		
		return factoryBean.getObject();
		
	}
}