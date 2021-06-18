package com.egu.network.tcp;

import java.io.IOException;
import java.net.ServerSocket;

public class SocketServer {

	/** サーバソケット */
	private final ServerSocket serverSocket;

	/**
	 * ポート番号を渡すことにより、インスタンスを生成します。
	 * @param port
	 */
	public SocketServer(int port) {
		try {
			this.serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void start() {

	}
}
