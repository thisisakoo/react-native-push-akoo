package com.dhqq.huaweipush;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.dhqq.RNPushAkooModule;
import com.dhqq.rnmixpush.MixPushManager;
import com.huawei.android.hms.agent.HMSAgent;
import com.huawei.android.hms.agent.common.handler.ConnectHandler;
import com.huawei.android.hms.agent.push.handler.DeleteTokenHandler;
import com.huawei.android.hms.agent.push.handler.EnableReceiveNotifyMsgHandler;
import com.huawei.android.hms.agent.push.handler.GetTokenHandler;


import static com.dhqq.huaweipush.HuaweiPushRevicer.ACTION_PUSHMSG;
import static com.dhqq.huaweipush.HuaweiPushRevicer.ACTION_GOTCLICK;
import static com.dhqq.huaweipush.HuaweiPushRevicer.ACTION_TOKEN;
import static com.dhqq.huaweipush.HuaweiPushRevicer.ACTION_UPDATEUI;


/**
 * Created by wangheng on 2017/11/22.
 */
public class HuaweiPushManager implements MixPushManager, HuaweiPushRevicer.IPushCallback {
    public static final String TAG = "HuaweiPushManager";


    public static final String NAME = "huaweipush";
    private  String token="";

    @Override
    public void registerPush(Activity activity) {
        Log.i(TAG, "HMS init :");
        Boolean result = HMSAgent.init(activity);
        Log.i(TAG, "HMS init :" + result);
        if (result) {
            HMSAgent.connect(activity, new ConnectHandler() {
                @Override
                public void onConnect(int rst) {
                    Log.i(TAG, "HMS connect end:" + rst);
                    HuaweiPushManager.this.getToken();
                }
            });
            HuaweiPushRevicer.registerPushCallback(this);

        } else {
            Log.i(TAG, "HMS init fail");
        }
    }

    @Override
    public void unRegisterPush(Activity activity) {
        HMSAgent.destroy();
    }

    /**
     * 获取token
     */
    private void getToken() {
        Log.i(TAG, "get token: begin");
        HMSAgent.Push.getToken(new GetTokenHandler() {
            @Override
            public void onResult(int rtnCode) {
                Log.i(TAG, "get token: end" + rtnCode);
                setReceiveNotifyMsg(true);

            }
        });
    }

    @Override
    public void setAlias(Context context, String alias) {

    }

    @Override
    public void unsetAlias(Context context, String alias) {

    }

    @Override
    public void setTags(Context context, String... tags) {

    }

    @Override
    public void unsetTags(Context context, String... tags) {

    }

    @Override
    public String getClientId(Context context) {
        return "";
    }

    @Override
    public String getName() {
        return NAME;
    }

//    public static String refreshToken() throws IOException, JSONException {
//        String tokenUrl = "https://login.vmall.com/oauth2/token";
//        String msgBody = MessageFormat.format("grant_type=client_credentials&client_secret={0}&client_id={1}", URLEncoder.encode(appSecret, "UTF-8"), appId);
//        String response = HttpHelpers.sendPost(tokenUrl, msgBody);
//        JSONObject obj = new JSONObject(response);
//        ;
//        String accessToken = obj.getString("access_token");
//        return accessToken;
//    }

//    //发送Push消息
//    public static String sendPushMessage(String deviceToken) throws IOException, JSONException {
//        String accessToken = refreshToken();
//        String apiUrl = "https://api.push.hicloud.com/pushsend.do";
//        JSONArray deviceTokens = new JSONArray();//目标设备Token
//        deviceTokens.put(deviceToken);
//        //deviceTokens.put("0866936023001827300001100100CN01");//测试设备
//        //deviceTokens.put("0A0000044270BAD0300001100100CN01");//测试设备
//
//        JSONObject body = new JSONObject();//仅通知栏消息需要设置标题和内容，透传消息key和value为用户自定义
//        body.put("title", "测试");//消息标题
//        body.put("content", "测试华为通知");//消息内容体
//
//        JSONObject param = new JSONObject();
//        param.put("appPkgName", "com.mixpush");//定义需要打开的appPkgName
//
//        JSONObject action = new JSONObject();
//        action.put("type", 3);//类型3为打开APP，其他行为请参考接口文档设置
//        action.put("param", param);//消息点击动作参数
//
//        JSONObject msg = new JSONObject();
//        msg.put("type", 3);//3: 通知栏消息，异步透传消息请根据接口文档设置
//        msg.put("action", action);//消息点击动作
//        msg.put("body", body);//通知栏消息body内容
//
//        JSONObject ext = new JSONObject();//扩展信息，含BI消息统计，特定展示风格，消息折叠。
//        ext.put("biTag", "Trump");//设置消息标签，如果带了这个标签，会在回执中推送给CP用于检测某种类型消息的到达率和状态
//        ext.put("icon", "http://pic.qiantucdn.com/58pic/12/38/18/13758PIC4GV.jpg");//自定义推送消息在通知栏的图标,value为一个公网可以访问的URL
//        JSONObject hps = new JSONObject();//华为PUSH消息总结构体
//        hps.put("msg", msg);
//        hps.put("ext", ext);
//
//        JSONObject payload = new JSONObject();
//        payload.put("hps", hps);
//
//        String postBody = MessageFormat.format(
//                "access_token={0}&nsp_svc={1}&nsp_ts={2}&device_token_list={3}&payload={4}",
//                URLEncoder.encode(accessToken, "UTF-8"),
//                URLEncoder.encode("openpush.message.api.send", "UTF-8"),
//                URLEncoder.encode(String.valueOf(System.currentTimeMillis() / 1000), "UTF-8"),
//                URLEncoder.encode(deviceTokens.toString(), "UTF-8"),
//                URLEncoder.encode(payload.toString(), "UTF-8"));
//
//        String postUrl = apiUrl + "?nsp_ctx=" + URLEncoder.encode("{\"ver\":\"1\", \"appId\":\"" + appId + "\"}", "UTF-8");
//        String result = HttpHelpers.sendPost(postUrl, postBody);
//        Log.i("HuaWeiPush", "PushResult:" + result);
//        return result;
//    }



    /**
     * 删除token | delete push token
     */
    private void deleteToken(String token) {
        Log.i(TAG, "deleteToken:begin");
        HMSAgent.Push.deleteToken(token, new DeleteTokenHandler() {
            @Override
            public void onResult(int rst) {
                Log.i(TAG, "deleteToken:end code=" + rst);
            }
        });
    }

    /**
     * 设置接收通知消息 | Set up receive notification messages
     *
     * @param enable 是否开启 | enabled or not
     */
    private void setReceiveNotifyMsg(boolean enable) {
        Log.i(TAG, "enableReceiveNotifyMsg:begin");
        HMSAgent.Push.enableReceiveNotifyMsg(enable, new EnableReceiveNotifyMsgHandler() {
            @Override
            public void onResult(int rst) {
                Log.i(TAG, "setReceiveNotifyMsg:end code=" + rst);
//                RNPushAkooModule.sendEvent(RNPushAkooModule.EVENT_RECEIVE_CLIENTID, stoken);
            }
        });
    }

    //RNPushAkooModule.sendEvent(RNPushAkooModule.EVENT_RECEIVE_CLIENTID, stoken);
    @Override
    public void onReceive(Intent intent) {
        Log.i(TAG, "got message");
        if (intent != null) {
            String action = intent.getAction();
            Bundle b = intent.getExtras();
            if (b != null && ACTION_TOKEN.equals(action)) {
                token = b.getString(ACTION_TOKEN);
                RNPushAkooModule.sendEvent(RNPushAkooModule.EVENT_RECEIVE_CLIENTID,token);
            } else if (b != null && ACTION_UPDATEUI.equals(action)) {
                String log = b.getString("log");
                Log.i(TAG,log);
            }
            else if (b != null && ACTION_GOTCLICK.equals(action)) {
                String log = b.getString("pushmsg");
                Log.i(TAG,log);
                RNPushAkooModule.sendEvent(RNPushAkooModule.EVENT_RECEIVE_REMOTE_NOTIFICATION,log);
            }
            else if (b != null && ACTION_PUSHMSG.equals(action)) {
                String log = b.getString("pushmsg");
                Log.i(TAG,log);
                RNPushAkooModule.sendEvent(RNPushAkooModule.EVENT_RECEIVE_REMOTE_NOTIFICATION,log);
            }

        }
    }
}
