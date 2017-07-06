package com.example.szymon.aplikacjazaliczeniowa;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    public void goToLokalizacja(View v){

        Context context;
        context = getApplicationContext();
        Intent intent = new Intent(context,Lokalizacja.class);
        startActivity(intent);

    }

    public void goToTemperatura(View v){

        Context context;
        context = getApplicationContext();
        Intent intent = new Intent(context,Temperatura.class);
        startActivity(intent);
    }
}
