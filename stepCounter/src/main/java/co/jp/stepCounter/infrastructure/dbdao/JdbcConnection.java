package co.jp.stepCounter.infrastructure.dbdao;

import co.jp.stepCounter.constant.SystemConstant;
import jp.co.future.uroborosql.UroboroSQL;
import jp.co.future.uroborosql.config.SqlConfig;

/**
 * <p>
 * JDBCに関するクラス
 * 
 * @since 1.0
 * @version 1.0
 * @author takashi.ebina
 */
public class JdbcConnection {

	/** 唯一のJdbcConnectionインスタンス */
	private static JdbcConnection thisInstance = null;
	/** SqlConfigオブジェクト */
	private final SqlConfig config;
	
	/**
	 * <p>
	 * インスタンス返却メソッド
	 * 
	 * @return JdbcConnectionインスタンス
	 */
	public static JdbcConnection getInstance() {
		if (thisInstance == null) {
			thisInstance = new JdbcConnection();
		} 
		return thisInstance;
	}

	/**
	 * <p>
	 * コンストラクタ
	 * <p>
	 * Singleton実装の為、外部からのインスタンス化は不可とする。
	 */
	private JdbcConnection() {
		this.config = UroboroSQL.builder(SystemConstant.JDBC_PROPERTIES.get("url")
							, SystemConstant.JDBC_PROPERTIES.get("user")
							, SystemConstant.JDBC_PROPERTIES.get("password")).build();
	}
	/**
	 * <p>
	 * Connectionオブジェクト取得メソッド
	 * 
	 * @return Connectionオブジェクト
	 */
	public SqlConfig getSqlConfig() {
		return this.config;
	}
}
