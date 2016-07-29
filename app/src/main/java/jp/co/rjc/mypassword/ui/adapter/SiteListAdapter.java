package jp.co.rjc.mypassword.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import jp.co.rjc.mypassword.R;
import jp.co.rjc.mypassword.provider.Sites;

/**
 * リスト作成用のアダプターです。
 */

public class SiteListAdapter extends CursorAdapter {
	LayoutInflater mInflater;

	static final class ViewHolder {
		TextView id;
		TextView mCassetteName;
	}

	public SiteListAdapter(final Context context, final Cursor cursor) {
		super(context, cursor, false);
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		final View view;
		final ViewHolder holder;

		// ビューの中身がnullだったらビューホルダーに保持していた値を使用する
		if (convertView == null) {
			view = mInflater.inflate(R.layout.fragment_list_item, null);
			holder = new ViewHolder();
			holder.id = (TextView) view.findViewById(R.id.site_id);
			holder.mCassetteName = (TextView) view.findViewById(R.id.edit_site_name);
			view.setTag(holder);
		} else {
			view = convertView;
			holder = (ViewHolder) view.getTag();
		}
		final Cursor c = getCursor();
		if (c.moveToPosition(position)) {
			holder.id.setText(c.getString(c.getColumnIndex(Sites.ID)));
			holder.mCassetteName.setText(c.getString(c.getColumnIndex(Sites.SITE_NAME)));
		}
		return view;
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		return null;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
	}
}

