package sample;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * ServletContextを構成するためのインターフェース.
 * <p>web.xmlの代わりにJavaプログラムによりServletContextを構成する。</p>
 * <p>サンプル・アプリケーションはwarファイルをビルドしスタンドアロンのサーブレット・コンテナに
 * デプロイする想定で構築しているので、このインターフェースを使って設定を変更する必要がある。</p>
 */
public class SampleAppServletInitializer extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SampleApplication.class);
	}
}