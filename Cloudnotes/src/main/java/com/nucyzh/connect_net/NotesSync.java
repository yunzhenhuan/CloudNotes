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
import com.nucyzh.notes.db.NotesDB;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;

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
                        progressDialog.setMessage("正在上传。。。");
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressDialog.show();


                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                final String[] filePaths = new String[1];
                                filePaths[0] = currentPath;

                                //批量上传是会依次上传文件夹里面的文件
                                Bmob.uploadBatch(context, filePaths, new UploadBatchListener() {
                                    @Override
                                    public void onSuccess(List<BmobFile> files, List<String> urls) {
                                        progressDialog.dismiss();
                                        // 查询本笔记的noteId并且检查是否有对应的多媒体，有则遍历显示在MediaList中
                                        Toast.makeText(context, "上传文件成功", Toast.LENGTH_SHORT).show();
                                        String table = NotesDB.TABLE_NAME_NOTES;
                                        Cursor cursor = dbRead.query(table, null, null, null, null, null, null);
                                        while (cursor.moveToNext()) {
                                            //根据列名获取列索引
                                            int nameColumnIndex = cursor.getColumnIndex("_id");
                                            String strValue_id = cursor.getString(nameColumnIndex);
                                            int nameColumnIndex_name = cursor.getColumnIndex("name");
                                            String strValue_name = cursor.getString(nameColumnIndex_name);
                                            int nameColumnIndex_content = cursor.getColumnIndex("content");
                                            String strValue_content = cursor.getString(nameColumnIndex_content);
                                            int nameColumnIndex_date = cursor.getColumnIndex("date");
                                            String strValue_date = cursor.getString(nameColumnIndex_date);
                                            System.out.println(strValue_id + strValue_name + strValue_content + strValue_date);
                                            Notes notes = new Notes(strValue_id, strValue_name, strValue_content, strValue_date);
                                            picture.add(notes);
                                        }

                                        String table2 = NotesDB.TABLE_NAME_MEDIA;
                                        Cursor cursor2 = dbRead.query(table2, null, null, null, null, null, null);
                                        while (cursor2.moveToNext()) {
                                            //根据列名获取列索引
                                            int nameColumnIndex = cursor2.getColumnIndex("_id");
                                            String strValue_id = cursor2.getString(nameColumnIndex);
                                            int nameColumnIndex_path = cursor2.getColumnIndex("path");
                                            String strValue_path = cursor2.getString(nameColumnIndex_path);
                                            int nameColumnIndex_note_id = cursor2.getColumnIndex("note_id");
                                            String strValue_note_id = cursor2.getString(nameColumnIndex_note_id);

                                            System.out.println(strValue_id + strValue_path + strValue_note_id );
                                            Media media = new Media(strValue_id, strValue_path, strValue_path);
                                            picture.add(media);
                                        }

                                       /* for (int i = 1; i <= 1; i++) {
                                            if (urls.size() == i) {//如果第i个文件上传完成

                                            }
                                        }*/
                                        insertBatch(picture);
                                        progressDialog.dismiss();
                                    }

                                    @Override
                                    public void onProgress(int i, int i1, int i2, int i3) {
                                        Toast.makeText(context, "上传中...", Toast.LENGTH_SHORT).show();

                                    }

                                    @Override
                                    public void onError(int i, String s) {
                                        Toast.makeText(context, "上传失败", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).start();
                    }
                }
        );
        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "downloading", Toast.LENGTH_SHORT).show();

            }
        });
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
