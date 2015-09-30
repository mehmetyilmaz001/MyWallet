package com.ninjakod.mehmetyilmaz.mywallet.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {

        private static final String LOG_TAG = DbHelper.class.getSimpleName();

        public static final String DATABASE_NAME   = "MyWallet";
        public static final String TABLE_NAME      = "TRANSACTIONS";
        public static final String ID              = "_id";
        public static final String NAME            = "Name";
        public static final String ACCOUNT_TYPE    = "AccountType";
        public static final String MONEY_COME_FROM = "MoneyComeFrom";
        public static final String AMOUNT          = "Amount";
        public static final String CURRENCY        = "Currency";
        public static final String TYPE            = "Type";
        public static final String CREATE_DATE     = "CreateDate";
        public static final String UPDATE_DATE     = "UpdateDate";

        private static final String CREATE = "CREATE TABLE " + TABLE_NAME +
                "(" +
                    ID              + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    NAME            + " VARCHAR(255)," +
                    ACCOUNT_TYPE    + " BIT," +
                    MONEY_COME_FROM + " BIT," +
                    AMOUNT          + " INT," +
                    CURRENCY        + " BIT," +
                    TYPE            + " BIT," +
                    CREATE_DATE     + " DATETIME DEFAULT CURRENT_TIMESTAMP," +
                    UPDATE_DATE     + " DATETIME" +
                ")";

        private static final String DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;

        private static final int DATABASE_VERSION   = 10;



        public DbHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);


        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try{
                db.execSQL(CREATE);
                Log.v(LOG_TAG, "onCreate called");
            }catch (Exception e){
                Log.e(LOG_TAG, e.getMessage());
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            try{
                db.execSQL(DROP);
                onCreate(db);
                Log.v(LOG_TAG, "onUpgrade called");
            }catch (Exception e){
                Log.e(LOG_TAG, e.getMessage());
                //e.printStackTrace();
            }

        }
    }