package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.develop.expense.myapplication.Summary;

import java.util.ArrayList;
import java.util.Calendar;

import Model.MoneyModel;
import Utils.DataConstants;

/**
 * Created by Shailendra on 12/1/2016.
 */
public class DataHandler extends SQLiteOpenHelper {


    private int total = 0;
    private int incomeTotal = 0;
    private int expenseTotal = 0;

    public ArrayList myList = new ArrayList();

    public DataHandler(Context context) {
        super(context, DataConstants.database_name, null, DataConstants.database_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "CREATE TABLE " + DataConstants.table_name + " ( " + DataConstants.id_name + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        DataConstants.category_name + " TEXT, " + DataConstants.date_name + " DATETIME," +
                        DataConstants.type_nameIE + " TEXT, " + DataConstants.amount_name + " INTEGER," +
                        DataConstants.description_name + " TEXT);");

    }


    public void addItem(MoneyModel moneyModel) {

        SQLiteDatabase dba = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DataConstants.category_name, moneyModel.getCategory());
        cv.put(DataConstants.amount_name, moneyModel.getAmount());
        cv.put(DataConstants.date_name, moneyModel.getDate());
        cv.put(DataConstants.type_nameIE, moneyModel.getIEType());
        cv.put(DataConstants.description_name, moneyModel.getDescription());
        Log.v("check", moneyModel.getIEType());
        dba.insert(DataConstants.table_name, null, cv);
        Log.v("Added item", "added successfully");

    }

    public int getTotal() {


        return this.total;
    }

    public int getIncomeTotal() {
        return this.incomeTotal;
    }

    public int getExpenseTotal() {
        return this.expenseTotal;
    }

    private void setTotal(Cursor cursor) {
        if (cursor.moveToFirst()) {
            this.total = 0;
            this.incomeTotal = 0;
            this.expenseTotal = 0;
            do {
                int money = (int) cursor.getInt(cursor.getColumnIndex(DataConstants.amount_name));
                if (money > 0) {
                    this.incomeTotal += money;
                } else if (money < 0) {
                    this.expenseTotal += money;
                }

                this.total += money;
            } while (cursor.moveToNext());
        }
    }

    public ArrayList<MoneyModel> getList(String all) {
        myList.clear();


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DataConstants.table_name, new String[]{DataConstants.amount_name, DataConstants.category_name, DataConstants.date_name,
                DataConstants.description_name, DataConstants.type_nameIE, DataConstants.id_name}, null, null, null, null, DataConstants.date_name + " DESC");
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        String sday = day < 10 ? "0" + day : day + "",
                smonth = month < 10 ? "0" + (month + 1) : (month + 1) + "";


        switch (all)

        {
            case "all":
                break;

            case "today": {

                String queryMonth = "SELECT * FROM " + DataConstants.table_name + " WHERE " + DataConstants.date_name + " LIKE \"" + year + "-" + smonth + "-" + sday + "\";";
                cursor = db.rawQuery(queryMonth, null);
                break;
            }

            case "This Week": {

                Calendar calendar2 = Calendar.getInstance();
                calendar2.clear();
                calendar2.setTimeInMillis(System.currentTimeMillis());

                while (calendar2.get(Calendar.DAY_OF_WEEK) > calendar2.getFirstDayOfWeek()) {
                    calendar2.add(Calendar.DATE, -1);
                }

                int startYear = calendar2.get(Calendar.YEAR);
                int startMonth = calendar2.get(Calendar.MONTH);
                int startDay = calendar2.get(Calendar.DAY_OF_MONTH);

                calendar2.add(Calendar.DATE, 7);
                int endYear = calendar2.get(Calendar.YEAR);
                int endMonth = calendar2.get(Calendar.MONTH);
                int endDay = calendar2.get(Calendar.DAY_OF_MONTH);


                String startDate = "'" + formatDate(startYear, startMonth, startDay) + "'";
                String endDate = "'" + formatDate(endYear, endMonth, endDay) + "'";

                Log.v("WEEK", startDate + "  " + endDate);

                String execQuery = "SELECT * FROM " + DataConstants.table_name + " WHERE " + DataConstants.date_name + " BETWEEN " + startDate + " AND " + endDate + ";";
                cursor = db.rawQuery(execQuery, null);

                break;

            }
            case "This Month":   //here is error
            {
                String queryMonth = "SELECT * FROM " + DataConstants.table_name + " WHERE " + DataConstants.date_name + " LIKE \"" + year + "-" + smonth + "%\";";
                cursor = db.rawQuery(queryMonth, null);
                break;


            }
            case "This Year": {
                String queryMonth = "SELECT * FROM " + DataConstants.table_name + " WHERE " + DataConstants.date_name + " LIKE \"" + year + "%\";";
                cursor = db.rawQuery(queryMonth, null);
                break;

            }
            case "Custom": {


                Summary.SelectDateFragment selectDateFragment = new Summary.SelectDateFragment();
                String startDateTemp = "'" + selectDateFragment.getStartDate() + "'";
                String endDateTemp = "'" + selectDateFragment.getEndDate() + "'";


                String execQuery = "SELECT * FROM " + DataConstants.table_name + " WHERE " + DataConstants.date_name + " BETWEEN " + startDateTemp + " AND " + endDateTemp + ";";
                cursor = db.rawQuery(execQuery, null);
                break;


            }


        }

        setTotal(cursor);

        if (cursor.moveToFirst())

            do {
                MoneyModel moneyModel = new MoneyModel();
                moneyModel.setCategory(cursor.getString(cursor.getColumnIndex(DataConstants.category_name)));
                moneyModel.setIEType(cursor.getString(cursor.getColumnIndex(DataConstants.type_nameIE)));
                moneyModel.setDescription(cursor.getString(cursor.getColumnIndex(DataConstants.description_name)));
                moneyModel.setAmount(cursor.getInt(cursor.getColumnIndex(DataConstants.amount_name)));
                moneyModel.setDate(cursor.getString(cursor.getColumnIndex(DataConstants.date_name)));

                moneyModel.setPrimaryKey(cursor.getInt(cursor.getColumnIndex(DataConstants.id_name)));



                myList.add(moneyModel);


            } while (cursor.moveToNext());

        return myList;
    }


    public void deleteItem(Object item) {
        SQLiteDatabase db = this.getWritableDatabase();
        // db.delete(DataConstants.table_name, DataConstants.id_name + "=?", new String[]{*});//add item id in database here
        String query="DELETE FROM "+DataConstants.table_name+" WHERE "+DataConstants.id_name+"="+((MoneyModel)item).getPrimaryKey();
        db.execSQL(query);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + DataConstants.table_name);
        onCreate(db);
    }


    private String formatDate(int xyear, int xmonth, int xday) {
        String sday = xday < 10 ? "0" + xday : xday + "",
                smonth = xmonth < 10 ? "0" + (xmonth + 1) : (xmonth + 1) + "";

        return (xyear + "-" + smonth + "-" + sday);
    }


}
