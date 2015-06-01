package com.example.rohanmenhdiratta.newz;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Yash on 5/30/2015.
 */
public class CampaignFinanceActivity extends Activity{

    // Declare class-level variables
    final String CampaignFinanceStoriesAPIKey = "5d6eb7d7c2ecbaad9b266704ff165007:17:72161870";
    public String json;
    final String TAG = "CAMPAIGNFINANCESTORIES";
    private ArrayList<News> CampaignFinanceNews;
    final String LastName = "paul";
    final String CampaignCycle = "2016";
    final String Version = "v3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String URL = "http://api.nytimes.com/svc/elections/us/" + Version + "/finances/" + CampaignCycle + "/candidates/search.json?query=" + LastName + "&api-key=" + CampaignFinanceStoriesAPIKey;

        CampaignFinanceNews = new ArrayList<News>();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                json = getJSON(URL);
            }
        });

        t.start();

        try {
            t.join();
            Log.d(TAG, json);

        }catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    // Use the API URL to get a JSON File and put it into a string
    public String getJSON(String address) {
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(address);
        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();

            //Toast.makeText(getBaseContext(), response.getStatusLine().getReasonPhrase(),Toast.LENGTH_LONG);
            Log.d("task", response.getStatusLine().getReasonPhrase());
            //_response = response;


            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            } else {
                Log.e(MainActivity.class.toString(), "Failed to JSON object");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    public void parseJSON(String JSONString)
    {
        try
        {
            JSONObject jsonObject = new JSONObject(JSONString);
            JSONArray results = jsonObject.getJSONArray("results");

            for(int i = 0; i < results.length(); i++)
            {
                News candidateNews = new News();
            }
        }
        catch(Throwable t)
        {
            t.printStackTrace();
        }
    }
}
