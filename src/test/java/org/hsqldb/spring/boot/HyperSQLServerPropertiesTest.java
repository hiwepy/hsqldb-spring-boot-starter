package org.hsqldb.spring.boot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link HyperSQLServerProperties}.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 */
class HyperSQLServerPropertiesTest {

    @Test
    void defaultValues() {
        HyperSQLServerProperties props = new HyperSQLServerProperties();
        assertThat(props.isEnabled()).isFalse();
        assertThat(props.isAcl()).isFalse();
        assertThat(props.getAddress()).isEqualTo("0.0.0.0");
        assertThat(props.isAutoRestart()).isFalse();
        assertThat(props.getDatabase()).isNull();
        assertThat(props.getDbname()).isNull();
        assertThat(props.getDbFilePath()).isNull();
        assertThat(props.isDaemon()).isFalse();
        assertThat(props.getDefaultPage()).isEqualTo("index.html");
        assertThat(props.getMaxconnections()).isEqualTo(50);
        assertThat(props.getMaxdatabases()).isEqualTo(10);
        assertThat(props.isNoSystemExit()).isTrue();
        assertThat(props.getPort()).isEqualTo(0);
        assertThat(props.getProtocol()).isEqualTo(HyperSQLServerProperties.Protocol.HTTP);
        assertThat(props.getProps()).isNull();
        assertThat(props.isRemoteOpen()).isFalse();
        assertThat(props.getRoot()).isEqualTo(".");
        assertThat(props.isSilent()).isTrue();
        assertThat(props.isTls()).isFalse();
        assertThat(props.isTrace()).isFalse();
    }

    @Test
    void prefixConstant() {
        assertThat(HyperSQLServerProperties.PREFIX).isEqualTo("hsqldb.server");
    }

    @Test
    void serverKeyConstants() {
        assertThat(HyperSQLServerProperties.SC_KEY_PREFIX).isEqualTo("server");
        assertThat(HyperSQLServerProperties.SC_KEY_ADDRESS).isEqualTo("server.address");
        assertThat(HyperSQLServerProperties.SC_KEY_AUTORESTART_SERVER).isEqualTo("server.restart_on_shutdown");
        assertThat(HyperSQLServerProperties.SC_KEY_DATABASE).isEqualTo("server.database");
        assertThat(HyperSQLServerProperties.SC_KEY_DBNAME).isEqualTo("server.dbname");
        assertThat(HyperSQLServerProperties.SC_KEY_NO_SYSTEM_EXIT).isEqualTo("server.no_system_exit");
        assertThat(HyperSQLServerProperties.SC_KEY_PORT).isEqualTo("server.port");
        assertThat(HyperSQLServerProperties.SC_KEY_HTTP_PORT).isEqualTo("server.port");
        assertThat(HyperSQLServerProperties.SC_KEY_SILENT).isEqualTo("server.silent");
        assertThat(HyperSQLServerProperties.SC_KEY_TLS).isEqualTo("server.tls");
        assertThat(HyperSQLServerProperties.SC_KEY_TRACE).isEqualTo("server.trace");
        assertThat(HyperSQLServerProperties.SC_KEY_WEB_DEFAULT_PAGE).isEqualTo("server.default_page");
        assertThat(HyperSQLServerProperties.SC_KEY_WEB_ROOT).isEqualTo("server.root");
        assertThat(HyperSQLServerProperties.SC_KEY_MAX_CONNECTIONS).isEqualTo("server.maxconnections");
        assertThat(HyperSQLServerProperties.SC_KEY_REMOTE_OPEN_DB).isEqualTo("server.remote_open");
        assertThat(HyperSQLServerProperties.SC_KEY_MAX_DATABASES).isEqualTo("server.maxdatabases");
        assertThat(HyperSQLServerProperties.SC_KEY_ACL).isEqualTo("server.acl");
        assertThat(HyperSQLServerProperties.SC_KEY_DAEMON).isEqualTo("server.daemon");
        assertThat(HyperSQLServerProperties.SC_KEY_SYSTEM).isEqualTo("system");
    }

    @Test
    void setAndGetEnabled() {
        HyperSQLServerProperties props = new HyperSQLServerProperties();
        props.setEnabled(true);
        assertThat(props.isEnabled()).isTrue();
    }

    @Test
    void setAndGetAcl() {
        HyperSQLServerProperties props = new HyperSQLServerProperties();
        props.setAcl(true);
        assertThat(props.isAcl()).isTrue();
    }

    @Test
    void setAndGetAddress() {
        HyperSQLServerProperties props = new HyperSQLServerProperties();
        props.setAddress("127.0.0.1");
        assertThat(props.getAddress()).isEqualTo("127.0.0.1");
    }

    @Test
    void setAndGetAutoRestart() {
        HyperSQLServerProperties props = new HyperSQLServerProperties();
        props.setAutoRestart(true);
        assertThat(props.isAutoRestart()).isTrue();
    }

    @Test
    void setAndGetDatabase() {
        HyperSQLServerProperties props = new HyperSQLServerProperties();
        props.setDatabase("file:/path/to/db");
        assertThat(props.getDatabase()).isEqualTo("file:/path/to/db");
    }

    @Test
    void setAndGetDbname() {
        HyperSQLServerProperties props = new HyperSQLServerProperties();
        props.setDbname("mydb");
        assertThat(props.getDbname()).isEqualTo("mydb");
    }

    @Test
    void setAndGetDbFilePath() {
        HyperSQLServerProperties props = new HyperSQLServerProperties();
        props.setDbFilePath("/var/data/hsqldb");
        assertThat(props.getDbFilePath()).isEqualTo("/var/data/hsqldb");
    }

    @Test
    void setAndGetDaemon() {
        HyperSQLServerProperties props = new HyperSQLServerProperties();
        props.setDaemon(true);
        assertThat(props.isDaemon()).isTrue();
    }

    @Test
    void setAndGetDefaultPage() {
        HyperSQLServerProperties props = new HyperSQLServerProperties();
        props.setDefaultPage("home.html");
        assertThat(props.getDefaultPage()).isEqualTo("home.html");
    }

    @Test
    void setAndGetMaxconnections() {
        HyperSQLServerProperties props = new HyperSQLServerProperties();
        props.setMaxconnections(100);
        assertThat(props.getMaxconnections()).isEqualTo(100);
    }

    @Test
    void setAndGetMaxdatabases() {
        HyperSQLServerProperties props = new HyperSQLServerProperties();
        props.setMaxdatabases(20);
        assertThat(props.getMaxdatabases()).isEqualTo(20);
    }

    @Test
    void setAndGetNoSystemExit() {
        HyperSQLServerProperties props = new HyperSQLServerProperties();
        props.setNoSystemExit(false);
        assertThat(props.isNoSystemExit()).isFalse();
    }

    @Test
    void setAndGetPort() {
        HyperSQLServerProperties props = new HyperSQLServerProperties();
        props.setPort(9001);
        assertThat(props.getPort()).isEqualTo(9001);
    }

    @Test
    void setAndGetProtocol() {
        HyperSQLServerProperties props = new HyperSQLServerProperties();
        props.setProtocol(HyperSQLServerProperties.Protocol.HSQL);
        assertThat(props.getProtocol()).isEqualTo(HyperSQLServerProperties.Protocol.HSQL);
    }

    @Test
    void setAndGetProps() {
        HyperSQLServerProperties props = new HyperSQLServerProperties();
        props.setProps("classpath:hsql.properties");
        assertThat(props.getProps()).isEqualTo("classpath:hsql.properties");
    }

    @Test
    void setAndGetRemoteOpen() {
        HyperSQLServerProperties props = new HyperSQLServerProperties();
        props.setRemoteOpen(true);
        assertThat(props.isRemoteOpen()).isTrue();
    }

    @Test
    void setAndGetRoot() {
        HyperSQLServerProperties props = new HyperSQLServerProperties();
        props.setRoot("/var/www");
        assertThat(props.getRoot()).isEqualTo("/var/www");
    }

    @Test
    void setAndGetSilent() {
        HyperSQLServerProperties props = new HyperSQLServerProperties();
        props.setSilent(false);
        assertThat(props.isSilent()).isFalse();
    }

    @Test
    void setAndGetTls() {
        HyperSQLServerProperties props = new HyperSQLServerProperties();
        props.setTls(true);
        assertThat(props.isTls()).isTrue();
    }

    @Test
    void setAndGetTrace() {
        HyperSQLServerProperties props = new HyperSQLServerProperties();
        props.setTrace(true);
        assertThat(props.isTrace()).isTrue();
    }

    // Protocol enum tests

    @Test
    void protocolEnumValues() {
        HyperSQLServerProperties.Protocol[] values = HyperSQLServerProperties.Protocol.values();
        assertThat(values).hasSize(3);
        assertThat(values).contains(
            HyperSQLServerProperties.Protocol.HTTP,
            HyperSQLServerProperties.Protocol.HSQL,
            HyperSQLServerProperties.Protocol.BER
        );
    }

    @Test
    void protocolGet() {
        assertThat(HyperSQLServerProperties.Protocol.HTTP.get()).isEqualTo(0);
        assertThat(HyperSQLServerProperties.Protocol.HSQL.get()).isEqualTo(1);
        assertThat(HyperSQLServerProperties.Protocol.BER.get()).isEqualTo(2);
    }

    @Test
    void protocolEquals() {
        HyperSQLServerProperties.Protocol protocol = HyperSQLServerProperties.Protocol.HTTP;
        assertThat(protocol.equals(HyperSQLServerProperties.Protocol.HTTP)).isTrue();
        assertThat(protocol.equals(HyperSQLServerProperties.Protocol.HSQL)).isFalse();
    }

    @Test
    void protocolEqualsInt() {
        HyperSQLServerProperties.Protocol protocol = HyperSQLServerProperties.Protocol.HTTP;
        assertThat(protocol.equals(0)).isTrue();
        assertThat(protocol.equals(1)).isFalse();
    }

    @Test
    void protocolValueOfIgnoreCase() {
        assertThat(HyperSQLServerProperties.Protocol.valueOfIgnoreCase(0))
            .isEqualTo(HyperSQLServerProperties.Protocol.HTTP);
        assertThat(HyperSQLServerProperties.Protocol.valueOfIgnoreCase(1))
            .isEqualTo(HyperSQLServerProperties.Protocol.HSQL);
        assertThat(HyperSQLServerProperties.Protocol.valueOfIgnoreCase(2))
            .isEqualTo(HyperSQLServerProperties.Protocol.BER);
    }

    @Test
    void protocolValueOfIgnoreCaseThrowsOnUnknown() {
        assertThatThrownBy(() -> HyperSQLServerProperties.Protocol.valueOfIgnoreCase(99))
            .isInstanceOf(NoSuchElementException.class)
            .hasMessageContaining("Cannot found transport with key '99'");
    }
}
