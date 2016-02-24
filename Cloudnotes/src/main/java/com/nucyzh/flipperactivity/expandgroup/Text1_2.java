package com.nucyzh.flipperactivity.expandgroup;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.nucyzh.R;


public class Text1_2 {
	private Context mContext;
	private View mRfidMsg;
	public Text1_2(Context context) {
		this.mContext = context;
		this.mRfidMsg = LayoutInflater.from(context).inflate(R.layout.chat, null);
		TextView textView = (TextView)mRfidMsg.findViewById(R.id.text);
		textView.setText("测试1_2");
	}
	public View getView(){
		return mRfidMsg;
	}
	
}
