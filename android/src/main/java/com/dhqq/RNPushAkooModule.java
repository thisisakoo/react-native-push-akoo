
package com.dhqq;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;

import com.dhqq.huaweipush.HuaweiPushManager;
import com.dhqq.mipush.MiPushManager;
import com.dhqq.rnmixpush.MixPushManager;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import org.json.JSONException;
import org.json.JSONObject;

public class RNPushAkooModule extends ReactContextBaseJavaModule {


  public static final String EVENT_RECEIVE_REMOTE_NOTIFICATION = "receiveRemoteNotification";
  public static final String EVENT_TYPE_PAYLOAD = "payload";
  public static final String EVENT_RECEIVE_CLIENTID ="receiveClientId";
  private static ReactApplicationContext mRAC;
  public static MixPushManager pushManager;
  public static Activity mMainActiviy;
    public static String miAppKey;
    public static String miAppId;
  private Handler  handler;

  public RNPushAkooModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.mRAC = reactContext;
    initpush();

  }
  public static void initpush()
  {
      new Thread(new Runnable() {
          @Override
          public void run() {
              //子线程操作
              String brand = Build.BRAND.toLowerCase();
              switch (brand) {
                  case "huawei1":
                      if(mMainActiviy!=null) {
                          pushManager = new HuaweiPushManager();
//                    Looper.prepare();
                          pushManager.registerPush(mMainActiviy);
//                    Looper.loop();// 进入loop中的循环，查看消息队列
                      }
                      break;
                  default:

                      pushManager = new MiPushManager(miAppId,miAppKey);
                      pushManager.registerPush(mMainActiviy);
                      break;
              }

          }
      }).start();
  }

  public static void miAppconfig(String appId,String appKey)
  {
      miAppId= appId;
      miAppKey = appKey;
  }
  @Override
  public String getName() {
    return "RNPushAkoo";
  }
  @Override
  public boolean canOverrideExistingModule() {
    return true;
  }
  public static void registerPush(Activity activity){

      mMainActiviy = activity;
  }

  public static void sendEvent(String eventName, @Nullable WritableMap params){
    mRAC.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
            .emit(eventName, params);
  }

  public static void sendEvent(String eventName, @Nullable String params){
    mRAC.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
            .emit(eventName, params);
  }

  @ReactMethod
  public void  setAlias(String alias){
    if(pushManager!=null){
      pushManager.setAlias(mRAC,alias);
    }
  }

  @ReactMethod
  public void  unsetAlias(String alias){
    if(pushManager!=null){
      pushManager.unsetAlias(mRAC,alias);
    }
  }

  @ReactMethod
  public void  setTags(String tags){
    if(pushManager!=null){
      pushManager.setTags(mRAC,tags);
    }
  }

  @ReactMethod
  public void  unsetTags(String tags){
    if(pushManager!=null){
      pushManager.unsetTags(mRAC,tags);
    }
  }

  @ReactMethod
  public void  getClientId(final Callback callback){
    String clientId="";
    if(pushManager!=null){
      clientId= pushManager.getClientId(mRAC);
    }
    callback.invoke(clientId);
  }

  @ReactMethod
  public void getDeviceInfo(final Callback callback) throws JSONException {
    JSONObject info = new JSONObject();
    info.put("BRAND", Build.BRAND);
    info.put("DEVICE", Build.DEVICE);
    info.put("MODEL", Build.MODEL);
    info.put("TAGS", Build.TAGS);
    callback.invoke(info.toString());
  }
}