package sample;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * ServletContextを構成するためのインターフェース.
 * <p>web.xmlの代わりにJavaプログラムによりServletContextを構成する。</p>
 * <p>サンプルのアプリはwarファイルをビルドしスタンドアロンのサーブレット・コンテナに
 * デプロイするタイプのアプリケーションなので、
 * このインターフェースを使って設定を変更する必要がある。</p>
 */
public class SampleAppServletInitializer extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SampleApplication.class);
	}
}