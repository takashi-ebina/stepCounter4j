package co.jp.stepCounter.presentation.controller.gui;

import java.io.File;

import javax.swing.JOptionPane;

import co.jp.stepCounter.application.service.StepCounterGuiMainService;
import co.jp.stepCounter.application.service.impl.StepCounterGuiMainServiceImpl;
import co.jp.stepCounter.constant.StepCounterConstant.ExecuteMode;
import co.jp.stepCounter.constant.StepCounterConstant.ProcessResult;
import co.jp.stepCounter.domain.model.stepCountExecutor.StepCountExecutor;
import co.jp.stepCounter.infrastructure.csvdao.StepCountCsvDao;
import co.jp.stepCounter.presentation.validator.ValidatorUtil;
import co.jp.stepCounter.presentation.view.StepCounterGuiMainView;
/**
 * <p>
 * GUIでステップカウント処理を実行するコントローラクラス
 * 
 * @since 1.0
 * @version 1.0
 * @author takashi.ebina
 */
public class StepCounterGuiMainController {
	
	/** GUIでステップカウント処理を実行するサービスクラス*/
	private StepCounterGuiMainService service; 
	
	/**
	 * コンストラクタ
	 */
	public StepCounterGuiMainController() {
		this.service = new StepCounterGuiMainServiceImpl(new StepCountCsvDao(), new StepCountExecutor());
	}
	
	/**
	 * <p>
	 * GUIモードのステップカウントメソッド
	 * <p>
	 * [処理概要]<br>
	 * <ol>
	 * <li>カウント対象のディレクトリパスの入力チェック<br>
	 * <li>カウント結果出力対象のファイルパスの入力チェック<br>
	 * <li>カウント対象ディレクトリのステップカウント処理及びカウント結果をカウント結果出力ファイルに書き込む処理の呼出し<br>
	 * </ol>
	 */
	public void stepCountGuiMode (final StepCounterGuiRequestDto dto, final StepCounterGuiMainView parent) {
		final String inputDirectoryPath = dto.getInputDirectoryPath();
		final String outputFilePath = dto.getOutputFilePath();
		
		if (!ValidatorUtil.inputDirectoryCheck(inputDirectoryPath)) {
			JOptionPane.showMessageDialog(parent, "入力フォルダに問題があります。");
			return;
		}
		if (!ValidatorUtil.outputFileCheck(outputFilePath, ExecuteMode.GUI, null)) {
			JOptionPane.showMessageDialog(parent, "出力ファイルに問題があります。");
			return;
		}
		
		final ProcessResult result = 
				service.execStepCount(new File(inputDirectoryPath), new File(outputFilePath), dto.getStepCountSortType(), dto.getStepCountSortTarget());
		
		if (result == ProcessResult.SUCCESS) {
			JOptionPane.showMessageDialog(parent, "ステップカウント処理が完了しました。 処理結果：正常終了");
		} else if (result == ProcessResult.FAIL) {
			JOptionPane.showMessageDialog(parent, "ステップカウント処理が完了しました。 処理結果：異常終了");	
		}
	}

}
