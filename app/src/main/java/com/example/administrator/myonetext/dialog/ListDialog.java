package com.example.administrator.myonetext.dialog;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.myonetext.R;


/**
 * Created by Administrator on 2017/12/12.
 */

public class ListDialog extends BaseDialog {

    private String[] items;
    private OnListDialogListener onListDialogListener;
    private Context context;
    private ListView lv_listdialog;

    public ListDialog(Context context, String[] items, OnListDialogListener onListDialogListener) {
        super(context);
        this.context = context;
        this.items =items;
        this.onListDialogListener = onListDialogListener;
    }

    public static void showDialog(Context context, String[] items, OnListDialogListener onListDialogListener){
        ListDialog dialog = new ListDialog(context, items, onListDialogListener);
        dialog.show();
    }

    @Override
    public void initView() {
        setContentView(R.layout.dialog_list);
        lv_listdialog = (ListView) findViewById(R.id.lv_listdialog);
    }

    @Override
    public void initListener() {
        //给lv_listdialog设置条目侦听
        lv_listdialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (onListDialogListener != null){
                    onListDialogListener.onItemClick(parent, view, position, id);
                }
                dismiss();
            }
        });
    }

    @Override
    public void initData() {
        lv_listdialog.setAdapter(new MyAdapter());
    }

    @Override
    public void processClick(View v) {

    }

    public interface OnListDialogListener{
        void onItemClick(AdapterView<?> parent, View view, int position, long id);
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return items.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(context, R.layout.item_listdialog, null);
            TextView tv_item_listdialog = (TextView) view.findViewById(R.id.tv_item_listdialog);
            tv_item_listdialog.setText(items[position]);
            return view;
        }
    }
}
