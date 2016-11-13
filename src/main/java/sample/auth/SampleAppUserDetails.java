package sample.auth;

import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * ユーザ認証・認可プロセスで利用されるユーザ情報オブジェクト.
 * <p>このオブジェクトは{@link UserDetailsService}インターフェースを通じて
 * Spring Securityフレームワークの認証・認可プロセスに提供される。
 * {@link WebSecurityConfigurerAdapter}インターフェースでは、
 * このオブジェクトの属性情報をもとにしたアクセス制御を行っている。</p>
 * <p>コントローラ（{@link Controller}もしくは{@link RestController}アノテーションが付与されたクラス）では、
 * そのハンドラー・メソッドにおいて引数として{@link Principal}インターフェースのインスタンスを受け取ることができるが、
 * Spring Securityフレームワークではこのインスタンスは{@link Authentication}インターフェースのインスタンスでもある。
 * 認証プロセスが済んでいる段階で{@link Authentication#getPrincipal()}メソッドを呼び出すと、
 * {@link UserDetails}インターフェースのインスタンス──すなわちこの{@link SampleAppUserDetails}の
 * インスタンスが手に入る。</p>
 */
public class SampleAppUserDetails extends org.springframework.security.core.userdetails.User {
	private static final long serialVersionUID = 2213449577870703888L;
	
	private static List<GrantedAuthority> emptyAuthorities() {
		return Collections.emptyList();
	}
	
	public static SampleAppUserDetails of(final Principal principal) {
		return (SampleAppUserDetails) ((Authentication) principal).getPrincipal();
	}
	
	public SampleAppUserDetails() {
        super("INVALID", "INVALID", false, false, false, false, emptyAuthorities());
        this.id = 0;
        this.admin = false;
    }
	
	public SampleAppUserDetails(final int id, final String username, 
			final String encodedPassword, final Collection<SampleAppGrantedAuthority> authorities) {
		super(username, encodedPassword, true, true, true, true, authorities);
		this.id  = id;
		for (final SampleAppGrantedAuthority a : authorities) {
			if (a.getName().equals(SampleAppGrantedAuthority.NAME_ADMINISTRATOR)) {
				this.admin = true;
				return;
			}
		}
		this.admin = false;
	}
	
	private final int id;
	private final boolean admin;
	
	public int getId() {
		return id;
	}
	public boolean isAdmin() {
		return admin;
	}
}
