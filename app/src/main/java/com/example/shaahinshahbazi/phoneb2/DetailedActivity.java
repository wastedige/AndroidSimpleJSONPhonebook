package com.example.shaahinshahbazi.phoneb2;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by shaahinshahbazi on 11/7/15.
 */
public class DetailedActivity extends Activity{

    String jsonDetailedAddress;
    JsonHandler jsonHandler = new JsonHandler();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_detailed);

        TextView name = (TextView) findViewById(R.id.name);
        TextView company = (TextView) findViewById(R.id.company);

        Intent i = getIntent();
        // getting attached intent data

        jsonDetailedAddress = i.getStringExtra("json");
        name.setText(i.getStringExtra("name"));
        company.setText(i.getStringExtra("company"));

        String readJSON = jsonHandler.getJSON(jsonDetailedAddress);
        String [] detailedContent = jsonHandler.parseJsonDetailed(readJSON);

        new DownloadImageTask((ImageView) findViewById(R.id.img))
                .execute(detailedContent[1]);

    }
}
