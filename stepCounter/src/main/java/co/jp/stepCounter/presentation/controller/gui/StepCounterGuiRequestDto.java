package co.jp.stepCounter.presentation.controller.gui;

import co.jp.stepCounter.constant.StepCounterConstant.SortTarget;
import co.jp.stepCounter.constant.StepCounterConstant.SortType;
import lombok.ToString;
import lombok.Value;

/**
 * <p>
 * GUIでステップカウント処理を実行する際に利用するDTOクラス
 * 
 * @since 1.0
 * @version 1.0
 * @author takashi.ebina
 */
@Value
@ToString
public class StepCounterGuiRequestDto {
	/** ステップカウントソート区分 */
	private SortType stepCountSortType;
	/** ステップカウントソート対象 */
	private SortTarget stepCountSortTarget;
	/** カウント対象のディレクトリパス */
	private String inputDirectoryPath;
	/** カウント結果出力対象のファイルパス */
	private String outputFilePath;
}
