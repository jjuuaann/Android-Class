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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import static com.example.juan.inclass08.R.id.etAddExpenseName;


public class ShowExpense extends Fragment {
    TextView tvName, tvCategory, tvAmount, tvDate;
    Expense currentExpense;

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mConditionRef = mRootRef.child(("condition"));

    private OnFragmentInteractionListener mListener;

    public ShowExpense() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_expense, container, false);

        tvName = (TextView) view.findViewById(R.id.tvShowName);
        tvCategory = (TextView) view.findViewById(R.id.tvShowCategory);
        tvAmount = (TextView) view.findViewById(R.id.tvShowAmount);
        tvDate = (TextView) view.findViewById(R.id.tvShowDate);

        return view;
    }

    /*@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }*/

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().findViewById(R.id.btnShowClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClose();
            }
        });
        showData();

        getActivity().findViewById(R.id.btnShowEdit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mListener.onEdit(currentExpense);

                /*mConditionRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                       mListener.onCreateEditExpense();


                        *//*DataSnapshot nodeDataSnapshot = dataSnapshot.getChildren().iterator().next();
                        String key = nodeDataSnapshot.getKey();
                        String path = "/" + dataSnapshot.getKey() + "/" + key;
                        HashMap<String, Object> result = new HashMap<>();
                        result.put("Status", "COMPLETED");
                        mRootRef.child(path).updateChildren(result);

                        String text = dataSnapshot.getValue(String.class);*//*
                     // mRootRef.setValue(AddExpense().etAddExpenseName.setText(text));

                        //childUpdates.put("/" +key, expense);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });*/

            }
        });

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

    public void showData()
    {
        tvName.setText(currentExpense.getName());
        tvCategory.setText(currentExpense.getCategory());
        tvAmount.setText("$" + String.valueOf(currentExpense.getAmount()));
        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String newDate = formatter.format(currentExpense.getDate());
        tvDate.setText(newDate);
    }




    public interface OnFragmentInteractionListener {
        void onEdit(Expense expense);
        void onClose();
    }
}
