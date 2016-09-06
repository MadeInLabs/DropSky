package br.com.madeinlabs.dropskytest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import br.com.madeinlabs.dropsky.CustomDropSkyAdapter;
import br.com.madeinlabs.dropsky.DropSkyLayout;

public class MainActivity extends AppCompatActivity {

    private DropSkyLayout mDropSkyLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDropSkyLayout = (DropSkyLayout) findViewById(R.id.drop_sky);

        CustomDropSkyAdapter adapter = new CustomDropSkyAdapter(this);
        int i = 0;
        adapter.addItem((i++) + " º item");
        adapter.addItem((i++) + " º item");
        adapter.addItem((i++) + " º item");
        adapter.addItem((i++) + " º item");
        adapter.addItem((i++) + " º item");
        adapter.addItem((i++) + " º item");
        adapter.addItem((i++) + " º item");
        adapter.addItem((i++) + " º item");
        adapter.addItem((i) + " º item");
        mDropSkyLayout.setAdapter(adapter);

        mDropSkyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDropSkyLayout.drop(700);
            }
        });
    }
}
