package org.hsqldb.spring.boot;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link HyperSQLProperties}.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 */
class HyperSQLPropertiesTest {

    @Test
    void defaultValues() {
        HyperSQLProperties props = new HyperSQLProperties();
        assertThat(props.isEnabled()).isFalse();
    }

    @Test
    void prefixConstant() {
        assertThat(HyperSQLProperties.PREFIX).isEqualTo("hsqldb");
    }

    @Test
    void setAndGetEnabled() {
        HyperSQLProperties props = new HyperSQLProperties();
        props.setEnabled(true);
        assertThat(props.isEnabled()).isTrue();
    }

    @Test
    void setEnabledFalse() {
        HyperSQLProperties props = new HyperSQLProperties();
        props.setEnabled(true);
        props.setEnabled(false);
        assertThat(props.isEnabled()).isFalse();
    }
}
