package com.nucyzh.flipperactivity.expandgroup;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.nucyzh.R;
import com.nucyzh.streetsdk.PanoramaActivity;
import com.nucyzh.streetsdk.PanoramaFragActivity;


public class Text2_1 {
    private Context mContext;
    private View mWikiMsg;
    private final DemoInfo[] demos = {
            new DemoInfo(R.string.demo_lable_pano_activity,
                    R.string.demo_desc_pano_activity, PanoramaActivity.class),
            new DemoInfo(R.string.demo_lable_pano_fragment,
                    R.string.demo_desc_pano_fragment, PanoramaFragActivity.class)
    };

    public Text2_1(final Context context) {
        mContext = context;
        mWikiMsg = LayoutInflater.from(context).inflate(R.layout.maptest, null);
        TextView textView = (TextView) mWikiMsg.findViewById(R.id.text);
        textView.setText("Map Test");

        ListView listView = (ListView) mWikiMsg.findViewById(R.id.listView);
        DemoAdapter adapter = new DemoAdapter(context, demos);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(context, demos[position].getActivityClass());
                context.startActivity(intent);
            }
        });
    }


    public View getView() {
        return mWikiMsg;
    }

    class DemoAdapter extends BaseAdapter {

        private Context mContext;
        private DemoInfo[] mDemos;
        private LayoutInflater mInflater;

        public DemoAdapter() {
            super();
        }

        public DemoAdapter(Context context, DemoInfo[] demos) {
            // TODO Auto-generated constructor stub
            mContext = context;
            mDemos = demos;
            mInflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mDemos.length;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return mDemos[position];
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.demo_list_item, null);
                holder = new ViewHolder();
                holder.lable = (TextView) convertView.findViewById(R.id.lable);
                holder.description = (TextView) convertView.findViewById(R.id.desc);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.lable.setText(mDemos[position].getLableId());
            holder.description.setText(mDemos[position].getDescId());
            return convertView;
        }

        class ViewHolder {
            TextView lable;
            TextView description;
        }
    }

    class DemoInfo {
        private final int mLableId;
        private final int mDescId;
        private final Class<? extends android.app.Activity> mDemoActivityClass;

        public DemoInfo(int lableId, int descId,
                        Class<? extends android.app.Activity> demoActivityClass) {
            mLableId = lableId;
            mDescId = descId;
            mDemoActivityClass = demoActivityClass;
        }

        public int getLableId() {
            return mLableId;
        }

        public int getDescId() {
            return mDescId;
        }

        public Class<? extends android.app.Activity> getActivityClass() {
            return mDemoActivityClass;
        }
    }

}


