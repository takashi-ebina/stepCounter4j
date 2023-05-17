package co.jp.stepCounter.application.service;

import java.io.File;

import co.jp.stepCounter.application.service.impl.StepCounterCuiServiceImpl;
import co.jp.stepCounter.constant.StepCounterConstant.ProcessResult;
import co.jp.stepCounter.constant.StepCounterConstant.SortTarget;
import co.jp.stepCounter.constant.StepCounterConstant.SortType;
/**
 * <p>
 * CUIでステップカウント処理を実行するサービスのインタフェース
 * 
 * @since 1.0
 * @version 1.0
 * @author takashi.ebina
 * 
 * @see StepCounterCuiServiceImpl
 */
public interface StepCounterCuiService {
	/**
	 * <p>
	 * ステップカウント処理実行メソッド
	 * <p>
	 * 引数のカウント対象のディレクトリ、カウント結果出力対象のファイルを元にステップカウント処理を実行する。
	 * <p>
	 * 処理中に例外が発生した場合はエラーメッセージを出力し処理を終了する。
	 * 
	 * @param inputDirectory      カウント対象のディレクトリ
	 * @param outputFile          カウント結果出力対象のファイル
	 * @param stepCountSortType   ソート区分
	 * @param stepCountSortTarget ソート対象
	 * @return 処理結果。正常終了の場合は{@link ProcessResult#SUCCESS}を返却。それ以外の場合は{@link ProcessResult#FAIL}を返却する。
	 */
	public ProcessResult execStepCount(final File inputDirectory, final File outputFile,
			final SortType stepCountSortType, final SortTarget stepCountSortTarget);
}
