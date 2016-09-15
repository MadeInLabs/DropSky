package br.com.madeinlabs.dropskytest;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import br.com.madeinlabs.dropsky.DropSkyAdapter;
import br.com.madeinlabs.dropsky.DropSkyItem;
import br.com.madeinlabs.dropsky.DropSkyLayout;

public class MainActivity extends AppCompatActivity {

    private DropSkyLayout mDropSkyLayout;
    private RelativeLayout mRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRoot = (RelativeLayout) findViewById(R.id.layout_root);
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
                Toast.makeText(MainActivity.this, "The base was dropped", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUpAdapter(boolean reverseMode) {
        CustomDropSkyAdapter adapter = new CustomDropSkyAdapter(this, reverseMode);
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

    private void show() {
        mDropSkyLayout.show(2000);
    }

    private void hide(DropSkyItem dropSkyItem) {
        mDropSkyLayout.hide(2000);
        mRoot.setBackgroundColor(dropSkyItem.getColor());
        mDropSkyLayout.setBackgroundColor(dropSkyItem.getColor());

        mDropSkyLayout.hide(1000);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpAdapter(true);
        refreshDropSkyWithoutAnimation();
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setHomeButtonEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                ActionBar actionBar = getSupportActionBar();
                if(actionBar != null) {
                    actionBar.setHomeButtonEnabled(false);
                    actionBar.setDisplayHomeAsUpEnabled(false);
                }
                show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
