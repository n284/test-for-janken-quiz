package jp.co.ginga.cui.impl.janken.jankenplayer.impl;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;

import jp.co.ginga.cui.impl.janken.JankenParam;
import jp.co.ginga.util.exception.ApplicationException;
import jp.co.ginga.util.exception.SystemException;
import jp.co.ginga.util.keybord.Keybord;

/**
 * じゃんけんプレーヤ 人間
 * @author yoshi
 *
 */

public class HumanJankenPlayerImplTest{
	//テストデータ
	private int rock = JankenParam.ROCK.getInt();
	private int scissors = JankenParam.SCISSORS.getInt();
	private int paper = JankenParam.PAPER.getInt();
	private int illegalValue = -1;
	private int zeroValue = 0;
	private String playerName = "プレイヤー1";
	private String nullPlayerName = null;
	private int playerHand = rock;
	private int notSetting = 0;
	private int from = rock;
	private int to = paper;
	private String errorMessage = "パラメーターの値が不正です。";

	@InjectMocks
	private HumanJankenPlayerImpl player = new HumanJankenPlayerImpl(this.playerName);

	/**
	 * testConstructor001 正常系
	 * HumanJankenPlayerImpl(String playerName)
	 * 普通の文字列が渡される
	 */
	@Test
	public void testConstructor001() {
		try {
			//テストメソッド
			HumanJankenPlayerImpl player = new HumanJankenPlayerImpl(this.playerName);

			//検証
			Field playerNameField = HumanJankenPlayerImpl.class.getDeclaredField("playerName");
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
	 * HumanJankenPlayerImpl(String playerName)
	 * nullが渡される
	 */
	@Test
	public void testConstructor002() {
		try {
			//テストメソッド
			HumanJankenPlayerImpl player = new HumanJankenPlayerImpl(this.nullPlayerName);

			//検証
			Field playerNameField = HumanJankenPlayerImpl.class.getDeclaredField("playerName");
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
			Field playerNameField = HumanJankenPlayerImpl.class.getDeclaredField("playerName");
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
			Field playerNameField = HumanJankenPlayerImpl.class.getDeclaredField("playerName");
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
			Field playerHandField = HumanJankenPlayerImpl.class.getDeclaredField("playerHand");
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
	 * playerHandフィールドにグーの値が代入する
	 */
	@Test
	public void testSelectJankenHand001() {
		//モック化
		try(MockedStatic<Keybord> mockKeybord = mockStatic(Keybord.class)){
			mockKeybord.when(() -> Keybord.getInt(this.from, this.to)).thenReturn(this.rock);

			//テストメソッド
			this.player.selectJankenHand();

			//検証
			Field playerHandField = HumanJankenPlayerImpl.class.getDeclaredField("playerHand");
			playerHandField.setAccessible(true);
			int playerHand = (Integer) playerHandField.get(this.player);

			assertEquals(playerHand, this.rock);
			mockKeybord.verify(() -> Keybord.getInt(this.from, this.to), times(1));

		}catch(NoSuchFieldException | SecurityException | NullPointerException| IllegalArgumentException | IllegalAccessException | SystemException | ApplicationException e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testSelectJankenHand002 正常系
	 * void selectJankenHand() throws SystemException
	 * playerHandフィールドにチョキの値が代入する
	 */
	@Test
	public void testSelectJankenHand002() {
		try(MockedStatic<Keybord> mockKeybord = mockStatic(Keybord.class)){
			mockKeybord.when(() -> Keybord.getInt(this.from, this.to)).thenReturn(this.scissors);

			//テストメソッド
			this.player.selectJankenHand();

			//検証
			Field playerHandField = HumanJankenPlayerImpl.class.getDeclaredField("playerHand");
			playerHandField.setAccessible(true);
			int playerHand = (Integer) playerHandField.get(this.player);

			assertEquals(playerHand, this.scissors);
			mockKeybord.verify(() -> Keybord.getInt(this.from, this.to), times(1));

		}catch(NoSuchFieldException | SecurityException | NullPointerException| IllegalArgumentException | IllegalAccessException | SystemException | ApplicationException e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testSelectJankenHand002 正常系
	 * void selectJankenHand() throws SystemException
	 * playerHandフィールドにパーの値が代入する
	 */
	@Test
	public void testSelectJankenHand003() {
		try(MockedStatic<Keybord> mockKeybord = mockStatic(Keybord.class)){
			mockKeybord.when(() -> Keybord.getInt(this.from, this.to)).thenReturn(this.paper);

			//テストメソッド
			this.player.selectJankenHand();

			//検証
			Field playerHandField = HumanJankenPlayerImpl.class.getDeclaredField("playerHand");
			playerHandField.setAccessible(true);
			int playerHand = (Integer) playerHandField.get(this.player);

			assertEquals(playerHand, this.paper);
			mockKeybord.verify(() -> Keybord.getInt(this.from, this.to), times(1));

		}catch(NoSuchFieldException | SecurityException | NullPointerException| IllegalArgumentException | IllegalAccessException | SystemException | ApplicationException e) {
			e.printStackTrace();
			fail();
		}
	}
	/**
	 * testSelectJankenHand004 正常系
	 * void selectJankenHand() throws SystemException
	 * KeybordのgetIntで不正な値が入力されApplicationExceptionが発生し、再度入力が求められて２回目に正常な値を入力する
	 */
	@Test
	public void testSelectJankenHand004() {
		try(MockedStatic<Keybord> mockKeybord = mockStatic(Keybord.class)){
			mockKeybord.when(() -> Keybord.getInt(this.from, this.to)).thenThrow(new ApplicationException(null)).thenReturn(this.rock);

			//テストメソッド
			this.player.selectJankenHand();

			//検証
			Field playerHandField = HumanJankenPlayerImpl.class.getDeclaredField("playerHand");
			playerHandField.setAccessible(true);
			int playerHand = (Integer) playerHandField.get(this.player);

			assertEquals(playerHand, this.rock);
			mockKeybord.verify(() -> Keybord.getInt(this.from, this.to), times(2));

		}catch(NoSuchFieldException | SecurityException | NullPointerException| IllegalArgumentException | IllegalAccessException | SystemException | ApplicationException e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testSelectJankenHand005 異常系
	 * void selectJankenHand() throws SystemException
	 * KeybordのgetIntから不正な値が返され、JankenParamのgetEnumに渡されたときNullPointerExceptionが発生する
	 */
	@Test
	public void testSelectJankenHand005() {
		try(MockedStatic<Keybord> mockKeybord = mockStatic(Keybord.class)){
			mockKeybord.when(() -> Keybord.getInt(this.from, this.to)).thenReturn(this.illegalValue);

			//テストメソッド 検証
			assertThrows(NullPointerException.class, () -> this.player.selectJankenHand());
			mockKeybord.verify(() -> Keybord.getInt(this.from, this.to), times(1));
		}
	}

	/**
	 * testSelectJankenHand006 異常系
	 * void selectJankenHand() throws SystemException
	 * KeybordのgetIntから不正な値が返されかつ0であった場合、JankenParamのgetEnumに渡されたらDRAWが返されdefaultに処理が移る
	 */
	@Test
	public void testSelectJankenHand006() {
		try(MockedStatic<Keybord> mockKeybord = mockStatic(Keybord.class)){
			mockKeybord.when(() -> Keybord.getInt(this.from, this.to)).thenReturn(this.zeroValue);

			//テストメソッド 検証
			SystemException e = assertThrows(SystemException.class, () -> this.player.selectJankenHand());
			assertEquals(this.errorMessage, e.getSysMsg());
			mockKeybord.verify(() -> Keybord.getInt(this.from, this.to), times(1));
		}
	}
}
