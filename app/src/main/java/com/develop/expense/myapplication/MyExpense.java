package com.develop.expense.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.List;

import Database.MyHandler2;

public class MyExpense extends AppCompatActivity {
    private GridView gridViewExpense;
    List list= new ArrayList();
    private Button button;
    LinearLayout layAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_expense);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Expense");
        final MyHandler2 myhandler2 = new MyHandler2(getApplicationContext());
        gridViewExpense = (GridView) findViewById(R.id.listViewExpense);

        List expenseCategoriesList = myhandler2.getExpenseCategories();
        expenseCategoriesList.add("Fun");
        expenseCategoriesList.add("Household");
        expenseCategoriesList.add("Personal");
        expenseCategoriesList.add("Medical");
        expenseCategoriesList.add("Education");
        expenseCategoriesList.add("Social");
        expenseCategoriesList.add("Food");
        expenseCategoriesList.add("Clothing");
        expenseCategoriesList.add("Gadget");

        layAd = (LinearLayout)findViewById(R.id.testL);

        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), R.layout.customitem, expenseCategoriesList);
        gridViewExpense.setAdapter(customAdapter);
        gridViewExpense.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //open a page with the item where income comes to add in database
                        String category = parent.getItemAtPosition(position).toString();

                        Intent intent = new Intent(MyExpense.this, DetailAdd.class);

                        Bundle extras = new Bundle();
                        extras.putString("type", "expense");
                        extras.putString("category", category);
                        intent.putExtras(extras);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        startActivity(intent);
                        finish();

                    }
                }
        );

//        MyApplication2 myApplication2= (MyApplication2)getApplication();
//        myApplication2.loadAd(MyExpense.this,layAd);
//

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            final MyHandler2 myhandler2= new MyHandler2(getApplicationContext());

            AlertDialog.Builder addExpense= new AlertDialog.Builder(MyExpense.this);
            addExpense.setTitle("Add Expense Category").setIcon(R.mipmap.ic_launcher).setCancelable(true);
            final EditText expenseInput= new EditText(MyExpense.this);
expenseInput.setFilters(new InputFilter[] {new InputFilter.LengthFilter(16)});
            LinearLayout.LayoutParams lp= new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            expenseInput.setLayoutParams(lp);
            addExpense.setView(expenseInput);
            addExpense.setMessage("Enter a new category for Expense.").setPositiveButton(
                    "Done", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String minput= expenseInput.getText().toString();
                            myhandler2.addCategoryExpense(minput);
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);

                        }
                    }
            );
            addExpense.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }

            );

            addExpense.show();





            return true;
        }
        if(id==android.R.id.home)
        {
            Intent intent = new Intent(MyExpense.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        return true;


    }
        return super.onOptionsItemSelected(item);
}


//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//
//
//        final InterstitialAd mInterstitialAd = new InterstitialAd(MyExpense.this);
//        mInterstitialAd.setAdUnitId(getResources().getString(R.string.admob_interstitial_id));
//        AdRequest adRequestInter = new AdRequest.Builder().build();
//        mInterstitialAd.setAdListener(new AdListener() {
//            @Override
//            public void onAdLoaded() {
//                mInterstitialAd.show();
//            }
//        });
//        mInterstitialAd.loadAd(adRequestInter);
//
//    }

}
