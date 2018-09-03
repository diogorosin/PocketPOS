package br.com.pocketpos.app.widget;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Diogo on 31/10/2017.
 */

public class SquareImageView extends android.support.v7.widget.AppCompatImageView {

    public SquareImageView(Context context) {

        super(context);

    }

    public SquareImageView(Context context, AttributeSet attrs) {

        super(context, attrs);

    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, widthMeasureSpec);

        int width = getMeasuredWidth();

        int height = getMeasuredHeight();

        if (width != height) {

            setMeasuredDimension(width, width);

        }

    }

}