package jp.co.rjc.mypassword;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);

        //ToDo 新規登録画面に飛ぶ
//        setContentView(R.layout.fragment_detail);
        //ToDo 呼ぶ画面のIntentを設定する
//        Intent intent = new Intent(this, TopActivity.class);
//        intent.putExtra("foo", someData);
//        startActivity(intent);
    }
}
