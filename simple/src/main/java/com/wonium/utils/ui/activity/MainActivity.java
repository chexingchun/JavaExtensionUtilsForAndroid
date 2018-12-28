/*
 * Copyright  2018  WoNium, Joy, Lokiwife.
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
 */

package com.wonium.utils.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.wonium.example.R;
import com.wonium.extension.utils.IntentUtil;
import com.wonium.extension.utils.StringUtil;
import com.wonium.extension.utils.ToastUtil;
import com.wonium.utils.adapter.MainFunctionAdapter;
import com.wonium.utils.presenter.MainPresenter;
import com.wonium.utils.presenter.impl.MainPresenterImpl;
import com.wonium.utils.ui.view.MainView;

import java.util.List;


/**
 * @ClassName: MainActivity.java
 * @Description: 类描述
 * @Author: Wonium
 * @E-mail: wonium@qq.com
 * @Blog: https://blog.wonium.com
 * @CreateDate: 2018/11/11 12:21
 * @UpdateUser: 更新者
 * @UpdateDate: 2018/11/11 12:21
 * @UpdateDescription: 更新说明
 * @Version: 1.0.0
 */

public class MainActivity extends BaseActivity implements MainView {

    private MainFunctionAdapter mAdapter;

    @Override
    public void initView(int layoutResID) {
        setContentView(layoutResID);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        RecyclerView mRecyclerView = findViewById(R.id.recyclerView);
        mAdapter = new MainFunctionAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        MainPresenter mPresenter = new MainPresenterImpl(this);
        mPresenter.getListData(this);
    }

    @Override
    public void initListener() {
        mAdapter.setOnItemClickListener((view, position) -> {
            ToastUtil.INSTANCE.show(this, StringUtil.INSTANCE.valueOf(position));
            switch (position) {
                case 0:
                    IntentUtil.INSTANCE.toActivity(this, ActivityManagerActivity.class);
                    break;
                case 1:
                    IntentUtil.INSTANCE.toActivity(this,BitmapActivity.class);
                    break;
                case 2:
                    IntentUtil.INSTANCE.toActivity(this,ByteActivity.class);
                    break;
                case 3:
                case 4:
                    IntentUtil.INSTANCE.toActivity(this, DataCleanActivity.class);
                    break;

                case 5:
                    IntentUtil.INSTANCE.toActivity(this,DateActivity.class);
                    break;
                case 6:
                    break;
                case 7:
                    IntentUtil.INSTANCE.toActivity(this, DeviceActivity.class);
                    break;
                case 8:
                case 9:
                    IntentUtil.INSTANCE.toActivity(this,FileActivity.class);
                    break;
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                    IntentUtil.INSTANCE.toActivity(this, StringActivity.class);
                    break;
                case 17:

                    break;
                default:
                    break;
            }
        });
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }


    @Override
    public void updateListData(List<String> datas) {
        mAdapter.setDatas(datas);
    }

}
