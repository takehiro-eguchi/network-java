package com.egu.network.application;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.egu.network.application.HttpRequest.HttpRequestLine;
import com.egu.network.application.HttpResponse.HttpStatusLine;
import com.egu.network.tcp.TCPSocketServer;
import com.egu.network.tcp.TCPSocketServer.SocketTask;
import com.egu.network.util.IOUtil;

/**
 * HTTP用のサーバです。
 * @author t-eguchi
 *
 */
public class HTTPServer {

	/** TCP用のサーバです */
	private final TCPSocketServer tcpServer;

	/** リスナー */
	private final Map<String, Function<HttpRequest, HttpResponse>> listeners = new HashMap<>();

	/**
	 * インスタンスを生成します。
	 * @param port ポート番号
	 */
	public HTTPServer(int port) {
		this.tcpServer = new TCPSocketServer(port, this::executeTask);
	}

	/** 処理を実行します */
	private synchronized void executeTask(SocketTask task) {
		// 入力からリクエストを取得
		HttpRequest request = HttpRequest.build(task.getIputStream());
		System.out.println("リクエストを受け付けました。内容:" + request);

		// パスにより、実行するリスナーを特定
		HttpRequestLine requestLine = request.getRequestLine();
		Function<HttpRequest, HttpResponse> listener = listeners.get(requestLine.getPath());

		// リスナーを実行
		HttpResponse response = null;
		if (listener != null) {
			response = listener.apply(request);
		}

		// レスポンスが作成出来ない場合はNot Found
		if (response == null)
			response = HttpResponse.notFound();

		// レスポンスを出力する
		writeResponse(task, response);
		System.out.println("レスポンスを送信しました。内容：" + response);

		// 終了
		task.shutdown();
	}

	/** レスポンスを出力します。 */
	private void writeResponse(SocketTask task, HttpResponse response) {
		OutputStream outputStream = task.getOutputStream();
		BufferedWriter writer = IOUtil.newBufferedWriter(outputStream);

		// ステータス行を出力
		HttpStatusLine line = response.getStatusLine();
		IOUtil.writeLine(
				writer, String.format("%s %s %s",
						line.getVersion(), line.getCode(), line.getMessage()));

		// Content-Lengthを設定
		HttpContent content = response.getContent();
		byte[] contentBytes = content != null ? content.getContent() : null;
		int contentLength = 0;
		if (contentBytes != null) {
			contentLength = contentBytes.length;
		}
		HttpHeader header = response.getHeader();
		header.addHeader("Content-Length", String.valueOf(contentLength));

		// ヘッダの出力
		header.forEach((headerName, headerValue) -> {
			IOUtil.writeLine(
					writer, String.format("%s: %s", headerName, headerValue));
		});
		IOUtil.newLine(writer);

		// 本体の出力
		if (contentBytes != null) {
			IOUtil.write(writer, new String(contentBytes));
		}

		// フラッシュ
		IOUtil.flush(writer);
	}

	/**
	 * リスナーを追加します。
	 * @param path
	 * @param listener
	 */
	public synchronized void addListener(
			String path, Function<HttpRequest, HttpResponse> listener) {
		listeners.put(path, listener);
	}

	/** 開始します */
	public void start() {
		tcpServer.start();
	}

	/** 停止します */
	public void stop() {
		tcpServer.stop();
	}
}
