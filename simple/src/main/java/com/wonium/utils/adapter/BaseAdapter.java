/*
 * Copyright  2018  WoNium，Joy，Lokiwife
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
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: BaseAdapter.java
 * @Description: Adapter的公共基类
 * @Author: Wonium
 * @E-mail: wonium@qq.com
 * @Blog: https://blog.wonium.com
 * @CreateDate: 2018/11/17 10:15
 * @UpdateUser: update user
 * @UpdateDate: 2018/11/17 10:15
 * @UpdateDescription: 更新说明
 * @Version: 1.0.0
 */
public abstract class BaseAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    private List<T> datas;
    private LayoutInflater inflater;
    private Context context;
    public OnItemClickListener onItemClickListener;


    /**
     * 设置Item得点击事件
     *
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }



    /**
     * Item 点击接口
     */
    public interface OnItemClickListener {
        /**
         * Item 点击事件
         * @param view 被点击的View
         * @param position 在列表中的位置
         */
        void onItemClick(View view,int position);
    }



    public BaseAdapter(Context context) {
        this.datas = new ArrayList<>();
        this.context =context;
        inflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     *
     * @return
     */
    public Context getContext() {
        return context;
    }

    /**
     * 获取LayoutInflater对象
     * @return
     */
    public LayoutInflater getInflater() {
        return inflater;
    }

    /**
     * 设置LayoutInflater
     * @param inflater LayoutInflater对象
     */
    private void setInflater(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return onCreateVH(parent, viewType);
    }

    /**
     * 创建ViewHolder
     * @param parent
     * @param viewType View雷西那个
     * @return ViewHolder
     */
    public abstract VH onCreateVH(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        onBindVH(holder, position);
    }

    /**
     * 绑定ViewHolder
     * @param holder ViewHolder对象
     * @param position 位置
     */
    public abstract void onBindVH(VH holder, int position);


    @Override
    public int getItemCount() {
        return datas.size();
    }

    /**
     * 加载更多
     *
     * @param data 加载的新数据
     */
    public void loadMoreData(List<T> data) {
        this.datas.addAll(data);
        notifyDataSetChanged();
    }

    /**
     * 刷新数据
     *
     * @param data 数据源
     */
    public void refreshData(List<T> data) {
        this.datas.clear();
        this.datas.addAll(data);
        notifyDataSetChanged();
    }

    /**
     * 获取所有的数据
     * @return datas
     */
    public List<T> getDatas() {
        return datas;
    }


    /**
     * 添加一个泛型对象到集合中
     * @param data 泛型对象
     */
    public void addData(T data) {
        this.datas.add(data);
        notifyDataSetChanged();
    }

    /**
     * 追加一个泛型集合到datas集合中
     * @param datas
     */
    public void addAll(List<T> datas) {
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    /**
     * 设置数据，并通知刷新列表
     * @param datas
     */
    public void setDatas(List<T> datas){
        this.datas=datas;
        notifyDataSetChanged();
    }

}
