package com.ninjakod.mehmetyilmaz.mywallet.fragment;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.ninjakod.mehmetyilmaz.mywallet.ICommunicator;
import com.ninjakod.mehmetyilmaz.mywallet.R;
import com.ninjakod.mehmetyilmaz.mywallet.adapter.DbAdapter;
import com.ninjakod.mehmetyilmaz.mywallet.helper.Const;

/**
 * Created by mehmetyilmaz on 11/04/15.
 */
public class SubMoneyDialogFragment extends DialogFragment {
    ICommunicator communicator;
    public static final String LOG_TAG = SubMoneyDialogFragment.class.getSimpleName();

    Spinner spendType, currency;
    RadioGroup accountTypeGroup;
    EditText amount, detail;
    Button subMoney, cancel;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        communicator = (ICommunicator) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //setCancelable(false);
        View view = inflater.inflate(R.layout.dialog_sub_money, null);

        getDialog().setTitle(R.string.sub_money_dialog_title);

        spendType = (Spinner) view.findViewById(R.id.sub_money_dialog_spend_type);
        currency  = (Spinner) view.findViewById(R.id.sub_money_dialog_currency);
        accountTypeGroup = (RadioGroup) view.findViewById(R.id.sub_money_dialog_account_type_radio_group);
        amount    = (EditText) view.findViewById(R.id.sub_money_dialog_amount);
        detail    = (EditText) view.findViewById(R.id.sub_money_dialog_detail);

        subMoney  = (Button) view.findViewById(R.id.sub_money_dialog_add_money_btn);
        cancel    = (Button) view.findViewById(R.id.sub_money_dialog_cancel);



        subMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int accountType      = (int) accountTypeGroup.getCheckedRadioButtonId();

                byte spendTypeValue  = (byte) spendType.getSelectedItemPosition();
                byte currencyValue   = (byte) currency.getSelectedItemPosition();

                String amountText = amount.getText().toString();
                String detailText = detail.getText().toString();

                if(amountText.matches("")) {
                    Toast.makeText(getActivity(), R.string.amount_cannot_be_null, Toast.LENGTH_LONG).show();

                }else {

                    int amountValue = Integer.parseInt(amountText);

                    byte accountTypeValue = Const.ACCOUNT_TYPE_CASH;

                    if (accountType == R.id.sub_money_dialog_rd_bank) {
                        accountTypeValue = Const.ACCOUNT_TYPE_BANK;
                    }

                    //Toast.makeText(getActivity(), "currency type : " + currencyValue, Toast.LENGTH_LONG).show();


                    DbAdapter dbAdapter = new DbAdapter(getActivity());

                    long id = dbAdapter.insertData(detailText, accountTypeValue, spendTypeValue, amountValue, currencyValue,
                            Const.TRANSACTION_TYPE_SUBTRACT);

                    if(id < 0){
                        Log.e(LOG_TAG, "Can not insert the values to the database");

                    }else {
                        Log.i(LOG_TAG, "Data successfully inserted. ID : " + id);
                        communicator.onDialogDismiss(id);
                        dismiss();

                    }
                }//amount empty check
            }//method onclick
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        return view;

    }



}
