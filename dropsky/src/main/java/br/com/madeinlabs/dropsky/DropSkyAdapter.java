package br.com.madeinlabs.dropsky;

import android.content.Context;
import android.view.View;

public abstract class DropSkyAdapter {
    private int mPosition;
    private Context mContext;

    public DropSkyAdapter(Context context) {
        mPosition = -1;
        mContext = context;
    }

    public DropSkyItem getNextView() {
        if(mPosition > 0) {
            DropSkyItem dropSkyItem = new DropSkyItem(mContext);
            dropSkyItem.addView(getView(mPosition));

            int color = getItemColor(mPosition);
            dropSkyItem.setBackgroundColor(color);
            mPosition--;
            return dropSkyItem;
        }
        return null;
    }

    protected abstract int getItemColor(int position);
    protected abstract View getView(int position);
}
