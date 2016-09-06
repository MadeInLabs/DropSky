package br.com.madeinlabs.dropsky;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

public class CustomDropSkyAdapter implements DropSkyAdapter {
    private List<String> mTitles;
    private Context mContext;

    public CustomDropSkyAdapter(Context context) {
        mContext = context;
        mTitles = new LinkedList<>();
    }

    @Override
    public int getCount() {
        return mTitles.size();
    }

    @Override
    public DropSkyItem getItem(int index) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        TextView txtMenuItem = (TextView) layoutInflater.inflate(R.layout.view_menu_item, null, false);
        txtMenuItem.setText(mTitles.get(index));
        return new DropSkyItem(mContext, txtMenuItem, 200);
    }

    public void addItem(String title) {
        mTitles.add(title);
    }
}
