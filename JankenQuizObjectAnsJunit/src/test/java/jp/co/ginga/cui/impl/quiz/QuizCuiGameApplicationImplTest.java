package jp.co.ginga.cui.impl.quiz;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;

import jp.co.ginga.cui.CuiGameApplication;
import jp.co.ginga.util.exception.ApplicationException;
import jp.co.ginga.util.exception.SystemException;
import jp.co.ginga.util.keybord.Keybord;
import jp.co.ginga.util.properties.MessageProperties;

/**
 * CUIクイズゲーム実装クラス
 *
 * @author yoshi
 *
 */
public class QuizCuiGameApplicationImplTest{

	//テストデータ
	private int zero = 0;
	private int one = 1;
	private int two = 2;
	private int three = 3;
	private String alphabet = "a";


	private List<QuizQuestion> quizList = new ArrayList<QuizQuestion>();
	private List<QuizQuestion> emptyQuizList = new ArrayList<QuizQuestion>();
	private String errorMessage ;


	//テストクラス
	@InjectMocks
	private CuiGameApplication game = new QuizCuiGameApplicationImpl();

	@BeforeEach
	private void createTestData() {
		try {
			int numberOfQuiz = Integer.parseInt(MessageProperties.getMessage("quiz.number.questions"));
			for (int i = 0; i < numberOfQuiz; i++) {
				this.quizList.add(new QuizQuestion(MessageProperties.getMessage("quiz.question.titile" + (i + 1)),
						MessageProperties.getMessage("quiz.question.body" + (i + 1)),
						MessageProperties.getMessage("quiz.question.choice" + (i + 1)),
						Integer.parseInt(MessageProperties.getMessage("quiz.question.correct" + (i + 1)))));
			}

			this.errorMessage = MessageProperties.getMessage("error.properties.error");

		}catch(SystemException e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testAction001 正常系
	 * public void action() throws SystemException
	 * SystemExceptionも含めて例外が発生しないことを確認
	 */
	@Test
	public void testAction001() {
		//モック化
		try(MockedStatic<Keybord> mockKeybord = mockStatic(Keybord.class)){
			//解答、1, 2, 3
			mockKeybord.when(() -> Keybord.getInt(this.one, this.three)).thenReturn(this.one).thenReturn(this.two).thenReturn(this.one).thenReturn(this.three);

			this.game.action();

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testCreateQuizList001 正常系
	 * private void createQuizList() throws SystemException
	 * 問題数が正常の3
	 * listにQuizQuestionに情報を詰めてインスタンス化したものが追加されているかを確認
	 */
	@Test
	public void testCreateQuizList001() {
		try{
			Method method = QuizCuiGameApplicationImpl.class.getDeclaredMethod("createQuizList");
			Field listField = QuizCuiGameApplicationImpl.class.getDeclaredField("list");
			method.setAccessible(true);
			listField.setAccessible(true);
			listField.set(this.game, this.emptyQuizList);

			//テストメソッド
			method.invoke(this.game);

			//検証
			@SuppressWarnings("unchecked")
			List<QuizQuestion> list = (List<QuizQuestion>) listField.get(this.game);
			assertEquals(this.quizList.size(), list.size());
			for(int i = 0; i < list.size(); i++) {
				assertEquals(this.quizList.get(i).getProblemTitle(), list.get(i).getProblemTitle());
				assertEquals(this.quizList.get(i).getProblemBody(), list.get(i).getProblemBody());
				assertEquals(this.quizList.get(i).getProblemChoice(), list.get(i).getProblemChoice());
				assertEquals(this.quizList.get(i).getCorrect(), list.get(i).getCorrect());
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testCreateQuizList002 異常系
	 * private void createQuizList() throws SystemException
	 * 問題数が0
	 * listに何も追加されないことを確認
	 */
	@Test
	public void testCreateQuizList002() {
		try(MockedStatic<MessageProperties> mockMessageProperties = mockStatic(MessageProperties.class)){
			mockMessageProperties.when(() -> MessageProperties.getMessage("quiz.number.questions")).thenReturn(String.valueOf(this.zero));
			Method method = QuizCuiGameApplicationImpl.class.getDeclaredMethod("createQuizList");
			Field listField = QuizCuiGameApplicationImpl.class.getDeclaredField("list");
			method.setAccessible(true);
			listField.setAccessible(true);
			listField.set(this.game, this.emptyQuizList);

			//テストメソッド
			method.invoke(this.game);

			//検証
			@SuppressWarnings("unchecked")
			List<QuizQuestion> list = (List<QuizQuestion>) listField.get(this.game);
			assertEquals(this.zero, list.size());
		} catch (NullPointerException| SecurityException | NoSuchMethodException | IllegalAccessException | IllegalArgumentException  | ExceptionInInitializerError | NoSuchFieldException | InvocationTargetException  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testCreateQuizList003 異常系
	 * private void createQuizList() throws SystemException
	 * 問題数が異常値
	 * NumberFormatExceptionが投げられ、SystemExceptionが発生することを確認
	 */
	@Test
	public void testCreateQuizList003() {
		try(MockedStatic<MessageProperties> mockMessageProperties = mockStatic(MessageProperties.class)){
			mockMessageProperties.when(() -> MessageProperties.getMessage("quiz.number.questions")).thenReturn(this.alphabet);
			Method method = QuizCuiGameApplicationImpl.class.getDeclaredMethod("createQuizList");
			Field listField = QuizCuiGameApplicationImpl.class.getDeclaredField("list");
			method.setAccessible(true);
			listField.setAccessible(true);
			listField.set(this.game, this.emptyQuizList);

			//テストメソッド
			InvocationTargetException e = assertThrows(InvocationTargetException.class, () -> method.invoke(this.game));

			//検証
			@SuppressWarnings("unchecked")
			List<QuizQuestion> list = (List<QuizQuestion>) listField.get(this.game);
			assertEquals(this.zero, list.size());
			assertEquals(e.getTargetException().getClass(), SystemException.class);
		} catch (NullPointerException| SecurityException | NoSuchMethodException | IllegalAccessException | IllegalArgumentException  | ExceptionInInitializerError | NoSuchFieldException e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testViewProblem001 正常系
	 * private void viewProblem(QuizQuestion quiz)
	 * 引数のQuizQuestionインスタンスからtitle、body、choiceのゲッターが1回ずつ呼び出されることを確認
	 */
	@Test
	public void testViewProblem001() {
		try{
			QuizQuestion mockQuizQuestion = mock(QuizQuestion.class);
			Method method = QuizCuiGameApplicationImpl.class.getDeclaredMethod("viewProblem", QuizQuestion.class);
			method.setAccessible(true);

			//テストメソッド
			method.invoke(this.game, mockQuizQuestion);

			//検証
			verify(mockQuizQuestion, times(1)).getProblemTitle();
			verify(mockQuizQuestion, times(1)).getProblemBody();
			verify(mockQuizQuestion, times(1)).getProblemChoice();
		} catch (NullPointerException| SecurityException | NoSuchMethodException | IllegalAccessException | IllegalArgumentException  | ExceptionInInitializerError | InvocationTargetException e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testJudge001 正常系
	 * private void judge(QuizQuestion quiz) throws SystemException
	 * 一度不正な値を入力して再度入力が求められることと、正解数が増えているかを確認
	 */
	@Test
	public void testJudge001() {
		//モック化
		try(MockedStatic<Keybord> mockKeybord = mockStatic(Keybord.class)){
			mockKeybord.when(() -> Keybord.getInt(this.one, this.three)).thenThrow(new ApplicationException(null)).thenReturn(this.one);
			QuizQuestion mockQuizQuestion = mock(QuizQuestion.class);
			when(mockQuizQuestion.getCorrect()).thenReturn(this.one);

			Method method = QuizCuiGameApplicationImpl.class.getDeclaredMethod("judge", QuizQuestion.class);
			Field correctCountField = QuizCuiGameApplicationImpl.class.getDeclaredField("correctConut");
			method.setAccessible(true);
			correctCountField.setAccessible(true);
			correctCountField.set(this.game, this.zero);

			//テストメソッド
			method.invoke(this.game, mockQuizQuestion);

			//検証
			int correctCount = (int) correctCountField.get(this.game);
			assertEquals(this.one, correctCount);
			verify(mockQuizQuestion, times(2)).getCorrect();
		} catch (NullPointerException| SecurityException | NoSuchMethodException | IllegalAccessException | IllegalArgumentException  | ExceptionInInitializerError | NoSuchFieldException | InvocationTargetException e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testViewResult001 正常系
	 * private void viewResult() throws SystemException
	 * 例外が発生しないことを確認
	 */
	@Test
	public void testViewResult001() {
		try{
			Method method = QuizCuiGameApplicationImpl.class.getDeclaredMethod("viewResult");
			method.setAccessible(true);

			//テストメソッド
			method.invoke(this.game);

			//検証
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
