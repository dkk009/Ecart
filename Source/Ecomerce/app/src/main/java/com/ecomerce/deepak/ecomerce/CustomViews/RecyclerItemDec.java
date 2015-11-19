package com.ecomerce.deepak.ecomerce.CustomViews;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ecomerce.deepak.ecomerce.EComerceApp;
import com.ecomerce.deepak.ecomerce.R;
import com.ecomerce.deepak.ecomerce.utilities.CommonUtility;

/**
 *
 */
public class RecyclerItemDec extends RecyclerView.ItemDecoration{



        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            //  super.onDraw(c, parent, state);
            int paddingLeft = parent.getPaddingLeft() + (int)CommonUtility.convertDpToPixel(5);
            int right = parent.getWidth() - (int)CommonUtility.convertDpToPixel(5);
            int childCount = parent.getChildCount();
            Paint paint = new Paint();
            paint.setColor(ContextCompat.getColor(EComerceApp.getInstance(),R.color.grey177));
            int height = 0;
            for (int i = 0; i < childCount; i++) {
                View view = parent.getChildAt(i);
                height = height + view.getHeight();
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();
                int top = view.getBottom() + params.bottomMargin;
                float bottom;
                bottom = top + CommonUtility.convertDpToPixel(1);
                c.drawRect(paddingLeft, top, right, bottom, paint);
            }
            parent.getLayoutParams().height = height;
        }
}
