package com.snowbuffer.study.java.spring.annotation.tx;

import com.snowbuffer.study.java.spring.annotation.tx.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-22 19:58
 */
@Configuration
@EnableTransactionManagement
public class TxConfig {

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(H2)
                .setScriptEncoding("UTF-8")
                .ignoreFailedDrops(true)
                .addScript("com/snowbuffer/study/java/spring/annotation/tx/schema.sql")
//                .addScripts("user_data.sql", "country_data.sql")
                .build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public TransactionManagementConfigurer transactionManagementConfigurer111() {
        return () -> new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public UserService userService() {
        return new UserService();
    }
}
