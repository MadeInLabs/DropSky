package br.com.madeinlabs.dropsky;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;

import java.util.LinkedList;
import java.util.List;

public class DropSkyAdapter {
    private final Context mContext;
    private List<DropSkyItem> mDropSkyItems;
    private int totalHeight;
    private Listener mListener;

    public interface Listener {
        void onItemClicked(DropSkyItem dropSkyItem, int index);
    }

    public DropSkyAdapter(Context context) {
        mDropSkyItems = new LinkedList<>();
        mContext = context;
    }

    public int getCount() {
        return mDropSkyItems.size();
    }

    public DropSkyItem getDropSkyItem(int index){
        return mDropSkyItems.get(index);
    }

    public void addViewItem(View view, int color) {
        final DropSkyItem dropSkyItem = new DropSkyItem(mContext, view, color);

        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        totalHeight += view.getMeasuredHeight();
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
        return totalHeight;
    }

    public void setOnItemClickListener(Listener onItemClickListener) {
        this.mListener = onItemClickListener;
    }
}
