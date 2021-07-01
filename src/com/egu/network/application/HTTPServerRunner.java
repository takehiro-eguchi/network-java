package com.egu.network.application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.egu.network.util.IOUtil;

/**
 * HTTPサーバの実行クラスです。
 * @author t-eguchi
 *
 */
public class HTTPServerRunner {

	/**
	 * 実行開始します。
	 * @param args
	 */
	public static void main(String[] args) {
		// サーバの開始
		HTTPServer server = new HTTPServer(8080, task -> {
			try(
				InputStream inputStream = task.getIputStream();
				BufferedReader reader = IOUtil.newBufferedReader(inputStream);
				OutputStream outputStream = task.getOutputStream();
				BufferedWriter writer = IOUtil.newBufferedWriter(outputStream)) {

				// 入力値のダンプ
				String line = null;
				while ((line = reader.readLine()) != null && !line.isEmpty()) {
					System.out.println(line);
				}

				// 出力
				writer.write("HTTP/1.1 200 OK");
				writer.newLine();
				String content = "<html><p>Hello, world</p></html>";
				int contentLength = content.getBytes().length;
				writer.write("Content-Length: " + contentLength);
				writer.newLine();
				writer.write("Content-Type: text/html");
				writer.newLine();
				writer.write("Cache-Control: max-age=20");
				writer.newLine();
				writer.newLine();
				writer.write(content);
				writer.flush();

			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});
		server.start();

		// シャットダウンフックの登録
		Thread shutdownThread = new Thread(() -> server.stop());
		Runtime.getRuntime().addShutdownHook(shutdownThread);
	}
}
