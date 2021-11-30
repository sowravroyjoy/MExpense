package com.meettechlab.m_expense;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class UpdateActivity extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;

    private TextInputLayout lName, lDestination,lDate,lDescription;
    private TextInputEditText tName,tDestination, tDescription;
    private Button bSave;
    private int sendID;


    private String[] risks = {"Yes","No"};
    private AutoCompleteTextView tRisk, tDate;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        lName = findViewById(R.id.update_name);
        lDestination = findViewById(R.id.update_destination);
        lDate = findViewById(R.id.update_date);
        lDescription = findViewById(R.id.update_description);
        tName = findViewById(R.id.update_name_text);
        tDestination = findViewById(R.id.update_destination_text);
        tDescription = findViewById(R.id.update_description_text);
        tDate = findViewById(R.id.update_date_text);
        tRisk = findViewById(R.id.update_risk_text);
        bSave = findViewById(R.id.update_save);

        getSupportActionBar().setTitle("Update Trip Information");

        Intent intent = getIntent();
        int tmpId = intent.getIntExtra("id",0);

        Cursor cursor=new dbmanager(this).readsingledata(tmpId);
        while(cursor.moveToNext()) {
            sendID = cursor.getInt(0);
            tName.setText(cursor.getString(1));
            tDestination.setText(cursor.getString(2));
            tDate.setText(cursor.getString(3));
            tRisk.setText(cursor.getString(4));
            tDescription.setText(cursor.getString(5));
        }

        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String res=new dbmanager(getApplicationContext()).updaterecord(tmpId,tName.getText().toString(),tDestination.getText().toString(),tDate.getText().toString(),tRisk.getText().toString(),tDescription.getText().toString());
                Toast.makeText(getApplicationContext(),res,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), IndividualDetailsActivity.class);
                intent.putExtra("id", sendID);
                startActivity(intent);
                finish();
            }
        });


        arrayAdapter = new ArrayAdapter<String>(this, R.layout.risk_list, risks);
        tRisk.setAdapter(arrayAdapter);
        tRisk.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Item: " +item, Toast.LENGTH_SHORT).show();
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
                String date = makeDateString(day, month, year);
                tDate.setText(date);
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