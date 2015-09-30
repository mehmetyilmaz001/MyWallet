package com.ninjakod.mehmetyilmaz.mywallet.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.ninjakod.mehmetyilmaz.mywallet.ITransactionsFragment;
import com.ninjakod.mehmetyilmaz.mywallet.R;
import com.ninjakod.mehmetyilmaz.mywallet.TransactionDetailActivity;
import com.ninjakod.mehmetyilmaz.mywallet.adapter.TransactionsViewAdapter;
import com.ninjakod.mehmetyilmaz.mywallet.helper.Const;
import com.ninjakod.mehmetyilmaz.mywallet.helper.Util;


public class TransactionsFragment extends Fragment implements ITransactionsFragment, ObservableScrollViewCallbacks {
        public byte mAccountType = 99;
        public byte mCurrency    = 99;
        public byte mType        = 99;
        public byte mCreateDate  = 99;

        LinearLayout mTransactionsFilterLinearLayout;

        Spinner mSpnCurrency, mSpnDate;
        RadioGroup mAccountTypeGroup, mMoneyTypeGroup;

        TransactionsViewAdapter mTransactionViewAdapter;

        ObservableListView mTransactionListView;

        TextView mTxtTotal;

        public TransactionsFragment() {
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_transactions, container, false);

            mTransactionsFilterLinearLayout = (LinearLayout) rootView.findViewById(R.id.transactions_filter_linear_layout);

            mTransactionListView = (ObservableListView) rootView.findViewById(R.id.transactions_list_view);
            mTransactionListView.setScrollViewCallbacks(this);

            mTxtTotal = (TextView) rootView.findViewById(R.id.transactions_total_textView);

            mCurrency   = Const.CURRENCY_TURKISH_LIRA;
            mCreateDate = Const.LAST_ONE_MONTH;

            initViewAdapter();

            mSpnCurrency = (Spinner) rootView.findViewById(R.id.transactions_spinner_currency);
            mSpnDate     = (Spinner) rootView.findViewById(R.id.transactions_spinner_date);
            mAccountTypeGroup = (RadioGroup) rootView.findViewById(R.id.transactions_account_type_radio_group);
            mMoneyTypeGroup   = (RadioGroup) rootView.findViewById(R.id.transactions_money_type_radio_group);

            ArrayAdapter currencyAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.entry_currency, R.layout.color_spinner_item);
            mSpnCurrency.setAdapter(currencyAdapter);

            ArrayAdapter dateAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.entry_date, R.layout.color_spinner_item);
            mSpnDate.setAdapter(dateAdapter);




            mSpnCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    mCurrency = (byte) position;

                    mTransactionViewAdapter.updateList(mAccountType, mCurrency, mType, mCreateDate);
                    mTransactionListView.setAdapter(mTransactionViewAdapter);

                    setTotalText();

                }


                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });



            mAccountTypeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    byte accountTypeValue = Const.ACCOUNT_TYPE_CASH;

                    if (checkedId == R.id.transactions_rd_bank) {
                        accountTypeValue = Const.ACCOUNT_TYPE_BANK;

                    } else {
                        accountTypeValue = Const.ACCOUNT_TYPE_CASH;

                    }

                    mAccountType = accountTypeValue;

                    mTransactionViewAdapter.updateList(mAccountType, mCurrency, mType, mCreateDate);
                    mTransactionListView.setAdapter(mTransactionViewAdapter);

                    setTotalText();


                }
            });


            mMoneyTypeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    byte moneyTypeValue = Const.TRANSACTION_TYPE_ADD;

                    if (checkedId == R.id.transactions_rd_add) {
                        moneyTypeValue = Const.TRANSACTION_TYPE_ADD;

                    } else {
                        moneyTypeValue = Const.TRANSACTION_TYPE_SUBTRACT;

                    }

                    mType = moneyTypeValue;

                    mTransactionViewAdapter.updateList(mAccountType, mCurrency, mType, mCreateDate);
                    mTransactionListView.setAdapter(mTransactionViewAdapter);
                    setTotalText();

                }
            });

            mSpnDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position == 3)
                        position = Const.SELECT_ALL_DATA;

                    mCreateDate = (byte) position;
                    mTransactionViewAdapter.updateList(mAccountType, mCurrency, mType, mCreateDate);
                    mTransactionListView.setAdapter(mTransactionViewAdapter);
                    setTotalText();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });




            mTransactionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    int transactionId = (Integer) view.findViewById(R.id.list_item_transaction_name).getTag();

                    Intent intent = new Intent(getActivity(), TransactionDetailActivity.class)
                            .putExtra("transactionId", transactionId);


                    /*
                    startActivity(intent);
                    TODO : Transaction detail will be handled
                    */


                }
            });



            return rootView;
        }


    private void setTotalText() {
        if(mTransactionViewAdapter.mTotal < 0){
            mTxtTotal.setBackgroundColor(getActivity().getResources().getColor(R.color.red));
        }else {
            mTxtTotal.setBackgroundColor(getActivity().getResources().getColor(R.color.green));
        }

        mTxtTotal.setText(mTransactionViewAdapter.mTotalTxt);
    }

    public void initViewAdapter(){
        mTransactionViewAdapter = new TransactionsViewAdapter(getActivity());
        mTransactionViewAdapter.updateList(mAccountType, mCurrency, mType, mCreateDate);
       // mTransactionListView.setAdapter(mTransactionViewAdapter);
        mTransactionListView.setAdapter(mTransactionViewAdapter);
        setTotalText();
    }

    @Override
    public void fragmentBecameVisible() {
        initViewAdapter();
    }




    @Override
    public void onScrollChanged(int i, boolean b, boolean b1) {

    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        if (scrollState == ScrollState.UP) {
            Log.d("scroll", "up");
            if (mTransactionsFilterLinearLayout.isShown()) {
                Util.collapse(mTransactionsFilterLinearLayout);
                //mTransactionsFilterLinearLayout.hide();
            }
        } else if (scrollState == ScrollState.DOWN) {
            Log.d("scroll", "down");

            if (!mTransactionsFilterLinearLayout.isShown()) {
                //ab.show();
                Util.expand(mTransactionsFilterLinearLayout);
            }
        }
    }
}


