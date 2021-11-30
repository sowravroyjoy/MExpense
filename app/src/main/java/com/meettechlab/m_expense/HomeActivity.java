package com.meettechlab.m_expense;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.meettechlab.m_expense.adapters.HomeAdapter;
import com.meettechlab.m_expense.models.ModelDatabase;
import com.meettechlab.m_expense.models.ModelName;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements AdvanceSearchDialog.AdvanceSearchListener {

    private TextView noFile;
    RecyclerView recyclerView;
    ArrayList<ModelDatabase> dataholder;
    AlertDialog.Builder builder;
    HomeAdapter adapter;


    private SearchView searchView;

    ModelDatabase selectionFilter;
    private String currentSearchText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        noFile = findViewById(R.id.no_file);
        searchView = findViewById(R.id.home_search);


        getSupportActionBar().setTitle("All Trips");

        builder = new AlertDialog.Builder(this);
        selectionFilter = new ModelDatabase();


        recyclerView = findViewById(R.id.home_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        dataholder = new ArrayList<>();
        Cursor cursor = new dbmanager(getApplicationContext()).readalldata();
        if (cursor.getCount() == 0) {
            noFile.setVisibility(View.VISIBLE);
        } else {
            while (cursor.moveToNext()) {
                recyclerView.setVisibility(View.VISIBLE);
                ModelDatabase obj = new ModelDatabase(cursor.getInt(0), cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));
                dataholder.add(obj);
            }
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                currentSearchText = newText;

                ArrayList<ModelDatabase> filteredDatabase = new ArrayList<ModelDatabase>();

                for(ModelDatabase modelDatabase: dataholder){
                    if(modelDatabase.getName().toLowerCase().contains(newText.toLowerCase())){
                        filteredDatabase.add(modelDatabase);
                    }
                }
                adapter = new HomeAdapter(filteredDatabase,getApplicationContext());
                recyclerView.setAdapter(adapter);
                //adapter.getFilter().filter(newText);
                return false;
            }
        });

        adapter = new HomeAdapter(dataholder,getApplicationContext());
        recyclerView.setAdapter(adapter);


    }

    private void filterList(String stringName, String stringDestination,String stringDate){


        selectionFilter.setName(stringName);
        selectionFilter.setDestination(stringDestination);
        selectionFilter.setDate(stringDate);

        ArrayList<ModelDatabase> filteredDatabase = new ArrayList<ModelDatabase>();

        for(ModelDatabase modelDatabase: dataholder){
            if(!selectionFilter.getName().equals("") && !selectionFilter.getDestination().equals("") && !selectionFilter.getDate().equals("")) {
                if (modelDatabase.getName().toLowerCase().contains(selectionFilter.getName().toLowerCase()) && modelDatabase.getDestination().toLowerCase().contains(selectionFilter.getDestination().toLowerCase()) && modelDatabase.getDate().toLowerCase().contains(selectionFilter.getDate().toLowerCase())) {
                    if (currentSearchText.equals("")) {
                        filteredDatabase.add(modelDatabase);
                    } else {
                        if (modelDatabase.getName().toLowerCase().contains(currentSearchText.toLowerCase())) {
                            filteredDatabase.add(modelDatabase);
                        }
                    }
                }
            }else if(!selectionFilter.getName().equals("") && !selectionFilter.getDestination().equals("") && selectionFilter.getDate().equals("")) {
                if (modelDatabase.getName().toLowerCase().contains(selectionFilter.getName().toLowerCase()) && modelDatabase.getDestination().toLowerCase().contains(selectionFilter.getDestination().toLowerCase()) ) {
                    if (currentSearchText.equals("")) {
                        filteredDatabase.add(modelDatabase);
                    } else {
                        if (modelDatabase.getName().toLowerCase().contains(currentSearchText.toLowerCase())) {
                            filteredDatabase.add(modelDatabase);
                        }
                    }
                }
            }else if(!selectionFilter.getName().equals("") && selectionFilter.getDestination().equals("") && selectionFilter.getDate().equals("")) {
                if (modelDatabase.getName().toLowerCase().contains(selectionFilter.getName().toLowerCase())  ) {
                    if (currentSearchText.equals("")) {
                        filteredDatabase.add(modelDatabase);
                    } else {
                        if (modelDatabase.getName().toLowerCase().contains(currentSearchText.toLowerCase())) {
                            filteredDatabase.add(modelDatabase);
                        }
                    }
                }
            }else if(selectionFilter.getName().equals("") && !selectionFilter.getDestination().equals("") && !selectionFilter.getDate().equals("")) {
                if ( modelDatabase.getDestination().toLowerCase().contains(selectionFilter.getDestination().toLowerCase()) && modelDatabase.getDate().toLowerCase().contains(selectionFilter.getDate().toLowerCase())) {
                    if (currentSearchText.equals("")) {
                        filteredDatabase.add(modelDatabase);
                    } else {
                        if (modelDatabase.getName().toLowerCase().contains(currentSearchText.toLowerCase()) ) {
                            filteredDatabase.add(modelDatabase);
                        }
                    }
                }
            }else if(selectionFilter.getName().equals("") && selectionFilter.getDestination().equals("") && !selectionFilter.getDate().equals("")) {
                if (modelDatabase.getDate().toLowerCase().contains(selectionFilter.getDate().toLowerCase())) {
                    if (currentSearchText.equals("")) {
                        filteredDatabase.add(modelDatabase);
                    } else {
                        if (modelDatabase.getName().toLowerCase().contains(currentSearchText.toLowerCase())) {
                            filteredDatabase.add(modelDatabase);
                        }
                    }
                }
            }else if(selectionFilter.getName().equals("") && selectionFilter.getDestination().equals("") && selectionFilter.getDate().equals("")) {
                    if (currentSearchText.equals("")) {
                        filteredDatabase.add(modelDatabase);
                    } else {
                        if (modelDatabase.getName().toLowerCase().contains(currentSearchText.toLowerCase())) {
                            filteredDatabase.add(modelDatabase);
                        }
                }
            }
        }
        adapter = new HomeAdapter(filteredDatabase,getApplicationContext());
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void applySearch(CharSequence cName, CharSequence cDestination, CharSequence cDate) {
        searchView.setQuery("",false);
        searchView.clearFocus();
        filterList(cName.toString(),cDestination.toString(),cDate.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_delete:
                getConfirmation();
                break;
            case R.id.menu_cloud:
                sendCloud();
                break;
            case R.id.menu_add:
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            case R.id.menu_advance:
                advance();
                break;

        }

        return super.onOptionsItemSelected(item);
    }


    private void getConfirmation() {

        builder.setMessage("Do you want to delete all entries ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String res=new dbmanager(getApplicationContext()).delallrecord();
                        Toast.makeText(getApplicationContext(),res,Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
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

    private void sendCloud() {

        builder.setMessage("Do you want to upload data into Cloud ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //String res=new dbmanager(getApplicationContext()).delallrecord();
                        Toast.makeText(getApplicationContext(),"Coming Soon...",Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                        //startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        //finish();
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

    private void advance() {
        AdvanceSearchDialog advanceSearchDialog = new AdvanceSearchDialog();
        advanceSearchDialog.show(getSupportFragmentManager(),"Advance Search");
    }



}