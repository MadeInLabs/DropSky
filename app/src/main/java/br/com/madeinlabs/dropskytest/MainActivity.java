package br.com.madeinlabs.dropskytest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.madeinlabs.dropsky.CustomDropSkyAdapter;
import br.com.madeinlabs.dropsky.DropSkyAdapter;
import br.com.madeinlabs.dropsky.DropSkyLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DropSkyLayout dropSkyLayout = (DropSkyLayout) findViewById(R.id.drop_sky);

        DropSkyAdapter adapter = new CustomDropSkyAdapter(this);
        dropSkyLayout.setAdapter(adapter);
    }
}
