package com.egu.network.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;

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
	 * 読み込みを行います。
	 * @param reader
	 * @param buf
	 * @return
	 */
	public static int read(Reader reader, char[] buf) {
		try {
			return reader.read(buf);
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

	/**
	 * 1行書き込みを行います。
	 * @param writer
	 * @param line
	 */
	public static void writeLine(BufferedWriter writer, String line) {
		try {
			writer.write(line);
			writer.newLine();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 改行します。
	 * @param writer
	 */
	public static void newLine(BufferedWriter writer) {
		try {
			writer.newLine();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 書き込みを行います。
	 * @param writer
	 * @param string
	 */
	public static void write(BufferedWriter writer, String string) {
		try {
			writer.write(string);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * バッファリングをフラッシュします。
	 * @param writer
	 */
	public static void flush(BufferedWriter writer) {
		try {
			writer.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
