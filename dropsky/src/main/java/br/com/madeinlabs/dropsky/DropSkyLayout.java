package br.com.madeinlabs.dropsky;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class DropSkyLayout extends RelativeLayout {
    private DropSkyAdapter mAdapter;

    public DropSkyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setAdapter(DropSkyAdapter adapter) {
        this.mAdapter = adapter;
    }

    public void drop() {
        DropSkyItem dropSkyItem = mAdapter.getNextView();
        dropSkyItem.setTranslationY(0);
        float parentBottom = getBottom();
        dropSkyItem.animate().translationY(parentBottom);
    }
}
