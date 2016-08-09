package jp.co.rjc.mypassword.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import jp.co.rjc.mypassword.R;

/**
 * About画面を提供します.
 */
public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // タイトル変更
        setTitle(getResources().getString(R.string.about_label_info));

        setContentView(R.layout.activity_about);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}