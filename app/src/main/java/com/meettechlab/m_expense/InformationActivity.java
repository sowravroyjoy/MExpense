package com.meettechlab.m_expense;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class InformationActivity extends AppCompatActivity {

    private TextView infoName, infoDestination, infoDate, infoDescription, infoRisk;
    private Button infoSave, infoCancel;

    private String name, destination, date, description, risk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        infoName = findViewById(R.id.info_name_view);
        infoDestination = findViewById(R.id.info_destination_view);
        infoDate = findViewById(R.id.info_date_view);
        infoRisk = findViewById(R.id.info_risk_view);
        infoDescription = findViewById(R.id.info_description_view);
        infoSave = findViewById(R.id.info_save);
        infoCancel = findViewById(R.id.info_cancel);

        getSupportActionBar().setTitle("Preview Trip Information");

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        destination = intent.getStringExtra("destination");
        date = intent.getStringExtra("date");
        risk = intent.getStringExtra("risk");
        description = intent.getStringExtra("description");

        infoName.setText(name);
        infoDestination.setText(destination);
        infoDate.setText(date);
        infoRisk.setText(risk);
        infoDescription.setText(description);


        infoCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InformationActivity.this, MainActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("destination", destination);
                intent.putExtra("date", date);
                intent.putExtra("description", description);
                intent.putExtra("risk", risk);
                startActivity(intent);
                finish();
            }
        });


        infoSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processinsert(infoName.getText().toString(),infoDestination.getText().toString(),infoDate.getText().toString(), infoRisk.getText().toString(),infoDescription.getText().toString());
                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                finish();
            }
        });
    }

    private void processinsert(String n, String d1, String d2, String r, String d3)
    {
        String res=new dbmanager(this).addrecord(n,d1,d2,r,d3);
        Toast.makeText(getApplicationContext(),res,Toast.LENGTH_SHORT).show();
    }
}