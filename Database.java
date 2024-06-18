package com.voanhhao.kiemtra_hk2.cau2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    public static final String DB_NAME = "products.sqlite";
    public static final int DB_VERSION = 1;
    public static final String TBL_NAME = "Product";
    public static final String COL_CODE = "ProductCode";
    public static final String COL_ID = "ProductId";
    public static final String COL_NAME = "ProductName";
    public static final String COL_PRICE = "ProductPrice";

    public Database(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TBL_NAME + " (" + COL_CODE + " VARCHAR(50) PRIMARY KEY , " + COL_NAME + " VARCHAR(50), " + COL_PRICE + " REAL)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TBL_NAME);
        onCreate(db);
    }
    //SELECT
    public Cursor queryData(String sql){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(sql,null);
    }
    //INSERT, UPDATE, DELETE
    public void execSql(String sql){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
    }
    public int getNumberOfRows(){
        Cursor cursor = queryData("SELECT * FROM "+ TBL_NAME);
        int numberOfRows = cursor.getCount();
        cursor.close();
        return numberOfRows;
    }
    public void createSampleData(){
        if(getNumberOfRows()==0){
            try {
                execSql("INSERT INTO "+ TBL_NAME+" VALUES( 'H1', 'Heineken', 19000)");
                execSql("INSERT INTO "+ TBL_NAME+" VALUES( 'T1', 'Tiger', 18000)");
                execSql("INSERT INTO "+ TBL_NAME+" VALUES( 'S1', 'Sapporo', 22000)");
                execSql("INSERT INTO "+ TBL_NAME+" VALUES( 'B1', 'Blanc 1664', 24000)");
                execSql("INSERT INTO "+ TBL_NAME+" VALUES( 'S1', 'SaiGon', 20000)");
            }catch (Exception e){
                Log.e("Error: ",e.getMessage().toString());
            }
        }
    }
}
