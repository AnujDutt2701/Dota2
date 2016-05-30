package com.example.android.dota2progresstracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ItemDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        TextView item_name = (TextView) findViewById(R.id.item_details_name);
        TextView item_cost = (TextView) findViewById(R.id.item_details_cost);
        TextView item_shop = (TextView) findViewById(R.id.item_shop_name);
        TextView item_desc = (TextView) findViewById(R.id.item_details_desc);
        ImageView item_image = (ImageView) findViewById(R.id.item_details_image);

        String itemImageURL = getString(R.string.ITEMS_IMAGES_LINK_1);
        String name = getIntent().getStringExtra("item_name").replace(" ","_");
        String url = itemImageURL + name + getString(R.string.ITEMS_IMAGES_LINK_2);
        Picasso.with(this).load(url).into(item_image);
        item_name.setText(getIntent().getStringExtra("item_name"));
        item_cost.setText(getIntent().getStringExtra("item_cost"));
        item_shop.setText(getIntent().getStringExtra("item_shop"));
        item_desc.setText(getIntent().getStringExtra("item_desc"));
        int statsCount = getIntent().getIntExtra("stats_count",0);
        for(int i=0; i<statsCount; i++)
        {
            switch(i)
            {
                case 1: TextView item_stats_1 = (TextView) findViewById(R.id.item_details_stats_1);
                        item_stats_1.setText(getIntent().getStringExtra("stats_1"));
                        break;
                case 2: TextView item_stats_2 = (TextView) findViewById(R.id.item_details_stats_2);
                    item_stats_2.setText(getIntent().getStringExtra("stats_2"));
                    break;
                case 3: TextView item_stats_3 = (TextView) findViewById(R.id.item_details_stats_3);
                    item_stats_3.setText(getIntent().getStringExtra("stats_3"));
                    break;
                case 4: TextView item_stats_4 = (TextView) findViewById(R.id.item_details_stats_4);
                    item_stats_4.setText(getIntent().getStringExtra("stats_4"));
                    break;
            }

        }
    }
}
