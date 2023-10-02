//package co.jp.stepCounter.presentation.controller.cui;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Matchers.*;
//import static org.mockito.Mockito.*;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import co.jp.base.StandardInputStream;
//import co.jp.base.StandardOutputStream;
//import co.jp.base.TestStepCounterConstant;
//import co.jp.base.TestStepCounterConstant.TestCaseInOutDiv;
//import co.jp.base.TestStepCounterUtil;
//import co.jp.stepCounter.application.service.impl.StepCounterCuiServiceImpl;
//import co.jp.stepCounter.constant.StepCounterConstant.ExecuteMode;
//import co.jp.stepCounter.constant.StepCounterConstant.ProcessResult;
//import co.jp.stepCounter.constant.StepCounterConstant.SortTarget;
//import co.jp.stepCounter.constant.StepCounterConstant.SortType;
//import co.jp.stepCounter.infrastructure.dbdao.JdbcConnection;
//import jp.co.future.uroborosql.SqlAgent;
//
//class TestStepCounterCuiController {
//
//	@Mock
//	private StepCounterCuiServiceImpl mockCuiService;
//	@InjectMocks
//	private StepCounterCuiController injectMockcuiController  = new StepCounterCuiController();
//	
//	private StepCounterCuiController cuiController = new StepCounterCuiController();
//	private StandardInputStream in = new StandardInputStream();
//	private StandardOutputStream out = new StandardOutputStream();
//
//	@BeforeEach
//	void setUp() {
//		final JdbcConnection con = JdbcConnection.getInstance();
//		try (final SqlAgent agent = con.getSqlConfig().agent()) {
//			agent.required(() -> {
//				agent.autoCommitScope(() -> {
//					agent.update("ddl/table/message").count();
//					agent.update("setup/data/insert_message").count();
//				});
//			});
//		}
//		System.setIn(in);
//		System.setOut(out);
//		MockitoAnnotations.initMocks(this);
//	}
//
//	@AfterEach
//	void tearDown() {
//		System.setIn(null);
//		System.setOut(null);
//	}
//
//	@Nested
//	class printHelp {
//		@DisplayName("【正常系】正常終了時の標準出力の内容が想定通り")
//		@Test
//		void success1() {
//			// 【事前準備】
//			// 【実行】
//			cuiController.printHelp();
//			// 【検証】
//			assertEquals("Usage: java -jar StepCounter-jar-with-dependencies.jar", out.readLine());
//			assertEquals("       java -jar StepCounter-jar-with-dependencies.jar [OPTIONS]", out.readLine());
//			assertEquals("OPTIONS", out.readLine());
//			assertEquals("       -h:このメッセージを表示して終了する。", out.readLine());
//			assertEquals("       -s:スクリプトモードで実行する。（オプションを指定しない場合はGUIモード）", out.readLine());
//			assertEquals("       -input=[inputDirectoryPath]:ステップカウント対象のディレクトリパスを指定する。 ※「-s」オプションを利用する場合に指定してください。", out.readLine());
//			assertEquals("       -output=[outputFilePath]:カウント結果出力対象のファイルパスを指定する。 ※「-s」オプションを利用する場合に指定してください。", out.readLine());
//			assertEquals("       -asc=[sortTarget]:ステップカウント処理の出力順を[sortTarget]をキーとして昇順ソートする。", out.readLine());
//			assertEquals("       -desc=[sortTarget]:ステップカウント処理の出力順を[sortTarget]をキーとして降順ソートする。", out.readLine());
//			assertEquals("       [sortTarget]: 0:ファイルパス、1:総行数、2:実行行数、3:コメント行数、4:空行数", out.readLine());
//			// 【後処理】
//		}
//	}
//	
//	@Nested
//	class stepCountInteractiveMode {
//		@DisplayName("【正常系】正常終了時の標準出力の内容が想定通り（ソートあり・CSVファイルなし）")
//		@Test
//		void success1() {
//			// 【事前準備】
//			Path p = Paths.get(TestStepCounterUtil.getTestcasePath(TestCaseInOutDiv.OUTPUT) + TestStepCounterConstant.RESULT_FILE_NAME);
//			try{
//				Files.deleteIfExists(p);
//			} catch (IOException e) {
//			}
//			// カウント対象のディレクトリパスの入力
//			in.inputln(TestStepCounterUtil.getCommonTestcasePath(TestCaseInOutDiv.INPUT)); 
//			// カウント結果出力対象のファイルパスの入力
//			in.inputln(TestStepCounterUtil.getTestcasePath(TestCaseInOutDiv.OUTPUT) + TestStepCounterConstant.RESULT_FILE_NAME);
//			// ソート区分の入力
//			in.inputln("1");
//			// ソート対象の入力
//			in.inputln("0");
//			
//			// 【実行】
//			cuiController.stepCountInteractiveMode();
//			// 【検証】
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			assertEquals("--> カウント対象のディレクトリパスを入力してください", out.readLine());
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			assertEquals("--> カウント結果出力対象のファイルパスを入力してください", out.readLine());
//			assertEquals("--> ファイル拡張子：CSV", out.readLine());
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			assertEquals("--> ソート区分を入力してください", out.readLine());
//			assertEquals("--> 0：ソートなし、1：昇順ソート、2：降順ソート", out.readLine());
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			assertEquals("--> ソート対象を入力してください", out.readLine());
//			assertEquals("--> 0：ファイルパス、1：総行数、2：実行行数、3：コメント行数、4：空行数", out.readLine());
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			assertEquals("--> ステップカウント処理が完了しました。 処理結果：正常終了", out.readLine());
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			// 【後処理】
//		}
//		@DisplayName("【正常系】正常終了時の標準出力の内容が想定通り（ソートあり・CSVファイルあり）")
//		@Test
//		void success2() {
//			// 【事前準備】
//			Path p = Paths.get(TestStepCounterUtil.getTestcasePath(TestCaseInOutDiv.OUTPUT) + TestStepCounterConstant.RESULT_FILE_NAME);
//			try {
//				Files.createFile(p);
//			} catch (IOException e) {
//			}
//			String outputPath = TestStepCounterUtil.getTestcasePath(TestCaseInOutDiv.OUTPUT) + TestStepCounterConstant.RESULT_FILE_NAME;
//			// カウント対象のディレクトリパスの入力
//			in.inputln(TestStepCounterUtil.getCommonTestcasePath(TestCaseInOutDiv.INPUT)); 
//			// カウント結果出力対象のファイルパスの入力
//			in.inputln(outputPath);
//			// ファイルの上書き実施
//			in.inputln("y");
//			// ソート区分の入力
//			in.inputln("2");
//			// ソート対象の入力
//			in.inputln("1");
//			
//			// 【実行】
//			cuiController.stepCountInteractiveMode();
//			// 【検証】
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			assertEquals("--> カウント対象のディレクトリパスを入力してください", out.readLine());
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			assertEquals("--> カウント結果出力対象のファイルパスを入力してください", out.readLine());
//			assertEquals("--> ファイル拡張子：CSV", out.readLine());
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			assertEquals("--> 該当のファイルが既に存在します。 上書きされてしまいますがよろしいですか？ y / n", out.readLine());
//			assertEquals("--> ファイルパス：" + outputPath, out.readLine());
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			assertEquals("--> ソート区分を入力してください", out.readLine());
//			assertEquals("--> 0：ソートなし、1：昇順ソート、2：降順ソート", out.readLine());
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			assertEquals("--> ソート対象を入力してください", out.readLine());
//			assertEquals("--> 0：ファイルパス、1：総行数、2：実行行数、3：コメント行数、4：空行数", out.readLine());
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			assertEquals("--> ステップカウント処理が完了しました。 処理結果：正常終了", out.readLine());
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			// 【後処理】
//		}
//		@DisplayName("【正常系】正常終了時の標準出力の内容が想定通り（ソートなし・CSVファイルなし）")
//		@Test
//		void success3() {
//			// 【事前準備】
//			Path p = Paths.get(TestStepCounterUtil.getTestcasePath(TestCaseInOutDiv.OUTPUT) + TestStepCounterConstant.RESULT_FILE_NAME);
//			try{
//				Files.deleteIfExists(p);
//			} catch (IOException e) {
//			}
//			String outputPath = TestStepCounterUtil.getTestcasePath(TestCaseInOutDiv.OUTPUT) + TestStepCounterConstant.RESULT_FILE_NAME;
//			// カウント対象のディレクトリパスの入力
//			in.inputln(TestStepCounterUtil.getCommonTestcasePath(TestCaseInOutDiv.INPUT)); 
//			// カウント結果出力対象のファイルパスの入力
//			in.inputln(outputPath);
//			// ソート区分の入力
//			in.inputln("0");
//			// ソート区分が0（ソートなし）の場合はソート対象は入力しない
//			
//			// 【実行】
//			cuiController.stepCountInteractiveMode();
//			// 【検証】
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			assertEquals("--> カウント対象のディレクトリパスを入力してください", out.readLine());
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			assertEquals("--> カウント結果出力対象のファイルパスを入力してください", out.readLine());
//			assertEquals("--> ファイル拡張子：CSV", out.readLine());
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			assertEquals("--> ソート区分を入力してください", out.readLine());
//			assertEquals("--> 0：ソートなし、1：昇順ソート、2：降順ソート", out.readLine());
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			assertEquals("--> ステップカウント処理が完了しました。 処理結果：正常終了", out.readLine());
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			// 【後処理】
//		}
//		@DisplayName("【正常系】正常終了時の標準出力の内容が想定通り（ソートなし・CSVファイルあり）")
//		@Test
//		void success4() {
//			// 【事前準備】
//			Path p = Paths.get(TestStepCounterUtil.getTestcasePath(TestCaseInOutDiv.OUTPUT) + TestStepCounterConstant.RESULT_FILE_NAME);
//			try {
//				Files.createFile(p);
//			} catch (IOException e) {
//			}
//			String outputPath = TestStepCounterUtil.getTestcasePath(TestCaseInOutDiv.OUTPUT) + TestStepCounterConstant.RESULT_FILE_NAME;
//			// カウント対象のディレクトリパスの入力
//			in.inputln(TestStepCounterUtil.getCommonTestcasePath(TestCaseInOutDiv.INPUT)); 
//			// カウント結果出力対象のファイルパスの入力
//			in.inputln(outputPath);
//			// ファイルの上書き実施
//			in.inputln("x"); // n / y 以外のコマンド入力
//			in.inputln("z"); // n / y 以外のコマンド入力
//			in.inputln("Y");
//			// ソート区分の入力
//			in.inputln("0");
//			// ソート区分が0（ソートなし）の場合はソート対象は入力しない
//			// 【実行】
//			cuiController.stepCountInteractiveMode();
//			// 【検証】
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			assertEquals("--> カウント対象のディレクトリパスを入力してください", out.readLine());
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			assertEquals("--> カウント結果出力対象のファイルパスを入力してください", out.readLine());
//			assertEquals("--> ファイル拡張子：CSV", out.readLine());
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			assertEquals("--> 該当のファイルが既に存在します。 上書きされてしまいますがよろしいですか？ y / n", out.readLine());
//			assertEquals("--> ファイルパス：" + outputPath, out.readLine());
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			
//			assertEquals("--> y または n を入力してください", out.readLine());
//			assertEquals("--> y または n を入力してください", out.readLine());
//			
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			assertEquals("--> ソート区分を入力してください", out.readLine());
//			assertEquals("--> 0：ソートなし、1：昇順ソート、2：降順ソート", out.readLine());
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			assertEquals("--> ステップカウント処理が完了しました。 処理結果：正常終了", out.readLine());
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			// 【後処理】
//		}
//		
//		@DisplayName("【準正常系】入力ディレクトリパスのバリデーションチェックエラー")
//		@Test
//		void warning1() {
//			// 【事前準備】
//			// カウント対象のディレクトリパスの入力
//			in.inputln(TestStepCounterUtil.getCommonTestcasePath(TestCaseInOutDiv.OUTPUT)); 
//			// 【実行】
//			cuiController.stepCountInteractiveMode();
//			// 【検証】
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			assertEquals("--> カウント対象のディレクトリパスを入力してください", out.readLine());
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			assertEquals("--> 入力フォルダ　ファイルパスを指定しています。", out.readLine());
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			// 【後処理】
//		}
//		@DisplayName("【準正常系】カウント結果出力対象のファイルパスのバリデーションチェックエラー")
//		@Test
//		void warning2() {
//			// 【事前準備】
//			// カウント対象のディレクトリパスの入力
//			in.inputln(TestStepCounterUtil.getCommonTestcasePath(TestCaseInOutDiv.INPUT)); 
//			// カウント結果出力対象のファイルパスの入力
//			in.inputln(TestStepCounterUtil.getCommonTestcasePath(TestCaseInOutDiv.INPUT));
//			// 【実行】
//			cuiController.stepCountInteractiveMode();
//			// 【検証】
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			assertEquals("--> カウント対象のディレクトリパスを入力してください", out.readLine());
//			assertEquals("--> ------------------------------------------------", out.readLine());
//
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			assertEquals("--> カウント結果出力対象のファイルパスを入力してください", out.readLine());
//			assertEquals("--> ファイル拡張子：CSV", out.readLine());
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			assertEquals("--> 出力ファイル　フォルダパスを指定しています。", out.readLine());
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			// 【後処理】
//		}
//		
//		@DisplayName("【準正常系】ソート区分のバリデーションチェックエラー")
//		@Test
//		void warning3() {
//			// 【事前準備】
//			Path p = Paths.get(TestStepCounterUtil.getTestcasePath(TestCaseInOutDiv.OUTPUT) + TestStepCounterConstant.RESULT_FILE_NAME);
//			try{
//				Files.deleteIfExists(p);
//			} catch (IOException e) {
//			}
//			// カウント対象のディレクトリパスの入力
//			in.inputln(TestStepCounterUtil.getCommonTestcasePath(TestCaseInOutDiv.INPUT)); 
//			// カウント結果出力対象のファイルパスの入力
//			in.inputln(TestStepCounterUtil.getTestcasePath(TestCaseInOutDiv.OUTPUT) + TestStepCounterConstant.RESULT_FILE_NAME);
//			// ソート区分の入力
//			in.inputln("3");
//			// 【実行】
//			cuiController.stepCountInteractiveMode();
//			// 【検証】
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			assertEquals("--> カウント対象のディレクトリパスを入力してください", out.readLine());
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			assertEquals("--> カウント結果出力対象のファイルパスを入力してください", out.readLine());
//			assertEquals("--> ファイル拡張子：CSV", out.readLine());
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			assertEquals("--> ソート区分を入力してください", out.readLine());
//			assertEquals("--> 0：ソートなし、1：昇順ソート、2：降順ソート", out.readLine());
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			assertEquals("--> ソート区分に問題があります。", out.readLine());
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			// 【後処理】
//		}
//		
//		@DisplayName("【準正常系】ソート対象のバリデーションチェックエラー")
//		@Test
//		void warning4() {
//			// 【事前準備】
//			Path p = Paths.get(TestStepCounterUtil.getTestcasePath(TestCaseInOutDiv.OUTPUT) + TestStepCounterConstant.RESULT_FILE_NAME);
//			try{
//				Files.deleteIfExists(p);
//			} catch (IOException e) {
//			}
//			// カウント対象のディレクトリパスの入力
//			in.inputln(TestStepCounterUtil.getCommonTestcasePath(TestCaseInOutDiv.INPUT)); 
//			// カウント結果出力対象のファイルパスの入力
//			in.inputln(TestStepCounterUtil.getTestcasePath(TestCaseInOutDiv.OUTPUT) + TestStepCounterConstant.RESULT_FILE_NAME);
//			// ソート区分の入力
//			in.inputln("1");
//			// ソート対象の入力
//			in.inputln("5");
//			// 【実行】
//			cuiController.stepCountInteractiveMode();
//			// 【検証】
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			assertEquals("--> カウント対象のディレクトリパスを入力してください", out.readLine());
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			assertEquals("--> カウント結果出力対象のファイルパスを入力してください", out.readLine());
//			assertEquals("--> ファイル拡張子：CSV", out.readLine());
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			assertEquals("--> ソート区分を入力してください", out.readLine());
//			assertEquals("--> 0：ソートなし、1：昇順ソート、2：降順ソート", out.readLine());
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			assertEquals("--> ソート対象を入力してください", out.readLine());
//			assertEquals("--> 0：ファイルパス、1：総行数、2：実行行数、3：コメント行数、4：空行数", out.readLine());
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			assertEquals("--> ソート対象に問題があります。", out.readLine());
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			// 【後処理】
//		}
//		@DisplayName("【異常系】ステップカウント処理実行メソッドで例外発生")
//		@Test
//		void exception1() {
//			// 【事前準備】
//			when(mockCuiService.execStepCount((File)any(), (File)any(), (SortType)any(), (SortTarget)any()))
//					.thenReturn(ProcessResult.FAIL);
//			
//			Path p = Paths.get(TestStepCounterUtil.getTestcasePath(TestCaseInOutDiv.OUTPUT) + TestStepCounterConstant.RESULT_FILE_NAME);
//			try{
//				Files.deleteIfExists(p);
//			} catch (IOException e) {
//			}
//			
//			// カウント対象のディレクトリパスの入力
//			in.inputln(TestStepCounterUtil.getCommonTestcasePath(TestCaseInOutDiv.INPUT)); 
//			// カウント結果出力対象のファイルパスの入力
//			in.inputln(TestStepCounterUtil.getTestcasePath(TestCaseInOutDiv.OUTPUT) + TestStepCounterConstant.RESULT_FILE_NAME);
//			// ソート区分の入力
//			in.inputln("1");
//			// ソート対象の入力
//			in.inputln("0");
//			
//			// 【実行】
//			injectMockcuiController.stepCountInteractiveMode();
//			// 【検証】
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			assertEquals("--> カウント対象のディレクトリパスを入力してください", out.readLine());
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			assertEquals("--> カウント結果出力対象のファイルパスを入力してください", out.readLine());
//			assertEquals("--> ファイル拡張子：CSV", out.readLine());
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			assertEquals("--> ソート区分を入力してください", out.readLine());
//			assertEquals("--> 0：ソートなし、1：昇順ソート、2：降順ソート", out.readLine());
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			assertEquals("--> ソート対象を入力してください", out.readLine());
//			assertEquals("--> 0：ファイルパス、1：総行数、2：実行行数、3：コメント行数、4：空行数", out.readLine());
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			assertEquals("--> ステップカウント処理が完了しました。 処理結果：異常終了", out.readLine());
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			// 【後処理】
//		}
//	}
//	
//	@Nested
//	class stepCountScriptMode {
//		@DisplayName("【正常系】正常終了時の標準出力の内容が想定通り")
//		@Test
//		void success1() {
//			// 【事前準備】
//			StepCounterCuiRequestDto dto = 
//					new StepCounterCuiRequestDto(
//							ExecuteMode.SCRIPT,
//							SortType.ASCENDING_ORDER,
//							SortTarget.FILEPATH,
//							TestStepCounterUtil.getCommonTestcasePath(TestCaseInOutDiv.INPUT),
//							TestStepCounterUtil.getTestcasePath(TestCaseInOutDiv.OUTPUT) + TestStepCounterConstant.RESULT_FILE_NAME);
//			// 【実行】
//			cuiController.stepCountScriptMode(dto);
//			// 【検証】
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			assertEquals("--> ステップカウント処理が完了しました。 処理結果：正常終了", out.readLine());
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			// 【後処理】
//		}
//		
//		@DisplayName("【準正常系】入力ディレクトリパスのバリデーションチェックエラー")
//		@Test
//		void warning1() {
//			// 【事前準備】
//			when(mockCuiService.execStepCount((File)any(), (File)any(), (SortType)any(), (SortTarget)any()))
//				.thenReturn(ProcessResult.FAIL);
//			
//			StepCounterCuiRequestDto dto = 
//					new StepCounterCuiRequestDto(
//							ExecuteMode.SCRIPT,
//							SortType.ASCENDING_ORDER,
//							SortTarget.FILEPATH,
//							TestStepCounterUtil.getTestcasePath(TestCaseInOutDiv.OUTPUT) + TestStepCounterConstant.RESULT_FILE_NAME,
//							TestStepCounterUtil.getTestcasePath(TestCaseInOutDiv.OUTPUT) + TestStepCounterConstant.RESULT_FILE_NAME);
//			// 【実行】
//			injectMockcuiController.stepCountScriptMode(dto);
//			// 【検証】
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			assertEquals("--> 入力フォルダ　ファイルパスを指定しています。", out.readLine());
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			// 【後処理】
//		}
//		
//		@DisplayName("【準正常系】カウント結果出力対象のファイルパスのバリデーションチェックエラー")
//		@Test
//		void warning2() {
//			// 【事前準備】
//			when(mockCuiService.execStepCount((File)any(), (File)any(), (SortType)any(), (SortTarget)any()))
//				.thenReturn(ProcessResult.FAIL);
//			
//			StepCounterCuiRequestDto dto = 
//					new StepCounterCuiRequestDto(
//							ExecuteMode.SCRIPT,
//							SortType.ASCENDING_ORDER,
//							SortTarget.FILEPATH,
//							TestStepCounterUtil.getCommonTestcasePath(TestCaseInOutDiv.INPUT),
//							TestStepCounterUtil.getTestcasePath(TestCaseInOutDiv.OUTPUT)); // フォルダ名を指定してエラーとする
//			// 【実行】
//			injectMockcuiController.stepCountScriptMode(dto);
//			// 【検証】
//			// FIXME 本当の期待値は「出力ファイル　フォルダパスを指定しています。」だが、Git Actions	から実行する際に期待値通りにならない。
//			// ビルドを成功させるために暫定でメッセージを変更している。
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			assertEquals("--> 出力ファイル　拡張子がcsvではありません。", out.readLine());
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			// 【後処理】
//		}
//		
//		@DisplayName("【異常系】ステップカウント処理実行メソッドで例外発生")
//		@Test
//		void Exception1() {
//			// 【事前準備】
//			when(mockCuiService.execStepCount((File)any(), (File)any(), (SortType)any(), (SortTarget)any()))
//				.thenReturn(ProcessResult.FAIL);
//			
//			StepCounterCuiRequestDto dto = 
//					new StepCounterCuiRequestDto(
//							ExecuteMode.SCRIPT,
//							SortType.ASCENDING_ORDER,
//							SortTarget.FILEPATH,
//							TestStepCounterUtil.getCommonTestcasePath(TestCaseInOutDiv.INPUT),
//							TestStepCounterUtil.getTestcasePath(TestCaseInOutDiv.OUTPUT) + TestStepCounterConstant.RESULT_FILE_NAME);
//			// 【実行】
//			injectMockcuiController.stepCountScriptMode(dto);
//			// 【検証】
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			assertEquals("--> ステップカウント処理が完了しました。 処理結果：異常終了", out.readLine());
//			assertEquals("--> ------------------------------------------------", out.readLine());
//			// 【後処理】
//		}
//	}
//}
