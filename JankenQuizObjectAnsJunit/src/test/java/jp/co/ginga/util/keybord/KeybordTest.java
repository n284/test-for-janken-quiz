package jp.co.ginga.util.keybord;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

import jp.co.ginga.util.exception.SystemException;

public class KeybordTest {

	//テストデータ
	private String input1 = "hello";
	private String errorMassage1 = "キーボード接続エラーです。ゲームを終了します。";
	private int from = 1;
	private int to = 3;

	/**
	 * testGetBufferedReaderInstance001 正常系
	 * brフィールドにnullが代入されている場合、新しくインスタンス化したものをフィールドに代入し、戻り値として返しているかを確認
	 */
	@Test
	public void testGetBufferedReaderInstance001() {
		try{
			Method method = Keybord.class.getDeclaredMethod("getBufferedReaderInstance");
			Field brField = Keybord.class.getDeclaredField("br");
			method.setAccessible(true);
			brField.setAccessible(true);
			brField.set(Keybord.class, null);

			//テストメソッド
			Object result =method.invoke(Keybord.class);

			//検証
			BufferedReader br = (BufferedReader) brField.get(Keybord.class);
			assertNotNull(result);
			assertEquals(BufferedReader.class, result.getClass());
			assertEquals(br, (BufferedReader) result);

		}catch(NoSuchMethodException | SecurityException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testGetBufferedReaderInstance002 正常系
	 * brフィールドにインスタンスが代入されている場合、インスタンス生成せずそのフィールドを返しているかを確認
	 */
	@Test
	public void testGetBufferedReaderInstance002() {
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			Method method = Keybord.class.getDeclaredMethod("getBufferedReaderInstance");
			Field brField = Keybord.class.getDeclaredField("br");
			method.setAccessible(true);
			brField.setAccessible(true);
			brField.set(Keybord.class, br);

			//テストメソッド
			BufferedReader result = (BufferedReader) method.invoke(Keybord.class);
			//検証

			assertNotNull(result);
			assertEquals(br, (BufferedReader) result);

		}catch(NoSuchMethodException | SecurityException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testGetString001 正常系
	 * public static String getString() throws SystemException
	 * 入力した値が変えることを確認する
	 */
	@Test
	public void testGetString001() {
		try {
			Method method = Keybord.class.getDeclaredMethod("getString");
			Field brField = Keybord.class.getDeclaredField("br");
			method.setAccessible(true);
			brField.setAccessible(true);
			//モック化
			BufferedReader br = mock(BufferedReader.class);
			when(br.readLine()).thenReturn(this.input1);
			brField.set(Keybord.class, br);
			//テストメソッド
			String result = Keybord.getString();
			assertEquals(result, this.input1);

		}catch(Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testGetString002 正常系
	 * public static String getString() throws SystemException
	 * 入力した値が変えることを確認する
	 */
	@Test
	public void testGetString002() {
		try {
			Method method = Keybord.class.getDeclaredMethod("getString");
			Field brField = Keybord.class.getDeclaredField("br");
			method.setAccessible(true);
			brField.setAccessible(true);
			//モック化
			BufferedReader br = mock(BufferedReader.class);
			when(br.readLine()).thenThrow(new IOException());
			brField.set(Keybord.class, br);
			//テストメソッド
			SystemException e = assertThrows(SystemException.class, () -> Keybord.getString());
			assertEquals(e.getSysMsg(), this.errorMassage1);

		}catch(Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testGetStringHasRange001 正常系
	 * public static String getString(int from, int to) throws SystemException, ApplicationException
	 * 入力した値が変えることを確認する
	 */
	@Test
	public void testGetStringHasRange001() {
		try {
			Method method = Keybord.class.getDeclaredMethod("getString");
			Field brField = Keybord.class.getDeclaredField("br");
			method.setAccessible(true);
			brField.setAccessible(true);
			//モック化
			BufferedReader br = mock(BufferedReader.class);
			when(br.readLine()).thenThrow(new IOException());
			brField.set(Keybord.class, br);
			//テストメソッド
			SystemException e = assertThrows(SystemException.class, () -> Keybord.getString());
			assertEquals(e.getSysMsg(), this.errorMassage1);

		}catch(Exception e) {
			e.printStackTrace();
			fail();
		}
	}


}
