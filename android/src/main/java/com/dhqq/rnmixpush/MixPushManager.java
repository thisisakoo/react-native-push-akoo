package com.dhqq.rnmixpush;

import android.app.Activity;
import android.content.Context;

public interface MixPushManager {

    void registerPush(Activity mactivity);

    void unRegisterPush(Activity mactivity);

    void setAlias(Context context, String alias);

    void unsetAlias(Context context, String alias);

    void setTags(Context context, String... tags);

    void unsetTags(Context context, String... tags);

    String getName();

    String getClientId(Context context);
}