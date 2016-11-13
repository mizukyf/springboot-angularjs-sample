package sample.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorViewResolver;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import sample.service.HtmlService;

/**
 * アプリケーション・エラーを示すパスに応答するコントローラ.
 * <p>親クラス{@link BasicErrorController}からの変更点は、
 * HTTPステータス・コードが"404 Not Found"のときの挙動だけである。
 * コントローラはクライアント側でHistory APIによる擬似的なページ遷移が行われることを前提に、
 * いかなるリクエストURIの場合でも静的リソースやRESTful APIに該当するものがない場合は"/"と同じコンテンツを返す。</p>
 */
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class ErrorController extends BasicErrorController {
	@Autowired
	private HtmlService htmlService;

	@Autowired
	public ErrorController(ServerProperties serverProperties, List<ErrorViewResolver> errorViewResolvers) {
		super(new DefaultErrorAttributes(), serverProperties.getError(), errorViewResolvers);
		Assert.notNull(serverProperties.getError(), "ErrorProperties must not be null");
	}

	@Override
	@RequestMapping(produces = "text/html")
	public ModelAndView errorHtml(final HttpServletRequest req, final HttpServletResponse resp) {
		final String uri = req.getRequestURI();
		final HttpStatus status = getStatus(req);
		final Map<String, Object> model = Collections.unmodifiableMap(getErrorAttributes(
				req, isIncludeStackTrace(req, MediaType.TEXT_HTML)));
		
		// HTTPステータス・コードが"404 Not Found"のときは"200 OK"に上書きして"/"と同じコンテンツを返す
		// ＊これはクライアントサイドでHistory APIを使用した擬似的ページ遷移を行うための設定である。
		// 　この設定がないとページリロードやURL直アクセスのとき404 Not Foundになってしまう。
		if ((uri == null || !uri.startsWith("/api")) && status == HttpStatus.NOT_FOUND) {
			resp.setStatus(HttpStatus.OK.value());
			final ModelAndView mv404 = htmlService.modelAndView("index", HttpStatus.OK, req, model);
			return mv404;
		}

		// それ以外のときはステータス・コードはそのままでエラー・ページを表示
		resp.setStatus(status.value());
		final ModelAndView modelAndView = resolveErrorView(req, resp, status, model);
		return (modelAndView == null ? htmlService.modelAndView("error", status, req, model) : modelAndView);
	}
	
	@Override
	@RequestMapping
	@ResponseBody
	public ResponseEntity<Map<String, Object>> error(final HttpServletRequest req){
		return super.error(req);
	}
}
