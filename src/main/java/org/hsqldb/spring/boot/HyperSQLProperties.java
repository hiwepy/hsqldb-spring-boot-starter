package org.hsqldb.spring.boot;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Top-level configuration properties for the HyperSQL starter, bound to the
 * {@value #PREFIX} namespace.
 * <p>
 * Currently exposes a single opt-in flag that gates the
 * {@link HyperSQLAutoConfiguration}; finer-grained server tuning lives in
 * {@link HyperSQLServerProperties}.
 * </p>
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
@ConfigurationProperties(HyperSQLProperties.PREFIX)
public class HyperSQLProperties {

	/**
	 * Property prefix under which HyperSQL options live.
	 */
	public static final String PREFIX = "hsqldb";

	/**
	 * Master switch that enables the HyperSQL auto-configuration.
	 */
	private boolean enabled = false;

	/**
	 * Returns whether the HyperSQL auto-configuration is enabled.
	 *
	 * @return {@code true} if HyperSQL integration is active
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * Enables or disables the HyperSQL auto-configuration.
	 *
	 * @param enabled whether HyperSQL integration should be active
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}