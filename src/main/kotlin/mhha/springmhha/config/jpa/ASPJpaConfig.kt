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
import java.util.Properties

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = ["mhha.springmhha.repository.asp"],
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager",
        enableDefaultTransactions = true)
class ASPJpaConfig {
    companion object {
        const val DATA_SOURCE = "ASPDataSource"
    }
    @Bean(name = [DATA_SOURCE])
    @ConfigurationProperties(prefix = "spring.datasource.asp-mssql")
    fun dataSource(): HikariDataSource = DataSourceBuilder.create().type(HikariDataSource::class.java).build()
    @Bean
    fun entityManagerFactory(): LocalContainerEntityManagerFactoryBean = LocalContainerEntityManagerFactoryBean().apply {
        this.dataSource = dataSource()
        this.setPackagesToScan("mhha.springmhha.model.asp")
        this.jpaVendorAdapter = HibernateJpaVendorAdapter()
        this.setJpaProperties(additionalProperties())
    }
    @Bean
    fun transactionManager(): PlatformTransactionManager = JpaTransactionManager().apply {
        this.entityManagerFactory = entityManagerFactory().`object`
    }
    @Bean
    fun exceptionTranslation() = PersistenceExceptionTranslationPostProcessor()
    fun additionalProperties(): Properties = Properties().apply {
        this.setProperty("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect")
    }
}