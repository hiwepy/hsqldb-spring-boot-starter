package org.hsqldb.spring.boot;

import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;

import org.hsqldb.persist.HsqlProperties;
import org.hsqldb.resources.ResourceBundleHandler;
import org.hsqldb.server.HsqlServerFactory;
import org.hsqldb.server.Server;
import org.hsqldb.server.ServerConfiguration;
import org.hsqldb.server.WebServer;
import org.hsqldb.spring.boot.HyperSQLServerProperties.Protocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StringUtils;

/**
 * Spring Boot auto-configuration that starts a standalone HyperSQL server
 * process (HTTP, HSQL or Berkeley protocol) alongside the application.
 * <p>
 * Activates when the HSQLDB JDBC driver is present and
 * {@code hsqldb.enabled=true}, running before
 * {@link DataSourceAutoConfiguration} so client data sources can target the
 * newly started server. The server is fully driven by
 * {@link HyperSQLServerProperties}; when an external {@code .properties} file
 * is referenced via {@code hsqldb.server.props} it takes precedence, otherwise
 * every option is derived from the bound properties.
 * </p>
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 1.0.0
 */
@Configuration
@ConditionalOnClass(org.hsqldb.jdbc.JDBCDriver.class)
@ConditionalOnProperty(prefix = HyperSQLProperties.PREFIX, value = "enabled", havingValue = "true")
@EnableConfigurationProperties({ HyperSQLProperties.class, HyperSQLServerProperties.class })
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
public class HyperSQLServerAutoConfiguration implements ResourceLoaderAware {

	private Logger logger = LoggerFactory.getLogger(getClass());
	private ResourceLoader resourceLoader;
	//
	protected static final int serverBundleHandle = ResourceBundleHandler
			.getBundleHandle("org_hsqldb_server_Server_messages", null);

	/**
	 * Provides the {@link PrintWriter} used by the HyperSQL server to emit log
	 * messages, defaulting to {@code System.out}.
	 *
	 * @return a print writer wrapping standard output
	 */
	@Bean
	@ConditionalOnMissingBean
	public PrintWriter logWriter() {
		return new PrintWriter(System.out);
	}

	/**
	 * Provides the {@link PrintWriter} used by the HyperSQL server to emit
	 * error messages, defaulting to {@code System.err}.
	 *
	 * @return a print writer wrapping standard error
	 */
	@Bean
	@ConditionalOnMissingBean
	public PrintWriter errWriter() {
		return new PrintWriter(System.err);
	}

	/**
	 * Creates, configures and starts the HyperSQL {@link Server} bean.
	 * <p>
	 * For the Berkeley ({@code BER}) protocol a lightweight in-process server
	 * is created via {@link HsqlServerFactory}. For HTTP/HSQL protocols the
	 * server properties are built either from an external properties file
	 * (when {@code hsqldb.server.props} points to one) or from the bound
	 * {@link HyperSQLServerProperties}, after which a {@link WebServer} is
	 * configured and started.
	 * </p>
	 *
	 * @param properties  the bound server configuration
	 * @param logWriter   destination for server log output
	 * @param errWriter   destination for server error output
	 * @return the started HyperSQL server, or {@code null} if the resolved
	 *         properties contained errors
	 * @throws Exception if the server cannot be configured or started
	 */
	@Bean
	public Server hyperSQLServer(HyperSQLServerProperties properties, PrintWriter logWriter, PrintWriter errWriter)
			throws Exception {

		if (Protocol.BER.equals(properties.getProtocol())) {
			return (Server) HsqlServerFactory.createHsqlServer(properties.getDbFilePath(), properties.isTrace(),
					properties.isSilent());
		}

		Properties fileProps = null;
		if (StringUtils.hasText(properties.getProps())) {
			Resource resource = resourceLoader.getResource(properties.getProps());
			if (resource.exists()) {
				fileProps = new Properties();
				InputStream input = resource.getInputStream();
				fileProps.load(input);
			}
		}

		HsqlProperties props = null;
		if(fileProps == null) {

			props = ServerConfiguration.newDefaultProperties(properties.getProtocol().get());
			props.setProperty(HyperSQLServerProperties.SC_KEY_ADDRESS, properties.getAddress());
			props.setProperty(HyperSQLServerProperties.SC_KEY_AUTORESTART_SERVER, properties.isAutoRestart());
			props.setProperty(HyperSQLServerProperties.SC_KEY_DATABASE, properties.getDatabase());
			props.setProperty(HyperSQLServerProperties.SC_KEY_DBNAME, properties.getDbname());
			props.setProperty(HyperSQLServerProperties.SC_KEY_NO_SYSTEM_EXIT, properties.isNoSystemExit());
			props.setProperty(HyperSQLServerProperties.SC_KEY_PORT, properties.getPort());
			props.setProperty(HyperSQLServerProperties.SC_KEY_HTTP_PORT, properties.getPort());
			props.setProperty(HyperSQLServerProperties.SC_KEY_SILENT, properties.isSilent());
			props.setProperty(HyperSQLServerProperties.SC_KEY_TLS, properties.isTls());
			props.setProperty(HyperSQLServerProperties.SC_KEY_TRACE, properties.isTrace());
			props.setProperty(HyperSQLServerProperties.SC_KEY_WEB_DEFAULT_PAGE, properties.getDefaultPage());
			props.setProperty(HyperSQLServerProperties.SC_KEY_WEB_ROOT, properties.getRoot());
			props.setProperty(HyperSQLServerProperties.SC_KEY_MAX_CONNECTIONS, properties.getMaxconnections());
			props.setProperty(HyperSQLServerProperties.SC_KEY_REMOTE_OPEN_DB, properties.isRemoteOpen());
			props.setProperty(HyperSQLServerProperties.SC_KEY_MAX_DATABASES, properties.getMaxdatabases());
			props.setProperty(HyperSQLServerProperties.SC_KEY_ACL, properties.isAcl());
			props.setProperty(HyperSQLServerProperties.SC_KEY_DAEMON, properties.isDaemon());

		} else {
			props = new HsqlProperties(fileProps);
		}

		props.setProperty(HyperSQLServerProperties.SC_KEY_MAX_CONNECTIONS, properties.getMaxconnections());
		props.setProperty(HyperSQLServerProperties.SC_KEY_MAX_DATABASES, properties.getMaxdatabases());

		String[] errors = props.getErrorKeys();

		if (errors.length != 0) {
			System.out.println("no value for argument:" + errors[0]);
			logger.warn(ResourceBundleHandler.getString(serverBundleHandle, "webserver.help"));
			return null;
		}

		ServerConfiguration.translateDefaultDatabaseProperty(props);

		// Standard behaviour when started from the command line
		// is to halt the VM when the server shuts down. This may, of
		// course, be overridden by whatever, if any, security policy is in place.
		ServerConfiguration.translateDefaultNoSystemExitProperty(props);
		ServerConfiguration.translateAddressProperty(props);

		// finished setting up properties;
		WebServer server = new WebServer();

		try {
			server.setProperties(props);
			server.setLogWriter(logWriter);
			server.setErrWriter(errWriter);
		} catch (Exception e) {
			logger.error(String.format("[%s]: [%s]: %s", server.getServerId(), Thread.currentThread(),
					"Failed to set properties"));
			throw e;
		}

		// now messages go to the channel specified in properties
		logger.debug("[" + server.getServerId() + "]: " + "Startup sequence initiated from main() method.");

		if (fileProps != null) {
			logger.debug("[" + server.getServerId() + "]: " + "Loaded properties from [" + properties.getProps()
					+ ".properties]");
		} else {
			logger.debug("[" + server.getServerId() + "]: " + "Could not load properties from file");
			logger.debug("[" + server.getServerId() + "]: " + "Using application.properties/application.yml only");
		}

		server.start();

		server.setRestartOnShutdown(properties.isAutoRestart());

		return server;
	}

	/**
	 * Stores the {@link ResourceLoader} injected by Spring so external property
	 * files (e.g. {@code classpath:hsql.properties}) can be resolved when the
	 * server starts.
	 *
	 * @param resourceLoader the resource loader provided by the application context
	 */
	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

}
