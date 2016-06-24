package com.developer.crdzbird.who_is_connected;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

public class VendorsDbHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private SQLiteDatabase myDataBase;
    private final Context myContext;
    private static final String DATABASE_NAME = "vendors.db";
    private static final String TABLE_NAME = "vendors";

    public static final String ID_COLUMN_NAME = "_id";
    public static final String MAC_COLUMN_NAME = "mac";
    public static final String VENDOR_COLUMN_NAME = "vendor";


    private static String DATABASE_PATH = "";
    private static final int DATABASE_VERSION = 2;
    public static final int DATABASE_VERSION_old = 1;

    //Constructor
    public VendorsDbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.myContext = context;
        DATABASE_PATH = myContext.getDatabasePath(DATABASE_NAME).toString();
    }

    public void createDatabase() throws IOException
    {
        boolean dbExist = checkDataBase();
        if(dbExist)
        {
            Log.v("DB Exists", "db exists");
            onUpgrade(myDataBase, DATABASE_VERSION_old, DATABASE_VERSION);
        }
        boolean dbExist1 = checkDataBase();
        if(!dbExist1)
        {
            this.getReadableDatabase();
            try
            {
                this.close();
                copyDataBase();
            }
            catch (Exception e)
            {
                Log.e(TAG, "Error copying database", e);
            }
        }
    }

    private boolean checkDataBase()
    {
        boolean checkDB = false;
        try
        {
            String myPath = DATABASE_PATH;
            File dbfile = new File(myPath);
            checkDB = dbfile.exists();
        }
        catch(Exception e)
        {
            Log.e(TAG, "exception while checking db", e);
        }
        return checkDB;
    }

    private void copyDataBase() throws IOException
    {
        String outFileName = DATABASE_PATH;
        OutputStream myOutput = new FileOutputStream(outFileName);
        InputStream myInput = myContext.getAssets().open(DATABASE_NAME);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0)
        {
            myOutput.write(buffer, 0, length);
        }
        myInput.close();
        myOutput.flush();
        myOutput.close();
    }

    public void db_delete()
    {
        File file = new File(DATABASE_PATH);
        if(file.exists())
        {
            file.delete();
            Log.v(TAG,"delete database file.");
        }
    }

    public void openDatabase() throws SQLException
    {
        String myPath = DATABASE_PATH;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public synchronized void closeDataBase()throws SQLException
    {
        if(myDataBase != null)
            myDataBase.close();
        super.close();
    }

    public void onCreate(SQLiteDatabase db)
    {
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        if (newVersion > oldVersion)
        {
            Log.v("Database Upgrade", "Database version higher than old.");
            db_delete();
        }
    }

    public String getVendor(String mac)
    {
        String macEdit = mac.substring(0,2) + mac.substring(3,5) + mac.substring(6,mac.length());
        SQLiteDatabase db = this.getWritableDatabase();
        String vendor;
        Cursor c = db.rawQuery("SELECT vendor FROM vendors WHERE mac = '"+macEdit+"'", null);
        if(c.moveToFirst()) {
            vendor = c.getString(c.getColumnIndex("vendor"));
        }
        else
        {
            vendor = "unknown";
        }
        c.close();
        return vendor;
    }

    public String getSpecVendor(String mac)
    {
        String macEdit = mac.substring(0,2) + mac.substring(3,5) + mac.substring(6,mac.length());
        SQLiteDatabase db = this.getWritableDatabase();
        String vendor;
        Cursor c = db.rawQuery("SELECT vendor FROM vendors WHERE mac = 'E04F43'", null);
        c.moveToFirst();
        vendor = c.getString(c.getColumnIndex("vendor"));
        c.close();
        return vendor;
    }

}
