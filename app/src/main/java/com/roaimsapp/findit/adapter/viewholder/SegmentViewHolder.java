package com.roaimsapp.findit.adapter.viewholder;

import com.roaimsapp.findit.adapter.SegmentClickListener;
import com.roaimsapp.findit.data.model.Segment;
import com.roaimsapp.findit.databinding.ItemSegmentBinding;

/**
 * Created by Roaim on 22-Jul-18.
 */

public class SegmentViewHolder extends BaseViewHolder<ItemSegmentBinding> {

    public SegmentViewHolder(ItemSegmentBinding binding, SegmentClickListener mListener) {
        super(binding);
        binding.setListener(mListener);
    }

    public void bind(Segment segment) {
        binding.setSegment(segment);
        binding.executePendingBindings();
    }
}
