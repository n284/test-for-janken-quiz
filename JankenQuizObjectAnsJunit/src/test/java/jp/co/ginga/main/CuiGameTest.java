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

	/**
	 * testMain001 正常系
	 * public static void main(String[] args)
	 * 一度不正な値を入力したことによるApplicationExceptionを発行し、再度入力が求められて1を入力し、クイズゲームを開始する
	 * 例外が発生しないことを確認する
	 */
	@Test
	public void testMain001() {
		//モック化
		try(MockedStatic<Keybord> mockKeybord = mockStatic(Keybord.class);
			 MockedConstruction<QuizCuiGameApplicationImpl> mockQuizGame = mockConstruction(QuizCuiGameApplicationImpl.class,
					 (quizGame, context) -> doNothing().when(quizGame).action())){
			mockKeybord.when(() -> Keybord.getInt(this.quizGameNum, this.jankenGameNum)).thenThrow(new ApplicationException(null)).thenReturn(this.quizGameNum);
			//テストメソッド
			CuiGame.main(null);
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
	 * 入力が求められて2を入力し、ジャンケンゲームを開始する
	 * 例外が発生しないことを確認する
	 */
	@Test
	public void testMain002() {
		//モック化
		try(MockedStatic<Keybord> mockKeybord = mockStatic(Keybord.class);
			 MockedConstruction<JankenCuiGameApplicationImpl> mockJankenGame = mockConstruction(JankenCuiGameApplicationImpl.class,
					 (jankenGame, context) -> doNothing().when(jankenGame).action())){
			mockKeybord.when(() -> Keybord.getInt(this.quizGameNum, this.jankenGameNum)).thenReturn(this.jankenGameNum);
			//テストメソッド
			CuiGame.main(null);
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
	 * 入力が求められて-1を入力し、処理が継続されてしまった場合
	 * 処理が終了することを確認
	 */
	@Test
	public void testMain003() {
		//モック化
		try(MockedStatic<Keybord> mockKeybord = mockStatic(Keybord.class)){
			mockKeybord.when(() -> Keybord.getInt(this.quizGameNum, this.jankenGameNum)).thenReturn(this.illegalValue);
			//テストメソッド
			CuiGame.main(null);
			mockKeybord.verify(()->Keybord.getInt(this.quizGameNum, this.jankenGameNum), times(1));
		}catch(Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testMain004 異常系
	 * public static void main(String[] args)
	 * actionメソッドからSystemExceptionが発生した場合
	 * 例外が発生せずに処理が終了することを確認
	 */
	@Test
	public void testMain004() {
		//モック化
		try(MockedStatic<Keybord> mockKeybord = mockStatic(Keybord.class);
			 MockedConstruction<JankenCuiGameApplicationImpl> mockJankenGame = mockConstruction(JankenCuiGameApplicationImpl.class,
					 (jankenGame, context) -> doThrow(new SystemException(null)).when(jankenGame).action())){
			mockKeybord.when(() -> Keybord.getInt(this.quizGameNum, this.jankenGameNum)).thenReturn(this.jankenGameNum);
			//テストメソッド
			CuiGame.main(null);
			mockKeybord.verify(()->Keybord.getInt(this.quizGameNum, this.jankenGameNum), times(1));
			verify(mockJankenGame.constructed().get(0), times(1)).action();
		}catch(Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testMain004 異常系
	 * public static void main(String[] args)
	 * actionメソッドからSystemException以外の例外が発生した場合
	 * 例外が発生せずに処理が終了することを確認
	 */
	@Test
	public void testMain005() {
		//モック化
		try(MockedStatic<Keybord> mockKeybord = mockStatic(Keybord.class);
			 MockedConstruction<JankenCuiGameApplicationImpl> mockJankenGame = mockConstruction(JankenCuiGameApplicationImpl.class,
					 (jankenGame, context) -> doThrow(new Exception()).when(jankenGame).action())){
			mockKeybord.when(() -> Keybord.getInt(this.quizGameNum, this.jankenGameNum)).thenReturn(this.jankenGameNum);
			//テストメソッド
			CuiGame.main(null);
			mockKeybord.verify(()->Keybord.getInt(this.quizGameNum, this.jankenGameNum), times(1));
//			verify(mockJankenGame.constructed().get(0), times(1)).action();
		}catch(Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
