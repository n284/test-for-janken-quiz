package jp.co.ginga.util.exception;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

public class ApplicationExceptionTest {
	//テストデータ
	private String message = "エラーが発生しました";

	@InjectMocks
	private ApplicationException exception = new ApplicationException(message);

	/**
	 * testConstructor001 正常系
	 * public ApplicationException(String sysMsg)
	 * 引数の値がフィールドに代入されることを確認
	 */
	@Test
	public void testConstructor001() {
		try {
			Field sysMsgField = ApplicationException.class.getDeclaredField("sysMsg");
			sysMsgField.setAccessible(true);
			//テストメソッド
			ApplicationException e = new ApplicationException(this.message);

			//検証
			String result = String.valueOf(sysMsgField.get(e));

			assertEquals(this.message, result);

		}catch(NoSuchFieldException | SecurityException | NullPointerException| IllegalArgumentException | IllegalAccessException  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testConstructor001 正常系
	 * public String getSysMsg()
	 * フィールドの値が取り出されることを確認する
	 */
	@Test
	public void testGetSysMsg001() {
		//テストメソッド
		String result = this.exception.getSysMsg();
		//検証
		assertEquals(this.message, result);
	}
}
