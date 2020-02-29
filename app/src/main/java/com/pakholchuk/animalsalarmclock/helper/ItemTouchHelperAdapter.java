package com.pakholchuk.animalsalarmclock.helper;

public interface ItemTouchHelperAdapter {
    boolean onItemMoved(int fromPosition, int toPosition);
    void onItemDismissed(int position);
}
