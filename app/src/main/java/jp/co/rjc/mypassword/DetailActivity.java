package jp.co.rjc.mypassword;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        findViewById(R.id.btnCreate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {





                Intent i = new Intent();
                i.setClassName("jp.co.rjc.mypassword", "jp.co.rjc.mypassword.TopActivity");

                startActivity(i);
            }
        });
    }
}
