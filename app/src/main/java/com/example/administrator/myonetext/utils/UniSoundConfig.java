package com.example.administrator.myonetext.utils;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechRecognizer;
/**
 * Created by Administrator on 2017/12/22.
 */

/**
 *
 * @author eBest_HX
 * 云知声配置
 *
 */
public class UniSoundConfig {
    /**
     * 语音听写设置
     */
    public static void setParam(SpeechRecognizer mIat,String mEngineType){
        //清空参数
        mIat.setParameter(SpeechConstant.PARAMS,null);
        //设置听写引擎
        mIat.setParameter(SpeechConstant.ENGINE_TYPE,mEngineType);
        //设置返回结果格式
        mIat.setParameter(SpeechConstant.RESULT_TYPE,"json");
        //设置语音前端点：静音超时时间，用户多长时间不说话则当作超时处理
        mIat.setParameter(SpeechConstant.VAD_BOS,"2000");
        //设置语音后端点：后端点静音检测时间，用户停止说话多长时间内即认为不输入，自动停止录音
        mIat.setParameter(SpeechConstant.VAD_EOS,"1000");
        //设置标点符号，设置为“0”返回结果无标点符号，设置为“1”返回结果有标点
        mIat.setParameter(SpeechConstant.ASR_PTT,"0");

        //2. 设置听写参数，详见《 MSC Reference Manual》 SpeechConstant类
        mIat.setParameter(SpeechConstant. DOMAIN, "iat" );// 短信和日常用语： iat (默认)
        mIat.setParameter(SpeechConstant. LANGUAGE, "zh_cn" );// 设置中文
        mIat.setParameter(SpeechConstant. ACCENT, "mandarin" );// 设置普通话
    }

//    /**
//     * 解析并打印语音输入
//     */
//    public static void printResult(RecognizerResult result, HashMap<String, String> mIatResults, EditText etSearch){
//        String resultString = JSONParser.parseIatResult(result.getResultString());
//        String snString = null;
//        //读取json结果中的sn字段
//        try {
//            JSONObject resultJson = new JSONObject(result.getResultString());
//            snString = resultJson.optString("sn");
//        } catch (JSONException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        mIatResults.put(snString, resultString);
//
//        StringBuffer buffer = new StringBuffer();
//        for (String key : mIatResults.keySet()) {
//            buffer.append(mIatResults.get(key));
//        }
//        etSearch.setText(buffer.toString());
//        etSearch.setSelection(etSearch.length());
//    }
}
