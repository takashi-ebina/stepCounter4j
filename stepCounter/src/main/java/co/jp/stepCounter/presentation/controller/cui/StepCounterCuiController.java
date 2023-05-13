package co.jp.stepCounter.presentation.controller.cui;

import co.jp.stepCounter.application.service.StepCounterCuiService;

public class StepCounterCuiController {
	
	/** CUIでステップカウント処理を実行するサービスクラス*/
	private final StepCounterCuiService service; 
	
	/**
	 * コンストラクタ
	 */
	public StepCounterCuiController() {
		this.service = new StepCounterCuiService();
	}
	
	/**
	 * <p>
	 * ステップカウント処理実行メソッド
	 * <p>
	 * {@link StepCounterCuiRequestDto#stepCountExecuteMode}の値に応じてサービスクラスで呼び出されるメソッドが変動する。
	 * 
	 * @param requestDto CUIでステップカウント処理を実行する際に利用するDTOクラス
	 */
	public void executeStepCount (final StepCounterCuiRequestDto requestDto) {
		
		switch (requestDto.getStepCountExecuteMode()) {
		case HELP:
			service.printHelp();
			break;
		case INTERACTIVE:
			service.stepCountInteractiveMode();
			break;
		case SCRIPT:
			service.stepCountScriptMode(requestDto);
			break;
		default:
			System.out.println("--> ------------------------------");
			System.out.println("--> オプションの指定に問題があります。");
			System.out.println("--> ------------------------------");
			service.printHelp();
			break;
		}
	}
}
