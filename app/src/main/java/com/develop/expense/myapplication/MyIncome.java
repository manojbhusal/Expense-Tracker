package com.develop.expense.myapplication;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
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

import Database.MyHandler;
import Utils.ConstantsCatogories;

public class MyIncome extends AppCompatActivity {
    public static  GridView gridViewIncome;
    private Button button;
    List list= new ArrayList();
    public  static CustomAdapter customAdapter;
    LinearLayout layAd;
    MyApplication2 myApplication2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_income);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Income");


        gridViewIncome=(GridView)findViewById(R.id.listViewIncome);
        final MyHandler myhandler= new MyHandler(getApplicationContext());

        List incomeCategoriesList= myhandler.getIncomeCategories();
        incomeCategoriesList.add("Salary");
        incomeCategoriesList.add("Business");
        incomeCategoriesList.add("Bonus");
        incomeCategoriesList.add("Extras");
        incomeCategoriesList.add("Donation");
        incomeCategoriesList.add("Profit");



        layAd = (LinearLayout)findViewById(R.id.testL);

        customAdapter=new CustomAdapter(getApplicationContext(),R.layout.customitem,incomeCategoriesList);
        gridViewIncome.setAdapter(customAdapter);
        gridViewIncome.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //open a page with the item where income comes to add in database
                         String category=parent.getItemAtPosition(position).toString();

                        Intent intent= new Intent(MyIncome.this,DetailAdd.class);

                        Bundle extras= new Bundle();
                        extras.putString("type","income");
                        extras.putString("category",category);
                        intent.putExtras(extras);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();


                    }
                }
        );


//        myApplication2= (MyApplication2)getApplication();
//
//       try{ layAd.addView(MyApplication2.adView);
//           myApplication2.loadAd(MyIncome.this,layAd);
//
//
//       }catch (Exception e){}
//        myApplication2.loadAd(MyIncome.this,layAd);
//




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            final MyHandler myhandler= new MyHandler(getApplicationContext());

            AlertDialog.Builder addIncome= new AlertDialog.Builder(MyIncome.this);
            addIncome.setTitle("Add Income Category").setIcon(R.mipmap.ic_launcher).setCancelable(true);
            final EditText incomeInput= new EditText(MyIncome.this);
            incomeInput.setFilters(new InputFilter[] {new InputFilter.LengthFilter(16)});

            LinearLayout.LayoutParams lp= new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            incomeInput.setLayoutParams(lp);
            addIncome.setView(incomeInput);
            addIncome.setMessage("Enter a new category for income.").setPositiveButton(
                    "Done", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String minput= incomeInput.getText().toString();
                            myhandler.addCategoryIncome(minput);
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);

                        }
                    }
            );
            addIncome.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }

            );

            addIncome.show();

            return true;
        }
        if(id==android.R.id.home)
        {
            Intent intent = new Intent(MyIncome.this, MainActivity.class);
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
//        final InterstitialAd mInterstitialAd = new InterstitialAd(MyIncome.this);
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
