package jp.co.rjc.mypassword.util;

import android.app.Activity;
import android.view.Gravity;
import android.widget.Toast;

/**
 * 画面に関連する共通機能を提供するクラスです.
 */
final public class ActivityUtils {

	private static ActivityUtils sInstance;

	private Activity mActivity;

	private ActivityUtils(Activity activity) {
		mActivity = activity;
	}

	private static synchronized ActivityUtils newInstance(final Activity activity) {
		sInstance = new ActivityUtils(activity);
		return sInstance;
	}

	/**
	 * 同期されたアクティビティを保持する共通クラスを返却します.
	 *
	 * @param activity
	 * @return
	 */
	public static synchronized ActivityUtils getInstance(final Activity activity) {

		if (sInstance == null) {
			sInstance = newInstance(activity);
		}
		return sInstance;
	}

	/**
	 * 同期されたアクティビティインスタンスを破棄します.
	 */
	public static synchronized void clearInstance() {
		sInstance = null;
	}

	/**
	 * 画面中央に短時間のトーストを表示します.
	 *
	 * @param message
	 */
	public void displayCenterToastShort(final String message) {
		final Toast toast = Toast.makeText(mActivity, message, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
}
