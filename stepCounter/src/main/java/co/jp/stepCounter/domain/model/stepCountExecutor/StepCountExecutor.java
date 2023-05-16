package co.jp.stepCounter.domain.model.stepCountExecutor;

import java.io.File;
import java.util.List;

import co.jp.stepCounter.domain.model.commentPatternMatch.CommentPatternMatchFactory.CommentPatternMatchType;
import co.jp.stepCounter.domain.model.commentPatternMatch.IfCommentPatternMatch;
import co.jp.stepCounter.domain.model.stepCountDetail.IfStepCount;
import co.jp.stepCounter.domain.model.stepCountDetail.StepCountFactory.StepCountType;
import co.jp.stepCounter.domain.value.StepCountData;
import co.jp.stepCounter.infrastructure.log.Log4J2;

/**
 * <p>
 * ステップ数集計処理を実行するクラス 
 * 
 * @since 1.0
 * @version 1.0
 * @author takashi.ebina
 */
public class StepCountExecutor {
	
	/** Log4J2インスタンス */
	private final Log4J2 logger = Log4J2.getInstance();
	
	/**
	 * <p>
	 * ディレクトリに対するステップ数カウントメソッド
	 * <p>
	 * 引数のカウント対象のディレクトリに対して再帰処理を行い、ステップカウント対象の拡張子をもつファイルに対して、<br>
	 * 総行数,実行行数,コメント行数,空行数を集計したデータクラスを返却する。
	 * 
	 * @param inputDirectory    カウント対象ディレクトリ
	 * @param stepCountDatalist 1ファイル単位の総行数／実行行数／コメント行数／空行数を集計するデータクラスリスト
	 * @return stepCountDatalist 1ファイル単位の総行数／実行行数／コメント行数／空行数を集計するデータクラスリスト
	 * @throws Exception プログラムファイルのステップ数集計処理で例外が発生した場合
	 * @see CommentPatternMatchType#containsExtension
	 * @see StepCountType#containsExtension
	 * @see CommentPatternMatchType#of
	 * @see StepCountType#of
	 * @see IfCommentPatternMatch
	 * @see IfStepCount#stepCount
	 */
	public List<StepCountData> execStepCountInDirectory(final File inputDirectory, final List<StepCountData> stepCountDatalist) throws Exception {
		for (final File inputFile : inputDirectory.listFiles()) {
			if (inputFile.isDirectory()) {
				execStepCountInDirectory(inputFile, stepCountDatalist);
			} else if (inputFile.isFile()) {
				final String extension = getExtension(inputFile);
				if (isValidExtension(extension)) {
					IfStepCount stepCountObj = StepCountType.of(extension, CommentPatternMatchType.of(extension));
					logger.logInfo("ステップカウント処理開始。 ファイル名：" + inputFile.getName());
					final StepCountData stepCountData = stepCountObj.stepCount(inputFile);
					stepCountDatalist.add(stepCountData);
				} else {
					logger.logWarn("ファイルの拡張子が未対応。ステップカウント処理をスキップ。 ファイル名：" + inputFile.getName());
				}
			}
		}
		return stepCountDatalist;
	}
	/**
	 * <p>
	 * 有効拡張子判定メソッド
	 * <p>
	 * 引数のファイル拡張子がカウント対象の拡張子であるか（有効であるか）判定する。
	 * 
	 * @param extension    カウント対象のファイル拡張子
	 * @return 引数のファイル拡張子がカウント対象の拡張子の場合はtrueを返却。それ以外の場合はfalseを返却する。
	 */
	private boolean isValidExtension(final String extension) {
		return CommentPatternMatchType.containsExtension(extension) && StepCountType.containsExtension(extension);
	}
	
	/**
	 * <p>
	 * ファイル拡張子取得メソッド
	 * <p>
	 * Fileオブジェクトからファイルの拡張子を取得し返却する。
	 * 
	 * @param file Fileオブジェクト
	 * @return ファイルの拡張子を文字列型で返却する。引数のFileオブジェクトがnullの場合はnullを返却する。
	 */
	private String getExtension(final File file) {
		if (file == null) {
			return null;
		}
		final String fileName = file.getName();
		return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase().trim();
	}
}
