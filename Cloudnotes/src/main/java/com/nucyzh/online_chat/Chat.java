package com.nucyzh.online_chat;

import cn.bmob.v3.BmobObject;

/**
 * Author:XiYang on 2016/2/20.
 * Email:765849854@qq.com
 */
public class Chat extends BmobObject {
    private String name;
    private String content;

    public Chat(String name, String content){
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

}