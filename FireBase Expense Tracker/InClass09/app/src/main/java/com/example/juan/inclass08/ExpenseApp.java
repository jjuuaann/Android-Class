package com.example.juan.inclass08;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ExpenseApp extends Fragment {

    private OnFragmentInteractionListener mListener;
    ListView listExpense;
    public Adapter adapter;
    ArrayList<Expense> list;

    public ExpenseApp() {
        // Required empty public constructor
    }

    public void sendArray(ArrayList<Expense> list){
        this.list.clear();
        this.list.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expense_app, container, false);
        listExpense = (ListView)view.findViewById(R.id.lvExpenses);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listExpense.setEmptyView(getActivity().findViewById(R.id.tvEmpty));

            adapter = new Adapter(getContext(), ((MainActivity)mListener).expenses);

        listExpense.setAdapter(adapter);
        adapter.setNotifyOnChange(true);

        getActivity().findViewById(R.id.floatingActionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCreateNewExpense();
            }
        });

        listExpense.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.onShowExpense(((MainActivity)mListener).expenses.get(position));
            }
        });

        listExpense.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                mListener.onDeleteExpense(((MainActivity)mListener).expenses.get(position));

                return true;
            }
        });


    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onCreateNewExpense();
        void onShowExpense(Expense expense);
       // void onFragmentInteraction(Uri uri);
        void onDeleteExpense(Expense expense);
       // void onTextChanged(String text);
    }


    public interface ExpenseAppListener {
    }
}
