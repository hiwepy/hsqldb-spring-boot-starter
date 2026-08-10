package org.hsqldb.spring.boot;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Spring Boot auto-configuration for the HyperSQL (HSQLDB) embedded database.
 * <p>
 * Activates when the HSQLDB JDBC driver is on the classpath and
 * {@code hsqldb.enabled=true}. Runs before {@link DataSourceAutoConfiguration}
 * so that the embedded HyperSQL database (and optional standalone server) is
 * available by the time Spring Boot configures the primary {@code DataSource}.
 * </p>
 * <p>
 * Binds both {@link HyperSQLProperties} (the global opt-in switch) and
 * {@link HyperSQLServerProperties} (the standalone server settings). The
 * actual server lifecycle is owned by {@link HyperSQLServerAutoConfiguration}.
 * </p>
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
@Configuration
@ConditionalOnClass(org.hsqldb.jdbc.JDBCDriver.class)
@ConditionalOnProperty(prefix = HyperSQLProperties.PREFIX, value = "enabled", havingValue = "true")
@EnableConfigurationProperties({ HyperSQLProperties.class, HyperSQLServerProperties.class })
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
public class HyperSQLAutoConfiguration {


}
