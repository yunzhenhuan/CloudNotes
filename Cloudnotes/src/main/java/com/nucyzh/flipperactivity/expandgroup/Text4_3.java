package com.nucyzh.flipperactivity.expandgroup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.nucyzh.R;


public class Text4_3 {
	private Context mContext;
	private View mWikiMsg;
	public Text4_3(Context context) {
		mContext = context;
		mWikiMsg = LayoutInflater.from(context).inflate(R.layout.chat, null);
		TextView textView = (TextView)mWikiMsg.findViewById(R.id.text);
		textView.setText("测试4_3");
	}
	public View getView() {
		return mWikiMsg;
	}
}
