package com.egu.network.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

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
	 * 1行読み込みます。
	 * @param reader
	 * @return
	 */
	public static String readLine(BufferedReader reader) {
		try {
			return reader.readLine();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * {@link Writer}へデータを移動させます。
	 * @param reader
	 * @param writer
	 * @return
	 */
	public static long transferTo(Reader reader, Writer writer) {
		try {
			return reader.transferTo(writer);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
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
