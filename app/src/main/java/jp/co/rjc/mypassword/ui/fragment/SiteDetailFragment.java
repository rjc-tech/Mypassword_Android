package jp.co.rjc.mypassword.ui.fragment;

import android.app.Fragment;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import jp.co.rjc.mypassword.R;
import jp.co.rjc.mypassword.common.Globals;
import jp.co.rjc.mypassword.db.DatabaseHelper;
import jp.co.rjc.mypassword.provider.SiteInfos;
import jp.co.rjc.mypassword.ui.activity.SiteDetailActivity;
import jp.co.rjc.mypassword.ui.activity.SiteListActivity;
import jp.co.rjc.mypassword.util.ActivityUtils;

import static android.content.Context.CLIPBOARD_SERVICE;

public class SiteDetailFragment extends Fragment {

    private int mAccountId;
    private int mEditStatus;
    private int mSiteId;
    DatabaseHelper mDb;

    private EditText mEditSiteName;
    private EditText mEditLoginId;
    private EditText mEditLoginPass;
    private EditText mEditUrl;
    private EditText mEditNotes;
    private Button mEditRevealPassBtn;
    private Button mEditCanselBtn;
    private Button mEditCompleteBtn;

    private TextView mSiteName;
    private TextView mLoginId;
    private TextView mLoginPass;
    private TextView mUrl;
    private TextView mNotes;
    private ImageButton mCopyIdBtn;
    private Button mRevealPassBtn;
    private ImageButton mCopyPassBtn;
    private ImageButton mBrowseBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getActivity().getIntent();
        mAccountId = getActivity().getIntent().getIntExtra(Globals.INTENT_KEY_ACCOUNT_ID, -1);
        mEditStatus = intent.getIntExtra(Globals.INTENT_KEY_EDIT_STATUS, SiteDetailActivity.SITE_EDIT_STATUS_READ);
        mSiteId = intent.getIntExtra(Globals.INTENT_KEY_SITE_ID, -1);
        mDb = new DatabaseHelper(getActivity().getApplicationContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (SiteDetailActivity.SITE_EDIT_STATUS_UNREGISTERED == mEditStatus
                || SiteDetailActivity.SITE_EDIT_STATUS_EDIT == mEditStatus) {
            View view = inflater.inflate(R.layout.fragment_edit_site, container, false);
            mEditSiteName = (EditText) view.findViewById(R.id.edit_site_name);
            mEditLoginId = (EditText) view.findViewById(R.id.edit_login_id);
            mEditLoginPass = (EditText) view.findViewById(R.id.edit_password);
            mEditRevealPassBtn = (Button) view.findViewById(R.id.btn_reveal_password);
            mEditUrl = (EditText) view.findViewById(R.id.edit_url);
            mEditNotes = (EditText) view.findViewById(R.id.edit_notes);
            mEditCanselBtn = (Button) view.findViewById(R.id.btn_edit_site_cancel);
            mEditCompleteBtn = (Button) view.findViewById(R.id.btn_edit_site_complete);
            initEditLayout();
            return view;
        } else if (SiteDetailActivity.SITE_EDIT_STATUS_READ == mEditStatus) {
            View view = inflater.inflate(R.layout.fragment_site_detail, container, false);
            mSiteName = (TextView) view.findViewById(R.id.edit_site_name);
            mLoginId = (TextView) view.findViewById(R.id.edit_login_id);
            mLoginPass = (TextView) view.findViewById(R.id.edit_password);
            mRevealPassBtn = (Button) view.findViewById(R.id.btn_reveal_password);
            mUrl = (TextView) view.findViewById(R.id.edit_url);
            mNotes = (TextView) view.findViewById(R.id.edit_notes);

            mCopyIdBtn = (ImageButton) view.findViewById(R.id.btn_copy_login_id);
            mCopyPassBtn = (ImageButton) view.findViewById(R.id.btn_copy_password);
            mBrowseBtn = (ImageButton) view.findViewById(R.id.btn_browse);
            initReadLayout();
            return view;
        } else {
            getActivity().finish();
            return null;
        }
    }

    //////////////////////////////////////////////////////////////////

    /**
     * 編集用サイト情報のレイアウトを初期化します.
     */
    private void initEditLayout() {

        if (SiteDetailActivity.SITE_EDIT_STATUS_UNREGISTERED == mEditStatus) {
            mEditCompleteBtn.setText(getResources().getString(R.string.btn_label_create));
            mEditCompleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 入力チェック
                    if (!validation(mEditSiteName.getText().toString(), getResources().getString(R.string.label_site_name))) {
                        return;
                    }
                    int result = DatabaseHelper.insert(
                            mDb.getWritableDatabase(),
                            mAccountId,
                            mEditSiteName.getText().toString(),
                            mEditLoginId.getText().toString(),
                            mEditLoginPass.getText().toString(),
                            mEditUrl.getText().toString(),
                            mEditNotes.getText().toString()
                    );
                    if (result > 0) {
                        ActivityUtils.getInstance(getActivity()).displayCenterToastShort(getResources().getString(R.string.toast_success_create));
                    }
                    goSiteList();
                }
            });

        } else if (SiteDetailActivity.SITE_EDIT_STATUS_EDIT == mEditStatus) {
            Cursor c = DatabaseHelper.selectOne(mDb.getReadableDatabase(), mSiteId);
            if (c.moveToFirst()) {
                mEditSiteName.setText(c.getString(c.getColumnIndex(SiteInfos.SITE_NAME)));
                mEditLoginId.setText(c.getString(c.getColumnIndex(SiteInfos.LOGIN_ID)));
                mEditLoginPass.setText(c.getString(c.getColumnIndex(SiteInfos.LOGIN_PASSWORD)));

                mEditUrl.setText(c.getString(c.getColumnIndex(SiteInfos.SITE_URL)));
                mEditNotes.setText(c.getString(c.getColumnIndex(SiteInfos.NOTES)));
            }
            mEditCompleteBtn.setText(getResources().getString(R.string.btn_label_update));
            mEditCompleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 入力チェック
                    if (!validation(mEditSiteName.getText().toString(), getResources().getString(R.string.label_site_name))) {
                        return;
                    }
                    int result = DatabaseHelper.update(
                            mDb.getWritableDatabase(),
                            mSiteId,
                            mEditSiteName.getText().toString(),
                            mEditLoginId.getText().toString(),
                            mEditLoginPass.getText().toString(),
                            mEditUrl.getText().toString(),
                            mEditNotes.getText().toString()
                    );
                    if (result > 0) {
                        ActivityUtils.getInstance(getActivity()).displayCenterToastShort(getResources().getString(R.string.toast_success_update));
                    }
                    goSiteList();
                }
            });
        }
        // パスワード表示切り替えボタンを初期化
        mEditRevealPassBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    mEditLoginPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    mEditLoginPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                return false;
            }
        });

        // キャンセルは現在のアクティビティを破棄
        mEditCanselBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SiteDetailActivity.SITE_EDIT_STATUS_EDIT == mEditStatus) {
                    Intent intent = new Intent(getActivity(), SiteDetailActivity.class);
                    intent.putExtra(Globals.INTENT_KEY_ACCOUNT_ID, mAccountId);
                    intent.putExtra(Globals.INTENT_KEY_SITE_ID, mSiteId);
                    intent.putExtra(Globals.INTENT_KEY_EDIT_STATUS, SiteDetailActivity.SITE_EDIT_STATUS_READ);
                    startActivity(intent);
                    getActivity().finish();
                } else {
                    getActivity().finish();
                }
            }
        });
    }

    /**
     * 入力値チェックを行います。
     */
    private boolean validation(String item, String message) {
        if (trimSpace(item).isEmpty()) {
            new AlertDialog.Builder(getActivity())
                    .setMessage(message + "を入力してください")
                    .setPositiveButton(R.string.dialog_label_ok, null)
                    .show();
            return false;
        }
        return true;
    }

    /**
     * 全角・半角スペースのトリムを行います。
     */
    public String trimSpace(String str) {
        if (str == null) {
            return null;
        }

        char[] value = str.toCharArray();
        int len = value.length;
        int i = 0;
        char[] valList = value;

        while ((i < len) && (valList[i] <= ' ' || valList[i] == '　')) {
            i++;
        }
        while ((i < len) && (valList[len - 1] <= ' ' || valList[len - 1] == '　')) {
            len--;
        }
        return ((i > 0) || (len < value.length)) ? str.substring(i, len) : str;
    }

    /**
     * 閲覧用サイト情報のレイアウトを初期化します.
     */
    private void initReadLayout() {

        Cursor c = DatabaseHelper.selectOne(mDb.getReadableDatabase(), mSiteId);
        if (c.moveToFirst()) {
            mSiteName.setText(c.getString(c.getColumnIndex(SiteInfos.SITE_NAME)));
            mLoginId.setText(c.getString(c.getColumnIndex(SiteInfos.LOGIN_ID)));
            mLoginPass.setText(c.getString(c.getColumnIndex(SiteInfos.LOGIN_PASSWORD)));
            mUrl.setText(c.getString(c.getColumnIndex(SiteInfos.SITE_URL)));
            mNotes.setText(c.getString(c.getColumnIndex(SiteInfos.NOTES)));

            // ログインIDコピーボタンを初期化

            mCopyIdBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipData cd = new ClipData(new ClipDescription("text_data", new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN}), new ClipData.Item(mLoginId.getText()));
                    ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);
                    cm.setPrimaryClip(cd);
                    ActivityUtils.getInstance(getActivity()).displayCenterToastShort(getResources().getString(R.string.toast_copy_clipboard_id));
                }
            });

            // ログインパスワード表示切り替えボタンを初期化
            mRevealPassBtn.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        mLoginPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        mLoginPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    }
                    return false;
                }
            });

            // ログインパスワードコピーボタンを初期化
            mCopyPassBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipData cd = new ClipData(new ClipDescription("text_data", new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN}), new ClipData.Item(mLoginPass.getText()));
                    ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);
                    cm.setPrimaryClip(cd);
                    ActivityUtils.getInstance(getActivity()).displayCenterToastShort(getResources().getString(R.string.toast_copy_clipboard_pass));
                }
            });

            // ブラウザ連携ボタンを初期化
            mBrowseBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // ページ遷移時に最終アクセス日時を更新
                    DatabaseHelper.updateLastAccessDatetime(mDb.getWritableDatabase(), mSiteId);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mUrl.getText().toString()));
                    startActivity(intent);
                }
            });
        } else {
            goSiteList();
        }
    }

    /**
     * サイトリスト画面へ遷移します.
     */
    private void goSiteList() {
        Intent intent = new Intent(getActivity(), SiteListActivity.class);
        intent.putExtra(Globals.INTENT_KEY_ACCOUNT_ID, mAccountId);
        startActivity(intent);
        getActivity().finish();
    }
}