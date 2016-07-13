package jp.co.rjc.mypassword;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * リスト作成用のアダプターです。
 */

public class ListAdapter extends ArrayAdapter<String> {
    LayoutInflater mInflater;

    static final class ViewHolder {
        TextView mCassetteName;
    }

    public ListAdapter(Context context) {
        super(context, 0);
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
            holder.mCassetteName = (TextView) view.findViewById(R.id.site_title);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }

        holder.mCassetteName.setText(getItem(position));
        return view;
    }
}

