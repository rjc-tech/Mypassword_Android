package jp.co.rjc.mypassword.ui.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import jp.co.rjc.mypassword.R;
import jp.co.rjc.mypassword.common.Globals;
import jp.co.rjc.mypassword.db.DatabaseHelper;
import jp.co.rjc.mypassword.provider.LoginInfos;
import jp.co.rjc.mypassword.util.ActivityUtils;
import jp.co.rjc.mypassword.util.SharedPreferencesUtils;

/**
 * PIN入力画面を提供します.
 */
public class InputPinActivity extends AppCompatActivity {

	/**
	 * 未登録(新規登録)ステータス
	 */
	private static final int INPUT_PIN_STATUS_UNREGISTERED = 0;
	/**
	 * 未登録時PIN情報入力ステータス(2回目)
	 */
	private static final int INPUT_PIN_STATUS_UNREGISTERED_2ND = 1;
	/**
	 * 登録済みステータス
	 */
	private static final int INPUT_PIN_STATUS_REGISTERED = 2;
	/**
	 * PIN情報更新ステータス
	 */
	public static final int INPUT_PIN_STATUS_EDIT = 3;
	/**
	 * 新PIN情報入力ステータス(1回目)
	 */
	private static final int INPUT_PIN_STATUS_EDIT_NEW_1ST = 4;
	/**
	 * 新PIN情報入力ステータス(2回目)
	 */
	private static final int INPUT_PIN_STATUS_EDIT_NEW_2ND = 5;

	private int mInputPinStatus;
	private int mTemporaryId;
	private String mTemporaryPin;

	/*
	デバッグ用PIN変更メニューボタン
	MenuItem mEditPinBtn;
	*/

	// PIN入力メッセージ
	private TextView mInfo;

	// PINコード入力欄
	private EditText mPin1;
	private EditText mPin2;
	private EditText mPin3;
	private EditText mPin4;
	// テンキー ※ Buttonで実装(TextViewから変更)
	private Button mTenKey0;
	private Button mTenKey1;
	private Button mTenKey2;
	private Button mTenKey3;
	private Button mTenKey4;
	private Button mTenKey5;
	private Button mTenKey6;
	private Button mTenKey7;
	private Button mTenKey8;
	private Button mTenKey9;
	private Button mTenKeyBS;
	// PIN変更キャンセルボタン
	private Button mBtnEditPinCancel;

	//////////////////////////////////////////////////////////////////
	// ライフサイクル

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_input_pin);

		// viewインスタンス生成
		mInfo = (TextView) findViewById(R.id.info);
		mPin1 = (EditText) findViewById(R.id.pin1);
		mPin2 = (EditText) findViewById(R.id.pin2);
		mPin3 = (EditText) findViewById(R.id.pin3);
		mPin4 = (EditText) findViewById(R.id.pin4);
		setDisableTouch(mPin1);
		setDisableTouch(mPin2);
		setDisableTouch(mPin3);
		setDisableTouch(mPin4);

		mTenKey0 = (Button) findViewById(R.id.tenKey0);
		mTenKey1 = (Button) findViewById(R.id.tenKey1);
		mTenKey2 = (Button) findViewById(R.id.tenKey2);
		mTenKey3 = (Button) findViewById(R.id.tenKey3);
		mTenKey4 = (Button) findViewById(R.id.tenKey4);
		mTenKey5 = (Button) findViewById(R.id.tenKey5);
		mTenKey6 = (Button) findViewById(R.id.tenKey6);
		mTenKey7 = (Button) findViewById(R.id.tenKey7);
		mTenKey8 = (Button) findViewById(R.id.tenKey8);
		mTenKey9 = (Button) findViewById(R.id.tenKey9);
		mTenKeyBS = (Button) findViewById(R.id.tenKeyBS);

		// テンキー押下時の処理呼び出し
		mTenKey0.setOnClickListener(new ButtonAction());
		mTenKey1.setOnClickListener(new ButtonAction());
		mTenKey2.setOnClickListener(new ButtonAction());
		mTenKey3.setOnClickListener(new ButtonAction());
		mTenKey4.setOnClickListener(new ButtonAction());
		mTenKey5.setOnClickListener(new ButtonAction());
		mTenKey6.setOnClickListener(new ButtonAction());
		mTenKey7.setOnClickListener(new ButtonAction());
		mTenKey8.setOnClickListener(new ButtonAction());
		mTenKey9.setOnClickListener(new ButtonAction());
		mTenKeyBS.setOnClickListener(new ButtonAction());

		mBtnEditPinCancel = (Button) findViewById(R.id.btn_edit_pin_cancel);
		mBtnEditPinCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				initInputPinStatus(SharedPreferencesUtils.getPinInputStatus(getApplicationContext()));
				initCancelBtnView();
				resetPin();
			}
		});
		initInputPinStatus(getIntent().getIntExtra(Globals.INTENT_KEY_INPUT_STATUS, SharedPreferencesUtils.getPinInputStatus(getApplicationContext())));
		initCancelBtnView();
	}

	//////////////////////////////////////////////////////////////////

	/*
    デバッグ用PIN変更メニューボタン
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		mEditPinBtn = menu.add(getResources().getString(R.string.btn_label_edit_pin));
		mEditPinBtn.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		mEditPinBtn.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				mInfo.setText(getResources().getString(R.string.msg_request_input_pin_old));
				mInputPinStatus = INPUT_PIN_STATUS_EDIT;
				initCancelBtnView();
				resetPin();
				return true;
			}
		});
		// 更新ステータス、あるいはPIN登録済みステータスであればPIN更新ボタンを表示する
		mEditPinBtn.setVisible(mInputPinStatus == INPUT_PIN_STATUS_EDIT || mInputPinStatus == INPUT_PIN_STATUS_REGISTERED);

		return super.onCreateOptionsMenu(menu);
	}
	*/

	/**
	 * PIN入力ステータスを更新し、メッセージを初期化します.
	 *
	 * @param inputPinStatus
	 */
	private void initInputPinStatus(final int inputPinStatus) {
		mInputPinStatus = inputPinStatus;
		if (INPUT_PIN_STATUS_UNREGISTERED == inputPinStatus) {
			mInfo.setText(getResources().getString(R.string.msg_request_input_pin_register));
		} else if (INPUT_PIN_STATUS_EDIT == inputPinStatus) {
			mInfo.setText(getResources().getString(R.string.msg_request_input_pin_old));
		} else {
			mInfo.setText(getResources().getString(R.string.msg_request_input_pin));
		}
	}

	/**
	 * PIN入力キャンセルボタンを初期化します.
	 */
	private void initCancelBtnView() {
		if (INPUT_PIN_STATUS_EDIT == mInputPinStatus
				|| INPUT_PIN_STATUS_EDIT_NEW_1ST == mInputPinStatus
				|| INPUT_PIN_STATUS_EDIT_NEW_2ND == mInputPinStatus
				|| INPUT_PIN_STATUS_UNREGISTERED_2ND == mInputPinStatus) {
			mBtnEditPinCancel.setVisibility(View.VISIBLE);
		} else {
			mBtnEditPinCancel.setVisibility(View.GONE);
		}
	}

	/**
	 * ボタン押下時の動作をまとめたクラスです.
	 */
	class ButtonAction implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			if (v.getId() == R.id.tenKeyBS) {
				deletePinText();
			} else {
				setPinText((((TextView) v).getText()).toString());
			}
		}
	}

	/**
	 * 入力のないPINコード入力欄に対し、テンキーで押下された数値を反映
	 */
	private void setPinText(String pin) {
		if (TextUtils.isEmpty(mPin1.getText())) {
			setPin(mPin1, pin, true);
		} else if (TextUtils.isEmpty(mPin2.getText())) {
			setPin(mPin2, pin, true);
		} else if (TextUtils.isEmpty(mPin3.getText())) {
			setPin(mPin3, pin, true);
		} else if (TextUtils.isEmpty(mPin4.getText())) {
			setPin(mPin4, pin, true);
		}
	}

	/**
	 * 入力のあるPINコード入力欄の数値を削除.
	 */
	private void deletePinText() {
		if (!TextUtils.isEmpty(mPin4.getText())) {
			setPin(mPin4, "", false);
		} else if (!TextUtils.isEmpty(mPin3.getText())) {
			setPin(mPin3, "", false);
		} else if (!TextUtils.isEmpty(mPin2.getText())) {
			setPin(mPin2, "", false);
		} else if (!TextUtils.isEmpty(mPin1.getText())) {
			setPin(mPin1, "", false);
		}
	}

	/**
	 * PINコード入力欄設定処理.
	 *
	 * @param view
	 * @param val
	 * @param flg
	 */
	private void setPin(final EditText view, final String val, final boolean flg) {
		final int keyId = view.getId();

		// PIN入力値設定
		view.setText(val);

		// PIN1～PIN4へフォーカス移動
		if (flg == true) {
			// 設定したPINの次のPIN入力へフォーカス移動
			if (keyId == R.id.pin1) {
				setNextFocus(mPin2);
			} else if (keyId == R.id.pin2) {
				setNextFocus(mPin3);
			} else if (keyId == R.id.pin3) {
				setNextFocus(mPin4);
			} else if (keyId == R.id.pin4) {
				judgeInputPin();
			}
			// PIN4～PIN1へフォーカス移動
		} else {
			// 設定したPINの次のPIN入力へフォーカス移動
			if (keyId == R.id.pin4) {
				setNextFocus(mPin3);
			} else if (keyId == R.id.pin3) {
				setNextFocus(mPin2);
			} else if (keyId == R.id.pin2 || keyId == R.id.pin1) {
				setNextFocus(mPin1);
			}
		}
	}

	/**
	 * 次のエディットテキストに遷移します.
	 *
	 * @param editText
	 */
	private void setNextFocus(final EditText editText) {
		editText.setFocusableInTouchMode(true);
		editText.setSelection(0);
		editText.requestFocus(View.FOCUS_UP);
	}

	/**
	 * 指定viewのタッチを無効化します.
	 *
	 * @param view
	 */
	private void setDisableTouch(View view) {
		view.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return true;
			}
		});
	}

	/**
	 * PIN入力判定を実施し、ステータスとメッセージを更新します.
	 */
	private void judgeInputPin() {
		final String inputPin =
				mPin1.getText().toString()
						+ mPin2.getText().toString()
						+ mPin3.getText().toString()
						+ mPin4.getText().toString();
		DatabaseHelper db = new DatabaseHelper(getApplicationContext());

		if (INPUT_PIN_STATUS_UNREGISTERED == mInputPinStatus) {
			mTemporaryPin = inputPin;
			mInfo.setText(getResources().getString(R.string.msg_request_input_pin_reclaim));
			mInputPinStatus = INPUT_PIN_STATUS_UNREGISTERED_2ND;
			resetPin();

		} else if (INPUT_PIN_STATUS_UNREGISTERED_2ND == mInputPinStatus) {
			if (mTemporaryPin.equals(inputPin)) {
				DatabaseHelper.insertPin(db.getWritableDatabase(), inputPin);
				mTemporaryPin = null;
				mInputPinStatus = INPUT_PIN_STATUS_REGISTERED;
				SharedPreferencesUtils.savePinInputStatus(getApplicationContext(), INPUT_PIN_STATUS_REGISTERED);
				mInfo.setText(getResources().getString(R.string.msg_request_input_pin));
				ActivityUtils.getInstance(this).displayCenterToastShort(getResources().getString(R.string.toast_success_pin_register));
				resetPin();
			} else {
				mInfo.setText(getResources().getString(R.string.msg_error_invalid_pin_different));
				resetPin();
			}

		} else if (INPUT_PIN_STATUS_EDIT_NEW_1ST == mInputPinStatus) {
			mTemporaryPin = inputPin;
			mInfo.setText(getResources().getString(R.string.msg_request_input_pin_reclaim));
			mInputPinStatus = INPUT_PIN_STATUS_EDIT_NEW_2ND;
			resetPin();

		} else if (INPUT_PIN_STATUS_EDIT_NEW_2ND == mInputPinStatus) {
			if (mTemporaryPin.equals(inputPin)) {
				DatabaseHelper.updatePin(db.getWritableDatabase(), mTemporaryId, inputPin);
				mTemporaryPin = null;
				mInputPinStatus = INPUT_PIN_STATUS_REGISTERED;
				mInfo.setText(getResources().getString(R.string.msg_request_input_pin));
				ActivityUtils.getInstance(this).displayCenterToastShort(getResources().getString(R.string.toast_success_pin_register));
				resetPin();
			} else {
				mInfo.setText(getResources().getString(R.string.msg_error_invalid_pin_different));
				resetPin();
			}

		} else {

			// pin情報を取得
			Cursor cursor = null;
			try {
				cursor = DatabaseHelper.getExistMatchPinId(db.getReadableDatabase(), inputPin);

				// 入力したPIN情報が登録情報と一致する場合
				if (cursor.moveToFirst()) {
					mTemporaryId = cursor.getInt(cursor.getColumnIndex(LoginInfos.ID));

					if (INPUT_PIN_STATUS_EDIT == mInputPinStatus) {
						mInfo.setText(getResources().getString(R.string.msg_request_input_pin_new));
						mInputPinStatus = INPUT_PIN_STATUS_EDIT_NEW_1ST;
						resetPin();

					} else if (INPUT_PIN_STATUS_REGISTERED == mInputPinStatus) {
						initInputPinStatus(mInputPinStatus);
						initCancelBtnView();
						resetPin();
						ActivityUtils.getInstance(this).displayCenterToastShort(getResources().getString(R.string.toast_success_login));
						goSiteList(cursor.getInt(cursor.getColumnIndex(LoginInfos.ID)));
					}
				} else {
					mInfo.setText(getResources().getString(R.string.msg_error_invalid_pin));
					resetPin();
				}
			} finally {
				if (cursor != null) {
					cursor.close();
				}
			}
		}
		initCancelBtnView();
	}

	/**
	 * サイトリスト画面へ遷移します.
	 */
	private void goSiteList(final int id) {
		Intent intent = new Intent(this, SiteListActivity.class);
		intent.putExtra(Globals.INTENT_KEY_ACCOUNT_ID, id);
		startActivity(intent);
	}

	/**
	 * 入力されたPIN情報を全てリセットし、カーソルを左に戻します.
	 */
	private void resetPin() {
		mPin4.setText("");
		mPin3.setText("");
		mPin2.setText("");
		mPin1.setText("");
		setNextFocus(mPin1);
	}
}