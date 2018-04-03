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
	public enum Protocol {

		HTTP(0), HSQL(1), BER(2);

		private final int protocol;

		Protocol(int protocol) {
			this.protocol = protocol;
		}

		public int get() {
			return protocol;
		}

		public boolean equals(Protocol protocol) {
			return this.compareTo(protocol) == 0;
		}

		public boolean equals(int protocol) {
			return this.compareTo(Protocol.valueOfIgnoreCase(protocol)) == 0;
		}

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
	/** HyperSQL Server 数据库路径 */
	private String database;
	/** HyperSQL Server 数据库名称 */
	private String dbname;
	/** HyperSQL Server 数据库文件存放位置 */
	private String dbFilePath;
	/**
	 * Whether server thread is a daemon. Used before starting. The default is
	 * false.
	 */
	protected boolean daemon = false;
	/** The name of the web page served when no page is specified */
	protected String defaultPage = "index.html";
	/** HyperSQL Server 允许的最大连接数 ，默认 50 */
	protected int maxconnections = 50;
	/** HyperSQL Server 允许创建的数据库数量，默认 10 */
	protected int maxdatabases = 10;
	/**
	 * Whether this server calls System.exit() when shutdown. if true, System.exit()
	 * will not be called.
	 */
	private boolean noSystemExit = true;
	/** The server listen port. */
	private int port;
	/** HyperSQL Server 服务类型；HTTP,HSQL,BER */
	private Protocol protocol = Protocol.HTTP;
	/** HyperSQL Server 外部配置文件；如 ： classpath:hsql.properties */
	protected String props;
	/** password: 连接数据库的密码 */
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

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isAcl() {
		return acl;
	}

	public void setAcl(boolean acl) {
		this.acl = acl;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isAutoRestart() {
		return autoRestart;
	}

	public void setAutoRestart(boolean autoRestart) {
		this.autoRestart = autoRestart;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getDbname() {
		return dbname;
	}

	public void setDbname(String dbname) {
		this.dbname = dbname;
	}

	public String getDbFilePath() {
		return dbFilePath;
	}

	public void setDbFilePath(String dbFilePath) {
		this.dbFilePath = dbFilePath;
	}

	public boolean isDaemon() {
		return daemon;
	}

	public void setDaemon(boolean daemon) {
		this.daemon = daemon;
	}

	public String getDefaultPage() {
		return defaultPage;
	}

	public void setDefaultPage(String defaultPage) {
		this.defaultPage = defaultPage;
	}

	public int getMaxconnections() {
		return maxconnections;
	}

	public void setMaxconnections(int maxconnections) {
		this.maxconnections = maxconnections;
	}

	public int getMaxdatabases() {
		return maxdatabases;
	}

	public void setMaxdatabases(int maxdatabases) {
		this.maxdatabases = maxdatabases;
	}

	public boolean isNoSystemExit() {
		return noSystemExit;
	}

	public void setNoSystemExit(boolean noSystemExit) {
		this.noSystemExit = noSystemExit;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public Protocol getProtocol() {
		return protocol;
	}

	public void setProtocol(Protocol protocol) {
		this.protocol = protocol;
	}

	public String getProps() {
		return props;
	}

	public void setProps(String props) {
		this.props = props;
	}

	public boolean isRemoteOpen() {
		return remoteOpen;
	}

	public void setRemoteOpen(boolean remoteOpen) {
		this.remoteOpen = remoteOpen;
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public boolean isSilent() {
		return silent;
	}

	public void setSilent(boolean silent) {
		this.silent = silent;
	}

	public boolean isTls() {
		return tls;
	}

	public void setTls(boolean tls) {
		this.tls = tls;
	}

	public boolean isTrace() {
		return trace;
	}

	public void setTrace(boolean trace) {
		this.trace = trace;
	}

}