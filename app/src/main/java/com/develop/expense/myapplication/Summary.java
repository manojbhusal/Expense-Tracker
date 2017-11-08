package com.develop.expense.myapplication;

import android.app.DatePickerDialog;
import android.app.Dialog;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import Database.DataHandler;

import static android.content.Context.MODE_PRIVATE;
import static com.develop.expense.myapplication.MyApplication2.adRequest;

/**
 * Created by Shailendra on 12/4/2016.
 */
public class Summary extends DialogFragment implements AdapterView.OnItemSelectedListener {
    public static final String[] spinnerWords = {"Today", "This Week", "This Month", "This Year", "Custom"};
    private Spinner spinner;
    private ListView summaryList;
    private ImageButton selectionParameter;
    private LinearLayout customChoose;
    static final int CALENDER_CODE = 6;
    public static int xyear;
    public static int xmonth;
    public static int xday;
    public static int nameOfDay;
    public static int xyear1;
    public static String xmonth1;
    public static String xday1;
    public static int nameOfDay1;
    public static int xyear2;
    public static String xmonth2;
    public static InterstitialAd interstitialAd;
    LinearLayout layAd;
    public static String xday2;
    public static int nameOfDay2; //xday m n year are for selection from database for custom buttons
    public static int DIALOG_ID = 5;
    public static Button firstCustomDate, secondCustomDate, goButton;
    public static TextView totalText, incomeText, expenseText;
    public static String sendingStartDate;
    public static String sendingEndDate;
    private int total = 0;
    public static EditText searchText;
    public static InterstitialAd interstitial;
   public  static MyApplication2 myApplication2;
    public static int summaryCount=0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_summary, container, false);
//        AdView mAdView = (AdView) view.findViewById(R.id.adView);
//        adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);
//
//// Prepare the Interstitial Ad
//        interstitial = new InterstitialAd(getActivity());
//// Insert the Ad Unit ID
//        interstitial.setAdUnitId(getString(R.string.admob_interstitial_id));
//
//        interstitial.loadAd(adRequest);
//// Prepare an Interstitial Ad Listener
//        interstitial.setAdListener(new AdListener() {
//            public void onAdLoaded() {
//                // Call displayInterstitial() function
//                displayInterstitial();
//            }
//        });
        DIALOG_ID = 5;
        customChoose = (LinearLayout) view.findViewById(R.id.customChoose);
        customChoose.setVisibility(View.GONE);
//        AdView madView=(AdView)view.findViewById(R.id.adView);
//        AdRequest madRequest= new AdRequest.Builder().setRequestAgent("android_studio:ad_template").build();
//
//        madView.loadAd(madRequest);
        layAd = (LinearLayout) view.findViewById(R.id.testL);


        spinner = (Spinner) view.findViewById(R.id.spinner);
        totalText = (TextView) view.findViewById(R.id.totalText);
        incomeText = (TextView) view.findViewById(R.id.incomeText);
        expenseText = (TextView) view.findViewById(R.id.expenseText);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(
                getActivity().getBaseContext(), android.R.layout.simple_spinner_dropdown_item, spinnerWords);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);

        summaryList = (ListView) view.findViewById(R.id.summaryList);

        firstCustomDate = (Button) view.findViewById(R.id.firstCustomDate);
        secondCustomDate = (Button) view.findViewById(R.id.secondCustomDate);
        goButton = (Button) view.findViewById(R.id.goButton);

//        myApplication2.loadAd(getActivity(),layAd);
        //myApplication2= (MyApplication2)getActivity().getApplication();


        firstCustomDate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        DialogFragment newFragment = new SelectDateFragment();
                        DIALOG_ID = 11;
                        newFragment.show(getFragmentManager(), "DatePicker");


                    }
                }
        );

        secondCustomDate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogFragment newFragment = new SelectDateFragment();
                        DIALOG_ID = 12;

                        newFragment.show(getFragmentManager(), "DatePicker");


                    }
                }
        );



        return view;
    }

    private void displayInterstitial() {
        if (interstitial.isLoaded()) {
            interstitial.show();
        }
    }


    public void setSendingStartDate(String sendingStartDate) {
        this.sendingStartDate = sendingStartDate;
    }

    public void setSendingEndDate(String sendingEndDate) {
        Summary.sendingEndDate = sendingEndDate;
    }

    public static class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Calendar calendar = Calendar.getInstance();

            xyear = calendar.get(Calendar.YEAR);
            xmonth = calendar.get(Calendar.MONTH);
            xday = calendar.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, xyear, xmonth, xday);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            xday = dayOfMonth;
            xmonth = monthOfYear;
            xyear = year;
            String sday = dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth + "",
                    smonth = monthOfYear < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1) + "";

            if (DIALOG_ID == 11) {


                firstCustomDate.setText(xyear + "-" + smonth + "-" + sday);
                xday1 = sday;
                xmonth1 = smonth;
                xyear1 = year;
            }//set text of first button


            if (DIALOG_ID == 12) {
                secondCustomDate.setText(xyear + "-" + smonth + "-" + sday);
                xday2 = sday;
                xmonth2 = smonth;
                xyear2 = year;
            }//set text of second button


        }


        public String getStartDate() {
            return sendingStartDate;
        }

        public String getEndDate() {
            return sendingEndDate;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        final DataHandler dataHandler = new DataHandler(getActivity());
        List mList = null;

        int total, income, expense;

        switch (position) {

            case 0:
                mList = dataHandler.getList("today");  //here we should set it to the parameter that the user wants for today
                customChoose.setVisibility(View.GONE);

                try {
                    total = dataHandler.getTotal();
                    income = dataHandler.getIncomeTotal();
                    expense = dataHandler.getExpenseTotal();

                    totalText.setText("Total= " + total);
                    if (total < 0) {
                        totalText.setTextColor(Color.parseColor("#fb5656"));
                    }
                    incomeText.setText("Income= " + income);
                    expenseText.setText("Expense= " + expense);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                break;


            case 1:


                customChoose.setVisibility(View.GONE);
                mList = dataHandler.getList("This Week");  //here we should set it to the paraameter that the user wants for week


                try {
                    total = dataHandler.getTotal();
                    income = dataHandler.getIncomeTotal();
                    expense = dataHandler.getExpenseTotal();


                    totalText.setText("Total= " + total);
                    incomeText.setText("Income= " + income);
                    expenseText.setText("Expense= " + expense);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                break;


            case 2:
                customChoose.setVisibility(View.GONE);
                mList = dataHandler.getList("This Month");  //here we should set it to the paraameter that the user wants for month

                try {
                    total = dataHandler.getTotal();
                    income = dataHandler.getIncomeTotal();
                    expense = dataHandler.getExpenseTotal();

                    totalText.setText("Total= " + total);
                    incomeText.setText("Income= " + income);
                    expenseText.setText("Expense= " + expense);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                break;
            case 3:
                customChoose.setVisibility(View.GONE);
                mList = dataHandler.getList("This Year");  //here we should set it to the paraameter that the user wants for year

                try {
                    total = dataHandler.getTotal();
                    income = dataHandler.getIncomeTotal();
                    expense = dataHandler.getExpenseTotal();

                    totalText.setText("Total= " + total);
                    incomeText.setText("Income= " + income);
                    expenseText.setText("Expense= " + expense);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                break;


            case 4:

                customChoose.setVisibility(View.VISIBLE);


                /////////////////////////////////////////////////////////////////////////////////////////////////////////////
                break;


        }


        goButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int totalCustom;
                        List mListCustom = null;
                        int incomeCustom, expenseCustom;


                        String startDate = firstCustomDate.getText().toString();
                        setSendingStartDate(startDate);

                        String endDate = secondCustomDate.getText().toString();
                        setSendingEndDate(endDate);


                        try {
                            mListCustom = dataHandler.getList("Custom");  //first check the value of date of exists or not then only show the list
                            incomeCustom = dataHandler.getIncomeTotal();
                            expenseCustom = dataHandler.getExpenseTotal();


                            totalCustom = dataHandler.getTotal(/*"This Week"*/);
                            totalText.setText("Total= " + totalCustom);
                            incomeText.setText("Income= " + incomeCustom);
                            expenseText.setText("Expense= " + expenseCustom);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        DisplayAllCustomAdapter displayAllCustomAdapter = new DisplayAllCustomAdapter(getActivity(), R.layout.displayall, mListCustom);

                        summaryList.setAdapter(displayAllCustomAdapter);

                    }
                }
        );


        DisplayAllCustomAdapter displayAllCustomAdapter = new DisplayAllCustomAdapter(getActivity(), R.layout.displayall, mList);

        String spinnerItemName = spinner.getSelectedItem().toString();
        try {
            if (!spinnerItemName.matches("Custom"))
                summaryList.setAdapter(displayAllCustomAdapter);
            displayAllCustomAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }


        if(summaryCount!=1)
        {
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

        summaryCount=summaryCount+1;
        }

    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    }



