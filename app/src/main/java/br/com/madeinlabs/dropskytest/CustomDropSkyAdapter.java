package br.com.madeinlabs.dropskytest;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;

import br.com.madeinlabs.dropsky.DropSkyAdapter;

public class CustomDropSkyAdapter extends DropSkyAdapter {
    private Context mContext;

    public CustomDropSkyAdapter(Context context, boolean reverse) {
        super(context, reverse);
        mContext = context;
    }

    public void addItem(int iconResource, String title, int colorResource) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        MaterialRippleLayout materialRippleLayout = (MaterialRippleLayout) layoutInflater.inflate(R.layout.view_menu_item, null, false);

        RelativeLayout layoutRoot = (RelativeLayout) materialRippleLayout.findViewById(R.id.layout_root);

        ImageView imgIcon = (ImageView) layoutRoot.findViewById(R.id.img_icon);
        imgIcon.setImageResource(iconResource);

        TextView txTitle = (TextView) layoutRoot.findViewById(R.id.txt_title);
        txTitle.setText(title);

        addViewItem(materialRippleLayout, ContextCompat.getColor(mContext, colorResource));
    }
}
