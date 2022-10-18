package jp.co.ginga.cui.impl.janken;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import jp.co.ginga.cui.impl.janken.jankenplayer.JankenPlayer;
import jp.co.ginga.cui.impl.janken.jankenplayer.impl.CpuJankenPlayerImpl;
import jp.co.ginga.cui.impl.janken.jankenplayer.impl.HumanJankenPlayerImpl;
import jp.co.ginga.util.exception.ApplicationException;
import jp.co.ginga.util.exception.SystemException;
import jp.co.ginga.util.keybord.Keybord;
import jp.co.ginga.util.properties.MessageProperties;

/**
 * CUIじゃんけん実装クラス
 * @author yoshi
 *
 */
public class JankenCuiGameApplicationImplTest{

	/*
	 * ・テストを書く際はJavaDocでどんなテストを行うのか、テスト内容と検証内容を記入する
	 * ・テストしたいメソッドの処理を理解して戻り値がどうなっていればいいかを把握する
	 * ・処理を把握する際に正常系のテストだけでなく異常系のテストも考える
	 * ・C0(命令網羅)、C1(分岐網羅)、C2(条件網羅)が100%になるようにテストケースを考える
	 * ・テストする際に必要になるデータはメソッドごとに分けると把握しやすい(共通して使うデータはフィールドに定義してもよい)
	 * ・Junit5でmock化できるのはprivateメソッド以外可能(privateメソッドはmock化できないため、package privateでアクセスできるようにしている)
	 */

	/*
	 * privateメソッドはmock化できないのでinit以外のすべてのアクセス修飾子をpackage privateに変える
	 * テスト対象クラス
	 */
	private JankenCuiGameApplicationImpl jankenCuiGameApplicationImpl = new JankenCuiGameApplicationImpl();

	/**
	 * testAction001 正常系
	 * public void action() throws SystemException
	 * --確認事項--
	 * isCheckJankenPlayerCountメソッドでtrueを返し、
	 * judgeメソッドでROCKを返し、
	 * hasGameContinueメソッドでfalseを返ようにして正常に処理が進む
	 * --条件--
	 * initはprivateメソッドなので呼び出す
	 * playerListはnull
	 * 他のメソッドはmock化
	 * actionメソッドは正常に呼び出すためにspy化
	 * --検証項目--
	 * 1. SystemExceptionも含めて例外が発生しないことを確認
	 * 2. メソッドの呼び出し回数
	 */
	@Test
	public void testAction001() {

		try{
			//テストデータ
			int rock = JankenParam.ROCK.getInt();

			//スパイ・モック化
			JankenCuiGameApplicationImpl spyGame = spy(JankenCuiGameApplicationImpl.class);
			doNothing().when(spyGame).createCpuOfJankenPlayer();
			doNothing().when(spyGame).createHumanOfJankenPlayer();
			doReturn(true).when(spyGame).isCheckJankenPlayerCount();
			doNothing().when(spyGame).selectJankenHand();
			doReturn(rock).when(spyGame).judge();
			doNothing().when(spyGame).viewWinner();
			doReturn(false).when(spyGame).hasGameContinue();

			//準備
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			playerListField.setAccessible(true);
			playerListField.set(spyGame, null);

			//テストメソッド
			spyGame.action();

			//検証
			verify(spyGame, times(1)).createCpuOfJankenPlayer();
			verify(spyGame, times(1)).createHumanOfJankenPlayer();
			verify(spyGame, times(1)).isCheckJankenPlayerCount();
			verify(spyGame, times(1)).selectJankenHand();
			verify(spyGame, times(1)).judge();
			verify(spyGame, times(1)).viewWinner();
			verify(spyGame, times(1)).hasGameContinue();

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testAction002 正常系
	 * public void action() throws SystemException
	 * --確認事項--
	 * isCheckJankenPlayerCountメソッドでtrueを返し、
	 * judgeメソッドでSCISSORSを返し、
	 * hasGameContinueメソッドでfalseを返ようにして正常に処理が進む
	 * --条件--
	 * initはprivateメソッドなので呼び出す
	 * playerListはnull
	 * 他のメソッドはmock化
	 * actionメソッドは正常に呼び出すためにspy化
	 * --検証項目--
	 * 1. SystemExceptionも含めて例外が発生しないことを確認
	 * 2. メソッドの呼び出し回数
	 */
	@Test
	public void testAction002() {

		try{
			//テストデータ
			int scissors = JankenParam.SCISSORS.getInt();

			//スパイ・モック化
			JankenCuiGameApplicationImpl spyGame = spy(JankenCuiGameApplicationImpl.class);
			doNothing().when(spyGame).createCpuOfJankenPlayer();
			doNothing().when(spyGame).createHumanOfJankenPlayer();
			doReturn(true).when(spyGame).isCheckJankenPlayerCount();
			doNothing().when(spyGame).selectJankenHand();
			doReturn(scissors).when(spyGame).judge();
			doNothing().when(spyGame).viewWinner();
			doReturn(false).when(spyGame).hasGameContinue();

			//準備
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			playerListField.setAccessible(true);
			playerListField.set(spyGame, null);

			//テストメソッド
			spyGame.action();

			//検証
			verify(spyGame, times(1)).createCpuOfJankenPlayer();
			verify(spyGame, times(1)).createHumanOfJankenPlayer();
			verify(spyGame, times(1)).isCheckJankenPlayerCount();
			verify(spyGame, times(1)).selectJankenHand();
			verify(spyGame, times(1)).judge();
			verify(spyGame, times(1)).viewWinner();
			verify(spyGame, times(1)).hasGameContinue();

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testAction003 正常系
	 * public void action() throws SystemException
	 * --確認事項--
	 * isCheckJankenPlayerCountメソッドでtrueを返し、
	 * judgeメソッドでPAPERを返し、
	 * hasGameContinueメソッドでfalseを返ようにして正常に処理が進む
	 * --条件--
	 * initはprivateメソッドなので呼び出す
	 * playerListはnull
	 * 他のメソッドはmock化
	 * actionメソッドは正常に呼び出すためにspy化
	 * --検証項目--
	 * 1. SystemExceptionも含めて例外が発生しないことを確認
	 * 2. メソッドの呼び出し回数
	 */
	@Test
	public void testAction003() {

		try{
			//テストデータ
			int paper = JankenParam.PAPER.getInt();

			//スパイ・モック化
			JankenCuiGameApplicationImpl spyGame = spy(JankenCuiGameApplicationImpl.class);
			doNothing().when(spyGame).createCpuOfJankenPlayer();
			doNothing().when(spyGame).createHumanOfJankenPlayer();
			doReturn(true).when(spyGame).isCheckJankenPlayerCount();
			doNothing().when(spyGame).selectJankenHand();
			doReturn(paper).when(spyGame).judge();
			doNothing().when(spyGame).viewWinner();
			doReturn(false).when(spyGame).hasGameContinue();

			//準備
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			playerListField.setAccessible(true);
			playerListField.set(spyGame, null);

			//テストメソッド
			spyGame.action();

			//検証
			verify(spyGame, times(1)).createCpuOfJankenPlayer();
			verify(spyGame, times(1)).createHumanOfJankenPlayer();
			verify(spyGame, times(1)).isCheckJankenPlayerCount();
			verify(spyGame, times(1)).selectJankenHand();
			verify(spyGame, times(1)).judge();
			verify(spyGame, times(1)).viewWinner();
			verify(spyGame, times(1)).hasGameContinue();

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testAction004 正常系
	 * public void action() throws SystemException
	 * --確認事項--
	 * isCheckJankenPlayerCountメソッドでtrueを返し、
	 * judgeメソッドで一回目をDRAW、2回目をROCKを返し、
	 * hasGameContinueメソッドでfalseを返ようにして正常に処理が進む
	 * --条件--
	 * initはprivateメソッドなので呼び出す
	 * playerListはnull
	 * 他のメソッドはmock化
	 * actionメソッドは正常に呼び出すためにspy化
	 * --検証項目--
	 * 1. SystemExceptionも含めて例外が発生しないことを確認
	 * 2. メソッドの呼び出し回数
	 */
	@Test
	public void testAction004() {

		try{
			//テストデータ
			int rock = JankenParam.ROCK.getInt();
			int draw = JankenParam.DRAW.getInt();

			//スパイ・モック化
			JankenCuiGameApplicationImpl spyGame = spy(JankenCuiGameApplicationImpl.class);
			doNothing().when(spyGame).createCpuOfJankenPlayer();
			doNothing().when(spyGame).createHumanOfJankenPlayer();
			doReturn(true).when(spyGame).isCheckJankenPlayerCount();
			doNothing().when(spyGame).selectJankenHand();
			doReturn(draw).doReturn(rock).when(spyGame).judge();
			doNothing().when(spyGame).viewWinner();
			doReturn(false).when(spyGame).hasGameContinue();

			//準備
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			playerListField.setAccessible(true);
			playerListField.set(spyGame, null);

			//テストメソッド
			spyGame.action();

			//検証
			verify(spyGame, times(1)).createCpuOfJankenPlayer();
			verify(spyGame, times(1)).createHumanOfJankenPlayer();
			verify(spyGame, times(1)).isCheckJankenPlayerCount();
			verify(spyGame, times(2)).selectJankenHand();
			verify(spyGame, times(2)).judge();
			verify(spyGame, times(1)).viewWinner();
			verify(spyGame, times(1)).hasGameContinue();

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testAction005 正常系
	 * public void action() throws SystemException
	 * --確認事項--
	 * isCheckJankenPlayerCountメソッドで1回目でfalse、2回目でtrueを返し、
	 * judgeメソッドでROCKを返し、
	 * hasGameContinueメソッドでfalseを返ようにして正常に処理が進む
	 * --条件--
	 * initはprivateメソッドなので呼び出す
	 * playerListはnull
	 * 他のメソッドはmock化
	 * actionメソッドは正常に呼び出すためにspy化
	 * --検証項目--
	 * 1. SystemExceptionも含めて例外が発生しないことを確認
	 * 2. メソッドの呼び出し回数
	 */
	@Test
	public void testAction005() {

		try{
			//テストデータ
			int rock = JankenParam.ROCK.getInt();

			//スパイ・モック化
			JankenCuiGameApplicationImpl spyGame = spy(JankenCuiGameApplicationImpl.class);
			doNothing().when(spyGame).createCpuOfJankenPlayer();
			doNothing().when(spyGame).createHumanOfJankenPlayer();
			doReturn(false).doReturn(true).when(spyGame).isCheckJankenPlayerCount();
			doNothing().when(spyGame).selectJankenHand();
			doReturn(rock).when(spyGame).judge();
			doNothing().when(spyGame).viewWinner();
			doReturn(false).when(spyGame).hasGameContinue();

			//準備
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			playerListField.setAccessible(true);
			playerListField.set(spyGame, null);

			//テストメソッド
			spyGame.action();

			//検証
			verify(spyGame, times(2)).createCpuOfJankenPlayer();
			verify(spyGame, times(2)).createHumanOfJankenPlayer();
			verify(spyGame, times(2)).isCheckJankenPlayerCount();
			verify(spyGame, times(1)).selectJankenHand();
			verify(spyGame, times(1)).judge();
			verify(spyGame, times(1)).viewWinner();
			verify(spyGame, times(1)).hasGameContinue();

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testAction006 正常系
	 * public void action() throws SystemException
	 * --確認事項--
	 * isCheckJankenPlayerCountメソッドでtrueで返し、
	 * judgeメソッドでROCKを返し、
	 * hasGameContinueメソッドで1回目true、2回目でfalseを返ようにして正常に処理が進む
	 * --条件--
	 * initはprivateメソッドなので呼び出す
	 * playerListはnull
	 * 他のメソッドはmock化
	 * actionメソッドは正常に呼び出すためにspy化
	 * --検証項目--
	 * 1. SystemExceptionも含めて例外が発生しないことを確認
	 */
	@Test
	public void testAction006() {

		try{
			//テストデータ
			int rock = JankenParam.ROCK.getInt();

			//スパイ・モック化
			JankenCuiGameApplicationImpl spyGame = spy(JankenCuiGameApplicationImpl.class);
			doNothing().when(spyGame).createCpuOfJankenPlayer();
			doNothing().when(spyGame).createHumanOfJankenPlayer();
			doReturn(true).when(spyGame).isCheckJankenPlayerCount();
			doNothing().when(spyGame).selectJankenHand();
			doReturn(rock).when(spyGame).judge();
			doNothing().when(spyGame).viewWinner();
			doReturn(true).doReturn(false).when(spyGame).hasGameContinue();

			//準備
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			playerListField.setAccessible(true);
			playerListField.set(spyGame, null);

			//テストメソッド
			spyGame.action();

			//検証
			verify(spyGame, times(2)).createCpuOfJankenPlayer();
			verify(spyGame, times(2)).createHumanOfJankenPlayer();
			verify(spyGame, times(2)).isCheckJankenPlayerCount();
			verify(spyGame, times(2)).selectJankenHand();
			verify(spyGame, times(2)).judge();
			verify(spyGame, times(2)).viewWinner();
			verify(spyGame, times(2)).hasGameContinue();

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testAction007 異常系
	 * public void action() throws SystemException
	 * --確認事項--
	 * getMessageで"janken.msg.start"が見つからない場合、SystemExceptionが発生する
	 * --条件--
	 * initはprivateメソッドなので呼び出す
	 * playerListはnull
	 * getMessageをモック化
	 * actionメソッドは正常に呼び出すためにspy化
	 * --検証項目--
	 * 1. SystemExceptionに"janken.msg.start"が含まれているか
	 */
	@Test
	public void testAction007() {
		//スパイ化
		JankenCuiGameApplicationImpl spyGame = spy(JankenCuiGameApplicationImpl.class);
		//モック化
		try(MockedStatic<MessageProperties> mockMessageProperties = mockStatic(MessageProperties.class)){
			mockMessageProperties.when(() -> MessageProperties.getMessage("janken.msg.start")).thenThrow(new SystemException("janken.msg.start"));

			//準備
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			playerListField.setAccessible(true);
			playerListField.set(spyGame, null);

			//テストメソッド・検証
			SystemException result = assertThrows(SystemException.class, () -> spyGame.action());
			assertEquals("janken.msg.start", result.getSysMsg());

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testAction008 異常系
	 * public void action() throws SystemException
	 * --確認事項--
	 * getMessageで"janken.msg.player.count.error"が見つからない場合、SystemExceptionが発生する
	 * --条件--
	 * initはprivateメソッドなので呼び出す
	 * playerListはnull
	 * getMessageをモック化
	 * actionメソッドは正常に呼び出すためにspy化
	 * --検証項目--
	 * 1. SystemExceptionに"janken.msg.player.count.error"が含まれているか
	 */
	@Test
	public void testAction008() {
		//スパイ化
		JankenCuiGameApplicationImpl spyGame = spy(JankenCuiGameApplicationImpl.class);
		//モック化
		try(MockedStatic<MessageProperties> mockMessageProperties = mockStatic(MessageProperties.class)){
			mockMessageProperties.when(() -> MessageProperties.getMessage("janken.msg.player.count.error")).thenThrow(new SystemException("janken.msg.player.count.error"));
			doNothing().when(spyGame).createCpuOfJankenPlayer();
			doNothing().when(spyGame).createHumanOfJankenPlayer();
			doReturn(false).when(spyGame).isCheckJankenPlayerCount();

			//準備
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			playerListField.setAccessible(true);
			playerListField.set(spyGame, null);

			//テストメソッド・検証
			SystemException result = assertThrows(SystemException.class, () -> spyGame.action());
			assertEquals("janken.msg.player.count.error", result.getSysMsg());
			verify(spyGame, times(1)).createCpuOfJankenPlayer();
			verify(spyGame, times(1)).createHumanOfJankenPlayer();
			verify(spyGame, times(1)).isCheckJankenPlayerCount();

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testAction009 異常系
	 * public void action() throws SystemException
	 * --確認事項--
	 * getMessageで"janken.msg.game.draw"が見つからない場合、SystemExceptionが発生する
	 * --条件--
	 * initはprivateメソッドなので呼び出す
	 * playerListはnull
	 * getMessageをモック化
	 * actionメソッドは正常に呼び出すためにspy化
	 * --検証項目--
	 * 1. SystemExceptionに"janken.msg.game.draw"が含まれているか
	 */
	@Test
	public void testAction009() {
		//テストデータ
		int draw = JankenParam.DRAW.getInt();

		//スパイ化
		JankenCuiGameApplicationImpl spyGame = spy(JankenCuiGameApplicationImpl.class);
		//モック化
		try(MockedStatic<MessageProperties> mockMessageProperties = mockStatic(MessageProperties.class)){
			mockMessageProperties.when(() -> MessageProperties.getMessage("janken.msg.game.draw")).thenThrow(new SystemException("janken.msg.game.draw"));
			doNothing().when(spyGame).createCpuOfJankenPlayer();
			doNothing().when(spyGame).createHumanOfJankenPlayer();
			doReturn(true).when(spyGame).isCheckJankenPlayerCount();
			doReturn(draw).when(spyGame).judge();

			//準備
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			playerListField.setAccessible(true);
			playerListField.set(spyGame, null);

			//テストメソッド・検証
			SystemException result = assertThrows(SystemException.class, () -> spyGame.action());
			assertEquals("janken.msg.game.draw", result.getSysMsg());
			verify(spyGame, times(1)).createCpuOfJankenPlayer();
			verify(spyGame, times(1)).createHumanOfJankenPlayer();
			verify(spyGame, times(1)).isCheckJankenPlayerCount();
			verify(spyGame, times(1)).selectJankenHand();
			verify(spyGame, times(1)).judge();

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testAction010 異常系
	 * public void action() throws SystemException
	 * --確認事項--
	 * getMessageで"janken.msg.end"が見つからない場合、SystemExceptionが発生する
	 * --条件--
	 * initはprivateメソッドなので呼び出す
	 * playerListはnull
	 * getMessageをモック化
	 * actionメソッドは正常に呼び出すためにspy化
	 * --検証項目--
	 * 1. SystemExceptionに"janken.msg.end"が含まれているか
	 */
	@Test
	public void testAction010() {
		//テストデータ
		int rock = JankenParam.ROCK.getInt();

		//スパイ化
		JankenCuiGameApplicationImpl spyGame = spy(JankenCuiGameApplicationImpl.class);
		//モック化
		try(MockedStatic<MessageProperties> mockMessageProperties = mockStatic(MessageProperties.class)){
			mockMessageProperties.when(() -> MessageProperties.getMessage("janken.msg.end")).thenThrow(new SystemException("janken.msg.end"));
			doNothing().when(spyGame).createCpuOfJankenPlayer();
			doNothing().when(spyGame).createHumanOfJankenPlayer();
			doReturn(true).when(spyGame).isCheckJankenPlayerCount();
			doNothing().when(spyGame).selectJankenHand();
			doReturn(rock).when(spyGame).judge();
			doNothing().when(spyGame).viewWinner();
			doReturn(false).when(spyGame).hasGameContinue();

			//準備
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			playerListField.setAccessible(true);
			playerListField.set(spyGame, null);

			//テストメソッド・検証
			SystemException result = assertThrows(SystemException.class, () -> spyGame.action());
			assertEquals("janken.msg.end", result.getSysMsg());
			verify(spyGame, times(1)).createCpuOfJankenPlayer();
			verify(spyGame, times(1)).createHumanOfJankenPlayer();
			verify(spyGame, times(1)).isCheckJankenPlayerCount();
			verify(spyGame, times(1)).selectJankenHand();
			verify(spyGame, times(1)).judge();
			verify(spyGame, times(1)).viewWinner();
			verify(spyGame, times(1)).hasGameContinue();

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testInit001 正常系
	 * private void init()
	 * --確認事項--
	 * playerListがnullの場合、ArrayList<JankenPlayer>のインスタンスを代入する
	 * --条件--
	 *	playerListフィールドの値がnull
	 * --検証項目--
	 * 1. playerListフィールドの値がnullでないこと
	 * 2. playerListフィールドの値がArrayList<JankenPlayer>のインスタンスであるか
	 * 2. playerListフィールドのサイズが0であるか
	 */
	@Test
	public void testInit001() {
		try {
			//準備
			Method method = JankenCuiGameApplicationImpl.class.getDeclaredMethod("init");
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			method.setAccessible(true);
			playerListField.setAccessible(true);
			playerListField.set(this.jankenCuiGameApplicationImpl, null);

			//テストメソッド
			method.invoke(this.jankenCuiGameApplicationImpl);

			//検証
			Object result = playerListField.get(this.jankenCuiGameApplicationImpl);
			assertNotNull(result);
			//ArrayListのインスタンスであるかどうか
			assertEquals(true, result instanceof ArrayList );
			//リストのジェネリクスの型を取り出すのは難しい
			//playerListFieldは現状でplayerListフィールドを指している為それから型を文字列で取り出し、
			//クラスの型名が含まれているかを検証している（無理やり）
			assertEquals(true, playerListField.getGenericType().toString().contains(JankenPlayer.class.getName()));
			//resultはOBject型であるため、上の検証よりListにキャストできる
			assertEquals(((List<?>) result).size(), 0);

		}catch(Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testInit002 正常系
	 * private void init()
	 * --確認事項--
	 * playerListがnullでない場合、リストの要素を空にする
	 * --条件--
	 *	playerListフィールドの値がJankenPlayerインスタンスが追加されたリスト
	 * --検証項目--
	 * 1. playerListフィールドの値がnullでない
	 * 2. playerListフィールドのサイズが0であるか
	 * 3. playerListフィールドの参照先が変更されていないか
	 */
	@Test
	public void testInit002() {
		try {
			//テストデータ
			JankenPlayer humanPlayer1 = new HumanJankenPlayerImpl("プレーヤー1");
			JankenPlayer cpuPlayer1 = new CpuJankenPlayerImpl("CPU1");
			List<JankenPlayer> playerList = new ArrayList<JankenPlayer>(Arrays.asList(humanPlayer1, cpuPlayer1));

			//準備
			Method method = JankenCuiGameApplicationImpl.class.getDeclaredMethod("init");
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			method.setAccessible(true);
			playerListField.setAccessible(true);
			playerListField.set(this.jankenCuiGameApplicationImpl, playerList);

			//テストメソッド
			method.invoke(this.jankenCuiGameApplicationImpl);

			//検証
			@SuppressWarnings("unchecked")
			List<JankenPlayer> result =(List<JankenPlayer>) playerListField.get(this.jankenCuiGameApplicationImpl);
			assertNotNull(result);
			assertEquals(0, result.size());
			assertEquals(playerList, result);

		}catch(Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testCreateHumanOfJankenPlayer001 正常系
	 * void createHumanOfJankenPlayer() throws SystemException
	 * --確認事項--
	 * 入力した数分のHumanPlayerインスタンスを生成してplayerListフィールドに追加する
	 * --条件--
	 * KeybordクラスのgetIntでは2を返すmock
	 *	playerListフィールドの値は空のリスト
	 * --検証項目--
	 * 1. playerListフィールドに2個のHumanPlayerインスタンスが追加されているか
	 * 2. それぞれ追加されたインスタンスが持つデータが指定した値であるか
	 * 3. playerListフィールドの参照先が変更されていないか
	 * 4. getIntメソッドは1回だけ呼び出されているか
	 */
	@Test
	public void testCreateHumanOfJankenPlayer001() {
		//モック化
		//staticメソッドをmock化したい場合は以下のように使用する
		try(MockedStatic<Keybord> mockKeybord = mockStatic(Keybord.class)){
			mockKeybord.when(() -> Keybord.getInt()).thenReturn(2);

			//テストデータ
			List<JankenPlayer> emptyPlayerList = new ArrayList<JankenPlayer>();
			JankenPlayer humanPlayer1 = new HumanJankenPlayerImpl("プレーヤー1");
			JankenPlayer humanPlayer2 = new HumanJankenPlayerImpl("プレーヤー2");

			//準備
			Method method = JankenCuiGameApplicationImpl.class.getDeclaredMethod("createHumanOfJankenPlayer");
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			method.setAccessible(true);
			playerListField.setAccessible(true);
			playerListField.set(this.jankenCuiGameApplicationImpl, emptyPlayerList);

			//テストメソッド
			method.invoke(this.jankenCuiGameApplicationImpl);

			//検証
			@SuppressWarnings("unchecked")
			List<JankenPlayer> result =(List<JankenPlayer>) playerListField.get(this.jankenCuiGameApplicationImpl);
			assertEquals(2, result.size());
			assertEquals(humanPlayer1.getPlayerName(), result.get(0).getPlayerName());
			assertEquals(humanPlayer2.getPlayerName(), result.get(1).getPlayerName());
			assertEquals(emptyPlayerList, result);
			mockKeybord.verify(() -> Keybord.getInt(), times(1));

		}catch(Exception  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testCreateHumanOfJankenPlayer002 正常系
	 * void createHumanOfJankenPlayer() throws SystemException
	 * --確認事項--
	 * 入力した数分のHumanPlayerインスタンスを生成してplayerListフィールドに追加する
	 * --条件--
	 * KeybordクラスのgetIntでは0を返すmock
	 *	playerListフィールドの値は空のリスト
	 * --検証項目--
	 * 1. playerListフィールドに0個のHumanPlayerインスタンスが追加されているか
	 * 2. playerListフィールドの参照先が変更されていないか
	 * 3. getIntメソッドは1回だけ呼び出されているか
	 */
	@Test
	public void testCreateHumanOfJankenPlayer002() {
		//モック化
		try(MockedStatic<Keybord> mockKeybord = mockStatic(Keybord.class)){
			mockKeybord.when(() -> Keybord.getInt()).thenReturn(0);

			//テストデータ
			List<JankenPlayer> emptyPlayerList = new ArrayList<JankenPlayer>();

			//準備
			Method method = JankenCuiGameApplicationImpl.class.getDeclaredMethod("createHumanOfJankenPlayer");
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			method.setAccessible(true);
			playerListField.setAccessible(true);
			playerListField.set(this.jankenCuiGameApplicationImpl, emptyPlayerList);

			//テストメソッド
			method.invoke(this.jankenCuiGameApplicationImpl);

			//検証
			@SuppressWarnings("unchecked")
			List<JankenPlayer> result =(List<JankenPlayer>) playerListField.get(this.jankenCuiGameApplicationImpl);
			assertEquals(0, result.size());
			assertEquals(emptyPlayerList, result);
			mockKeybord.verify(() -> Keybord.getInt(), times(1));

		}catch(Exception  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testCreateHumanOfJankenPlayer003 正常系
	 * void createHumanOfJankenPlayer() throws SystemException
	 * --確認事項--
	 * 不正な値を入力したら再度入力が求められ入力した数分のHumanPlayerインスタンスを生成してplayerListフィールドに追加する
	 * --条件--
	 * KeybordクラスのgetIntでは一回目はApplicationExceptionが発生し、二回目に2を返すmock
	 *	playerListフィールドの値は空のリスト
	 * --検証項目--
	 * 1. playerListフィールドに2個のHumanPlayerインスタンスが追加されているか
	 * 2. それぞれ追加されたインスタンスが持つデータが指定した値であるか
	 * 3. playerListフィールドの参照先が変更されていないか
	 * 4. getIntメソッドは2回だけ呼び出されているか
	 */
	@Test
	public void testCreateHumanOfJankenPlayer003() {
		//モック化
		try(MockedStatic<Keybord> mockKeybord = mockStatic(Keybord.class)){
			mockKeybord.when(() -> Keybord.getInt()).thenThrow(new ApplicationException(null)).thenReturn(2);

			//テストデータ
			List<JankenPlayer> emptyPlayerList = new ArrayList<JankenPlayer>();
			JankenPlayer humanPlayer1 = new HumanJankenPlayerImpl("プレーヤー1");
			JankenPlayer humanPlayer2 = new HumanJankenPlayerImpl("プレーヤー2");

			//準備
			Method method = JankenCuiGameApplicationImpl.class.getDeclaredMethod("createHumanOfJankenPlayer");
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			method.setAccessible(true);
			playerListField.setAccessible(true);
			playerListField.set(this.jankenCuiGameApplicationImpl, emptyPlayerList);

			//テストメソッド
			method.invoke(this.jankenCuiGameApplicationImpl);

			//検証
			@SuppressWarnings("unchecked")
			List<JankenPlayer> result =(List<JankenPlayer>) playerListField.get(this.jankenCuiGameApplicationImpl);
			assertEquals(2, result.size());
			assertEquals(humanPlayer1.getPlayerName(), result.get(0).getPlayerName());
			assertEquals(humanPlayer2.getPlayerName(), result.get(1).getPlayerName());
			assertEquals(emptyPlayerList, result);
			mockKeybord.verify(() -> Keybord.getInt(), times(2));

		}catch(Exception  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testCreateHumanOfJankenPlayer004 異常系
	 * void createHumanOfJankenPlayer() throws SystemException
	 * --確認事項--
	 * playerListフィールドにnullが代入されている場合、NullPointerExceptionが発生する
	 * --条件--
	 * KeybordクラスのgetIntでは2を返すmock
	 *	playerListフィールドの値はnull
	 * --検証項目--
	 * 1. NullPointerExceptionが発生するか
	 * 2. getIntメソッドは1回だけ呼び出されているか
	 */
	@Test
	public void testCreateHumanOfJankenPlayer004() {
		//モック化
		try(MockedStatic<Keybord> mockKeybord = mockStatic(Keybord.class)){
			mockKeybord.when(() -> Keybord.getInt()).thenReturn(2);

			//準備
			Method method = JankenCuiGameApplicationImpl.class.getDeclaredMethod("createHumanOfJankenPlayer");
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			method.setAccessible(true);
			playerListField.setAccessible(true);
			playerListField.set(this.jankenCuiGameApplicationImpl, null);

			//テストメソッド
			InvocationTargetException e = assertThrows(InvocationTargetException.class, () -> method.invoke(this.jankenCuiGameApplicationImpl));

			//検証
			assertEquals(NullPointerException.class , e.getTargetException().getClass());
			mockKeybord.verify(() -> Keybord.getInt(), times(1));

		}catch(Exception  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testCreateCpuOfJankenPlayer001 正常系
	 * void createCpuOfJankenPlayer() throws SystemException
	 * --確認事項--
	 * 入力した数分のCpuPlayerインスタンスを生成してplayerListフィールドに追加する
	 * --条件--
	 * KeybordクラスのgetIntでは2を返すmock
	 *	playerListフィールドの値は空のリスト
	 * --検証項目--
	 * 1. playerListフィールドに2個のCpuPlayerインスタンスが追加されているか
	 * 2. それぞれ追加されたインスタンスが持つデータが指定した値であるか
	 * 3. playerListフィールドの参照先が変更されていないか
	 * 4. getIntメソッドは1回だけ呼び出されているか
	 */
	@Test
	public void testCreateCpuOfJankenPlayer001() {
		//モック化
		try(MockedStatic<Keybord> mockKeybord = mockStatic(Keybord.class)){
			mockKeybord.when(() -> Keybord.getInt()).thenReturn(2);

			//テストデータ
			List<JankenPlayer> emptyPlayerList = new ArrayList<JankenPlayer>();
			JankenPlayer cpuPlayer1 = new HumanJankenPlayerImpl("CPU1");
			JankenPlayer cpuPlayer2 = new HumanJankenPlayerImpl("CPU2");

			//準備
			Method method = JankenCuiGameApplicationImpl.class.getDeclaredMethod("createCpuOfJankenPlayer");
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			method.setAccessible(true);
			playerListField.setAccessible(true);
			playerListField.set(this.jankenCuiGameApplicationImpl, emptyPlayerList);

			//テストメソッド
			method.invoke(this.jankenCuiGameApplicationImpl);

			//検証
			@SuppressWarnings("unchecked")
			List<JankenPlayer> result =(List<JankenPlayer>) playerListField.get(this.jankenCuiGameApplicationImpl);
			assertEquals(2, result.size());
			assertEquals(cpuPlayer1.getPlayerName(), result.get(0).getPlayerName());
			assertEquals(cpuPlayer2.getPlayerName(), result.get(1).getPlayerName());
			assertEquals(emptyPlayerList, result);
			mockKeybord.verify(() -> Keybord.getInt(), times(1));

		}catch(Exception  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testCreateCpuOfJankenPlayer002 正常系
	 * void createCpuOfJankenPlayer() throws SystemException
	 * --確認事項--
	 * 入力した数分のHumanPlayerインスタンスを生成してplayerListフィールドに追加する
	 * --条件--
	 * KeybordクラスのgetIntでは0を返すmock
	 *	playerListフィールドの値は空のリスト
	 * --検証項目--
	 * 1. playerListフィールドに0個のHumanPlayerインスタンスが追加されているか
	 * 2. playerListフィールドの参照先が変更されていないか
	 * 3. getIntメソッドは1回だけ呼び出されているか
	 */
	@Test
	public void testCreateCpuOfJankenPlayer002() {
		//モック化
		try(MockedStatic<Keybord> mockKeybord = mockStatic(Keybord.class)){
			mockKeybord.when(() -> Keybord.getInt()).thenReturn(0);

			//テストデータ
			List<JankenPlayer> emptyPlayerList = new ArrayList<JankenPlayer>();

			//準備
			Method method = JankenCuiGameApplicationImpl.class.getDeclaredMethod("createCpuOfJankenPlayer");
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			method.setAccessible(true);
			playerListField.setAccessible(true);
			playerListField.set(this.jankenCuiGameApplicationImpl, emptyPlayerList);

			//テストメソッド
			method.invoke(this.jankenCuiGameApplicationImpl);

			//検証
			@SuppressWarnings("unchecked")
			List<JankenPlayer> result =(List<JankenPlayer>) playerListField.get(this.jankenCuiGameApplicationImpl);
			assertEquals(result.size(), 0);
			assertEquals(emptyPlayerList, result);
			mockKeybord.verify(() -> Keybord.getInt(), times(1));

		}catch(Exception  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testCreateCpuOfJankenPlayer003 正常系
	 * void createCpuOfJankenPlayer() throws SystemException
	 * --確認事項--
	 * 不正な値を入力したら再度入力が求められ入力した数分のCpuPlayerインスタンスを生成してplayerListフィールドに追加する
	 * --条件--
	 * KeybordクラスのgetIntでは一回目はApplicationExceptionが発生し、二回目に2を返すmock
	 *	playerListフィールドの値は空のリスト
	 * --検証項目--
	 * 1. playerListフィールドに2個のCpuPlayerインスタンスが追加されているか
	 * 2. それぞれ追加されたインスタンスが持つデータが指定した値であるか
	 * 3. playerListフィールドの参照先が変更されていないか
	 * 4. getIntメソッドは2回だけ呼び出されているか
	 */
	@Test
	public void testCreateCpuOfJankenPlayer003() {
		//モック化
		try(MockedStatic<Keybord> mockKeybord = mockStatic(Keybord.class)){
			mockKeybord.when(() -> Keybord.getInt()).thenThrow(new ApplicationException(null)).thenReturn(2);

			//テストデータ
			List<JankenPlayer> emptyPlayerList = new ArrayList<JankenPlayer>();
			JankenPlayer cpuPlayer1 = new HumanJankenPlayerImpl("CPU1");
			JankenPlayer cpuPlayer2 = new HumanJankenPlayerImpl("CPU2");

			//準備
			Method method = JankenCuiGameApplicationImpl.class.getDeclaredMethod("createCpuOfJankenPlayer");
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			method.setAccessible(true);
			playerListField.setAccessible(true);
			playerListField.set(this.jankenCuiGameApplicationImpl, emptyPlayerList);

			//テストメソッド
			method.invoke(this.jankenCuiGameApplicationImpl);

			//検証
			@SuppressWarnings("unchecked")
			List<JankenPlayer> result =(List<JankenPlayer>) playerListField.get(this.jankenCuiGameApplicationImpl);
			assertEquals(result.size(), 2);
			assertEquals(cpuPlayer1.getPlayerName(), result.get(0).getPlayerName());
			assertEquals(cpuPlayer2.getPlayerName(), result.get(1).getPlayerName());
			assertEquals(emptyPlayerList, result);
			mockKeybord.verify(() -> Keybord.getInt(), times(2));

		}catch(Exception  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testCreateCpuOfJankenPlayer004 異常系
	 * void createCpuOfJankenPlayer() throws SystemException
	 * --確認事項--
	 * playerListフィールドにnullが代入されている場合、NullPointerExceptionが発生する
	 * --条件--
	 * KeybordクラスのgetIntでは2を返すmock
	 *	playerListフィールドの値はnull
	 * --検証項目--
	 * 1. NullPointerExceptionが発生するか
	 * 2. getIntメソッドは1回だけ呼び出されているか
	 */
	@Test
	public void testCreateCpuOfJankenPlayer004() {
		//モック化
		try(MockedStatic<Keybord> mockKeybord = mockStatic(Keybord.class)){
			mockKeybord.when(() -> Keybord.getInt()).thenReturn(2);

			//準備
			Method method = JankenCuiGameApplicationImpl.class.getDeclaredMethod("createCpuOfJankenPlayer");
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			method.setAccessible(true);
			playerListField.setAccessible(true);
			playerListField.set(this.jankenCuiGameApplicationImpl, null);

			//テストメソッド
			InvocationTargetException e = assertThrows(InvocationTargetException.class, () -> method.invoke(this.jankenCuiGameApplicationImpl));

			//検証
			assertEquals(NullPointerException.class , e.getTargetException().getClass());
			mockKeybord.verify(() -> Keybord.getInt(), times(1));

		}catch(Exception  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testSelectJankenHand001 正常系
	 * void selectJankenHand() throws SystemException
	 * --確認事項--
	 * playerListフィールドに追加されている各々のプレイヤーインスタンスからselectJankenHandメソッドを呼び出す
	 * --条件--
	 * playerListフィールドにはmock化されたhumanPlayerとcpuPlayerの2個が追加されている
	 * --検証項目--
	 * 1. それぞれのプレイヤーインスタンスからselectJankenHandメソッドを1回ずつ呼び出されているか
	 */
	@Test
	public void testSelectJankenHand001() {
		try{
			//準備
			Method method = JankenCuiGameApplicationImpl.class.getDeclaredMethod("selectJankenHand");
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			method.setAccessible(true);
			playerListField.setAccessible(true);

			//モック化
			JankenPlayer mockHumanPlayer = mock(HumanJankenPlayerImpl.class);
			JankenPlayer mockCpuPlayer = mock(CpuJankenPlayerImpl.class);
			doNothing().when(mockHumanPlayer).selectJankenHand();
			doNothing().when(mockCpuPlayer).selectJankenHand();
			List<JankenPlayer> playerList = new ArrayList<JankenPlayer>(Arrays.asList(mockHumanPlayer, mockCpuPlayer));
			playerListField.set(this.jankenCuiGameApplicationImpl, playerList);

			//テストメソッド
			method.invoke(this.jankenCuiGameApplicationImpl);

			//検証
			verify(mockHumanPlayer, times(1)).selectJankenHand();
			verify(mockCpuPlayer, times(1)).selectJankenHand();

		}catch(Exception  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testSelectJankenHand002 異常系
	 * void selectJankenHand() throws SystemException
	 * --確認事項--
	 * playerListフィールドに追加されている各々のプレイヤーインスタンスからselectJankenHandメソッドを呼び出す
	 * --条件--
	 * playerListフィールドには何も追加されていない
	 * --検証項目--
	 * 1. playerListフィールドから一度も要素が取り出されていないか
	 */
	@Test
	public void testSelectJankenHand002() {
		try{
			//準備
			Method method = JankenCuiGameApplicationImpl.class.getDeclaredMethod("selectJankenHand");
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			method.setAccessible(true);
			playerListField.setAccessible(true);

			//モック化
			@SuppressWarnings("unchecked")
			List<JankenPlayer> playerList = mock(new ArrayList<JankenPlayer>().getClass());
			playerListField.set(this.jankenCuiGameApplicationImpl, playerList);

			//テストメソッド
			method.invoke(this.jankenCuiGameApplicationImpl);

			//検証
			verify(playerList, times(0)).get(0);

		}catch(Exception  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testSelectJankenHand003 異常系
	 * void selectJankenHand() throws SystemException
	 * --確認事項--
	 * playerListフィールドにnullが代入されている場合、NullPointerExceptionが発生する
	 * --条件--
	 * playerListフィールドはnull
	 * --検証項目--
	 * 1. NullPointerExceptionが発生すること
	 */
	@Test
	public void testSelectJankenHand003() {
		try{
			//準備
			Method method = JankenCuiGameApplicationImpl.class.getDeclaredMethod("selectJankenHand");
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			method.setAccessible(true);
			playerListField.setAccessible(true);
			playerListField.set(this.jankenCuiGameApplicationImpl, null);

			//テストメソッド
			InvocationTargetException e = assertThrows(InvocationTargetException.class, () -> method.invoke(this.jankenCuiGameApplicationImpl));

			//検証
			assertEquals(NullPointerException.class , e.getTargetException().getClass());

		}catch(Exception  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testJudge001 正常系
	 * int judge() throws SystemException
	 * --確認事項--
	 * プレイヤーから手を取り出し、フラグを切り替えて勝ちの手を返す
	 * --条件--
	 * playerListフィールドにグーを出したCpuプレイヤー、チョキを出したhumanプレイヤーが追加されている
	 * --検証項目--
	 * 1. 戻り値がROCKに対応する定数と等しいか
	 * 2. プレイヤーインスタンスからgetJankenHandメソッドがそれぞれ1回呼び出されているか
	 */
	@Test
	public void testJudge001() {
		try{
			//テストデータ
			int rock = JankenParam.ROCK.getInt();
			int scissors = JankenParam.SCISSORS.getInt();

			//準備
			Method method = JankenCuiGameApplicationImpl.class.getDeclaredMethod("judge");
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			method.setAccessible(true);
			playerListField.setAccessible(true);

			//モック化
			JankenPlayer humanPlayer = mock(HumanJankenPlayerImpl.class);
			JankenPlayer cpuPlayer = mock(CpuJankenPlayerImpl.class);
			when(humanPlayer.getJankenHand()).thenReturn(scissors);
			when(cpuPlayer.getJankenHand()).thenReturn(rock);
			List<JankenPlayer> playerList = new ArrayList<JankenPlayer>(Arrays.asList(humanPlayer, cpuPlayer));
			playerListField.set(this.jankenCuiGameApplicationImpl, playerList);

			//テストメソッド
			int result = (int) method.invoke(this.jankenCuiGameApplicationImpl);

			//検証
			assertEquals(rock, result);
			verify(humanPlayer, times(1)).getJankenHand();
			verify(cpuPlayer, times(1)).getJankenHand();

		}catch(Exception  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testJudge002 正常系
	 * int judge() throws SystemException
	 * --確認事項--
	 * プレイヤーから手を取り出し、フラグを切り替えて勝ちの手を返す
	 * --条件--
	 * playerListフィールドにグーを出したCpuプレイヤー、パーを出したhumanプレイヤーが追加されている
	 * --検証項目--
	 * 1. 戻り値がPAPERに対応する定数と等しいか
	 * 2. プレイヤーインスタンスからgetJankenHandメソッドがそれぞれ1回呼び出されているか
	 */
	@Test
	public void testJudge002() {
		try{
			//テストデータ
			int rock = JankenParam.ROCK.getInt();
			int paper = JankenParam.PAPER.getInt();

			//準備
			Method method = JankenCuiGameApplicationImpl.class.getDeclaredMethod("judge");
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			method.setAccessible(true);
			playerListField.setAccessible(true);

			//モック化
			JankenPlayer humanPlayer = mock(HumanJankenPlayerImpl.class);
			JankenPlayer cpuPlayer = mock(CpuJankenPlayerImpl.class);
			when(humanPlayer.getJankenHand()).thenReturn(paper);
			when(cpuPlayer.getJankenHand()).thenReturn(rock);
			List<JankenPlayer> playerList = new ArrayList<JankenPlayer>(Arrays.asList(humanPlayer, cpuPlayer));
			playerListField.set(this.jankenCuiGameApplicationImpl, playerList);

			//テストメソッド
			int result = (int) method.invoke(this.jankenCuiGameApplicationImpl);

			//検証
			assertEquals(paper, result);
			verify(humanPlayer, times(1)).getJankenHand();
			verify(cpuPlayer, times(1)).getJankenHand();

		}catch(Exception  e) {
			e.printStackTrace();
			fail();
		}
	}


	/**
	 * testJudge003 正常系
	 * int judge() throws SystemException
	 * --確認事項--
	 * プレイヤーから手を取り出し、フラグを切り替えて勝ちの手を返す
	 * --条件--
	 * playerListフィールドにチョキを出したCpuプレイヤー、パーを出したhumanプレイヤーが追加されている
	 * --検証項目--
	 * 1. 戻り値がSCISSORSに対応する定数と等しいか
	 * 2. プレイヤーインスタンスからgetJankenHandメソッドがそれぞれ1回呼び出されているか
	 */
	@Test
	public void testJudge003() {
		try{
			//テストデータ
			int scissors = JankenParam.SCISSORS.getInt();
			int paper = JankenParam.PAPER.getInt();

			//準備
			Method method = JankenCuiGameApplicationImpl.class.getDeclaredMethod("judge");
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			method.setAccessible(true);
			playerListField.setAccessible(true);

			//モック化
			JankenPlayer humanPlayer = mock(HumanJankenPlayerImpl.class);
			JankenPlayer cpuPlayer = mock(CpuJankenPlayerImpl.class);
			when(humanPlayer.getJankenHand()).thenReturn(paper);
			when(cpuPlayer.getJankenHand()).thenReturn(scissors);
			List<JankenPlayer> playerList = new ArrayList<JankenPlayer>(Arrays.asList(humanPlayer, cpuPlayer));
			playerListField.set(this.jankenCuiGameApplicationImpl, playerList);

			//テストメソッド
			int result = (int) method.invoke(this.jankenCuiGameApplicationImpl);

			//検証
			assertEquals(scissors, result);
			verify(humanPlayer, times(1)).getJankenHand();
			verify(cpuPlayer, times(1)).getJankenHand();

		}catch(Exception  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testJudge004 正常系
	 * int judge() throws SystemException
	 * --確認事項--
	 * プレイヤーから手を取り出し、フラグを切り替えて勝ちの手を返す
	 * --条件--
	 * playerListフィールドにグーを出したCpuプレイヤー、グーを出したhumanプレイヤーが追加されている
	 * --検証項目--
	 * 1. 戻り値がDRAWに対応する定数と等しいか
	 * 2. プレイヤーインスタンスからgetJankenHandメソッドがそれぞれ1回呼び出されているか
	 */
	@Test
	public void testJudge004() {
		try{
			//テストデータ
			int rock = JankenParam.ROCK.getInt();
			int draw = JankenParam.DRAW.getInt();

			//準備
			Method method = JankenCuiGameApplicationImpl.class.getDeclaredMethod("judge");
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			method.setAccessible(true);
			playerListField.setAccessible(true);

			//モック化
			JankenPlayer humanPlayer = mock(HumanJankenPlayerImpl.class);
			JankenPlayer cpuPlayer = mock(CpuJankenPlayerImpl.class);
			when(humanPlayer.getJankenHand()).thenReturn(rock);
			when(cpuPlayer.getJankenHand()).thenReturn(rock);
			List<JankenPlayer> playerList = new ArrayList<JankenPlayer>(Arrays.asList(humanPlayer, cpuPlayer));
			playerListField.set(this.jankenCuiGameApplicationImpl, playerList);

			//テストメソッド
			int result = (int) method.invoke(this.jankenCuiGameApplicationImpl);

			//検証
			assertEquals(draw, result);
			verify(humanPlayer, times(1)).getJankenHand();
			verify(cpuPlayer, times(1)).getJankenHand();

		}catch(Exception  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testJudge005 正常系
	 * int judge() throws SystemException
	 * --確認事項--
	 * プレイヤーから手を取り出し、フラグを切り替えて勝ちの手を返す
	 * --条件--
	 * playerListフィールドにチョキを出したCpuプレイヤー、チョキを出したhumanプレイヤーが追加されている
	 * --検証項目--
	 * 1. 戻り値がDRAWに対応する定数と等しいか
	 * 2. プレイヤーインスタンスからgetJankenHandメソッドがそれぞれ1回呼び出されているか
	 */
	@Test
	public void testJudge005() {
		try{
			//テストデータ
			int scissors = JankenParam.SCISSORS.getInt();
			int draw = JankenParam.DRAW.getInt();

			//準備
			Method method = JankenCuiGameApplicationImpl.class.getDeclaredMethod("judge");
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			method.setAccessible(true);
			playerListField.setAccessible(true);

			//モック化
			JankenPlayer humanPlayer = mock(HumanJankenPlayerImpl.class);
			JankenPlayer cpuPlayer = mock(CpuJankenPlayerImpl.class);
			when(humanPlayer.getJankenHand()).thenReturn(scissors);
			when(cpuPlayer.getJankenHand()).thenReturn(scissors);
			List<JankenPlayer> playerList = new ArrayList<JankenPlayer>(Arrays.asList(humanPlayer, cpuPlayer));
			playerListField.set(this.jankenCuiGameApplicationImpl, playerList);

			//テストメソッド
			int result = (int) method.invoke(this.jankenCuiGameApplicationImpl);

			//検証
			assertEquals(draw, result);
			verify(humanPlayer, times(1)).getJankenHand();
			verify(cpuPlayer, times(1)).getJankenHand();

		}catch(Exception  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testJudge006 正常系
	 * int judge() throws SystemException
	 * --確認事項--
	 * プレイヤーから手を取り出し、フラグを切り替えて勝ちの手を返す
	 * --条件--
	 * playerListフィールドにパーを出したCpuプレイヤー、パーを出したhumanプレイヤーが追加されている
	 * --検証項目--
	 * 1. 戻り値がDRAWに対応する定数と等しいか
	 * 2. プレイヤーインスタンスからgetJankenHandメソッドがそれぞれ1回呼び出されているか
	 */
	@Test
	public void testJudge006() {
		try{
			//テストデータ
			int paper = JankenParam.PAPER.getInt();
			int draw = JankenParam.DRAW.getInt();

			//準備
			Method method = JankenCuiGameApplicationImpl.class.getDeclaredMethod("judge");
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			method.setAccessible(true);
			playerListField.setAccessible(true);

			//モック化
			JankenPlayer humanPlayer = mock(HumanJankenPlayerImpl.class);
			JankenPlayer cpuPlayer = mock(CpuJankenPlayerImpl.class);
			when(humanPlayer.getJankenHand()).thenReturn(paper);
			when(cpuPlayer.getJankenHand()).thenReturn(paper);
			List<JankenPlayer> playerList = new ArrayList<JankenPlayer>(Arrays.asList(humanPlayer, cpuPlayer));
			playerListField.set(this.jankenCuiGameApplicationImpl, playerList);

			//テストメソッド
			int result = (int) method.invoke(this.jankenCuiGameApplicationImpl);

			//検証
			assertEquals(draw, result);
			verify(humanPlayer, times(1)).getJankenHand();
			verify(cpuPlayer, times(1)).getJankenHand();

		}catch(Exception  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testJudge007 正常系
	 * int judge() throws SystemException
	 * --確認事項--
	 * プレイヤーから手を取り出し、フラグを切り替えて勝ちの手を返す
	 * --条件--
	 * playerListフィールドにグーを出したCpuプレイヤー、チョキを出したhumanプレイヤー、パーを出したhumanプレイヤーが追加されている
	 * --検証項目--
	 * 1. 戻り値がDRAWに対応する定数と等しいか
	 * 2. プレイヤーインスタンスからgetJankenHandメソッドがそれぞれ1回呼び出されているか
	 */
	@Test
	public void testJudge007() {
		try{
			//テストデータ
			int rock = JankenParam.ROCK.getInt();
			int scissors = JankenParam.SCISSORS.getInt();
			int paper = JankenParam.PAPER.getInt();
			int draw = JankenParam.DRAW.getInt();

			//準備
			Method method = JankenCuiGameApplicationImpl.class.getDeclaredMethod("judge");
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			method.setAccessible(true);
			playerListField.setAccessible(true);

			//モック化
			JankenPlayer humanPlayer1 = mock(HumanJankenPlayerImpl.class);
			JankenPlayer humanPlayer2 = mock(HumanJankenPlayerImpl.class);
			JankenPlayer cpuPlayer = mock(CpuJankenPlayerImpl.class);
			when(cpuPlayer.getJankenHand()).thenReturn(rock);
			when(humanPlayer1.getJankenHand()).thenReturn(scissors);
			when(humanPlayer2.getJankenHand()).thenReturn(paper);
			List<JankenPlayer> playerList = new ArrayList<JankenPlayer>(Arrays.asList(humanPlayer1, humanPlayer2, cpuPlayer));
			playerListField.set(this.jankenCuiGameApplicationImpl, playerList);

			//テストメソッド
			int result = (int) method.invoke(this.jankenCuiGameApplicationImpl);

			//検証
			assertEquals(draw, result);
			verify(humanPlayer1, times(1)).getJankenHand();
			verify(humanPlayer2, times(1)).getJankenHand();
			verify(cpuPlayer, times(1)).getJankenHand();

		}catch(Exception  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testJudge008 異常系
	 * int judge() throws SystemException
	 * --確認事項--
	 * プレイヤーから手を取り出し、異常値である場合はNullPointerExceptionが発生する
	 * --条件--
	 * playerListフィールドにグーを出したCpuプレイヤー、異常値を出したhumanプレイヤーが追加されている
	 * --検証項目--
	 * 1. NullPointerExceptionがスローされるか
	 * 2. humanプレイヤー、Cpuプレイヤーの順番であるため、getJankenHandが１回と０回呼び出されているか
	 */
	@Test
	public void testJudge008() {
		try{
			//テストデータ
			int rock = JankenParam.ROCK.getInt();
			int illegalValue = -1;

			//準備
			Method method = JankenCuiGameApplicationImpl.class.getDeclaredMethod("judge");
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			method.setAccessible(true);
			playerListField.setAccessible(true);

			//モック化
			JankenPlayer humanPlayer = mock(HumanJankenPlayerImpl.class);
			JankenPlayer cpuPlayer = mock(CpuJankenPlayerImpl.class);
			when(humanPlayer.getJankenHand()).thenReturn(illegalValue);
			when(cpuPlayer.getJankenHand()).thenReturn(rock);
			List<JankenPlayer> playerList = new ArrayList<JankenPlayer>(Arrays.asList(humanPlayer, cpuPlayer));
			playerListField.set(this.jankenCuiGameApplicationImpl, playerList);

			//テストメソッド
			InvocationTargetException e = assertThrows(InvocationTargetException.class, () -> method.invoke(this.jankenCuiGameApplicationImpl));

			//検証
			assertEquals(NullPointerException.class , e.getTargetException().getClass());
			verify(humanPlayer, times(1)).getJankenHand();
			verify(cpuPlayer, times(0)).getJankenHand();

		}catch(Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testJudge009 異常系
	 * int judge() throws SystemException
	 * --確認事項--
	 * プレイヤーから手を取り出し、異常値であるDRAWの定数の場合はSystemExceptionが発生する
	 * --条件--
	 * playerListフィールドにグーを出したCpuプレイヤー、異常値を出したhumanプレイヤーが追加されている
	 * --検証項目--
	 * 1. SystemExceptionがスローされるか
	 * 2. メッセージ「システムエラーが発生しました。終了します。」が含まれるか (取り出せないため未検証)
	 * 3. humanプレイヤー、Cpuプレイヤーの順番であるため、getJankenHandが１回と０回呼び出されているか
	 */
	@Test
	public void testJudge009() {
		try{
			//テストデータ
			int rock = JankenParam.ROCK.getInt();
			int draw = JankenParam.DRAW.getInt();

			//準備
			Method method = JankenCuiGameApplicationImpl.class.getDeclaredMethod("judge");
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			method.setAccessible(true);
			playerListField.setAccessible(true);

			//モック化
			JankenPlayer humanPlayer = mock(HumanJankenPlayerImpl.class);
			JankenPlayer cpuPlayer = mock(CpuJankenPlayerImpl.class);
			when(humanPlayer.getJankenHand()).thenReturn(draw);
			when(cpuPlayer.getJankenHand()).thenReturn(rock);
			List<JankenPlayer> playerList = new ArrayList<JankenPlayer>(Arrays.asList(humanPlayer, cpuPlayer));
			playerListField.set(this.jankenCuiGameApplicationImpl, playerList);

			//テストメソッド
			InvocationTargetException e = assertThrows(InvocationTargetException.class, () -> method.invoke(this.jankenCuiGameApplicationImpl));

			//検証
			assertEquals(SystemException.class , e.getTargetException().getClass());
			verify(humanPlayer, times(1)).getJankenHand();
			verify(cpuPlayer, times(0)).getJankenHand();

		}catch(Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testJudge010 異常系
	 * int judge() throws SystemException
	 * --確認事項--
	 * プレイヤーが0人の場合、DRAWを返す
	 * --条件--
	 * playerListがは空
	 * --検証項目--
	 * 1. 戻り値がDRAWに対応する定数と等しいか
	 */
	@Test
	public void testJudge010() {
		try{
			//テストデータ
			List<JankenPlayer> emptyPlayerList = new ArrayList<JankenPlayer>();
			int draw = JankenParam.DRAW.getInt();

			//準備
			Method method = JankenCuiGameApplicationImpl.class.getDeclaredMethod("judge");
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			method.setAccessible(true);
			playerListField.setAccessible(true);
			playerListField.set(this.jankenCuiGameApplicationImpl, emptyPlayerList);

			//テストメソッド
			int result = (int) method.invoke(this.jankenCuiGameApplicationImpl);

			//検証
			assertEquals(draw, result);

		}catch(Exception  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testViewWinner001 正常系
	 * void viewWinner() throws SystemException
	 * --確認事項--
	 * winnHandフィールドと同じ値をもつプレイヤーのplayerNameフィールドの値をを出力する
	 * --条件--
	 * playerListフィールドにグーを出したhumanプレイヤーのモック、チョキを出したcpuプレイヤーのモックが追加されている
	 * winHandフィールドの値をROCKに対応する定数
	 * --検証項目--
	 * 1. getJankenHandメソッドが各々1回呼び出されているか
	 * 2. humanプレイヤーのgetPlayerNameメソッドが1回、cpuプレイヤーのgetPlayerNameメソッドは0回呼び出されているか
	 */
	@Test
	public void testViewWinner001() {
		try{
			//テストデータ
			int rock = JankenParam.ROCK.getInt();
			int scissors = JankenParam.SCISSORS.getInt();
			String playerName1 = "プレーヤー1";
			String cpuName1 = "CPU1";

			//準備
			Method method = JankenCuiGameApplicationImpl.class.getDeclaredMethod("viewWinner");
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			Field winHandField = JankenCuiGameApplicationImpl.class.getDeclaredField("winHand");
			method.setAccessible(true);
			playerListField.setAccessible(true);
			winHandField.setAccessible(true);

			//モック化
			JankenPlayer humanPlayer = mock(HumanJankenPlayerImpl.class);
			JankenPlayer cpuPlayer = mock(CpuJankenPlayerImpl.class);
			when(humanPlayer.getJankenHand()).thenReturn(rock);
			when(cpuPlayer.getJankenHand()).thenReturn(scissors);
			when(humanPlayer.getPlayerName()).thenReturn(playerName1);
			when(cpuPlayer.getPlayerName()).thenReturn(cpuName1);
			List<JankenPlayer> playerList = new ArrayList<JankenPlayer>(Arrays.asList(humanPlayer, cpuPlayer));
			playerListField.set(this.jankenCuiGameApplicationImpl, playerList);
			winHandField.set(this.jankenCuiGameApplicationImpl, rock);

			//テストメソッド
			method.invoke(this.jankenCuiGameApplicationImpl);

			//検証
			verify(humanPlayer, times(1)).getJankenHand();
			verify(cpuPlayer, times(1)).getJankenHand();
			verify(humanPlayer, times(1)).getPlayerName();
			verify(cpuPlayer, times(0)).getPlayerName();

		}catch(Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testIsCheckJankenPlayerCount001 正常系
	 * void isCheckJankenPlayerCount() throws SystemException
	 * --確認事項--
	 * playerListフィールドの長さが２以上の場合はtrueを返すか
	 * --条件--
	 * playerListフィールドは要素が2個追加されているリスト
	 * --検証項目--
	 * 1. 戻り値がtrueであるか
	 */
	@Test
	public void testIsCheckJankenPlayerCount001() {
		try{
			//テストデータ
			JankenPlayer humanPlayer1 = new HumanJankenPlayerImpl("プレーヤー1");
			JankenPlayer cpuPlayer1 = new CpuJankenPlayerImpl("CPU1");
			List<JankenPlayer> playerList = new ArrayList<JankenPlayer>(Arrays.asList(humanPlayer1, cpuPlayer1));

			//準備
			Method method = JankenCuiGameApplicationImpl.class.getDeclaredMethod("isCheckJankenPlayerCount");
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			method.setAccessible(true);
			playerListField.setAccessible(true);
			playerListField.set(this.jankenCuiGameApplicationImpl, playerList);

			//テストメソッド
			boolean result = (boolean)method.invoke(this.jankenCuiGameApplicationImpl);

			//検証
			assertEquals(true, result);

		}catch(Exception  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testIsCheckJankenPlayerCount002 正常系
	 * void isCheckJankenPlayerCount() throws SystemException
	 * --確認事項--
	 * playerListフィールドの長さが２未満の場合はfalseを返すか
	 * --条件--
	 * playerListフィールドは空のリスト
	 * --検証項目--
	 * 1. 戻り値がfalseであるか
	 */
	@Test
	public void testIsCheckJankenPlayerCount002() {
		try{
			//テストデータ
			List<JankenPlayer> emptyPlayerList = new ArrayList<JankenPlayer>();

			//準備
			Method method = JankenCuiGameApplicationImpl.class.getDeclaredMethod("isCheckJankenPlayerCount");
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			method.setAccessible(true);
			playerListField.setAccessible(true);
			playerListField.set(this.jankenCuiGameApplicationImpl, emptyPlayerList);

			//テストメソッド
			boolean result = (boolean)method.invoke(this.jankenCuiGameApplicationImpl);

			//検証
			assertEquals(false, result);

		}catch(Exception  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testIsCheckJankenPlayerCount003 異常系
	 * void isCheckJankenPlayerCount() throws SystemException
	 * --確認事項--
	 * playerListフィールドがnullの場合NullPointerExceptionが発生する
	 * --条件--
	 * playerListフィールドはnull
	 * --検証項目--
	 * 1. NullPointerExceptionが発生するか
	 */
	@Test
	public void testIsCheckJankenPlayerCount003() {
		try{
			//準備
			Method method = JankenCuiGameApplicationImpl.class.getDeclaredMethod("isCheckJankenPlayerCount");
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			method.setAccessible(true);
			playerListField.setAccessible(true);
			playerListField.set(this.jankenCuiGameApplicationImpl, null);

			//テストメソッド
			InvocationTargetException e = assertThrows(InvocationTargetException.class, () -> method.invoke(this.jankenCuiGameApplicationImpl));

			//検証
			assertEquals(NullPointerException.class , e.getTargetException().getClass());

		}catch(Exception  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testHasGameContinue001 正常系
	 * void hasGameContinue() throws SystemException
	 * --確認事項--
	 * 1を入力するとtrueを返す
	 * --条件--
	 * KeybordクラスはgetIntメソッドで1を返すモック
	 * --検証項目--
	 * 1. 戻り値がtrueであるか
	 * 2. getIntメソッドが1回呼び出されているか
	 */
	@Test
	public void testHasGameContinue001() {
		//モック化
		try(MockedStatic<Keybord> mockKeybord = mockStatic(Keybord.class)){
			mockKeybord.when(() -> Keybord.getInt(1, 2)).thenReturn(1);

			//準備
			Method method = JankenCuiGameApplicationImpl.class.getDeclaredMethod("hasGameContinue");
			method.setAccessible(true);

			//テストメソッド
			boolean result = (boolean)method.invoke(this.jankenCuiGameApplicationImpl);

			//検証
			assertEquals(result, true);
			mockKeybord.verify(() -> Keybord.getInt(1, 2), times(1));

		}catch(Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testHasGameContinue002 正常系
	 * void hasGameContinue() throws SystemException
	 * --確認事項--
	 * 2を入力するとfalseを返す
	 * --条件--
	 * KeybordクラスはgetIntメソッドで2を返すモック
	 * --検証項目--
	 * 1. 戻り値がfalseであるか
	 * 2. getIntメソッドが1回呼び出されているか
	 */
	@Test
	public void testHasGameContinue002() {
		//モック化
		try(MockedStatic<Keybord> mockKeybord = mockStatic(Keybord.class)){
			mockKeybord.when(() -> Keybord.getInt(1, 2)).thenReturn(2);

			//準備
			Method method = JankenCuiGameApplicationImpl.class.getDeclaredMethod("hasGameContinue");
			method.setAccessible(true);

			//テストメソッド
			boolean result = (boolean)method.invoke(this.jankenCuiGameApplicationImpl);

			//検証
			assertEquals(result, false);
			mockKeybord.verify(() -> Keybord.getInt(1, 2), times(1));

		}catch(Exception  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testHasGameContinue003 正常系
	 * void hasGameContinue() throws SystemException
	 * --確認事項--
	 * 異常値を入力すると再度入力が求められる
	 * --条件--
	 * KeybordクラスはgetIntメソッドで1回目はApplicationExceptionを発生し、2回目は1を返すモック
	 * --検証項目--
	 * 1. 戻り値がtrueであるか
	 * 2. getIntメソッドが2回呼び出されているか
	 */
	@Test
	public void testHasGameContinue003() {
		//モック化
		try(MockedStatic<Keybord> mockKeybord = mockStatic(Keybord.class)){
			mockKeybord.when(() -> Keybord.getInt(1, 2)).thenThrow(new ApplicationException(null)).thenReturn(1);

			//準備
			Method method = JankenCuiGameApplicationImpl.class.getDeclaredMethod("hasGameContinue");
			method.setAccessible(true);

			//テストメソッド
			boolean result = (boolean)method.invoke(this.jankenCuiGameApplicationImpl);

			//検証
			assertEquals(result, true);
			mockKeybord.verify(() -> Keybord.getInt(1, 2), times(2));

		}catch(Exception  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testHasGameContinue004 異常系
	 * void hasGameContinue() throws SystemException
	 * --確認事項--
	 * 範囲外の整数を入力し処理が進んでしまった場合、SystemExceptionを発生させる
	 * --条件--
	 * KeybordクラスはgetIntメソッドで異常値を返すモック
	 * --検証項目--
	 * 1. SystemExceptionがスローされるか
	 * 2. getIntメソッドが1回呼び出されているか
	 */
	@Test
	public void testHasGameContinue004() {
		//テストデータ
		int illegalValue = -1;

		//モック化
		try(MockedStatic<Keybord> mockKeybord = mockStatic(Keybord.class)){
			mockKeybord.when(() -> Keybord.getInt(1, 2)).thenReturn(illegalValue);

			//準備
			Method method = JankenCuiGameApplicationImpl.class.getDeclaredMethod("hasGameContinue");
			method.setAccessible(true);
			
			//テストメソッド
			InvocationTargetException e = assertThrows(InvocationTargetException.class, () -> method.invoke(this.jankenCuiGameApplicationImpl));
			
			//検証
			assertEquals(SystemException.class , e.getTargetException().getClass());
			mockKeybord.verify(() -> Keybord.getInt(1, 2), times(1));

		}catch(Exception  e) {
			e.printStackTrace();
			fail();
		}
	}

//	/**
//	 * testHasGameContinue00 異常系
//	 * void hasGameContinue() throws SystemException
//	 * --確認事項--
//	 * 範囲外の整数を入力し処理が進んでしまった場合、SystemExceptionを発生させる
//	 * --条件--
//	 * KeybordクラスはgetIntメソッドで異常値を返すモック
//	 * --検証項目--
//	 * 1. SystemExceptionがスローされるか
//	 * 2. getIntメソッドが1回呼び出されているか
//	 */
//	@Test
//	public void testHasGameContinue00() {
//		//テストデータ
//		int illegalValue = -1;
//
//		//モック化
//		try(MockedStatic<Keybord> mockKeybord = mockStatic(Keybord.class)){
//			mockKeybord.when(() -> Keybord.getInt(1, 2)).thenReturn(illegalValue);
//
//			//準備
////			Method method = JankenCuiGameApplicationImpl.class.getDeclaredMethod("hasGameContinue");
////			method.setAccessible(true);
//			JankenCuiGameApplicationImpl game = new JankenCuiGameApplicationImpl();
//			//テストメソッド
////			InvocationTargetException e = assertThrows(InvocationTargetException.class, () -> method.invoke(this.jankenCuiGameApplicationImpl));
//			SystemException result = assertThrows(SystemException.class, () -> game.hasGameContinue());
//			//検証
////			assertEquals(SystemException.class , e.getTargetException().getClass());
//			assertEquals(true, result instanceof SystemException);
//			assertEquals("システムエラーが発生しました。終了します。", result.getSysMsg());
//			mockKeybord.verify(() -> Keybord.getInt(1, 2), times(1));
//
//		}catch(Exception  e) {
//			e.printStackTrace();
//			fail();
//		}
//	}

	/**
	 * testGetPlayerList001 正常系
	 * public List<JankenPlayer> getPlayerList()
	 * --確認事項--
	 * playerListフィールドの値を返す
	 * --条件--
	 * playerListフィールドにArrayList<JankenPlayer>インスタンスが代入され, 要素が追加されている
	 * --検証項目--
	 * 1. 戻り値とplayerListフィールドの値が等しいか
	 * 2. 追加されている要素のデータは指定した値であるか
	 */
	@Test
	public void testGetPlayerList001() {
		try {
			//テストデータ
			JankenPlayer humanPlayer1 = new HumanJankenPlayerImpl("プレーヤー1");
			JankenPlayer cpuPlayer1 = new CpuJankenPlayerImpl("CPU1");
			List<JankenPlayer> playerList = new ArrayList<JankenPlayer>(Arrays.asList(humanPlayer1, cpuPlayer1));

			//準備
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			playerListField.setAccessible(true);
			playerListField.set(this.jankenCuiGameApplicationImpl, playerList);

			//テストメソッド
			List<JankenPlayer> result = this.jankenCuiGameApplicationImpl.getPlayerList();

			//検証
			@SuppressWarnings("unchecked")
			List<JankenPlayer> field = (List<JankenPlayer>) playerListField.get(this.jankenCuiGameApplicationImpl);
			assertEquals(field, result);
			assertEquals(humanPlayer1.getPlayerName(), result.get(0).getPlayerName());
			assertEquals(cpuPlayer1.getPlayerName(), result.get(1).getPlayerName());

		}catch(Exception  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testGetPlayerList002 正常系
	 * ppublic List<JankenPlayer> getPlayerList()
	 * --確認事項--
	 * playerListフィールドの値を返す
	 * --条件--
	 * playerListフィールドにArrayList<JankenPlayer>インスタンスが代入され, 要素が追加されていない
	 * --検証項目--
	 * 1. 戻り値とplayerListフィールドの値が等しいか
	 * 2. 追加されている要素のデータは指定した値であるか
	 */
	@Test
	public void testGetPlayerList002() {
		try {
			//テストデータ
			List<JankenPlayer> emptyPlayerList = new ArrayList<JankenPlayer>();

			//準備
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			playerListField.setAccessible(true);
			playerListField.set(this.jankenCuiGameApplicationImpl, emptyPlayerList);

			//テストメソッド
			List<JankenPlayer> result = this.jankenCuiGameApplicationImpl.getPlayerList();

			//検証
			//検証
			@SuppressWarnings("unchecked")
			List<JankenPlayer> playerList = (List<JankenPlayer>) playerListField.get(this.jankenCuiGameApplicationImpl);
			assertEquals(playerList, result);

		}catch(Exception  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testGetPlayerList003 正常系
	 * ppublic List<JankenPlayer> getPlayerList()
	 * --確認事項--
	 * playerListフィールドの値を返す
	 * --条件--
	 * playerListフィールドにnullが代入されている場合
	 * --検証項目--
	 * 1. 戻り値がnullであるか
	 */
	@Test
	public void testGetPlayerList003() {
		try {
			//準備
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			playerListField.setAccessible(true);
			playerListField.set(this.jankenCuiGameApplicationImpl, null);

			//テストメソッド
			List<JankenPlayer> result = this.jankenCuiGameApplicationImpl.getPlayerList();

			//検証
			assertNull(result);

		}catch(Exception  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testSetPlayerList001 正常系
	 * ppublic void setPlayerList(List<JankenPlayer> playerList)
	 * --確認事項--
	 * playerListフィールドに値を代入する
	 * --条件--
	 * playerListフィールドの値はnull
	 * 引数に要素が追加されているArrayList<JankenPlayer>インスタンスを渡す
	 * --検証項目--
	 * 1. playerListフィールドの値と引数の値が等しいか
	 * 2. 追加されている要素のデータは指定した値であるか
	 */
	@Test
	public void testSetPlayerList001() {
		try {
			//テストデータ
			JankenPlayer humanPlayer1 = new HumanJankenPlayerImpl("プレーヤー1");
			JankenPlayer cpuPlayer1 = new CpuJankenPlayerImpl("CPU1");
			List<JankenPlayer> playerList = new ArrayList<JankenPlayer>(Arrays.asList(humanPlayer1, cpuPlayer1));

			//準備
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			playerListField.setAccessible(true);

			//テストメソッド
			this.jankenCuiGameApplicationImpl.setPlayerList(playerList);

			//検証
			@SuppressWarnings("unchecked")
			List<JankenPlayer> result = (List<JankenPlayer>) playerListField.get(this.jankenCuiGameApplicationImpl);
			assertEquals(playerList.size(), result.size());
			assertEquals(humanPlayer1.getPlayerName(), result.get(0).getPlayerName());
			assertEquals(cpuPlayer1.getPlayerName(), result.get(1).getPlayerName());

		}catch(Exception  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testSetPlayerList002 正常系
	 * ppublic void setPlayerList(List<JankenPlayer> playerList)
	 * --確認事項--
	 * playerListフィールドに値を代入する
	 * --条件--
	 * playerListフィールドの値はnull
	 * 引数に要素が追加されていないArrayList<JankenPlayer>インスタンスを渡す
	 * --検証項目--
	 * 1. playerListフィールドの値と引数の値が等しいか
	 */
	@Test
	public void testSetPlayerList002() {
		try {
			//テストデータ
			List<JankenPlayer> emptyPlayerList = new ArrayList<JankenPlayer>();

			//準備
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			playerListField.setAccessible(true);

			//テストメソッド
			this.jankenCuiGameApplicationImpl.setPlayerList(emptyPlayerList);

			//検証
			@SuppressWarnings("unchecked")
			List<JankenPlayer> result = (List<JankenPlayer>) playerListField.get(this.jankenCuiGameApplicationImpl);
			assertEquals(emptyPlayerList.size(), result.size());

		}catch(Exception  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testSetPlayerList003 異常系
	 * ppublic void setPlayerList(List<JankenPlayer> playerList)
	 * --確認事項--
	 * playerListフィールドに値を代入する
	 * --条件--
	 * playerListフィールドの値はnull
	 * 引数にnullを渡す
	 * --検証項目--
	 * 1. playerListフィールドの値はnullであるか
	 */
	@Test
	public void testSetPlayerList003() {
		try {
			//準備
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			playerListField.setAccessible(true);

			//テストメソッド
			this.jankenCuiGameApplicationImpl.setPlayerList(null);

			//検証
			@SuppressWarnings("unchecked")
			List<JankenPlayer> result = (List<JankenPlayer>) playerListField.get(this.jankenCuiGameApplicationImpl);
			assertNull(result);

		}catch(Exception  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testGetWinHand001 正常系
	 * ppublic int getWinHand()
	 * --確認事項--
	 * winHandフィールドの値を返す
	 * --条件--
	 * winHandフィールドの値は1
	 * --検証項目--
	 * 1. 戻り値とwinHandフィールドの値が等しいか
	 */
	@Test
	public void testGetWinHand001() {
		try {
			//準備
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("winHand");
			playerListField.setAccessible(true);
			playerListField.set(this.jankenCuiGameApplicationImpl, 1);

			//テストメソッド
			int result = this.jankenCuiGameApplicationImpl.getWinHand();

			//検証
			assertEquals(1, result);

		}catch(Exception  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testSetPlayerList001 正常系
	 * ppublic int setWinHand()
	 * --確認事項--
	 * winHandフィールドに値を代入する
	 * --条件--
	 * winHandフィールドの値は0
	 * --検証項目--
	 * 1. 引数とwinHandフィールドの値が等しいか
	 */
	@Test
	public void testSetWinHand001() {
		try {
			//準備
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("winHand");
			playerListField.setAccessible(true);

			//テストメソッド
			this.jankenCuiGameApplicationImpl.setWinHand(1);

			//検証
			int result = (int) playerListField.get(this.jankenCuiGameApplicationImpl);
			assertEquals(1, result);

		}catch(Exception  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testAction001 正常系
	 * public void action() throws SystemException
	 * --確認事項--
	 * humna:1人, cpu:1人, プレーヤー数2人以上 1回目2未満にして入力を再度求める
	 * human:グー、cpu:チョキ、1回目あいこにして再度入力を求める
	 * 継続し、同じ入力で繰り返して終了する
	 * --条件--
	 * KeybordクラスはgetIntで指定した値を返すモック
	 * RandomクラスはgetIntで指定した値を返すモック
	 * --検証項目--
	 * 1. SystemExceptionも含めて例外が発生しないことを確認
	 */
//	@Test
//	public void testAction001() {
//		//テストデータ
//		int rock = JankenParam.ROCK.getInt();
//		int scissors = JankenParam.SCISSORS.getInt();
//		int paper = JankenParam.PAPER.getInt();
//
//		//モック化
//		try(MockedStatic<Keybord> mockKeybord = mockStatic(Keybord.class);
//				//CpuPlayerチョキ、CpuPlayer:チョキ、CpuPlayer:チョキ
//			 MockedConstruction<Random> mockRandom = mockConstruction(
//					 Random.class,
//					 (random, context) -> when(random.nextInt(paper)).thenReturn(scissors-1)
//			)
//		){
//			//入力(プレイヤー数) ー＞ HumanPlayer:1、CpuPlayer:0 |HumanPlayer1、CpuPlayer1 |HumanPlayer1、CpuPlayer1
//			mockKeybord.when(() -> Keybord.getInt()).thenReturn(1).thenReturn(0).thenReturn(1).thenReturn(1).thenReturn(1).thenReturn(1);
//			//入力(継続するか)ー＞継続する | 終了する
//			mockKeybord.when(() -> Keybord.getInt(1, 2)).thenReturn(1).thenReturn(2);
//			//入力(humanの手選択) ー＞ HumanPlayer:チョキ、HumanPlayer:グー、HumanPlayer:グー
//			mockKeybord.when(() -> Keybord.getInt(rock, paper)).thenReturn(scissors).thenReturn(rock).thenReturn(rock);
//
//			//テストメソッド
//			this.jankenCuiGameApplicationImpl.action();
//
//			//検証
//		} catch (Exception e) {
//			e.printStackTrace();
//			fail();
//		}
//	}

}
