package com.example.james.jamesdraglayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

/**
 * Created by James.Shi on 11/25/14.
 */
public class ListViewCompat extends ListView {

    private static final String TAG = "ListViewCompat";
    private boolean mIsOpened = true;

    private SlideView mFocusedItemView;

    public ListViewCompat(Context context) {
        super(context);
    }

    public ListViewCompat(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewCompat(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void shrinkListItem(int position) {
        View item = getChildAt(position);

        if (item != null) {
            try {
                ((SlideView) item).shrink();
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                int x = (int) event.getX();
                int y = (int) event.getY();
                int position = pointToPosition(x, y);

                int[] listViewCoords = new int[2];
                int[] listViewWindows = new int[2];
                this.getLocationOnScreen(listViewCoords);
                this.getLocationInWindow(listViewWindows);

                Log.e(TAG, "postion=" + position);

                if (position != INVALID_POSITION) {
                    Main.PetItem data = (Main.PetItem) getItemAtPosition(position);
                    mFocusedItemView = data.slideView;
                    Log.e(TAG, "FocusedItemView=" + mFocusedItemView);
                }
            }
            default:
                break;
        }

        if (mFocusedItemView != null) {
            mFocusedItemView.onRequireTouchEvent(event);
        }

        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        DragLayout dragLayout = new DragLayout(getContext());
//
//        if (mIsOpened) {
//            dragLayout.onCloseParentViewTouch(false);
//            return super.onInterceptTouchEvent(ev);
//        } else {
//            dragLayout.onCloseParentViewTouch(true);
//            return false;
//        }

        Log.d("test", "ListViewCompat onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //ListViewCompat
        Log.d("test", "ListViewCompat dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }
}
