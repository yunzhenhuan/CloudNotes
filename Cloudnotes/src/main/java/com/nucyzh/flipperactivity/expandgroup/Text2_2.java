package com.nucyzh.flipperactivity.expandgroup;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.RadioButton;

import com.nucyzh.R;


public class Text2_2 {
    private Context mContext;
    private View mWikiMsg;
    ViewPager viewPager;
    private RadioButton notes;
    private RadioButton news;
    private RadioButton contacts;
    private RadioButton setting;

    public Text2_2(Context context) {
        mContext = context;
        mWikiMsg = LayoutInflater.from(context).inflate(R.layout.message, null);
        viewPager = (ViewPager) mWikiMsg.findViewById(R.id.message_pager);
        notes = (RadioButton) mWikiMsg.findViewById(R.id.rb_notes);
        news = (RadioButton) mWikiMsg.findViewById(R.id.rb_news);
        contacts = (RadioButton) mWikiMsg.findViewById(R.id.rb_contacts);
        setting = (RadioButton) mWikiMsg.findViewById(R.id.rb_setting);
        setListener();//设置监听
    }

    public View getView() {
        return mWikiMsg;
    }

    private void setListener() {
        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setBackgroundColor(Color.BLUE);
            }
        });
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setBackgroundColor(Color.GRAY);
            }
        });
        contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setBackgroundColor(Color.BLACK);
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setBackgroundColor(Color.YELLOW);
            }
        });
    }
    Adapter
}
