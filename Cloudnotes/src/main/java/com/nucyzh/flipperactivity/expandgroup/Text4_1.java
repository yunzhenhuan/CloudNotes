package com.nucyzh.flipperactivity.expandgroup;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nucyzh.R;
import com.nucyzh.online_chat.Chat;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.ValueEventListener;


public class Text4_1 {
    private Context mContext;
    private View mWikiMsg;
    private TextView tv_time;
    ListView lv_data;
    Button btn_send;
    EditText et_name, et_content;

    MyAdapter myAdapter;
    List<Chat> messages = new ArrayList<Chat>();
    BmobRealTimeData data = new BmobRealTimeData();

    public Text4_1(Context context) {
        mContext = context;
        mWikiMsg = LayoutInflater.from(context).inflate(R.layout.online_chat, null);
        init();
        et_name = (EditText) mWikiMsg.findViewById(R.id.et_name);
        et_content = (EditText) mWikiMsg.findViewById(R.id.et_content);
        lv_data = (ListView) mWikiMsg.findViewById(R.id.lv_data);
        btn_send = (Button) mWikiMsg.findViewById(R.id.btn_send);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_name.getText().toString();
                String content = et_content.getText().toString();
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(content)) {
                    Toast.makeText(mContext, "用户名和内容不能为空", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    sendMsg(name, content,new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
                }
            }
        });
        myAdapter = new MyAdapter();
        lv_data.setAdapter(myAdapter);
    }

    public View getView() {
        return mWikiMsg;
    }

    private void init() {
        Bmob.initialize(mContext, "c238263866c0f587531c8c406cc47251");
        data.start(mContext, new ValueEventListener() {
            @Override
            public void onDataChange(JSONObject arg0) {
                // TODO Auto-generated method stub
                if (BmobRealTimeData.ACTION_UPDATETABLE.equals(arg0.optString("action"))) {
                    JSONObject data = arg0.optJSONObject("data");
                    messages.add(new Chat(data.optString("name"), data.optString("content"),data.optString("send_time")));
                    myAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onConnectCompleted() {
                // TODO Auto-generated method stub
                if (data.isConnected()) {
                    data.subTableUpdate("Chat");
                }
            }
        });
    }

    /**
     * 发送消息
     *
     * @param name
     * @param msg
     */
    private void sendMsg(String name, String msg,String send_time) {
        Chat chat = new Chat(name + "：", msg,send_time);
        chat.save(mContext, new SaveListener() {

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                et_content.setText("");
            }

            @Override
            public void onFailure(int arg0, String arg1) {
                // TODO Auto-generated method stub
            }
        });
    }

    private class MyAdapter extends BaseAdapter {
        ViewHolder holder;

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return messages.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return messages.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            if (convertView == null) {
                System.out.print("getView");
                convertView = LayoutInflater.from(mContext.getApplicationContext()).inflate(R.layout.list_item, null);
                holder = new ViewHolder();
                holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
                holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Chat chat = messages.get(position);
            holder.tv_name.setText(chat.getName());
            holder.tv_content.setText(chat.getContent());
            holder.tv_time.setText(chat.getSend_time());
            holder.tv_time.setBackgroundColor(Color.GRAY);
            return convertView;
        }

        class ViewHolder {
            TextView tv_name;
            TextView tv_content;
            TextView tv_time;
        }
    }
}
