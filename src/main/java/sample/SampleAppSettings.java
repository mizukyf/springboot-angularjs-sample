package sample;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * アプリケーションの設定情報を保持するオブジェクト.
 * <p>プロパティ・ファイルで定義された設定情報をインスタンス・フィールドに保持する。</p>
 */
@Configuration
@PropertySource("classpath:application.properties")
public class SampleAppSettings {
	@Value("${sample.app.name:Sample.app}")
	private String appName;
	@Value("${sample.app.version:1.0.0-BETA}")
	private String appVersion;
	@Value("${sample.xsrf.protection.enabled:true}")
	private boolean xsrfProtectionEnabled;
	@Value("${sample.xsrf.protection.cookie.name:XSRF-TOKEN}")
	private String xsrfProtectionCookieName;
	@Value("${sample.xsrf.protection.header.name:X-XSRF-TOKEN}")
	private String xsrfProtectionHeaderName;
	@Value("${sample.access.control.enabled:true}")
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
