package com.ninjakod.mehmetyilmaz.mywallet.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ninjakod.mehmetyilmaz.mywallet.R;
import com.ninjakod.mehmetyilmaz.mywallet.helper.Const;
import com.ninjakod.mehmetyilmaz.mywallet.helper.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by mehmetyilmaz on 14/04/15.
 */
public class TransactionsViewAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<DbAdapter.MyWalletObject>  mTransactionsList;
    public long mTotal;
    public String mTotalTxt;

    Context context;

    DbAdapter dbAdapter;


    public TransactionsViewAdapter(Activity activity) {

        context = activity;

        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        dbAdapter = new DbAdapter(activity);

        byte accountType = Const.SELECT_ALL_DATA;
        byte currency    = Const.SELECT_ALL_DATA;
        byte type        = Const.SELECT_ALL_DATA;
        byte createDate  = Const.SELECT_ALL_DATA;

        mTransactionsList = dbAdapter.filterData(accountType, currency, type, createDate);
    }

    public void updateList(byte accountType, byte currency, byte type, byte createDate){
        mTransactionsList = dbAdapter.filterData(accountType, currency, type, createDate);
        long total = 0;

        for(DbAdapter.MyWalletObject myWalletObject : mTransactionsList){

            if(myWalletObject.type == Const.TRANSACTION_TYPE_ADD){
                total += myWalletObject.amount;

            }else{
                total -= myWalletObject.amount;

            }
        }

        mTotal    = total;
        mTotalTxt = Util.formatCurrency(currency, total);
    }


    @Override
    public int getCount() {
        return mTransactionsList.size();
    }

    @Override
    public DbAdapter.MyWalletObject getItem(int position) {
        return mTransactionsList.get(position);
    }

    @Override
    public long getItemId(int position) {


        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView;

        listItemView = mInflater.inflate(R.layout.list_item_transaction, null);

        TextView txtName            =
                (TextView) listItemView.findViewById(R.id.list_item_transaction_name);

        TextView txtAmount          =
                (TextView) listItemView.findViewById(R.id.list_item_transaction_amount);

        TextView txtAccountType     =
                (TextView)listItemView.findViewById(R.id.list_item_transaction_account_type);

        TextView txtCreateDate      =
                (TextView)listItemView.findViewById(R.id.list_item_transaction_date);

        TextView txtCreateDateMonth =
                (TextView)listItemView.findViewById(R.id.list_item_transaction_date_month);

        TextView txtCreateDateYear  =
                (TextView)listItemView.findViewById(R.id.list_item_transaction_date_year);


        LinearLayout linearLayout = (LinearLayout) listItemView.findViewById(R.id.list_item_transaction_linear_layout);

        DbAdapter.MyWalletObject myWalletObject = mTransactionsList.get(position);

        String date_s = myWalletObject.createDate;

        // *** note that it's "yyyy-MM-dd hh:mm:ss" not "yyyy-mm-dd hh:mm:ss"
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = null;
        try {
            date = dt.parse(date_s);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        SimpleDateFormat dtDay = new SimpleDateFormat("dd");
        String createDateDay = dtDay.format(date);

        SimpleDateFormat dtMonth = new SimpleDateFormat("MMMM");
        String createDateMonth = dtMonth.format(date);

        SimpleDateFormat dtYear = new SimpleDateFormat("yyyy");
        String createDateYear = dtYear.format(date);

        txtName.setText(myWalletObject.transactionName);
        txtName.setTag(myWalletObject.id);

        String amount = Util.formatCurrency(myWalletObject.currency, myWalletObject.amount);

        if(myWalletObject.type == Const.TRANSACTION_TYPE_SUBTRACT){
            amount = "- " + amount;

            linearLayout.setBackgroundColor(context.getResources().getColor(R.color.red));

            txtName           .setTextAppearance(context, R.style.SubMoneyTextViewStyle);
            txtAmount         .setTextAppearance(context, R.style.SubMoneyTextViewStyle);
            txtAccountType    .setTextAppearance(context, R.style.SubMoneyTextViewStyle);
            txtCreateDate     .setTextAppearance(context, R.style.SubMoneyTextViewStyle);
            txtCreateDateMonth.setTextAppearance(context, R.style.SubMoneyTextViewStyle);
            txtCreateDateYear .setTextAppearance(context, R.style.SubMoneyTextViewStyle);
        }

        txtAmount.setText(amount);

        String accountType = null;

        if(myWalletObject.accountType == Const.ACCOUNT_TYPE_CASH){
            accountType =  context.getResources().getString(R.string.cash);
        }else{
            accountType =  context.getResources().getString(R.string.bank);
        }

        txtAccountType    .setText(accountType);
        txtCreateDate     .setText(createDateDay);
        txtCreateDateMonth.setText(createDateMonth);
        txtCreateDateYear .setText(createDateYear);

        return listItemView;
    }


}
