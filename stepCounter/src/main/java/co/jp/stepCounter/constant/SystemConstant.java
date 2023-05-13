package co.jp.stepCounter.constant;

import java.nio.charset.Charset;

/**
 * <p>
 * 共通の定数クラス
 * 
 * @since 1.0
 * @version 1.0
 * @author takashi.ebina
 */
public class SystemConstant {
	/**
	 * <p>
	 * コンストラクタ
	 * <p>
	 * 定数クラスのため、インスタンス化は不可とする。
	 */
	private SystemConstant() {
	}

	/**
	 * <p>
	 * 改行コード(UNIXでは"\n")
	 */
	public static final String LINE_SEPARATOR = System.getProperty("line.separator");

	/**
	 * <p>
	 * 文字コード（Java仮想マシンのデフォルトの文字セット）
	 */
	public static final String CHARSET_NAME = Charset.defaultCharset().name();

}

