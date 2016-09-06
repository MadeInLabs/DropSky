package br.com.madeinlabs.dropsky;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class DropSkyItem extends RelativeLayout{
    private View mView;

    public DropSkyItem(Context context) {
        super(context);
    }

    public DropSkyItem(Context context, View view, int pixelsHeight) {
        this(context);
        View root = inflate(context, R.layout.view_drop_sky_item, this);

        RelativeLayout viewContainer = (RelativeLayout) root.findViewById(R.id.layout_true_item);

        RelativeLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, pixelsHeight);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        view.setLayoutParams(params);
        viewContainer.addView(view);

        mView = view;
    }

    public float getTrueHeight() {
        return mView.getHeight();
    }
}
