package com.nucyzh.notes;

import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nucyzh.R;
import com.nucyzh.connect_net.NotesSync;
import com.nucyzh.notes.db.NotesDB;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author:XiYang on 2016/2/18.
 * Email:765849854@qq.com
 */
public class AtyEditNote extends ListActivity {

    private int noteId = -1;
    private EditText etName, etContent;
    private MediaAdapter adapter;
    private NotesDB db;
    private SQLiteDatabase dbRead, dbWrite;
    private String currentPath = null;

    public static final int REQUEST_CODE_GET_PHOTO = 1;
    public static final int REQUEST_CODE_GET_VIDEO = 2;

    public static final String EXTRA_NOTE_ID = "noteId";
    public static final String EXTRA_NOTE_NAME = "noteName";
    public static final String EXTRA_NOTE_CONTENT = "noteContent";

    /**
     * 按钮点击的监听器，实现OnClickListener接口
     */
    private View.OnClickListener btnClickHandler = new View.OnClickListener() {

        Intent i;
        File f;

        /**
         * 点击AddNotes
         * @param v
         */
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnAddPhoto:// 添加照片按钮
                    // 使用Intent调用系统照相机，传入图像保存路径和名称
                    i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    f = new File(getMediaDir(), System.currentTimeMillis() + ".jpg");
                    if (!f.exists()) {
                        try {
                            f.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    currentPath = f.getAbsolutePath();
                    new NotesSync(currentPath);
                    i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(i, REQUEST_CODE_GET_PHOTO);
                    break;
                case R.id.btnAddVideo:// 添加视频按钮
                    // 使用Intent调用系统录像器，传入视频保存路径和名称
                    i = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                    f = new File(getMediaDir(), System.currentTimeMillis() + ".mp4");
                    if (!f.exists()) {
                        try {
                            f.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    currentPath = f.getAbsolutePath();
                    i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));

                    startActivityForResult(i, REQUEST_CODE_GET_VIDEO);
                    break;
                case R.id.btnSave:// 保存按钮
                    // 保存多媒体信息和笔记信息到数据库，然后关闭当前页面，返回到笔记列表页面/主页面
                    saveMedia(saveNote());
                    setResult(RESULT_OK);
                    finish();
                    break;
                case R.id.btnCancel:// 取消按钮
                    // 关闭当前页面，返回到笔记列表页面/主页面
                    setResult(RESULT_CANCELED);
                    finish();
                    break;

                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_eidt_note);

        db = new NotesDB(this);
        dbRead = db.getReadableDatabase();
        dbWrite = db.getWritableDatabase();

        // 显示多媒体列表
        adapter = new MediaAdapter(this);
        setListAdapter(adapter);

        etName = (EditText) findViewById(R.id.etName);
        etContent = (EditText) findViewById(R.id.etContent);

        // 获取Activity传递过来的noteId
        noteId = getIntent().getIntExtra(EXTRA_NOTE_ID, -1);

        if (noteId > -1) {
            etName.setText(getIntent().getStringExtra(EXTRA_NOTE_NAME));
            etContent.setText(getIntent().getStringExtra(EXTRA_NOTE_CONTENT));

            // 查询本笔记的noteId并且检查是否有对应的多媒体，有则遍历显示在MediaList中
            Cursor c = dbRead.query(NotesDB.TABLE_NAME_MEDIA, null,
                    NotesDB.COLUMN_NAME_MEDIA_OWNER_NOTE_ID + "=?",
                    new String[]{noteId + ""}, null, null, null);
            while (c.moveToNext()) {
                adapter.add(new MediaListCellData(c.getString(c
                        .getColumnIndex(NotesDB.COLUMN_NAME_MEDIA_PATH)), c
                        .getInt(c.getColumnIndex(NotesDB.COLUMN_NAME_ID))));
            }
            /**
             * Notifies the attached observers that the underlying data has been
             * changed and any View reflecting the data set should refresh
             * itself.
             */
            adapter.notifyDataSetChanged();
        }

        findViewById(R.id.btnSave).setOnClickListener(btnClickHandler);
        findViewById(R.id.btnCancel).setOnClickListener(btnClickHandler);
        findViewById(R.id.btnAddPhoto).setOnClickListener(btnClickHandler);
        findViewById(R.id.btnAddVideo).setOnClickListener(btnClickHandler);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        MediaListCellData data = adapter.getItem(position);
        Intent i;

        switch (data.type) {
            case MediaType.PHOTO:
                i = new Intent(this, AtyPhotoViewer.class);
                i.putExtra(AtyPhotoViewer.EXTRA_PATH, data.path);
                startActivity(i);
                break;
            case MediaType.VIDEO:
                i = new Intent(this, AtyVideoViewer.class);
                i.putExtra(AtyVideoViewer.EXTRA_PATH, data.path);
                startActivity(i);
                break;
        }

        super.onListItemClick(l, v, position, id);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        System.out.println(data);

        switch (requestCode) {
            case REQUEST_CODE_GET_PHOTO:
            case REQUEST_CODE_GET_VIDEO:
                if (resultCode == RESULT_OK) {
                    adapter.add(new MediaListCellData(currentPath));
                    adapter.notifyDataSetChanged();
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 获取存储Media的目录路径
     *
     * @return File类型的目录路径
     */
    public File getMediaDir() {
        File dir = new File(Environment.getExternalStorageDirectory(),
                "NotesMedia");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    /**
     * 保存Media信息到数据库
     *
     * @param noteId
     */
    public void saveMedia(int noteId) {

        MediaListCellData data;
        ContentValues cv;

        for (int i = 0; i < adapter.getCount(); i++) {
            data = adapter.getItem(i);

            if (data.id <= -1) {
                cv = new ContentValues();
                cv.put(NotesDB.COLUMN_NAME_MEDIA_PATH, data.path);
                cv.put(NotesDB.COLUMN_NAME_MEDIA_OWNER_NOTE_ID, noteId);
                dbWrite.insert(NotesDB.TABLE_NAME_MEDIA, null, cv);
            }
        }

    }

    /**
     * 保存日志到数据库
     *
     * @return
     */
    public int saveNote() {

        ContentValues cv = new ContentValues();
        cv.put(NotesDB.COLUMN_NAME_NOTE_NAME, etName.getText().toString());
        cv.put(NotesDB.COLUMN_NAME_NOTE_CONTENT, etContent.getText().toString());
        cv.put(NotesDB.COLUMN_NAME_NOTE_DATE, new SimpleDateFormat(
                "yyyy-MM-dd hh:mm:ss").format(new Date()));

        if (noteId > -1) {
            dbWrite.update(NotesDB.TABLE_NAME_NOTES, cv, NotesDB.COLUMN_NAME_ID
                    + "=?", new String[]{noteId + ""});
            return noteId;
        } else {
            return (int) dbWrite.insert(NotesDB.TABLE_NAME_NOTES, null, cv);
        }
    }

    /**
     * 复写Activity的生命周期方法，用于关闭读写数据库的操作
     */
    @Override
    protected void onDestroy() {
        dbRead.close();
        dbWrite.close();
        super.onDestroy();
    }

    /**
     * 继承BaseAdapter类的MediaAdapter类，用于显示媒体信息
     *
     * @author XiYang
     */
    static class MediaAdapter extends BaseAdapter {
        private Context context;
        private List<MediaListCellData> list = new ArrayList<MediaListCellData>();

        public MediaAdapter(Context context) {
            this.context = context;
        }

        public void add(MediaListCellData data) {
            list.add(data);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public MediaListCellData getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.media_list_cell, null);
            }
            MediaListCellData data = getItem(position);

            ImageView ivIcon = (ImageView) convertView
                    .findViewById(R.id.ivIcon);
            TextView tvPath = (TextView) convertView.findViewById(R.id.tvPath);

            ivIcon.setImageResource(data.iconId);
            tvPath.setText(data.path);
            return convertView;
        }

    }

    /**
     * 显示多媒体的条目类
     *
     * @author XiYang
     */
    static class MediaListCellData {
        int type = 0;
        int id = -1;
        String path = "";
        int iconId = R.drawable.ic_launcher;

        public MediaListCellData(String path, int id) {
            this(path);

            this.id = id;
        }

        public MediaListCellData(String path) {
            this.path = path;

            if (path.endsWith(".jpg")) {
                iconId = R.drawable.icon_photo;
                type = MediaType.PHOTO;
            } else if (path.endsWith(".mp4")) {
                iconId = R.drawable.icon_video;
                type = MediaType.VIDEO;
            }
        }
    }

    /**
     * 多媒体的种类
     *
     * @author XiYang
     */
    static class MediaType {
        static final int PHOTO = 1;
        static final int VIDEO = 2;
    }
}
