package co.jp.stepCounter.application.service;

import java.io.File;

import javax.swing.JOptionPane;

import co.jp.stepCounter.application.sharedService.StepCounterSharedService;
import co.jp.stepCounter.constant.StepCounterConstant.ProcessResult;
import co.jp.stepCounter.constant.StepCounterConstant.SortTarget;
import co.jp.stepCounter.constant.StepCounterConstant.SortType;
import co.jp.stepCounter.presentation.view.StepCounterGuiMainView;

/**
 * <p>
 * GUIでステップカウント処理を実行するサービスクラス
 * 
 * @since 1.0
 * @version 1.0
 * @author takashi.ebina
 */
public class StepCounterGuiMainService {

	/** 共通サービスクラス */
	private StepCounterSharedService sharedService;
	
	/**
	 * コンストラクタ
	 */
	public StepCounterGuiMainService() {
		sharedService = new StepCounterSharedService();
	}
	/**
	 * <p>
	 * GUIモードのステップカウントメソッド
	 * <p>
	 * [処理概要]<br>
	 * <ol>
	 * <li>カウント対象のディレクトリパスの入力チェック<br>
	 * <li>カウント結果出力対象のファイルパスの入力チェック<br>
	 * <li>カウント結果出力対象のファイルパスのCSVヘッダーに書き込む処理<br>
	 * <li>プログラムファイルのステップ数をステップカウント結果出力ファイルに書き込む処理<br>
	 * </ol>
	 * 
	 * @param inputDirectoryPath カウント対象のディレクトリ
	 * @param outputFilePath カウント結果出力対象のファイル
	 * @param parent 親画面
	 */
	public void stepCountGuiMode(final String inputDirectoryPath, final String outputFilePath ,final StepCounterGuiMainView parent) {

		final ProcessResult result = 
				sharedService.execStepCount(new File(inputDirectoryPath), new File(outputFilePath), SortType.NO_SORT, SortTarget.FILEPATH);

		if (result == ProcessResult.SUCCESS) {
			JOptionPane.showMessageDialog(parent, "ステップカウント処理が完了しました。 処理結果：正常終了");
		} else if (result == ProcessResult.FAIL) {
			JOptionPane.showMessageDialog(parent, "ステップカウント処理が完了しました。 処理結果：異常終了");	
		}
	}
}
