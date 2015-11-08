package com.example.shaahinshahbazi.phoneb2;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
        TextView email = (TextView) findViewById(R.id.email);
        TextView website = (TextView) findViewById(R.id.website);
        ImageView star = (ImageView) findViewById(R.id.star);

        Intent i = getIntent();
        // getting attached intent data
        jsonDetailedAddress = i.getStringExtra("json");
        name.setText(i.getStringExtra("name"));
        company.setText(i.getStringExtra("company"));

        String readJSON = jsonHandler.getJsonDetailed(jsonDetailedAddress);
        String [] detailedContent = jsonHandler.parseJsonDetailed(readJSON);

        // using parsed JSON data
        if (detailedContent[0] == "false") {
            star.setVisibility(View.INVISIBLE);
        }

        new DownloadImageTask((ImageView) findViewById(R.id.img))
                .execute(detailedContent[1]);

        email.setText(detailedContent[2]);
        website.setText(detailedContent[3]);

        findViewById(R.id.layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(j);

            }
        });

    }
}
