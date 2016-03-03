package com.nucyzh.flipperactivity.activity_test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;

import com.nucyzh.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Desktop {
    private String[] mGroupName;
    private String[] mGroupName2;
    private String[] group_name1_chlid;
    private String[] group_name2_chlid;
    private String[] group_name3_chlid;
    private String[] group_name4_chlid;
    private String[] mChildFavoritesd;
    private int[] mChildActionIcon;
    private int[] image_group_chlid;
    private String[] mChildFavorite;
    private int[] mChildFavoriteIcon;
    private String[] mChildAction;
    private int expandFlag = -1;
    private OnChangeViewListener onChangeViewListener;
    private ExpandableListView desktop_list;
    private ExpandableListView mDisplay;
    DesktopAdapter adapter;
    DesktopAdapter adapter2;
    public static int mChooesId = 0;
    public static int mGroupId = 0;
    Context context;
    View mDesktop;
    private List<Map<String, Object>> mGroup1 = new ArrayList<Map<String, Object>>();
    private List<Map<String, Object>> mGroup = new ArrayList<Map<String, Object>>();
    private List<List<Map<String, Object>>> mChild1 = new ArrayList<List<Map<String, Object>>>();

    public Desktop(Context context1) {
        this.context = context1;
        mDesktop = LayoutInflater.from(context).inflate(R.layout.main, null);
        desktop_list = (ExpandableListView) mDesktop.findViewById(R.id.list);
        init_data();
        desktop_list.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // TODO Auto-generated method stub
                switch (groupPosition) {
                    case 0:
                        if (expandFlag == -1) {
                            // 展开被选的group
                            desktop_list.expandGroup(groupPosition);
                            // 设置被选中的group置于顶端
                            desktop_list.setSelectedGroup(groupPosition);
                            expandFlag = groupPosition;
                        } else if (expandFlag == groupPosition) {
                            desktop_list.collapseGroup(expandFlag);
                            expandFlag = -1;
                        } else {
                            desktop_list.collapseGroup(expandFlag);
                            // 展开被选的group
                            desktop_list.expandGroup(groupPosition);
                            // 设置被选中的group置于顶端
                            desktop_list.setSelectedGroup(groupPosition);
                            expandFlag = groupPosition;
                        }
                        break;
                    case 1:
                        if (expandFlag == -1) {
                            // 展开被选的group
                            desktop_list.expandGroup(groupPosition);
                            // 设置被选中的group置于顶端
                            desktop_list.setSelectedGroup(groupPosition);
                            expandFlag = groupPosition;
                        } else if (expandFlag == groupPosition) {
                            desktop_list.collapseGroup(expandFlag);
                            expandFlag = -1;
                        } else {
                            desktop_list.collapseGroup(expandFlag);
                            // 展开被选的group
                            desktop_list.expandGroup(groupPosition);
                            // 设置被选中的group置于顶端
                            desktop_list.setSelectedGroup(groupPosition);
                            expandFlag = groupPosition;
                        }
                        break;
                    case 2:
                        if (expandFlag == -1) {
                            // 展开被选的group
                            desktop_list.expandGroup(groupPosition);
                            // 设置被选中的group置于顶端
                            desktop_list.setSelectedGroup(groupPosition);
                            expandFlag = groupPosition;
                        } else if (expandFlag == groupPosition) {
                            desktop_list.collapseGroup(expandFlag);
                            expandFlag = -1;
                        } else {
                            desktop_list.collapseGroup(expandFlag);
                            // 展开被选的group
                            desktop_list.expandGroup(groupPosition);
                            // 设置被选中的group置于顶端
                            desktop_list.setSelectedGroup(groupPosition);
                            expandFlag = groupPosition;
                        }
                        break;
                    case 3:
                        if (expandFlag == -1) {
                            // 展开被选的group
                            desktop_list.expandGroup(groupPosition);
                            // 设置被选中的group置于顶端
                            desktop_list.setSelectedGroup(groupPosition);
                            expandFlag = groupPosition;
                        } else if (expandFlag == groupPosition) {
                            desktop_list.collapseGroup(expandFlag);
                            expandFlag = -1;
                        } else {
                            desktop_list.collapseGroup(expandFlag);
                            // 展开被选的group
                            desktop_list.expandGroup(groupPosition);
                            // 设置被选中的group置于顶端
                            desktop_list.setSelectedGroup(groupPosition);
                            expandFlag = groupPosition;
                        }
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        desktop_list.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                mChooesId = childPosition;
                mGroupId = groupPosition;
                adapter.notifyDataSetChanged();
                switch (groupPosition) {
                    case 0:
                        if (onChangeViewListener != null) {
                            switch (childPosition) {
                                case 0:
                                    onChangeViewListener.onChangeView(1);
                                    break;
                                case 1:
                                    onChangeViewListener.onChangeView(2);
                                    break;
                                case 2:
                                    onChangeViewListener.onChangeView(3);
                                    break;
                            }
                        }
                        break;
                    case 1:
                        if (onChangeViewListener != null) {
                            switch (childPosition) {
                                case 0:
                                    onChangeViewListener.onChangeView(4);
                                    break;
                                case 1:
                                    onChangeViewListener.onChangeView(5);
                                    break;
                                case 2:
                                    onChangeViewListener.onChangeView(6);
                                    break;
                            }
                        }
                        break;
                    case 2:
                        if (onChangeViewListener != null) {
                            switch (childPosition) {
                                case 0:
                                    onChangeViewListener.onChangeView(7);
                                    break;
                                case 1:
                                    onChangeViewListener.onChangeView(8);
                                    break;
                                case 2:
                                    onChangeViewListener.onChangeView(9);
                                    break;
                            }
                        }
                        break;
                    case 3:
                        if (onChangeViewListener != null) {
                            switch (childPosition) {
                                case 0:
                                    onChangeViewListener.onChangeView(10);
                                    break;
                                case 1:
                                    onChangeViewListener.onChangeView(11);
                                    break;
                                case 2:
                                    onChangeViewListener.onChangeView(12);
                                    break;
                            }
                        }
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    private void init_data() {
        HashMap<Integer, String[]> childs = new HashMap<Integer, String[]>();
        mGroupName = context.getResources().getStringArray(R.array.group_name);
        mGroupName2 = context.getResources().getStringArray(R.array.desktop_list_head_strings);

        group_name1_chlid = context.getResources().getStringArray(R.array.group_name1_chlid);
        group_name2_chlid = context.getResources().getStringArray(R.array.group_name2_chlid);
        group_name3_chlid = context.getResources().getStringArray(R.array.group_name3_chlid);
        group_name4_chlid = context.getResources().getStringArray(R.array.group_name4_chlid);
        mChildFavorite = context.getResources().getStringArray(R.array.desktop_list_item_favorite_strings);
        mChildAction = context.getResources().getStringArray(R.array.desktop_list_item_action_strings);
        childs.put(0, group_name1_chlid);
        childs.put(1, group_name2_chlid);
        childs.put(2, group_name3_chlid);
        childs.put(3, group_name4_chlid);
        childs.put(4, mChildFavoritesd);

        image_group_chlid = new int[3];
        mChildFavoriteIcon = new int[8];
        mChildActionIcon = new int[2];

        image_group_chlid[0] = R.drawable.menu_ico_a;
        image_group_chlid[1] = R.drawable.menu_ico_b;
        image_group_chlid[2] = R.drawable.menu_ico_c;
        mChildFavoriteIcon[0] = R.drawable.v5_0_1_desktop_list_newsfeed;
        mChildFavoriteIcon[1] = R.drawable.v5_0_1_desktop_list_message;
        mChildFavoriteIcon[2] = R.drawable.v5_0_1_desktop_list_chat;
        mChildFavoriteIcon[3] = R.drawable.v5_0_1_desktop_list_friends;
        mChildFavoriteIcon[4] = R.drawable.v5_0_1_desktop_list_page;
        mChildFavoriteIcon[5] = R.drawable.v5_0_1_desktop_list_location;
        mChildFavoriteIcon[6] = R.drawable.v5_0_1_desktop_list_search;
        mChildFavoriteIcon[7] = R.drawable.v5_0_1_desktop_list_apps_center;
        mChildActionIcon[0] = R.drawable.v5_0_1_desktop_list_settings;
        mChildActionIcon[1] = R.drawable.v5_0_1_desktop_list_log_out;

        for (int i = 0; i < mGroupName.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("icon", mChildFavoriteIcon[i]);
            map.put("name", mGroupName[i]);
            mGroup.add(map);
        }
        for (int j = 0; j < mChildFavorite.length; j++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("icon", mChildFavoriteIcon[j]);
            map.put("name", mChildFavorite[j]);
            mGroup.add(map);
        }
        Map<String, Object> map_oprate = new HashMap<String, Object>();
        map_oprate.put("name", mGroupName2[1]);
        //map_oprate.put("click", true);
        mGroup1.add(map_oprate);
        for (int i = 0; i < mGroupName2.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("icon", mChildActionIcon[i]);
            map.put("name", mChildAction[i]);
            mGroup.add(map);
        }
        for (int j = 0; j < mGroupName.length; j++) {
            String[] mChildFavoritename = childs.get(j);
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            for (int m = 0; m < mChildFavoritename.length; m++) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("icon", image_group_chlid[m]);//icon对应图片
                map.put("name", mChildFavoritename[m]);//name对应字符串
                map.put("click", false);
                list.add(map);
            }
            mChild1.add(list);
        }
        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("icon", image_group_chlid[0]);
        map2.put("name", childs.get(0)[0]);
        map2.put("click", false);
        mChild1.get(0).set(0, map2);

        adapter = new DesktopAdapter(context,mGroup1,mGroup, mChild1);
        desktop_list.setAdapter(adapter);
        // 默认打开第一个闭合其他的
        for (int i = 0; i < desktop_list.getChildCount(); i++) {
            //   desktop_list.collapseGroup(i);
        }
    }

    public View getView() {
        return mDesktop;
    }

    public interface OnChangeViewListener {
        public abstract void onChangeView(int args);
    }

    public void setOnChangeViewListener(
            OnChangeViewListener onChangeViewListener) {
        this.onChangeViewListener = onChangeViewListener;
    }
}
