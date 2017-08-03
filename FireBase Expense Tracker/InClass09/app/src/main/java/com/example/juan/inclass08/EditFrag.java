package com.example.juan.inclass08;

import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class EditFrag extends Fragment {

    Spinner categories;
    Button btnAdd, btnCancel;
    TextView name, amount;
    EditText addName, addAmount;
    final static String VALUE_KEY = "value";
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference expensesRef = mRootRef.child("expenses");

    Expense currentExpense;

    private OnFragmentInteractionListener mListener;

    public EditFrag() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit, container, false);



        categories = (Spinner) view.findViewById(R.id.spinnerAddCategory);
        name = (EditText) view.findViewById(R.id.etAddExpenseName);
        amount = (EditText) view.findViewById(R.id.etAddExpenseAmount);
       // date = (Date) view.findViewById(R.id.tvAdd)


        return view;
    }

    public void showData()
    {
        name.setText(currentExpense.getName());
        //categories.selected(currentExpense.getCategory());
        amount.setText(String.valueOf(currentExpense.getAmount()));
        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String newDate = formatter.format(currentExpense.getDate());
        //date.setText(newDate);
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
        addAmount = (EditText)getActivity().findViewById(R.id.etAddExpenseAmount);

        List<String> spinnerArray = new ArrayList<String>();
        spinnerArray = Arrays.asList(getResources().getStringArray(R.array.categories));
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, spinnerArray);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        categories.setAdapter(spinnerArrayAdapter);
        Expense.getCategories();

        showData();

         //Expense.name = data.getExtras().getString(VALUE_KEY).toString();

        getActivity().findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!addName.getText().toString().equals("") && !addAmount.getText().toString().equals("")){
                    Expense newExpense = new Expense(addName.getText().toString(), categories.getSelectedItem().toString(),
                            Double.parseDouble(addAmount.getText().toString()),
                            Calendar.getInstance().getTime());
                    expensesRef.child(currentExpense._id).setValue(newExpense);
                    mListener.OnUpdated();
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

    /*private void Edit(EditType type) {
        Intent intent = new Intent("");
        intent.putExtra(TYPE_KEY, type);
    }*/

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void OnUpdated();
        void OnCancel();
    }

}





