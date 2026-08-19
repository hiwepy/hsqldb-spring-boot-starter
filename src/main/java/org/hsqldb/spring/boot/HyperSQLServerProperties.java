package org.hsqldb.spring.boot;

import java.util.NoSuchElementException;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(HyperSQLServerProperties.PREFIX)
public class HyperSQLServerProperties {

	public static final String PREFIX = "hsqldb.server";

	// keys to properties
	public static final String SC_KEY_PREFIX = "server";
	public static final String SC_KEY_ADDRESS = "server.address";
	public static final String SC_KEY_AUTORESTART_SERVER = "server.restart_on_shutdown";
	public static final String SC_KEY_DATABASE = "server.database";
	public static final String SC_KEY_DBNAME = "server.dbname";
	public static final String SC_KEY_NO_SYSTEM_EXIT = "server.no_system_exit";
	public static final String SC_KEY_PORT = "server.port";
	public static final String SC_KEY_HTTP_PORT = "server.port";
	public static final String SC_KEY_SILENT = "server.silent";
	public static final String SC_KEY_TLS = "server.tls";
	public static final String SC_KEY_TRACE = "server.trace";
	public static final String SC_KEY_WEB_DEFAULT_PAGE = "server.default_page";
	public static final String SC_KEY_WEB_ROOT = "server.root";
	public static final String SC_KEY_MAX_CONNECTIONS = "server.maxconnections";
	public static final String SC_KEY_REMOTE_OPEN_DB = "server.remote_open";
	public static final String SC_KEY_MAX_DATABASES = "server.maxdatabases";
	public static final String SC_KEY_ACL = "server.acl";
	public static final String SC_KEY_DAEMON = "server.daemon";
	public static final String SC_KEY_SYSTEM = "system";

	// type of server
	/**
	 * <p>Enumeration of protocol values.</p>
	 *
	 * @author <a href="https://github.com/loong10k">Loong Wan</a>
	 * @since 1.0.0
	 */
	public enum Protocol {

		/** HTTP transport, allowing browser/WebSocket style access. */
		HTTP(0),
		/** Native HSQL wire protocol. */
		HSQL(1),
		/** Berkeley DB in-process protocol. */
		BER(2);

		private final int protocol;

		Protocol(int protocol) {
			this.protocol = protocol;
		}

		/**
		 * Returns the numeric identifier used by HSQLDB for this protocol.
		 *
		 * @return the underlying HSQLDB protocol code
		 */
		public int get() {
			return protocol;
		}

		/**
		 * Compares this protocol with another {@link Protocol} instance.
		 *
		 * @param protocol the protocol to compare against
		 * @return {@code true} if both protocols are equal
		 */
		public boolean equals(Protocol protocol) {
			return this.compareTo(protocol) == 0;
		}

		/**
		 * Compares this protocol with a numeric protocol code.
		 *
		 * @param protocol the numeric protocol code to compare against
		 * @return {@code true} if the codes refer to the same protocol
		 */
		public boolean equals(int protocol) {
			return this.compareTo(Protocol.valueOfIgnoreCase(protocol)) == 0;
		}

		/**
		 * Resolves a protocol by its numeric code, ignoring case.
		 *
		 * @param key the numeric protocol code
		 * @return the matching {@link Protocol}
		 * @throws NoSuchElementException if no protocol matches the given key
		 */
		public static Protocol valueOfIgnoreCase(int key) {
			for (Protocol protocol : Protocol.values()) {
				if (protocol.get() == key) {
					return protocol;
				}
			}
			throw new NoSuchElementException("Cannot found transport with key '" + key + "'.");
		}

	}

	/**
	 * Enable HyperSQL Server.
	 */
	private boolean enabled = false;
	/** Enable HyperSQL Server Acl */
	protected boolean acl;
	/** Path to the ACL (Access Control List) file for HyperSQL Server. */
	private String aclFilePath;
	/**
	 * A string representing the desired InetAddress as would be retrieved by
	 * InetAddres.getByName(), or a null or empty string or "0.0.0.0" to signify
	 * that the server socket should be constructed using the signature that does
	 * not specify the InetAddress.
	 */
	private String address = "0.0.0.0";
	/**
	 * Whether this server restarts on shutdown. if true, this server restarts on
	 * shutdown
	 */
	protected boolean autoRestart = false;
	/** HyperSQL Server database path. */
	private String database;
	/** HyperSQL Server database name. */
	private String dbname;
	/** Location where HyperSQL Server database files are stored. */
	private String dbFilePath;
	/**
	 * Whether server thread is a daemon. Used before starting. The default is
	 * false.
	 */
	protected boolean daemon = false;
	/** The name of the web page served when no page is specified */
	protected String defaultPage = "index.html";
	/** Maximum number of connections allowed by HyperSQL Server, default 50. */
	protected int maxconnections = 50;
	/** Maximum number of databases HyperSQL Server may create, default 10. */
	protected int maxdatabases = 10;
	/**
	 * Whether this server calls System.exit() when shutdown. if true, System.exit()
	 * will not be called.
	 */
	private boolean noSystemExit = true;
	/** The server listen port. */
	private int port;
	/** HyperSQL Server transport protocol: HTTP, HSQL or BER. */
	private Protocol protocol = Protocol.HTTP;
	/** External configuration file for HyperSQL Server, e.g. {@code classpath:hsql.properties}. */
	protected String props;
	/** Whether remote opening of databases is allowed. */
	protected boolean remoteOpen = false;
	/** The path of the root directory from which web content is served. */
	protected String root = ".";
	/**
	 * The silent mode operation. if true, then silent mode, else trace messages are
	 * to be printed.
	 */
	protected boolean silent = true;
	/** Whether to use secure sockets. */
	protected boolean tls = false;
	/**
	 * Whether trace messages go to System.out or the DriverManger
	 * PrintStream/PrintWriter, if any.
	 */
	protected boolean trace = false;
	/**
	 * <p>Is enabled.</p>
	 * @return the boolean
	 */

	public boolean isEnabled() {
		return enabled;
	}
	/** Sets the enabled. */

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	/**
	 * <p>Is acl.</p>
	 * @return the boolean
	 */

	public boolean isAcl() {
		return acl;
	}
	/** Sets the acl. */

	public void setAcl(boolean acl) {
		this.acl = acl;
	}
	/** Gets the acl file path. */

	public String getAclFilePath() {
		return aclFilePath;
	}
	/** Sets the acl file path. */

	public void setAclFilePath(String aclFilePath) {
		this.aclFilePath = aclFilePath;
	}
	/** Gets the address. */

	public String getAddress() {
		return address;
	}
	/** Sets the address. */

	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * <p>Is auto restart.</p>
	 * @return the boolean
	 */

	public boolean isAutoRestart() {
		return autoRestart;
	}
	/** Sets the auto restart. */

	public void setAutoRestart(boolean autoRestart) {
		this.autoRestart = autoRestart;
	}
	/** Gets the database. */

	public String getDatabase() {
		return database;
	}
	/** Sets the database. */

	public void setDatabase(String database) {
		this.database = database;
	}
	/** Gets the dbname. */

	public String getDbname() {
		return dbname;
	}
	/** Sets the dbname. */

	public void setDbname(String dbname) {
		this.dbname = dbname;
	}
	/** Gets the db file path. */

	public String getDbFilePath() {
		return dbFilePath;
	}
	/** Sets the db file path. */

	public void setDbFilePath(String dbFilePath) {
		this.dbFilePath = dbFilePath;
	}
	/**
	 * <p>Is daemon.</p>
	 * @return the boolean
	 */

	public boolean isDaemon() {
		return daemon;
	}
	/** Sets the daemon. */

	public void setDaemon(boolean daemon) {
		this.daemon = daemon;
	}
	/** Gets the default page. */

	public String getDefaultPage() {
		return defaultPage;
	}
	/** Sets the default page. */

	public void setDefaultPage(String defaultPage) {
		this.defaultPage = defaultPage;
	}
	/** Gets the maxconnections. */

	public int getMaxconnections() {
		return maxconnections;
	}
	/** Sets the maxconnections. */

	public void setMaxconnections(int maxconnections) {
		this.maxconnections = maxconnections;
	}
	/** Gets the maxdatabases. */

	public int getMaxdatabases() {
		return maxdatabases;
	}
	/** Sets the maxdatabases. */

	public void setMaxdatabases(int maxdatabases) {
		this.maxdatabases = maxdatabases;
	}
	/**
	 * <p>Is no system exit.</p>
	 * @return the boolean
	 */

	public boolean isNoSystemExit() {
		return noSystemExit;
	}
	/** Sets the no system exit. */

	public void setNoSystemExit(boolean noSystemExit) {
		this.noSystemExit = noSystemExit;
	}
	/** Gets the port. */

	public int getPort() {
		return port;
	}
	/** Sets the port. */

	public void setPort(int port) {
		this.port = port;
	}
	/** Gets the protocol. */

	public Protocol getProtocol() {
		return protocol;
	}
	/** Sets the protocol. */

	public void setProtocol(Protocol protocol) {
		this.protocol = protocol;
	}
	/** Gets the props. */

	public String getProps() {
		return props;
	}
	/** Sets the props. */

	public void setProps(String props) {
		this.props = props;
	}
	/**
	 * <p>Is remote open.</p>
	 * @return the boolean
	 */

	public boolean isRemoteOpen() {
		return remoteOpen;
	}
	/** Sets the remote open. */

	public void setRemoteOpen(boolean remoteOpen) {
		this.remoteOpen = remoteOpen;
	}
	/** Gets the root. */

	public String getRoot() {
		return root;
	}
	/** Sets the root. */

	public void setRoot(String root) {
		this.root = root;
	}
	/**
	 * <p>Is silent.</p>
	 * @return the boolean
	 */

	public boolean isSilent() {
		return silent;
	}
	/** Sets the silent. */

	public void setSilent(boolean silent) {
		this.silent = silent;
	}
	/**
	 * <p>Is tls.</p>
	 * @return the boolean
	 */

	public boolean isTls() {
		return tls;
	}
	/** Sets the tls. */

	public void setTls(boolean tls) {
		this.tls = tls;
	}
	/**
	 * <p>Is trace.</p>
	 * @return the boolean
	 */

	public boolean isTrace() {
		return trace;
	}
	/** Sets the trace. */

	public void setTrace(boolean trace) {
		this.trace = trace;
	}

}