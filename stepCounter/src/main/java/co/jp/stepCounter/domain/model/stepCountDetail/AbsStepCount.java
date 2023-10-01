package co.jp.stepCounter.domain.model.stepCountDetail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

import co.jp.stepCounter.constant.SystemConstant;
import co.jp.stepCounter.domain.model.commentPatternMatch.AbsCommentPatternMatch;
import co.jp.stepCounter.domain.model.commentPatternMatch.IfCommentPatternMatch;
import co.jp.stepCounter.domain.model.stepCountDetail.StepCountFactory.MethodType;
import co.jp.stepCounter.domain.model.stepCountDetail.StepCountFactory.StepCountType;
import co.jp.stepCounter.domain.value.StepCountData;
import co.jp.stepCounter.infrastructure.log.Log4J2;
/**
 * <p>
 * ステップカウントの抽象クラス
 * <p>
 * 各プログラムファイルのステップカウント処理を提供する抽象クラスです。<br>
 *  標準のステップカウント処理を利用したい場合は{@link AbsStepCount#deFaultFileReadStepCount}を呼び出してください。<br>
 * プログラムファイル固有のステップ数集計処理を実装したい場合は、{@link MethodType#ORIGINAL}を設定し、<br>
 * {@link AbsStepCount#oridinalFileReadStepCount}をオーバライドし独自の集計処理を実装してください。<br>
 * また、１行コメント／複数行コメント（開始）／複数行コメント（終了）を判定するための正規表現の定義および実装を<br>
 * {@link AbsCommentPatternMatch}を継承したクラスに記載してください。
 * <p>
 * ステップカウントのインスタンスを生成する際は、{@link StepCountType#of(String, IfCommentPatternMatch)}を用いて生成してください。
 * 
 * @since 1.0
 * @version 1.0
 * @author takashi.ebina
 * 
 * @see IfStepCount
 * @see JavaStepCount
 * @see CsStepCount
 * @see SqlStepCount
 * @see StepCountFactory
 * @see AbsCommentPatternMatch
 */
public abstract class AbsStepCount implements IfStepCount {
	/** Log4J2インスタンス */
	protected final Log4J2 logger = Log4J2.getInstance();
	/** コメントパターン判定用クラス */
	protected final IfCommentPatternMatch commentPatternMatch;
	/** メソッド区分 */
	protected final MethodType methodType;

	/**
	 * <p>
	 * コンストラクタ
	 * <p>
	 * ファクトリクラス（{@link StepCountType#of}）で利用するために実装しています。<br>
	 * 本クラスを継承する際は引数なしのコンストラクタを実装してください。
	 */
	protected AbsStepCount() {
		this.commentPatternMatch = null;
		this.methodType = null;
	}

	/**
	 * <p>
	 * コンストラクタ
	 * 
	 * @param commentPatternMatch コメント判定用クラス
	 * @param methodType メソッド区分
	 * @throws IllegalArgumentException コメントパターン判定用クラスがNullの場合
	 */
	public AbsStepCount(final IfCommentPatternMatch commentPatternMatch, final MethodType methodType) {
		if (Objects.isNull(commentPatternMatch) || Objects.isNull(methodType == null)) {
			throw new IllegalArgumentException("コンストラクタの引数の値がNullです");
		}
		this.commentPatternMatch = commentPatternMatch;
		this.methodType = methodType;
	}

	/**
	 * <p>
	 * プログラムファイルのステップ数を集計するメソッド
	 * <p>
	 * プログラムファイルのステップ数の集計処理を行い、1ファイル単位の総行数／実行行数／コメント行数／空行数を集計するデータクラスを返却します。<br>
	 * {@link MethodType#DEFAULT}の場合、{@link AbsStepCount#deFaultFileReadStepCount}を実行し、<br>
	 * {@link MethodType#ORIGINAL}の場合は{@link AbsStepCount#oridinalFileReadStepCount}を実行します。<br>
	 * <p>
	 * プログラムファイル固有のステップ数集計処理を実装したい場合は、{@link MethodType#ORIGINAL}を設定し、<br>
	 * {@link AbsStepCount#oridinalFileReadStepCount}をオーバライドし独自の集計処理を実装してください。
	 * 
	 * @param inputFile ステップカウント対象ファイル
	 * @return 1ファイル単位の総行数／実行行数／コメント行数／空行数を集計するデータクラス
	 * @throws Exception プログラムファイルのステップ数集計処理で例外が発生した場合
	 */
	@Override
	public StepCountData stepCount(final File inputFile) throws Exception {
		StepCountData stepCountData = null;
		switch (this.methodType) {
			case DEFAULT  -> stepCountData = deFaultFileReadStepCount(inputFile);
			case ORIGINAL -> stepCountData = oridinalFileReadStepCount(inputFile);
		}
		return stepCountData;
	}

	/**
	 * <p>
	 * １行コメント判定メソッド
	 * <p>
	 * １行コメントを含んでいるか判定するメソッドです。
	 * <p>
	 * 実際の処理は{@link IfCommentPatternMatch#isSingleCommentPattern}に移譲しています。
	 * 
	 * @param target 検索対象の行
	 * @return １行コメントが存在する場合はtrueを返却。それ以外の場合はfalseを返却する。
	 */
	public boolean isSingleCommentPattern(final String target) {
		return commentPatternMatch.isSingleCommentPattern(target);
	}

	/**
	 * <p>
	 * 複数行コメント（開始）判定メソッド
	 * <p>
	 * 複数行コメント（開始）を含んでいるか判定するメソッドです。
	 * <p>
	 * 実際の処理は{@link IfCommentPatternMatch#isStartMultiCommentPattern}に移譲しています。
	 * 
	 * @param target 検索対象の行
	 * @return 複数行コメント（開始）が存在する場合はtrueを返却。それ以外の場合はfalseを返却する。
	 */
	public boolean isStartMultiCommentPattern(final String target) {
		return commentPatternMatch.isStartMultiCommentPattern(target);
	}

	/**
	 * <p>
	 * 複数行コメント（終了）判定メソッド
	 * <p>
	 * 複数行コメント（終了）を含んでいるか判定するメソッドです。
	 * <p>
	 * 実際の処理は{@link IfCommentPatternMatch#isEndMultiCommentPattern}に移譲しています。
	 * 
	 * @param target 検索対象の行
	 * @return 複数行コメント（終了）が存在する場合はtrueを返却。それ以外の場合はfalseを返却する。
	 */
	public boolean isEndMultiCommentPattern(final String target) {
		return commentPatternMatch.isEndMultiCommentPattern(target);
	}

	/**
	 * <p>
	 * プログラムファイルのステップ数の集計処理を行うメソッド
	 * <p>
	 * カウント対象プログラムファイルを元に、総行数／実行行数／コメント行数／空行数を集計処理を行います。<br>
	 * 共通的に利用される集計メソッドであり、{@link MethodType#DEFAULT}の場合に呼ばれます。<br>
	 * プログラムファイル独自の集計メソッドを実装したい場合は{@link AbsStepCount#oridinalFileReadStepCount}をオーバライドして呼び出すようにしてください。
	 * 
	 * @param inputFile カウント対象プログラムファイル
	 * @return 1ファイル単位の総行数／実行行数／コメント行数／空行数を集計するデータクラス
	 */
	protected final StepCountData deFaultFileReadStepCount(final File inputFile) {
		int tmpTotalStepCount = 0, tmpExecStepCount = 0,
				tmpCommentStepCount = 0, tmpEmptyStepCount = 0;
		boolean canWriteStepCount = true;
		try (BufferedReader bw = new BufferedReader(
				new InputStreamReader(new FileInputStream(inputFile), SystemConstant.CHARSET_NAME))) {
			String readLine = "";
			boolean isCommentLine = false;
			while (Objects.nonNull((readLine = bw.readLine()))) {
				final String trimReadLine = readLine.trim();
				tmpTotalStepCount++;
				// コメント行状態判定
				if (isCommentLine) {
					tmpCommentStepCount++;
					if (this.isEndMultiCommentPattern(trimReadLine)) {
						isCommentLine = false;
					}
					continue;
				}
				// 空行存在判定
				if (Objects.equals(trimReadLine, "")) {
					tmpEmptyStepCount++;
					continue;
				}
				// １行コメント存在判定
				if (this.isSingleCommentPattern(trimReadLine)) {
					tmpCommentStepCount++;
					continue;
				}
				// 複数行コメント（１行で完結する複数行コメント）存在判定
				if (this.isStartMultiCommentPattern(trimReadLine) && this.isEndMultiCommentPattern(trimReadLine)) {
					tmpCommentStepCount++;
					continue;
				}
				// 複数行コメント（複数行にまたがる複数行コメント）存在判定
				if (this.isStartMultiCommentPattern(trimReadLine) && !this.isEndMultiCommentPattern(trimReadLine)) {
					tmpCommentStepCount++;
					isCommentLine = true;
					continue;
				}
				tmpExecStepCount++;
			}
		} catch (IOException e) {
			logger.logError("StepCount countError!! [fileName]:" + inputFile.getName(), e);
			// ステップカウント処理で例外が発生した場合は、該当ファイルのステップ数の出力を行わない。
			canWriteStepCount = false;
		}
		return new StepCountData(tmpTotalStepCount, tmpExecStepCount, 
				tmpCommentStepCount, tmpEmptyStepCount, canWriteStepCount, inputFile);
	}

	/**
	 * <p>
	 * プログラムファイルのステップ数の集計処理を行うメソッド（独自処理）
	 * <p>
	 * カウント対象プログラムファイルを元に、総行数／実行行数／コメント行数／空行数を集計処理を行います。
	 * 
	 * @param inputFile カウント対象プログラムファイル
	 * @return 1ファイル単位の総行数／実行行数／コメント行数／空行数を集計するデータクラス
	 * @throws Exception 本メソッドをオーバライドせずに呼び出した場合
	 */
	protected StepCountData oridinalFileReadStepCount(final File inputFile) throws Exception {
		throw new Exception("oridinalFileReadStepCountメソッドはオーバライドして実装してください。");
	}
}