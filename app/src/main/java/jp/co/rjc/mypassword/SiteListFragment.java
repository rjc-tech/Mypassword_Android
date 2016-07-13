package jp.co.rjc.mypassword;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * 一覧画面表示のフラグメントです。
 */
public class SiteListFragment extends ListFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);

        // ボタンを取得して、ClickListenerをセット
        Button btn = (Button)v.findViewById(R.id.create_item_button);
        btn.setOnClickListener(new ButtonAction());

        // アダプターの作成
        ListAdapter listAdapter = new ListAdapter(getActivity());

        // アダプターを使用したデータの入れ込み。後々DBから持ってきます↓
        for (int i = 0; i < 40; i++) {
            listAdapter.add("aaa" + i);
        }
        setListAdapter(listAdapter);
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    /**
     * ボタン押下時の動作をまとめたクラスです。
     */
    class ButtonAction implements View.OnClickListener {

        @Override
        public void onClick(View v){
            //処理
        }
    }

}
