package com.meettechlab.m_expense;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.appcompat.widget.SearchView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class AdvanceSearchDialog extends AppCompatDialogFragment {
    private SearchView adName;
    private SearchView adDestination;
    private SearchView adDate;
    private DatePickerDialog datePickerDialog;
    private AdvanceSearchListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        AlertDialog.Builder advanceSearch = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.advance_search_dialog, null);

        advanceSearch.setView(view).setTitle("Advance Search")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Search", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       CharSequence cName = adName.getQuery();
                       CharSequence cDestination = adDestination.getQuery();
                       CharSequence cDate = adDate.getQuery();
                       listener.applySearch(cName,cDestination,cDate);
                    }
                });

        adName = view.findViewById(R.id.ad_name);
        adDestination = view.findViewById(R.id.ad_destination);
        adDate = view.findViewById(R.id.ad_date);




        initDatePicker();
        adDate.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });


        return advanceSearch.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (AdvanceSearchListener) context;
        }catch (ClassCastException e){
             throw  new ClassCastException(context.toString() + "must implement AdvanceSearchListener");
        }

    }

    public interface AdvanceSearchListener{
            void applySearch(CharSequence cName, CharSequence cDestination, CharSequence cDate);
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
                adDate.setQuery(dDate,false);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = android.app.AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(getActivity(), style, dateSetListener, year, month, day);
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
