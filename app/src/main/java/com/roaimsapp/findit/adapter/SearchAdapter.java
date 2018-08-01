package com.roaimsapp.findit.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.roaimsapp.findit.R;
import com.roaimsapp.findit.adapter.viewholder.NumberViewHolder;
import com.roaimsapp.findit.adapter.viewholder.SearchViewHolder;
import com.roaimsapp.findit.data.model.Number;
import com.roaimsapp.findit.databinding.ItemNumberBinding;
import com.roaimsapp.findit.databinding.ItemSearchBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roaim on 21-Jul-18.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder> {
    private List<Number> mList;
    private int length;

    public void setLength(int length) {
        this.length = length;
    }

    public SearchAdapter() {
        mList = new ArrayList<>();
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemSearchBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_search, parent, false);
        return new SearchViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        Number number = getNumber(position);
        holder.bind(number, length);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public Number getNumber(int position) {
        return mList.get(position);
    }

    public int getIndexOf(Number number) {
        return mList.indexOf(number);
    }

    public void update(Number number, int index) {
        mList.set(index, number);
    }

    public void update(int startIndex, Number... numbers) {
        for (int i = 0; i < numbers.length; i++) {
            int index = startIndex - 1 - i;
            mList.set(index, numbers[i]);
        }
    }

    public void update(Number... numbers) {
        for (int i = 0; i < numbers.length; i++) {
            int index = mList.size() - 1 - i;
            mList.set(index, numbers[i]);
        }
    }

    public void addAll(List<Number> list) {
        clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addNumber(Number number) {
        mList.add(number);
    }

    public void removeNumber(Number number) {
        mList.remove(number);
    }

    @Override
    public void onViewDetachedFromWindow(SearchViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.unBind();
    }

    public void clear() {
        if (!mList.isEmpty()) {
            mList.removeAll(mList);
        }
        notifyDataSetChanged();
    }
}
