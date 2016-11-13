package sample.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import sample.service.HtmlService;

/**
 * リクエスト・パス"/"に応答するコントローラ.
 */
@Controller
public class IndexController {
	@Autowired
	private HtmlService htmlService;
	
	/**
	 * リクエスト・パス"/"に対応するハンドラー・メソッド.
	 * @param req HTTPサーブレット・リクエスト
	 * @return {@link ModelAndView}インスタンス
	 */
	@RequestMapping("/")
	public ModelAndView get(final HttpServletRequest req) {
		return htmlService.modelAndView("index", req);
	}
}
