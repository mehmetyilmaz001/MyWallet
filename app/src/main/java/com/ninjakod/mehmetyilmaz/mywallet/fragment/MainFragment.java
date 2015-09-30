package com.ninjakod.mehmetyilmaz.mywallet.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ninjakod.mehmetyilmaz.mywallet.R;
import com.ninjakod.mehmetyilmaz.mywallet.adapter.TransactionsViewAdapter;
import com.ninjakod.mehmetyilmaz.mywallet.helper.Const;

public class MainFragment extends Fragment{

    TextView mTotalCashAmount, mTotalCashAmountLabel, mTotalBankAmount, mTotalBankAmountLabel, mTotalAmount;
    View mRootView;

    public MainFragment() {
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_main, container, false);

        mTotalCashAmount      = (TextView) mRootView.findViewById(R.id.main_total_cash_amount);
        mTotalCashAmountLabel = (TextView) mRootView.findViewById(R.id.main_total_cash_amount_label);

        mTotalBankAmount      = (TextView) mRootView.findViewById(R.id.main_total_bank_amount);
        mTotalBankAmountLabel = (TextView) mRootView.findViewById(R.id.main_total_bank_amount_label);

        mTotalAmount = (TextView) mRootView.findViewById(R.id.main_total_amount);

        setMainTotal();

        return mRootView;
    }

    public void setMainTotal() {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        String currencyFromSettingStr = prefs.getString(getString(R.string.pref_currency_key), getString(R.string.pref_currency_default));
        byte currencyFromSetting = (byte) Integer.parseInt(currencyFromSettingStr);

        if(currencyFromSetting == -1){
            currencyFromSetting = 0;
        }

        TransactionsViewAdapter transactionsViewAdapter = new TransactionsViewAdapter(getActivity());

        transactionsViewAdapter.updateList(Const.ACCOUNT_TYPE_CASH, currencyFromSetting, Const.SELECT_ALL_DATA, Const.SELECT_ALL_DATA);
        String cashAmountTxt = transactionsViewAdapter.mTotalTxt;
        long cashAmount = transactionsViewAdapter.mTotal;

        transactionsViewAdapter.updateList(Const.ACCOUNT_TYPE_BANK, currencyFromSetting, Const.SELECT_ALL_DATA, Const.SELECT_ALL_DATA);
        String bankAmountTxt = transactionsViewAdapter.mTotalTxt;
        long bankAmount = transactionsViewAdapter.mTotal;


        transactionsViewAdapter.updateList(Const.SELECT_ALL_DATA, currencyFromSetting, Const.SELECT_ALL_DATA, Const.SELECT_ALL_DATA);
        String totalAmountTxt = transactionsViewAdapter.mTotalTxt;
        long totalAmount = transactionsViewAdapter.mTotal;


        mTotalCashAmount.setText(cashAmountTxt);
        mTotalBankAmount.setText(bankAmountTxt);
        mTotalAmount.setText(totalAmountTxt);

        if(cashAmount < 0){
            mTotalCashAmount.setTextColor(getActivity().getResources().getColor(R.color.red));
            mTotalCashAmountLabel.setTextColor(getActivity().getResources().getColor(R.color.red));
        }else {
            mTotalCashAmount.setTextColor(getActivity().getResources().getColor(R.color.green));
            mTotalCashAmountLabel.setTextColor(getActivity().getResources().getColor(R.color.green));
        }

        if(bankAmount < 0){
            mTotalBankAmount.setTextColor(getActivity().getResources().getColor(R.color.red));
            mTotalBankAmountLabel.setTextColor(getActivity().getResources().getColor(R.color.red));
        }else{
            mTotalBankAmount.setTextColor(getActivity().getResources().getColor(R.color.green));
            mTotalBankAmountLabel.setTextColor(getActivity().getResources().getColor(R.color.green));
        }

        if(totalAmount < 0){
            mTotalAmount.setTextColor(getActivity().getResources().getColor(R.color.red));
        }else{
            mTotalAmount.setTextColor(getActivity().getResources().getColor(R.color.green));
        }



    }

}