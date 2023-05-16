package co.jp.stepCounter.domain.model.commentPatternMatch;

import java.util.Objects;
import java.util.regex.Pattern;

import co.jp.stepCounter.domain.model.commentPatternMatch.CommentPatternMatchFactory.CommentPatternMatchType;

/**
 * <p>
 * sqlコメントパターン判定の具象クラス
 * <p>
 * sqlコメントを判定する処理を提供する具象クラスです。
 * <p>
 * {@link IfCommentPatternMatch}で定義されているメソッドをオーバライドして、<br>
 * １行コメント／複数行コメント（開始）／複数行コメント（終了）を判定する処理を実装しています。
 * <p>
 * インスタンスを生成する際は、{@link CommentPatternMatchType#of(String)}を用いて生成してください。
 * 
 * @since 1.0
 * @version 1.0
 * @author takashi.ebina
 * 
 * @see IfCommentPatternMatch
 * @see AbsCommentPatternMatch
 * @see CommentPatternMatchFactory
 */
public class SqlCommentPatternMatch extends AbsCommentPatternMatch {
	/**
	 * <p>
	 * １行コメント判定メソッド
	 * <p>
	 * １行コメントを含んでいるか判定するメソッドです。
	 * 
	 * @param target 検索対象の行
	 * @return １行コメントが存在する場合はtrueを返却。それ以外の場合はfalseを返却する。
	 */
	@Override
	public boolean isSingleCommentPattern(String target) {
		final Pattern singleCommentPattern = Pattern.compile(SingleCommentPattern.sql.getValue());
		return singleCommentPattern.matcher(target).find();
	}

	/**
	 * <p>
	 * 複数行コメント（開始）判定メソッド
	 * <p>
	 * 複数行コメント（開始）を含んでいるか判定するメソッドです。
	 * 
	 * @param target 検索対象の行
	 * @return 複数行コメント（開始）が存在する場合はtrueを返却。それ以外の場合はfalseを返却する。
	 */
	@Override
	public boolean isStartMultiCommentPattern(final String target) {
		final Pattern startMultiCommentPattern = Pattern.compile(StartMultiCommentPattern.sql.getValue());
		return startMultiCommentPattern.matcher(target).find();
	}

	/**
	 * <p>
	 * 複数行コメント（終了）判定メソッド
	 * <p>
	 * 複数行コメント（終了）を含んでいるか判定するメソッドです。
	 * 
	 * @param target 検索対象の行
	 * @return 複数行コメント（終了）が存在する場合はtrueを返却。それ以外の場合はfalseを返却する。
	 */
	@Override
	public boolean isEndMultiCommentPattern(final String target) {
		final Pattern endMultiCCommentPattern = Pattern.compile(EndMultiCommentPattern.sql.getValue());
		return endMultiCCommentPattern.matcher(target).find();
	}

	/**
	 * <p>
	 * このオブジェクトが引数の他のオブジェクトが等しいかどうかを判定するメソッド
	 *
	 * @param obj 比較対象のオブジェクト
	 * @return このオブジェクトが引数と同じである場合はtrue。それ以外の場合はfalseを返却する。
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof SqlCommentPatternMatch)) {
			return false;
		}
		return true;
	}

	/**
	 * <p>
	 * オブジェクトのハッシュ・コード値を返却するメソッド
	 * 
	 * @return このオブジェクトのハッシュ・コード値。
	 */
	@Override
	public int hashCode() {
		return Objects.hash();
	}
	
	/**
	 * <p>
	 * sqlコメントパターン判定のオブジェクトを返却するメソッド
	 * 
	 * @return Javaコメントパターン判定のオブジェクト
	 */
    @Override
    @SuppressWarnings("unchecked")
	public <T extends IfCommentPatternMatch> T create() {
    	return (T) new SqlCommentPatternMatch();
	}

}
