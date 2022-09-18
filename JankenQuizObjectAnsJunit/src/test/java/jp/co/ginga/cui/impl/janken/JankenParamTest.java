package jp.co.ginga.cui.impl.janken;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;

/**
 * JankenParam ENUM
 * @author yoshi
 *
 */
public class JankenParamTest {

	//テストデータ
		private int rock = 1;
		private int scissors = 2;
		private int paper = 3;
		private int draw = 0;
		private int illegalValue = -1;

//		private Set<JankenParam> completeParams = new HashSet<JankenParam>(
//				Arrays.asList(JankenParam.DRAW, JankenParam.ROCK, JankenParam.SCISSORS, JankenParam.PAPER )
//		);
//
//		private Set<JankenParam> imcompleteParams = new HashSet<JankenParam>(
//				Arrays.asList(JankenParam.ROCK, JankenParam.SCISSORS, JankenParam.PAPER )
//		);

	/**
	 * testConstructor001 正常系
	 * private JankenParam(int enumValue)
	 * 1が代入されたROCKが生成されるか
	 */
	@Test
	public void testConstructor001() {
		try {
			Field enumValueField = JankenParam.class.getDeclaredField("enumValue");
			enumValueField.setAccessible(true);
			//テストメソッド
			JankenParam param = JankenParam.ROCK;

			//検証
			int result = (int) enumValueField.get(param);

			assertEquals(this.rock, result);

		}catch(NoSuchFieldException | SecurityException | NullPointerException| IllegalArgumentException | IllegalAccessException  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testConstructor002 正常系
	 * private JankenParam(int enumValue)
	 * 2が代入されたSCISSORSが生成されるか
	 */
	@Test
	public void testConstructor002() {
		try {
			Field enumValueField = JankenParam.class.getDeclaredField("enumValue");
			enumValueField.setAccessible(true);
			//テストメソッド
			JankenParam param = JankenParam.SCISSORS;

			//検証
			int result = (int) enumValueField.get(param);

			assertEquals(this.scissors, result);

		}catch(NoSuchFieldException | SecurityException | NullPointerException| IllegalArgumentException | IllegalAccessException  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testConstructor003 正常系
	 * private JankenParam(int enumValue)
	 * 3が代入されたPAPERが生成されるか
	 */
	@Test
	public void testConstructor003() {
		try {
			Field enumValueField = JankenParam.class.getDeclaredField("enumValue");
			enumValueField.setAccessible(true);
			//テストメソッド
			JankenParam param = JankenParam.PAPER;

			//検証
			int result = (int) enumValueField.get(param);

			assertEquals(this.paper, result);

		}catch(NoSuchFieldException | SecurityException | NullPointerException| IllegalArgumentException | IllegalAccessException  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testConstructor004 正常系
	 * private JankenParam(int enumValue)
	 * 0が代入されたDRAWが生成されるか
	 */
	@Test
	public void testConstructor004() {
		try {
			Field enumValueField = JankenParam.class.getDeclaredField("enumValue");
			enumValueField.setAccessible(true);
			//テストメソッド
			JankenParam param = JankenParam.DRAW;

			//検証
			int result = (int) enumValueField.get(param);

			assertEquals(this.draw, result);

		}catch(NoSuchFieldException | SecurityException | NullPointerException| IllegalArgumentException | IllegalAccessException  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testGetInt001 正常系
	 * public int getInt()
	 * フィールドの値が返されるかを確認
	 */
	@Test
	public void testGetInt001() {
		JankenParam param = JankenParam.ROCK;
		//テストメソッド
		int result = param.getInt();
		//検証
		assertEquals(this.rock, result);
	}

	/**
	 * testGetEnum001 正常系
	 * public static JankenParam getEnum(final int value)
	 * 引数と同じ値のenumを取り出せるかを確認
	 */
	@Test
	public void testGetEnum001() {
		//テストメソッド
		JankenParam result = JankenParam.getEnum(this.rock);
		//検証
		assertEquals(JankenParam.ROCK, result);
		assertEquals(this.rock, result.getInt());
	}

	/**
	 * testGetEnum002 異常系
	 * public static JankenParam getEnum(final int value)
	 * 引数に範囲外の値を渡し、nullが戻されることをを確認
	 */
	@Test
	public void testGetEnum002() {
		//テストメソッド
		JankenParam result = JankenParam.getEnum(this.illegalValue);
		//検証
		assertNull(result);
	}

}