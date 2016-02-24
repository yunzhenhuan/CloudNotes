package com.nucyzh.flipperactivity.expandgroup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.nucyzh.R;


public class Text2_2 {
    private Context mContext;
    private View mWikiMsg;

    public Text2_2(Context context) {
        mContext = context;
        mWikiMsg = LayoutInflater.from(context).inflate(R.layout.chat, null);
        TextView textView = (TextView) mWikiMsg.findViewById(R.id.text);
        textView.setText("测试2_2");
    }

    public View getView() {
        return mWikiMsg;
    }
}
