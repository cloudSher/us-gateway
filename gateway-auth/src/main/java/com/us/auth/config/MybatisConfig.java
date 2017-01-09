package com.us.auth.config;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@MapperScan("com.startup.saas.storage.mapper")
public class MybatisConfig {


    private Logger logger = LoggerFactory.getLogger(MybatisConfig.class);

	@Autowired
	private Environment env;
	
    @Bean
    public SqlSessionFactory sqlSessionFactoryBean(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:com/us/auth/storage/*/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * 创建数据源
     * @param
     * @return 
     */
    @Bean
	public DataSource dataSource(){
		Properties properties = new Properties();
		properties.setProperty("driverClassName", env.getProperty("spring.datasource.driver-class-name"));
		properties.setProperty("url", env.getProperty("spring.datasource.url"));
		properties.setProperty("username", env.getProperty("spring.datasource.username"));
		properties.setProperty("password", env.getProperty("spring.datasource.password"));
        try {
			return DruidDataSourceFactory.createDataSource(properties);
		} catch (Exception e) {
			return null;
		}
	}


}
