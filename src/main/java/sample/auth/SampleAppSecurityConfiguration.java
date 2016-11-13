package sample.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.filter.OncePerRequestFilter;

import sample.SampleAppSettings;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled=true)
public class SampleAppSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private SampleAppSettings settings;
	@Autowired
	private SampleAppUserDetailsService userDetailsService;
	
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
    	// アクセス制御「有効」と設定されているかどうかを判定
    	if (settings.isAccessControlEnabled()) {
    		// 「有効」と設定されている場合はURLパターンに基づく設定を行う
	        http.authorizeRequests()
        	.antMatchers("/api/users/**", "/api/roles/**").hasAnyAuthority(SampleAppGrantedAuthority.NAME_ADMINISTRATOR)
        	.antMatchers("/api/**").hasAnyAuthority(SampleAppGrantedAuthority.NAME_OPERATORY)
            .and()
            .exceptionHandling()
            // 認証が必要なパスに未認証状態でアクセスされた場合に利用するエントリーポイントの設定
            .authenticationEntryPoint(new SampleAppAuthenticationEntryPoint());
	        
	        if (settings.isXsrfProtectionEnabled()) {
	            // CSRF（XSRF）対策をON（デフォルトでON）、トークン・リポジトリを設定
	            // ＊この実装はトークン情報はセッションに保存されておりサーバ冗長構成やサーバ再起動に耐えられない
	        	http.csrf().csrfTokenRepository(csrfTokenRepository());
	        	// HTTPレスポンスにCSRFトークンをCookieに保存するべき旨のレコードを追加するフィルターの設定
	        	http.addFilterAfter(csrfTokenFilter(), CsrfFilter.class);
	        } else {
	            // CSRF（XSRF）対策をOFF（デフォルトでON）
	        	http.csrf().disable();
	        }
    	}else {
    		// 「有効」と設定されていない場合はCSRF対策をOFF、加えてすべてのURLに匿名アクセスを有効にする
    		http.csrf().disable().authorizeRequests().anyRequest().permitAll();
    	}
    }
    
    @Autowired
    public void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(userDetailsService)
            .passwordEncoder(userDetailsService.passwordEncorder());
    }
    
    private OncePerRequestFilter csrfTokenFilter() {
    	return new SampleAppCsrfHeaderFilter(settings.getXsrfProtectionCookieName());
    }
    
    private CsrfTokenRepository csrfTokenRepository() {
    	final HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
    	repository.setHeaderName(settings.getXsrfProtectionHeaderName());
    	return repository;
    }
}
