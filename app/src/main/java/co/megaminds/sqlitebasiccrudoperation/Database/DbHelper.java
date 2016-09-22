package co.megaminds.sqlitebasiccrudoperation.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "database.db";

    //for information table
    private static final String TABLE_NAME = "information";
    private static final String INFORMATION_TABLE_ID = "id";
    private static final String INFORMATION_TABLE_NAME = "name";
    private static final String INFORMATION_TABLE_PHONE = "phone";

    //"information" Table create SQL query string
    private static final String SQL_CREATE_TABLE_INFORMATION = "CREATE TABLE " + TABLE_NAME +
            "("
            + INFORMATION_TABLE_ID + " INTEGER PRIMARY KEY, "
            + INFORMATION_TABLE_NAME + " TEXT, "
            + INFORMATION_TABLE_PHONE + " TEXT "
            + ");";

    //read all data from database. SQL query
    private static final String SQL_READ_QUERY = "SELECT * FROM information";


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_INFORMATION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS information");
        onCreate(sqLiteDatabase);
    }

    public boolean insertRecord(String name, String phone){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(INFORMATION_TABLE_NAME, name);
        contentValues.put(INFORMATION_TABLE_PHONE, phone);

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);

        return true;
    }

    public ArrayList<String> getAllInformation(){

        ArrayList<String> arrayList = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor result = sqLiteDatabase.rawQuery(SQL_READ_QUERY, null);
        result.moveToFirst();

        while (result.isAfterLast()==false){
            arrayList.add(result.getString(result.getColumnIndex(INFORMATION_TABLE_NAME)));
            result.moveToNext();
        }

        return arrayList;
    }

}
