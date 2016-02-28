package com.nucyzh.connect_net;

import cn.bmob.v3.BmobObject;

/**
 * Author:XiYang on 2016/2/28.
 * Email:765849854@qq.com
 *
 * @class description
 */
public class Notes extends BmobObject {
    private String id;
    private String name;
    private String content;
    private String date;

    public Notes(String id, String name, String content, String date) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
