package com.ninjakod.mehmetyilmaz.mywallet.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ninjakod.mehmetyilmaz.mywallet.R;
import com.ninjakod.mehmetyilmaz.mywallet.adapter.DbAdapter;


/**
 * A placeholder fragment containing a simple view.
 */
public class TransactionDetailActivityFragment extends Fragment {

    public TransactionDetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_transaction_detail, container, false);

        Intent intent = getActivity().getIntent();

        if(intent != null && intent.hasExtra("transactionId")){
            int transactionId = intent.getIntExtra("transactionId", 1);

            DbAdapter dbAdapter = new DbAdapter(getActivity());
            DbAdapter.MyWalletObject myWalletObject =  dbAdapter.getRowById(transactionId);

            TextView textView = (TextView) rootView.findViewById(R.id.transaction_detail_name_txtview);
            textView.setText(myWalletObject.transactionName);
        }

        return rootView;
    }

}
