package jp.co.rjc.mypassword;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.FragmentActivity;
/**
 * Created by m_yoshida on 2016/07/13.
 */

public class MakeEntryDetailActivity extends FragmentActivity  {

    // TODO 修正
    // メンバー変数は頭文字m
    // static変数は頭文字s

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make_entry_detail);

        findViewById(R.id.btnCreate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {





//                Intent i = new Intent();
//                i.setClassName("jp.co.rjc.mypassword", "jp.co.rjc.mypassword.TopActivity");

//                startActivity(i);
            }
        });
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.make_entry_detail, container, false);
    }

}
