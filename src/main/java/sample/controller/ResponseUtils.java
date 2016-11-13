package sample.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class ResponseUtils {
	private ResponseUtils() {}
	
	/**
	 * HTTPステータスコード{@code 200 OK}を返すためのレスポンスエンティティを生成する.
	 * @return レスポンスエンティティ
	 */
	public static<T> ResponseEntity<T> ok() {
		return new ResponseEntity<T>(HttpStatus.OK);
	}
	
	/**
	 * HTTPステータスコード{@code 200 OK}を返すためのレスポンスエンティティを生成する.
	 * @param value オブジェクト
	 * @return レスポンスエンティティ
	 */
	public static<T> ResponseEntity<T> ok(final T value) {
		return new ResponseEntity<T>(value, HttpStatus.OK);
	}
	
	/**
	 * HTTPステータスコード{@code 404 Not Found}を返すためのレスポンスエンティティを生成する.
	 * @return レスポンスエンティティ
	 */
	public static<T> ResponseEntity<T> notFound() {
		return new ResponseEntity<T>(HttpStatus.NOT_FOUND);
	}
	
	/**
	 * 引数が{@code null}の場合HTTPステータスコード{@code 404 Not Found}のレスポンスエンティティを生成する.
	 * そうでない場合はHTTPステータスコード{@code 200 OK}のレスポンスエンティティを生成する.
	 * 
	 * @param value オブジェクト
	 * @return レスポンスエンティティ
	 */
	public static<T> ResponseEntity<T> okOrNotFound(final T value) {
		if (value == null) {
			return notFound();
		} else {
			return ok(value);
		}
	}
	
	/**
	 * HTTPステータスコード{@code 400 Bad Request}を返すためのレスポンスエンティティを生成する.
	 * @param message メッセージ
	 * @return レスポンスエンティティ
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static<T> ResponseEntity<T> badRequest(final String message) {
		// 戻り値型を揃えるため強引にキャストを行う
		// ＊イレイジャを前提としたトリック
		return (ResponseEntity<T>) new ResponseEntity(message, HttpStatus.BAD_REQUEST);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static<T> ResponseEntity<T> badRequest(final Throwable cause) {
		// 戻り値型を揃えるため強引にキャストを行う
		// ＊イレイジャを前提としたトリック
		return (ResponseEntity<T>) new ResponseEntity(cause, HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * HTTPステータスコード{@code 500 Internal Server Error}を返すためのレスポンスエンティティを生成する.
	 * @param value オブジェクト
	 * @return レスポンスエンティティ
	 */
	public static<T> ResponseEntity<T> internalServerError(final T value) {
		return new ResponseEntity<T>(value, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/**
	 * HTTPステータスコード{@code 500 Internal Server Error}を返すためのレスポンスエンティティを生成する.
	 * @param message メッセージ
	 * @return レスポンスエンティティ
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static<T> ResponseEntity<T> internalServerError(final String message) {
		// 戻り値型を揃えるため強引にキャストを行う
		// ＊イレイジャを前提としたトリック
		return (ResponseEntity<T>) new ResponseEntity(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static<T> ResponseEntity<T> internalServerError(final Throwable cause) {
		// 戻り値型を揃えるため強引にキャストを行う
		// ＊イレイジャを前提としたトリック
		return (ResponseEntity<T>) new ResponseEntity(cause, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
