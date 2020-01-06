package client.example.com.server.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

import client.example.com.server.R;
import client.example.com.server.customView.CircleImageView;

public class ExpandableListViewAdapter extends BaseExpandableListAdapter {

    LayoutInflater inflater;
    String[] group = new String[]{"Servers"};
    String[][] childs = new String[][]{{"Server1", "Server2", "Server3", "Server4", "Server5"}};

    int[] headersId = {R.mipmap.iuser001, R.mipmap.iuser002, R.mipmap.iuser003, R.mipmap.iuser004, R.mipmap.iuser005, R.mipmap.iuser006};
    String[] child_infos = {"Hey, I'm online", "I'm busy", "", "", "", ""};
    String[] child_newworks = {"", "", "", "", "", ""};

    public ExpandableListViewAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return group.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childs[groupPosition].length;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return group[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childs[groupPosition][childPosition];
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    /* The layout of GroupView */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_elv_group, null);

        ImageView iv_group_icon = (ImageView) view.findViewById(R.id.iv_group_icon);
        TextView tv_group_name = (TextView) view.findViewById(R.id.tv_group_name);
        TextView tv_group_number = (TextView) view.findViewById(R.id.tv_group_number);

        tv_group_name.setText(group[groupPosition]);
        tv_group_number.setText(childs[groupPosition].length + "/" + childs[groupPosition].length);

        if (isExpanded) {
            iv_group_icon.setImageResource(R.mipmap.arrow_down);
        } else {
            iv_group_icon.setImageResource(R.mipmap.arrow_right);
        }

        return view;
    }

    /* The layout of ChildView */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_elv_child, null);

        int seed = new Random().nextInt(5) + 1;

        CircleImageView iv_child_icon = (CircleImageView) view.findViewById(R.id.iv_child_icon);
        iv_child_icon.setImageResource(headersId[seed]);

        TextView tv_child_info = (TextView) view.findViewById(R.id.tv_child_info);
        tv_child_info.setText(child_infos[seed]);

        TextView tv_child_name = (TextView) view.findViewById(R.id.tv_child_name);
        TextView tv_child_network = (TextView) view.findViewById(R.id.tv_child_network);

        tv_child_name.setText(childs[groupPosition][childPosition]);
        tv_child_network.setText(child_newworks[seed]);
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}