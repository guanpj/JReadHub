package com.jeez.guanpj.jreadhub.widget.decoration;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jeez.guanpj.jreadhub.R;
import com.takwolf.android.hfrecyclerview.FixedViewHolder;
import com.takwolf.android.hfrecyclerview.HeaderAndFooterRecyclerView;

public class GapItemDecoration extends RecyclerView.ItemDecoration {

    private final int gapSize;

    public GapItemDecoration(@NonNull Context context) {
        gapSize = context.getResources().getDimensionPixelSize(R.dimen.gap_size);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int left = 0;
        int top = 0;
        int right = 0;
        int bottom = 0;

        int positionOffset;
        if (parent instanceof HeaderAndFooterRecyclerView) {
            positionOffset = ((HeaderAndFooterRecyclerView) parent).getProxyAdapter().getPositionOffset();
        } else {
            positionOffset = 0;
        }

        RecyclerView.ViewHolder holder = parent.findContainingViewHolder(view);
        if (holder != null && holder.getItemViewType() != FixedViewHolder.VIEW_TYPE_HEADER && holder.getItemViewType() != FixedViewHolder.VIEW_TYPE_FOOTER) {
            int position = holder.getAdapterPosition() - positionOffset;
            if (position != 0) {
                top = gapSize;
            }
        }

        outRect.set(left, top, right, bottom);
    }

}

