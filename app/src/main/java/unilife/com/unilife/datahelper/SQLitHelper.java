package unilife.com.unilife.datahelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLitHelper extends SQLiteOpenHelper {

    public static final String DataBase_Name = "COMPANY_MANAGEMENT";
    public static final int Version = 1;
    public static final String TbLCOMPANY = "tblcompany";
    public static final String TbLEntry = "tblentry";

    final String TAG = "SQLitHelper";
    public SQLitHelper(Context context) {
        super(context, DataBase_Name, null, Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("Create table " + TbLCOMPANY + "" +
                "(id INTEGER PRIMARY KEY," +
                "USER_ID TEXT," +
                "COMP_NAME TEXT," +
                "COMP_AREA TEXT," +
                "COMP_CONTACT TEXT," +
                "COMP_MANAGER TEXT)");


        db.execSQL("Create table " + TbLEntry + "" +
                "(id INTEGER PRIMARY KEY," +
                "COMP_ID TEXT," +
                "COMP_NAME TEXT," +
                "DATE TEXT," +
                "AMOUNT INTEGER," +
                "PAID INTEGER," +
                "REMAIN INTEGER," +
                "NOTE TEXT)");

       Log.e(TAG, "Tables Created");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TbLCOMPANY);
        db.execSQL("DROP TABLE IF EXISTS " + TbLEntry);

        // Create tables again
        onCreate(db);
    }
}
