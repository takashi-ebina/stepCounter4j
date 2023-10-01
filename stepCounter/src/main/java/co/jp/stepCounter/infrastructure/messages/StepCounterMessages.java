package co.jp.stepCounter.infrastructure.messages;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import co.jp.stepCounter.infrastructure.dbdao.JdbcConnection;
import jp.co.future.uroborosql.SqlAgent;

/**
 * <p>
 * テーブルに保有するメッセージをメモリに保持するクラス
 * 
 * @since 1.0
 * @version 1.0
 * @author takashi.ebina
 */
public class StepCounterMessages {

	/** 唯一のStepCounterMessagesインスタンス */
	private static StepCounterMessages thisInstance = null;
	/** JdbcConnectionインスタンス */
	private final JdbcConnection con = JdbcConnection.getInstance();
	/** メッセージ */
	private final List<Map<String, Object>> messages;
	
	/**
	 * <p>
	 * インスタンス返却メソッド
	 * 
	 * @return SqlConfigインスタンス
	 */
	public static StepCounterMessages getInstance() {
		if (thisInstance == null) {
			thisInstance = new StepCounterMessages();
		} 
		return thisInstance;
	}
	
	/**
	 * <p>
	 * コンストラクタ
	 * <p>
	 * Singleton実装の為、外部からのインスタンス化は不可とする。
	 */
	private StepCounterMessages() {
		try (final SqlAgent agent = con.getSqlConfig().agent()) {
			this.messages = agent.query("setup/select_message").collect();
		}
	}
	/**
	 * <p>
	 * メッセージコードとバインドパラメータから、メッセージを生成するメソッド
	 * @param id メッセージID
	 * @param bindMsgs バインドメッセージ
	 * @return メッセージ
	 */
	public String getMessageText(final String id, final Object... bindMessages) {
		final Optional<Map<String, Object>> mapMessage = this.messages.stream()
				.filter(r -> r.containsValue(id)).findFirst();
		final Object objMessage = mapMessage.orElseGet(HashMap<String, Object>::new).get("MESSAGETEXT");
		
		if (!(objMessage instanceof String strMessage)) return "";
		
		if (Objects.nonNull(bindMessages) && bindMessages.length > 0) {
			strMessage = MessageFormat.format(strMessage, bindMessages);
		} 
		return strMessage;
	}
}
