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
 * Created by rohanmenhdiratta on 5/27/15.
 */
public class TopStoriesActivity extends Activity {


    final String TopStoriesAPIKey = "9656df77b6fbf9ad6a43bced7e436343:2:72161870";
    public String json;
    final String TAG = "TOPSTORIES";
    private ArrayList<News> news;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String URL = "http://api.nytimes.com/svc/topstories/v1/home.json?api-key=" + TopStoriesAPIKey;
        //String json = "";

        news = new ArrayList<News>();

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

        //We have a json string at this point


    }

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

    public void parseJSON(String jsonString)
    {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            JSONArray results = jsonObject.getJSONArray("results");

            for(int i = 0; i < results.length(); i++) {

                News currentNews = new News();
                JSONObject resultObject = results.getJSONObject(i);
                currentNews.setSection(resultObject.getString("section"));
                currentNews.setSubsection(resultObject.getString("subsection"));
                currentNews.setTitle(resultObject.getString("title"));
                currentNews.setArticleAbstract(resultObject.getString("abstract"));
                currentNews.setUrl(resultObject.getString("url"));
                currentNews.setAuthor(resultObject.getString("byline"));
                currentNews.setPublishedDate(resultObject.getString("published_date"));

                JSONArray multimediaJSONArray = resultObject.getJSONArray("multimedia");
                for(int j = 0; j < multimediaJSONArray.length(); j++)
                {
                    Multimedia multimedia = new Multimedia();
                    JSONObject multimediaObject = multimediaJSONArray.getJSONObject(j);
                    multimedia.setUrl(multimediaObject.getString("url"));
                    multimedia.setFormat(multimediaObject.getString("format"));
                    multimedia.setHeight(multimediaObject.getInt("height"));
                    multimedia.setWidth(multimediaObject.getInt("width"));
                    multimedia.setType(multimediaObject.getString("type"));
                    multimedia.setCaption(multimediaObject.getString("caption"));

                    currentNews.addMultimedia(multimedia);
                }

                news.add(currentNews);
            }



        }catch (Throwable t)
        {
            t.printStackTrace();
        }
    }

}

