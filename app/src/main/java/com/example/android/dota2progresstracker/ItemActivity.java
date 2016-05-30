package com.example.android.dota2progresstracker;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.dota2progresstracker.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class ItemActivity extends AppCompatActivity {

    public ItemDesc itemDescArray[];
    String apiInterfaceItems = "";
    String apiKey = "";
    String apiURL = "";
    String itemImageURL = "";
    ArrayList<Item> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        apiInterfaceItems = getString(R.string.API_INTERFACE_ITEMS);
        apiKey = getString(R.string.API_KEY);
        apiURL = getString(R.string.API_URL);
        itemImageURL = getString(R.string.ITEMS_IMAGES_LINK_1);
        new RetrieveFeedTask(this).execute();
        Log.i("","");
    }

    static class ViewHolder
    {
        static TextView item_name;
        static TextView item_shop;
        static TextView item_cost;
        static TextView item_desc;
        static ImageView item_image;
    }

    public class Item
    {
        public String itemId;
        public String itemName;
        public String itemImage;
        public String itemCost;
        public String itemShop;
        public String itemDescription;
        public String[] itemStats;
    }

    public class ItemDesc
    {
        public String itemId;
        public String itemDescription;
        public String[] itemStats;
    }

    class RetrieveFeedTask extends AsyncTask<Void, Void, String> {

        Context context;
        private Exception exception;
        RetrieveFeedTask(Context context)
        {
            this.context = context;
        }
        protected void onPreExecute() {
        }

        protected String doInBackground(Void... urls) {
            try {
                URL url = new URL(apiURL + "/" + apiInterfaceItems + "?" + apiKey);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                }
                finally{
                    urlConnection.disconnect();
                }
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String response) {
            if(response == null) {
                response = "THERE WAS AN ERROR";
            }
            if(response !=null) {
                try {
                    JSONObject itemDescJSON = new JSONObject(getString(R.string.ITEM_JSON));
                    JSONObject itemJSON = new JSONObject(response);
                    JSONArray itemsDesc = itemDescJSON.getJSONArray("items");
                    itemDescArray = new ItemDesc[itemsDesc.length()];
                    for (int i = 0; i < itemsDesc.length(); i++) {
                        itemDescArray[i] = new ItemDesc();
                        JSONObject item = itemsDesc.getJSONObject(i);
                        itemDescArray[i].itemId = item.getString("id");
                        itemDescArray[i].itemDescription = item.getString("desc");
                        JSONArray stats = item.getJSONArray("stats");
                        Log.i("Id",itemDescArray[i].itemId);
                        itemDescArray[i].itemStats = new String[stats.length()];
                        for (int j = 0; j < stats.length(); j++) {
                            String count = String.valueOf(j);
                            JSONObject stat = stats.getJSONObject(j);
                            itemDescArray[i].itemStats[j] = stat.getString(count);

                        }
                    }
                    itemList = new ArrayList<Item>();
                    JSONArray items = itemJSON.getJSONObject("result").getJSONArray("items");
                    for (int i = 0; i < items.length(); i++) {
                        for (int j = 0; j < itemsDesc.length(); j++) {
                            if (itemDescArray[j].itemId.equals(String.valueOf(items.getJSONObject(i).getInt("id")))) {
                                Item itemToBeDisplayed = new Item();

                                String name = items.getJSONObject(i).getString("name");
                                name = name.replace("item_", "");
                                name = name.replace("_", " ");

                                itemToBeDisplayed.itemId = String.valueOf(items.getJSONObject(i).getInt("id"));
                                itemToBeDisplayed.itemName = name;
                                itemToBeDisplayed.itemCost = String.valueOf(items.getJSONObject(i).getInt("cost"));
                                itemToBeDisplayed.itemDescription = itemDescArray[j].itemDescription;
                                if (items.getJSONObject(i).getInt("secret_shop") == 1)
                                    itemToBeDisplayed.itemShop = "Secret shop";
                                if (items.getJSONObject(i).getInt("side_shop") == 1)
                                    itemToBeDisplayed.itemShop = "Side shop";
                                itemToBeDisplayed.itemStats = new String[itemDescArray[j].itemStats.length];
                                for (int k = 0; k < itemDescArray[j].itemStats.length; k++) {
                                    String count = String.valueOf(j);
                                    itemToBeDisplayed.itemStats[k] = itemDescArray[j].itemStats[k];

                                }


                                itemList.add(itemToBeDisplayed);
                            }
                        }

                    }
                }catch(JSONException ex)
                {
                    String xyz = "123";
                }
                catch(Exception ex)
                {
                    String xyz = "12333";
                }

                    ListView list = (ListView) findViewById(R.id.list_of_items);
                    list.setDivider(null);
                    CustomArrayAdapter dataAdapter = new CustomArrayAdapter(context, R.id.item, itemList);
                    list.setAdapter(dataAdapter);
                }

        }
    }

    class CustomArrayAdapter extends ArrayAdapter<Item>
    {
        private ArrayList<Item> list;
        public CustomArrayAdapter(Context context, int textViewResourceId, ArrayList<Item> rowDataList)
        {
            super(context, textViewResourceId, rowDataList);
            this.list = new ArrayList<Item>();
            this.list.addAll(rowDataList);
        }

        public View getView(final int position, View convertView, ViewGroup parent)
        {

                    LayoutInflater inflator = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                    convertView = inflator.inflate(R.layout.item_items, null);

                    ViewHolder.item_name = (TextView) convertView.findViewById(R.id.item_name);
                    ViewHolder.item_cost = (TextView) convertView.findViewById(R.id.item_cost);
                    ViewHolder.item_shop = (TextView) convertView.findViewById(R.id.item_shop);
                    ViewHolder.item_desc = (TextView) convertView.findViewById(R.id.item_description);
                    LinearLayout item_details = (LinearLayout)convertView.findViewById(R.id.item_details);



                    ViewHolder.item_name.setText(list.get(position).itemName);
                    ViewHolder.item_cost.setText(list.get(position).itemCost);
                    if(list.get(position).itemShop == null){
                        ViewHolder.item_shop.setText(R.string.main_shop);
                    }
                    else {
                        ViewHolder.item_shop.setText(list.get(position).itemShop);
                    }
                    ViewHolder.item_desc.setText(list.get(position).itemDescription);

                    item_details.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getContext(),ItemDetailActivity.class);
                            intent.putExtra("item_name",list.get(position).itemName);
                            intent.putExtra("item_cost",list.get(position).itemCost);
                            intent.putExtra("item_shop",list.get(position).itemShop);
                            intent.putExtra("item_desc",list.get(position).itemDescription);
                            intent.putExtra("stats_count",list.get(position).itemStats.length);
                            for(int k=0; k< list.get(position).itemStats.length; k++)
                            {
                                intent.putExtra("stats_"+k,list.get(position).itemStats[k]);
                            }
                            startActivity(intent);
                        }
                    });
                    return convertView;
        }
    }

}