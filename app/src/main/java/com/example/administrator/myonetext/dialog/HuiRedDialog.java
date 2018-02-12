package com.example.administrator.myonetext.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.administrator.myonetext.R;
import com.example.administrator.myonetext.fragment.HomeFragment;

/**
 * Created by Administrator on 2018/1/10.
 */

public class HuiRedDialog extends Dialog {
    /**
     * 上下文对象 *
     */
    Activity context;
    private View.OnClickListener mClickListener;
    private ImageView open;

    public HuiRedDialog(Activity context) {
        super(context);
        this.context = context;
    }

    public HuiRedDialog(Activity context, int theme, View.OnClickListener clickListener) {
        super(context, theme);
        this.context = context;
        this.mClickListener = clickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 指定布局
        this.setContentView(R.layout.hui_red_dialog);
        open = (ImageView) findViewById(R.id.open);
        /*
         * 获取圣诞框的窗口对象及参数对象以修改对话框的布局设置, 可以直接调用getWindow(),表示获得这个Activity的Window
         * 对象,这样这可以以同样的方式改变这个Activity的属性.
         */
        Window dialogWindow = this.getWindow();
        WindowManager m = context.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.8); // 高度设置为屏幕的0.6
        p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.8
        dialogWindow.setAttributes(p);
        // 为按钮绑定点击事件监听器
        open.setOnClickListener(mClickListener);

        this.setCancelable(true);
    }
}
