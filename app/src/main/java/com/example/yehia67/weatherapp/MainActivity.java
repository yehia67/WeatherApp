package com.example.yehia67.weatherapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {
TextView textView;
EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.tvResult);
        editText = (EditText) findViewById(R.id.et_search);

    }
    public class DownloadTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            URLConnection urlConnection;
            try
            {
                url = new URL(urls[0]);
                urlConnection = url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);
                int data = reader.read();
                while (data != -1)
                {
                    char info = (char) data;
                    result += info;
                    data = reader.read();
                }
                return result;
            }catch(MalformedURLException e)
            {
                Toast.makeText(getApplicationContext(),"malformed url",Toast.LENGTH_LONG);
            }catch (IOException e)
            {
                Toast.makeText(getApplicationContext(),"can t open url link",Toast.LENGTH_LONG);
            }
       return null;
        }
        @Override
    protected void onPostExecute(String result){
     super.onPostExecute(result);
            try{
                String message = "";
                JSONObject jsonObject = new JSONObject();
                String weather = jsonObject.getString("weather");
                JSONArray arr = new JSONArray(weather);
                for(int i = 0; i < arr.length();i++)
                {
                    String main ="",description ="";
                    JSONObject jsonpart = arr.getJSONObject(i);
                main = jsonpart.getString("main");
                description = jsonpart.getString("description");
                    if (main != "" && description != "")
                    {
                        message = main + ": "+description + "\r\n";
                    }
                }
                if(message != "")
                {
                    textView.setText(message);
                }
                else
                {
                    textView.setText("Sorry we can't get any useful info for this city");
                }
            }catch (JSONException e)
            {
                Toast.makeText(getApplicationContext(),"error in getting json weather",Toast.LENGTH_LONG);
            }

    }
    }
}
