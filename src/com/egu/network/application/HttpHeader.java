package com.egu.network.application;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * HTTPヘッダを表すオブジェクトです。
 * @author t-eguchi
 */
public class HttpHeader {

	/** コンテンツ長 */
	private static final String CONTENT_LENGTH = "Content-Length";

	/** ヘッダ */
	private final Map<String, String> headers = new HashMap<>();

	/**
	 * ヘッダを追加します。
	 * @param key
	 * @param value
	 */
	public void addHeader(String key, String value) {
		headers.put(key, value);
	}

	/**
	 * コンテンツ長を設定します。
	 * @param length
	 */
	public void addContentLength(int length) {
		addHeader(CONTENT_LENGTH, String.valueOf(length));
	}

	/**
	 * コンテンツ長を取得します。
	 */
	public int getContentLength() {
		String stringValue = getHeader(CONTENT_LENGTH);
		return stringValue != null ? Integer.valueOf(stringValue) : 0;
	}

	/**
	 * ヘッダを追加します。
	 * @param line
	 */
	public void addHeader(String line) {
		// : で区切る
		String[] elements = line.split(":", 2);
		// キー
		int index = 0;
		String key = elements[index++].trim();
		// 値
		String value = elements[index++].trim();
		// 追加
		addHeader(key, value);
	}

	/**
	 * ヘッダ値を取得します。
	 * @param key
	 * @return
	 */
	public String getHeader(String key) { return headers.get(key); }

	/**
	 * ヘッダの処理を行います。
	 * @param consumer
	 */
	public void forEach(BiConsumer<String, String> consumer) {
		headers.forEach(consumer);
	}

	@Override
	public String toString() {
		return headers.toString();
	}
}
