package co.jp.stepCounter.constant;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

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
	
	/**
	 * <p>
	 * JDBCの接続情報
	 */
	public static final Map<String, String> JDBC_PROPERTIES = new HashMap<>();
	static {
		// プロパティファイルのパスを指定する
		File dicDir = Paths.get(".\\resources\\settings").toFile();
        URLClassLoader urlLoader = null; // ★2
        try {
			urlLoader = new URLClassLoader(new URL[]{dicDir.toURI().toURL()});
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} // ★3
        
		ResourceBundle res = ResourceBundle.getBundle("JDBC", Locale.JAPAN, urlLoader);
		for (String key : res.keySet()) {
			SystemConstant.JDBC_PROPERTIES.put(key, res.getString(key));
		}
	}
}

