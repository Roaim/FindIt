package com.roaimsapp.findit.adapter;

import com.roaimsapp.findit.data.model.Segment;

/**
 * Created by Roaim on 22-Jul-18.
 */

public interface SegmentClickListener {
    void onSegmentClick(Segment segment);
    void onSegmentDeleteClick(Segment segment);
}
