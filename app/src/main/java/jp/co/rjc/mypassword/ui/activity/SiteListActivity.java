package jp.co.rjc.mypassword.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import jp.co.rjc.mypassword.R;
import jp.co.rjc.mypassword.common.Globals;

/**
 * サイトリスト画面を提供します.
 */
public class SiteListActivity extends AppCompatActivity {

    /**
     * PIN変更メニューボタン.
     */
    public MenuItem mEditPinBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mEditPinBtn = menu.add(getResources().getString(R.string.btn_label_edit_pin));
        mEditPinBtn.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        mEditPinBtn.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(SiteListActivity.this, InputPinActivity.class);
                intent.putExtra(Globals.INTENT_KEY_INPUT_STATUS, InputPinActivity.INPUT_PIN_STATUS_EDIT);
                startActivity(intent);
//				finish();
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
