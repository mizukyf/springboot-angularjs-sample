package sample.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import sample.SampleAppSettings;

/**
 * リクエスト・パス"/"に応答するコントローラ.
 */
@Controller
public class IndexController {
	@Autowired
	private SampleAppSettings settings;
	private Map<String,Object> appInfo = null;
	
	/**
	 * リクエスト・パス"/"に対応するハンドラー・メソッド.
	 * @param req HTTPサーブレット・リクエスト
	 * @return {@link ModelAndView}インスタンス
	 */
	@RequestMapping("/")
	public ModelAndView get(final HttpServletRequest req) {
		return modelAndView(req);
	}
	
	/**
	 * アプリケーションのルート・ページのため{@link ModelAndView}インスタンスを初期化する.
	 * <p>ビューのHTTPステータス・コードは必ず"200 OK"に設定される。
	 * またモデルには以下のキーと値が設定される。
	 * メソッド呼び出し元は必要に応じてこの値を上書きすべきである：</p>
	 * <ul>
	 * 	<li>{@code "appName"} - アプリケーションの名称。プロパティ・ファイルで指定されたもの。</li>
	 * 	<li>{@code "appVersion"} - アプリケーションのバージョン。プロパティ・ファイルで指定されたもの。</li>
	 * 	<li>{@code "contextPath"} - コンテキスト・パス。{@link HttpServletRequest#getContextPath()}の返す値。
	 * アプリケーションがデフォルト（ルート）のコンテキストで動作している場合は{@code ""}が設定される。</li>
	 * 	<li>{@code "error"} - エラー情報。デフォルトでは空のマップ。何らかのエラー発生時のフォールバックとして
	 * このメソッドが返す{@link ModelAndView}インスタンスを使用する場合はこの値は当該エラー情報で上書きされるべきである。</li>
	 * </ul>
	 * @param req HTTPサーブレット・リクエスト
	 * @return {@link ModelAndView}インスタンス
	 */
	public ModelAndView modelAndView(final HttpServletRequest req) {
		if (appInfo == null) {
			final Map<String,Object> tmp = new HashMap<String, Object>();
			tmp.put("appName", settings.getAppName());
			tmp.put("appVersion", settings.getAppVersion());
			appInfo = tmp;
		}
		final ModelAndView mv = new ModelAndView("index");
		mv.setStatus(HttpStatus.OK);
		mv.addObject("contextPath", req.getContextPath());
		mv.addObject("error", Collections.EMPTY_MAP);
		mv.addAllObjects(appInfo);
		return mv;
	}
}
