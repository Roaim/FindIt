package com.roaimsapp.findit.adapter;

import android.support.v7.widget.RecyclerView;

import com.roaimsapp.findit.data.model.Segment;
import com.roaimsapp.findit.databinding.ItemSegmentBinding;

/**
 * Created by Roaim on 22-Jul-18.
 */

public class SegmentViewHolder extends RecyclerView.ViewHolder {
    private ItemSegmentBinding binding;

    public SegmentViewHolder(ItemSegmentBinding binding, SegmentClickListener mListener) {
        super(binding.getRoot());
        this.binding = binding;
        binding.setListener(mListener);
    }

    public void bind(Segment segment) {
        binding.setSegment(segment);
        binding.executePendingBindings();
    }

    public void unBind() {
        if (binding != null) {
            binding.unbind();
        }
    }
}
