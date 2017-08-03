package com.example.juan.inclass08;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddExpense.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */


public class AddExpense extends Fragment {

    Spinner categories;
    Button btnAdd, btnCancel;
    EditText addName, addAmount;

    private OnFragmentInteractionListener mListener;

    public AddExpense() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_add_expense, container, false);
        categories = (Spinner) view.findViewById(R.id.spinnerAddCategory);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        addName = (EditText)getActivity().findViewById(R.id.etAddExpenseName);
        addAmount = (EditText)getActivity().findViewById(R.id.tvAddExpenseAmount);

        List<String> spinnerArray = new ArrayList<String>();
        spinnerArray = Arrays.asList(getResources().getStringArray(R.array.categories));
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, spinnerArray);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        categories.setAdapter(spinnerArrayAdapter);

        getActivity().findViewById(R.id.btnAddExpense).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!addName.getText().toString().equals("") && !addAmount.getText().toString().equals("")){
                    Expense newExpense = new Expense(addName.getText().toString(), categories.getSelectedItem().toString(), Double.parseDouble(addAmount.getText().toString()), Calendar.getInstance().getTime());
                    mListener.AddExpense(newExpense);
                }else
                    Toast.makeText(getContext(), getResources().getString(R.string.info_missing), Toast.LENGTH_SHORT).show();
               }
        });
        getActivity().findViewById(R.id.btnAddCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.OnCancel();
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
        void AddExpense(Expense expense);
        void OnCancel();
        //void onTextChanged(String text);
    }


}
