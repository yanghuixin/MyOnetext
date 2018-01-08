package com.bunny.view;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bunny.timerlib.R;

import java.util.ArrayList;


/**
 * Created by bayin on 2016/1/29.
 */
public class TimeCountView extends LinearLayout {

    private static final int SECOND_BITS = 0;
    private static final int SECOND_TEN = 1;
    private static final int MIN_BITS = 2;
    private static final int MIN_TEN = 3;
    private static final int HOUR_BITS = 4;
    private static final int HOUR_TEN = 5;
    private static final int _END = 999;//倒计时结束
    private View mView;
    private TextView mhour_ten;
    private TextView mhour_bits;
    private TextView mmin_ten;
    private TextView mmin_bits;
    private TextView msecond_ten;
    private TextView msecond_bits;
    private short hour_ten, hour_bits, min_ten, min_bits, second_ten, second_bits;

    private boolean isInit = false;
    private boolean TIMEISOVER = false;
    private OnTimeOverListener mListener;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SECOND_BITS:
                    msecond_bits.setText(second_bits + "");
                    break;
                case SECOND_TEN:
                    msecond_ten.setText(second_ten + "");
                    break;
                case MIN_BITS:
                    mmin_bits.setText(min_bits + "");
                    break;
                case MIN_TEN:
                    mmin_ten.setText(min_ten + "");
                    break;
                case HOUR_BITS:
                    mhour_bits.setText(hour_bits + "");
                    break;
                case HOUR_TEN:
                    mhour_ten.setText(hour_ten + "");
                    break;
                case _END:
                    TIMEISOVER = true;
                    if (mListener != null) {
                        mListener.onTimeOver();
                    }
                    break;
            }
        }
    };

    public TimeCountView(Context context) {
        this(context, null);
    }

    public TimeCountView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeCountView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        LayoutInflater inflater = LayoutInflater.from(context);
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.TimeCountView);
        int textColor = mTypedArray.getColor(R.styleable.TimeCountView_timerTextColor, Color.parseColor("#ffffff"));
        float textSize = mTypedArray.getDimension(R.styleable.TimeCountView_timerTextSize, -1);
        int background = mTypedArray.getResourceId(R.styleable.TimeCountView_timerBackground, -1);

        ArrayList<TextView> textList = new ArrayList<>();
        mView = inflater.inflate(R.layout.timecount_layout, this, true);
        mhour_ten = (TextView) mView.findViewById(R.id.hour_ten);
        textList.add(mhour_ten);
        mhour_bits = (TextView) mView.findViewById(R.id.hour_bits);
        textList.add(mhour_bits);
        mmin_ten = (TextView) mView.findViewById(R.id.min_ten);
        textList.add(mmin_ten);
        mmin_bits = (TextView) mView.findViewById(R.id.min_bits);
        textList.add(mmin_bits);
        msecond_ten = (TextView) mView.findViewById(R.id.second_ten);
        textList.add(msecond_ten);
        msecond_bits = (TextView) mView.findViewById(R.id.second_bits);
        textList.add(msecond_bits);
        TextView splite_1 = (TextView) mView.findViewById(R.id.tv_splite_1);
        TextView splite_2 = (TextView) mView.findViewById(R.id.tv_splite_2);
        if (textSize>0){
            splite_1.setTextSize(textSize);
            splite_2.setTextSize(textSize);
        }
        for (TextView textview :
                textList) {
            textview.setTextColor(textColor);
            if (textSize>0) textview.setTextSize(textSize);
            if (background>0) textview.setBackgroundResource(background);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    public void initTime(Context context, String s) {
        setTypeFace(context);

//        mhour_ten.setText(chars[0] + "");
//        mhour_bits.setText(chars[1] + "");
//        mmin_ten.setText(chars[2] + "");
//        mmin_bits.setText(chars[3] + "");
//        msecond_ten.setText(chars[4] + "");
//        msecond_bits.setText(chars[5] + "");
        setTime(s);
        if (isInit) return;
        new Thread() {
            @Override
            public void run() {
                while (!TIMEISOVER) {
                    second_bits--;
                    handler.sendEmptyMessage(SECOND_BITS);
                    if (second_bits <= 0) {
                        if (second_ten == 0 && min_bits == 0 && min_ten == 0 && hour_bits == 0 && hour_ten == 0) {
                            handler.sendEmptyMessage(_END);
                            return;
                        }
                        //秒小于0，十位的秒减一，秒复位成9
                        second_bits = 9;
                        if (second_ten > 0) second_ten--;

                        handler.sendEmptyMessage(SECOND_TEN);
                        handler.sendEmptyMessage(SECOND_BITS);
                    }

                    if (second_ten < 0) {
                        //十位秒小于0，个位的分减一，十位秒复位成5
                        second_ten = 5;

                        if (min_bits > 0) min_bits--;

                        handler.sendEmptyMessage(MIN_BITS);
                        handler.sendEmptyMessage(SECOND_TEN);
                    }
                    if (min_bits < 0) {
                        //分的个位小于0，分的十位减1，个位复位成9
                        min_bits = 9;

                        if (min_ten > 0) min_ten--;

                        handler.sendEmptyMessage(MIN_TEN);
                        handler.sendEmptyMessage(MIN_BITS);
                    }
                    if (min_ten < 0) {
                        //分的十位小于0，时的个位减一，分的十位复位成5
                        min_ten = 5;

                        if (hour_bits > 0) {
                            //如果时的个位还大于0，就--，否则不变
                            hour_bits--;
                        }

                        handler.sendEmptyMessage(MIN_TEN);
                        handler.sendEmptyMessage(HOUR_BITS);
                    }
                    if (hour_bits < 0) {
                        //小时的个位小于0，时的十位减一，个位复位成9
                        hour_bits = 9;

                        if (hour_ten > 0) {
                            //如果十位还大于零，就--，否则不变
                            hour_ten--;
                        }

                        handler.sendEmptyMessage(HOUR_BITS);
                        handler.sendEmptyMessage(HOUR_TEN);
                    }

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        //第一次初始化完毕，将标志改为true；防止刷新的时候线程重复开启
        isInit = true;
    }

    private void setTime(String time) {
        char[] chars = time.toCharArray();
        hour_ten = char2int(chars[0]);
        hour_bits = char2int(chars[1]);
        min_ten = char2int(chars[2]);
        min_bits = char2int(chars[3]);
        second_ten = char2int(chars[4]);
        second_bits = char2int(chars[5]);

        mhour_ten.setText(hour_ten + "");
        mhour_bits.setText(hour_bits + "");
        mmin_ten.setText(min_ten + "");
        mmin_bits.setText(min_bits + "");
        msecond_ten.setText(second_ten + "");
        msecond_bits.setText(second_bits + "");
    }

    /**
     * 设置字体
     */
    private void setTypeFace(Context context) {
        AssetManager mgr = context.getAssets();
        Typeface tf = Typeface.createFromAsset(mgr, "fonts/Roboto_Bold.ttf");
        msecond_bits.setTypeface(tf);
        msecond_ten.setTypeface(tf);
        mmin_bits.setTypeface(tf);
        mmin_ten.setTypeface(tf);
        mhour_bits.setTypeface(tf);
        mhour_ten.setTypeface(tf);
    }

    /**
     * 字符转成short
     *
     * @param c
     * @return short
     */
    public short char2int(char c) {
        return Short.parseShort(String.valueOf(c));
    }

    public void setOnTimeOverListener(OnTimeOverListener listener) {
        this.mListener = listener;
    }

    public interface OnTimeOverListener {
        void onTimeOver();
    }

}
