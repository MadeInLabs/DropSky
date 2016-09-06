package br.com.madeinlabs.dropsky;

import android.content.Context;
import android.view.View;

public class CustomDropSkyAdapter extends DropSkyAdapter {
    public CustomDropSkyAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemColor(int position) {
        return 0;
    }

    @Override
    protected View getView(int position) {
        return null;
    }

    public void addView(int resourceIcon, String title, int color) {

    }
}
