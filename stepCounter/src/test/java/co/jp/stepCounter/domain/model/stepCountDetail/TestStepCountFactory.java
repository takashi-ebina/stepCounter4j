package co.jp.stepCounter.domain.model.stepCountDetail;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import co.jp.stepCounter.domain.model.commentPatternMatch.CsCommentPatternMatch;
import co.jp.stepCounter.domain.model.commentPatternMatch.JavaCommentPatternMatch;
import co.jp.stepCounter.domain.model.commentPatternMatch.SqlCommentPatternMatch;
import co.jp.stepCounter.domain.model.stepCountDetail.StepCountFactory.MethodType;

class TestStepCountFactory {
	private JavaCommentPatternMatch jcpm;
	private CsCommentPatternMatch ccpm;
	private SqlCommentPatternMatch scpm;

	@BeforeEach
	void setUp() {
		jcpm = new JavaCommentPatternMatch();
		ccpm = new CsCommentPatternMatch();
		scpm = new SqlCommentPatternMatch();
	}

	@Nested
	class Create {
		@DisplayName("【正常系】Javaステップカウントオブジェクト生成成功")
		@Test
		void success1() throws Exception {
			// 【事前準備】
			// 【実行】
			IfStepCount stepCountObj = StepCountFactory.StepCountType.of("java", jcpm);
			// 【検証】
			assertEquals(new JavaStepCount(jcpm, MethodType.DEFAULT), stepCountObj);
			// 【後処理】
		}

		@DisplayName("【正常系】Csステップカウントオブジェクト生成成功")
		@Test
		void success2() throws Exception {
			// 【事前準備】
			// 【実行】
			IfStepCount stepCountObj = StepCountFactory.StepCountType.of("cs", ccpm);
			// 【検証】
			assertEquals(new CsStepCount(ccpm, MethodType.DEFAULT), stepCountObj);
			// 【後処理】
		}

		@DisplayName("【正常系】sqlステップカウントオブジェクト生成成功")
		@Test
		void success3() throws Exception {
			// 【事前準備】
			// 【実行】
			IfStepCount stepCountObj = StepCountFactory.StepCountType.of("sql", scpm);
			// 【検証】
			assertEquals(new CsStepCount(scpm, MethodType.DEFAULT), stepCountObj);
			// 【後処理】
		}
		
		@DisplayName("【異常系】Enum(StepCountType)に定義されていない拡張子を引数に指定し、IllegalArgumentExceptionが発生")
		@Test
		void Exception1() throws Exception {
			// 【事前準備】
			// 【実行】
			// 【検証】
			assertThrows(IllegalArgumentException.class, () -> StepCountFactory.StepCountType.of("vb", jcpm));
			// 【後処理】
		}

	}

}
