package com.example.android.dota2progresstracker;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HeroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero);
        Context context = this;

        try {
            String heroDetails = getString(R.string.HERO_JSON);
            JSONObject heroesJSON = new JSONObject(heroDetails);
            JSONArray heroes = heroesJSON.getJSONArray("heroes");
            ArrayList<Hero> heroArrayList = new ArrayList<>();
            for(int i=0;i<heroes.length(); i++)
            {
                Hero hero = new Hero();
                JSONObject heroJSON = heroes.getJSONObject(i);
                hero.hero_name = heroJSON.getString("hero_name");
                hero.hero_role = heroJSON.getString("role");
                hero.ability_1 = heroJSON.getJSONObject("abilities").getString("ability_1");
                hero.ability_2 = heroJSON.getJSONObject("abilities").getString("ability_2");
                hero.ability_3 = heroJSON.getJSONObject("abilities").getString("ability_3");
                hero.ability_4 = heroJSON.getJSONObject("abilities").getString("ability_4");
                heroArrayList.add(hero);
            }
            CustomArrayAdapter customArrayAdapter = new CustomArrayAdapter(this,R.layout.hero_items,heroArrayList);
            ListView listView = (ListView)findViewById(R.id.list_of_heroes);
            listView.setAdapter(customArrayAdapter);
        }catch(JSONException ex)
        {
            String xyz = "123";
            try {
                throw ex;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class Hero{
        public String hero_name;
        public String hero_role;
        public String hero_id;
        public String hero_desc;
        public String ability_1;
        public String ability_2;
        public String ability_3;
        public String ability_4;
        public String ability_1_desc;
        public String ability_2_desc;
        public String ability_3_desc;
        public String ability_4_desc;

    }
    static class ViewHolder
    {
        static TextView hero_name;
        static TextView hero_role;
        static TextView hero_id;
        static TextView hero_desc;
        static TextView ability_1;
        static TextView ability_2;
        static TextView ability_3;
        static TextView ability_4;
        static TextView ability_1_desc;
        static TextView ability_2_desc;
        static TextView ability_3_desc;
        static TextView ability_4_desc;
    }


    class CustomArrayAdapter extends ArrayAdapter<Hero>
    {
        private ArrayList<Hero> list;
        public CustomArrayAdapter(Context context, int textViewResourceId, ArrayList<Hero> rowDataList)
        {
            super(context, textViewResourceId, rowDataList);
            this.list = new ArrayList<Hero>();
            this.list.addAll(rowDataList);
        }

        public View getView(final int position, View convertView, ViewGroup parent)
        {
            LayoutInflater inflator = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflator.inflate(R.layout.hero_items, null);

            ViewHolder.hero_name = (TextView) convertView.findViewById(R.id.hero_name);
            ViewHolder.hero_role = (TextView) convertView.findViewById(R.id.hero_role);
            ViewHolder.ability_1 = (TextView) convertView.findViewById(R.id.hero_ability_1);
            ViewHolder.ability_2 = (TextView) convertView.findViewById(R.id.hero_ability_2);
            ViewHolder.ability_3 = (TextView) convertView.findViewById(R.id.hero_ability_3);
            ViewHolder.ability_4 = (TextView) convertView.findViewById(R.id.hero_ability_4);

            ViewHolder.hero_name.setText(list.get(position).hero_name);
            ViewHolder.hero_role.setText(list.get(position).hero_role);
            ViewHolder.ability_1.setText(list.get(position).ability_1);
            ViewHolder.ability_2.setText(list.get(position).ability_2);
            ViewHolder.ability_3.setText(list.get(position).ability_3);
            ViewHolder.ability_4.setText(list.get(position).ability_4);

            return convertView;
        }

    }
}
