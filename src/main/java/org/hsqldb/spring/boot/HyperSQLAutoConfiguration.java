package org.hsqldb.spring.boot;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 
 */
@Configuration
@ConditionalOnClass(org.hsqldb.jdbc.JDBCDriver.class)
@ConditionalOnProperty(prefix = HyperSQLProperties.PREFIX, value = "enabled", havingValue = "true")
@EnableConfigurationProperties({ HyperSQLProperties.class, HyperSQLServerProperties.class })
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
public class HyperSQLAutoConfiguration {

	
}
