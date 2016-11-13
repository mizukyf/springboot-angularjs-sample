package sample.auth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

/**
 * CSRF（XSRF）対策のトークン設定を行うフィルター.
 * <p>1回のリクエスト・ディスパッチあたり必ず1回だけ実行される。</p>
 * <p>Spring BootではデフォルトでCSRF対策のクッキーを埋め込む機能を有しているが、
 * クッキーの名称が"CSRF-TOKEN"となっており、
 * AngularJSが用いる名称の"XSRF-TOKEN"と異なっている。
 * このフィルターではこの齟齬をなくすための処理を行っている。</p>
 */
public class SampleAppCsrfHeaderFilter extends OncePerRequestFilter {
	private final String cookieName;
	
	public SampleAppCsrfHeaderFilter(final String cookieName) {
		this.cookieName = cookieName;
	}
	
	@Override
	protected void doFilterInternal(
			final HttpServletRequest request,
			final HttpServletResponse response,
			final FilterChain filterChain)
			throws ServletException, IOException {
		
		final CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
		if (csrf != null) {
			final Cookie cookie = WebUtils.getCookie(request, cookieName);
			final String token = csrf.getToken();
			if (cookie == null || token != null && !token.equals(cookie.getValue())) {
				final Cookie newCookie = new Cookie(cookieName, token);
				newCookie.setPath("/");
				response.addCookie(newCookie);
			}
		}
		filterChain.doFilter(request, response);
	}
}