package jp.co.ginga.cui.impl.janken;

import java.util.EnumSet;

/**
 * JankenParam ENUM
 * @author yoshi
 *
 */
public enum JankenParam {

	DRAW(0),
	ROCK(1),
	SCISSORS(2),
	PAPER(3),
	;
	
	
	/**
	 * ENUM設定値
	 */
    private int enumValue;

    // コンストラクタの定義
    private JankenParam(int enumValue) {
        this.enumValue = enumValue;
    }

    /**
     * ENUM設定値 取得処理
	 * @return enumValue
	 */
	public int getInt() {
		return this.enumValue;
	}


    /**
     * ENUM値 取得処理
     * @param value
     * @return enum値
     */
    public static JankenParam getEnum(final int value) {
        for(JankenParam jp : EnumSet.allOf(JankenParam.class)) {
            if(jp.ordinal() == value) {  //ordinal() 列挙子の順番を取得
                return jp;
            }
        }
    	
        //以下の処理は、本来なら不具合(バグ)の記述になります。
        //なぜ不具合(バグ)なのか、正しい記述を考えてみてください。
        //解った人は、講師陣に報告をお願いします。
        return null;
    }

}