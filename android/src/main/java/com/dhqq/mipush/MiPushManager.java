/**
 * Created by wangheng on 2017/11/22.
 */
package com.dhqq.mipush;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import com.dhqq.rnmixpush.MixPushManager;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.List;

public class MiPushManager implements MixPushManager {
    public static final String NAME = "mipush";
    private String appId;
    private String appKey;
    private  Activity mActivity;

    public MiPushManager(String appId, String appKey) {
        this.appId = appId;
        this.appKey = appKey;
    }

//    @Override
//    public void registerPush(Context context) {
//        MiPushClient.registerPush(context.getApplicationContext(), appId, appKey);
//    }
//
//    @Override
//    public void unRegisterPush(Context context) {
//        unsetAlias(context, null);
//        MiPushClient.unregisterPush(context.getApplicationContext());
//    }

    @Override
    public void registerPush(Activity mactivity) {
        mActivity = mactivity;
        MiPushClient.registerPush(mactivity.getApplicationContext(), appId, appKey);
    }
    public void reRegisterPush() {
        MiPushClient.registerPush(mActivity.getApplicationContext(), appId, appKey);
    }
//    private boolean shouldInit() {
//        ActivityManager am = ((ActivityManager) mActivity.getSystemService(Context.ACTIVITY_SERVICE));
//        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
//        String mainProcessName = mActivity.getPackageName();
//        int myPid = Process.myPid();
//        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
//            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
//                return true;
//            }
//        }
//        return false;
//    }

    @Override
    public void unRegisterPush(Activity mactivity) {
        unsetAlias(mactivity, null);
        MiPushClient.unregisterPush(mactivity.getApplicationContext());
    }

    @Override
    public void setAlias(Context context, String alias) {
        if (!MiPushClient.getAllAlias(context).contains(alias)) {
            MiPushClient.setAlias(context, alias, null);
        }
    }

    @Override
    public void unsetAlias(Context context, String alias) {
        List<String> allAlias = MiPushClient.getAllAlias(context);
        for (int i = 0; i < allAlias.size(); i++) {
            MiPushClient.unsetAlias(context, allAlias.get(i), null);
        }
    }

    @Override
    public void setTags(Context context, String... tags) {
        for (String tag : tags){
            MiPushClient.subscribe(context, tag, null);
        }

    }

    @Override
    public void unsetTags(Context context, String... tags) {
        for (String tag : tags) {
            MiPushClient.unsubscribe(context, tag, null);
        }
    }

    @Override
    public String getClientId(Context context) {
       return  MiPushClient.getRegId(context);
    }

    @Override
    public String getName() {
        return NAME;
    }
}
