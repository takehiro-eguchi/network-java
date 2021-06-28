package com.egu.network.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * ソケット用のサーバです。
 * @author t-eguchi
 *
 */
public class TCPSocketServer {

	/** ソケット用のタスクです。 */
	public static class SocketTask {
		private final Socket socket;
		public SocketTask(Socket socket) {
			this.socket = socket;
		}
		public InputStream getIputStream() {
			try {
				return socket.getInputStream();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		public OutputStream getOutputStream() {
			try {
				return socket.getOutputStream();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		public void shutdown() {
			try {
				socket.shutdownInput();
				socket.shutdownOutput();
				socket.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	/** サーバソケット */
	private final ServerSocket serverSocket;

	/** 実行サービス */
	private final ExecutorService executorService;

	/** 停止フラグ */
	private volatile boolean isStopped;

	/** ソケット処理用のタスク */
	private final Consumer<SocketTask> consumer;

	/**
	 * インスタンスを生成します。
	 * @param port ポート番号
	 * @param consumer 受信時の実行タスク
	 */
	public TCPSocketServer(
			int port, Consumer<SocketTask> consumer) {
		this(port, consumer, 10, 5);
	}

	/**
	 * インスタンスを生成します。
	 * @param port ポート番号
	 * @param consumer 受信時の実行タスク
	 * @param backlog サーバソケットのバックログ
	 * @param threads サーバソケット処理の用スレッド数
	 */
	public TCPSocketServer(
			int port, Consumer<SocketTask> consumer,
			int backlog, int threads) {
		// サーバソケットの初期化
		try {
			this.serverSocket = new ServerSocket(port, backlog);
			this.serverSocket.setSoTimeout(1000);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		// コールバック処理の設定
		this.consumer = consumer;

		// 実行サービスの初期化
		this.executorService = Executors.newFixedThreadPool(threads);
	}

	/** 実行を開始します。 */
	public void start() {
		Thread thread = new Thread(() -> {
			while (!isStopped) {
				acceptSocket();
			}
		});
		thread.start();
	}

	/** ソケットの受付を行います。 */
	private void acceptSocket() {
		try {
			// ソケットの受付
			Socket socket = serverSocket.accept();

			// 実行
			executorService.execute(() -> {
				// ソケットタスクを作成
				SocketTask task = new SocketTask(socket);

				// 実行
				consumer.accept(task);

				// 終了
				task.shutdown();
			});

		} catch (SocketTimeoutException e) {
			// タイムアウト時は無視
		} catch (IOException e) {
			// エラー表示
			System.err.println("Failed to accept socket.");
			e.printStackTrace();

			// 終了
			stop();
		}
	}

	/** 停止します。 */
	public void stop() {
		isStopped = true;
	}
}
