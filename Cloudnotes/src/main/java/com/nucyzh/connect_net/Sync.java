package com.nucyzh.connect_net;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Author:XiYang on 2016/2/25.
 * Email:765849854@qq.com
 *
 * @class description
 */
public class Sync extends BmobObject {
    private String id;
    private String name;
    private String content;
    private String date;
    BmobFile file;


    private String path;
    private String note_id;

    /**
     * @param id
     * @param name    标题
     * @param content 内容
     * @param date    编辑日期
     * @param path  媒体文件路径
     * @param note_id 媒体所有者的id
     * @param file    文件
     */
    public Sync(String id, String name, String content, String date, String path, String note_id, BmobFile file) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.date = date;
        this.file = file;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setNote_id(String note_id) {
        this.note_id = note_id;
    }

    public void setFile(BmobFile file) {
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getPath() {
        return path;
    }

    public String getNote_id() {
        return note_id;
    }
    public BmobFile getFile() {
        return file;
    }
}
