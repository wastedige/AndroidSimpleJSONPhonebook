package com.example.shaahinshahbazi.phoneb2;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;

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

    JsonHandler jsonHandler = new JsonHandler();
    String readJSON;

    private static final String TAG_NAME = "name";
    private static final String TAG_EMPLOYEE_ID = "employeeId";
    private static final String TAG_DETAILS = "detailsURL";
    private static final String TAG_COMPANY = "company";
    private static final String TAG_IMAGE = "smallImageURL";
    private static final String TAG_BIRTHDATE = "birthdate";
    private static final String TAG_WORKPHONE = "work";
    private static final String TAG_HOMEPHONE = "home";
    private static final String TAG_MOBILEPHONE = "mobile";
    private static final String TAG_URL = "http://solstice.applauncher.com/external/contacts.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView lv = getListView();
        contactList = new ArrayList<HashMap<String, String>>();

        // https://dylansegna.wordpress.com/2013/09/19/using-http-requests-to-get-json-objects-in-android/
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String readJSON = jsonHandler.getJsonMain( TAG_URL ) ;

        jsonHandler.parseJsonMain(readJSON);

        setListAdapter(new PhonelistAdapter(this,
                jsonHandler.returnArrayList(TAG_NAME),
                jsonHandler.returnArrayList(TAG_WORKPHONE),
                jsonHandler.returnArrayList(TAG_IMAGE)));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //Toast.makeText(MainActivity.this, contactList.get(position).get(TAG_DETAILS), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), DetailedActivity.class);
                i.putExtra("json", jsonHandler.returnArrayList(TAG_DETAILS)[position]);
                i.putExtra("name", jsonHandler.returnArrayList(TAG_NAME)[position] );
                i.putExtra("company", jsonHandler.returnArrayList(TAG_COMPANY)[position]);
                startActivity(i);
            }
        });
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
