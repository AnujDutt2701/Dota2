package com.example.android.dota2progresstracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LearnDotaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_dota);
    }
    public void showItems (View view)
    {
        Intent intent = new Intent(this, ItemActivity.class);
        startActivity(intent);
    }
    public void showHeroes (View view)
    {
        Intent intent = new Intent(this, HeroActivity.class);
        startActivity(intent);
    }

}
