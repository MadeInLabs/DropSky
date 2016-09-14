package br.com.madeinlabs.dropsky;

import android.content.Context;
import android.view.View;

import java.util.LinkedList;
import java.util.List;

public class DropSkyAdapter {
    private final Context mContext;
    private List<DropSkyItem> mDropSkyItems;
    private Listener mListener;
    private boolean mReverse;
    private List<Integer> mHeights;

    public boolean isReverse() {
        return mReverse;
    }

    public interface Listener {
        void onItemClicked(DropSkyItem dropSkyItem, int index);
    }

    public DropSkyAdapter(Context context) {
        mDropSkyItems = new LinkedList<>();
        mHeights = new LinkedList<>();
        mContext = context;
    }

    public DropSkyAdapter(Context context, boolean reverseMode) {
        this(context);
        mReverse = reverseMode;
    }

    public int getCount() {
        return mDropSkyItems.size();
    }

    public DropSkyItem getDropSkyItem(int index){
        return mDropSkyItems.get(index);
    }

    public void addViewItem(View view, int color) {
        final DropSkyItem dropSkyItem = new DropSkyItem(mContext, view, color, mReverse);

        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        mHeights.add(view.getMeasuredHeight());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mListener != null) {
                    mListener.onItemClicked(dropSkyItem, mDropSkyItems.indexOf(dropSkyItem));
                }
            }
        });

        mDropSkyItems.add(dropSkyItem);
    }

    public int getTotalHeight() {
        int totalHeight = 0;
        for (int height : mHeights) {
            totalHeight += height;
        }
        return totalHeight;
    }

    public int getItemY(int index) {
        int y = 0;
        for(int i = 0; i < index; i++) {
            y += mHeights.get(i);
        }
        return y;
    }

    public void setOnItemClickListener(Listener onItemClickListener) {
        this.mListener = onItemClickListener;
    }

    public int getTrueHeight(int index) {
        return mHeights.get(index);
    }
}
