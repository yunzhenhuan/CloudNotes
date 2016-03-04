package com.nucyzh.flipperactivity.expandgroup;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.nucyzh.R;

/**
 * Author:XiYang on 2016/3/4.
 * Email:765849854@qq.com
 *
 * @class description
 */
public class LoginAndLogout {
    private Context mContext;
    private View mWikiMsg;
    public LoginAndLogout(Context context) {
        mContext = context;
        mWikiMsg = LayoutInflater.from(context).inflate(R.layout.chat, null);
        TextView textView = (TextView)mWikiMsg.findViewById(R.id.text);
        textView.setText("loginAndLogout");
        TextView content = (TextView)mWikiMsg.findViewById(R.id.tv_content);
        content.setBackgroundColor(Color.RED);
    }
    public View getView() {
        return mWikiMsg;
    }

}
