package co.jp.base;

import java.io.InputStream;
/**
 * <p>
 * 標準入力をプログラムから実行するためにInputStreamをラップしたクラス
 * 
 * @since 1.0
 * @version 1.0
 * @author takashi.ebina
 * 
 * @see <a href="https://qiita.com/aky100200/items/f4f7d6279524774610fc">参考URL</a>
 */
public class StandardInputStream extends InputStream {
	/** 入力文字列 */
	private final StringBuilder sb = new StringBuilder();
	/** 改行コード */
	private final String lf = System.getProperty("line.separator");

	/**
	 * <p>
	 * 文字列を入力する。改行は自動的に行う
	 * 
	 * @param str 入力文字列
	 */
	public void inputln(final String str) {
		sb.append(str).append(lf);
	}

	/**
	 * <p>
	 * 入力ストリームからデータの次のバイトを読み込みます。
	 * 
	 * @return データの次のバイト。ストリームの終わりに達した場合は-1。
	 */
	@Override
	public int read() {
		if (sb.length() == 0) return -1;
		final int result = sb.charAt(0);
		sb.deleteCharAt(0);
		return result;
	}
}