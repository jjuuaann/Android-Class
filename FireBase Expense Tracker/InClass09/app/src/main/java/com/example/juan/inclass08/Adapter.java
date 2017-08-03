package com.example.juan.inclass08;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juan on 6/20/2017.
 */


public class Adapter extends ArrayAdapter<Expense> {

    Context context;
    ArrayList<Expense> expenses;

    //int resource;
    public Adapter(Context context, ArrayList<Expense> expenses) {
        super(context, R.layout.expense_list, expenses);
        this.context = context;
        this.expenses = expenses;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //convertView = inflater.inflate(R.layout.expense_list, parent, false);
            convertView = inflater.inflate(R.layout.expense_list, parent, false);
        }

        TextView name = (TextView)convertView.findViewById(R.id.tvName);
        TextView amount = (TextView)convertView.findViewById(R.id.tvAmount);
        //set text
        name.setText(expenses.get(position).getName());
        amount.setText("$" + String.valueOf(expenses.get(position).getAmount()));


        return convertView;
    }
}
