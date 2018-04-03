package org.hsqldb.spring.boot;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(HyperSQLProperties.PREFIX)
public class HyperSQLProperties {

	public static final String PREFIX = "hsqldb";

	/**
	 * Enable HyperSQL.
	 */
	private boolean enabled = false;

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}