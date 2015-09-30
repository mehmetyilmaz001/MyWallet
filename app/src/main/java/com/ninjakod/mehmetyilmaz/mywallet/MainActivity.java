package com.ninjakod.mehmetyilmaz.mywallet;

//import android.app.ActionBar;

//import android.app.Fragment;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.ninjakod.mehmetyilmaz.mywallet.fragment.AddMoneyDialogFragment;
import com.ninjakod.mehmetyilmaz.mywallet.fragment.MainFragment;
import com.ninjakod.mehmetyilmaz.mywallet.fragment.SubMoneyDialogFragment;
import com.ninjakod.mehmetyilmaz.mywallet.fragment.TransactionsFragment;


public class MainActivity extends ActionBarActivity implements ICommunicator {


    Button mActionBarMainBtn, mActionBarTransBtn;
    View mActionMainBtnLineView, mActionTransactionsBtnLineView;
    MainFragment mMainFragment;
    TransactionsFragment mTransactionsFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(R.layout.action_bar,null);

        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(actionBarLayout);

        mActionBarMainBtn = (Button) findViewById(R.id.action_bar_main_btn);
        mActionBarTransBtn = (Button) findViewById(R.id.action_bar_transactions_btn);

        mActionMainBtnLineView         = (View) findViewById(R.id.action_bar_main_line);
        mActionTransactionsBtnLineView = (View) findViewById(R.id.action_bar_transactions_line);

        final ImageView imageView = (ImageView) findViewById(R.id.action_logo);
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.abc_item_background_holo_light));


        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();

        if (savedInstanceState == null) {
            mMainFragment = new MainFragment();
            replaceFragments(mMainFragment);

        }

        mActionBarMainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mMainFragment == null){
                    mMainFragment = new MainFragment();
                }

                replaceFragments(mMainFragment);
            }
        });

        mActionBarTransBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTransactionsFragment == null) {
                    mTransactionsFragment = new TransactionsFragment();
                }

                replaceFragments(mTransactionsFragment);

            }
        });

    }

    public void replaceFragments(Fragment fragment){
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        String fragmentName = fragment.getClass().getName();

        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment, fragmentName)
                .addToBackStack(fragmentName)

        .commit();

        fragmentManager.executePendingTransactions();

        if(fragmentName.equals(MainFragment.class.getName())) {
            setButtonsState(mActionMainBtnLineView);
        }else{
            setButtonsState(mActionTransactionsBtnLineView);
        }
    }

    public void setButtonsState(View clickedLine){
        mActionMainBtnLineView.setBackgroundColor(getResources().getColor(R.color.mywallet_blue));
        mActionTransactionsBtnLineView.setBackgroundColor(getResources().getColor(R.color.mywallet_blue));

        clickedLine.setBackgroundColor(getResources().getColor(R.color.action_bar_active_item));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void showAddMoneyDialog(View view){
        FragmentManager fragmentManager = getFragmentManager();
        AddMoneyDialogFragment addMoneyDialogFragment = new AddMoneyDialogFragment();
        addMoneyDialogFragment.show(fragmentManager, "AddMoneyDialog");
    }

    public void showSubMoneyDialog(View view){
        FragmentManager fragmentManager = getFragmentManager();
        SubMoneyDialogFragment subMoneyDialogFragment = new SubMoneyDialogFragment();
        subMoneyDialogFragment.show(fragmentManager, "SubMoneyDialog");
    }

    @Override
    public void onDialogDismiss(long id) {
        Log.d("fragment", "On dialog dismiss");
       if(mMainFragment != null){
           mMainFragment.setMainTotal();
       }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("fragment", "on activity resume");
        if (mMainFragment != null) {
            mMainFragment.setMainTotal();
        }
    }

}
