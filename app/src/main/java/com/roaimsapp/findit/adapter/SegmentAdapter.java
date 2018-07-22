package com.roaimsapp.findit.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.roaimsapp.findit.R;
import com.roaimsapp.findit.adapter.viewholder.SegmentViewHolder;
import com.roaimsapp.findit.data.model.Segment;
import com.roaimsapp.findit.databinding.ItemSegmentBinding;

import java.util.List;

/**
 * Created by Roaim on 21-Jul-18.
 */

public class SegmentAdapter extends RecyclerView.Adapter<SegmentViewHolder> {
    private List<Segment> mList;
    private SegmentClickListener mListener;

    public SegmentAdapter(List<Segment> list, SegmentClickListener listener) {
        mList = list;
        this.mListener = listener;
    }

    @Override
    public SegmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemSegmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_segment, parent, false);
        return new SegmentViewHolder(binding, mListener);
    }

    @Override
    public void onBindViewHolder(SegmentViewHolder holder, int position) {
        Segment segment = getSegment(position);
        holder.bind(segment);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public Segment getSegment(int position) {
        return mList.get(position);
    }

    public void addAllSegments(List<Segment> list) {
        if (!mList.isEmpty()) {
            mList.removeAll(mList);
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addSegment(Segment segment) {
        mList.add(segment);
        notifyDataSetChanged();
    }

    public void removeSegment(Segment segment) {
        mList.remove(segment);
        notifyDataSetChanged();
    }

    @Override
    public void onViewDetachedFromWindow(SegmentViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.unBind();
    }
}
