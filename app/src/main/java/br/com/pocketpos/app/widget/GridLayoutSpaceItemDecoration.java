package br.com.pocketpos.app.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class GridLayoutSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public GridLayoutSpaceItemDecoration(int space) {

        this.space = space;

    }

    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {

        outRect.right = space;

        outRect.bottom = space;

        if (parent.getChildLayoutPosition(view) % 2 == 0) {

            outRect.left = 2;

        } else {

            outRect.left = 0;

        }

    }

}