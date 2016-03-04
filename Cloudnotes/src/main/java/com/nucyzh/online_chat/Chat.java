package com.nucyzh.online_chat;

import cn.bmob.v3.BmobObject;

/**
 * Author:XiYang on 2016/2/20.
 * Email:765849854@qq.com
 */
public class Chat extends BmobObject {
    private String name;
    private String content;
    private String send_time;



    public Chat(String name, String content,String send_time) {
        this.name = name;
        this.content = content;
        this.send_time = send_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setSend_time(String send_time) {
        this.send_time = send_time;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getSend_time() {
        return send_time;
    }
}