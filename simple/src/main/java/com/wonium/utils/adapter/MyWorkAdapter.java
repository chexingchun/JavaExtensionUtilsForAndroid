/*
 * Copyright  2018.  wonium
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.wonium.utils.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wonium.example.R;
import com.wonium.utils.beans.Work;
import com.wonium.utils.beans.WorkChild;
import com.wonium.utils.beans.WorkGroup;

import java.util.List;

public class MyWorkAdapter extends BaseExpandableListAdapter {

    private List<Work> mList;
    private LayoutInflater mLayoutInflater;

    public MyWorkAdapter(Context context, List<Work> list) {

        mLayoutInflater = LayoutInflater.from(context);
        mList = list;
    }

    class GroupViewHolder {
        // Group内部类缓存
        ImageView groupImage;
        TextView groupTitle;
        ImageView groupIndicator;

    }

    class ChildViewHolder {
        // Child内部类缓存
        ImageView childImage;
        TextView childTitle;
    }

    @Override
    public int getGroupCount() {
        // 返回表头的数量,即List<Work>的长度
        return mList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        // 返回当前组内有表体数量，即每一组中List<WorkChild>的长度
        return mList.get(groupPosition)// 得到当前的组Work
                .getmWorkChildList().size();// 得到List<WorkChild>的长度
    }

    @Override
    public Object getGroup(int groupPosition) {
        // 返回当前组的表头，即WorkGroup
        return mList.get(groupPosition)// 得到当前的组Work
                .getmWorkGroup();// 得到WorkGroup
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        // 返回当前组的当前表体项，即WorkChild
        return mList.get(groupPosition)// 得到当前的组Work
                .getmWorkChildList()// 得到List<WorkChild>
                .get(childPosition);// 得到WorkChild
    }

    @Override
    public long getGroupId(int groupPosition) {
        // 返回当前表头Id
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        // 返回当前表体Id
        return childPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        // 返回表头View
        GroupViewHolder mGroupViewHolder = null;
        if (convertView == null) {
            mGroupViewHolder = new GroupViewHolder();
            // 内部类绑定相应控件
            convertView = mLayoutInflater.inflate(R.layout.work_groups, parent,
                    false);
            mGroupViewHolder.groupImage = (ImageView) convertView
                    .findViewById(R.id.group_image1);
            mGroupViewHolder.groupTitle = (TextView) convertView
                    .findViewById(R.id.group_title);
            mGroupViewHolder.groupIndicator = (ImageView) convertView
                    .findViewById(R.id.group_image2);
            // 内部类绑定当前载入的convertView
            convertView.setTag(mGroupViewHolder);
        } else {
            mGroupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        // 当前convertView的数据源WorkGroup
        WorkGroup mWorkGroup = (WorkGroup) getGroup(groupPosition);
        // 设定控件资源
        mGroupViewHolder.groupImage.setBackgroundResource(mWorkGroup
                .getImage1Group());
        mGroupViewHolder.groupTitle.setText(mWorkGroup.getTitleGroup());
        // 表头的open和close设置不同image
        if (isExpanded) {
            mGroupViewHolder.groupIndicator
                    .setBackgroundResource(R.drawable.ic_launcher_foreground);
        } else {
            mGroupViewHolder.groupIndicator
                    .setBackgroundResource(R.drawable.ic_launcher_background);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        // 返回表体View,具体内容同getGroupView一样
        ChildViewHolder mChildViewHolder = null;
        if (convertView == null) {
            mChildViewHolder = new ChildViewHolder();
            // 内部类绑定相应控件
            convertView = mLayoutInflater.inflate(R.layout.work_childs, parent,
                    false);
            mChildViewHolder.childImage = (ImageView) convertView
                    .findViewById(R.id.child_image1);
            mChildViewHolder.childTitle = (TextView) convertView
                    .findViewById(R.id.child_title);
            // 内部类绑定当前载入的convertView
            convertView.setTag(mChildViewHolder);
        } else {
            mChildViewHolder = (ChildViewHolder) convertView.getTag();
        }
        // 当前convertView的数据源WorkChild
        WorkChild mWorkChild = (WorkChild) getChild(groupPosition,
                childPosition);
        // 设定控件资源
        mChildViewHolder.childImage.setBackgroundResource(mWorkChild
                .getImageChild());
        mChildViewHolder.childTitle.setText(mWorkChild.getTitleChild());
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        // 是否指定分组视图及其子视图的Id对应的后台数据改变也会保持该Id
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // 指定位置的子视图是否可选择
        return true;
    }

}
