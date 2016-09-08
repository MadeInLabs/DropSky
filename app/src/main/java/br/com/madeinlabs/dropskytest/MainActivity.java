package br.com.madeinlabs.dropskytest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.madeinlabs.dropsky.DropSkyLayout;

public class MainActivity extends AppCompatActivity {

    private DropSkyLayout mDropSkyLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDropSkyLayout = (DropSkyLayout) findViewById(R.id.drop_sky);

        CustomDropSkyAdapter adapter = new CustomDropSkyAdapter(this);
        adapter.addItem(R.drawable.pikachu, "Profile", R.color.one);
        adapter.addItem(R.drawable.insignia, "Map", R.color.two);
        adapter.addItem(R.drawable.pokeball, "History", R.color.three);
        adapter.addItem(R.drawable.egg_incubator, "Settings", R.color.four);
        adapter.addItem(R.drawable.razz_berry, "Help", R.color.five);
        mDropSkyLayout.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDropSkyLayout.drop(1500);
    }
}
