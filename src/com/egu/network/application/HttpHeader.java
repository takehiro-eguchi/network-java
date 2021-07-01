package com.egu.network.application;

import java.util.HashMap;
import java.util.Map;

/**
 * HTTPヘッダを表すオブジェクトです。
 * @author t-eguchi
 */
public class HttpHeader {

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
	 * ヘッダを追加します。
	 * @param line
	 */
	public void addHeader(String line) {
		// : で区切る
		String[] elements = line.split(":", 1);
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
	public String getHeader(String key) {
		return headers.get(key);
	}
}
