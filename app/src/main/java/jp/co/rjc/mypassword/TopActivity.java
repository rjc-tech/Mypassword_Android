package jp.co.rjc.mypassword;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

public class TopActivity extends AppCompatActivity {

    // PINコード入力欄
    EditText mPin1;
    EditText mPin2;
    EditText mPin3;
    EditText mPin4;
    // テンキー ※ Buttonで実装(TextViewから変更)
    Button mTenKey0;
    Button mTenKey1;
    Button mTenKey2;
    Button mTenKey3;
    Button mTenKey4;
    Button mTenKey5;
    Button mTenKey6;
    Button mTenKey7;
    Button mTenKey8;
    Button mTenKey9;
    Button mTenKeyBS;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);

        // インスタンス生成
//        Button mCancel = (Button)findViewById(R.id.cancel);
        TextView mInfo = (TextView) findViewById(R.id.info);
        mPin1 = (EditText) findViewById(R.id.pin1);
        mPin2 = (EditText) findViewById(R.id.pin2);
        mPin3 = (EditText) findViewById(R.id.pin3);
        mPin4 = (EditText) findViewById(R.id.pin4);
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

        findViewById(R.id.goUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setClassName("jp.co.rjc.mypassword", "jp.co.rjc.mypassword.DetailActivity");

                startActivity(i);
            }
        });

        // 取消ボタンの表示制御
        Intent mI = getIntent();
        //boolean editFlg = mI.getBooleanExtra(“EDIT_FLG”);
        //if (editFlg) {
        //    cancel.INVISIBLE;
        //}

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Top Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }


    /**
     * ボタン押下時の動作をまとめたクラスです。
     */
    class ButtonAction implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v == mTenKey0) {
                setPinText("0");
            } else if (v == mTenKey1) {
                setPinText("1");
            } else if (v == mTenKey2) {
                setPinText("2");
            } else if (v == mTenKey3) {
                setPinText("3");
            } else if (v == mTenKey4) {
                setPinText("4");
            } else if (v == mTenKey5) {
                setPinText("5");
            } else if (v == mTenKey6) {
                setPinText("6");
            } else if (v == mTenKey7) {
                setPinText("7");
            } else if (v == mTenKey8) {
                setPinText("8");
            } else if (v == mTenKey9) {
                setPinText("9");
            } else if (v == mTenKeyBS) {
                deletePinText();
            }
        }
    }

    /**
     * 入力のないPINコード入力欄に対し、テンキーで押下された数値を反映
     */
    private void setPinText(String pin) {
        if (mPin1.getText() == null || mPin1.getText().length() == 0) {
            setPin(mPin1, pin, true);
        } else if (mPin2.getText() == null || mPin2.getText().length() == 0) {
            setPin(mPin2, pin, true);
        } else if (mPin3.getText() == null || mPin3.getText().length() == 0) {
            setPin(mPin3, pin, true);
        } else if (mPin4.getText() == null || mPin4.getText().length() == 0) {
            setPin(mPin4, pin, true);
        }
    }

    /**
     * 入力のあるPINコード入力欄の数値を削除
     */
    private void deletePinText() {
        if (mPin4.getText() != null && mPin4.getText().length() != 0) {
            setPin(mPin4, "", false);
        } else if (mPin3.getText() != null && mPin3.getText().length() != 0) {
            setPin(mPin3, "", false);
        } else if (mPin2.getText() != null && mPin2.getText().length() != 0) {
            setPin(mPin2, "", false);
        } else if (mPin1.getText() != null && mPin1.getText().length() != 0) {
            setPin(mPin1, "", false);
        }
    }

    /**
     * PINコード入力欄設定処理
     */
    private void setPin(EditText mPin, String val, boolean flg) {
        // PIN入力値設定
        mPin.setText(val);

        // PIN1～PIN4へフォーカス移動
        if (flg == true) {
            // 設定したPINの次のPIN入力へフォーカス移動
            if (mPin == mPin1) {
                mPin2.setFocusableInTouchMode(true);
                mPin2.setSelection(0);
                mPin2.requestFocus(View.FOCUS_UP);
            } else if (mPin == mPin2) {
                mPin3.setFocusableInTouchMode(true);
                mPin3.setSelection(0);
                mPin3.requestFocus(View.FOCUS_UP);
            } else if (mPin == mPin3) {
                mPin4.setFocusableInTouchMode(true);
                mPin4.setSelection(0);
                mPin4.requestFocus(View.FOCUS_UP);
            }
        // PIN4～PIN1へフォーカス移動
        } else {
            // 設定したPINの次のPIN入力へフォーカス移動
            if (mPin == mPin4) {
                mPin3.setFocusableInTouchMode(true);
                mPin3.setSelection(0);
                mPin3.requestFocus(View.FOCUS_UP);
            } else if (mPin == mPin3) {
                mPin2.setFocusableInTouchMode(true);
                mPin2.setSelection(0);
                mPin2.requestFocus(View.FOCUS_UP);
            } else if (mPin == mPin2) {
                mPin1.setFocusableInTouchMode(true);
                mPin1.setSelection(0);
                mPin1.requestFocus(View.FOCUS_UP);
            }
        }
    }
}