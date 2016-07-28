package jp.co.rjc.mypassword.common;


/**
 * このアプリケーション全体で使用する定数クラスを提供します.
 *
 * @author 石井 友和
 */
public final class Globals {

	/**
	 * インスタンス化できないことを強制します.
	 */
	private Globals() {
	}

	// ////////////////////////////////////////////////////////////////////////
	// PIN入力ステータス情報

	/**
	 * PIN未登録情報です.
	 */
	public static final int UNREGISTERED_PIN = -99;

	/**
	 * 入力PIN間違え情報です.
	 */
	public static final int INVALID_PIN = -1;

	/**
	 * アカウントIDのインテントキーです.
	 */
	public static final String INTENT_KEY_ACCOUNT_ID = "intent_key_account_id";

	/**
	 * PIN入力ステータス情報のインテントキーです.
	 */
	public static final String INTENT_KEY_INPUT_STATUS = "intent_key_input_status";

	/**
	 * サイト情報編集ステータスのインテントキーです.
	 */
	public static final String INTENT_KEY_EDIT_STATUS = "intent_key_edit_status";

	/**
	 * サイトIDのインテントキーです.
	 */
	public static final String INTENT_KEY_SITE_ID = "intent_key_site_id";
}