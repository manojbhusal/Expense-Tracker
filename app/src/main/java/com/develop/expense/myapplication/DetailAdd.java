package com.develop.expense.myapplication;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import Database.DataHandler;
import Model.MoneyModel;

public class DetailAdd extends AppCompatActivity {
    TextView categoryText;
    String databaseSaveDate;
    static ImageView detailImage;
    Button detailSaveButton, dateButton;
    EditText amountNumber, description;
    String category, type;
     public LinearLayout layAd;
    int xyear, xmonth, xday, nameOfDay;
    final static String[] dayName = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    final static String[] monthName = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
    static final int DATE_CODE = 7;
    DataHandler dataHandler = new DataHandler(DetailAdd.this);
    public static int detailAd=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_add);
        setTitle("Add detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        categoryText = (TextView) findViewById(R.id.categoryText);
        amountNumber = (EditText) findViewById(R.id.amountNumber);
        description = (EditText) findViewById(R.id.description);
        detailSaveButton = (Button) findViewById(R.id.detailSaveButton);
        detailImage=(ImageView)findViewById(R.id.detailImage);

        dateButton = (Button) findViewById(R.id.dateButton);

        Bundle extras = getIntent().getExtras();
        type = extras.getString("type");
        category = extras.getString("category");
        layAd = (LinearLayout)findViewById(R.id.testL);

        categoryText.setText(category + " " + type);
getImageIcon(category);


        MyApplication2 myApplication2= (MyApplication2)getApplication();
        myApplication2.loadAd(DetailAdd.this,layAd);





        Calendar cal = Calendar.getInstance();
        xyear = cal.get(Calendar.YEAR);
        xmonth = cal.get(Calendar.MONTH);
        xday = cal.get(Calendar.DAY_OF_MONTH);
        nameOfDay = cal.get(Calendar.DAY_OF_WEEK);

        /*databaseSaveDate = xyear + "-" + (xmonth + 1) + "-" + xday;*/

        String sday = xday < 10 ? "0" + xday : xday + "",
                smonth = xmonth < 10 ? "0" + (xmonth+1) : (xmonth+1) + "";

        databaseSaveDate = xyear + "-" + smonth + "-" + sday;

        DateFormat dateFormat = DateFormat.getDateInstance();
        String date = dateFormat.format(new Date(System.currentTimeMillis()).getTime());
        dateButton.setText(date + "   " + dayName[nameOfDay - 1]);


        detailSaveButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //put button action over here to save data

                        savetoDB();





                    }
                }
        );

        dateButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog(DATE_CODE);
                    }
                }
        );


    }

    private void getImageIcon(String category) {

        switch (category) {
            case "Medical":
                detailImage.setImageResource(R.drawable.medical);
                break;
            case "Clothing":
                detailImage.setImageResource(R.drawable.clothing);
                break;
            case "Gadget":
                detailImage.setImageResource(R.drawable.gadget);
                break;
            case "Food":
                detailImage.setImageResource(R.drawable.pizza);
                break;
            case "Education":
                detailImage.setImageResource(R.drawable.education);
                break;
            case "Personal":
                detailImage.setImageResource(R.drawable.personal);
                break;
            case "Social":
                detailImage.setImageResource(R.drawable.social);
                break;
            case "Fun":
                detailImage.setImageResource(R.drawable.fun);
                break;
            case "Household":
                detailImage.setImageResource(R.drawable.household);
                break;
            case "Bonus":
                detailImage.setImageResource(R.drawable.bonus);
                break;
            case "Donation":
                detailImage.setImageResource(R.drawable.donation);
                break;
            case "Profit":
                detailImage.setImageResource(R.drawable.profit);
                break;
            case "Salary":
                detailImage.setImageResource(R.drawable.salary);
                break;
            case "Business":
                detailImage.setImageResource(R.drawable.business);
                break;
            case "Extras":
                detailImage.setImageResource(R.drawable.extras);
                break;


            default:
                detailImage.setImageResource(R.drawable.pizza);
                break;
        }
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DATE_CODE) ;

        return new DatePickerDialog(this, datePickerListener, xyear, xmonth, xday);

    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


            //  String nowDay= dayName[]
            xyear = year;
            xmonth = monthOfYear;
            xday = dayOfMonth;

            Calendar calendar = new GregorianCalendar(xyear, xmonth, xday);

            int dayResult = calendar.get(Calendar.DAY_OF_WEEK) - 1;


            String mdayName = dayName[dayResult];
            dateButton.setText(monthName[xmonth] + " " + xday + ", " + xyear + "  " + mdayName);

            String sday = xday < 10 ? "0" + xday : xday + "",
                    smonth = xmonth < 10 ? "0" + (xmonth+1) : (xmonth+1) + "";

            databaseSaveDate = xyear + "-" + smonth + "-" + sday;


        }


    };

    private void savetoDB() {


try {
    if (!amountNumber.getText().toString().isEmpty())

    {
        int amountNO = Integer.parseInt(amountNumber.getText().toString());
        String amountDesc = description.getText().toString();
        MoneyModel moneyModel = new MoneyModel();
        moneyModel.setDescription(amountDesc);
        moneyModel.setIEType(type);
        if (type.matches("expense")) {
            moneyModel.setAmount(-amountNO);
        } else {
            moneyModel.setAmount(amountNO);
        }
        moneyModel.setCategory(category);
        moneyModel.setDate(databaseSaveDate);   //date to save in database
        dataHandler.addItem(moneyModel);
        Toast.makeText(getApplicationContext(), "Saved successfully!", Toast.LENGTH_LONG).show();
//        Intent intent= new Intent(DetailAdd.this,MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
        amountNumber.setText("");
        description.setText("");


    } else {
        Toast.makeText(getApplicationContext(), "Please enter valid amount number", Toast.LENGTH_LONG).show();
    }
}   catch (Exception e){Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_LONG).show();}
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
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
            return true;
        }
        if (id == android.R.id.home) {
            this.finish();


        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onStop() {
        super.onStop();

        if (detailAd % 2==0) {
            final InterstitialAd mInterstitialAd = new InterstitialAd(DetailAdd.this);
            mInterstitialAd.setAdUnitId(getResources().getString(R.string.admob_interstitial_id));
            AdRequest adRequestInter = new AdRequest.Builder().build();
            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    mInterstitialAd.show();
                }
            });
            mInterstitialAd.loadAd(adRequestInter);

        }
        detailAd=detailAd+1;
        Log.v("adsldetail",detailAd+"");

    }

}
