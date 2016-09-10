package br.com.madeinlabs.dropskytest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
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

        CustomDropSkyAdapter adapter = new CustomDropSkyAdapter(this);
        adapter.addItem(R.drawable.pikachu, "Refresh with Drop", R.color.one);
        adapter.addItem(R.drawable.insignia, "Refresh without Drop", R.color.two);
        adapter.addItem(R.drawable.pokeball, "Add one more item", R.color.three);
        adapter.addItem(R.drawable.egg_incubator, "Incubators", R.color.four);
        adapter.addItem(R.drawable.razz_berry, "Razz berries", R.color.five);
        adapter.setOnItemClickListener(new DropSkyAdapter.Listener() {
            @Override
            public void onItemClicked(DropSkyItem dropSkyItem, int index) {
                switch (index) {
                    case 0:
                        animateDropSky();
                        break;
                    case 1:
                        refreshDropSkyWithoutAnimation();
                        break;
                    case 2:
                        addOneMoreItem(dropSkyItem, index);
                        break;
                    default:
                        Toast.makeText(MainActivity.this, "Option not available", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mDropSkyLayout.setAdapter(adapter);
    }

    private void addOneMoreItem(DropSkyItem dropSkyItem, int index) {
        mRoot.setBackgroundColor(dropSkyItem.getColor());
        mDropSkyLayout.fly(1500);
    }

    private void refreshDropSkyWithoutAnimation() {
        mDropSkyLayout.drop();
    }

    private void animateDropSky() {
        mDropSkyLayout.drop(3000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshDropSkyWithoutAnimation();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.action_refresh:
                animateDropSky();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
