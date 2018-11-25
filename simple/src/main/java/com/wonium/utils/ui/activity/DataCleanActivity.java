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

import android.databinding.DataBindingUtil;

import com.wonium.example.R;
import com.wonium.example.databinding.ActivityDataCleanBinding;
import com.wonium.extension.utils.DataCleanUtil;
import com.wonium.extension.utils.StringUtil;

/**
 * @ClassName: AcacheActivity.java
 * @Description: 类描述
 * @Author: Wonium
 * @E-mail: wonium@qq.com
 * @Blog: https://blog.wonium.com
 * @CreateDate: 2018/11/17 15:25
 * @UpdateUser: update user
 * @UpdateDate: 2018/11/17 15:25
 * @UpdateDescription: 更新说明
 * @Version: 1.0.0
 */
public class DataCleanActivity extends BaseActivity {
    private ActivityDataCleanBinding binding;

    @Override
    public void initView(int layoutResID) {
        binding = DataBindingUtil.setContentView(this, layoutResID);
        setSupportActionBar(binding.includeLayoutAcacheToolbar.toolbar);
        binding.setTitle(getString(R.string.item_data_clean));
    }

    @Override
    public void initListener() {
        binding.btnGetAcache.setOnClickListener(v -> {
            try {
                binding.tvAcacheResult.setText(StringUtil.INSTANCE.isEmpty(DataCleanUtil.INSTANCE.getTotalCacheSize(this)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        binding.btnCleanAcache.setOnClickListener(v -> binding.tvAcacheResult.setText(StringUtil.INSTANCE.isEmpty(DataCleanUtil.INSTANCE.clearAllCache(this) + "KB")));
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_data_clean;
    }
}
