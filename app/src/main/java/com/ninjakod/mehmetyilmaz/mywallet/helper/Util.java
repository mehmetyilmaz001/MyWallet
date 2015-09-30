package com.ninjakod.mehmetyilmaz.mywallet.helper;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by mehmetyilmaz on 25/04/15.
 */
public class Util {

    public static String formatCurrency(short localeCode, double amount){

        String[] localeStr = new String[2];

        switch (localeCode){
            case Const.CURRENCY_TURKISH_LIRA:
                localeStr[0] = "tr";
                localeStr[1] = "TR";
                break;

            case Const.CURRENCY_EURO:
                localeStr[0] = "de";
                localeStr[1] = "DE";
                break;

            case Const.CURRENCY_US_DOLLAR:
                localeStr[0] = "en";
                localeStr[1] = "US";
                break;

            default:
                localeStr[0] = "tr";
                localeStr[1] = "TR";
                break;

        }

        Locale locale = new Locale(localeStr[0], localeStr[1]);
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);


        return currencyFormat.format(amount);
    }

    public static String removeLastChar(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }
        return s.substring(0, s.length()-1);
    }

    public static void expand(final View v) {
        v.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        v.getLayoutParams().height = 0;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? LinearLayout.LayoutParams.WRAP_CONTENT
                        : (int)(targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    v.setVisibility(View.GONE);
                }else{
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }
}
