package com.example.shaahinshahbazi.phoneb2;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by shaahinshahbazi on 11/8/15.
 */
public class JsonHandler {

    private static final String TAG_FAVORITE = "favorite";
    private static final String TAG_LIMAGE = "largeImageURL";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_WEBSITE = "website";



    public String getJSON(String address){
        // StringBuilder builder = new StringBuilder();
        String builder = "";
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(address);
        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if(statusCode == 200){
                HttpEntity entity = response.getEntity();

                builder = EntityUtils.toString(entity);

            } else {
                Log.e(MainActivity.class.toString(), "Failed to get JSON object");
            }
        } catch(ClientProtocolException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return builder;
    }

    public String [] parseJsonDetailed(String jstring) {
        if (jstring != null) {
            try {

                JSONObject c = new JSONObject(jstring);


                String[] tmp = { c.getString(TAG_FAVORITE),
                        c.getString(TAG_LIMAGE),
                        c.optString(TAG_EMAIL),
                        c.optString(TAG_WEBSITE),
                };

                Log.d("JSONHANDLER", tmp[0]);

                return tmp;

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("ServiceHandler", "Nothing was fetched!");
        }

        return null;
    }
}
