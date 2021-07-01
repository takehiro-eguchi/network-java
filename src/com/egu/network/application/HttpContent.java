package com.egu.network.application;

/**
 * HTTPのコンテンツを表すオブジェクトです。
 * @author t-eguchi
 *
 */
public class HttpContent {

	/** 内容 */
	private final byte[] content;

	/**
	 * コンテンツを渡すことにより、インスタンスを生成します。
	 * @param content
	 */
	public HttpContent(byte[] content) {
		this.content = content;
	}

	/**
	 * コンテンツを渡すことにより、インスタンスを生成します。
	 * @param content
	 */
	public HttpContent(String content) {
		this.content = content.getBytes();
	}

	/**
	 * コンテンツを取得します。
	 * @return
	 */
	public byte[] getContent() {
		return content;
	}

	/**
	 * 文字列としてコンテンツを取得します。
	 * @return
	 */
	public String getContentAsString() {
		return new String(content);
	}

	@Override
	public String toString() {
		return getContentAsString();
	}
}
