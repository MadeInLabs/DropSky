package br.com.madeinlabs.dropsky;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class DropSkyItem extends RelativeLayout{
    private View mView;

    public DropSkyItem(Context context) {
        super(context);
    }

    public DropSkyItem(Context context, View view, int colorResource) {
        this(context);
        RelativeLayout root = (RelativeLayout) inflate(context, R.layout.view_drop_sky_item, this);
        setBackgroundColor(ContextCompat.getColor(context, colorResource));

        RelativeLayout viewContainer = (RelativeLayout) root.findViewById(R.id.layout_true_item);
        viewContainer.addView(view, LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        mView = view;
    }

    public int getTrueHeight() {
        return mView.getHeight();
    }
}
