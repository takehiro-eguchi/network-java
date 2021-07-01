package com.egu.network.application;

import com.egu.network.application.HttpResponse.HttpStatusLine;

/**
 * HTTPサーバの実行クラスです。
 * @author t-eguchi
 *
 */
public class HTTPServerRunner {

	/** リクエスト回数 */
	private static int requestCount = 0;

	/**
	 * 実行開始します。
	 * @param args
	 */
	public static void main(String[] args) {
		// サーバの開始
		HTTPServer server = new HTTPServer(8080);
		server.addListener("/kadai_1", HTTPServerRunner::responseKadai1);
		server.addListener("/kadai_2", HTTPServerRunner::responseKadai2);
		server.addListener("/kadai_3", HTTPServerRunner::responseKadai3);
		server.start();

		// シャットダウンフックの登録
		Thread shutdownThread = new Thread(() -> server.stop());
		Runtime.getRuntime().addShutdownHook(shutdownThread);

		System.out.println("HTTPサーバを起動しました。");
	}

	/** 課題1のレスポンス */
	private static HttpResponse responseKadai1(HttpRequest request) {
		// ステータス行
		HttpStatusLine line = HttpStatusLine.ok();
		// ヘッダ
		HttpHeader header = new HttpHeader();
		header.addHeader("Content-Type", "text/html");
		// コンテンツ
		String contentString = getContent();
		HttpContent content = new HttpContent(contentString);
		// レスポンス作成
		return new HttpResponse(line, header, content);
	}

	/** 課題2のレスポンス */
	private static HttpResponse responseKadai2(HttpRequest request) {
		// ステータス行
		HttpStatusLine line = HttpStatusLine.ok();
		// ヘッダ
		HttpHeader header = new HttpHeader();
		header.addHeader("Content-Type", "text/html");
		header.addHeader("Cache-Control", "max-age=20");
		// コンテンツ
		String contentString = getContent();
		HttpContent content = new HttpContent(contentString);
		// レスポンス作成
		return new HttpResponse(line, header, content);
	}

	/** 課題3のレスポンス */
	private static HttpResponse responseKadai3(HttpRequest request) {
		// ステータス行
		HttpStatusLine line = HttpStatusLine.found();
		// ヘッダ
		HttpHeader header = new HttpHeader();
		header.addHeader("Location", "/kadai_1");
		// レスポンス作成
		return new HttpResponse(line, header, new HttpContent(new byte[0]));
	}

	/** コンテンツを取得します */
	private static String getContent() {
		return String.format(
				"<html><p>Hello, world !! (Request Count = %d) </p></html>", ++requestCount);
	}
}
