package com.example.administrator.myonetext.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myonetext.R;

/**
 * Created by Administrator on 2017/12/24.
 * 自定义吐司
 * 用户多次点击，自动显示最新吐司内容
 */
public class CustomToast {
    private static Toast mToast;
    private static TextView mTvToast;
    public static void showToast(Context ctx, String content) {
        if (mToast == null) {
            mToast = new Toast(ctx);
            //mToast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);//设置toast显示的位置，这是居中
            mToast.setDuration(Toast.LENGTH_SHORT);//设置toast显示的时长
            View _root = LayoutInflater.from(ctx).inflate(R.layout.toast_custom_common, null);//自定义样式，自定义布局文件
            mTvToast = (TextView) _root.findViewById(R.id.tvCustomToast);
            mToast.setView(_root);//设置自定义的view
        }
        mTvToast.setText(content);//设置文本
        mToast.show();//展示toast
    }
    public static void showToast(Context ctx, int stringId) {
        showToast(ctx, ctx.getString(stringId));
    }
    public static void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
            mTvToast = null;
        }
    }
}
