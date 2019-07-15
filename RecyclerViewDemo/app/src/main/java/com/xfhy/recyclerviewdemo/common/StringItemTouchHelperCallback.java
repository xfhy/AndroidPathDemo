package com.xfhy.recyclerviewdemo.common;

import android.graphics.Color;
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
public class StringItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private StringDataAdapter mStringDataAdapter;

    public StringItemTouchHelperCallback(StringDataAdapter stringDataAdapter) {
        mStringDataAdapter = stringDataAdapter;
    }

    /**
     * 给句返回值来设置是否处理某次拖拽或者滑动事件
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
//        如果是列表类型的 RecyclerView，拖拽只有 UP、DOWN 两个方向
//        如果是网格类型的则有 UP、DOWN、LEFT、RIGHT 四个方向
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int dragFlags;
        int swipeFlags = 0;
        if (layoutManager instanceof GridLayoutManager) {
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END;
        } else if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            if (linearLayoutManager.getOrientation() == LinearLayoutManager.VERTICAL) {
                //垂直
                dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                //为滑动删除做准备
                swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            } else {
                //水平
                dragFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                swipeFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            }
        } else {
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END;
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
     * 当长按 item拖拽的时候调用
     */
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            //这个时候可以给拖拽的item设置一个深颜色的背景
//            viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    //当完成拖拽手指松开的时候调用
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        //给已经完成拖拽的item恢复开始的背景
        //这里我们设置的颜色尽量和你item在xml中设置的颜色保持一致
//        viewHolder.itemView.setBackgroundColor(Color.WHITE);
    }

    /**
     * 滑动删除的回调
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int adapterPosition = viewHolder.getAdapterPosition();
        mStringDataAdapter.getStringList().remove(adapterPosition);
        mStringDataAdapter.notifyItemRemoved(adapterPosition);
    }
}
