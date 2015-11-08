package com.example.shaahinshahbazi.phoneb2;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * Created by shaahinshahbazi on 11/7/15.
 */

public class MainActivity extends ListActivity {
    // if encountering java.net.UnknownHostException while the address is actually reachable, try restarting wifi/computer. It's a known issue.
    // http://www.androidhive.info/2012/01/android-json-parsing-tutorial/

    JSONArray contacts = null;
    ArrayList<HashMap<String, String>> contactList;
    String [] namesList;
    String [] imagesList;

    private static final String TAG_NAME = "name";
    private static final String TAG_EMPLOYEE_ID = "employeeId";
    private static final String TAG_DETAILS = "detailsURL";
    private static final String TAG_COMPANY = "company";
    private static final String TAG_IMAGE = "smallImageURL";
    private static final String TAG_BIRTHDATE = "birthdate";
    private static final String TAG_WORKPHONE = "work";
    private static final String TAG_HOMEPHONE = "home";
    private static final String TAG_MOBILEPHONE = "mobile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView lv = getListView();
        contactList = new ArrayList<HashMap<String, String>>();

        // https://dylansegna.wordpress.com/2013/09/19/using-http-requests-to-get-json-objects-in-android/
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String readJSON = getJSON("http://solstice.applauncher.com/external/contacts.json") ;

        parseJSON(readJSON);

        setListAdapter(new PhonelistAdapter(this, returnArrayList(TAG_NAME), returnArrayList(TAG_WORKPHONE), returnArrayList(TAG_IMAGE)));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //Toast.makeText(MainActivity.this, contactList.get(position).get(TAG_DETAILS), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), DetailedActivity.class);
                i.putExtra("json", contactList.get(position).get(TAG_DETAILS));
                i.putExtra("name", contactList.get(position).get(TAG_NAME));
                i.putExtra("company", contactList.get(position).get(TAG_COMPANY));
                startActivity(i);
            }
        });
    }

    private void parseJSON(String jstring) {
        if (jstring != null) {
            try {
                JSONArray jsonArr = new JSONArray(jstring);

                // optstring will return empty if json object is not present
                for (int i = 0; i < jsonArr.length(); i++) {
                    JSONObject c = jsonArr.getJSONObject(i);

                    String name = c.getString(TAG_NAME);
                    String empId = c.getString(TAG_EMPLOYEE_ID); // empId and name left with getString rather than optString since they are mandatory
                    String detailsURL = c.optString(TAG_DETAILS);
                    String company = c.optString(TAG_COMPANY);
                    String smallImg = c.optString(TAG_IMAGE);
                    String bday = c.optString(TAG_BIRTHDATE);

                    JSONObject phone = c.getJSONObject("phone"); // json within json
                    String phoneWork = phone.optString(TAG_WORKPHONE);
                    String phoneHome = phone.optString(TAG_HOMEPHONE);
                    String phoneMobile = phone.optString(TAG_MOBILEPHONE);

                    // temporary hashmap for single contact
                    HashMap<String, String> contact = new HashMap<String, String>();

                    contact.put(TAG_EMPLOYEE_ID, empId);
                    contact.put(TAG_NAME, name);
                    contact.put(TAG_DETAILS, detailsURL);
                    contact.put(TAG_COMPANY, company);
                    contact.put(TAG_IMAGE, smallImg);
                    contact.put(TAG_BIRTHDATE, bday);
                    contact.put(TAG_WORKPHONE, phoneWork);
                    contact.put(TAG_HOMEPHONE, phoneHome);
                    contact.put(TAG_MOBILEPHONE, phoneMobile);

                    // Log.d("OUTPUT", name + " / " + empId);

                    // adding contact to contact list
                    contactList.add(contact);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("ServiceHandler", "Nothing was fetched!");
        }

        return;
    }

    private String [] returnArrayList(String tag) {
        String [] tmp = new String [contactList.size()];
        for (int i = 0; i < contactList.size(); i++) {
            tmp[i] = contactList.get(i).get(tag);
        }
        return tmp;
    }

    private String getJSON(String address){
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
