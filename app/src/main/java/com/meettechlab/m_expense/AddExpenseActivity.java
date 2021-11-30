package com.meettechlab.m_expense;

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

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class AddExpenseActivity extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;

    private TextInputLayout eType,eAmount,eTime,eComment;
    private TextInputEditText etAmount,etComment;
    private AutoCompleteTextView etType,etTime;
    private Button eAdd, eShow;
    private String[] types = {"Travel","Food","Accommodation","Shopping", "Other"};
    ArrayAdapter<String> arrayAdapter;

    private int tmpId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense_activity);

        eType = findViewById(R.id.e_type);
        etType = findViewById(R.id.e_type_view);
        eAmount = findViewById(R.id.e_amount);
        etAmount = findViewById(R.id.e_amount_view);
        eTime = findViewById(R.id.e_time);
        etTime = findViewById(R.id.e_time_view);
        eComment = findViewById(R.id.e_comments);
        etComment = findViewById(R.id.e_comments_view);
        eAdd = findViewById(R.id.e_save);
        eShow = findViewById(R.id.e_show);

        getSupportActionBar().setTitle("Add Expense Information");

        Intent intent = getIntent();
         tmpId = intent.getIntExtra("id",0);
        String tmpDate = intent.getStringExtra("date");
        etTime.setText(tmpDate);

        eAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){

                    String type = etType.getText().toString();
                    String amount = etAmount.getText().toString();
                    String time = etTime.getText().toString();
                    String comment = etComment.getText().toString();
                    String res=new dbmanager(getApplicationContext()).addexpense(type,amount,time,comment,tmpId);
                    Toast.makeText(getApplicationContext(),res,Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), ViewExpenseActivity.class);
                    intent.putExtra("id", tmpId);
                    intent.putExtra("date", tmpDate);
                    startActivity(intent);
                    finish();
                }

            }
        });

        eShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), ViewExpenseActivity.class);
                intent1.putExtra("id",tmpId);
                startActivity(intent1);
                finish();
            }
        });


        arrayAdapter = new ArrayAdapter<String>(this, R.layout.risk_list, types);
        etType.setAdapter(arrayAdapter);
        etType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                // Toast.makeText(getApplicationContext(), "Item: " +item, Toast.LENGTH_SHORT).show();
            }
        });

        initDatePicker();
        eTime.setEndIconOnClickListener(new View.OnClickListener() {
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
                String etime = makeDateString(day, month, year);
                etTime.setText(etime);
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

    private boolean validate() {
        String vType = etType.getText().toString();
        String vAmount = etAmount.getText().toString();
        String vTime = etTime.getText().toString();
        if(vType.isEmpty() || vAmount.isEmpty() || vTime.isEmpty()){
            if(vType.isEmpty()){
                Toast.makeText(this, "Please Insert Type!", Toast.LENGTH_SHORT).show();
                return false;
            }else if(vAmount.isEmpty()){
                Toast.makeText(this, "Please Insert Amount!", Toast.LENGTH_SHORT).show();
                return false;
            }else if(vTime.isEmpty()){
                Toast.makeText(this, "Please insert Time!", Toast.LENGTH_SHORT).show();
                return false;
            }else{
                Toast.makeText(this, "Please Insert Missing Details!", Toast.LENGTH_SHORT).show();
                return false;
            }
        }else return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent2 = new Intent(getApplicationContext(), ViewExpenseActivity.class);
        intent2.putExtra("id",tmpId);
        startActivity(intent2);
        super.onBackPressed();
    }
}