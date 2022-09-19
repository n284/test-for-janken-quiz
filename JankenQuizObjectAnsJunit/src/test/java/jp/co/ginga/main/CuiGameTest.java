package jp.co.ginga.main;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;

import jp.co.ginga.cui.impl.janken.JankenCuiGameApplicationImpl;
import jp.co.ginga.cui.impl.quiz.QuizCuiGameApplicationImpl;
import jp.co.ginga.util.exception.ApplicationException;
import jp.co.ginga.util.exception.SystemException;
import jp.co.ginga.util.keybord.Keybord;

/**
 * CUIゲーム クラス
 * @author yoshi
 *
 */
public class CuiGameTest {
	
	//テストデータ
	private int quizGameNum = 1;
	private int jankenGameNum = 2;
	private int illegalValue = -1;
	private String errorMessage = "入力した値が不正です。再入力をお願いします。";


	/**
	 * testMain001 正常系
	 * public static void main(String[] args)
	 * --確認事項--
	 * ゲーム選択時に不正な値が入力されたらもう一度入力が求められる
	 * クイズゲームインスタンスのactionが呼び出される
	 * --条件--
	 * getIntでは一回目は不正な値、二回目は1を入力するmock
	 * mock化したゲームインスタンスが生成される
	 * --検証項目--
	 * 1. getIntメソッドが2回呼び出されているか
	 * 2. actionメソッドが1回呼び出されているか
	 * 3. 例外が発生しないこと
	 */
	@Test
	public void testMain001() {
		//モック化
		try(MockedStatic<Keybord> mockKeybord = mockStatic(Keybord.class);
			 MockedConstruction<QuizCuiGameApplicationImpl> mockQuizGame = mockConstruction(
					 QuizCuiGameApplicationImpl.class,
					 (quizGame, context) -> doNothing().when(quizGame).action()
			)
		){
			mockKeybord.when(() -> Keybord.getInt(this.quizGameNum, this.jankenGameNum)).thenThrow(new ApplicationException(this.errorMessage)).thenReturn(this.quizGameNum);
			
			//テストメソッド
			CuiGame.main(null);
			
			//検証
			mockKeybord.verify(()->Keybord.getInt(this.quizGameNum, this.jankenGameNum), times(2));
			verify(mockQuizGame.constructed().get(0), times(1)).action();
			
		}catch(Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testMain002 正常系
	 * public static void main(String[] args)
	 * --確認事項--
	 * ゲーム選択時に不正な値が入力されたらもう一度入力が求められる
	 * クイズゲームインスタンスのactionが呼び出される
	 * --条件--
	 * getIntメソッドでは1を入力するmock
	 * mock化したゲームインスタンスが生成される
	 * --検証項目--
	 * 1. getIntメソッドが1回呼び出されているか
	 * 2. actionメソッドが1回呼び出されているか
	 * 3. 例外が発生しないこと
	 */
	@Test
	public void testMain002() {
		//モック化
		try(MockedStatic<Keybord> mockKeybord = mockStatic(Keybord.class);
			 MockedConstruction<JankenCuiGameApplicationImpl> mockJankenGame = mockConstruction(
					 JankenCuiGameApplicationImpl.class,
					 (jankenGame, context) -> doNothing().when(jankenGame).action()
			)
		){
			mockKeybord.when(() -> Keybord.getInt(this.quizGameNum, this.jankenGameNum)).thenReturn(this.jankenGameNum);
			
			//テストメソッド
			CuiGame.main(null);
			
			//検証
			mockKeybord.verify(()->Keybord.getInt(this.quizGameNum, this.jankenGameNum), times(1));
			verify(mockJankenGame.constructed().get(0), times(1)).action();
			
		}catch(Exception e) {
			e.printStackTrace();
			fail();
		}
	}


	/**
	 * testMain003 異常系
	 * public static void main(String[] args)
	 * --確認事項--
	 * ゲーム選択時に不正な値を入力しても再入力が求められずに処理が進んでしまい終了する
	 * --条件--
	 * getIntでは一回目は-1を入力するmock
	 * mock化したゲームインスタンスが生成される
	 * --検証項目--
	 * 1. getIntメソッドが1回呼び出されているか
	 * 2. ゲームインスタンスが生成されていないか
	 * 3. 処理が終了するか
	 */
	@Test
	public void testMain003() {
		//モック化
		try(MockedStatic<Keybord> mockKeybord = mockStatic(Keybord.class);
				  MockedConstruction<JankenCuiGameApplicationImpl> mockJankenGame = mockConstruction(
						 JankenCuiGameApplicationImpl.class,
						 (jankenGame, context) -> doThrow(new Exception()).when(jankenGame).action()
				  );
				MockedConstruction<QuizCuiGameApplicationImpl> mockQuizGame = mockConstruction(
						 QuizCuiGameApplicationImpl.class,
						 (quizGame, context) -> doNothing().when(quizGame).action()
				)
		){
			mockKeybord.when(() -> Keybord.getInt(this.quizGameNum, this.jankenGameNum)).thenReturn(this.illegalValue);
			
			//テストメソッド
			CuiGame.main(null);
			
			//検証
			mockKeybord.verify(()->Keybord.getInt(this.quizGameNum, this.jankenGameNum), times(1));
			assertEquals(0, mockQuizGame.constructed().size());
			assertEquals(0, mockJankenGame.constructed().size());
			
		}catch(Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testMain004 異常系
	 * public static void main(String[] args)
	 * --確認事項--
	 * actionメソッドからSystemExceptionが発生すると処理が終了する
	 * --条件--
	 * getIntでは一回目は1を入力するmock
	 * 生成されるゲームインスタンスはSystemExceptionが発生するmock
	 * --検証項目--
	 * 1. getIntメソッドが1回呼び出されているか
	 * 2. actionメソッドが1回呼び出されているか
	 */
	@Test
	public void testMain004() {
		//モック化
		try(MockedStatic<Keybord> mockKeybord = mockStatic(Keybord.class);
			  MockedConstruction<JankenCuiGameApplicationImpl> mockJankenGame = mockConstruction(
					 JankenCuiGameApplicationImpl.class,
					 (jankenGame, context) -> doThrow(new SystemException(null)).when(jankenGame).action()
			)
		){
			mockKeybord.when(() -> Keybord.getInt(this.quizGameNum, this.jankenGameNum)).thenReturn(this.jankenGameNum);
			
			//テストメソッド
			CuiGame.main(null);
			
			//検証
			mockKeybord.verify(()->Keybord.getInt(this.quizGameNum, this.jankenGameNum), times(1));
			verify(mockJankenGame.constructed().get(0), times(1)).action();
			
		}catch(Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testMain005 異常系
	 * public static void main(String[] args)
	 * --確認事項--
	 * actionメソッドからApplicationExcepitonが発生すると処理が終了する
	 * --条件--
	 * getIntでは一回目は2を入力するmock
	 * 生成されるゲームインスタンスはApplicationExcepitonが発生するmock
	 * --検証項目--
	 * 1. getIntメソッドが1回呼び出されているか
	 * 2. actionメソッドが1回呼び出されているか
	 */
	@Test
	public void testMain005() {
		//モック化
		try(MockedStatic<Keybord> mockKeybord = mockStatic(Keybord.class);
				MockedConstruction<JankenCuiGameApplicationImpl> mockJankenGame = mockConstruction(
						 JankenCuiGameApplicationImpl.class,
						 (jankenGame, context) -> doThrow(new ApplicationException(null)).when(jankenGame).action()
				)
		){
			mockKeybord.when(() -> Keybord.getInt(this.quizGameNum, this.jankenGameNum)).thenReturn(this.jankenGameNum);
			
			//テストメソッド
			CuiGame.main(null);
			
			//検証
			mockKeybord.verify(()->Keybord.getInt(this.quizGameNum, this.jankenGameNum), times(1));
			verify(mockJankenGame.constructed().get(0), times(1)).action();
			
		}catch(Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	/**
	 * カバレッジを100%にするためのコンストラクタテスト
	 */
	@Test
	public void testConstructor001() {
		@SuppressWarnings("unused")
		CuiGame c = new CuiGame();
	}
	
}
