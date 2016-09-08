package br.com.madeinlabs.dropskytest;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;

import java.util.LinkedList;
import java.util.List;

import br.com.madeinlabs.dropsky.DropSkyAdapter;
import br.com.madeinlabs.dropsky.DropSkyItem;

public class CustomDropSkyAdapter implements DropSkyAdapter {
    private List<Integer> mIcons;
    private List<String> mTitles;
    private List<Integer> mColors;
    private Context mContext;

    public CustomDropSkyAdapter(Context context) {
        mContext = context;
        mIcons = new LinkedList<>();
        mTitles = new LinkedList<>();
        mColors = new LinkedList<>();
    }

    @Override
    public int getCount() {
        return mTitles.size();
    }

    @Override
    public DropSkyItem getItem(int index) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        MaterialRippleLayout materialRippleLayout = (MaterialRippleLayout) layoutInflater.inflate(R.layout.view_menu_item, null, false);

        RelativeLayout layoutRoot = (RelativeLayout) materialRippleLayout.findViewById(R.id.layout_root);
        Integer colorResource = mColors.get(index);
        layoutRoot.setBackgroundColor(ContextCompat.getColor(mContext, colorResource));

        ImageView imgIcon = (ImageView) layoutRoot.findViewById(R.id.img_icon);
        imgIcon.setImageResource(mIcons.get(index));

        TextView txTitle = (TextView) layoutRoot.findViewById(R.id.txt_title);
        txTitle.setText(mTitles.get(index));

        return new DropSkyItem(mContext, materialRippleLayout, colorResource);
    }

    public void addItem(int iconResource, String title, int colorResource) {
        mIcons.add(iconResource);
        mTitles.add(title);
        mColors.add(colorResource);
    }
}
