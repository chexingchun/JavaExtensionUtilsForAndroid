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

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.wonium.example.R;
import com.wonium.extension.utils.IntentUtil;
import com.wonium.extension.utils.StringUtil;
import com.wonium.extension.utils.ToastUtil;
import com.wonium.utils.adapter.BaseAdapter;
import com.wonium.utils.adapter.MainFunctionAdapter;
import com.wonium.utils.presenter.MainPresenter;
import com.wonium.utils.presenter.impl.MainPresenterImpl;
import com.wonium.utils.ui.view.MainView;

import java.util.List;

import ru.alexbykov.nopermission.PermissionHelper;

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
    private PermissionHelper permissionHelper;

    private MainPresenter mPresenter;
    private RecyclerView mRecyclerView;
    private MainFunctionAdapter mAdapter;

    @Override
    public void initView(int layoutResID) {
        setContentView(layoutResID);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mRecyclerView = findViewById(R.id.recyclerView);
        mAdapter = new MainFunctionAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        mPresenter = new MainPresenterImpl(this);

        permissionHelper = new PermissionHelper(this);
        mPresenter.getListData(this);
        getWifiSSid();
    }

    @Override
    public void initListener() {
        mAdapter.setOnItemClickListener((view, position) -> {
            ToastUtil.INSTANCE.show(this, StringUtil.INSTANCE.valueOf(position));
            switch (position) {
                case 0:
                    break;
                case 1:
                case 2:
                case 3:
                case 4:
                    IntentUtil.INSTANCE.toActivity(this, DataCleanActivity.class);
                    break;
                case 5:
                case 6:
                case 7:
                    IntentUtil.INSTANCE.toActivity(this,DeviceActivity.class);
                    break;
                case 8:
                case 9:
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

    private void getWifiSSid() {
        permissionHelper.check(Manifest.permission.ACCESS_FINE_LOCATION).onSuccess(this::onSuccess).onDenied(this::onDenied).onNeverAskAgain(this::onNeverAskAgain).run();
    }

    private void onSuccess() {

        //        mTvSSID.setText(DeviceUtil.INSTANCE.getWIFISSID(this));
    }

    private void onDenied() {
        ToastUtil.INSTANCE.show(this, "权限被拒绝，9.0系统无法获取SSID");
    }

    private void onNeverAskAgain() {
        ToastUtil.INSTANCE.show(this, "权限被拒绝，9.0系统无法获取SSID,下次不会在询问了");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void updateListData(List<String> datas) {
        mAdapter.setDatas(datas);
    }

}
