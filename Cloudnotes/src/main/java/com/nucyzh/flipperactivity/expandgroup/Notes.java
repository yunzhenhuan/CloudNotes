package com.nucyzh.flipperactivity.expandgroup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.nucyzh.R;
import com.nucyzh.notes.AtyEditNote;
import com.nucyzh.notes.db.NotesDB;


public class Notes {
    private static String TAG = "Notes";
    public Context mContext;
    private View mChatMsg;
    private SimpleCursorAdapter adapter = null;
    private ListView noteList;
    private NotesDB db;
    private SQLiteDatabase dbRead;

    public static final int REQUEST_CODE_ADD_NOTE = 1;
    public static final int REQUEST_CODE_EDIT_NOTE = 2;

    /**
     * 实现OnClickListener接口，添加日志按钮的监听
     */
    private View.OnClickListener btnAddNote_clickHandler = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // 有返回结果的开启编辑日志的Activity，
            // requestCode If >= 0, this code will be returned
            // in onActivityResult() when the activity exits.

            // startActivityForResult(new Intent(mContext,AtyEditNote.class), REQUEST_CODE_ADD_NOTE);
        }
    };

    public Notes(Context context) {
        mContext = context;
        mChatMsg = LayoutInflater.from(context).inflate(R.layout.chat, null);
        TextView textView = (TextView) mChatMsg.findViewById(R.id.text);
        textView.setText("Notes");  //设置标题内容

        noteList = (ListView) mChatMsg.findViewById(R.id.notelist);
        // 操作数据库
        db = new NotesDB(mContext);
        dbRead = db.getReadableDatabase();

        // 查询数据库并将数据显示在ListView上。
        // 建议使用CursorLoader，这个操作因为在UI线程，容易引起无响应错误
        adapter = new SimpleCursorAdapter(mContext, R.layout.notes_list_cell, null,
                new String[]{NotesDB.COLUMN_NAME_NOTE_NAME,
                        NotesDB.COLUMN_NAME_NOTE_DATE}, new int[]{
                R.id.tvName, R.id.tvDate});
        noteList.setAdapter(adapter);
        refreshNotesListView();
        mChatMsg.findViewById(R.id.btnAddNote).setOnClickListener(btnAddNote_clickHandler);

        //item点击事件
        noteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * 笔记列表中的笔记条目被点击时被调用，打开编辑笔记页面，同事传入当前笔记的信息
             * @param parent
             * @param view
             * @param position
             * @param id
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 获取当前笔记条目的Cursor对象
                Cursor c = adapter.getCursor();
                c.moveToPosition(position);
                System.out.println("点击事件");

                // 显式Intent开启编辑笔记页面
                Intent intent = new Intent(mContext, AtyEditNote.class);

                // 传入笔记id，name，content
                intent.putExtra(AtyEditNote.EXTRA_NOTE_ID,
                        c.getInt(c.getColumnIndex(NotesDB.COLUMN_NAME_ID)));
                intent.putExtra(AtyEditNote.EXTRA_NOTE_NAME,
                        c.getString(c.getColumnIndex(NotesDB.COLUMN_NAME_NOTE_NAME)));
                intent.putExtra(AtyEditNote.EXTRA_NOTE_CONTENT,
                        c.getString(c.getColumnIndex(NotesDB.COLUMN_NAME_NOTE_CONTENT)));

                // 有返回的开启Activity
                //mContext.startActivityForResult(intent, REQUEST_CODE_EDIT_NOTE);


            }
        });
        //item长按事件
        noteList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("长按事件");
                return true;
            }
        });
    }

    public View getView() {

        return mChatMsg;
    }


    /**
     * Called when an activity you launched exits, giving you the requestCode
     * you started it with 当被开启的Activity存在并返回结果时调用的方法
     * <p/>
     * 当从编辑笔记页面返回时调用，刷新笔记列表
     */

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case REQUEST_CODE_ADD_NOTE:
            case REQUEST_CODE_EDIT_NOTE:
                if (resultCode == Activity.RESULT_OK) {
                    refreshNotesListView();
                }
                break;

            default:
                break;
        }

        // super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 刷新笔记列表，内容从数据库中查询
     */
    public void refreshNotesListView() {
        /**
         * Change the underlying cursor to a new cursor. If there is an existing
         * cursor it will be closed.
         *
         * Parameters: cursor The new cursor to be used
         */
        adapter.changeCursor(dbRead.query(NotesDB.TABLE_NAME_NOTES, null, null,
                null, null, null, null));
    }
}
