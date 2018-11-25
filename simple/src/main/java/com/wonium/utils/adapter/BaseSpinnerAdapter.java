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
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * @ClassName: BaseSpinnerAdapter.java
 * @Description: 下拉列表的公共基类
 * @Author: Wonium
 * @E-mail: wonium@qq.com
 * @Blog: https://blog.wonium.com
 * @CreateDate: 2018/11/17 10:15
 * @UpdateUser: update user
 * @UpdateDate: 2018/11/17 10:15
 * @UpdateDescription: 更新说明
 * @Version: 1.0.0
 */
public abstract class BaseSpinnerAdapter<T> extends BaseAdapter {
    private List<T> datas;
    private LayoutInflater inflater;

    public BaseSpinnerAdapter(List<T> datas) {
        this.datas = datas;
    }

    /**
     * 创建ViewHolder对象
     * @param position 点击ItemView
     * @param parent ViewGroup
     * @return ViewHolder对象
     */
    protected abstract ViewHolder onCreateViewHolder(int position, ViewGroup parent);

    /**
     * 数据绑定到ViewHolder上
     * @param position 位置
     * @param holder ViewHolder对象 用来获取Item中的View
     */
    protected abstract void onBindViewHolder(int position, ViewHolder holder);

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public T getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 获取Spinner 默认显示的View
     * @param position 位置
     * @param contentView
     * @param viewGroup
     * @return
     */
    @Override
    public View getView(int position, View contentView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (inflater == null) {
            inflater = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (contentView == null) {
            holder = onCreateViewHolder(position, viewGroup);
            contentView = holder.getBinding().getRoot();
            contentView.setTag(holder);
        } else {
            holder = (ViewHolder) contentView.getTag();
        }
        onBindViewHolder(position, holder);
        return contentView;
    }

    /**
     * 获取Spinner点击展开的View
     * @param position
     * @param contentView
     * @param parent
     * @return
     */
    @Override
    public View getDropDownView(int position, View contentView, ViewGroup parent) {
        ViewHolder holder;
        if (inflater == null) {
            inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (contentView == null) {
            holder = onCreateViewHolder(position, parent);
            contentView = holder.getBinding().getRoot();
            contentView.setTag(holder);
        } else {
            holder = (ViewHolder) contentView.getTag();
        }
        onBindViewHolder(position, holder);
        return contentView;
    }

    /**
     * ViveHolder内部类
     */
    public static final class ViewHolder {
        private ViewDataBinding binding;

        public ViewHolder(ViewDataBinding binding) {
            this.binding = binding;
        }

        public ViewDataBinding getBinding() {
            return binding;
        }
    }

    /**
     * 获取当前的LayoutInflater 对象
     * @return LayoutInflater 的对象
     * @throws NullPointerException 当inflater没有赋值的情况下会报空指针异常
     */
    public LayoutInflater getInflater() throws NullPointerException {
        return inflater;
    }

    /**
     * 添加一个泛型数据，并通知Adapter 刷新数据
     * @param data
     */
    public void addData(T data) {
        this.datas.add(data);
        notifyDataSetChanged();
    }

    /**
     * 追加一个数据集合，并通知Adapter刷新数据
     * @param datas 一个数据泛型集合
     */
    public void addAllData(List<T> datas) {
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    /**
     * 根据点击位置 删除指定的data
     * @param position Adapter中点击的Item位置
     */
    public void removeDataByPosition(int position) {
        this.datas.remove(position);
        notifyDataSetChanged();
    }

    /**
     * 设置数据源
     * @param datas 数据源 如果datas 有数据 则会覆盖之前的数据。如果datas为空，则会赋值。 datas是一个List泛型集合。
     */
    public void setDatas(List<T> datas){
        this.datas=datas;
        notifyDataSetChanged();
    }

    /**
     * 获取所有的数据
     * @return 一个List泛型集合
     */
    public List<T> getAllDatas() {
        return this.datas;
    }


}
