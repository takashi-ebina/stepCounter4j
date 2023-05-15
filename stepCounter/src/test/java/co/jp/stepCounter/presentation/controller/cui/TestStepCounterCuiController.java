package co.jp.stepCounter.presentation.controller.cui;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import co.jp.base.StandardInputStream;
import co.jp.base.StandardOutputStream;
import co.jp.stepCounter.application.service.StepCounterCuiService;
import co.jp.stepCounter.constant.StepCounterConstant.ExecuteMode;
import co.jp.stepCounter.constant.StepCounterConstant.ProcessResult;
import co.jp.stepCounter.constant.StepCounterConstant.SortTarget;
import co.jp.stepCounter.constant.StepCounterConstant.SortType;

class TestStepCounterCuiController {

	@Mock
	private StepCounterCuiService mockCuiService;
	@InjectMocks
	private StepCounterCuiController injectMockcuiController  = new StepCounterCuiController();
	
	private StepCounterCuiController cuiController = new StepCounterCuiController();
	private StandardInputStream in = new StandardInputStream();
	private StandardOutputStream out = new StandardOutputStream();

	@BeforeEach
	void setUp() {
		System.setIn(in);
		System.setOut(out);
		MockitoAnnotations.initMocks(this);
	}

	@AfterEach
	void tearDown() {
		System.setIn(null);
		System.setOut(null);
	}

	@Nested
	class printHelp {
		@DisplayName("【正常系】正常終了時の標準出力の内容が想定通り")
		@Test
		void success1() {
			// 【事前準備】
			// 【実行】
			cuiController.printHelp();
			// 【検証】
			assertEquals("Usage: java StepCounter", out.readLine());
			assertEquals("       java StepCounter [OPTIONS]", out.readLine());
			assertEquals("OPTIONS", out.readLine());
			assertEquals("       -h:このメッセージを表示して終了する。", out.readLine());
			assertEquals("       -s:スクリプトモードで実行する。（オプションを指定しない場合はGUIモード）", out.readLine());
			assertEquals("       -input=[inputDirectoryPath]:ステップカウント対象のディレクトリパスを指定する。 ※「-s」オプションを利用する場合に指定してください。", out.readLine());
			assertEquals("       -output=[outputFilePath]:カウント結果出力対象のファイルパスを指定する。 ※「-s」オプションを利用する場合に指定してください。", out.readLine());
			assertEquals("       -asc=[sortTarget]:ステップカウント処理の出力順を[sortTarget]をキーとして昇順ソートする。", out.readLine());
			assertEquals("       -desc=[sortTarget]:ステップカウント処理の出力順を[sortTarget]をキーとして降順ソートする。", out.readLine());
			assertEquals("       [sortTarget]: 0:ファイルパス、1:総行数、2:実行行数、3:コメント行数、4:空行数", out.readLine());
			// 【後処理】
		}
	}
	
	@Nested
	class stepCountInteractiveMode {
		@DisplayName("【正常系】正常終了時の標準出力の内容が想定通り（ソートあり・CSVファイルなし）")
		@Test
		void success1() {
			// 【事前準備】
			Path p = Paths.get("/Users/takashi.ebina/git/repository2/stepCounter/var/output/result.csv");
			try{
				Files.deleteIfExists(p);
			} catch (IOException e) {
			}
			
			// TODO 相対パスで取れるようにしたい
			// カウント対象のディレクトリパスの入力
			in.inputln("/Users/takashi.ebina/git/repository2/stepCounter/var/input"); 
			// カウント結果出力対象のファイルパスの入力
			in.inputln("/Users/takashi.ebina/git/repository2/stepCounter/var/output/result.csv");
			// ソート区分の入力
			in.inputln("1");
			// ソート対象の入力
			in.inputln("0");
			
			// 【実行】
			cuiController.stepCountInteractiveMode();
			// 【検証】
			assertEquals("--> ------------------------------------------------", out.readLine());
			assertEquals("--> カウント対象のディレクトリパスを入力してください", out.readLine());
			assertEquals("--> ------------------------------------------------", out.readLine());
			
			assertEquals("--> ------------------------------------------------", out.readLine());
			assertEquals("--> カウント結果出力対象のファイルパスを入力してください", out.readLine());
			assertEquals("--> ファイル拡張子：CSV", out.readLine());
			assertEquals("--> ------------------------------------------------", out.readLine());
			
			assertEquals("--> ------------------------------------------------", out.readLine());
			assertEquals("--> ソート区分を入力してください", out.readLine());
			assertEquals("--> 0：ソートなし、1：昇順ソート、2：降順ソート", out.readLine());
			assertEquals("--> ------------------------------------------------", out.readLine());
			
			assertEquals("--> ------------------------------------------------", out.readLine());
			assertEquals("--> ソート対象を入力してください", out.readLine());
			assertEquals("--> 0：ファイルパス、1：総行数、2：実行行数、3：コメント行数、4：空行数", out.readLine());
			assertEquals("--> ------------------------------------------------", out.readLine());
			
			assertEquals("--> ------------------------------------------------", out.readLine());
			assertEquals("--> ステップカウント処理が完了しました。 処理結果：正常終了", out.readLine());
			assertEquals("--> ------------------------------------------------", out.readLine());
			// 【後処理】
		}
		@DisplayName("【正常系】正常終了時の標準出力の内容が想定通り（ソートあり・CSVファイルあり）")
		@Test
		void success2() {
			// 【事前準備】
			Path p = Paths.get("/Users/takashi.ebina/git/repository2/stepCounter/var/output/result.csv");
			try {
				Files.createFile(p);
			} catch (IOException e) {
			}
			String outputPath = "/Users/takashi.ebina/git/repository2/stepCounter/var/output/result.csv";
			// TODO 相対パスで取れるようにしたい
			// カウント対象のディレクトリパスの入力
			in.inputln("/Users/takashi.ebina/git/repository2/stepCounter/var/input"); 
			// カウント結果出力対象のファイルパスの入力
			in.inputln(outputPath);
			// ファイルの上書き実施
			in.inputln("y");
			// ソート区分の入力
			in.inputln("2");
			// ソート対象の入力
			in.inputln("1");
			
			// 【実行】
			cuiController.stepCountInteractiveMode();
			// 【検証】
			assertEquals("--> ------------------------------------------------", out.readLine());
			assertEquals("--> カウント対象のディレクトリパスを入力してください", out.readLine());
			assertEquals("--> ------------------------------------------------", out.readLine());
			
			assertEquals("--> ------------------------------------------------", out.readLine());
			assertEquals("--> カウント結果出力対象のファイルパスを入力してください", out.readLine());
			assertEquals("--> ファイル拡張子：CSV", out.readLine());
			assertEquals("--> ------------------------------------------------", out.readLine());
			
			assertEquals("--> ------------------------------------------------", out.readLine());
			assertEquals("--> 該当のファイルが既に存在します。 上書きされてしまいますがよろしいですか？ y / n", out.readLine());
			assertEquals("--> ファイルパス：" + outputPath, out.readLine());
			assertEquals("--> ------------------------------------------------", out.readLine());
			
			assertEquals("--> ------------------------------------------------", out.readLine());
			assertEquals("--> ソート区分を入力してください", out.readLine());
			assertEquals("--> 0：ソートなし、1：昇順ソート、2：降順ソート", out.readLine());
			assertEquals("--> ------------------------------------------------", out.readLine());
			
			assertEquals("--> ------------------------------------------------", out.readLine());
			assertEquals("--> ソート対象を入力してください", out.readLine());
			assertEquals("--> 0：ファイルパス、1：総行数、2：実行行数、3：コメント行数、4：空行数", out.readLine());
			assertEquals("--> ------------------------------------------------", out.readLine());
			
			assertEquals("--> ------------------------------------------------", out.readLine());
			assertEquals("--> ステップカウント処理が完了しました。 処理結果：正常終了", out.readLine());
			assertEquals("--> ------------------------------------------------", out.readLine());
			// 【後処理】
		}
		@DisplayName("【正常系】正常終了時の標準出力の内容が想定通り（ソートなし・CSVファイルなし）")
		@Test
		void success3() {
			// 【事前準備】
			Path p = Paths.get("/Users/takashi.ebina/git/repository2/stepCounter/var/output/result.csv");
			try{
				Files.deleteIfExists(p);
			} catch (IOException e) {
			}
			String outputPath = "/Users/takashi.ebina/git/repository2/stepCounter/var/output/result.csv";
			// TODO 相対パスで取れるようにしたい
			// カウント対象のディレクトリパスの入力
			in.inputln("/Users/takashi.ebina/git/repository2/stepCounter/var/input"); 
			// カウント結果出力対象のファイルパスの入力
			in.inputln(outputPath);
			// ソート区分の入力
			in.inputln("0");
			// ソート区分が0（ソートなし）の場合はソート対象は入力しない
			
			// 【実行】
			cuiController.stepCountInteractiveMode();
			// 【検証】
			assertEquals("--> ------------------------------------------------", out.readLine());
			assertEquals("--> カウント対象のディレクトリパスを入力してください", out.readLine());
			assertEquals("--> ------------------------------------------------", out.readLine());
			
			assertEquals("--> ------------------------------------------------", out.readLine());
			assertEquals("--> カウント結果出力対象のファイルパスを入力してください", out.readLine());
			assertEquals("--> ファイル拡張子：CSV", out.readLine());
			assertEquals("--> ------------------------------------------------", out.readLine());
			
			assertEquals("--> ------------------------------------------------", out.readLine());
			assertEquals("--> ソート区分を入力してください", out.readLine());
			assertEquals("--> 0：ソートなし、1：昇順ソート、2：降順ソート", out.readLine());
			assertEquals("--> ------------------------------------------------", out.readLine());
			
			assertEquals("--> ------------------------------------------------", out.readLine());
			assertEquals("--> ステップカウント処理が完了しました。 処理結果：正常終了", out.readLine());
			assertEquals("--> ------------------------------------------------", out.readLine());
			// 【後処理】
		}
		@DisplayName("【正常系】正常終了時の標準出力の内容が想定通り（ソートなし・CSVファイルあり）")
		@Test
		void success4() {
			// 【事前準備】
			Path p = Paths.get("/Users/takashi.ebina/git/repository2/stepCounter/var/output/result.csv");
			try {
				Files.createFile(p);
			} catch (IOException e) {
			}
			String outputPath = "/Users/takashi.ebina/git/repository2/stepCounter/var/output/result.csv";
			// TODO 相対パスで取れるようにしたい
			// カウント対象のディレクトリパスの入力
			in.inputln("/Users/takashi.ebina/git/repository2/stepCounter/var/input"); 
			// カウント結果出力対象のファイルパスの入力
			in.inputln(outputPath);
			// ファイルの上書き実施
			in.inputln("x"); // n / y 以外のコマンド入力
			in.inputln("z"); // n / y 以外のコマンド入力
			in.inputln("Y");
			// ソート区分の入力
			in.inputln("0");
			// ソート区分が0（ソートなし）の場合はソート対象は入力しない
			// 【実行】
			cuiController.stepCountInteractiveMode();
			// 【検証】
			assertEquals("--> ------------------------------------------------", out.readLine());
			assertEquals("--> カウント対象のディレクトリパスを入力してください", out.readLine());
			assertEquals("--> ------------------------------------------------", out.readLine());
			
			assertEquals("--> ------------------------------------------------", out.readLine());
			assertEquals("--> カウント結果出力対象のファイルパスを入力してください", out.readLine());
			assertEquals("--> ファイル拡張子：CSV", out.readLine());
			assertEquals("--> ------------------------------------------------", out.readLine());
			
			assertEquals("--> ------------------------------------------------", out.readLine());
			assertEquals("--> 該当のファイルが既に存在します。 上書きされてしまいますがよろしいですか？ y / n", out.readLine());
			assertEquals("--> ファイルパス：" + outputPath, out.readLine());
			assertEquals("--> ------------------------------------------------", out.readLine());
			
			assertEquals("--> y または n を入力してください", out.readLine());
			assertEquals("--> y または n を入力してください", out.readLine());
			
			assertEquals("--> ------------------------------------------------", out.readLine());
			assertEquals("--> ソート区分を入力してください", out.readLine());
			assertEquals("--> 0：ソートなし、1：昇順ソート、2：降順ソート", out.readLine());
			assertEquals("--> ------------------------------------------------", out.readLine());
			
			assertEquals("--> ------------------------------------------------", out.readLine());
			assertEquals("--> ステップカウント処理が完了しました。 処理結果：正常終了", out.readLine());
			assertEquals("--> ------------------------------------------------", out.readLine());
			// 【後処理】
		}
		
		@DisplayName("【準正常系】入力ディレクトリパスのバリデーションチェックエラー")
		@Test
		void warning1() {
			// 【事前準備】
			// TODO 相対パスで取れるようにしたい
			// カウント対象のディレクトリパスの入力
			in.inputln(""); 
			// カウント結果出力対象のファイルパスの入力
			in.inputln("/Users/takashi.ebina/git/repository2/stepCounter/var/output/result.csv");
			// 【実行】
			cuiController.stepCountInteractiveMode();
			// 【検証】
			assertEquals("--> ------------------------------------------------", out.readLine());
			assertEquals("--> カウント対象のディレクトリパスを入力してください", out.readLine());
			assertEquals("--> ------------------------------------------------", out.readLine());
			
			assertEquals("--> ------------------------------------------------", out.readLine());
			assertEquals("--> 入力フォルダに問題があります。", out.readLine());
			assertEquals("--> ------------------------------------------------", out.readLine());
			// 【後処理】
		}
		@DisplayName("【準正常系】カウント結果出力対象のファイルパスのバリデーションチェックエラー")
		@Test
		void warning2() {
			// 【事前準備】
			// TODO 相対パスで取れるようにしたい
			// カウント対象のディレクトリパスの入力
			in.inputln("/Users/takashi.ebina/git/repository2/stepCounter/var/input"); 
			// カウント結果出力対象のファイルパスの入力
			in.inputln("/Users/takashi.ebina/git/repository2/stepCounter/var/input");
			// 【実行】
			cuiController.stepCountInteractiveMode();
			// 【検証】
			assertEquals("--> ------------------------------------------------", out.readLine());
			assertEquals("--> カウント対象のディレクトリパスを入力してください", out.readLine());
			assertEquals("--> ------------------------------------------------", out.readLine());

			assertEquals("--> ------------------------------------------------", out.readLine());
			assertEquals("--> カウント結果出力対象のファイルパスを入力してください", out.readLine());
			assertEquals("--> ファイル拡張子：CSV", out.readLine());
			assertEquals("--> ------------------------------------------------", out.readLine());
			
			assertEquals("--> ------------------------------------------------", out.readLine());
			assertEquals("--> 出力ファイルに問題があります。", out.readLine());
			assertEquals("--> ------------------------------------------------", out.readLine());
			// 【後処理】
		}
		
		@DisplayName("【準正常系】ソート区分のバリデーションチェックエラー")
		@Test
		void warning3() {
			// 【事前準備】
			Path p = Paths.get("/Users/takashi.ebina/git/repository2/stepCounter/var/output/result.csv");
			try{
				Files.deleteIfExists(p);
			} catch (IOException e) {
			}
			// TODO 相対パスで取れるようにしたい
			// カウント対象のディレクトリパスの入力
			in.inputln("/Users/takashi.ebina/git/repository2/stepCounter/var/input"); 
			// カウント結果出力対象のファイルパスの入力
			in.inputln("/Users/takashi.ebina/git/repository2/stepCounter/var/output/result.csv");
			// ソート区分の入力
			in.inputln("3");
			// 【実行】
			cuiController.stepCountInteractiveMode();
			// 【検証】
			assertEquals("--> ------------------------------------------------", out.readLine());
			assertEquals("--> カウント対象のディレクトリパスを入力してください", out.readLine());
			assertEquals("--> ------------------------------------------------", out.readLine());
			
			assertEquals("--> ------------------------------------------------", out.readLine());
			assertEquals("--> カウント結果出力対象のファイルパスを入力してください", out.readLine());
			assertEquals("--> ファイル拡張子：CSV", out.readLine());
			assertEquals("--> ------------------------------------------------", out.readLine());
			
			assertEquals("--> ------------------------------------------------", out.readLine());
			assertEquals("--> ソート区分を入力してください", out.readLine());
			assertEquals("--> 0：ソートなし、1：昇順ソート、2：降順ソート", out.readLine());
			assertEquals("--> ------------------------------------------------", out.readLine());
			
			assertEquals("--> ------------------------------------------------", out.readLine());
			assertEquals("--> ソート区分に問題があります。", out.readLine());
			assertEquals("--> ------------------------------------------------", out.readLine());
			// 【後処理】
		}
		
		@DisplayName("【準正常系】ソート対象のバリデーションチェックエラー")
		@Test
		void warning4() {
			// 【事前準備】
			Path p = Paths.get("/Users/takashi.ebina/git/repository2/stepCounter/var/output/result.csv");
			try{
				Files.deleteIfExists(p);
			} catch (IOException e) {
			}
			// TODO 相対パスで取れるようにしたい
			// カウント対象のディレクトリパスの入力
			in.inputln("/Users/takashi.ebina/git/repository2/stepCounter/var/input"); 
			// カウント結果出力対象のファイルパスの入力
			in.inputln("/Users/takashi.ebina/git/repository2/stepCounter/var/output/result.csv");
			// ソート区分の入力
			in.inputln("1");
			// ソート対象の入力
			in.inputln("5");
			// 【実行】
			cuiController.stepCountInteractiveMode();
			// 【検証】
			assertEquals("--> ------------------------------------------------", out.readLine());
			assertEquals("--> カウント対象のディレクトリパスを入力してください", out.readLine());
			assertEquals("--> ------------------------------------------------", out.readLine());
			
			assertEquals("--> ------------------------------------------------", out.readLine());
			assertEquals("--> カウント結果出力対象のファイルパスを入力してください", out.readLine());
			assertEquals("--> ファイル拡張子：CSV", out.readLine());
			assertEquals("--> ------------------------------------------------", out.readLine());
			
			assertEquals("--> ------------------------------------------------", out.readLine());
			assertEquals("--> ソート区分を入力してください", out.readLine());
			assertEquals("--> 0：ソートなし、1：昇順ソート、2：降順ソート", out.readLine());
			assertEquals("--> ------------------------------------------------", out.readLine());
			
			assertEquals("--> ------------------------------------------------", out.readLine());
			assertEquals("--> ソート対象を入力してください", out.readLine());
			assertEquals("--> 0：ファイルパス、1：総行数、2：実行行数、3：コメント行数、4：空行数", out.readLine());
			assertEquals("--> ------------------------------------------------", out.readLine());
			
			assertEquals("--> ------------------------------------------------", out.readLine());
			assertEquals("--> ソート対象に問題があります。", out.readLine());
			assertEquals("--> ------------------------------------------------", out.readLine());
			// 【後処理】
		}
		@DisplayName("【異常系】ステップカウント処理実行メソッドで例外発生")
		@Test
		void exception1() {
			// 【事前準備】
			when(mockCuiService.execStepCount((File)any(), (File)any(), (SortType)any(), (SortTarget)any()))
					.thenReturn(ProcessResult.FAIL);
			
			Path p = Paths.get("/Users/takashi.ebina/git/repository2/stepCounter/var/output/result.csv");
			try{
				Files.deleteIfExists(p);
			} catch (IOException e) {
			}
			
			// TODO 相対パスで取れるようにしたい
			// カウント対象のディレクトリパスの入力
			in.inputln("/Users/takashi.ebina/git/repository2/stepCounter/var/input"); 
			// カウント結果出力対象のファイルパスの入力
			in.inputln("/Users/takashi.ebina/git/repository2/stepCounter/var/output/result.csv");
			// ソート区分の入力
			in.inputln("1");
			// ソート対象の入力
			in.inputln("0");
			
			// 【実行】
			injectMockcuiController.stepCountInteractiveMode();
			// 【検証】
			assertEquals("--> ------------------------------------------------", out.readLine());
			assertEquals("--> カウント対象のディレクトリパスを入力してください", out.readLine());
			assertEquals("--> ------------------------------------------------", out.readLine());
			
			assertEquals("--> ------------------------------------------------", out.readLine());
			assertEquals("--> カウント結果出力対象のファイルパスを入力してください", out.readLine());
			assertEquals("--> ファイル拡張子：CSV", out.readLine());
			assertEquals("--> ------------------------------------------------", out.readLine());
			
			assertEquals("--> ------------------------------------------------", out.readLine());
			assertEquals("--> ソート区分を入力してください", out.readLine());
			assertEquals("--> 0：ソートなし、1：昇順ソート、2：降順ソート", out.readLine());
			assertEquals("--> ------------------------------------------------", out.readLine());
			
			assertEquals("--> ------------------------------------------------", out.readLine());
			assertEquals("--> ソート対象を入力してください", out.readLine());
			assertEquals("--> 0：ファイルパス、1：総行数、2：実行行数、3：コメント行数、4：空行数", out.readLine());
			assertEquals("--> ------------------------------------------------", out.readLine());
			
			assertEquals("--> ------------------------------------------------", out.readLine());
			assertEquals("--> ステップカウント処理が完了しました。 処理結果：異常終了", out.readLine());
			assertEquals("--> ------------------------------------------------", out.readLine());
			// 【後処理】
		}
	}
	
	@Nested
	class stepCountScriptMode {
		@DisplayName("【正常系】正常終了時の標準出力の内容が想定通り")
		@Test
		void success1() {
			// 【事前準備】
			StepCounterCuiRequestDto dto = 
					new StepCounterCuiRequestDto(
							ExecuteMode.SCRIPT,
							SortType.ASCENDING_ORDER,
							SortTarget.FILEPATH,
							"/Users/takashi.ebina/git/repository2/stepCounter/var/input",
							"/Users/takashi.ebina/git/repository2/stepCounter/var/output/result.csv");
			// 【実行】
			cuiController.stepCountScriptMode(dto);
			// 【検証】
			
			assertEquals("--> ------------------------------------------------", out.readLine());
			assertEquals("--> ステップカウント処理が完了しました。 処理結果：正常終了", out.readLine());
			assertEquals("--> ------------------------------------------------", out.readLine());
			// 【後処理】
		}
		
		@DisplayName("【準正常系】入力ディレクトリパスのバリデーションチェックエラー")
		@Test
		void warning1() {
			// 【事前準備】
			when(mockCuiService.execStepCount((File)any(), (File)any(), (SortType)any(), (SortTarget)any()))
				.thenReturn(ProcessResult.FAIL);
			
			StepCounterCuiRequestDto dto = 
					new StepCounterCuiRequestDto(
							ExecuteMode.SCRIPT,
							SortType.ASCENDING_ORDER,
							SortTarget.FILEPATH,
							"/Users/takashi.ebina/git/repository2/stepCounter/var/input/result.csv",
							"/Users/takashi.ebina/git/repository2/stepCounter/var/output/result.csv");
			// 【実行】
			injectMockcuiController.stepCountScriptMode(dto);
			// 【検証】
			assertEquals("--> ------------------------------------------------", out.readLine());
			assertEquals("--> 入力フォルダに問題があります。", out.readLine());
			assertEquals("--> ------------------------------------------------", out.readLine());
			// 【後処理】
		}
		
		@DisplayName("【準正常系】カウント結果出力対象のファイルパスのバリデーションチェックエラー")
		@Test
		void warning2() {
			// 【事前準備】
			when(mockCuiService.execStepCount((File)any(), (File)any(), (SortType)any(), (SortTarget)any()))
				.thenReturn(ProcessResult.FAIL);
			
			StepCounterCuiRequestDto dto = 
					new StepCounterCuiRequestDto(
							ExecuteMode.SCRIPT,
							SortType.ASCENDING_ORDER,
							SortTarget.FILEPATH,
							"/Users/takashi.ebina/git/repository2/stepCounter/var/input",
							"/Users/takashi.ebina/git/repository2/stepCounter/var/output");
			// 【実行】
			injectMockcuiController.stepCountScriptMode(dto);
			// 【検証】
			assertEquals("--> ------------------------------------------------", out.readLine());
			assertEquals("--> 出力ファイルに問題があります。", out.readLine());
			assertEquals("--> ------------------------------------------------", out.readLine());
			// 【後処理】
		}
		
		@DisplayName("【異常系】ステップカウント処理実行メソッドで例外発生")
		@Test
		void Exception1() {
			// 【事前準備】
			when(mockCuiService.execStepCount((File)any(), (File)any(), (SortType)any(), (SortTarget)any()))
				.thenReturn(ProcessResult.FAIL);
			
			StepCounterCuiRequestDto dto = 
					new StepCounterCuiRequestDto(
							ExecuteMode.SCRIPT,
							SortType.ASCENDING_ORDER,
							SortTarget.FILEPATH,
							"/Users/takashi.ebina/git/repository2/stepCounter/var/input",
							"/Users/takashi.ebina/git/repository2/stepCounter/var/output/result.csv");
			// 【実行】
			injectMockcuiController.stepCountScriptMode(dto);
			// 【検証】
			assertEquals("--> ------------------------------------------------", out.readLine());
			assertEquals("--> ステップカウント処理が完了しました。 処理結果：異常終了", out.readLine());
			assertEquals("--> ------------------------------------------------", out.readLine());
			// 【後処理】
		}
	}
}
