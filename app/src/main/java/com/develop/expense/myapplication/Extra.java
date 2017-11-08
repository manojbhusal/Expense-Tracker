package com.develop.expense.myapplication;

import android.app.DatePickerDialog;
import android.app.Dialog;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import Database.DataHandler;
import Model.MoneyModel;

public class Extra extends Fragment implements AdapterView.OnItemSelectedListener {
private Button exportToFile;
    private Spinner spinner;
    List<MoneyModel> mList;
    public static final String[] spinnerWords = {"Today", "This Week", "This Month", "This Year"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_extra, container, false);
        exportToFile=(Button)view.findViewById(R.id.exportToFile);
//        Summary.interstitial.loadAd(Summary.adRequest);
        spinner = (Spinner) view.findViewById(R.id.spinner);
//        AdView adView=(AdView)view.findViewById(R.id.adView);
//        AdRequest adRequest= new AdRequest.Builder().setRequestAgent("android_studio:ad_template").build();
//
//        adView.loadAd(adRequest);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(
                getActivity().getBaseContext(), android.R.layout.simple_spinner_dropdown_item, spinnerWords);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);
        DataHandler dataHandler= new DataHandler(getContext());
       mList=  dataHandler.getList("all");
        exportToFile.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        DataHandler dataHandler= new DataHandler(getContext());
                        String spinnerItemName = spinner.getSelectedItem().toString();

                        if(spinnerItemName.matches("Today"))

                        {
                            mList=  dataHandler.getList("today");
                        }


                        else if (spinnerItemName.matches("This Week"))  {
                            mList=  dataHandler.getList("This Week");
                        }
                        else if (spinnerItemName.matches("This Month"))  {
                            mList=  dataHandler.getList("This Month");
                        }

                        else if (spinnerItemName.matches("This Year"))  {
                            mList=  dataHandler.getList("This Year");
                        }







                        int incomeTotal= dataHandler.getIncomeTotal();
                        int expenseTotal= dataHandler.getExpenseTotal();
                        int total= dataHandler.getTotal();

                        StringBuilder builder= new StringBuilder();
                       for(int i=0;i<mList.size();i++) {
                           String mcategory= mList.get(i).getCategory();
                           String mdescription= mList.get(i).getDescription();
                           String mdate= mList.get(i).getDate();
                           String mIEtype= mList.get(i).getIEType();
                           String mvalue = mList.get(i).getAmount() + "";
                           String finalItemString= "Category: "+mcategory+", "+"\t"+"Amount= "+ mvalue+", "+"\t"+ "Description: "+mdescription+", "+"\t"+"Date: "+mdate+"\n\n";


                               builder.append(finalItemString);


                       }
                        String finalText= "Data are given as \n\n"+builder.toString()+"\n\n"+  "Income: "+incomeTotal+", "+"\t"+"Expense= "+ expenseTotal+", "+"\t"+ "Total: "+total;

                        Intent sendIntent = new Intent(Intent.ACTION_SEND);
                        sendIntent.setType("message/rfc822");
                        sendIntent.putExtra(Intent.EXTRA_EMAIL,"");
                        sendIntent.putExtra(Intent.EXTRA_SUBJECT,"");
                        sendIntent.putExtra(Intent.EXTRA_TEXT, finalText);

                        Log.v("bibek",builder.toString());

                        sendIntent.setType("text/plain");
                        startActivity(Intent.createChooser(sendIntent, "Share Expense"));


                    }
                }
        );

        final InterstitialAd mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.admob_interstitial_id));
        AdRequest adRequestInter = new AdRequest.Builder().build();
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                mInterstitialAd.show();
            }
        });
        mInterstitialAd.loadAd(adRequestInter);



   return view;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {



    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}