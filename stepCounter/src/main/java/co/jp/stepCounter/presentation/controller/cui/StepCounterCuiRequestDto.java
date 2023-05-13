package co.jp.stepCounter.presentation.controller.cui;

import co.jp.stepCounter.constant.StepCounterConstant.ExecuteMode;
import co.jp.stepCounter.constant.StepCounterConstant.SortTarget;
import co.jp.stepCounter.constant.StepCounterConstant.SortType;
import lombok.Value;
/**
 * <p>
 * CUIでステップカウント処理を実行する際に利用するDTOクラス
 * 
 * @since 1.0
 * @version 1.0
 * @author takashi.ebina
 */
@Value
public class StepCounterCuiRequestDto {
	/** ステップカウント処理実行モード */
	private ExecuteMode stepCountExecuteMode;
	/** ステップカウントソート区分 */
	private SortType stepCountSortType;
	/** ステップカウントソート対象 */
	private SortTarget stepCountSortTarget;
	/** カウント対象のディレクトリパス */
	private String inputDirectoryPath;
	/** カウント結果出力対象のファイルパス */
	private String outputFilePath;
}
