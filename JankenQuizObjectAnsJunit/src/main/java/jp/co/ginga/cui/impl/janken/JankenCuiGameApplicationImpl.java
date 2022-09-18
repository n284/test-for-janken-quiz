package jp.co.ginga.cui.impl.janken;

import java.util.ArrayList;
import java.util.List;

import jp.co.ginga.cui.CuiGameApplication;
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
public class JankenCuiGameApplicationImpl implements CuiGameApplication {

	private List<JankenPlayer> playerList;
	private int winHand;

	/**
	 * じゃんけんゲーム処理
	 * @throws SystemException
	 */
	@Override
	public void action() throws SystemException {

		while (true) {

			while (true) {
				System.out.println(MessageProperties.getMessage("janken.msg.start"));

				//初期化処理
				this.init();

				//人間プレーヤーオブジェクト生成処理
				this.createHumanOfJankenPlayer();
				//CPUプレーヤーオブジェクト生成処理
				this.createCpuOfJankenPlayer();

				//じゃんけんプレーヤー数チェック処理
				if (isCheckJankenPlayerCount()) {
					break;
				}
				System.out.println(MessageProperties.getMessage("janken.msg.player.count.error"));
			}

			while (true) {

				//じゃんけんプレーヤのじゃんけんの手選択処理
				this.selectJankenHand();

				//判定処理
				this.winHand = this.judge();
				if (this.winHand != JankenParam.DRAW.getInt()) {
					break;
				}

				System.out.println(MessageProperties.getMessage("janken.msg.game.draw"));

			}

			//勝利者表示処理
			this.viewWinner();

			//じゃんけんゲームコンティニュー判定処理
			if (hasGameContinue() == false) {
				break;
			}

		}

		System.out.println(MessageProperties.getMessage("janken.msg.end"));


	}

	/**
	 * じゃんけんゲーム初期化処理
	 * @throws SystemException
	 */
	private void init() {

		//プレーヤリストの初期処理
		if (this.playerList == null) {
			this.playerList = new ArrayList<JankenPlayer>();
		} else {
			this.playerList.clear();
		}

	}

	/**
	 * 人間プレーヤーオブジェクト生成処理
	 * @throws SystemException
	 */
	private void createHumanOfJankenPlayer() throws SystemException {

		while (true) {

			try {

				System.out.println(MessageProperties.getMessage("janken.msg.create.human"));
				int value = Keybord.getInt();

				for (int i = 0; i < value; i++) {
					//人間プレーヤオブジェクト生成処理
					JankenPlayer jp = new HumanJankenPlayerImpl(
							MessageProperties.getMessage("janken.msg.playername.human") + (i + 1));
					playerList.add(jp);
				}

				break;

			} catch (ApplicationException e) {
				System.out.println(MessageProperties.getMessage("msg.retype"));
			}

		}

	}

	/**
	 * CPUプレーヤーオブジェクト生成処理
	 * @throws SystemException
	 */
	private void createCpuOfJankenPlayer() throws SystemException {

		while (true) {

			try {

				System.out.println(MessageProperties.getMessage("janken.msg.create.cpu"));
				int value = Keybord.getInt();

				for (int i = 0; i < value; i++) {
					//CPUプレーヤオブジェクト生成処理
					JankenPlayer jp = new CpuJankenPlayerImpl(
							MessageProperties.getMessage("janken.msg.playername.cpu") + (i + 1));
					playerList.add(jp);
				}

				break;

			} catch (ApplicationException e) {
				System.out.println(MessageProperties.getMessage("msg.retype"));
			}

		}

	}

	/**
	 * じゃんけんの手を選択する処理
	 * @throws SystemException
	 */
	private void selectJankenHand() throws SystemException {
		for (int i = 0; i < playerList.size(); i++) {
			//じゃんけんプレーヤのじゃんけんの手を選択する処理
			playerList.get(i).selectJankenHand();
		}

	}

	/**
	 * じゃんけん判定処理
	 * @return
	 * @throws SystemException
	 */
	private int judge() throws SystemException {

		boolean rockFlag = false;
		boolean scissorsFlag = false;
		boolean paperFlag = false;

		//じゃんけんプレーヤーが選択した手の状況を取得する処理
		for (JankenPlayer player : playerList) {
			switch (JankenParam.getEnum(player.getJankenHand())) {
			case ROCK:
				rockFlag = true;
				break;
			case SCISSORS:
				scissorsFlag = true;
				break;
			case PAPER:
				paperFlag = true;
				break;
			default:
				throw new SystemException(MessageProperties.getMessage("errpr.stop")); //メッセージが取得されていなかったので修正
			}

		}

		//判定処理
		if (rockFlag == true && scissorsFlag == true && paperFlag == false) {
			return JankenParam.ROCK.getInt();
		} else if (rockFlag == false && scissorsFlag == true && paperFlag == true) {
			return JankenParam.SCISSORS.getInt();
		} else if (rockFlag == true && scissorsFlag == false && paperFlag == true) {
			return JankenParam.PAPER.getInt();
		} else {
			return JankenParam.DRAW.getInt();
		}

	}

	/**
	 * 勝利者表示処理
	 * @throws SystemException
	 */
	private void viewWinner() throws SystemException {
		StringBuilder sb = new StringBuilder();
		for (JankenPlayer player : playerList) {
			if (this.winHand == player.getJankenHand()) {
				sb.append(player.getPlayerName() + " ");
			}
		}
		System.out.println(MessageProperties.getMessage("janken.msg.game.winner", sb.toString()));
	}

	/**
	 * じゃんけんプレーヤー数チェック処理
	 * @return
	 * @throws SystemException
	 */
	private boolean isCheckJankenPlayerCount() {

		if (playerList.size() >= 2) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * じゃんけんゲーム継続問い合わせ処理
	 * @return true:続ける false:終了
	 * @throws SystemException
	 */
	private boolean hasGameContinue() throws SystemException {

		while (true) {
			try {
				System.out.println(MessageProperties.getMessage("janken.msg.game.continue"));
				switch (Keybord.getInt(1, 2)) {
				case 1:
					return true;
				case 2:
					return false;
				default:
					throw new SystemException(MessageProperties.getMessage("errpr.stop"));
				}

			} catch (ApplicationException e) {
				System.out.println(MessageProperties.getMessage("msg.retype"));
			}
		}
	}

	/**
	 * @return playerList
	 */
	public List<JankenPlayer> getPlayerList() {
		return playerList;
	}

	/**
	 * @param playerList セットする playerList
	 */
	public void setPlayerList(List<JankenPlayer> playerList) {
		this.playerList = playerList;
	}

	/**
	 * @return winHand
	 */
	public int getWinHand() {
		return winHand;
	}

	/**
	 * @param winHand セットする winHand
	 */
	public void setWinHand(int winHand) {
		this.winHand = winHand;
	}

}
