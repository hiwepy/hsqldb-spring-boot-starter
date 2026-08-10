package org.hsqldb.spring.boot;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

/**
 * Tests for {@link HyperSQLAutoConfiguration}.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 */
class HyperSQLAutoConfigurationTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
        .withConfiguration(AutoConfigurations.of(HyperSQLAutoConfiguration.class))
        .withPropertyValues("hsqldb.enabled=true");

    @Test
    void propertiesAreBoundWhenEnabled() {
        this.contextRunner.run(context -> {
            assertThat(context).hasSingleBean(HyperSQLProperties.class);
            assertThat(context.getBean(HyperSQLProperties.class).isEnabled()).isTrue();
            assertThat(context).hasSingleBean(HyperSQLServerProperties.class);
        });
    }

    @Test
    void autoConfigurationNotActivatedWhenDisabled() {
        new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(HyperSQLAutoConfiguration.class))
            .withPropertyValues("hsqldb.enabled=false")
            .run(context -> {
                assertThat(context).doesNotHaveBean(HyperSQLProperties.class);
            });
    }

    @Test
    void autoConfigurationNotActivatedByDefault() {
        new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(HyperSQLAutoConfiguration.class))
            .run(context -> {
                assertThat(context).doesNotHaveBean(HyperSQLProperties.class);
            });
    }
}
