package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import Utils.ConstantsCatogories;
import Utils.DataConstants;

/**
 * Created by Shailendra on 11/30/2016.
 */
public class MyHandler extends SQLiteOpenHelper {
    public ArrayList incomeList= new ArrayList();
    public MyHandler(Context context) {
        super(context, ConstantsCatogories.database_name, null, ConstantsCatogories.database_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "CREATE TABLE "+ ConstantsCatogories.table_name+ "(" + ConstantsCatogories.id_name+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +
              ConstantsCatogories.income_name + " TEXT);"
        );






    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS "+ ConstantsCatogories.table_name);
        onCreate(db);
    }


    public void addCategoryIncome(String incomeName )
    {
        SQLiteDatabase dba= this.getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put(ConstantsCatogories.income_name,incomeName);
        dba.insert(ConstantsCatogories.table_name,null,cv);
        dba.close();}

    public List<String> getIncomeCategories()
    {
         incomeList.clear();
        SQLiteDatabase dba= this.getReadableDatabase();
      String incomeSelection="SELECT * FROM "+ ConstantsCatogories.table_name + " ORDER BY "+ ConstantsCatogories.income_name ;

      Cursor cursor= dba.rawQuery(incomeSelection,null);
        //Cursor cursor= dba.query(ConstantsCatogories.table_name, new String[] {ConstantsCatogories.income_name},null,null,null,null,ConstantsCatogories.income_name+ " DESC");

        if(cursor.moveToFirst())

            do{
  String tempIncomeCategory=cursor.getString(cursor.getColumnIndex(ConstantsCatogories.income_name));

                incomeList.add(tempIncomeCategory);
            }while (cursor.moveToNext());
        dba.close();
        return incomeList;
    }



}
