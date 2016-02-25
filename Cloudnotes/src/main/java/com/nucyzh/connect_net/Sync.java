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
    private BmobFile file;

    public Sync() {

    }
    public Sync(String id, BmobFile file) {
        this.id = id;
        this.file = file;
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
