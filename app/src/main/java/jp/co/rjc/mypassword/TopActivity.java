package jp.co.rjc.mypassword;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class TopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);

        findViewById(R.id.goUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setClassName("jp.co.rjc.mypassword", "jp.co.rjc.mypassword.DetailActivity");

                startActivity(i);
            }
        });

        // インスタンス生成
        Button mCancel = (Button)findViewById(R.id.cancel);
        TextView mInfo = (TextView)findViewById(R.id.info);
        EditText mPin1 = (EditText)findViewById(R.id.pin1);
        EditText mPin2 = (EditText)findViewById(R.id.pin2);
        EditText mPin3 = (EditText)findViewById(R.id.pin3);
        EditText mPin4 = (EditText)findViewById(R.id.pin4);
        TextView mTenKey0 = (TextView)findViewById(R.id.tenKey0);
        TextView mTenKey1 = (TextView)findViewById(R.id.tenKey1);
        TextView mTenKey2 = (TextView)findViewById(R.id.tenKey2);
        TextView mTenKey3 = (TextView)findViewById(R.id.tenKey3);
        TextView mTenKey4 = (TextView)findViewById(R.id.tenKey4);
        TextView mTenKey5 = (TextView)findViewById(R.id.tenKey5);
        TextView mTenKey6 = (TextView)findViewById(R.id.tenKey6);
        TextView mTenKey7 = (TextView)findViewById(R.id.tenKey7);
        TextView mTenKey8 = (TextView)findViewById(R.id.tenKey8);
        TextView mTenKey9 = (TextView)findViewById(R.id.tenKey9);
        TextView mTenKeyBS = (TextView)findViewById(R.id.tenKeyBS);

        // 取消ボタンの表示制御
        Intent mI = getIntent();
        boolean editFlg = mI.getBooleanExtra(“EDIT_FLG”);
        if (editFlg) {
            cancel.INVISIBLE;
        }
    }
}
