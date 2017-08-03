/*
Juan Monsalve
Expense Activity
inClass09
 */


package com.example.juan.inclass08;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements ExpenseApp.OnFragmentInteractionListener,
        AddExpense.OnFragmentInteractionListener,
        ShowExpense.OnFragmentInteractionListener,
        EditFrag.OnFragmentInteractionListener {

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

        expensesRef.child(expense._id).removeValue();
        Toast.makeText(this, getResources().getString(R.string.deleted), Toast.LENGTH_SHORT).show();
    }

   /* @Override
    public void onTextChanged(String text) {

    }
*/
    @Override
    public void onCreateNewExpense() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.actexpenseContainer, new AddExpense(), "AddExpense")
                .addToBackStack(null)
                .commit();
    }

    public void onCreateEditExpense(){
        getSupportFragmentManager().beginTransaction().replace(R.id.actEditExpenseContainer,new AddExpense(), "EditExpense")
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
        childUpdates.put("/" +key, expense);

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
                    expense._id = child.getKey();
                    retrievedExpenses.add(expense);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run()
                    {
                        expenses = retrievedExpenses;
                        expenseApp.adapter = new Adapter(MainActivity.this, retrievedExpenses);
                        expenseApp.adapter.expenses = retrievedExpenses;
                        expenseApp.adapter.setNotifyOnChange(true);
                        expenseApp.listExpense.setAdapter(expenseApp.adapter);
                        expenseApp.adapter.notifyDataSetChanged();
                    }
                });



                Log.d("ExpensesActivity", expenses.toString());
                Log.d("ExpensesActivity", "Data Changed");
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
    public void onEdit(Expense expense) {

        EditFrag edit = new EditFrag();
        edit.currentExpense = expense;

        //Toast.makeText(this, expense.getName(), Toast.LENGTH_SHORT).show();


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.actexpenseContainer, edit, "EditFrag")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onClose() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    protected void onStart() {
        super.onStart();

        referenceListener();
    }


    @Override
    public void OnUpdated() {
        expenseApp = new ExpenseApp();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.actexpenseContainer, expenseApp, "ExpenseApp")
                .commit();

    }
}
//expense._id = child.getKey();


/*


*/
/*
In Class 08
Expense app
Juan Monsalve
Brittany Hatter
 *//*


package com.example.juan.inclass08;

        import android.content.Intent;
        import android.net.Uri;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.widget.Toast;

        import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ExpenseApp.OnFragmentInteractionListener,
        AddExpense.OnFragmentInteractionListener,
        ShowExpense.OnFragmentInteractionListener {

    public ArrayList<Expense> expenses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expenses = new ArrayList<Expense>();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, new ExpenseApp(), "ExpenseApp").commit();

        getSupportFragmentManager().executePendingTransactions();

        //getSupportFragmentManager().beginTransaction().add(MainActivity.this, ExpenseActivity.class);








    */
/*@Override
    protected void onStart() {
        super.onStart();

*//*

    }

    @Override
    public void onDeleteExpense(Expense expense) {
        expenses.remove(expense);
        ExpenseApp ea = (ExpenseApp) getSupportFragmentManager().findFragmentByTag("ExpenseApp");
        ea.adapter.notifyDataSetChanged();
        Toast.makeText(this, getResources().getString(R.string.deleted), Toast.LENGTH_SHORT).show();
    }





    @Override
    public void onCreateNewExpense() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new AddExpense(), "AddExpense")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onShowExpense(Expense expense) {
        ShowExpense show = new ShowExpense();
        show.currentExpense = expense;


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, show, "ShowExpense")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0)
            getSupportFragmentManager().popBackStack();
        else
            super.onBackPressed();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void AddExpense(Expense expense) {
        expenses.add(expense);

        getSupportFragmentManager().popBackStack();
       */
/* getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new ExpenseApp(), "ShowExpense")
                .addToBackStack(null)
                .commit();
        getSupportFragmentManager().executePendingTransactions();

        ExpenseApp exp = (ExpenseApp) getSupportFragmentManager().findFragmentByTag("ShowExpense");

        if(exp != null){
            exp.sendArray(expenses);
        }
        ExpenseApp f = (ExpenseApp)getSupportFragmentManager().findFragmentByTag("ShowExpense");
        //Expense exp = (Expense) getSupportFragmentManager().findFragmentById(R.layout.fragment_expense_app);


       // getSupportFragmentManager().popBackStack();*//*

    }

    @Override
    public void OnCancel() {
        getSupportFragmentManager().popBackStack();

    }


    @Override
    public void onClose() {
        getSupportFragmentManager().popBackStack();
    }
}

*/
