package jp.co.rjc.mypassword.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import jp.co.rjc.mypassword.R;

/**
 * OSSライセンス画面を提供します.
 */
public class LicensesActivity extends AppCompatActivity {

	protected Button mOSSBtn;
	protected Button mTopBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_licenses);

		mTopBtn = (Button) findViewById(R.id.btn_top);
		mTopBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(LicensesActivity.this, InputPinActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
	}
}