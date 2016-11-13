package sample.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import sample.SampleAppSettings;

@Service
public class HtmlService {
	@Autowired
	private SampleAppSettings settings;
	private Map<String,Object> appInfo = null;
	
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
	 * @param name ビュー名
	 * @param status HTTPステータス
	 * @param req HTTPサーブレット・リクエスト
	 * @param errorAttributes エラー属性情報
	 * @return {@link ModelAndView}インスタンス
	 */
	public ModelAndView modelAndView(final String name, 
			final HttpStatus status, final HttpServletRequest req, 
			final Map<String,Object> errorAttributes) {
		
		if (appInfo == null) {
			final Map<String,Object> tmp = new HashMap<String, Object>();
			tmp.put("appName", settings.getAppName());
			tmp.put("appVersion", settings.getAppVersion());
			appInfo = tmp;
		}
		final ModelAndView mv = new ModelAndView(name);
		mv.setStatus(status);
		mv.addObject("contextPath", req.getContextPath());
		mv.addObject("error", errorAttributes == null ? Collections.EMPTY_MAP : errorAttributes);
		mv.addAllObjects(appInfo);
		return mv;
	}
	
	public ModelAndView modelAndView(final String name, 
			final HttpStatus status, final HttpServletRequest req) {
		return modelAndView(name, status, req, null);
	}
	
	public ModelAndView modelAndView(final String name, final HttpServletRequest req) {
		return modelAndView(name, HttpStatus.OK, req, null);
	}
}
