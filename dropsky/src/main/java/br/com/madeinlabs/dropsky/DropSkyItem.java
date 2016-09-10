package br.com.madeinlabs.dropsky;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class DropSkyItem extends RelativeLayout{
    private RelativeLayout mRoot;
    public RelativeLayout mViewContainer;
    public View mViewItem;
    private int mColor;

    public DropSkyItem(Context context) {
        super(context);
        mRoot = (RelativeLayout) inflate(context, R.layout.view_drop_sky_item, this);
        mViewContainer = (RelativeLayout) mRoot.findViewById(R.id.layout_true_item);
    }

    public DropSkyItem(Context context, View view, int colorResource) {
        this(context);
        setColor(ContextCompat.getColor(context, colorResource));
        addView(view);
    }

    @Override
    public void addView(View child) {
        mViewContainer.addView(child, LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mViewItem = child;
    }

    public int getTrueHeight() {
        return mViewItem.getHeight();
    }

    public void setColor(int color) {
        mColor = color;
        setBackgroundColor(color);
    }

    public int getColor() {
        return mColor;
    }
}
