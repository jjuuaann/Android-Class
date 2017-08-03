package com.example.juan.inclass08;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ExpenseActivity{

}
/*
public class ExpenseActivity extends AppCompatActivity implements ExpenseApp.OnFragmentInteractionListener,
        AddExpense.OnFragmentInteractionListener,
        ShowExpense.OnFragmentInteractionListener,
        EditFrag.OnFragmentInteractionListener{

    public ArrayList<Expense> expenses;
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference expensesRef = rootRef.child("expenses");
    ExpenseApp expenseApp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (expenses == null);
        expenses = new ArrayList<Expense>();
        setContentView(R.layout.activity_expense);

        expenseApp = new ExpenseApp();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.actexpenseContainer, expenseApp, "ExpenseApp")
                .commit();

    }

    @Override
    public void onDeleteExpense(final Expense expense) {

        Toast.makeText(this, getResources().getString(R.string.deleted), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateNewExpense() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.actexpenseContainer, new AddExpense(), "AddExpense")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onShowExpense(Expense expense) {
        ShowExpense show = new ShowExpense();
        show.currentExpense = expense;

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.actexpenseContainer, show, "ShowExpense")
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onTextChanged(String text) {

    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0)
            getSupportFragmentManager().popBackStack();
        else
            super.onBackPressed();
    }

    @Override
    public void AddExpense(Expense expense) {
        String key = expensesRef.push().getKey();

        Map<String, Object> childUpdates = new HashMap<>();

        expensesRef.updateChildren(childUpdates);

        getSupportFragmentManager().popBackStack();
    }

    private void referenceListener()
    {
        expensesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final ArrayList<Expense> retrievedExpenses = new ArrayList<Expense>();

                for (DataSnapshot child : dataSnapshot.getChildren())
                {
                    Log.d("ExpensesAcivity", "Key: " + child.getKey());
                   Expense expense = child.getValue(Expense.class);

                    retrievedExpenses.add(expense);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run()
                    {
                        expenses = retrievedExpenses;
                        expenseApp.adapter = new Adapter(ExpenseActivity.this, retrievedExpenses);
                        expenseApp.adapter.expenses = retrievedExpenses;
                        expenseApp.adapter.setNotifyOnChange(true);
                        expenseApp.listExpense.setAdapter(expenseApp.adapter);
                        expenseApp.adapter.notifyDataSetChanged();
                    }
                });



                Log.d("ExpensesActivity", expenses.toString());
                Log.d("ExpensesActivity", "Data Changed!!");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void OnCancel() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onClose() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onCreateEditExpense() {

    }

    @Override
    protected void onStart() {
        super.onStart();

        referenceListener();
    }
}
//expense._id = child.getKey();*/
