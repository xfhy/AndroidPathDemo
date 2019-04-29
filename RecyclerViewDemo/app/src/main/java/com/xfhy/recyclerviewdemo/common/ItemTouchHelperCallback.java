package com.xfhy.recyclerviewdemo.common;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.xfhy.recyclerviewdemo.StringDataAdapter;

import java.util.Collections;

/**
 * Created by feiyang on 2019-04-29 16:25
 * Description :
 */
public class ItemTouchHelperCallback extends ItemTouchHelper.Callback {

    /**
     * 给句返回值来设置是否处理某次拖拽或者滑动事件
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
//        如果是列表类型的 RecyclerView，拖拽只有 UP、DOWN 两个方向
//        如果是网格类型的则有 UP、DOWN、LEFT、RIGHT 四个方向
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int dragFlags = 0;
        int swipeFlags = 0;
        if (layoutManager instanceof GridLayoutManager) {
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        } else if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            if (linearLayoutManager.getOrientation() == LinearLayoutManager.VERTICAL) {
                //垂直
                dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            } else {
                //水平
                dragFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            }
        } else {
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        }
        //创建flags的快捷方法
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    /**
     * 当长按并进入拖拽状态时,拖拽的过程中不断的回调此方法
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        //拖动的item的下标
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        StringDataAdapter stringDataAdapter = (StringDataAdapter) adapter;
        int fromPosition = viewHolder.getAdapterPosition();
        int toPosition = target.getAdapterPosition();
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                //交换数据
                Collections.swap(stringDataAdapter.getStringList(), i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(stringDataAdapter.getStringList(), i, i - 1);
            }
        }
        stringDataAdapter.notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    /**
     * 滑动删除的回调
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }
}
