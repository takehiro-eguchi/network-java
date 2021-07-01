package com.egu.network.application;

import java.io.BufferedReader;
import java.io.InputStream;

import com.egu.network.util.IOUtil;

/**
 * HTTPリクエストオブジェクトです。
 * @author t-eguchi
 *
 */
public class HttpRequest {

	/** リクエスト行 */
	public static class HttpRequestLine {
		/** メソッド */
		private final String method;
		/** パス */
		private final String path;
		/** バージョン */
		private final String version;

		/**
		 * メソッド、パス、バージョンを渡すことにより、インスタンスを生成します。
		 * @param method
		 * @param path
		 * @param version
		 */
		private HttpRequestLine(String method, String path, String version) {
			this.method = method;
			this.path = path;
			this.version = version;
		}

		/**
		 * 文字列からリクエスト行を作成します。
		 * @param line
		 * @return
		 */
		public static HttpRequestLine build(String line) {
			// スペース区切り
			String[] elements = line.split(" ");
			// メソッド
			int index = 0;
			String method = elements[index++].trim();
			// パス
			String path = elements[index++].trim();
			// バージョン
			String version = elements[index++].trim();

			return new HttpRequestLine(method, path, version);
		}

		/** メソッドを取得します。 */
		public String getMethod() { return method; }

		/** パスを取得します。 */
		public String getPath() { return path; }

		/** バージョンを取得します。 */
		public String getVersion() { return version; }

		@Override
		public String toString() {
			return String.format(
					"method = %s, path = %s, version = %s",
					method, path, version);
		}
	}

	/** リクエスト行 */
	private final HttpRequestLine requestLine;
	/** ヘッダ */
	private final HttpHeader header;
	/** コンテンツ */
	private final HttpContent content;

	/**
	 * リクエスト行、ヘッダ、コンテンツを渡すことにより、インスタンスを生成します。
	 * @param requestLine
	 * @param header
	 * @param content
	 */
	private HttpRequest(HttpRequestLine requestLine, HttpHeader header, HttpContent content) {
		this.requestLine = requestLine;
		this.header = header;
		this.content = content;
	}

	/**
	 * HTTPリクエストを入力ストリームから作成します。
	 * @param inputStream
	 * @return
	 */
	public static HttpRequest build(InputStream inputStream) {
		BufferedReader reader = IOUtil.newBufferedReader(inputStream);

		// リクエスト行を取得
		String line = IOUtil.readLine(reader);
		HttpRequestLine requestLine = HttpRequestLine.build(line);

		// ヘッダ行を取得(改行がくるまで)
		HttpHeader header = new HttpHeader();
		while ((line = IOUtil.readLine(reader)) != null && !line.isEmpty()) {
			header.addHeader(line);
		}

		// 残りはコンテンツとして格納する
		int contentLength = header.getContentLength();
		char[] contentBufs = new char[contentLength];
		IOUtil.read(reader, contentBufs);
		HttpContent content = new HttpContent(new String(contentBufs));

		return new HttpRequest(requestLine, header, content);
	}

	/** リクエスト行を取得します。 */
	public HttpRequestLine getRequestLine() { return requestLine; }

	/** ヘッダを取得します。 */
	public HttpHeader getHeader() { return header; }

	/** コンテンツを取得します。 */
	public HttpContent getContent() { return content; }

	@Override
	public String toString() {
		return String.format(
				"requestLine = {%s}, header ={%s}, content = {%s}",
				requestLine, header, content);
	}
}
