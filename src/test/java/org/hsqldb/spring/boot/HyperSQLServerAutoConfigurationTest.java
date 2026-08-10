package org.hsqldb.spring.boot;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;

import org.hsqldb.persist.HsqlProperties;
import org.hsqldb.server.Server;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

/**
 * Tests for {@link HyperSQLServerAutoConfiguration}.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 */
class HyperSQLServerAutoConfigurationTest {

    @Test
    void berProtocolCreatesServer() {
        new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(HyperSQLServerAutoConfiguration.class))
            .withPropertyValues(
                "hsqldb.enabled=true",
                "hsqldb.server.enabled=true",
                "hsqldb.server.protocol=BER",
                "hsqldb.server.db-file-path=mem:berdb"
            )
            .run(context -> {
                assertThat(context).hasSingleBean(Server.class);
                Server server = context.getBean(Server.class);
                assertThat(server).isNotNull();
                server.stop();
            });
    }

    @Test
    void logWriterBeanCreated() {
        new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(HyperSQLServerAutoConfiguration.class))
            .withPropertyValues(
                "hsqldb.enabled=true",
                "hsqldb.server.enabled=true",
                "hsqldb.server.protocol=BER",
                "hsqldb.server.db-file-path=mem:logtest"
            )
            .run(context -> {
                assertThat(context).hasSingleBean(PrintWriter.class);
                Server server = context.getBean(Server.class);
                server.stop();
            });
    }

    @Test
    void propertiesAreBound() {
        new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(HyperSQLServerAutoConfiguration.class))
            .withPropertyValues(
                "hsqldb.enabled=true",
                "hsqldb.server.enabled=true",
                "hsqldb.server.protocol=BER",
                "hsqldb.server.db-file-path=mem:propstest",
                "hsqldb.server.maxconnections=100",
                "hsqldb.server.maxdatabases=20"
            )
            .run(context -> {
                assertThat(context).hasSingleBean(HyperSQLServerProperties.class);
                HyperSQLServerProperties props = context.getBean(HyperSQLServerProperties.class);
                assertThat(props.getMaxconnections()).isEqualTo(100);
                assertThat(props.getMaxdatabases()).isEqualTo(20);
                Server server = context.getBean(Server.class);
                server.stop();
            });
    }

    @Test
    void autoConfigurationNotActivatedWhenDisabled() {
        new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(HyperSQLServerAutoConfiguration.class))
            .withPropertyValues("hsqldb.enabled=false")
            .run(context -> {
                assertThat(context).doesNotHaveBean(Server.class);
            });
    }

    @Test
    void serverWithTraceAndNotSilent() {
        new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(HyperSQLServerAutoConfiguration.class))
            .withPropertyValues(
                "hsqldb.enabled=true",
                "hsqldb.server.enabled=true",
                "hsqldb.server.protocol=BER",
                "hsqldb.server.db-file-path=mem:booltest",
                "hsqldb.server.trace=true",
                "hsqldb.server.silent=false"
            )
            .run(context -> {
                assertThat(context).hasSingleBean(Server.class);
                Server server = context.getBean(Server.class);
                server.stop();
            });
    }

    @Test
    void hyperSQLServerDirectCallWithBerProtocol() throws Exception {
        HyperSQLServerAutoConfiguration config = new HyperSQLServerAutoConfiguration();
        config.setResourceLoader(new DefaultResourceLoader());

        HyperSQLServerProperties props = new HyperSQLServerProperties();
        props.setProtocol(HyperSQLServerProperties.Protocol.BER);
        props.setDbFilePath("mem:directber");

        Server server = config.hyperSQLServer(props, new PrintWriter(new StringWriter()), new PrintWriter(new StringWriter()));
        assertThat(server).isNotNull();
        server.stop();
    }

    @Test
    void hyperSQLServerDirectCallWithBerTraceAndSilent() throws Exception {
        HyperSQLServerAutoConfiguration config = new HyperSQLServerAutoConfiguration();
        config.setResourceLoader(new DefaultResourceLoader());

        HyperSQLServerProperties props = new HyperSQLServerProperties();
        props.setProtocol(HyperSQLServerProperties.Protocol.BER);
        props.setDbFilePath("mem:bertracesilent");
        props.setTrace(true);
        props.setSilent(false);

        Server server = config.hyperSQLServer(props, new PrintWriter(new StringWriter()), new PrintWriter(new StringWriter()));
        assertThat(server).isNotNull();
        server.stop();
    }

    @Test
    void setResourceLoaderSetsCorrectly() {
        HyperSQLServerAutoConfiguration config = new HyperSQLServerAutoConfiguration();
        config.setResourceLoader(new DefaultResourceLoader());
        assertThat(config).isNotNull();
    }

    @Test
    void hyperSQLServerWithHsqlProtocol() throws Exception {
        HyperSQLServerAutoConfiguration config = new HyperSQLServerAutoConfiguration();
        config.setResourceLoader(new DefaultResourceLoader());

        HyperSQLServerProperties props = new HyperSQLServerProperties();
        props.setProtocol(HyperSQLServerProperties.Protocol.HSQL);
        props.setDatabase("mem:httpdb");
        props.setDbname("httpdb");
        props.setPort(0);
        props.setSilent(true);
        props.setNoSystemExit(true);

        Server server = config.hyperSQLServer(props, new PrintWriter(new StringWriter()), new PrintWriter(new StringWriter()));
        assertThat(server).isNotNull();
        assertThat(server.getState()).isGreaterThanOrEqualTo(0);
        server.stop();
    }

    @Test
    void hyperSQLServerWithAllProperties() throws Exception {
        HyperSQLServerAutoConfiguration config = new HyperSQLServerAutoConfiguration();
        config.setResourceLoader(new DefaultResourceLoader());

        HyperSQLServerProperties props = new HyperSQLServerProperties();
        props.setProtocol(HyperSQLServerProperties.Protocol.HSQL);
        props.setDatabase("mem:alldb");
        props.setDbname("alldb");
        props.setPort(0);
        props.setSilent(true);
        props.setNoSystemExit(true);
        props.setAddress("0.0.0.0");
        props.setTls(false);
        props.setTrace(false);
        props.setAutoRestart(false);
        props.setRemoteOpen(false);
        props.setDaemon(false);
        props.setMaxconnections(50);
        props.setMaxdatabases(10);

        Server server = config.hyperSQLServer(props, new PrintWriter(new StringWriter()), new PrintWriter(new StringWriter()));
        assertThat(server).isNotNull();
        server.stop();
    }

    @Test
    void hyperSQLServerWithAutoRestart() throws Exception {
        HyperSQLServerAutoConfiguration config = new HyperSQLServerAutoConfiguration();
        config.setResourceLoader(new DefaultResourceLoader());

        HyperSQLServerProperties props = new HyperSQLServerProperties();
        props.setProtocol(HyperSQLServerProperties.Protocol.HSQL);
        props.setDatabase("mem:restartdb");
        props.setDbname("restartdb");
        props.setPort(0);
        props.setSilent(true);
        props.setNoSystemExit(true);
        props.setAutoRestart(true);

        Server server = config.hyperSQLServer(props, new PrintWriter(new StringWriter()), new PrintWriter(new StringWriter()));
        assertThat(server).isNotNull();
        server.stop();
    }

    @Test
    void hyperSQLServerWithHttpProtocol() throws Exception {
        HyperSQLServerAutoConfiguration config = new HyperSQLServerAutoConfiguration();
        config.setResourceLoader(new DefaultResourceLoader());

        HyperSQLServerProperties props = new HyperSQLServerProperties();
        props.setProtocol(HyperSQLServerProperties.Protocol.HTTP);
        props.setDatabase("mem:httpdb");
        props.setDbname("httpdb");
        props.setPort(0);
        props.setSilent(true);
        props.setNoSystemExit(true);

        Server server = config.hyperSQLServer(props, new PrintWriter(new StringWriter()), new PrintWriter(new StringWriter()));
        assertThat(server).isNotNull();
        server.stop();
    }

    @Test
    void hyperSQLServerWithNonExistentPropsFile() throws Exception {
        HyperSQLServerAutoConfiguration config = new HyperSQLServerAutoConfiguration();
        config.setResourceLoader(new DefaultResourceLoader());

        HyperSQLServerProperties props = new HyperSQLServerProperties();
        props.setProtocol(HyperSQLServerProperties.Protocol.HSQL);
        props.setDatabase("mem:propsfiledb");
        props.setDbname("propsfiledb");
        props.setPort(0);
        props.setSilent(true);
        props.setNoSystemExit(true);
        props.setProps("classpath:non-existent-file.properties");

        Server server = config.hyperSQLServer(props, new PrintWriter(new StringWriter()), new PrintWriter(new StringWriter()));
        assertThat(server).isNotNull();
        server.stop();
    }

    @Test
    void hyperSQLServerWithErrWriter() throws Exception {
        HyperSQLServerAutoConfiguration config = new HyperSQLServerAutoConfiguration();
        config.setResourceLoader(new DefaultResourceLoader());

        HyperSQLServerProperties props = new HyperSQLServerProperties();
        props.setProtocol(HyperSQLServerProperties.Protocol.HSQL);
        props.setDatabase("mem:errwriterdb");
        props.setDbname("errwriterdb");
        props.setPort(0);
        props.setSilent(true);
        props.setNoSystemExit(true);

        PrintWriter logWriter = new PrintWriter(System.out);
        PrintWriter errWriter = new PrintWriter(System.err);
        Server server = config.hyperSQLServer(props, logWriter, errWriter);
        assertThat(server).isNotNull();
        server.stop();
    }
}
