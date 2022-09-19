package jp.co.ginga.cui.impl.janken;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;

import jp.co.ginga.cui.impl.janken.jankenplayer.JankenPlayer;
import jp.co.ginga.cui.impl.janken.jankenplayer.impl.CpuJankenPlayerImpl;
import jp.co.ginga.cui.impl.janken.jankenplayer.impl.HumanJankenPlayerImpl;
import jp.co.ginga.util.exception.ApplicationException;
import jp.co.ginga.util.exception.SystemException;
import jp.co.ginga.util.keybord.Keybord;

/**
 * CUIじゃんけん実装クラス
 * @author yoshi
 *
 */
public class JankenCuiGameApplicationImplTest{

	//テストデータ
	private int multi = 2;
	private int none = 0;
	private int two = 2;
	private int one = 1;
	private int zero = 0;
	private int illegalValue = -1;
	private int rock = JankenParam.ROCK.getInt();
	private int scissors = JankenParam.SCISSORS.getInt();
	private int paper = JankenParam.PAPER.getInt();
	private int draw = JankenParam.DRAW.getInt();
	private String playerName1 = "プレーヤー1";
	private String playerName2 = "プレーヤー2";
	private String cpuName1 = "CPU1";
	private String cpuName2 = "CPU2";
	private JankenPlayer humanPlayer1 = new HumanJankenPlayerImpl(playerName1);
	private JankenPlayer humanPlayer2 = new HumanJankenPlayerImpl(playerName2);
	private JankenPlayer cpuPlayer1 = new CpuJankenPlayerImpl(cpuName1);
	private JankenPlayer cpuPlayer2 = new CpuJankenPlayerImpl(cpuName2);
	private List<JankenPlayer> emptyPlayerList = new ArrayList<JankenPlayer>();
	private List<JankenPlayer> nullPlayerList = null;
	private List<JankenPlayer> playerList = new ArrayList<JankenPlayer>(Arrays.asList(humanPlayer1, cpuPlayer1));
	private List<JankenPlayer> humanPlayerList = new ArrayList<JankenPlayer>(Arrays.asList(humanPlayer1, humanPlayer2));
	private List<JankenPlayer> cpuPlayerList = new ArrayList<JankenPlayer>(Arrays.asList(cpuPlayer1, cpuPlayer2));

	@InjectMocks
	private JankenCuiGameApplicationImpl jankenCuiGameApplicationImpl = new JankenCuiGameApplicationImpl();

	/**
	 * testInit001 正常系
	 * void init()
	 * playerListに何も代入されていない場合、ArrayList<JankenPlayer>のインスタンスを代入する
	 */
	@Test
	public void testInit001() {
		try {
			//準備
			Method method = JankenCuiGameApplicationImpl.class.getDeclaredMethod("init");
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			method.setAccessible(true);
			playerListField.setAccessible(true);
			
			//テストメソッド
			method.invoke(this.jankenCuiGameApplicationImpl);
			
			//検証
			@SuppressWarnings("unchecked")
			List<JankenPlayer> playerList =(List<JankenPlayer>) playerListField.get(this.jankenCuiGameApplicationImpl);
			assertEquals(playerList, this.emptyPlayerList);
			assertEquals(playerList.size(), 0);

		}catch(Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testInit002 正常系
	 * void init()
	 * playerListにArrayList<JankenPlayer>のインスタンスが代入され要素が追加されている場合、要素を削除する
	 */
	@Test
	public void testInit002() {
		try {
			//準備
			Method method = JankenCuiGameApplicationImpl.class.getDeclaredMethod("init");
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			method.setAccessible(true);
			playerListField.setAccessible(true);
			playerListField.set(this.jankenCuiGameApplicationImpl, this.playerList);

			//テストメソッド
			method.invoke(this.jankenCuiGameApplicationImpl);

			//検証
			@SuppressWarnings("unchecked")
			List<JankenPlayer> playerList =(List<JankenPlayer>) playerListField.get(this.jankenCuiGameApplicationImpl);
			assertEquals(playerList, this.emptyPlayerList);
			assertEquals(playerList.size(), 0);

		}catch(Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testCreateHumanOfJankenPlayer001 正常系
	 * void createHumanOfJankenPlayer() throws SystemException
	 * playerListに複数のhumanJankenPlayerImplインスタンスを代入する
	 */
	@Test
	public void testCreateHumanOfJankenPlayer001() {
		//モック化
		try(MockedStatic<Keybord> mockKeybord = mockStatic(Keybord.class)){
			mockKeybord.when(() -> Keybord.getInt()).thenReturn(this.multi);
			
			//準備
			Method method = JankenCuiGameApplicationImpl.class.getDeclaredMethod("createHumanOfJankenPlayer");
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			method.setAccessible(true);
			playerListField.setAccessible(true);
			playerListField.set(this.jankenCuiGameApplicationImpl, this.emptyPlayerList);

			//テストメソッド
			method.invoke(this.jankenCuiGameApplicationImpl);

			//検証
			@SuppressWarnings("unchecked")
			List<JankenPlayer> playerList =(List<JankenPlayer>) playerListField.get(this.jankenCuiGameApplicationImpl);
			assertEquals(playerList.size(), this.multi);
			assertEquals(playerList.get(0).getPlayerName(), this.humanPlayer1.getPlayerName());
			assertEquals(playerList.get(1).getPlayerName(), this.humanPlayer2.getPlayerName());
			mockKeybord.verify(() -> Keybord.getInt(), times(1));
			
		}catch(Exception  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testCreateHumanOfJankenPlayer002 正常系
	 * void createHumanOfJankenPlayer() throws SystemException
	 * playerListに0個のhumanJankenPlayerImplインスタンスを代入する
	 */
	@Test
	public void testCreateHumanOfJankenPlayer002() {
		//モック化
		try(MockedStatic<Keybord> mockKeybord = mockStatic(Keybord.class)){
			mockKeybord.when(() -> Keybord.getInt()).thenReturn(this.none);
			
			//準備
			Method method = JankenCuiGameApplicationImpl.class.getDeclaredMethod("createHumanOfJankenPlayer");
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			method.setAccessible(true);
			playerListField.setAccessible(true);
			playerListField.set(this.jankenCuiGameApplicationImpl, this.emptyPlayerList);

			//テストメソッド
			method.invoke(this.jankenCuiGameApplicationImpl);

			//検証
			@SuppressWarnings("unchecked")
			List<JankenPlayer> playerList =(List<JankenPlayer>) playerListField.get(this.jankenCuiGameApplicationImpl);
			assertEquals(playerList.size(), this.none);
			mockKeybord.verify(() -> Keybord.getInt(), times(1));
			
		}catch(Exception  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testCreateHumanOfJankenPlayer003 正常系
	 * void createHumanOfJankenPlayer() throws SystemException
	 * KeybordのgetInt()から一度ApplicationExceptionを発生し、２度目に正常な値を入力する
	 */
	@Test
	public void testCreateHumanOfJankenPlayer003() {
		//モック化
		try(MockedStatic<Keybord> mockKeybord = mockStatic(Keybord.class)){
			mockKeybord.when(() -> Keybord.getInt()).thenThrow(new ApplicationException(null)).thenReturn(this.multi);
			
			//準備
			Method method = JankenCuiGameApplicationImpl.class.getDeclaredMethod("createHumanOfJankenPlayer");
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			method.setAccessible(true);
			playerListField.setAccessible(true);
			playerListField.set(this.jankenCuiGameApplicationImpl, this.emptyPlayerList);

			//テストメソッド
			method.invoke(this.jankenCuiGameApplicationImpl);

			//検証
			@SuppressWarnings("unchecked")
			List<JankenPlayer> playerList =(List<JankenPlayer>) playerListField.get(this.jankenCuiGameApplicationImpl);
			assertEquals(playerList.size(), this.multi);
			assertEquals(playerList.get(0).getPlayerName(), this.humanPlayer1.getPlayerName());
			assertEquals(playerList.get(1).getPlayerName(), this.humanPlayer2.getPlayerName());
			mockKeybord.verify(() -> Keybord.getInt(), times(2));
			
		}catch(Exception  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testCreateHumanOfJankenPlayer004 異常系
	 * void createHumanOfJankenPlayer() throws SystemException
	 * playerListにnullが代入されている
	 */
	@Test
	public void testCreateHumanOfJankenPlayer004() {
		//モック化
		try(MockedStatic<Keybord> mockKeybord = mockStatic(Keybord.class)){
			mockKeybord.when(() -> Keybord.getInt()).thenReturn(this.multi);
			
			//準備
			Method method = JankenCuiGameApplicationImpl.class.getDeclaredMethod("createHumanOfJankenPlayer");
			method.setAccessible(true);

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
	 * playerListに複数のcpuJankenPlayerImplインスタンスを代入する
	 */
	@Test
	public void testCreateCpuOfJankenPlayer001() {
		//モック化
		try(MockedStatic<Keybord> mockKeybord = mockStatic(Keybord.class)){
			mockKeybord.when(() -> Keybord.getInt()).thenReturn(this.multi);
			
			//準備
			Method method = JankenCuiGameApplicationImpl.class.getDeclaredMethod("createCpuOfJankenPlayer");
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			method.setAccessible(true);
			playerListField.setAccessible(true);
			playerListField.set(this.jankenCuiGameApplicationImpl, this.emptyPlayerList);

			//テストメソッド
			method.invoke(this.jankenCuiGameApplicationImpl);

			//検証
			@SuppressWarnings("unchecked")
			List<JankenPlayer> playerList =(List<JankenPlayer>) playerListField.get(this.jankenCuiGameApplicationImpl);
			assertEquals(playerList.size(), this.multi);
			assertEquals(playerList.get(0).getPlayerName(), this.cpuPlayer1.getPlayerName());
			assertEquals(playerList.get(1).getPlayerName(), this.cpuPlayer2.getPlayerName());
			mockKeybord.verify(() -> Keybord.getInt(), times(1));
			
		}catch(Exception  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testCreateCpuOfJankenPlayer002 正常系
	 * void createCpuOfJankenPlayer() throws SystemException
	 * playerListに0個のCpuJankenPlayerImplインスタンスを代入する
	 */
	@Test
	public void testCreateCpuOfJankenPlayer002() {
		//モック化
		try(MockedStatic<Keybord> mockKeybord = mockStatic(Keybord.class)){
			mockKeybord.when(() -> Keybord.getInt()).thenReturn(this.none);
			
			//準備
			Method method = JankenCuiGameApplicationImpl.class.getDeclaredMethod("createCpuOfJankenPlayer");
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			method.setAccessible(true);
			playerListField.setAccessible(true);
			playerListField.set(this.jankenCuiGameApplicationImpl, this.emptyPlayerList);

			//テストメソッド
			method.invoke(this.jankenCuiGameApplicationImpl);

			//検証
			@SuppressWarnings("unchecked")
			List<JankenPlayer> playerList =(List<JankenPlayer>) playerListField.get(this.jankenCuiGameApplicationImpl);
			assertEquals(playerList.size(), this.none);
			mockKeybord.verify(() -> Keybord.getInt(), times(1));
			
		}catch(Exception  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testCreateCpuOfJankenPlayer003 正常系
	 * void createCpuOfJankenPlayer() throws SystemException
	 * KeybordのgetInt()から一度ApplicationExceptionを発生し、２度目に正常な値を入力する
	 */
	@Test
	public void testCreateCpuOfJankenPlayer003() {
		//モック化
		try(MockedStatic<Keybord> mockKeybord = mockStatic(Keybord.class)){
			mockKeybord.when(() -> Keybord.getInt()).thenThrow(new ApplicationException(null)).thenReturn(this.multi);
			
			//準備
			Method method = JankenCuiGameApplicationImpl.class.getDeclaredMethod("createCpuOfJankenPlayer");
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			method.setAccessible(true);
			playerListField.setAccessible(true);
			playerListField.set(this.jankenCuiGameApplicationImpl, this.emptyPlayerList);

			//テストメソッド
			method.invoke(this.jankenCuiGameApplicationImpl);

			//検証
			@SuppressWarnings("unchecked")
			List<JankenPlayer> playerList =(List<JankenPlayer>) playerListField.get(this.jankenCuiGameApplicationImpl);
			assertEquals(playerList.size(), this.multi);
			assertEquals(playerList.get(0).getPlayerName(), this.cpuPlayer1.getPlayerName());
			assertEquals(playerList.get(1).getPlayerName(), this.cpuPlayer2.getPlayerName());
			mockKeybord.verify(() -> Keybord.getInt(), times(2));
			
		}catch(Exception  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testCreateCpuOfJankenPlayer004 異常系
	 * void createCpuOfJankenPlayer() throws SystemException
	 * playerListにnullが代入されている
	 */
	@Test
	public void testCreateCpuOfJankenPlayer004() {
		//モック化
		try(MockedStatic<Keybord> mockKeybord = mockStatic(Keybord.class)){
			mockKeybord.when(() -> Keybord.getInt()).thenReturn(this.multi);
			
			//準備
			Method method = JankenCuiGameApplicationImpl.class.getDeclaredMethod("createCpuOfJankenPlayer");
			method.setAccessible(true);

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
	 * playerListの長さが2以上
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
	 * playerListの長さが0
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
	 * playerListがnull
	 */
	@Test
	public void testSelectJankenHand003() {
		try{
			//準備
			Method method = JankenCuiGameApplicationImpl.class.getDeclaredMethod("selectJankenHand");
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			method.setAccessible(true);
			playerListField.setAccessible(true);
			playerListField.set(this.jankenCuiGameApplicationImpl, this.nullPlayerList);

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
	 * CPU:グー、プレイヤー:チョキ
	 */
	@Test
	public void testJudge001() {
		try{
			//準備
			Method method = JankenCuiGameApplicationImpl.class.getDeclaredMethod("judge");
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			method.setAccessible(true);
			playerListField.setAccessible(true);
			
			//モック化
			JankenPlayer humanPlayer = mock(HumanJankenPlayerImpl.class);
			JankenPlayer cpuPlayer = mock(CpuJankenPlayerImpl.class);
			when(humanPlayer.getJankenHand()).thenReturn(this.scissors);
			when(cpuPlayer.getJankenHand()).thenReturn(this.rock);
			List<JankenPlayer> playerList = new ArrayList<JankenPlayer>(Arrays.asList(humanPlayer, cpuPlayer));
			playerListField.set(this.jankenCuiGameApplicationImpl, playerList);

			//テストメソッド
			int result = (int) method.invoke(this.jankenCuiGameApplicationImpl);

			//検証
			assertEquals(this.rock, result);
			verify(humanPlayer, times(1)).getJankenHand();
			verify(cpuPlayer, times(1)).getJankenHand();

		}catch(Exception  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testJudge001 正常系
	 * int judge() throws SystemException
	 * CPU:グー、プレイヤー:パー
	 */
	@Test
	public void testJudge002() {
		try{
			//準備
			Method method = JankenCuiGameApplicationImpl.class.getDeclaredMethod("judge");
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			method.setAccessible(true);
			playerListField.setAccessible(true);
			
			//モック化
			JankenPlayer humanPlayer = mock(HumanJankenPlayerImpl.class);
			JankenPlayer cpuPlayer = mock(CpuJankenPlayerImpl.class);
			when(humanPlayer.getJankenHand()).thenReturn(this.paper);
			when(cpuPlayer.getJankenHand()).thenReturn(this.rock);
			List<JankenPlayer> playerList = new ArrayList<JankenPlayer>(Arrays.asList(humanPlayer, cpuPlayer));
			playerListField.set(this.jankenCuiGameApplicationImpl, playerList);

			//テストメソッド
			int result = (int) method.invoke(this.jankenCuiGameApplicationImpl);

			//検証
			assertEquals(this.paper, result);
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
	 * CPU:チョキ、プレイヤー:パー
	 */
	@Test
	public void testJudge003() {
		try{
			//準備
			Method method = JankenCuiGameApplicationImpl.class.getDeclaredMethod("judge");
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			method.setAccessible(true);
			playerListField.setAccessible(true);
			
			//モック化
			JankenPlayer humanPlayer = mock(HumanJankenPlayerImpl.class);
			JankenPlayer cpuPlayer = mock(CpuJankenPlayerImpl.class);
			when(humanPlayer.getJankenHand()).thenReturn(this.paper);
			when(cpuPlayer.getJankenHand()).thenReturn(this.scissors);
			List<JankenPlayer> playerList = new ArrayList<JankenPlayer>(Arrays.asList(humanPlayer, cpuPlayer));
			playerListField.set(this.jankenCuiGameApplicationImpl, playerList);

			//テストメソッド
			int result = (int) method.invoke(this.jankenCuiGameApplicationImpl);

			//検証
			assertEquals(this.scissors, result);
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
	 * CPU:グー、プレイヤー:グー
	 */
	@Test
	public void testJudge004() {
		try{
			//準備
			Method method = JankenCuiGameApplicationImpl.class.getDeclaredMethod("judge");
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			method.setAccessible(true);
			playerListField.setAccessible(true);
			
			//モック化
			JankenPlayer humanPlayer = mock(HumanJankenPlayerImpl.class);
			JankenPlayer cpuPlayer = mock(CpuJankenPlayerImpl.class);
			when(humanPlayer.getJankenHand()).thenReturn(this.rock);
			when(cpuPlayer.getJankenHand()).thenReturn(this.rock);
			List<JankenPlayer> playerList = new ArrayList<JankenPlayer>(Arrays.asList(humanPlayer, cpuPlayer));
			playerListField.set(this.jankenCuiGameApplicationImpl, playerList);

			//テストメソッド
			int result = (int) method.invoke(this.jankenCuiGameApplicationImpl);

			//検証
			assertEquals(this.draw, result);
			verify(humanPlayer, times(1)).getJankenHand();
			verify(cpuPlayer, times(1)).getJankenHand();

		}catch(Exception  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testJudge005 異常系
	 * int judge() throws SystemException
	 * CPU:グー、プレイヤー:異常値
	 */
	@Test
	public void testJudge005() {
		try{
			//準備
			Method method = JankenCuiGameApplicationImpl.class.getDeclaredMethod("judge");
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			method.setAccessible(true);
			playerListField.setAccessible(true);
			
			//モック化
			JankenPlayer humanPlayer = mock(HumanJankenPlayerImpl.class);
			JankenPlayer cpuPlayer = mock(CpuJankenPlayerImpl.class);
			when(humanPlayer.getJankenHand()).thenReturn(this.illegalValue);
			when(cpuPlayer.getJankenHand()).thenReturn(this.rock);
			List<JankenPlayer> playerList = new ArrayList<JankenPlayer>(Arrays.asList(humanPlayer, cpuPlayer));
			playerListField.set(this.jankenCuiGameApplicationImpl, playerList);

			//テストメソッド
			InvocationTargetException e = assertThrows(InvocationTargetException.class, () -> method.invoke(this.jankenCuiGameApplicationImpl));

			//検証
			assertEquals(NullPointerException.class , e.getTargetException().getClass());
			verify(humanPlayer, times(1)).getJankenHand();
			verify(cpuPlayer, times(0)).getJankenHand();

		}catch(NullPointerException| SecurityException | NoSuchMethodException | IllegalAccessException | IllegalArgumentException  | ExceptionInInitializerError | NoSuchFieldException e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testJudge006 異常系
	 * int judge() throws SystemException
	 * プレイヤー:異常値(DRAW), CPU:グー
	 */
	@Test
	public void testJudge006() {
		try{
			//準備
			Method method = JankenCuiGameApplicationImpl.class.getDeclaredMethod("judge");
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			method.setAccessible(true);
			playerListField.setAccessible(true);
			
			//モック化
			JankenPlayer humanPlayer = mock(HumanJankenPlayerImpl.class);
			JankenPlayer cpuPlayer = mock(CpuJankenPlayerImpl.class);
			when(humanPlayer.getJankenHand()).thenReturn(this.draw);
			when(cpuPlayer.getJankenHand()).thenReturn(this.rock);
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
	 * testViewWinner001 正常系
	 * private void viewWinner() throws SystemException
	 * プレイヤー:グー, CPU:チョキ
	 */
	@Test
	public void testViewWinner001() {
		try{
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
			when(humanPlayer.getJankenHand()).thenReturn(this.rock);
			when(cpuPlayer.getJankenHand()).thenReturn(this.scissors);
			when(humanPlayer.getPlayerName()).thenReturn(this.playerName1);
			when(cpuPlayer.getPlayerName()).thenReturn(this.cpuName1);
			List<JankenPlayer> playerList = new ArrayList<JankenPlayer>(Arrays.asList(humanPlayer, cpuPlayer));
			playerListField.set(this.jankenCuiGameApplicationImpl, playerList);
			winHandField.set(this.jankenCuiGameApplicationImpl, this.rock);

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
	 * testViewWinner002 異常系
	 * private void viewWinner() throws SystemException
	 * プレイヤー:グー, CPU:グー
	 * 引き分けなのに実行された場合
	 */
	@Test
	public void testViewWinner002() {
		try{
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
			when(humanPlayer.getJankenHand()).thenReturn(this.rock);
			when(cpuPlayer.getJankenHand()).thenReturn(this.rock);
			when(humanPlayer.getPlayerName()).thenReturn(this.playerName1);
			when(cpuPlayer.getPlayerName()).thenReturn(this.cpuName1);
			List<JankenPlayer> playerList = new ArrayList<JankenPlayer>(Arrays.asList(humanPlayer, cpuPlayer));
			playerListField.set(this.jankenCuiGameApplicationImpl, playerList);
			winHandField.set(this.jankenCuiGameApplicationImpl, this.scissors);

			//テストメソッド
			method.invoke(this.jankenCuiGameApplicationImpl);

			//検証
			verify(humanPlayer, times(1)).getJankenHand();
			verify(cpuPlayer, times(1)).getJankenHand();
			verify(humanPlayer, times(0)).getPlayerName();
			verify(cpuPlayer, times(0)).getPlayerName();

		}catch(Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testIsCheckJankenPlayerCount001 正常系
	 * void isCheckJankenPlayerCount() throws SystemException
	 * playerListの長さが2以上
	 */
	@Test
	public void testIsCheckJankenPlayerCount001() {
		try{
			//準備
			Method method = JankenCuiGameApplicationImpl.class.getDeclaredMethod("isCheckJankenPlayerCount");
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			method.setAccessible(true);
			playerListField.setAccessible(true);
			playerListField.set(this.jankenCuiGameApplicationImpl, this.playerList);

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
	 * playerListの長さが2未満
	 */
	@Test
	public void testIsCheckJankenPlayerCount002() {
		try{
			//準備
			Method method = JankenCuiGameApplicationImpl.class.getDeclaredMethod("isCheckJankenPlayerCount");
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			method.setAccessible(true);
			playerListField.setAccessible(true);
			playerListField.set(this.jankenCuiGameApplicationImpl, this.emptyPlayerList);

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
	 * playerListがnull
	 */
	@Test
	public void testIsCheckJankenPlayerCount003() {
		try{
			//準備
			Method method = JankenCuiGameApplicationImpl.class.getDeclaredMethod("isCheckJankenPlayerCount");
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			method.setAccessible(true);
			playerListField.setAccessible(true);
			playerListField.set(this.jankenCuiGameApplicationImpl, this.nullPlayerList);

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
	 * 継続を選択
	 */
	@Test
	public void testHasGameContinue001() {
		//モック化
		try(MockedStatic<Keybord> mockKeybord = mockStatic(Keybord.class)){
			mockKeybord.when(() -> Keybord.getInt(this.one, this.two)).thenReturn(this.one);
			
			//準備
			Method method = JankenCuiGameApplicationImpl.class.getDeclaredMethod("hasGameContinue");
			method.setAccessible(true);

			//テストメソッド
			boolean result = (boolean)method.invoke(this.jankenCuiGameApplicationImpl);

			//検証
			assertEquals(result, true);
			mockKeybord.verify(() -> Keybord.getInt(this.one, this.two), times(1));
			
		}catch(Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testHasGameContinue002 正常系
	 * void hasGameContinue() throws SystemException
	 * 終了を選択
	 */
	@Test
	public void testHasGameContinue002() {
		//モック化
		try(MockedStatic<Keybord> mockKeybord = mockStatic(Keybord.class)){
			mockKeybord.when(() -> Keybord.getInt(this.one, this.two)).thenReturn(this.two);
			
			//準備
			Method method = JankenCuiGameApplicationImpl.class.getDeclaredMethod("hasGameContinue");
			method.setAccessible(true);

			//テストメソッド
			boolean result = (boolean)method.invoke(this.jankenCuiGameApplicationImpl);

			//検証
			assertEquals(result, false);
			mockKeybord.verify(() -> Keybord.getInt(this.one, this.two), times(1));
			
		}catch(Exception  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testHasGameContinue003 正常系
	 * void hasGameContinue() throws SystemException
	 * KeybordのgetInt()から一度ApplicationExceptionを発生し二度目に継続を選択
	 */
	@Test
	public void testHasGameContinue003() {
		//モック化
		try(MockedStatic<Keybord> mockKeybord = mockStatic(Keybord.class)){
			mockKeybord.when(() -> Keybord.getInt(this.one, this.two)).thenThrow(new ApplicationException(null)).thenReturn(this.one);
			
			//準備
			Method method = JankenCuiGameApplicationImpl.class.getDeclaredMethod("hasGameContinue");
			method.setAccessible(true);

			//テストメソッド
			boolean result = (boolean)method.invoke(this.jankenCuiGameApplicationImpl);

			//検証
			assertEquals(result, true);
			mockKeybord.verify(() -> Keybord.getInt(this.one, this.two), times(2));
			
		}catch(Exception  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testHasGameContinue004 正常系
	 * void hasGameContinue() throws SystemException
	 * KeybordのgetInt()から1,2以外の値が返る
	 */
	@Test
	public void testHasGameContinue004() {
		//モック化
		try(MockedStatic<Keybord> mockKeybord = mockStatic(Keybord.class)){
			mockKeybord.when(() -> Keybord.getInt(this.one, this.two)).thenReturn(this.illegalValue);
			
			//準備
			Method method = JankenCuiGameApplicationImpl.class.getDeclaredMethod("hasGameContinue");
			method.setAccessible(true);

			//テストメソッド
			InvocationTargetException e = assertThrows(InvocationTargetException.class, () -> method.invoke(this.jankenCuiGameApplicationImpl));

			//検証
			assertEquals(SystemException.class , e.getTargetException().getClass());
			mockKeybord.verify(() -> Keybord.getInt(this.one, this.two), times(1));
			
		}catch(Exception  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testGetPlayerList001 正常系
	 * List<JankenPlayer> getPlayerList()
	 * playerListフィールドにArrayList<JankenPlayer>インスタンスが代入され, 要素が追加されている場合
	 */
	@Test
	public void testGetPlayerList001() {
		try {
			//準備
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			playerListField.setAccessible(true);
			playerListField.set(this.jankenCuiGameApplicationImpl, this.playerList);

			//テストメソッド
			List<JankenPlayer> result = this.jankenCuiGameApplicationImpl.getPlayerList();

			//検証
			assertEquals(this.playerList.size(), result.size());
			assertEquals(this.humanPlayer1.getPlayerName(), result.get(0).getPlayerName());
			assertEquals(this.cpuPlayer1.getPlayerName(), result.get(1).getPlayerName());

		}catch(Exception  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testGetPlayerList002 正常系
	 * List<JankenPlayer> getPlayerList()
	 * playerListフィールドにArrayList<JankenPlayer>インスタンスが代入され, 要素が追加されていない場合
	 */
	@Test
	public void testGetPlayerList002() {
		try {
			//準備
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			playerListField.setAccessible(true);
			playerListField.set(this.jankenCuiGameApplicationImpl, this.emptyPlayerList);

			//テストメソッド
			List<JankenPlayer> result = this.jankenCuiGameApplicationImpl.getPlayerList();

			//検証
			assertEquals(this.emptyPlayerList.size(), result.size());

		}catch(Exception  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testGetPlayerList002 正常系
	 * List<JankenPlayer> getPlayerList()
	 * playerListフィールドにnullが代入されている場合
	 */
	@Test
	public void testGetPlayerList003() {
		try {
			//準備
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			playerListField.setAccessible(true);
			playerListField.set(this.jankenCuiGameApplicationImpl, this.nullPlayerList);

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
	 * void setPlayerList(List<JankenPlayer> playerList)
	 * 引数playerListに要素が追加されているArrayList<JankenPlayer>インスタンスを渡す
	 */
	@Test
	public void testSetPlayerList001() {
		try {
			//準備
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			playerListField.setAccessible(true);

			//テストメソッド
			this.jankenCuiGameApplicationImpl.setPlayerList(this.playerList);

			//検証
			@SuppressWarnings("unchecked")
			List<JankenPlayer> result = (List<JankenPlayer>) playerListField.get(this.jankenCuiGameApplicationImpl);
			assertEquals(this.playerList.size(), result.size());
			assertEquals(this.humanPlayer1.getPlayerName(), result.get(0).getPlayerName());
			assertEquals(this.cpuPlayer1.getPlayerName(), result.get(1).getPlayerName());

		}catch(Exception  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testSetPlayerList002 正常系
	 * void setPlayerList(List<JankenPlayer> playerList)
	 * 引数playerListに要素が追加されていないArrayList<JankenPlayer>インスタンスを渡す
	 */
	@Test
	public void testSetPlayerList002() {
		try {
			//準備
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			playerListField.setAccessible(true);

			//テストメソッド
			this.jankenCuiGameApplicationImpl.setPlayerList(this.emptyPlayerList);

			//検証
			@SuppressWarnings("unchecked")
			List<JankenPlayer> result = (List<JankenPlayer>) playerListField.get(this.jankenCuiGameApplicationImpl);
			assertEquals(this.emptyPlayerList.size(), result.size());

		}catch(Exception  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testSetPlayerList002 異常系
	 * void setPlayerList(List<JankenPlayer> playerList)
	 * 引数playerListにnullを渡す
	 */
	@Test
	public void testSetPlayerList003() {
		try {
			//準備
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("playerList");
			playerListField.setAccessible(true);

			//テストメソッド
			this.jankenCuiGameApplicationImpl.setPlayerList(this.nullPlayerList);

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
	 * testGetPlayerList001 正常系
	 * int getWinHand()
	 * winHandフィールドを取得
	 */
	@Test
	public void testGetWinHand001() {
		try {
			//準備
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("winHand");
			playerListField.setAccessible(true);
			playerListField.set(this.jankenCuiGameApplicationImpl, this.one);

			//テストメソッド
			int result = this.jankenCuiGameApplicationImpl.getWinHand();

			//検証
			assertEquals(this.one, result);

		}catch(Exception  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testSetPlayerList001 正常系
	 * int getWinHand()
	 * winHandフィールドを取得
	 */
	@Test
	public void testSetWinHand001() {
		try {
			//準備
			Field playerListField = JankenCuiGameApplicationImpl.class.getDeclaredField("winHand");
			playerListField.setAccessible(true);

			//テストメソッド
			this.jankenCuiGameApplicationImpl.setWinHand(this.one);

			//検証
			int result = (int) playerListField.get(this.jankenCuiGameApplicationImpl);
			assertEquals(this.one, result);

		}catch(Exception  e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testAction001 正常系
	 * public void action() throws SystemException
	 * humna:1人, cpu:1人, プレーヤー数2人以上 1回目2未満にして入力を再度求める
	 * human:グー、cpu:チョキ、1回目あいこにして再度入力を求める
	 * 継続し、同じ入力で繰り返して終了する
	 * SystemExceptionも含めて例外が発生しないことを確認
	 */
	@Test
	public void testAction001() {
		//モック化
		try(MockedStatic<Keybord> mockKeybord = mockStatic(Keybord.class);
				//CpuPlayerチョキ、CpuPlayer:チョキ、CpuPlayer:チョキ
			 MockedConstruction<Random> mockRandom = mockConstruction(Random.class, (random, context) -> when(random.nextInt(this.paper)).thenReturn(this.scissors-1))){

			//HumanPlayer:1、CpuPlayer:0、HumanPlayer1、CpuPlayer1、HumanPlayer1、CpuPlayer1
			mockKeybord.when(() -> Keybord.getInt()).thenReturn(this.one).thenReturn(this.zero).thenReturn(this.one).thenReturn(this.one).thenReturn(this.one).thenReturn(this.one);
			//継続する, 終了する
			mockKeybord.when(() -> Keybord.getInt(this.one, this.two)).thenReturn(this.one).thenReturn(this.two);
			//HumanPlayer:チョキ、HumanPlayer:グー、HumanPlayer:グー
			mockKeybord.when(() -> Keybord.getInt(this.rock, this.paper)).thenReturn(this.scissors).thenReturn(this.rock).thenReturn(this.rock);
			
			//テストメソッド
			this.jankenCuiGameApplicationImpl.action();
			
			//検証
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}
