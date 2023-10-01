package co.jp.stepCounter.constant;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

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
		final Properties properties = new Properties();
		// プロパティファイルのパスを指定する
		String propertiesFilePath = System.getProperty("user.dir") + SystemConstant.JDBC_PROPERTIES_FILE_PATH;
		try {
			if (!Files.exists(Paths.get(propertiesFilePath))){
				propertiesFilePath = System.getProperty("user.dir") + SystemConstant.JDBC_PROPERTIES_FILE_PATH_LOCAL;
			}
			InputStream istream = new FileInputStream(propertiesFilePath);
			properties.load(istream);
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (Map.Entry<Object, Object> e : properties.entrySet()) {
			SystemConstant.JDBC_PROPERTIES.put(e.getKey().toString(), e.getValue().toString());
		}
	}
	/**
	 * <p>
	 * JDBC.propertiesのパス（ローカル）
	 */
	public static final String JDBC_PROPERTIES_FILE_PATH_LOCAL = "/src/main/resources/settings/JDBC.properties";
	/**
	 * <p>
	 * JDBC.propertiesのパス
	 */
	public static final String JDBC_PROPERTIES_FILE_PATH = "/src/main/resources/settings/JDBC.properties";
}

