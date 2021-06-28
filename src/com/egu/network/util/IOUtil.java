package com.egu.network.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * 入出力ユーティリティです。
 * @author t-eguchi
 *
 */
public class IOUtil {

	/** デフォルトコンストラクタを隠蔽 */
	private IOUtil() {}

	/**
	 * {@link BufferedReader}を作成します。
	 * @param inputStream
	 * @return
	 */
	public static BufferedReader newBufferedReader(InputStream inputStream) {
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		return bufferedReader;
	}

	/**
	 * {@link BufferedWriter}を作成します。
	 * @param outputStream
	 * @return
	 */
	public static BufferedWriter newBufferedWriter(OutputStream outputStream) {
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
		BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
		return bufferedWriter;
	}
}
