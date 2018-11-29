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

package com.wonium.utils.ui.activity;


import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ExpandableListView;

import com.wonium.example.R;
import com.wonium.utils.adapter.MyWorkAdapter;
import com.wonium.utils.beans.Work;
import com.wonium.utils.beans.WorkChild;
import com.wonium.utils.beans.WorkGroup;

import java.util.ArrayList;
import java.util.List;

public class ExpandListActivity extends Activity {
    private ExpandableListView mExpandableListView;
    private MyWorkAdapter mMyWorkAdapter;
    // 数据源
    private List<Work> mWorkList;
    private Work mWork;
    private WorkGroup mWorkGroup;
    private List<WorkChild> mWorkChildList;
    private WorkChild mWorkChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_expand_list);
        // 初始化数据
        init();
        mExpandableListView = findViewById(R.id.expand);
        // 去除默认的指示器
        mExpandableListView.setGroupIndicator(null);
        mMyWorkAdapter = new MyWorkAdapter(getApplicationContext(), mWorkList);
        mExpandableListView.setAdapter(mMyWorkAdapter);
    }

    private void init() {
        mWorkList = new ArrayList<>();
        // 后端开发
        mWork = new Work();
        // 表头
        mWorkGroup = new WorkGroup();
        mWorkGroup.setImage1Group(R.drawable.ic_launcher_background);
        mWorkGroup.setTitleGroup("后端开发");
        mWork.setmWorkGroup(mWorkGroup);
        // 表体
        mWorkChildList = new ArrayList<>();
        mWorkChild = new WorkChild();// Java
        mWorkChild.setImageChild(R.drawable.ic_launcher_background);
        mWorkChild.setTitleChild("Java");
        mWorkChildList.add(mWorkChild);
        mWorkChild = new WorkChild();// PHP
        mWorkChild.setImageChild(R.drawable.ic_launcher_background);
        mWorkChild.setTitleChild("PHP");
        mWorkChildList.add(mWorkChild);
        mWorkChild = new WorkChild();// Python
        mWorkChild.setImageChild(R.drawable.ic_launcher_background);
        mWorkChild.setTitleChild("Python");
        mWorkChildList.add(mWorkChild);
        mWorkChild = new WorkChild();// C#
        mWorkChild.setImageChild(R.drawable.ic_launcher_background);
        mWorkChild.setTitleChild("C#");
        mWorkChildList.add(mWorkChild);
        mWorkChild = new WorkChild();// C++
        mWorkChild.setImageChild(R.drawable.ic_launcher_background);
        mWorkChild.setTitleChild("C++");
        mWorkChildList.add(mWorkChild);
        mWorkChild = new WorkChild();// C
        mWorkChild.setImageChild(R.drawable.ic_launcher_background);
        mWorkChild.setTitleChild("C");
        mWorkChildList.add(mWorkChild);
        mWorkChild = new WorkChild();// 后端开发其他
        mWorkChild.setImageChild(R.drawable.ic_launcher_background);
        mWorkChild.setTitleChild("后端开发其他");
        mWorkChildList.add(mWorkChild);
        mWork.setmWorkChildList(mWorkChildList);
        mWorkList.add(mWork);
        // 移动开发
        mWork = new Work();
        mWorkGroup = new WorkGroup();
        mWorkGroup.setImage1Group(R.drawable.ic_launcher_foreground);
        mWorkGroup.setTitleGroup("移动开发");
        mWork.setmWorkGroup(mWorkGroup);
        mWorkChildList = new ArrayList<>();
        mWorkChild = new WorkChild();// IOS
        mWorkChild.setImageChild(R.drawable.ic_launcher_foreground);
        mWorkChild.setTitleChild("IOS");
        mWorkChildList.add(mWorkChild);
        mWorkChild = new WorkChild();// Android
        mWorkChild.setImageChild(R.drawable.ic_launcher_foreground);
        mWorkChild.setTitleChild("Android");
        mWorkChildList.add(mWorkChild);
        mWorkChild = new WorkChild();// WP
        mWorkChild.setImageChild(R.drawable.ic_launcher_foreground);
        mWorkChild.setTitleChild("WP");
        mWorkChildList.add(mWorkChild);
        mWorkChild = new WorkChild();// 移动开发其他
        mWorkChild.setImageChild(R.drawable.ic_launcher_foreground);
        mWorkChild.setTitleChild("移动开发其他");
        mWorkChildList.add(mWorkChild);
        mWork.setmWorkChildList(mWorkChildList);
        mWorkList.add(mWork);

    }

}
