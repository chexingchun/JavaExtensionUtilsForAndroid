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

package com.wonium.utils.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.View;
import android.view.ViewGroup;

import com.android.databinding.library.baseAdapters.BR;
import com.wonium.example.R;

/**
 * @ClassName: MainFuncationAdpter.java
 * @Description: 类描述
 * @Author: Wonium
 * @E-mail: wonium@qq.com
 * @Blog: https://blog.wonium.com
 * @CreateDate: 2018/11/17 14:10
 * @UpdateUser: update user
 * @UpdateDate: 2018/11/17 14:10
 * @UpdateDescription: 更新说明
 * @Version: 1.0.0
 */
public class MainFunctionAdapter extends BaseAdapter<String,MainFunctionAdapter.MainViewHolder> {

    public MainFunctionAdapter(Context context) {
        super(context);
    }

    @Override
    public MainViewHolder onCreateVH(ViewGroup parent, int viewType) {
        ViewDataBinding dataBinding=DataBindingUtil.inflate(getInflater(),R.layout.layout_main_item,parent,false);
        return new MainViewHolder(dataBinding);
    }

    @Override
    public void onBindVH(MainViewHolder holder, int position) {
        holder.getBinding().setVariable(BR.content,getDatas().get(position));
        holder.getBinding().getRoot().setOnClickListener(v -> onItemClickListener.onItemClick(v,position));

    }

    static class MainViewHolder extends BaseViewHolder<ViewDataBinding>{

        MainViewHolder(ViewDataBinding binding) {
            super(binding);
        }
    }
}
