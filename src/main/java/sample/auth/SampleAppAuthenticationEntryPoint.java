package sample.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * サンプル・アプリケーションのための認証エントリーポイント.
 * <p>Spring Bootのデフォルトでは認証失敗時にHTTPステータスコード403を返すエントリーポイントが使用される。
 * それでも良いといえば良いのだが、「認証されていない（だから認証手続きをすべきだ）」という旨を表現したいため、
 * 独自のエントリーポイント実装を提供する。</p>
 */
public class SampleAppAuthenticationEntryPoint implements AuthenticationEntryPoint {
	@Override
	public void commence(
			final HttpServletRequest req, 
			final HttpServletResponse resp, 
			final AuthenticationException ex)
					throws IOException, ServletException {
		resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
	}
}
