package co.jp.stepCounter.presentation.validator;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import co.jp.base.StandardInputStream;
import co.jp.base.StandardOutputStream;
import co.jp.base.TestStepCounterConstant;
import co.jp.base.TestStepCounterConstant.TestCaseInOutDiv;
import co.jp.base.TestStepCounterUtil;
import co.jp.stepCounter.constant.StepCounterConstant.ExecuteMode;
import co.jp.stepCounter.infrastructure.dbdao.JdbcConnection;
import jp.co.future.uroborosql.SqlAgent;

class TestValidatorUtil {

	private StandardInputStream in = new StandardInputStream();
	private StandardOutputStream out = new StandardOutputStream();
	
	@BeforeEach
	void setUp() {
		final JdbcConnection con = JdbcConnection.getInstance();
		try (final SqlAgent agent = con.getSqlConfig().agent()) {
			agent.required(() -> {
				agent.autoCommitScope(() -> {
					agent.update("ddl/table/message").count();
					agent.update("setup/data/insert_message").count();
				});
			});
		}
		
		System.setIn(in);
		System.setOut(out);
	}
	
	@AfterEach
	void tearDown() {
		System.setIn(null);
		System.setOut(null);
	}
	
	@Nested
	class inputDirectoryCheck {
		@DisplayName("【正常系】入力ディレクトリのチェック処理成功")
		@Test
		void success1() {
			// 【事前準備】
			// 【実行】
			List<String> result = ValidatorUtil.inputDirectoryCheck(TestStepCounterUtil.getCommonTestcasePath(TestCaseInOutDiv.INPUT));
			// 【検証】
			assertEquals(0, result.size());
			// 【後処理】
		}
		
		@DisplayName("【準正常系】入力ディレクトリのチェック処理失敗（未入力）")
		@Test
		void warning1() {
			// 【事前準備】
			// 【実行】
			List<String> result = ValidatorUtil.inputDirectoryCheck("");
			// 【検証】
			assertEquals("入力フォルダ　未入力です。", result.get(0));
			// 【後処理】
		}
		
		@DisplayName("【準正常系】入力ディレクトリのチェック処理失敗（最大桁数オーバー）")
		@Test
		void warning2() {
			// 【事前準備】
			// 【実行】
			List<String> result = ValidatorUtil.inputDirectoryCheck("/Max_Length/123456789/123456789/123456789/123456789/123456789/123456789/123456789/123456789/123456789/123456789/123456789/123456789/123456789/123456789/123456789/123456789/123456789/123456789/123456789");
			// 【検証】
			assertEquals("入力フォルダ　200文字を超えています。", result.get(0));
			// 【後処理】
		}
		
		@DisplayName("【準正常系】入力ディレクトリのチェック処理失敗（ファイルパスを指定）")
		@Test
		void warning3() {
			// 【事前準備】
			// 【実行】
			List<String> result = ValidatorUtil.inputDirectoryCheck(TestStepCounterUtil.getCommonTestcasePath(TestCaseInOutDiv.OUTPUT) + TestStepCounterConstant.RESULT_FILE_NAME);
			// 【検証】
			assertEquals("入力フォルダ　ファイルパスを指定しています。", result.get(0));
			// 【後処理】
		}
	}
	
	@Nested
	class outputFileCheck {
		@DisplayName("【正常系】出力ファイルのチェック処理成功（GUIで実行）")
		@Test
		void success1() {
			// 【事前準備】
			// 【実行】
			List<String> result = ValidatorUtil.outputFileCheck(TestStepCounterUtil.getCommonTestcasePath(TestCaseInOutDiv.OUTPUT) + TestStepCounterConstant.RESULT_FILE_NAME
					, ExecuteMode.GUI, null);
			// 【検証】
			assertEquals(0, result.size());
			// 【後処理】
		}
		@DisplayName("【正常系】出力ファイルのチェック処理成功（CUIで実行かつファイルがすでに存在する場合）")
		@Test
		void success2() {
			// 【事前準備】
			Path p = Paths.get(TestStepCounterUtil.getCommonTestcasePath(TestCaseInOutDiv.OUTPUT) + TestStepCounterConstant.RESULT_FILE_NAME);
			try {
				Files.createFile(p);
			} catch (IOException e) {
			}
			in.inputln("z"); // n / y 以外のコマンド入力
			in.inputln("Y");
			// 【実行】
			List<String> result = ValidatorUtil.outputFileCheck(TestStepCounterUtil.getCommonTestcasePath(TestCaseInOutDiv.OUTPUT) + TestStepCounterConstant.RESULT_FILE_NAME
					, ExecuteMode.INTERACTIVE, new Scanner(System.in));
			// 【検証】
			assertEquals(0, result.size());
			// 【後処理】
		}
		@DisplayName("【準正常系】出力ファイルのチェック処理失敗（未入力）")
		@Test
		void warning1() {
			// 【事前準備】
			// 【実行】
			List<String> result = ValidatorUtil.outputFileCheck("", ExecuteMode.GUI, null);
			// 【検証】
			assertEquals("出力ファイル　未入力です。", result.get(0));
			// 【後処理】
		}
		
		@DisplayName("【準正常系】出力ファイルのチェック処理失敗（最大桁数オーバー）")
		@Test
		void warning2() {
			// 【事前準備】
			// 【実行】
			List<String> result = ValidatorUtil.outputFileCheck("/Max_Length/123456789/123456789/123456789/123456789/123456789/123456789/123456789/123456789/123456789/123456789/123456789/123456789/123456789/123456789/123456789/123456789/123456789/123456789/123456789/123456789/123456789/123456789/123456789/1234567890.csv"
					, ExecuteMode.GUI, null);
			// 【検証】
			assertEquals("出力ファイル　255文字を超えています。", result.get(0));
			// 【後処理】
		}
		
		@DisplayName("【準正常系】出力ファイルのチェック処理失敗（ファイルパスを指定）")
		@Test
		void warning3() {
			// 【事前準備】
			// 【実行】
			List<String> result = ValidatorUtil.outputFileCheck(TestStepCounterUtil.getCommonTestcasePath(TestCaseInOutDiv.INPUT)
					, ExecuteMode.GUI, null);
			// 【検証】
			assertEquals("出力ファイル　フォルダパスを指定しています。", result.get(0));
			// 【後処理】
		}
		@DisplayName("【準正常系】出力ファイルのチェック処理失敗（拡張子がCSV以外）")
		@Test
		void warning4() {
			// 【事前準備】
			// 【実行】
			List<String> result = ValidatorUtil.outputFileCheck("/test/testExtension.sql"
					, ExecuteMode.GUI, null);
			// 【検証】
			assertEquals("出力ファイル　拡張子がcsvではありません。", result.get(0));
			// 【後処理】
		}
		@DisplayName("【準正常系】出力ファイルのチェック処理成功（CUIで実行かつファイルがすでに存在する場合）")
		@Test
		void warning5() {
			// 【事前準備】
			Path p = Paths.get(TestStepCounterUtil.getCommonTestcasePath(TestCaseInOutDiv.OUTPUT) + TestStepCounterConstant.RESULT_FILE_NAME);
			try {
				Files.createFile(p);
			} catch (IOException e) {
			}
			in.inputln("n");
			// 【実行】
			List<String> result = ValidatorUtil.outputFileCheck(TestStepCounterUtil.getCommonTestcasePath(TestCaseInOutDiv.OUTPUT) + TestStepCounterConstant.RESULT_FILE_NAME
					, ExecuteMode.INTERACTIVE, new Scanner(System.in));
			// 【検証】
			assertEquals("ステップカウント処理が中断しました。", result.get(0));
			// 【後処理】
		}
	}
}
