package jp.co.rjc.mypassword.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * アプリケーションの永続設定データに関するユーティリティを提供します.
 *
 * @author 石井 友和
 */
public final class SharedPreferencesUtils {

	public static final long INVALIDATE = 0L;

	/**
	 * インスタンス化できない事を強制します。
	 */
	private SharedPreferencesUtils() {
	}

	public static SharedPreferences getSharedPreferences(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context);
	}

	// ////////////////////////////////////////////////////////////////////////

	// PIN入力ステータス情報
	public static final String PIN_INPUT_STATUS = "pin_input_status";

	public static int getPinInputStatus(final Context context) {
		return getSharedPreferences(context).getInt(PIN_INPUT_STATUS, 0);
	}

	public static void savePinInputStatus(final Context context, final int value) {
		getSharedPreferences(context).edit().putInt(PIN_INPUT_STATUS, value).commit();
	}
}
