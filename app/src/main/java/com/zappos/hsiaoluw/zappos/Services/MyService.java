package com.zappos.hsiaoluw.zappos.Services;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.zappos.hsiaoluw.zappos.DataItem;
import com.zappos.hsiaoluw.zappos.Network.NetworkManager;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class MyService extends IntentService {

    public static final String TAG = "MyService";
    public static final String MY_SERVICE_MESSAGE = "myServiceMessage";
    public static final String MY_SERVICE_PAYLOAD = "myServicePayload";

    public MyService() {
        super("MyService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Uri uri = intent.getData();
        Log.i(TAG, "onHandleIntent: " + uri.toString());
        JsonReader rd = null;
        String result;
        try {
            result = NetworkManager.downloadUrl(uri.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        InputStream stream = new ByteArrayInputStream(result.getBytes(StandardCharsets.UTF_8));

        try {
            rd =  new JsonReader(new InputStreamReader(stream,"UTF-8") );
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        JsonParser jsonParser = new JsonParser();
        JsonArray s= (jsonParser.parse(rd)).getAsJsonObject().getAsJsonArray("results");
        DataItem[] dataItems = new DataItem[s.size()];
        if(s!=null) for(int i=0;i<s.size();i++) {dataItems[i]=  new DataItem(s.get(i).getAsJsonObject());}
        Intent messageIntent = new Intent(MY_SERVICE_MESSAGE);
        messageIntent.putExtra(MY_SERVICE_PAYLOAD,  dataItems);
        LocalBroadcastManager manager =
                LocalBroadcastManager.getInstance(getApplicationContext());
        manager.sendBroadcast(messageIntent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

}
