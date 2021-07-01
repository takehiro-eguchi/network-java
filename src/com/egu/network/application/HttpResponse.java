package com.egu.network.application;

/**
 * HTTPレスポンスを表すオブジェクトです。
 * @author t-eguchi
 *
 */
public class HttpResponse {

	/** ステータス行 */
	public static class HttpStatusLine {

		/** デフォルトHTTPバージョン */
		public static final String DEFAULT_HTTP_VERSION = "HTTP/1.1";
		/** バージョン */
		private final String version;
		/** ステータスコード */
		private final String code;
		/** ステータスメッセージ */
		private final String message;

		/** OKを作成します */
		public static HttpStatusLine ok() {
			return new HttpStatusLine(DEFAULT_HTTP_VERSION, "200", "OK");
		}

		/** OKを作成します */
		public static HttpStatusLine found() {
			return new HttpStatusLine(DEFAULT_HTTP_VERSION, "302", "Found");
		}

		/**
		 * バージョン、ステータスコード、ステータスメッセージを渡すことにより、インスタンスを生成します。
		 * @param version
		 * @param code
		 * @param message
		 */
		public HttpStatusLine(
				String version, String code, String message) {
			this.version = version;
			this.code = code;
			this.message = message;
		}

		/** バージョンを取得します */
		public String getVersion() { return version; }

		/** ステータスコードを取得します */
		public String getCode() { return code; }

		/** ステータスメッセージを取得します */
		public String getMessage() { return message; }

		@Override
		public String toString() {
			return String.format(
					"version = %s, code = %s, message = %s",
					version, code, message);
		}
	}

	/** ステータス行 */
	private final HttpStatusLine statusLine;
	/** ヘッダ */
	private final HttpHeader header;
	/** コンテンツ */
	private final HttpContent content;

	/**
	 * ステータス行、ヘッダ、コンテンツを渡すことにより、インスタンスを生成します。
	 * @param statusLine
	 * @param header
	 * @param content
	 */
	public HttpResponse(
			HttpStatusLine statusLine, HttpHeader header, HttpContent content) {
		this.statusLine = statusLine;
		this.header = header;
		this.content = content;
	}

	/** Not Found レスポンスを取得します */
	public static HttpResponse notFound() {
		HttpStatusLine line = new HttpStatusLine(
				HttpStatusLine.DEFAULT_HTTP_VERSION, "404", "Not Found");
		return new HttpResponse(line, null, null);
	}

	/** ステータス行 */
	public HttpStatusLine getStatusLine() { return statusLine;}

	/** ヘッダ */
	public HttpHeader getHeader() { return header; }

	/** コンテンツ */
	public HttpContent getContent() { return content; }

	@Override
	public String toString() {
		return String.format(
				"status = {%s}, header = {%s}, content = {%s}",
				statusLine, header, content);
	}
}
