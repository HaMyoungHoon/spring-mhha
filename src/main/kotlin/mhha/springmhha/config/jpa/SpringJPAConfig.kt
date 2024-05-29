package mhha.springmhha.config.jpa

import com.zaxxer.hikari.HikariDataSource
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.util.*

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = ["mhha.springmhha.repository.sqlSpring"],
        entityManagerFactoryRef = SpringJPAConfig.ENTITY_MANAGER,
        transactionManagerRef = SpringJPAConfig.TRANSACTION_MANAGER,
        enableDefaultTransactions = true)
class SpringJPAConfig {
    companion object {
        const val DATA_SOURCE = "SpringDataSource"
        const val ENTITY_MANAGER = "springEntityManagerFactory"
        const val TRANSACTION_MANAGER = "springTransactionManager"
    }
    @Bean
    fun exceptionTranslation() = PersistenceExceptionTranslationPostProcessor()
    @Bean(name = [DATA_SOURCE])
    @ConfigurationProperties(prefix = "spring.datasource.spring-mssql")
    fun dataSource(): HikariDataSource = DataSourceBuilder.create().type(HikariDataSource::class.java).build()
    @Bean
    fun springEntityManagerFactory(): LocalContainerEntityManagerFactoryBean = LocalContainerEntityManagerFactoryBean().apply {
        this.dataSource = dataSource()
        this.setPackagesToScan("mhha.springmhha.model.sqlSpring")
        this.jpaVendorAdapter = HibernateJpaVendorAdapter()
        this.setJpaProperties(additionalProperties())
    }
    @Bean
    fun springTransactionManager(): PlatformTransactionManager = JpaTransactionManager().apply {
        this.entityManagerFactory = springEntityManagerFactory().`object`
    }
    fun additionalProperties(): Properties = Properties().apply {
        this.setProperty("hibernate.hbm2ddl.auto", "create-drop")
        this.setProperty("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect")
    }
}