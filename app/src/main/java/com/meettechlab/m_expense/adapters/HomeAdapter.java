package com.meettechlab.m_expense.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.meettechlab.m_expense.IndividualDetailsActivity;
import com.meettechlab.m_expense.R;
import com.meettechlab.m_expense.models.ModelDatabase;
import com.meettechlab.m_expense.models.ModelName;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>{

    private ArrayList<ModelDatabase> names;
    private ArrayList<ModelDatabase> allNames;
    private Context context;


    public HomeAdapter(ArrayList<ModelDatabase> names, Context context) {
        this.names = names;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.name_list, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final ModelDatabase modelName = names.get(position);

        holder.Name.setText(modelName.getName());


        holder.Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, IndividualDetailsActivity.class);
                intent.putExtra("id",modelName.getTrip_id());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return names.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView Name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Name = itemView.findViewById(R.id.model_name);

        }

    }
}


