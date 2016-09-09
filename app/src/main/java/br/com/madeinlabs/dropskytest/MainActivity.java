package br.com.madeinlabs.dropskytest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDropSkyLayout = (DropSkyLayout) findViewById(R.id.drop_sky);

        mDropSkyLayout.setListener(new DropSkyLayout.DropSkyListener() {
            @Override
            public void onItemAnimationEnd(View view, int index) {
                YoYo.with(Techniques.Wave).duration(200).interpolate(new DecelerateInterpolator()).playOn(view);
            }

            @Override
            public void onItemAnimationStart(View view, int index) {
                YoYo.with(Techniques.Swing).duration(200).interpolate(new DecelerateInterpolator()).playOn(view);
            }

            @Override
            public void onDropEnd() {
                Toast.makeText(MainActivity.this, "The base was dropped", Toast.LENGTH_SHORT).show();
            }
        });

        CustomDropSkyAdapter adapter = new CustomDropSkyAdapter(this);
        adapter.addItem(R.drawable.pikachu, "Pokémons", R.color.one);
        adapter.addItem(R.drawable.insignia, "Insignias", R.color.two);
        adapter.addItem(R.drawable.pokeball, "Items", R.color.three);
        adapter.addItem(R.drawable.egg_incubator, "Incubators", R.color.four);
        adapter.addItem(R.drawable.razz_berry, "Razz berries", R.color.five);
        adapter.addItem(R.drawable.psyduck, "Psyduck", R.color.six);
        adapter.addItem(R.drawable.gotcha, "Gotcha", R.color.seven);
        adapter.addItem(R.drawable.pokedex, "Pokedex", R.color.eight);
        adapter.setOnItemClickListener(new DropSkyAdapter.Listener() {
            @Override
            public void onItemClicked(DropSkyItem dropSkyItem, int index) {
                Toast.makeText(MainActivity.this, "Item at " + index + " clicked", Toast.LENGTH_SHORT).show();
            }
        });
        mDropSkyLayout.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDropSkyLayout.drop(3000);
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
                mDropSkyLayout.drop(3000);
        }
        return super.onOptionsItemSelected(item);
    }
}
