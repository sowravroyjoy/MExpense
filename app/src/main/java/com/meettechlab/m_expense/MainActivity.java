package com.meettechlab.m_expense;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;

    private TextInputLayout lName, lDestination,lDate,lDescription;
    private TextInputEditText tName,tDestination, tDescription;
    private Button bSave, bShow;

    private String name, destination, date, description, risk;

    private String[] risks = {"Yes","No"};
    private AutoCompleteTextView tRisk, tDate;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lName = findViewById(R.id.trip_name);
        lDestination = findViewById(R.id.trip_destination);
        lDate = findViewById(R.id.trip_date);
        lDescription = findViewById(R.id.trip_description);
        tName = findViewById(R.id.trip_name_text);
        tDestination = findViewById(R.id.trip_destination_text);
        tDescription = findViewById(R.id.trip_description_text);
        tDate = findViewById(R.id.trip_date_text);
        tRisk = findViewById(R.id.trip_risk_text);
        bSave = findViewById(R.id.trip_save);
        bShow = findViewById(R.id.trip_show);

        getSupportActionBar().setTitle("Add Trip Information");


        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        destination = intent.getStringExtra("destination");
        date = intent.getStringExtra("date");
        risk = intent.getStringExtra("risk");
        description = intent.getStringExtra("description");

        tName.setText(name);
        tDestination.setText(destination);
        tDate.setText(date);
        tRisk.setText(risk);
        tDescription.setText(description);

        bShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                finish();

            }
        });

        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()) {
                    Intent intent = new Intent(MainActivity.this, InformationActivity.class);
                    intent.putExtra("name", tName.getText().toString());
                    intent.putExtra("destination", tDestination.getText().toString());
                    intent.putExtra("date", tDate.getText().toString());
                    intent.putExtra("description", tDescription.getText().toString());
                    intent.putExtra("risk", tRisk.getText().toString());
                    startActivity(intent);
                    finish();
                }
            }
        });


        arrayAdapter = new ArrayAdapter<String>(this, R.layout.risk_list, risks);
        tRisk.setAdapter(arrayAdapter);
        tRisk.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
               // Toast.makeText(getApplicationContext(), "Item: " +item, Toast.LENGTH_SHORT).show();
            }
        });


        initDatePicker();
        lDate.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });


    }

    private boolean validate() {
        String vName = tName.getText().toString();
        String vDestination = tDestination.getText().toString();
        String vDate = tDate.getText().toString();
        String vRisk = tRisk.getText().toString();
        if(vName.isEmpty() || vDestination.isEmpty() || vDate.isEmpty()|| vRisk.isEmpty()){
            if(vName.isEmpty()){
                Toast.makeText(this, "Please Insert Name!", Toast.LENGTH_SHORT).show();
                return false;
            }else if(vDestination.isEmpty()){
                Toast.makeText(this, "Please Insert Destination!", Toast.LENGTH_SHORT).show();
                return false;
            }else if(vDate.isEmpty()){
                Toast.makeText(this, "Please insert Date!", Toast.LENGTH_SHORT).show();
                return false;
            }
            else if(vRisk.isEmpty()){
                Toast.makeText(this, "Please Insert Risk Assessment!", Toast.LENGTH_SHORT).show();
                return false;
            }else{
                Toast.makeText(this, "Please Insert Missing Details!", Toast.LENGTH_SHORT).show();
                return false;
            }
        }else return true;
    }

    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month +1;
                String dDate = makeDateString(day, month, year);
                tDate.setText(dDate);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }
    private String makeDateString(int day, int month, int year)
    {
        return day + " " + getMonthFormat(month)  + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }

}