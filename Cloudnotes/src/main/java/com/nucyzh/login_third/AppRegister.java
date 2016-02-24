package com.nucyzh.login_third;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Author:XiYang on 2016/2/21.
 * Email:765849854@qq.com
 */
public class AppRegister extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        final IWXAPI api = WXAPIFactory.createWXAPI(context, null);
        api.registerApp(Constants.WEIXIN_APP_ID);
    }
}

