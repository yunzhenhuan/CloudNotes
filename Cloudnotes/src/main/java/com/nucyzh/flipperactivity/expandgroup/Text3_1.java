package com.nucyzh.flipperactivity.expandgroup;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.nucyzh.R;


public class Text3_1 {
	private Context mContext;
	private View mWikiMsg;
	public Text3_1(Context context) {
		mContext = context;
		mWikiMsg = LayoutInflater.from(context).inflate(R.layout.chat, null);
		TextView textView = (TextView)mWikiMsg.findViewById(R.id.text);
		textView.setText("Chat");
		TextView content = (TextView)mWikiMsg.findViewById(R.id.tv_content);
		content.setBackgroundColor(Color.RED);
	}
	public View getView() {
		return mWikiMsg;
	}
}
