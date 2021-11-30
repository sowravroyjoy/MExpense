package com.meettechlab.m_expense.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meettechlab.m_expense.IndividualDetailsActivity;
import com.meettechlab.m_expense.R;
import com.meettechlab.m_expense.models.ModelExpense;
import com.meettechlab.m_expense.models.ModelName;

import java.util.ArrayList;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.MyViewHolder> {
    private ArrayList<ModelExpense> expenses;
    private Context context;


    public ExpenseAdapter(ArrayList<ModelExpense> expenses, Context context) {
        this.expenses = expenses;
        this.context = context;
    }

    @NonNull
    @Override
    public ExpenseAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_list, parent, false);
        ExpenseAdapter.MyViewHolder myViewHolder = new ExpenseAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseAdapter.MyViewHolder holder, int position) {
        final ModelExpense modelExpense = expenses.get(position);

        holder.type.setText(modelExpense.getType());
        holder.amount.setText(modelExpense.getAmount());
        holder.time.setText(modelExpense.getTime());
        holder.comment.setText(modelExpense.getComment());


        /*holder.Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, IndividualDetailsActivity.class);
                intent.putExtra("id",modelName.getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView type,amount,time,comment;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            type = itemView.findViewById(R.id.v_type);
            amount = itemView.findViewById(R.id.v_amount);
            time = itemView.findViewById(R.id.v_time);
            comment = itemView.findViewById(R.id.v_comment);


        }

    }
}
