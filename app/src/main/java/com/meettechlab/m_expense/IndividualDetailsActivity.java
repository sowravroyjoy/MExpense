package com.meettechlab.m_expense;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.meettechlab.m_expense.models.ModelName;

import java.util.ArrayList;

public class IndividualDetailsActivity extends AppCompatActivity {

    private TextView dName,dDestination,dDate,dRisk,dDescription;
    private Button dUpdate, dDelete, dExpense;
    private int tmpID, sendID;
    private String sendDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_details);


        dName = findViewById(R.id.d_name_view);
        dDestination = findViewById(R.id.d_destination_view);
        dDate = findViewById(R.id.d_date_view);
        dRisk = findViewById(R.id.d_risk_view);
        dDescription = findViewById(R.id.d_description_view);
        dUpdate = findViewById(R.id.d_update);
        dDelete = findViewById(R.id.d_delete);
        dExpense = findViewById(R.id.d_expense);



        Intent intent = getIntent();
        int tmpId = intent.getIntExtra("id",0);

        dExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IndividualDetailsActivity.this, ViewExpenseActivity.class);
                intent.putExtra("id", sendID);
                intent.putExtra("date", sendDate);
                startActivity(intent);
                finish();
            }
        });

        dUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IndividualDetailsActivity.this, UpdateActivity.class);
                intent.putExtra("id", sendID);
                startActivity(intent);
                finish();
            }
        });

        dDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String res=new dbmanager(getApplicationContext()).delrecord(tmpId);
                Toast.makeText(getApplicationContext(),res,Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                finish();
            }
        });



        Cursor cursor=new dbmanager(this).readsingledata(tmpId);
        while(cursor.moveToNext())
        {
            sendID = cursor.getInt(0);
            sendDate = cursor.getString(3);
            dName.setText(cursor.getString(1));
            dDestination.setText(cursor.getString(2));
            dDate.setText(sendDate);
            dRisk.setText(cursor.getString(4));
            dDescription.setText(cursor.getString(5));
            getSupportActionBar().setTitle(dName.getText());
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        finish();
        super.onBackPressed();
    }
}