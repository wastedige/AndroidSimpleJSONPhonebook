package com.example.shaahinshahbazi.phoneb2;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;


/**
 * Created by shaahinshahbazi on 11/7/15.
 */
public class DetailedActivity extends Activity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_detailed);

        TextView name = (TextView) findViewById(R.id.name);
        TextView empID = (TextView) findViewById(R.id.employeeID);
        TextView company = (TextView) findViewById(R.id.company);

        Intent i = getIntent();
        // getting attached intent data

        name.setText(i.getStringExtra("name"));
        empID.setText(i.getStringExtra("employeeID"));
        company.setText(i.getStringExtra("company"));
    }
}
