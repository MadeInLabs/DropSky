package br.com.madeinlabs.dropskytest;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import br.com.madeinlabs.dropsky.DropSkyAdapter;
import br.com.madeinlabs.dropsky.DropSkyItem;
import br.com.madeinlabs.dropsky.DropSkyLayout;

public class MainActivity extends AppCompatActivity {

    private DropSkyLayout mDropSkyLayout;
    private CoordinatorLayout mRoot;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mRoot = (CoordinatorLayout) findViewById(R.id.layout_root);
        mDropSkyLayout = (DropSkyLayout) findViewById(R.id.drop_sky);

        mDropSkyLayout.setListener(new DropSkyLayout.DropSkyListener() {
            @Override
            public void onItemDropEnd(View view, int index) {
                YoYo.with(Techniques.Wave).duration(200).interpolate(new DecelerateInterpolator()).playOn(view);
            }

            @Override
            public void onItemDropStart(View view, int index) {
                YoYo.with(Techniques.Swing).duration(200).interpolate(new DecelerateInterpolator()).playOn(view);
            }

            @Override
            public void onDropEnd() {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpAdapter(false);
        refreshDropSkyWithoutAnimation();
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setHomeButtonEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
    }

    private void setUpAdapter(boolean reverseMode) {
        CustomDropSkyAdapter adapter = (CustomDropSkyAdapter) mDropSkyLayout.getAdapter();
        if (adapter != null) {
            if (adapter.isReverse() == !reverseMode && !adapter.isReverse() == reverseMode) {
                TransitionDrawable transitionDrawable = (TransitionDrawable) mToolbar.getBackground();
                if (!reverseMode) {
                    transitionDrawable.reverseTransition(500);
                } else {
                    transitionDrawable.startTransition(500);
                }
            }
        }

        String mode;
        if (reverseMode) {
            mode = getString(R.string.reverse);
        } else {
            mode = getString(R.string.normal);
        }
        setTitle(getString(R.string.drop_sky_mode, mode));

        adapter = new CustomDropSkyAdapter(this, reverseMode);
        adapter.addItem(R.drawable.pikachu, "Show", R.color.one);
        adapter.addItem(R.drawable.insignia, "Hide", R.color.two);
        adapter.addItem(R.drawable.pokeball, "Refresh", R.color.three);
        adapter.addItem(R.drawable.egg_incubator, "Turn On Normal Mode", R.color.four);
        adapter.addItem(R.drawable.razz_berry, "Turn On Reverse Mode", R.color.five);
        adapter.addItem(R.drawable.psyduck, "Psyduck", R.color.six);
        adapter.addItem(R.drawable.pokedex, "Pokedex", R.color.seven);
        adapter.addItem(R.drawable.gotcha, "Gotcha", R.color.eight);
        adapter.setOnItemClickListener(new DropSkyAdapter.Listener() {
            @Override
            public void onItemClicked(DropSkyItem dropSkyItem, int index) {
                switch (index) {
                    case 0:
                        show();
                        break;
                    case 1:
                        hide(dropSkyItem);
                        break;
                    case 2:
                        refreshDropSkyWithoutAnimation();
                        break;
                    case 3:
                        setUpAdapter(false);
                        break;
                    case 4:
                        setUpAdapter(true);
                        break;
                    default:
                        Toast.makeText(MainActivity.this, "Option not available", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mDropSkyLayout.setAdapter(adapter);
    }

    private void refreshDropSkyWithoutAnimation() {
        mDropSkyLayout.show();
    }

    private boolean show() {
        return mDropSkyLayout.show(2000);
    }

    private void hide(DropSkyItem dropSkyItem) {
        mRoot.setBackgroundColor(dropSkyItem.getColor());
        mDropSkyLayout.setBackgroundColor(dropSkyItem.getColor());

        boolean hiding = mDropSkyLayout.hide(1000);
        if(hiding) {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setHomeButtonEnabled(true);
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                boolean showing = show();
                if (showing) {
                    ActionBar actionBar = getSupportActionBar();
                    if(actionBar != null) {
                        actionBar.setHomeButtonEnabled(false);
                        actionBar.setDisplayHomeAsUpEnabled(false);
                    }
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
