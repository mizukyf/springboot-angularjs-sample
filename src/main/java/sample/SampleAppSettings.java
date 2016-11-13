package sample;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class SampleAppSettings {
	@Value("${sample.app.name}")
	private String appName;
	@Value("${sample.app.version}")
	private String appVersion;
	@Value("${sample.xsrf.protection.enabled}")
	private boolean xsrfProtectionEnabled;
	@Value("${sample.xsrf.protection.cookie.name}")
	private String xsrfProtectionCookieName;
	@Value("${sample.xsrf.protection.header.name}")
	private String xsrfProtectionHeaderName;
	@Value("${sample.access.control.enabled}")
	private boolean accessControlEnabled;
	
	public boolean isXsrfProtectionEnabled() {
		return xsrfProtectionEnabled;
	}
	public String getXsrfProtectionCookieName() {
		return xsrfProtectionCookieName;
	}
	public String getXsrfProtectionHeaderName() {
		return xsrfProtectionHeaderName;
	}
	public boolean isAccessControlEnabled() {
		return accessControlEnabled;
	}
	public String getAppName() {
		return appName;
	}
	public String getAppVersion() {
		return appVersion;
	}
}
