package com.nucyzh.connect_net;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.nucyzh.R;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UploadFileListener;
import cn.bmob.v3.listener.ValueEventListener;

/**
 * Author:XiYang on 2016/2/25.
 * Email:765849854@qq.com
 *
 * @class description 实现数据上传与下载
 */
public class NotesSync {
    Context context;

    public static String TABLE_NAME_NOTES = "notes";
    public static String TABLE_NAME_MEDIA = "media";
    public static String COLUMN_NAME_ID = "_id";
    public static String COLUMN_NAME_NOTE_NAME = "name";
    public static String COLUMN_NAME_NOTE_CONTENT = "content";
    public static String COLUMN_NAME_NOTE_DATE = "date";
    public static String COLUMN_NAME_MEDIA_PATH = "path";
    public static String COLUMN_NAME_MEDIA_OWNER_NOTE_ID = "note_id";

    private ListView lv_data;
    private Button btn_upload;
    private Button btn_download;

    MyAdapter myAdapter;
    List<Sync> messages = new ArrayList<Sync>();
    BmobRealTimeData data = new BmobRealTimeData();

    public NotesSync(Context context, Button upLoad, Button downLoad) {
        this.context = context;
        this.btn_upload = upLoad;
        this.btn_download = downLoad;
        init();
    }

    private void init() {
        System.out.println("init()");
        //Bmob.initialize(this, "c238263866c0f587531c8c406cc47251");
        //data.start(context, new ValueEventListener() {

        /**
         *连接完成
         *//*
            @Override
            public void onConnectCompleted() {
                // TODO Auto-generated method stub
                if (data.isConnected()) {
                    data.subTableUpdate("Notes");
                }
            }
        });*/
        btn_upload.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context,"Upload",Toast.LENGTH_SHORT);
                        System.out.println("点击upload");
                        data.start(context, new ValueEventListener() {
                            @Override
                            public void onDataChange(JSONObject arg0) {
                                // TODO Auto-generated method stub
                                /*if (BmobRealTimeData.ACTION_UPDATETABLE.equals(arg0.optString("action"))) {
                                    JSONObject data = arg0.optJSONObject("data");
                                    messages.add(new Chat(data.optString("name"), data.optString("content")));
                                    myAdapter.notifyDataSetChanged();
                                }*/
                                System.out.println("DataChange");
                            }
                            @Override
                            public void onConnectCompleted() {
                                // TODO Auto-generated method stub
                                if (data.isConnected()) {
                                    data.subTableUpdate("Notes");//更新数据表
                                }
                            }
                        });
                        @SuppressWarnings("unused")
                        File file = new File("/mnt/sdcard/" + COLUMN_NAME_MEDIA_PATH);
                        if (file != null) {
                            final BmobFile bmobFile = new BmobFile(file);
                            //上传进度对话框
                            final ProgressDialog progressDialog = new ProgressDialog(context);
                            progressDialog.setMessage("正在上传。。。");
                            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            progressDialog.show();
                            bmobFile.upload(context, new UploadFileListener() {
                                @Override
                                public void onSuccess() {
                                    // TODO Auto-generated method stub
                                    // 文件上传成功后，可将文件对象保存到数据表中
                                    Toast.makeText(context, "上传media成功", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                  /*final GameSauce gs = new GameSauce();
                                  gs.setPlayerName("rohs");
                                  gs.setPic(bmobFile);
                                  gs.save(MainActivity.this, new SaveListener() {
                                      @Override
                                      public void onSuccess() {
                                          // TODO Auto-generated method stub
                                          // toast("创建数据成功："+gs.getObjectId());
                                      }

                                      @Override
                                      public void onFailure(int code, String msg) {
                                          // TODO Auto-generated method stub
                                          toast("创建数据失败：" + msg);
                                      }
                                  });
*/
                                }

                                public void onProgress(Integer value) {
                                    // TODO Auto-generated method stub //
                                    // L.d("上传进度：" + value);
                                }

                                @Override
                                public void onFailure(int arg0, String arg1) {
                                    // TODO Auto-generated method stub
                                    Toast.makeText(context, "上传media失败" + arg1, Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Toast.makeText(context, "文件为空", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("点击Download");
                Toast.makeText(context,"Upload",Toast.LENGTH_SHORT);

                //Chat chat = new Chat(name, msg);
                /*Sync sync = new Sync(id, path, note_id);
                sync.save(context, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        // TODO Auto-generated method stub
                        Toast.makeText(context, "同步成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int arg0, String arg1) {
                        // TODO Auto-generated method stub
                        Toast.makeText(context, "同步失败", Toast.LENGTH_SHORT).show();
                    }
                });*/
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

            Sync sync = messages.get(position);

            /*holder.tv_name.setText(sync.getName());
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
