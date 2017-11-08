package com.develop.expense.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.List;

import Database.DataHandler;

/**
 * Created by Shailendra on 11/30/2016.
 */
public class Firstview extends Fragment implements AdapterView.OnItemSelectedListener {
    private Spinner spinner;
    private TextView selectionParameter, totalText, incomeText, expenseText;
    private ListView recentList;
    private Button addIncome, addExpense;
    public static final String[] spinnerWords = {"Day", "Month", "Year"};
    LinearLayout layAd;
  public  DisplayAllCustomAdapter displayAllCustomAdapter;
    List mList;
    DataHandler dataHandler;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_firstview, container, false);
        //  spinner= (Spinner)view.findViewById(R.id.spinner);
        dataHandler = new DataHandler(getContext());
        recentList = (ListView) view.findViewById(R.id.recentList);
        addIncome = (Button) view.findViewById(R.id.addIncome);
        addExpense = (Button) view.findViewById(R.id.addExpense);
        totalText = (TextView) view.findViewById(R.id.totalText);
        incomeText = (TextView) view.findViewById(R.id.incomeText);
        expenseText = (TextView) view.findViewById(R.id.expenseText);
        layAd = (LinearLayout)view. findViewById(R.id.testL);

//        AdView adView=(AdView)view.findViewById(R.id.adView);
//        AdRequest adRequest= new AdRequest.Builder().build();
//
//        adView.loadAd(adRequest);


//        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(
//               getActivity().getApplicationContext(),android.R.layout.simple_spinner_dropdown_item, spinnerWords);
//        spinner.setAdapter(spinnerAdapter);
//        spinner.setOnItemSelectedListener(this);


        addIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MyIncome.class));


            }
        });

        addExpense.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), MyExpense.class));

                    }
                }
        );
        mList = dataHandler.getList("today");
        int total = dataHandler.getTotal();
        int income = dataHandler.getIncomeTotal();
        int expense = dataHandler.getExpenseTotal();
        totalText.setText(total+"");
        incomeText.setText(income+"");
        expenseText.setText(expense+"");
       displayAllCustomAdapter = new DisplayAllCustomAdapter(getContext(), R.layout.displayall, mList);
        recentList.setAdapter(displayAllCustomAdapter);
        displayAllCustomAdapter.notifyDataSetChanged();


        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:

                selectionParameter.setText(parent.getItemAtPosition(position) + "");
            case 1:

                selectionParameter.setText(parent.getItemAtPosition(position) + "");

            case 2:

                selectionParameter.setText(parent.getItemAtPosition(position) + "");

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


//    @Override
//    public void onResume() {
//        super.onResume();
//        displayAllCustomAdapter.notifyDataSetChanged();
//        recentList.invalidate();
//        mList = dataHandler.getList("today");
//        displayAllCustomAdapter = new DisplayAllCustomAdapter(getContext(), R.layout.displayall, mList);
//        recentList.setAdapter(displayAllCustomAdapter);
//
//    }

    @Override
    public void onStart() {
        super.onStart();
        displayAllCustomAdapter.notifyDataSetChanged();
        recentList.invalidate();
        mList = dataHandler.getList("today");
        displayAllCustomAdapter = new DisplayAllCustomAdapter(getContext(), R.layout.displayall, mList);
        recentList.setAdapter(displayAllCustomAdapter);
    }
}
