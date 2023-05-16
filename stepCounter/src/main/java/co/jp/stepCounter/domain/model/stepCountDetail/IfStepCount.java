package co.jp.stepCounter.domain.model.stepCountDetail;

import java.io.File;

import co.jp.stepCounter.domain.model.commentPatternMatch.IfCommentPatternMatch;
import co.jp.stepCounter.domain.model.stepCountDetail.StepCountFactory.MethodType;
import co.jp.stepCounter.domain.model.stepCountDetail.StepCountFactory.StepCountType;
import co.jp.stepCounter.domain.value.StepCountData;

/**
 * <p>ステップカウントのインタフェース
 * <p>各プログラムファイルのステップカウント処理を提供するインタフェースであり、<br>
 * 実装する際は、このインタフェースをimplementsした{@link AbsStepCount}を継承してください。
 * <p>ステップカウントのインスタンスを生成する際は、{@link StepCountType#of(String, IfCommentPatternMatch)}を用いて生成してください。
 * 
 * @since 1.0
 * @version 1.0
 * @author takashi.ebina
 * 
 * @see AbsStepCount
 * @see JavaStepCount
 * @see CsStepCount
 * @see SqlStepCount
 */
public interface IfStepCount {
	/**
	 * <p>
	 * プログラムファイルのステップ数を集計するメソッド
	 * <p>
	 * プログラムファイルのステップ数を集計する際の共通処理を実装してください。<br>
	 * 各プログラムファイル毎に異なる処理に関しては、{@link AbsStepCount}、およびそれを継承したサブクラスに実装してください。
	 * 
	 * @param inputFile           ステップカウント対象ファイル
	 * @return 1ファイル単位の総行数／実行行数／コメント行数／空行数を集計するデータクラス
	 * @throws Exception プログラムファイルのステップ数集計処理で例外が発生した場合
	 */
	public StepCountData stepCount(final File inputFile) throws Exception;
	
	/**
	 * <p>
	 * ステップカウントのオブジェクトを返却するメソッド
	 * 
	 * @param <T> 型パラメーターの例です
	 * @param commentPatternMatch コメントパターン判定用クラス
	 * @param methodType メソッド区分
	 * @return ステップカウントのオブジェクト
	 */
	public <T extends IfStepCount> T create(final IfCommentPatternMatch commentPatternMatch, final MethodType methodType);
}
