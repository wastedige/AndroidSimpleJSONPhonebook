package com.example.shaahinshahbazi.phoneb2;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends Activity {
    // if encountering java.net.UnknownHostException while the address is actually reachable, try restarting wifi/computer. It's a known issue.
    // http://www.androidhive.info/2012/01/android-json-parsing-tutorial/

    JSONArray contacts = null;
    ArrayList<HashMap<String, String>> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView myText = (TextView) findViewById(R.id.myText);
        // https://dylansegna.wordpress.com/2013/09/19/using-http-requests-to-get-json-objects-in-android/
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String readJSON = getJSON("http://solstice.applauncher.com/external/contacts.json") ;
        //myText.setText(readJSON);
        parseJSON(readJSON);

    }

    public void parseJSON(String jstring) {
        if (jstring != null) {
            try {
                JSONArray jsonArr = new JSONArray(jstring);

                // optstring will return empty if json object is not present
                for (int i = 0; i < jsonArr.length(); i++) {
                    JSONObject c = jsonArr.getJSONObject(i);

                    String name = c.getString("name");
                    int empId = c.getInt("employeeId"); // empId and name left with getString rather than optString since they are mandatory
                    String detailsURL = c.optString("detailsURL");
                    String company = c.optString("company");
                    String smallImg = c.optString("smallImageURL");
                    String bday = c.optString("birthdate");

                    JSONObject phone = c.getJSONObject("phone"); // json within json
                    String phoneWork = phone.optString("work");
                    String phoneHome = phone.optString("home");
                    String phoneMobile = phone.optString("mobile");




                    // tmp hashmap for single contact
                    //HashMap<String, String> contact = new HashMap<String, String>();

                    // adding each child node to HashMap key => value

                    //contact.put("name", name);

                    Log.d("OUTPUT", name + "/" + phoneWork);

                    // adding contact to contact list
                    //contactList.add(contact);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("ServiceHandler", "Couldn't get any data from the url");
        }

        return;
    }

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
                Log.e(MainActivity.class.toString(),"Failed to get JSON object");
            }
        } catch(ClientProtocolException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return builder;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
