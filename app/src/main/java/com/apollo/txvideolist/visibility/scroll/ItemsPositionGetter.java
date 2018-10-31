package com.apollo.txvideolist.visibility.scroll;

import android.view.View;

/**
 * This class is an API for {@link com.apollo.txvideolist.visibility.calculator.SingleListViewItemActiveCalculator}
 * Using this class is can access all the data from RecyclerView / ListView
 * <p>
 * There is two different implementations for ListView and for RecyclerView.
 * RecyclerView introduced LayoutManager that's why some of data moved there
 */
public interface ItemsPositionGetter {
    View getChildAt(int position);

    int indexOfChild(View view);

    int getChildCount();

    int getLastVisiblePosition();

    int getFirstVisiblePosition();
}
