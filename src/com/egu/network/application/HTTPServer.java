package com.egu.network.application;

import java.util.function.Consumer;

import com.egu.network.tcp.TCPSocketServer;
import com.egu.network.tcp.TCPSocketServer.SocketTask;

/**
 * HTTP用のサーバです。
 * @author t-eguchi
 *
 */
public class HTTPServer {

	/** TCP用のサーバです */
	private final TCPSocketServer tcpServer;

	/**
	 * インスタンスを生成します。
	 * @param port ポート番号
	 * @param consumer コンシューマ
	 */
	public HTTPServer(
			int port, Consumer<SocketTask> consumer) {
		this.tcpServer = new TCPSocketServer(port, consumer);
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
