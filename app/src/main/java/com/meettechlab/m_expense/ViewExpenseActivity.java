package com.meettechlab.m_expense;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.meettechlab.m_expense.adapters.ExpenseAdapter;
import com.meettechlab.m_expense.adapters.HomeAdapter;
import com.meettechlab.m_expense.models.ModelExpense;
import com.meettechlab.m_expense.models.ModelName;

import java.util.ArrayList;

public class ViewExpenseActivity extends AppCompatActivity {

    private TextView noExpenses;
    private RecyclerView recyclerView;
    private ArrayList<ModelExpense> dataholder;
    private AlertDialog.Builder builder;
    private int tmpID;
    private String tmpDate;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_expense);

        getSupportActionBar().setTitle("All Expenses");

        builder = new AlertDialog.Builder(this);

        recyclerView=findViewById(R.id.expense_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        tmpID = intent.getIntExtra("id",0);
        tmpDate = intent.getStringExtra("date");

        Cursor cursor=new dbmanager(this).readallexpensedata(tmpID);
        dataholder=new ArrayList<>();

        if(cursor.getCount()==0){
            noExpenses = findViewById(R.id.no_expense);
            noExpenses.setVisibility(View.VISIBLE);
        }else {
            while(cursor.moveToNext())
            {
                recyclerView.setVisibility(View.VISIBLE);
                ModelExpense obj=new ModelExpense(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getInt(5));
                dataholder.add(obj);
            }
        }


        ExpenseAdapter adapter=new ExpenseAdapter(dataholder, getApplicationContext());
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_delete:
                getConfirmation();
                break;
            case R.id.menu_add:
                Intent intent = new Intent(getApplicationContext(), AddExpenseActivity.class);
                intent.putExtra("id", tmpID);
                intent.putExtra("date", tmpDate);
                startActivity(intent);
                finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void getConfirmation() {

        builder.setMessage("Do you want to delete all expenses ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String res=new dbmanager(getApplicationContext()).delallexpenserecord(tmpID);
                        Toast.makeText(getApplicationContext(),res,Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), IndividualDetailsActivity.class);
                        intent.putExtra("id", tmpID);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.setTitle("CONFIRM");
        alert.show();
    }

    @Override
    public void onBackPressed() {
        Intent intent1 = new Intent(getApplicationContext(), IndividualDetailsActivity.class);
        intent1.putExtra("id", tmpID);
        startActivity(intent1);
        super.onBackPressed();
    }
}