package jp.co.rjc.mypassword.ui.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import jp.co.rjc.mypassword.R;
import jp.co.rjc.mypassword.common.Globals;
import jp.co.rjc.mypassword.db.DatabaseHelper;
import jp.co.rjc.mypassword.provider.Sites;
import jp.co.rjc.mypassword.ui.activity.SiteDetailActivity;
import jp.co.rjc.mypassword.ui.adapter.SiteListAdapter;

/**
 * 一覧画面表示のフラグメントです。
 */
public class SiteListFragment extends ListFragment {

    private int mAccountId;
    private DatabaseHelper mDb;

    private TextView mEmptyMessage;
    private ListView mSiteListView;
    private SiteListAdapter mSiteListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAccountId = getActivity().getIntent().getIntExtra(Globals.INTENT_KEY_ACCOUNT_ID, -1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_site_list, container, false);
        mEmptyMessage = (TextView) view.findViewById(R.id.empty_msg);

        // 新規作成ボタンを初期化
        view.findViewById(R.id.create_item_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goSiteEditByCreate();
            }
        });
        mSiteListView = (ListView) view.findViewById(android.R.id.list);
        mDb = new DatabaseHelper(getActivity().getApplicationContext());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateSiteList();
    }

    /**
     * リストカセット押下時にサイト詳細に遷移します.
     *
     * @param l
     * @param v
     * @param position
     * @param id
     */
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        final Cursor c = (Cursor) mSiteListAdapter.getItem(position);
        Intent intent = new Intent(getActivity(), SiteDetailActivity.class);
        intent.putExtra(Globals.INTENT_KEY_ACCOUNT_ID, mAccountId);
        intent.putExtra(Globals.INTENT_KEY_SITE_ID, Integer.valueOf(c.getInt(c.getColumnIndex(Sites.ID))));
        intent.putExtra(Globals.INTENT_KEY_EDIT_STATUS, SiteDetailActivity.SITE_EDIT_STATUS_READ);
        startActivity(intent);
    }

    /**
     * サイトリストを更新します.
     */
    private void updateSiteList() {
        mSiteListView.setAdapter(null);
        mSiteListAdapter = new SiteListAdapter(getActivity(), DatabaseHelper.select(mDb.getReadableDatabase(), mAccountId));

        if (mSiteListAdapter.getCount() > 0) {
            mSiteListView.setAdapter(mSiteListAdapter);
            mEmptyMessage.findViewById(R.id.empty_msg).setVisibility(View.GONE);
        } else {
            mEmptyMessage.findViewById(R.id.empty_msg).setVisibility(View.VISIBLE);
        }
        mSiteListAdapter.notifyDataSetChanged();
    }

    /**
     * サイトの新規作成画面に遷移します.
     */
    private void goSiteEditByCreate() {
        Intent intent = new Intent(getActivity(), SiteDetailActivity.class);
        intent.putExtra(Globals.INTENT_KEY_ACCOUNT_ID, mAccountId);
        intent.putExtra(Globals.INTENT_KEY_EDIT_STATUS, SiteDetailActivity.SITE_EDIT_STATUS_UNREGISTERED);
        startActivity(intent);
    }
}