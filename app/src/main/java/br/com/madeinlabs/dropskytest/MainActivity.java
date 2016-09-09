package br.com.madeinlabs.dropskytest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import br.com.madeinlabs.dropsky.DropSkyLayout;

public class MainActivity extends AppCompatActivity {

    private DropSkyLayout mDropSkyLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDropSkyLayout = (DropSkyLayout) findViewById(R.id.drop_sky);

        CustomDropSkyAdapter adapter = new CustomDropSkyAdapter(this);
        adapter.addItem(R.drawable.pikachu, "Pokémons", R.color.one);
        adapter.addItem(R.drawable.insignia, "Insignias", R.color.two);
        adapter.addItem(R.drawable.pokeball, "Items", R.color.three);
        adapter.addItem(R.drawable.egg_incubator, "Incubators", R.color.four);
        adapter.addItem(R.drawable.razz_berry, "Razz berries", R.color.five);
        adapter.addItem(R.drawable.psyduck, "Psy Duck", R.color.six);
        adapter.addItem(R.drawable.gotcha, "Gotcha", R.color.seven);
        adapter.addItem(R.drawable.pokedex, "Pokedex", R.color.eight);
        mDropSkyLayout.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDropSkyLayout.drop(1500);
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
                mDropSkyLayout.drop(1500);
        }
        return super.onOptionsItemSelected(item);
    }
}
