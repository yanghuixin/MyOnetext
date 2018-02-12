package com.example.administrator.myonetext.bean;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * Created by Administrator on 2018/1/19.
 */

public class GouhuiUser {

    private static GouhuiUser instance;
    private SharedPreferences sp;

    private GouhuiUser() {
    }

    public static GouhuiUser getInstance() {
        if (instance == null) {
            instance = new GouhuiUser();
        }
        return instance;
    }


    /**
     * 保存自动登录的用户信息
     */
    public void saveUserInfo(Context context, String username, String password,String uid,String usafe,String uflag) {
        SharedPreferences sp = context.getSharedPreferences("gouHuiUserInfo", Context.MODE_PRIVATE);//Context.MODE_PRIVATE表示SharePrefences的数据只有自己应用程序能访问。
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("USER_NAME", username);
        editor.putString("PASSWORD", password);
        editor.putString("USERID", uid);
        editor.putString("USAFE", usafe);
        editor.putString("UFLAG", uflag);
        editor.commit();
    }

    /**
     * 保存自动登录的用户信息
     */
    public void saveMyInfo(Context context,  String dlno, String upicurl) {
        SharedPreferences sp = context.getSharedPreferences("gouHuiUserInfo", Context.MODE_PRIVATE);//Context.MODE_PRIVATE表示SharePrefences的数据只有自己应用程序能访问。
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("DLNO", dlno);
        editor.putString("UPICURL", upicurl);
        editor.commit();
    }
    /**
     * 获取用户信息model
     *
     * @param context
     * @param
     * @param
     */
    public UserInfo getUserInfo(Context context) {
        sp = context.getSharedPreferences("gouHuiUserInfo", Context.MODE_PRIVATE);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(sp.getString("USER_NAME", ""));
        userInfo.setPassword(sp.getString("PASSWORD", ""));
        userInfo.setUid(sp.getString("USERID", ""));
        userInfo.setUsafe(sp.getString("USAFE", ""));
        userInfo.setUflag(sp.getString("UFLAG", ""));
        userInfo.setProductNum(sp.getString("PRODUCTNUMBER",""));
        userInfo.setDlno(sp.getString("DLNO",""));
        userInfo.setUpicurl(sp.getString("UPICURL",""));
        return userInfo;
    }

    /**
     * userInfo中是否有数据
     */
    public boolean hasUserInfo(Context context) {
        UserInfo userInfo = getUserInfo(context);
        if (userInfo != null) {
            if ((!TextUtils.isEmpty(userInfo.getUserName())) && (!TextUtils.isEmpty(userInfo.getPassword()))&&(!TextUtils.isEmpty(userInfo.getUid()))&&(!TextUtils.isEmpty(userInfo.getUflag()))&&(!TextUtils.isEmpty(userInfo.getUsafe()))) {//有数据
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
    /**
     * 清除SharedPreferences里的数据
     */
    public void clearUserInfo(){
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }
}