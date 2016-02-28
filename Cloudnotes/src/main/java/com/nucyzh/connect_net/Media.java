package com.nucyzh.connect_net;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Author:XiYang on 2016/2/25.
 * Email:765849854@qq.com
 *
 * @class description
 */
public class Media extends BmobObject {
    private String id;
    private String path;
    private String note_id;
    private BmobFile file;

    public Media(String id, String path, String note_id ) {
        this.id = id;
        this.path = path;
        this.note_id = note_id;

    }
    public Media(String id, String path, String note_id, BmobFile file) {
        this.id = id;
        this.path = path;
        this.note_id = note_id;
        this.file = file;
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
