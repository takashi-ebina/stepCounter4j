package co.jp.stepCounter.domain.model.stepCountDetail;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import co.jp.stepCounter.domain.model.commentPatternMatch.CsCommentPatternMatch;
import co.jp.stepCounter.domain.model.commentPatternMatch.JavaCommentPatternMatch;

class TestStepCountFactory {
	private JavaCommentPatternMatch jcpm;
	private CsCommentPatternMatch ccpm;

	@BeforeEach
	void setUp() {
		jcpm = new JavaCommentPatternMatch();
		ccpm = new CsCommentPatternMatch();
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
			assertEquals(new JavaStepCount(jcpm), stepCountObj);
			// 【後処理】
		}

		@DisplayName("【正常系】Csステップカウントオブジェクト生成成功")
		@Test
		void success2() throws Exception {
			// 【事前準備】
			// 【実行】
			IfStepCount stepCountObj = StepCountFactory.StepCountType.of("cs", ccpm);
			// 【検証】
			assertEquals(new CsStepCount(ccpm), stepCountObj);
			// 【後処理】
		}

		@DisplayName("【異常系】Enum(StepCountType)に定義されていない拡張子を引数に指定し、IllegalArgumentExceptionが発生")
		@Test
		void Exception1() throws Exception {
			// 【事前準備】
			// 【実行】
			// 【検証】
			assertThrows(IllegalArgumentException.class, () -> StepCountFactory.StepCountType.of("sql", jcpm));
			// 【後処理】
		}

	}

}
