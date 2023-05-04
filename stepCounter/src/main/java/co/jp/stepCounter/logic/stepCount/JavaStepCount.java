package co.jp.stepCounter.logic.stepCount;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

import co.jp.stepCounter.constant.Constant;
import co.jp.stepCounter.data.StepCountData;
import co.jp.stepCounter.logic.commentPatternMatch.AbsCommentPatternMatch;
import co.jp.stepCounter.logic.commentPatternMatch.IfCommentPatternMatch;
import co.jp.stepCounter.logic.stepCount.StepCountFactory.StepCountType;

/**
 * <p>
 * ステップカウントの具象クラス
 * <p>
 * Javaファイルのステップカウント処理を提供する具象クラスです。
 * <p>
 * インスタンスを生成する際は、{@link StepCountType#of(String, IfCommentPatternMatch)}を用いて生成してください。
 * 
 * @since 1.0
 * @version 1.0
 * @author takashi.ebina
 * 
 * @see IfStepCount
 * @see AbsStepCount
 * @see CsStepCount
 * @see StepCountFactory
 * @see AbsCommentPatternMatch
 */
public class JavaStepCount extends AbsStepCount {
	
	/**
	 * <p>
	 * コンストラクタ
	 * 
	 * ファクトリクラスとして利用するために、{@link StepCountType}で利用しています。<br>
	 * 同一パッケージでこのコンストラクタを用いたインスタンスの生成は可能ですが。<br>
	 * {@link AbsStepCount#commentPatternMatch}が初期化されないため、推奨していません。<br>
	 * インスタンスを生成する際は、{@link StepCountType#of(String, IfCommentPatternMatch)}を用いて生成してください。
	 */
	JavaStepCount() {
		super();
	}
	
	/**
	 * <p>
	 * コンストラクタ
	 * 
	 * @param commentPatternMatch コメントパターン判定用クラス
	 * @throws IllegalArgumentException コメントパターン判定用クラスがNullの場合
	 */
	public JavaStepCount(final IfCommentPatternMatch commentPatternMatch) {
		super(commentPatternMatch);
	}

	/**
	 * <p>
	 * Javaファイルのステップ数をカウント結果出力対象ファイルに書き込み処理を行う具象メソッド
	 * <p>
	 * カウント対象プログラムファイル（{@link StepCountData#getInputFile}）を元に、総行数／実行行数／コメント行数／空行数を集計処理を行います。
	 * 
	 * @param stepCountData 1ファイル単位の総行数／実行行数／コメント行数／空行数を集計するデータクラス
	 * @return 1ファイル単位の総行数／実行行数／コメント行数／空行数を集計するデータクラス
	 */
	@Override
	protected StepCountData fileReadStepCount(final StepCountData stepCountData) {
		try (BufferedReader bw = new BufferedReader(
				new InputStreamReader(new FileInputStream(stepCountData.getInputFile()), Constant.CHARSET_NAME))) {
			String readLine = "";
			boolean isCommentLine = false;
			while ((readLine = bw.readLine()) != null) {
				final String trimReadLine = readLine.trim();
				stepCountData.incrementTotalStepCount();
				// コメント行状態判定
				if (isCommentLine) {
					stepCountData.incrementCommentStepCount();
					if (super.isEndMultiCommentPattern(trimReadLine)) {
						isCommentLine = false;
					}
					continue;
				}
				// 空行存在判定
				if (Objects.equals(trimReadLine, "")) {
					stepCountData.incrementEmptyStepCount();
					continue;
				}
				// １行コメント存在判定
				if (super.isSingleCommentPattern(trimReadLine)) {
					stepCountData.incrementCommentStepCount();
					continue;
				}
				// 複数行コメント（１行で完結する複数行コメント）存在判定
				if (super.isStartMultiCommentPattern(trimReadLine) && super.isEndMultiCommentPattern(trimReadLine)) {
					stepCountData.incrementCommentStepCount();
					continue;
				}
				// 複数行コメント（複数行にまたがる複数行コメント）存在判定
				if (super.isStartMultiCommentPattern(trimReadLine) && !super.isEndMultiCommentPattern(trimReadLine)) {
					stepCountData.incrementCommentStepCount();
					isCommentLine = true;
					continue;
				}
				stepCountData.incrementExecStepCount();
			}
		} catch (IOException e) {
			logger.logError("Javaステップ数集計処理で例外発生。 ファイル名：" + stepCountData.getInputFile().getName(), e);
			// ステップカウント処理で例外が発生した場合は、該当ファイルのステップ数の出力を行わない。
			stepCountData.setCanWriteStepCount(false);
		}
		return stepCountData;
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
		if (!(obj instanceof JavaStepCount)) {
			return false;
		}
		
		JavaStepCount test = (JavaStepCount) obj;
		if (!(Objects.equals(this.commentPatternMatch, test.commentPatternMatch))) {
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
	 * Javaステップカウントのオブジェクトを返却するメソッド
	 * 
	 * @param commentPatternMatch コメントパターン判定用クラス
	 * @throws IllegalArgumentException コメントパターン判定用クラスがNullの場合
	 * @return Javaステップカウントのオブジェクト
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T extends IfStepCount> T create(final IfCommentPatternMatch commentPatternMatch) {
		return (T) new JavaStepCount(commentPatternMatch);
	}
}