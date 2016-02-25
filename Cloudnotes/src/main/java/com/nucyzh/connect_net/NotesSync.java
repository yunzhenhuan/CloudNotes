package com.nucyzh.connect_net;

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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.datatype.BmobFile;
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
    List<Sync> messages = new ArrayList<Sync>();
    BmobRealTimeData data = new BmobRealTimeData();

    public static String currentPath;


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
    List<BmobObject> picture;
    private void init() {
        System.out.println("init()");
        picture = new ArrayList<BmobObject>();
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
                        System.out.println("点击upload");

                        System.out.println("test1");
                        String[] filePaths = new String[1];
                        filePaths[0] = currentPath;

                        final File file = new File(currentPath);
                        //批量上传是会依次上传文件夹里面的文件
                        Bmob.uploadBatch(context, filePaths, new UploadBatchListener() {
                            @Override
                            public void onSuccess(List<BmobFile> files, List<String> urls) {
                                System.out.println("上传成功");

                                if (urls.size() == 1) {//如果第一个文件上传完成
                                    System.out.println("上传成功1");
                                    Sync sync = new Sync("ABC", files.get(0));

                                    picture.add(sync);
                                }
                            }

                            @Override
                            public void onProgress(int i, int i1, int i2, int i3) {
                                System.out.println("上传中...");
                            }

                            @Override
                            public void onError(int i, String s) {
                                System.out.println("上传失败");
                            }
                        });

                    }
                }
        );
        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("点击Download");
                Toast.makeText(context, "Upload", Toast.LENGTH_SHORT);

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
