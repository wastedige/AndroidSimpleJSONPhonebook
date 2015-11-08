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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by shaahinshahbazi on 11/8/15.
 */
public class JsonHandler {

    private static final String TAG_NAME = "name";
    private static final String TAG_EMPLOYEE_ID = "employeeId";
    private static final String TAG_DETAILS = "detailsURL";
    private static final String TAG_COMPANY = "company";
    private static final String TAG_IMAGE = "smallImageURL";
    private static final String TAG_BIRTHDATE = "birthdate";
    private static final String TAG_WORKPHONE = "work";
    private static final String TAG_HOMEPHONE = "home";
    private static final String TAG_MOBILEPHONE = "mobile";


    private static final String TAG_FAVORITE = "favorite";
    private static final String TAG_LIMAGE = "largeImageURL";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_WEBSITE = "website";

    ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();

    public String getJsonDetailed(String address){
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

    protected String getJsonMain(String address){
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

    protected void parseJsonMain(String jstring) {
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

    // Returns an array from a specific attribute (key) from the pre-parsed JSON
    protected String [] returnArrayList(String tag) {
        String [] tmp = new String [contactList.size()];
        for (int i = 0; i < contactList.size(); i++) {
            tmp[i] = contactList.get(i).get(tag);
        }
        return tmp;
    }
}
