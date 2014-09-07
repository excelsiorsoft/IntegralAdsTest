package com.excelsiorsoft.integral.process;


import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.excelsiorsoft.integral.process.akka.Aggregator;
import com.excelsiorsoft.integral.process.akka.Master;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

@Configuration
public class ExampleConfiguration {

	@Value("${batch.jdbc.driver}")
	private String driverClassName;

	@Value("${batch.jdbc.url}")
	private String driverUrl;

	@Value("${batch.jdbc.user}")
	private String driverUsername;

	@Value("${batch.jdbc.password}")
	private String driverPassword;

	@Autowired
	@Qualifier("jobRepository")
	private JobRepository jobRepository;

	
	
	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(driverUrl);
		dataSource.setUsername(driverUsername);
		dataSource.setPassword(driverPassword);
		return dataSource;
	}

	@Bean
	public SimpleJobLauncher jobLauncher() {
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.setJobRepository(jobRepository);
		return jobLauncher;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}
	
	 @Autowired
	    private ApplicationContext applicationContext;
	
	public static final String MASTER_ACTOR = "master-actor";
	public static final String AGGREGATOR_ACTOR = "aggregator-actor";
    public static final String ACTOR_SYSTEM = "Aggregator-actorSystem";
    private ActorSystem actorSystem;
    
    @Bean(name = ACTOR_SYSTEM, destroyMethod = "shutdown")
    public ActorSystem actorSystem() {
        actorSystem = ActorSystem.create(ACTOR_SYSTEM);
        return actorSystem;
    }
 
    @Bean(name = MASTER_ACTOR)
    @DependsOn({ACTOR_SYSTEM})
    public ActorRef businessActor() {
        return actorSystem.actorOf(//
                new DependencyInjectionProps(applicationContext, Master.class), MASTER_ACTOR);
    }
    
    @Bean(name = AGGREGATOR_ACTOR)
    @DependsOn({ACTOR_SYSTEM})
    public ActorRef aggregatorActor() {
        return actorSystem.actorOf(//
                new DependencyInjectionProps(applicationContext, Aggregator.class), AGGREGATOR_ACTOR);
    }

}
