package com.nucyzh.flipperactivity.activity_test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nucyzh.R;

import java.util.List;
import java.util.Map;

public class DesktopAdapter extends BaseExpandableListAdapter {

	private Context context;
	private List<Map<String,Object>> mGroup;
	private List<List<Map<String,Object>>> mChild;
	private LayoutInflater inflater;

	public DesktopAdapter(Context context, List<Map<String, Object>> mGroup,
			List<List<Map<String, Object>>> mChild) {
		super();
		this.context = context;
		this.mGroup = mGroup;
		this.mChild = mChild;
		inflater = LayoutInflater.from(context);
	}
	private class ViewHolder{
		private TextView mGroupName;
		private TextView mChildName;
		private ImageView mChildIcon;
	}
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return mChild.get(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.desktop_list_child, null);
			viewHolder = new ViewHolder();
			viewHolder.mChildName = (TextView)convertView.findViewById(R.id.desktop_list_child_name);
			viewHolder.mChildIcon = (ImageView) convertView.findViewById(R.id.desktop_list_child_icon);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		viewHolder.mChildName.setText(mChild.get(groupPosition).get(childPosition).get("name").toString());
		viewHolder.mChildIcon.setImageDrawable(context.getResources().getDrawable(
				Integer.parseInt(mChild.get(groupPosition).get(childPosition).get("icon").toString())));
		viewHolder.mChildName.setTextColor(context.getResources().getColor(R.color.button_unselected));
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return mChild.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return mGroup.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return mGroup.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if(convertView==null){
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.desktop_list_group, null);
			holder.mGroupName = (TextView) convertView.findViewById(R.id.desktop_list_group_name);
			convertView.setTag(holder);
		}else{
			holder= (ViewHolder)convertView.getTag();
		}
		holder.mGroupName.setText(mGroup.get(groupPosition).get("name").toString());
	
//		convertView.setBackgroundResource(R.drawable.desktop_list_item_bg);
		
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

}
