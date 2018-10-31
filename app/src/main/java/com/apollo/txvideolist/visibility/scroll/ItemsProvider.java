package com.apollo.txvideolist.visibility.scroll;


import com.apollo.txvideolist.visibility.items.ListItem;

/**
 * This interface is used by {@link com.apollo.txvideolist.visibility.calculator.SingleListViewItemActiveCalculator}.
 * Using this class to get {@link com.apollo.txvideolist.visibility.items.ListItem}
 */
public interface ItemsProvider {

    ListItem getListItem(int position);

    int listItemSize();

}
