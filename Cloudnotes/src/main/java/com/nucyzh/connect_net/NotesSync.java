package com.nucyzh.connect_net;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.nucyzh.R;
import com.nucyzh.connect_net.utils.ToastUtil;
import com.nucyzh.notes.NotesActivity;
import com.nucyzh.notes.db.NotesDB;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Author:XiYang on 2016/2/25.
 * Email:765849854@qq.com
 *
 * @class description 实现数据上传与下载
 */
public class NotesSync {
    Context context;

    private ListView lv_data;
    private Button btn_upload;
    private Button btn_download;

    MyAdapter myAdapter;
    List<Media> messages = new ArrayList<Media>();
    BmobRealTimeData data = new BmobRealTimeData();

    public static String currentPath;
    private NotesDB db;
    private SQLiteDatabase dbRead, dbWrite;

    public NotesSync(String currentPath) {
        this.currentPath = currentPath;
        System.out.println(currentPath);
    }

    public NotesSync(Context context, Button upLoad, Button downLoad) {
        this.context = context;
        this.btn_upload = upLoad;
        this.btn_download = downLoad;
        init();
    }

    List<BmobObject> picture = new ArrayList<BmobObject>();

    private void init() {
        Bmob.initialize(context, "c238263866c0f587531c8c406cc47251");
        System.out.println("init()");
        db = new NotesDB(context);
        dbRead = db.getReadableDatabase();
        dbWrite = db.getWritableDatabase();

        btn_upload.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("点击upload");
                        final ProgressDialog progressDialog = new ProgressDialog(context);
                        progressDialog.setMessage("Uploading。。。");
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressDialog.show();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                final BmobQuery<Notes> query = new BmobQuery<Notes>();
                                query.findObjects(context, new FindListener<Notes>() {
                                    @Override
                                    public void onSuccess(final List<Notes> list) {
                                        final Cursor cursor = dbRead.query(NotesDB.TABLE_NAME_NOTES, null, null, null, null, null, null);
                                        while (cursor.moveToNext()) {
                                            String id = cursor.getString(cursor.getColumnIndex("_id"));
                                            boolean already = false;  //记录是否已经上传过,默认为没有上传过
                                            for (int i = 0; i < list.size(); i++) {//与云端查出云端的所有数据比较
                                                Cursor c_test = dbRead.rawQuery("select * from notes where _id=?", new String[]{list.get(i).getId()});
                                                if (!c_test.moveToNext()) {//如果本地数据库中没有该条数据
                                                    System.out.println("Notes-----删除云端该条数据");
                                                    final Notes notes2 = list.get(i);
                                                    notes2.delete(context, notes2.getObjectId(), new DeleteListener() {
                                                        @Override
                                                        public void onSuccess() {
                                                            System.out.println("Notes----删除云端id=" + notes2.getId() + "成功");
                                                        }

                                                        @Override
                                                        public void onFailure(int i, String s) {
                                                            System.out.println("Notes----------删除云端id=" + notes2.getId() + "失败");
                                                        }
                                                    });
                                                }
                                                if (id.equals(list.get(i).getId())) {
                                                    already = true;
                                                    Notes notes2 = list.get(i);
                                                    notes2.setName(cursor.getString(cursor.getColumnIndex("name")));
                                                    notes2.setContent(cursor.getString(cursor.getColumnIndex("content")));
                                                    notes2.setDate(cursor.getString(cursor.getColumnIndex("date")));
                                                    System.out.println("Notes---------" + id + "---已经上传过,本次更新");
                                                    notes2.update(context, notes2.getObjectId(), new UpdateListener() {
                                                        @Override
                                                        public void onSuccess() {
                                                            System.out.println("Notes------本次更新成功");
                                                        }

                                                        @Override
                                                        public void onFailure(int i, String s) {
                                                            System.out.println("Notes---------本次更新失败");
                                                        }
                                                    });
                                                    break;//在云端数据中发现已经上传过,更新并跳出比较
                                                }
                                            }
                                            if (!already) {//如果没有上传过就上传
                                                Notes notes = new Notes();
                                                notes.setId(id);
                                                notes.setName(cursor.getString(cursor.getColumnIndex("name")));
                                                notes.setContent(cursor.getString(cursor.getColumnIndex("content")));
                                                notes.setDate(cursor.getString(cursor.getColumnIndex("date")));
                                                notes.save(context);
                                                System.out.println("Notes--" + id + "---没有上传过---本次上传");
                                            }
                                        }
                                    }

                                    @Override
                                    public void onError(int i, String s) {
                                        ToastUtil.toast(context, s + "Notes表查询失败-----初次上传");//查询失败
                                        List<BmobObject> notes1 = new ArrayList<BmobObject>();

                                        final Cursor cursor = dbRead.query(NotesDB.TABLE_NAME_NOTES, null, null, null, null, null, null);
                                        while (cursor.moveToNext()) {
                                            //根据列名获取列索引
                                            Notes notes = new Notes();
                                            notes.setId(cursor.getString(cursor.getColumnIndex("_id")));
                                            notes.setName(cursor.getString(cursor.getColumnIndex("name")));
                                            notes.setContent(cursor.getString(cursor.getColumnIndex("content")));
                                            notes.setDate(cursor.getString(cursor.getColumnIndex("date")));
                                            notes1.add(notes);
                                        }//批量上传
                                        new BmobObject().insertBatch(context, notes1, new SaveListener() {
                                            @Override
                                            public void onSuccess() {
                                                ToastUtil.toast(context, "上传成功");
                                            }

                                            @Override
                                            public void onFailure(int arg0, String arg1) {
                                                ToastUtil.toast(context, arg1 + "上传失败");
                                            }
                                        });
                                    }
                                });
                                final BmobQuery<Media> query_media = new BmobQuery<Media>();
                                query_media.findObjects(context, new FindListener<Media>() {
                                    @Override
                                    public void onSuccess(final List<Media> list) {
                                        System.out.println("在云上查找Media成功");
                                        final Cursor cursor = dbRead.query(NotesDB.TABLE_NAME_MEDIA, null, null, null, null, null, null);
                                        while (cursor.moveToNext()) {
                                            //根据列名获取列索引
                                            final Media media = new Media();
                                            String id = cursor.getString(cursor.getColumnIndex("_id"));
                                            boolean already = false;  //记录是否已经上传过
                                            for (int i = 0; i < list.size(); i++) {//查出云端的所有数据
                                                Cursor c_test = dbRead.rawQuery("select * from notes where _id=?", new String[]{list.get(i).getId()});
                                                if (!c_test.moveToNext()) {//如果本地数据库中没有,就删除云端的数据
                                                    final Media media2 = list.get(i);
                                                    media2.delete(context, media2.getObjectId(), new DeleteListener() {
                                                        @Override
                                                        public void onSuccess() {
                                                            System.out.println("Media----删除id=" + media2.getId() + "成功");
                                                        }

                                                        @Override
                                                        public void onFailure(int i, String s) {
                                                            System.out.println("Media------删除id=" + media2.getId() + "失败");
                                                        }
                                                    });
                                                }
                                                if (id.equals(list.get(i).getId())) {//已经上传过
                                                    already = true;
                                                    final Media media2 = list.get(i);
                                                    media2.setId(id);
                                                    media2.setPath(cursor.getString(cursor.getColumnIndex("path")));
                                                    media2.setNote_id(cursor.getString(cursor.getColumnIndex("note_id")));
                                                    media2.update(context, media2.getObjectId(), new UpdateListener() {
                                                        @Override
                                                        public void onSuccess() {
                                                            System.out.println("Media----id=" + media2.getId() + "已经上传过,本次更新成功");
                                                        }

                                                        @Override
                                                        public void onFailure(int i, String s) {
                                                            System.out.println("Media----id=" + media2.getId() + "已经上传过,本次更新失败");
                                                        }
                                                    });
                                                    break;//在云端数据中发现已经上传过,更新并跳出比较
                                                }
                                            }
                                            if (!already) {//如果没有上传过就上传
                                                media.setId(id);
                                                media.setPath(cursor.getString(cursor.getColumnIndex("path")));
                                                media.setNote_id(cursor.getString(cursor.getColumnIndex("note_id")));
                                                media.save(context);
                                                System.out.println("Media----id=" + media.getId() + "没有上传过----本条数据初次上传");
                                            }
                                        }
                                        progressDialog.dismiss();
                                        ToastUtil.toast(context, "Upload Complete");
                                    }

                                    @Override
                                    public void onError(int i, String s) {
                                        System.out.println("在云上查找Media表失败");
                                        ToastUtil.toast(context, s + "初次创建Media表");
                                        List<BmobObject> media1 = new ArrayList<BmobObject>();
                                        final Cursor cursor = dbRead.query(NotesDB.TABLE_NAME_MEDIA, null, null, null, null, null, null);
                                        while (cursor.moveToNext()) {
                                            //根据列名获取列索引
                                            Media media = new Media();
                                            media.setId(cursor.getString(cursor.getColumnIndex("_id")));
                                            media.setPath(cursor.getString(cursor.getColumnIndex("path")));
                                            media.setNote_id(cursor.getString(cursor.getColumnIndex("note_id")));
                                            media1.add(media);
                                        }//批量上传
                                        new BmobObject().insertBatch(context, media1, new SaveListener() {
                                            @Override
                                            public void onSuccess() {
                                                ToastUtil.toast(context, "上传成功---Media");
                                                progressDialog.dismiss();
                                                ToastUtil.toast(context, "上传成功");
                                            }

                                            @Override
                                            public void onFailure(int arg0, String arg1) {
                                                ToastUtil.toast(context, arg1 + "上传失败");
                                            }
                                        });
                                    }
                                });
                            }
                        }).start();
                    }
                }
        );
        btn_download.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("点击downloading");
                        final ProgressDialog progressDialog = new ProgressDialog(context);
                        progressDialog.setMessage("Downloading。。。");
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressDialog.show();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                final BmobQuery<Notes> query_notes = new BmobQuery<Notes>();
                                query_notes.findObjects(context, new FindListener<Notes>() {
                                            @Override
                                            public void onSuccess(List<Notes> list) {
                                                for (int i = 0; i < list.size(); i++) {
                                                    System.out.println("1test");
                                                    Cursor c_test = dbRead.query("notes", new String[]{"_id,name,content,date"}, "_id =?", new String[]{list.get(i).getId()}, null, null, null);
                                                    //dbRead.rawQuery("select * from notes where _id=?", new String[]{list.get(i).getId()});
                                                    System.out.println("2teset");
                                                    if (!c_test.moveToNext()) {//如果本地数据库中没有,就插入该条数据
                                                        dbWrite.execSQL("insert into notes values('"
                                                                + list.get(i).getId() + "','"
                                                                + list.get(i).getName() + "','"
                                                                + list.get(i).getContent() + "','"
                                                                + list.get(i).getDate() + "')");
                                                        System.out.println("如果本地数据库中没有");
                                                    } else {//否则更新该条数据
                                                        dbWrite.execSQL("update notes set name ='" + list.get(i).getName() + "'"
                                                                + ",content='" + list.get(i).getContent() + "'"
                                                                + ",date='" + list.get(i).getDate() + "'where _id=" + list.get(i).getId());
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onError(int i, String s) {
                                                ToastUtil.toast(context, s + "查询失败");
                                            }
                                        }
                                );
                                final BmobQuery<Media> query_media = new BmobQuery<Media>();
                                query_media.findObjects(context, new FindListener<Media>() {
                                            @Override
                                            public void onSuccess(List<Media> list) {
                                                for (int i = 0; i < list.size(); i++) {
                                                    System.out.println("1test");
                                                    Cursor c_test = dbRead.query("media", new String[]{"_id,path,note_id"}, "_id =?", new String[]{list.get(i).getId()}, null, null, null);
                                                    //dbRead.rawQuery("select * from notes where _id=?", new String[]{list.get(i).getId()});
                                                    System.out.println("2teset");
                                                    if (!c_test.moveToNext()) {//如果本地数据库中没有,就插入该条数据
                                                        dbWrite.execSQL("insert into media values('"
                                                                + list.get(i).getId() + "','"
                                                                + list.get(i).getPath() + "','"
                                                                + list.get(i).getNote_id() +  "')");
                                                        System.out.println("如果本地数据库中没有");
                                                    } else {//否则更新该条数据
                                                        dbWrite.execSQL("update media set path ='" + list.get(i).getPath() + "'"
                                                                + ",note_id='" + list.get(i).getNote_id() +  "'where _id=" + list.get(i).getId());
                                                    }
                                                }
                                                progressDialog.dismiss();
                                                ToastUtil.toast(context, "Upload Complete");
                                                NotesActivity.adapter.changeCursor(dbRead.query(NotesDB.TABLE_NAME_NOTES, null, null, null, null, null, null));
                                            }
                                            @Override
                                            public void onError(int i, String s) {
                                                ToastUtil.toast(context, s + "查询失败");
                                            }
                                        }
                                );
                            }
                        }).start();
                    }
                }
        );
    }

    /**
     * 弹出提示
     *
     * @param userBmobObjects
     * @return
     */

    private String showMsg(List<Notes> userBmobObjects) {
        String msg = "";
        for (Notes notes : userBmobObjects) {
            msg += notes.getObjectId() + "," + notes.getId() + "," + notes.getName() + ","
                    + notes.getContent() + "," + notes.getDate() + "\n";
        }
        return msg;
    }

    /**
     * 批量插入操作
     * insertBatch
     *
     * @return void
     * @throws
     */

    public void insertBatch(List<BmobObject> files) {
        new BmobObject().insertBatch(context, files, new SaveListener() {

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                System.out.println("批量更新成功");
                Toast.makeText(context, "批量更新成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int arg0, String arg1) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "批量更新成败", Toast.LENGTH_SHORT).show();
                System.out.println("批量更新成败");
            }
        });
    }

    private class MyAdapter extends BaseAdapter {
        private String TAG = "MyAdapter";
        ViewHolder holder;

        @Override
        public int getCount() {
            Log.d(TAG, "getCount()");
            // TODO Auto-generated method stub
            return messages.size();
        }

        @Override
        public Object getItem(int position) {
            Log.d(TAG, "getItem()");
            // TODO Auto-generated method stub
            return messages.get(position);
        }

        @Override
        public long getItemId(int position) {
            Log.d(TAG, "getItemId()");
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            Log.d(TAG, "getView()");
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.notes_desktop, null);
                holder = new ViewHolder();

                holder.listView = (ListView) convertView.findViewById(R.id.notelist2);
                holder.btnUpload = (Button) convertView.findViewById(R.id.btnUpload);
                // holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Media media = messages.get(position);

            /*holder.tv_name.setText(media.getName());
            holder.tv_content.setText(chat.getContent());
            holder.tv_time.setText(chat.getCreatedAt());*/

            return convertView;
        }
    }

    public class ViewHolder {

        ListView listView;
        Button btnUpload;
        // TextView tv_time;
    }
}
