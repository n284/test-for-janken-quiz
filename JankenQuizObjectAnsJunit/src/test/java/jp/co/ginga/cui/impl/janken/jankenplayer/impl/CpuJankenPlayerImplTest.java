package jp.co.ginga.cui.impl.janken.jankenplayer.impl;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockedConstruction;

import jp.co.ginga.cui.impl.janken.JankenParam;
import jp.co.ginga.util.exception.ApplicationException;
import jp.co.ginga.util.exception.SystemException;

/**
 * じゃんけんプレーヤ CPU
 *
 *
 */
public class CpuJankenPlayerImplTest {
	//テストデータ
	private int rock = JankenParam.ROCK.getInt();
	private int scissors = JankenParam.SCISSORS.getInt();
	private int paper = JankenParam.PAPER.getInt();
	private int illegalValue = -1;
	private int zeroValue = 0;
	private String playerName = "CPU1";
	private String nullPlayerName = null;
	private int playerHand = rock;
	private int notSetting = 0;
	private int from = rock;
	private int to = paper;
	private String errorMessage = "パラメーターの値が不正です。";

	@InjectMocks
	private CpuJankenPlayerImpl player = new CpuJankenPlayerImpl(this.playerName);

	/**
	 * testConstructor001 正常系
	 * CpuJankenPlayerImpl(String playerName)
	 * 普通の文字列が渡される
	 */
	@Test
	public void testConstructor001() {
		try {
			//テストメソッド
			CpuJankenPlayerImpl player = new CpuJankenPlayerImpl(this.playerName);

			//検証
			Field playerNameField = CpuJankenPlayerImpl.class.getDeclaredField("playerName");
			playerNameField.setAccessible(true);
			String result = String.valueOf(playerNameField.get(player));

			assertEquals(this.playerName, result);

		}catch(NoSuchFieldException | SecurityException | NullPointerException| IllegalArgumentException | IllegalAccessException  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testConstructor002 異常系
	 * CpuJankenPlayerImpl(String playerName)
	 * nullが渡される
	 */
	@Test
	public void testConstructor002() {
		try {
			//テストメソッド
			CpuJankenPlayerImpl player = new CpuJankenPlayerImpl(this.nullPlayerName);

			//検証
			Field playerNameField = CpuJankenPlayerImpl.class.getDeclaredField("playerName");
			playerNameField.setAccessible(true);
			String result = (String) playerNameField.get(player);

			assertNull(result);

		}catch(NoSuchFieldException | SecurityException | NullPointerException| IllegalArgumentException | IllegalAccessException  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testGetPlayerName001 正常系
	 * String getPlayerName()
	 * playerNameフィールドに普通の文字列が代入されている
	 */
	@Test
	public void testGetPlayerName001() {
		try {
			Field playerNameField = CpuJankenPlayerImpl.class.getDeclaredField("playerName");
			playerNameField.setAccessible(true);
			playerNameField.set(this.player, this.playerName);

			//テストメソッド
			String result = this.player.getPlayerName();

			//検証
			assertEquals(this.playerName, result);

		}catch(NoSuchFieldException | SecurityException | NullPointerException| IllegalArgumentException | IllegalAccessException  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testGetPlayerName002 異常系
	 * String getPlayerName()
	 * playerNameフィールドに普通の文字列が代入されていない
	 */
	@Test
	public void testGetPlayerName002() {
		try {
			Field playerNameField = CpuJankenPlayerImpl.class.getDeclaredField("playerName");
			playerNameField.setAccessible(true);
			playerNameField.set(this.player, this.nullPlayerName);

			//テストメソッド
			String result = player.getPlayerName();

			//検証
			assertNull(result);

		}catch(NoSuchFieldException | SecurityException | NullPointerException| IllegalArgumentException | IllegalAccessException  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testGetJankenHand001 正常系
	 * int getJankenHand()
	 * playerHandフィールドに値が代入されている
	 */
	@Test
	public void testGetJankenHand001() {
		try {
			Field playerHandField = CpuJankenPlayerImpl.class.getDeclaredField("playerHand");
			playerHandField.setAccessible(true);
			playerHandField.set(this.player, this.playerHand);

			//テストメソッド
			int result = this.player.getJankenHand();

			//検証
			assertEquals(result, this.playerHand);

		}catch(NoSuchFieldException | SecurityException | NullPointerException| IllegalArgumentException | IllegalAccessException  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testGetJankenHand001 異常系
	 * int getJankenHand()
	 * playerHandフィールドに値が代入されていない
	 */
	@Test
	public void testGetJankenHand002() {

		//テストメソッド
		int result = this.player.getJankenHand();

		//検証
		assertEquals(result, this.notSetting);
	}

	/**
	 * testSelectJankenHand001 正常系
	 * void selectJankenHand() throws SystemException
	 * playerHandフィールドにグーの値を代入する
	 */
	@Test
	public void testSelectJankenHand001() {
		//モック化
		try (MockedConstruction<Random> mockRandom = mockConstruction(Random.class, (random, context) -> when(random.nextInt(this.to)).thenReturn(this.rock-1))){
			//テストメソッド
			this.player.selectJankenHand();

			//検証
			Field playerHandField = CpuJankenPlayerImpl.class.getDeclaredField("playerHand");
			playerHandField.setAccessible(true);
			int playerHand = (Integer) playerHandField.get(this.player);

			assertEquals(playerHand, this.rock);
			assertEquals(mockRandom.constructed().size(), 1);
			verify(mockRandom.constructed().get(0), times(1)).nextInt(this.to);

		}catch(NoSuchFieldException | SecurityException | NullPointerException| IllegalArgumentException | IllegalAccessException | SystemException | ApplicationException e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testSelectJankenHand002 正常系
	 * void selectJankenHand() throws SystemException
	 * playerHandフィールドにチョキの値を代入する
	 */
	@Test
	public void testSelectJankenHand002() {
		//モック化
		try (MockedConstruction<Random> mockRandom = mockConstruction(Random.class, (random, context) -> when(random.nextInt(this.to)).thenReturn(this.scissors-1))){
			//テストメソッド
			this.player.selectJankenHand();

			//検証
			Field playerHandField = CpuJankenPlayerImpl.class.getDeclaredField("playerHand");
			playerHandField.setAccessible(true);
			int playerHand = (Integer) playerHandField.get(this.player);

			assertEquals(playerHand, this.scissors);
			assertEquals(mockRandom.constructed().size(), 1);
			verify(mockRandom.constructed().get(0), times(1)).nextInt(this.to);

		}catch(NoSuchFieldException | SecurityException | NullPointerException| IllegalArgumentException | IllegalAccessException | SystemException | ApplicationException e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testSelectJankenHand003 正常系
	 * void selectJankenHand() throws SystemException
	 * playerHandフィールドにパーの値を代入する
	 */
	@Test
	public void testSelectJankenHand003() {
		//モック化
		try (MockedConstruction<Random> mockRandom = mockConstruction(Random.class, (random, context) -> when(random.nextInt(this.to)).thenReturn(this.paper-1))){
			//テストメソッド
			this.player.selectJankenHand();

			//検証
			Field playerHandField = CpuJankenPlayerImpl.class.getDeclaredField("playerHand");
			playerHandField.setAccessible(true);
			int playerHand = (Integer) playerHandField.get(this.player);

			assertEquals(playerHand, this.paper);
			assertEquals(mockRandom.constructed().size(), 1);
			verify(mockRandom.constructed().get(0), times(1)).nextInt(this.to);

		}catch(NoSuchFieldException | SecurityException | NullPointerException| IllegalArgumentException | IllegalAccessException | SystemException | ApplicationException e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testSelectJankenHand004 異常系
	 * void selectJankenHand() throws SystemException
	 * KeybordのgetIntから不正な値が返され、JankenParamのgetEnumに渡されたときNullPointerExceptionが発生する
	 */
	@Test
	public void testSelectJankenHand004() {
		//モック化
		try (MockedConstruction<Random> mockRandom = mockConstruction(Random.class, (random, context) -> when(random.nextInt(this.to)).thenReturn(this.illegalValue-1))){

			//テストメソッド 検証
			assertThrows(NullPointerException.class, () -> this.player.selectJankenHand());
			assertEquals(mockRandom.constructed().size(), 1);
			verify(mockRandom.constructed().get(0), times(1)).nextInt(this.to);
		}
	}

	/**
	 * testSelectJankenHand005 異常系
	 * void selectJankenHand() throws SystemException
	 * KeybordのgetIntから不正な値が返されかつ0であった場合、JankenParamのgetEnumに渡されたらDRAWが返されdefaultに処理が移る
	 */
	@Test
	public void testSelectJankenHand005() {
		//モック化
		try (MockedConstruction<Random> mockRandom = mockConstruction(Random.class, (random, context) -> when(random.nextInt(this.to)).thenReturn(this.zeroValue-1))){
			//テストメソッド 検証
			SystemException e = assertThrows(SystemException.class, () -> this.player.selectJankenHand());
			assertEquals(this.errorMessage, e.getSysMsg());
			assertEquals(mockRandom.constructed().size(), 1);
			verify(mockRandom.constructed().get(0), times(1)).nextInt(this.to);
		}
	}

}
