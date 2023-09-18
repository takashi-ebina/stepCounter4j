package co.jp.base;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;

/**
 * <p>
 * 標準出力をプログラムから実行するためにPrintStreamをラップしたクラス
 * 
 * @since 1.0
 * @version 1.0
 * @author takashi.ebina
 * 
 * @see <a href="https://qiita.com/aky100200/items/f4f7d6279524774610fc">参考URL</a>
 */
public class StandardOutputStream extends PrintStream {
	/** 出力文字列 */
	private BufferedReader br = new BufferedReader(new StringReader(""));

	/** コンストラクタ */
	public StandardOutputStream() {
		super(new ByteArrayOutputStream());
	}

	/**
	 * 1行分の文字列を読み込む
	 * 
	 * @return 改行を含まない文字。終端の場合はnull
	 */
	public String readLine() {
		String line = "";
		try {
			if ((line = br.readLine()) != null) return line;
			br = new BufferedReader(new StringReader(out.toString()));
			((ByteArrayOutputStream) out).reset();
			return br.readLine();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
