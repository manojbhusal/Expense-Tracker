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
import Utils.ConstantsCatogories2;

/**
 * Created by Shailendra on 12/4/2016.
 */
public class MyHandler2 extends SQLiteOpenHelper {
    public ArrayList expenseList= new ArrayList();

    public MyHandler2(Context context) {
        super(context, ConstantsCatogories2.database_name, null, ConstantsCatogories2.database_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "CREATE TABLE "+ ConstantsCatogories2.table_name+ "(" + ConstantsCatogories2.id_name+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + ConstantsCatogories2.expense_name+ " Text);"
        );






    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS "+ ConstantsCatogories2.table_name);
        onCreate(db);
    }


    public void addCategoryExpense(String expenseName )
    {
        SQLiteDatabase dba= this.getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put(ConstantsCatogories2.expense_name,expenseName);
        dba.insert(ConstantsCatogories2.table_name,null,cv);
        dba.close();
    }

    public List<String> getExpenseCategories()
    {
        expenseList.clear();
        SQLiteDatabase dba= this.getReadableDatabase();
        Cursor cursor= dba.query(ConstantsCatogories2.table_name, new String[] {ConstantsCatogories2.expense_name},null,null,null,null,null);

        if(cursor.moveToFirst())

            do{
                String tempExpenseCategory=cursor.getString(cursor.getColumnIndex(ConstantsCatogories2.expense_name));

                expenseList.add(tempExpenseCategory);
            }while (cursor.moveToNext());
        dba.close();
        return expenseList;
    }



}

