package jp.co.ginga.util.properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import jp.co.ginga.util.exception.SystemException;

/**
 * SysetemMessagePropertiesクラス
 *
 */
public class MessagePropertiesTest {
	//テストデータ
	private String messageId1 = "msg.start";
	private String messageId2 = "janken.msg.game.winner";
	private String errorMessageId1 = "msg.error.argument";
	private String errorMessageId2 = "error.nodata";
	private String errorMessageId3 = "msg.error.properties.load";
	private String emptyMessageId = "";
	private String message1 = "これからゲームを開始します\n遊ぶゲームを選択してください.\n1:昭和クイズゲーム 2:じゃんけん";
	private String message2 = "勝利者は、プレーヤーです。";
	private String embedMessage = "プレーヤー";
	private String errorMessage = "msg.error.argument";
	private String errorMessage3 = "msg.error.properties.load";

	/**
	 * testInit001 正常系
	 * private static void init() throws SystemException
	 * propertiesフィールドがnullの場合
	 */
	@Test
	public void testInit001() {
		try {
			Properties properties = new Properties();
			InputStream is = MessageProperties.class.getClassLoader().getResourceAsStream("properties/messages.properties");
			properties.load(new InputStreamReader(is,"UTF-8"));

			Method method = MessageProperties.class.getDeclaredMethod("init");
			Field propertiesField = MessageProperties.class.getDeclaredField("properties");
			propertiesField.setAccessible(true);
			method.setAccessible(true);
			propertiesField.set(MessageProperties.class, null);


			//テストメソッド
			method.invoke(MessageProperties.class);
			//検証
			Properties result = (Properties) propertiesField.get(MessageProperties.class);
			assertEquals(properties, result);
		} catch (NoSuchMethodException | SecurityException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException | IOException | InvocationTargetException e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testInit001 正常系
	 * private static void init() throws SystemException
	 * propertiesフィールドがnullではない場合
	 */
	@Test
	public void testInit002() {
		try {
			Properties properties = new Properties();
			InputStream is = MessageProperties.class.getClassLoader().getResourceAsStream("properties/messages.properties");
			properties.load(new InputStreamReader(is,"UTF-8"));

			Method method = MessageProperties.class.getDeclaredMethod("init");
			Field propertiesField = MessageProperties.class.getDeclaredField("properties");
			propertiesField.setAccessible(true);
			method.setAccessible(true);
			propertiesField.set(MessageProperties.class, properties);

			//テストメソッド
			method.invoke(MessageProperties.class);
			//検証
			Properties result = (Properties) propertiesField.get(MessageProperties.class);
			assertEquals(properties, result);
		} catch (NoSuchMethodException | SecurityException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException | IOException | InvocationTargetException e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testInit003 異常系
	 * private static void init() throws SystemException
	 * propertiesフィールドがnullではない場合
	 */
	@Test
	public void testInit003() {
		try(MockedConstruction<Properties> mockProperties = mockConstruction(Properties.class,
				(properties, context) -> {
					doThrow(new IOException()).when(properties).load(new InputStreamReader( MessageProperties.class.getClassLoader().getResourceAsStream("properties/messages.properties"),"UTF-8"));
					doReturn(this.errorMessage3).when(properties).getProperty(this.errorMessageId3);
			})){
			Method method = MessageProperties.class.getDeclaredMethod("init");
			Field propertiesField = MessageProperties.class.getDeclaredField("properties");
			propertiesField.setAccessible(true);
			method.setAccessible(true);
			propertiesField.set(MessageProperties.class, null);

			//テストメソッド
			InvocationTargetException e = assertThrows(InvocationTargetException.class, () -> method.invoke(MessageProperties.class));
			//検証
			assertEquals(SystemException.class, e.getClass());

		} catch (NoSuchMethodException | SecurityException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testGetMessage001 正常系
	 * public static String getMessage(String resourceId) throws SystemException
	 * 引数にメッセージコードを渡して正常に文字列が取得できるかを確認する
	 */
	@Test
	public void testGetMessage001() {
		try {
			//テストメソッド
			String result = MessageProperties.getMessage(this.messageId1);
			//検証
			assertEquals(this.message1, result);
		} catch (SystemException e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testGetMessage002 異常系
	 * public static String getMessage(String resourceId) throws SystemException
	 * 引数に空のメッセージコードを渡してSystemExceptionが発生することを確認する
	 */
	@Test
	public void testGetMessage002() {
		//モック化
		try {
			Properties mockProperties = mock(Properties.class);
			when(mockProperties.getProperty(this.errorMessageId1)).thenReturn(this.errorMessage);
			Field propertiesField = MessageProperties.class.getDeclaredField("properties");
			propertiesField.setAccessible(true);
			propertiesField.set(MessageProperties.class, mockProperties);

			//テストメソッド、検証
			SystemException e = assertThrows(SystemException.class, () -> MessageProperties.getMessage(this.emptyMessageId));
			assertEquals(this.errorMessage, e.getSysMsg());
		}catch(Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testGetMessage003 異常系
	 * public static String getMessage(String resourceId) throws SystemException
	 * 引数にnullメッセージコードを渡してSystemExceptionが発生することを確認する
	 */
	@Test
	public void testGetMessage003() {
		//モック化
		try {
			Properties mockProperties = mock(Properties.class);
			when(mockProperties.getProperty(this.errorMessageId1)).thenReturn(this.errorMessage);
			Field propertiesField = MessageProperties.class.getDeclaredField("properties");
			propertiesField.setAccessible(true);
			propertiesField.set(MessageProperties.class, mockProperties);

			//テストメソッド、検証
			SystemException e = assertThrows(SystemException.class, () -> MessageProperties.getMessage(null));
			assertEquals(this.errorMessage, e.getSysMsg());
		}catch(Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testGetMessage004 異常系
	 * public static String getMessage(String resourceId) throws SystemException
	 * 引数にメッセージコードを渡してSystemExceptionが発生することを確認する
	 */
	@Test
	public void testGetMessage004() {
		//モック化
		try {
			Properties mockProperties = mock(Properties.class);
			when(mockProperties.getProperty(this.messageId1)).thenReturn(null);
			when(mockProperties.getProperty(this.errorMessageId2, this.messageId1)).thenReturn(null);
			Field propertiesField = MessageProperties.class.getDeclaredField("properties");
			propertiesField.setAccessible(true);
			propertiesField.set(MessageProperties.class, mockProperties);

			//テストメソッド、検証
			SystemException e = assertThrows(SystemException.class, () -> MessageProperties.getMessage(this.messageId1));
			assertEquals(null, e.getSysMsg());
		}catch(Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testGetMessageEmbed001 正常系
	 * public static String getMessage(String resourceId, String... arguments) throws SystemException {
	 * 引数にメッセージコードを渡して正常に文字列が取得できるかを確認する
	 */
	@Test
	public void testGetMessageEmbed001() {
		try {
			//テストメソッド
			String result = MessageProperties.getMessage(this.messageId2, this.embedMessage);
			//検証
			assertEquals(this.message2, result);
		} catch (SystemException e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testGetMessageEmbed002 異常系
	 * public static String getMessage(String resourceId, String... arguments) throws SystemException {
	 * 引数に空のメッセージコードを渡してSystemExceptionが発生することを確認する
	 */
	@Test
	public void testGetMessageEmbed002() {
		//モック化
		try {
			Properties mockProperties = mock(Properties.class);
			when(mockProperties.getProperty(this.errorMessageId1)).thenReturn(this.errorMessage);
			Field propertiesField = MessageProperties.class.getDeclaredField("properties");
			propertiesField.setAccessible(true);
			propertiesField.set(MessageProperties.class, mockProperties);

			//テストメソッド、検証
			SystemException e = assertThrows(SystemException.class, () -> MessageProperties.getMessage(this.emptyMessageId, this.embedMessage));
			assertEquals(this.errorMessage, e.getSysMsg());
		}catch(Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testGetMessage003 異常系
	 * public static String getMessage(String resourceId, String... arguments) throws SystemException {
	 * 引数にnullメッセージコードを渡してSystemExceptionが発生することを確認する
	 */
	@Test
	public void testGetMessageEmbed003() {
		//モック化
		try {
			Properties mockProperties = mock(Properties.class);
			when(mockProperties.getProperty(this.errorMessageId1)).thenReturn(this.errorMessage);
			Field propertiesField = MessageProperties.class.getDeclaredField("properties");
			propertiesField.setAccessible(true);
			propertiesField.set(MessageProperties.class, mockProperties);

			//テストメソッド、検証
			SystemException e = assertThrows(SystemException.class, () -> MessageProperties.getMessage(null, this.embedMessage));
			assertEquals(this.errorMessage, e.getSysMsg());
		}catch(Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testGetMessageEmbed004 異常系
	 * public static String getMessage(String resourceId, String... arguments) throws SystemException {
	 * 引数にメッセージコードとnullを渡してSystemExceptionが発生することを確認する
	 */
	@Test
	public void testGetMessageEmbed004() {
		//モック化
		try {
			Properties mockProperties = mock(Properties.class);
			when(mockProperties.getProperty(this.errorMessageId1)).thenReturn(this.errorMessage);
			Field propertiesField = MessageProperties.class.getDeclaredField("properties");
			propertiesField.setAccessible(true);
			propertiesField.set(MessageProperties.class, mockProperties);

			//テストメソッド、検証
			SystemException e = assertThrows(SystemException.class, () -> MessageProperties.getMessage(this.emptyMessageId, null));
			assertEquals(this.errorMessage, e.getSysMsg());
		}catch(Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * testGetMessageEmbed005 異常系
	 * public static String getMessage(String resourceId, String... arguments) throws SystemException {
	 * 引数にメッセージコードを渡してSystemExceptionが発生することを確認する
	 */
	@Test
	public void testGetMessageEmbed005() {
		//モック化
		try {
			Properties mockProperties = mock(Properties.class);
			when(mockProperties.getProperty(this.messageId1)).thenReturn(null);
			when(mockProperties.getProperty(this.errorMessageId2, this.messageId1)).thenReturn(null);
			Field propertiesField = MessageProperties.class.getDeclaredField("properties");
			propertiesField.setAccessible(true);
			propertiesField.set(MessageProperties.class, mockProperties);

			//テストメソッド、検証
			SystemException e = assertThrows(SystemException.class, () -> MessageProperties.getMessage(this.messageId2, this.embedMessage));
			assertEquals(null, e.getSysMsg());
		}catch(Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}
