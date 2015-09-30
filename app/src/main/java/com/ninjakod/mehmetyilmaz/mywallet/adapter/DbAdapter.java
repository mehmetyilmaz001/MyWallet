package com.ninjakod.mehmetyilmaz.mywallet.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ninjakod.mehmetyilmaz.mywallet.helper.Const;
import com.ninjakod.mehmetyilmaz.mywallet.helper.DbHelper;
import com.ninjakod.mehmetyilmaz.mywallet.helper.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mehmetyilmaz on 11/04/15.
 */

public class DbAdapter {

    public DbHelper dbHelper;

    public DbAdapter(Context context){
        dbHelper = new DbHelper(context);
    }

    public long insertData(String name, byte accountType, byte moneyComeFrom, int amount,
                           byte currency, byte type){

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {

            ContentValues contentValues = new ContentValues();

            contentValues.put(DbHelper.NAME, name);
            contentValues.put(DbHelper.ACCOUNT_TYPE, accountType);
            contentValues.put(DbHelper.MONEY_COME_FROM, moneyComeFrom);
            contentValues.put(DbHelper.AMOUNT, amount);
            contentValues.put(DbHelper.CURRENCY, currency);
            contentValues.put(DbHelper.TYPE, type);
            //  contentValues.put(MyWalletDbHelper.CREATE_DATE,     dateFormat.format(createDate));

            long id = db.insert(DbHelper.TABLE_NAME, null, contentValues);
            return id;

        }catch (Exception e){}
        finally {
            if(db != null){
                db.close();
            }
        }

        return -1;
    }

    public MyWalletObject getRowById(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = null;

        try{

            cursor = db.rawQuery("SELECT * FROM "+ DbHelper.TABLE_NAME +" WHERE _id = ?", new String[] {id + ""});
            MyWalletObject myWalletObject = new MyWalletObject();

            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                myWalletObject.id              = cursor.getInt(0);
                myWalletObject.transactionName = cursor.getString(1);
                myWalletObject.accountType     = (byte) cursor.getShort(2);
                myWalletObject.amount          = cursor.getInt(3);
                myWalletObject.moneyComeFrom   = (byte) cursor.getShort(4);
                myWalletObject.type            = (byte) cursor.getShort(5);
                myWalletObject.createDate      = cursor.getString(6);
                myWalletObject.currency        = (byte) cursor.getShort(7);
            }

            return myWalletObject;

        }finally {

            cursor.close();
        }
    }



    public List<MyWalletObject> filterData(byte accountType, byte currency, byte type, byte date){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String[] columns = {
                DbHelper.ID,
                DbHelper.NAME,
                DbHelper.ACCOUNT_TYPE,
                DbHelper.AMOUNT,
                DbHelper.MONEY_COME_FROM,
                DbHelper.TYPE,
                DbHelper.CREATE_DATE,
                DbHelper.CURRENCY
        };

        String whereClause = "";

        if(accountType != 99) {
            whereClause += " and " + DbHelper.ACCOUNT_TYPE + " = " + accountType;
        }

        if(currency != 99) {
            whereClause += " and " + DbHelper.CURRENCY + " = " + currency;
        }

        if(type != 99) {
            whereClause += " and " + DbHelper.TYPE + " = " + type;
        }

        if(date != 99){
            whereClause += " and " + DbHelper.CREATE_DATE + " between " +
                    "datetime('now', "+getDateRange(date)+") and datetime('now', 'localtime')";
        }

        List<MyWalletObject> myWalletObjectList = new ArrayList<>();

        String query = "SELECT ";

        for(String col : columns){
            query += col + ",";
        }

        query = Util.removeLastChar(query);

        query += " FROM " + DbHelper.TABLE_NAME + " WHERE 1 = 1" + whereClause;

        Cursor cursor = db.rawQuery(query, null);

        try {

            while (cursor.moveToNext()) {

                MyWalletObject myWalletObject = new MyWalletObject();

                myWalletObject.id              = cursor.getInt(0);
                myWalletObject.transactionName = cursor.getString(1);
                myWalletObject.accountType     = (byte) cursor.getShort(2);
                myWalletObject.amount          = cursor.getInt(3);
                myWalletObject.moneyComeFrom   = (byte) cursor.getShort(4);
                myWalletObject.type            = (byte) cursor.getShort(5);
                myWalletObject.createDate      = cursor.getString(6);
                myWalletObject.currency        = (byte) cursor.getShort(7);

                myWalletObjectList.add(myWalletObject);

            }
        }catch (Exception e){}
        finally {
            if(cursor != null) {
                cursor.close();
            }
        }

        return myWalletObjectList;
    }

    public String getDateRange(byte dateCode){
        String date  = "";

        switch (dateCode){
            case Const.LAST_ONE_MONTH :
                date = Const.LAST_ONE_MONTH_CLAUSE;
                break;

            case Const.LAST_SIX_MONTH:
                date = Const.LAST_SIX_MONTH_CLAUSE;
                break;

            case Const.LAST_ONE_YEAR:
                date = Const.LAST_ONE_YEAR_CLAUSE;

        }

        return date;
    }

    public class MyWalletObject{
        public int    id;
        public String transactionName;
        public byte   accountType;
        public int    amount;
        public byte   moneyComeFrom;
        public byte   currency;
        public byte   type;
        public String createDate;
    }

}
