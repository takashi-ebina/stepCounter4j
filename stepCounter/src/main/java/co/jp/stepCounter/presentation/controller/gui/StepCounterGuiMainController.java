package co.jp.stepCounter.presentation.controller.gui;

import java.awt.Component;
import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

import javax.swing.JOptionPane;

import co.jp.stepCounter.application.service.StepCounterGuiMainService;
import co.jp.stepCounter.application.service.impl.StepCounterGuiMainServiceImpl;
import co.jp.stepCounter.constant.MessageConstant.InfoMessageDiv;
import co.jp.stepCounter.constant.StepCounterConstant.ExecuteMode;
import co.jp.stepCounter.constant.StepCounterConstant.ProcessResult;
import co.jp.stepCounter.constant.SystemConstant;
import co.jp.stepCounter.domain.model.stepCountExecutor.StepCountExecutor;
import co.jp.stepCounter.infrastructure.csvdao.StepCountCsvDao;
import co.jp.stepCounter.infrastructure.log.Log4J2;
import co.jp.stepCounter.infrastructure.messages.StepCounterMessages;
import co.jp.stepCounter.presentation.validator.ValidatorUtil;
/**
 * <p>
 * GUIでステップカウント処理を実行するコントローラクラス
 * 
 * @since 1.0
 * @version 1.0
 * @author takashi.ebina
 */
public class StepCounterGuiMainController {
	
	/** Log4J2インスタンス */
	private final Log4J2 logger = Log4J2.getInstance();
	/** StepCounerMessagesインスタンス */
	private final StepCounterMessages messages = StepCounterMessages.getInstance();
	/** GUIでステップカウント処理を実行するサービスクラス*/
	private final StepCounterGuiMainService service; 
	
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
	 * 
	 * @param dto GUIでステップカウント処理を実行する際に利用するDTOクラス
	 * @param parent ステップ数の集計を実施するGUIクラス
	 */
	public void stepCountGuiMode (final StepCounterGuiRequestDto dto, final Component parent) {
		logger.logInfo(String.format("[START]StepCounterGuiRequestDto=%s", dto.toString()));
		
		final String inputDirectoryPath = dto.getInputDirectoryPath();
		final String outputFilePath = dto.getOutputFilePath();
		
		// カウント対象のディレクトリパスの入力チェック
		List<String> errorMessageList = ValidatorUtil.inputDirectoryCheck(inputDirectoryPath);
		if (Objects.nonNull(errorMessageList) && errorMessageList.size() > 0) {
			final StringJoiner sj = new StringJoiner(SystemConstant.LINE_SEPARATOR);
			errorMessageList.stream().forEach(r -> sj.add(r));
			JOptionPane.showMessageDialog(parent, sj.toString());
			return;
		}
		// カウント結果出力対象のファイルパスの入力チェック
		errorMessageList = ValidatorUtil.outputFileCheck(outputFilePath, ExecuteMode.GUI, null);
		if (Objects.nonNull(errorMessageList) && errorMessageList.size() > 0) {
			final StringJoiner sj = new StringJoiner(SystemConstant.LINE_SEPARATOR);
			errorMessageList.stream().forEach(r -> sj.add(r));
			JOptionPane.showMessageDialog(parent, sj.toString());
			return;
		}
		// サービス呼出し
		final ProcessResult result = 
				service.execStepCount(new File(inputDirectoryPath), new File(outputFilePath), dto.getStepCountSortType(), dto.getStepCountSortTarget());
		
		JOptionPane.showMessageDialog(parent, messages.getMessageText(InfoMessageDiv.RESULT_MESSAGE.name(),result.getMessage()));
	}

}
