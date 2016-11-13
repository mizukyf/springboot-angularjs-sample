package sample.auth;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * サンプル・アプリケーションのための認証エントリーポイント.
 * <p>Spring Bootのデフォルトでは認証失敗時にHTTPステータスコード403を返すエントリーポイントが使用される。
 * それでも良いといえば良いのだが、「認証されていない（だから認証手続きをすべきだ）」という旨を表現したいため、
 * 独自のエントリーポイント実装を提供する。</p>
 * @return 認証エントリーポイント
 */
public class SampleAppAuthenticationEntryPoint implements AuthenticationEntryPoint {
	private final ObjectMapper mapper = new ObjectMapper();
	
	@Override
	public void commence(
			final HttpServletRequest req, 
			final HttpServletResponse resp, 
			final AuthenticationException ex)
					throws IOException, ServletException {
		resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		final PrintWriter pw = resp.getWriter();
		mapper.writeValue(pw, ex);
		pw.flush();
	}
}
