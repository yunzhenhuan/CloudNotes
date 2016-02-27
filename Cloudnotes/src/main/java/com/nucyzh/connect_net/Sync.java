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
    private String path;
    private String note_id;
    public Sync() {

    }


    public Sync(String id, String name, String content, String date) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.date = date;
    }

    public Sync(String id, String path, String note_id,BmobFile file) {
        this.id = id;
        this.path = path;
        this.file = file;
        this.note_id = note_id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getNote_id() {
        return note_id;
    }

    public void setNote_id(String note_id) {
        this.note_id = note_id;
    }

    private BmobFile file;


    public void setId(String id) {
        this.id = id;
    }

    public void setFile(BmobFile file) {
        this.file = file;
    }

    public String getId() {
        return id;
    }

    public BmobFile getFile() {
        return file;
    }
}
