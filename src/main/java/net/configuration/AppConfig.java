package net.configuration;


import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@ComponentScan(basePackages = {"net"})
@EnableTransactionManagement
@EnableWebMvc
@PropertySource("classpath:connection.properties")
public class AppConfig implements EnvironmentAware {

    private Environment env;

    @Override
    public void setEnvironment(Environment environment) {
        this.env = environment;
    }

    @Bean
    public InternalResourceViewResolver internalResourceViewResolver(){
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/pages/");
        resolver.setSuffix(".jsp");

        return resolver;
    }

    @Bean
    public DataSource dataSource(){

        BasicDataSource source = new BasicDataSource();
        source.setDriverClassName(env.getRequiredProperty("db.class.name"));
        source.setUrl(env.getRequiredProperty("db.connection.string"));
        source.setUsername(env.getRequiredProperty("user"));
        source.setPassword(env.getRequiredProperty("pass"));

        return source;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {

        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        entityManagerFactoryBean.setPackagesToScan(new String[]{"net.model"});

        entityManagerFactoryBean.setJpaProperties(hibernateProperties());
        return entityManagerFactoryBean;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter(){
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(org.springframework.orm.jpa.vendor.Database.MYSQL);
        return adapter;
    }

    @Bean
    public JpaTransactionManager jpaTransactionManager(){
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory(dataSource(),jpaVendorAdapter()).getObject());
        return jpaTransactionManager;
    }

    final Properties hibernateProperties() {
        final Properties hibernateProperties = new Properties();

        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hbm2ddl"));
        hibernateProperties.setProperty("hibernate.dialect", env.getProperty("dialect"));
        hibernateProperties.setProperty("hibernate.show_sql", env.getProperty("showsql"));

        return hibernateProperties;
    }

    /*
    @Bean
    public PlatformTransactionManager transactionManager(){
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory());

        return jpaTransactionManager;
    }
    */

   /* @Bean
    public EntityManagerFactory entityManagerFaEntityManager(){

        Properties properties = PropertiesReader.getProperties(FilePath.CONNECTION_PROPERTIES);

        Map<String, Object> config = new HashMap<>();
        //Configuring JDBC properties
        config.put("javax.persistence.jdbc.url", properties.getProperty("DB_CONNECTION_STRING"));
        config.put("javax.persistence.jdbc.user", properties.getProperty("USER"));
        config.put("javax.persistence.jdbc.password", properties.getProperty("PASS"));
        config.put("javax.persistence.jdbc.driver", properties.getProperty("DB_CLASS_NAME"));
        //Hibernate properties
        config.put("hibernate.dialect", properties.getProperty("DIALECT"));
        config.put("hibernate.hbm2ddl.auto", properties.getProperty("HBN2DDL"));
        config.put("hibernate.show_sql", properties.getProperty("SHOW_SQL"));

        EntityManagerFactory entityManagerFactory = new HibernatePersistenceProvider().createContainerEntityManagerFactory(
                archiverPersistenceUnitInfo(),
                config);
        return entityManagerFactory;

    }

    private PersistenceUnitInfo archiverPersistenceUnitInfo() {
        return new PersistenceUnitInfo() {
            @Override
            public String getPersistenceUnitName() {
                return "ApplicationPersistenceUnit";
            }

            @Override
            public String getPersistenceProviderClassName() {
                return "org.hibernate.jpa.HibernatePersistenceProvider";
            }

            @Override
            public PersistenceUnitTransactionType getTransactionType() {
                return PersistenceUnitTransactionType.RESOURCE_LOCAL;
            }

            @Override
            public DataSource getJtaDataSource() {
                return null;
            }

            @Override
            public DataSource getNonJtaDataSource() {
                return null;
            }

            @Override
            public List<String> getMappingFileNames() {
                return Collections.emptyList();
            }

            @Override
            public List<URL> getJarFileUrls() {
                try {
                    return Collections.list(this.getClass()
                            .getClassLoader()
                            .getResources(""));
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }

            @Override
            public URL getPersistenceUnitRootUrl() {
                return null;
            }

            @Override
            public List<String> getManagedClassNames() {
                return Collections.emptyList();
            }

            @Override
            public boolean excludeUnlistedClasses() {
                return false;
            }

            @Override
            public SharedCacheMode getSharedCacheMode() {
                return null;
            }

            @Override
            public ValidationMode getValidationMode() {
                return null;
            }

            @Override
            public Properties getProperties() {
                return new Properties();
            }

            @Override
            public String getPersistenceXMLSchemaVersion() {
                return null;
            }

            @Override
            public ClassLoader getClassLoader() {
                return null;
            }

            @Override
            public void addTransformer(ClassTransformer transformer) {
            }

            @Override
            public ClassLoader getNewTempClassLoader() {
                return null;
            }
        };
    }*/
}
