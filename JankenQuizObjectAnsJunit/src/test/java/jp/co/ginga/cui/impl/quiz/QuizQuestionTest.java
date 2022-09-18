package jp.co.ginga.cui.impl.quiz;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Field;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import jp.co.ginga.util.exception.SystemException;
import jp.co.ginga.util.properties.MessageProperties;

/**
 * クイズ問題クラス
 * @author yoshi
 *
 */
public class QuizQuestionTest {
	//テストデータ
	private int number = 1;

	private String title ;
	private String body;
	private String choice;
	private int correct;

	//テストクラス
	@InjectMocks
	private QuizQuestion quiz = new QuizQuestion(null, null, null, 0);

	@BeforeEach
	private void createTestData() {
		try {
			this.title = MessageProperties.getMessage("quiz.question.titile" +this.number);
			this.body = MessageProperties.getMessage("quiz.question.body" + this.number);
			this.choice = MessageProperties.getMessage("quiz.question.choice" + this.number);
			this.correct = Integer.parseInt(MessageProperties.getMessage("quiz.question.correct" + this.number));

		}catch(SystemException e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testConstructor001 正常系
	 * public QuizQuestion(String problemTitle, String problemBody, String problemChoice, int correct)
	 * 引数で渡された情報がフィールドに代入されているかを確認
	 */
	@Test
	public void testConstructor001() {
		try {
			//テストメソッド
			QuizQuestion quiz = new QuizQuestion(this.title, this.body, this.choice, this.correct);

			//検証
			Field problemTitleField = QuizQuestion.class.getDeclaredField("problemTitle");
			Field problemBodyField = QuizQuestion.class.getDeclaredField("problemBody");
			Field problemChoiceField = QuizQuestion.class.getDeclaredField("problemChoice");
			Field correctField = QuizQuestion.class.getDeclaredField("correct");
			problemTitleField.setAccessible(true);
			problemBodyField.setAccessible(true);
			problemChoiceField.setAccessible(true);
			correctField.setAccessible(true);

			String title = String.valueOf(problemTitleField.get(quiz));
			String body = String.valueOf(problemBodyField.get(quiz));
			String choice = String.valueOf(problemChoiceField.get(quiz));
			int correct = (int) correctField.get(quiz);

			assertEquals(this.title, title);
			assertEquals(this.body, body);
			assertEquals(this.choice, choice);
			assertEquals(this.correct, correct);

		}catch(NoSuchFieldException | SecurityException | NullPointerException| IllegalArgumentException | IllegalAccessException  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testConstructor002 異常系
	 * public QuizQuestion(String problemTitle, String problemBody, String problemChoice, int correct)
	 * 引数で渡された情報が、フィールドに代入されているかを確認
	 * nullと整数を渡す
	 */
	@Test
	public void testConstructor002() {
		try {
			//テストメソッド
			QuizQuestion quiz = new QuizQuestion(null, null, null, this.correct);

			//検証
			Field problemTitleField = QuizQuestion.class.getDeclaredField("problemTitle");
			Field problemBodyField = QuizQuestion.class.getDeclaredField("problemBody");
			Field problemChoiceField = QuizQuestion.class.getDeclaredField("problemChoice");
			Field correctField = QuizQuestion.class.getDeclaredField("correct");
			problemTitleField.setAccessible(true);
			problemBodyField.setAccessible(true);
			problemChoiceField.setAccessible(true);
			correctField.setAccessible(true);

			String title = (String) problemTitleField.get(quiz);
			String body = (String) problemBodyField.get(quiz);
			String choice =  (String) problemChoiceField.get(quiz);
			int correct = (int) correctField.get(quiz);

			assertNull( title);
			assertNull(body);
			assertNull(choice);
			assertEquals(this.correct, correct);

		}catch(NoSuchFieldException | SecurityException | NullPointerException| IllegalArgumentException | IllegalAccessException  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testGetProblemTitle001 正常系
	 * public String getProblemTitle()
	 * フィールドの値が返されるかを確認
	 */
	@Test
	public void testGetProblemTitle001() {
		try {
			Field problemTitleField = QuizQuestion.class.getDeclaredField("problemTitle");
			problemTitleField.setAccessible(true);
			problemTitleField.set(this.quiz, this.title);

			//テストメソッド
			String result = this.quiz.getProblemTitle();

			//検証
			assertEquals(this.title, result);

		}catch(NoSuchFieldException | SecurityException | NullPointerException| IllegalArgumentException | IllegalAccessException  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testGetProblemTitle002 異常系
	 * public String getProblemTitle()
	 * nullが代入されているフィールドの値が返されるかを確認
	 */
	@Test
	public void testGetProblemTitle002() {
		try {
			Field problemTitleField = QuizQuestion.class.getDeclaredField("problemTitle");
			problemTitleField.setAccessible(true);
			problemTitleField.set(this.quiz, null);

			//テストメソッド
			String result = this.quiz.getProblemTitle();

			//検証
			assertNull(result);

		}catch(NoSuchFieldException | SecurityException | NullPointerException| IllegalArgumentException | IllegalAccessException  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testSetProblemTitle001 正常系
	 * public void setProblemTitle(String problemTitle)
	 * フィールドに値が代入されるかを確認
	 */
	@Test
	public void testSetProblemTitle001() {
		try {
			Field problemTitleField = QuizQuestion.class.getDeclaredField("problemTitle");
			problemTitleField.setAccessible(true);

			//テストメソッド
			this.quiz.setProblemTitle(this.title);

			//検証
			String result = String.valueOf(problemTitleField.get(this.quiz));
			assertEquals(this.title, result);

		}catch(NoSuchFieldException | SecurityException | NullPointerException| IllegalArgumentException | IllegalAccessException  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testSetProblemTitle002 異常系
	 * public void setProblemTitle(String problemTitle)
	 * フィールドにnullが代入されるかを確認
	 */
	@Test
	public void testSetProblemTitle002() {
		try {
			Field problemTitleField = QuizQuestion.class.getDeclaredField("problemTitle");
			problemTitleField.setAccessible(true);

			//テストメソッド
			this.quiz.setProblemTitle(null);

			//検証
			String result = (String) problemTitleField.get(this.quiz);
			assertNull(result);

		}catch(NoSuchFieldException | SecurityException | NullPointerException| IllegalArgumentException | IllegalAccessException  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testGetProblemBody001 正常系
	 * public String getProblemBody()
	 * フィールドの値が返されるかを確認
	 */
	@Test
	public void testGetProblemBody001() {
		try {
			Field problemBodyField = QuizQuestion.class.getDeclaredField("problemBody");
			problemBodyField.setAccessible(true);
			problemBodyField.set(this.quiz, this.body);

			//テストメソッド
			String result = this.quiz.getProblemBody();

			//検証
			assertEquals(this.body, result);

		}catch(NoSuchFieldException | SecurityException | NullPointerException| IllegalArgumentException | IllegalAccessException  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testGetProblemBody002 異常系
	 * public String getProblemBody()
	 * フィールドの値が返されるかを確認
	 */
	@Test
	public void testGetProblemBody002() {
		try {
			Field problemBodyField = QuizQuestion.class.getDeclaredField("problemBody");
			problemBodyField.setAccessible(true);
			problemBodyField.set(this.quiz, null);

			//テストメソッド
			String result = this.quiz.getProblemBody();

			//検証
			assertNull(result);

		}catch(NoSuchFieldException | SecurityException | NullPointerException| IllegalArgumentException | IllegalAccessException  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testSetProblemBody001 正常系
	 * public void setProblemBody(String problemBody)
	 * フィールドに値が代入されるかを確認
	 */
	@Test
	public void testSetProblemBody001() {
		try {
			Field problemBodyField = QuizQuestion.class.getDeclaredField("problemBody");
			problemBodyField.setAccessible(true);

			//テストメソッド
			this.quiz.setProblemBody(this.body);

			//検証
			String result = String.valueOf(problemBodyField.get(this.quiz));
			assertEquals(this.body, result);

		}catch(NoSuchFieldException | SecurityException | NullPointerException| IllegalArgumentException | IllegalAccessException  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testSetProblemBody002 異常系
	 * public void setProblemBody(String problemBody)
	 * フィールドにnullが代入されるかを確認
	 */
	@Test
	public void testSetProblemBody002() {
		try {
			Field problemBodyField = QuizQuestion.class.getDeclaredField("problemBody");
			problemBodyField.setAccessible(true);

			//テストメソッド
			this.quiz.setProblemBody(null);

			//検証
			String result = (String) problemBodyField.get(this.quiz);
			assertNull(result);

		}catch(NoSuchFieldException | SecurityException | NullPointerException| IllegalArgumentException | IllegalAccessException  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testGetProblemChoice001 正常系
	 * public String getProblemChoice()
	 * フィールドの値が返されるかを確認
	 */
	@Test
	public void testGetProblemChoice001() {
		try {
			Field problemChoiceField = QuizQuestion.class.getDeclaredField("problemChoice");
			problemChoiceField.setAccessible(true);
			problemChoiceField.set(this.quiz, this.choice);

			//テストメソッド
			String result = this.quiz.getProblemChoice();

			//検証
			assertEquals(this.choice, result);

		}catch(NoSuchFieldException | SecurityException | NullPointerException| IllegalArgumentException | IllegalAccessException  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testGetProblemChoice002 異常系
	 * public String getProblemChoice()
	 * フィールドの値が返されるかを確認
	 */
	@Test
	public void testGetProblemChoice002() {
		try {
			Field problemChoiceField = QuizQuestion.class.getDeclaredField("problemChoice");
			problemChoiceField.setAccessible(true);
			problemChoiceField.set(this.quiz, null);

			//テストメソッド
			String result = this.quiz.getProblemChoice();

			//検証
			assertNull(result);

		}catch(NoSuchFieldException | SecurityException | NullPointerException| IllegalArgumentException | IllegalAccessException  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testSetProblemChoice001 正常系
	 * public void setProblemChoice(String problemChoice)
	 * フィールドに値が代入されるかを確認
	 */
	@Test
	public void testSetProblemChoice001() {
		try {
			Field problemChoiceField = QuizQuestion.class.getDeclaredField("problemChoice");
			problemChoiceField.setAccessible(true);

			//テストメソッド
			this.quiz.setProblemChoice(this.choice);

			//検証
			String result = String.valueOf(problemChoiceField.get(this.quiz));
			assertEquals(this.choice, result);

		}catch(NoSuchFieldException | SecurityException | NullPointerException| IllegalArgumentException | IllegalAccessException  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testSetProblemChoice002 正常系
	 * public void setProblemChoice(String problemChoice)
	 * フィールドにnullが代入されるかを確認
	 */
	@Test
	public void testSetProblemChoice002() {
		try {
			Field problemChoiceField = QuizQuestion.class.getDeclaredField("problemChoice");
			problemChoiceField.setAccessible(true);

			//テストメソッド
			this.quiz.setProblemChoice(null);

			//検証
			String result = (String) problemChoiceField.get(this.quiz);
			assertNull(result);

		}catch(NoSuchFieldException | SecurityException | NullPointerException| IllegalArgumentException | IllegalAccessException  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testGetCorrect001 正常系
	 * public int getCorrect()
	 * フィールドの値が返されるかを確認
	 */
	@Test
	public void testGetCorrect001() {
		try {
			Field correctField = QuizQuestion.class.getDeclaredField("correct");
			correctField.setAccessible(true);
			correctField.set(this.quiz, this.correct);

			//テストメソッド
			int result = this.quiz.getCorrect();

			//検証
			assertEquals(this.correct, result);

		}catch(NoSuchFieldException | SecurityException | NullPointerException| IllegalArgumentException | IllegalAccessException  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testSetCorrect001 正常系
	 * public void setCorrect(int correct)
	 * フィールドに値が代入されるかを確認
	 */
	@Test
	public void testSetCorrect001() {
		try {
			Field correctField = QuizQuestion.class.getDeclaredField("correct");
			correctField.setAccessible(true);

			//テストメソッド
			this.quiz.setCorrect(this.correct);

			//検証
			int result = (int) correctField.get(this.quiz);
			assertEquals(this.correct, result);

		}catch(NoSuchFieldException | SecurityException | NullPointerException| IllegalArgumentException | IllegalAccessException  e) {
			e.printStackTrace();
			fail();
		}
	}

}
