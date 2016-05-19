package com.example.android.dota2progresstracker;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.android.dota2progresstracker.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ItemActivity extends AppCompatActivity {

    String apiInterfaceItems = "";
    String apiKey = "";
    String apiURL = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        apiInterfaceItems = getString(R.string.API_INTERFACE_ITEMS);
        apiKey = getString(R.string.API_KEY);
        apiURL = getString(R.string.API_URL);
        new RetrieveFeedTask().execute();
    }

    class Item
    {
        public String itemName;
        public String itemImage;
        public String itemCost;
        public String itemDescription;
    }


    class RetrieveFeedTask extends AsyncTask<Void, Void, String> {

        private Exception exception;

        protected void onPreExecute() {
            //progressBar.setVisibility(View.VISIBLE);
            //responseView.setText("");
        }

        protected String doInBackground(Void... urls) {

            // Do some validation here

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

            TextView textView = (TextView) findViewById(R.id.helloid);
            textView.setText(response);
            if(response !=null) {
                try {
                    JSONObject itemJSON = new JSONObject(response);
                    JSONObject result = itemJSON.getJSONObject("result");
                    JSONArray items = result.getJSONArray("items");
                    String name = "";
                    Item itemArray[] = new Item[items.length()];
                    for(int i=0; i<items.length();i++)
                    {
                        JSONObject item = items.getJSONObject(i);
                        name += "\n"+ item.getString("name");
                        Log.i("INFO:",item.getString("name"));
                    }

                }
                catch(JSONException ex)
                {
                    response = "THERE WAS AN ERROR";
                }
            }

        }
    }

}