package jp.co.rjc.mypassword.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import jp.co.rjc.mypassword.R;
import jp.co.rjc.mypassword.common.Globals;

public class SiteDetailActivity extends AppCompatActivity {

	/**
	 * 未登録(新規登録)ステータス
	 */
	public static final int SITE_EDIT_STATUS_UNREGISTERED = 0;

	/**
	 * 編集ステータス
	 */
	public static final int SITE_EDIT_STATUS_EDIT = 1;

	/**
	 * 閲覧ステータス
	 */
	public static final int SITE_EDIT_STATUS_READ = 2;

	public MenuItem mEditSiteBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_site_detail);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// サイト閲覧モード時のみ編集ボタンを表示
		if (SiteDetailActivity.SITE_EDIT_STATUS_READ == getIntent().getIntExtra(Globals.INTENT_KEY_EDIT_STATUS, SiteDetailActivity.SITE_EDIT_STATUS_READ)) {
			mEditSiteBtn = menu.add("Nomal Item");
			mEditSiteBtn.setIcon(android.R.drawable.ic_menu_edit);
			mEditSiteBtn.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
			mEditSiteBtn.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
				@Override
				public boolean onMenuItemClick(MenuItem item) {
					Intent intent = new Intent(SiteDetailActivity.this, SiteDetailActivity.class);
					intent.putExtra(Globals.INTENT_KEY_ACCOUNT_ID, getIntent().getIntExtra(Globals.INTENT_KEY_ACCOUNT_ID, -1));
					intent.putExtra(Globals.INTENT_KEY_SITE_ID, getIntent().getIntExtra(Globals.INTENT_KEY_SITE_ID, -1));
					intent.putExtra(Globals.INTENT_KEY_EDIT_STATUS, SiteDetailActivity.SITE_EDIT_STATUS_EDIT);
					startActivity(intent);
					finish();
					return true;
				}
			});
		}
		return super.onCreateOptionsMenu(menu);
	}
}
