package com.blueinklabs.taxassistantpakistan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ExpandListAdapter extends BaseExpandableListAdapter {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_ITEM_DISPLAY = 1;
    private static final int TYPE_MAX_COUNT = TYPE_ITEM_DISPLAY + 1;

    private Context context;
    private ArrayList<Group> groups;
    private LayoutInflater mInflater;

    public ExpandListAdapter(Context context, ArrayList<Group> groups) {
        this.context = context;
        this.groups = groups;
        mInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<Child> chList = groups.get(groupPosition).getItems();
        return chList.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        Child child = (Child) getChild(groupPosition, childPosition);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.child_item_standard, null);
        }

        TextView tv = (TextView) convertView.findViewById(R.id.country_name);
        ImageView iv = (ImageView) convertView.findViewById(R.id.flag);
        TextView tvAmount = (TextView) convertView.findViewById(R.id.entered_amount);

        tv.setText(child.getName().toString());
        iv.setImageResource(child.getImage());
        tvAmount.setText(child.getEnteredAmount());

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        ArrayList<Child> chList = groups.get(groupPosition).getItems();
        return chList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        Group group = (Group) getGroup(groupPosition);
        ViewHolder holder = null;
        int typeGroup = getGroupType(groupPosition);
        if (convertView == null) {
            holder = new ViewHolder();
            switch (typeGroup) {
                case TYPE_ITEM:
                    convertView = mInflater.inflate(R.layout.group_item, null);
                    holder.tv = (TextView) convertView.findViewById(R.id.group_name);
                    holder.tvPKR = (TextView) convertView.findViewById(R.id.group_PKR);
                    holder.iv = (ImageView) convertView.findViewById(R.id.group_icon_item);
                    break;
                case TYPE_ITEM_DISPLAY:
                    convertView = mInflater.inflate(R.layout.group_item_display, null);
                    holder.tvDisplay = (TextView) convertView.findViewById(R.id.group_name_display);
                    holder.tvPKRDisplay = (TextView) convertView.findViewById(R.id.group_PKR_display);
                    break;
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        switch (typeGroup) {
            case TYPE_ITEM:
                holder.tv.setText(group.getName());
                holder.tvPKR.setText(group.getHeadingResult());
                if (isExpanded) {
                    holder.iv.setImageResource(R.drawable.up);
                } else {
                    holder.iv.setImageResource(R.drawable.down);
                }
                break;
            case TYPE_ITEM_DISPLAY:
                holder.tvDisplay.setText(group.getName());
                holder.tvPKRDisplay.setText(group.getHeadingResult());
                break;
        }
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    /*
    @Override
    public int getChildType(int groupPosition, int childPosition){

    }

    public int getChildTypeCount(){

    }
    */
    @Override
    public int getGroupType(int groupPosition) {
        switch (groupPosition) {
            case 0:
                return TYPE_ITEM;
            case 1:
                return TYPE_ITEM;
            case 2:
                return TYPE_ITEM;
            case 3:
                return TYPE_ITEM;
            case 4:
                return TYPE_ITEM;
            case 5:
                return TYPE_ITEM;
            default:
                break;
        }
        return TYPE_ITEM;
    }

    @Override
    public int getGroupTypeCount() {
        return TYPE_MAX_COUNT;
    }

    public static class ViewHolder {
        public TextView tv;
        public TextView tvPKR;
        public ImageView iv;
        public TextView tvDisplay;
        public TextView tvPKRDisplay;
    }

}
