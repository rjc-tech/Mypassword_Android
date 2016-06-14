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
    }
}
