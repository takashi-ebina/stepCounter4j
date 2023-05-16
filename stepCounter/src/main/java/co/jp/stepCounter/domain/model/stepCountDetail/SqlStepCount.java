package co.jp.stepCounter.domain.model.stepCountDetail;

import java.util.Objects;

import co.jp.stepCounter.domain.model.commentPatternMatch.AbsCommentPatternMatch;
import co.jp.stepCounter.domain.model.commentPatternMatch.IfCommentPatternMatch;
import co.jp.stepCounter.domain.model.stepCountDetail.StepCountFactory.MethodType;
import co.jp.stepCounter.domain.model.stepCountDetail.StepCountFactory.StepCountType;

/**
 * <p>
 * ステップカウントの具象クラス
 * <p>
 * Sqlファイルのステップカウント処理を提供する具象クラスです。
 * <p>
 * インスタンスを生成する際は、{@link StepCountType#of(String, IfCommentPatternMatch)}を用いて生成してください。
 * 
 * @since 1.0
 * @version 1.0
 * @author takashi.ebina
 * 
 * @see IfStepCount
 * @see AbsStepCount
 * @see StepCountFactory
 * @see AbsCommentPatternMatch
 */
public class SqlStepCount extends AbsStepCount {

	/**
	 * <p>
	 * コンストラクタ
	 * 
	 * ファクトリクラスとして利用するために、{@link StepCountType}で利用しています。<br>
	 * 同一パッケージでこのコンストラクタを用いたインスタンスの生成は可能ですが。<br>
	 * {@link AbsStepCount#commentPatternMatch}が初期化されないため、推奨していません。<br>
	 * インスタンスを生成する際は、{@link StepCountType#of(String, IfCommentPatternMatch)}を用いて生成してください。
	 */
	SqlStepCount() {
		super();
	}
	
	/**
	 * <p>
	 * コンストラクタ
	 * 
	 * @param commentPatternMatch コメントパターン判定用クラス
	 * @param methodType メソッド区分
	 * @throws IllegalArgumentException コメントパターン判定用クラスがNullの場合
	 */
	public SqlStepCount(final IfCommentPatternMatch commentPatternMatch, final MethodType methodType) {
		super(commentPatternMatch, methodType);
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
		if (!(obj instanceof SqlStepCount)) {
			return false;
		}
		
		SqlStepCount test = (SqlStepCount) obj;
		if (!(Objects.equals(this.commentPatternMatch, test.commentPatternMatch))) {
			return false;
		}
		if (!(Objects.equals(this.methodType, test.methodType))) {
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
	 * Sqlステップカウントのオブジェクトを返却するメソッド
	 * 
	 * @param commentPatternMatch コメントパターン判定用クラス
	 * @param methodType メソッド区分
	 * @throws IllegalArgumentException コメントパターン判定用クラスがNullの場合
	 * @return Sqlステップカウントのオブジェクト
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T extends IfStepCount> T create(IfCommentPatternMatch commentPatternMatch, MethodType methodType) {
		return (T) new SqlStepCount(commentPatternMatch, methodType);
	}

}
