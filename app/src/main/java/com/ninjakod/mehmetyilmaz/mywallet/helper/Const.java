package com.ninjakod.mehmetyilmaz.mywallet.helper;

public class Const {
        public static final byte ACCOUNT_TYPE_CASH = 0;
        public static final byte ACCOUNT_TYPE_BANK = 1;


        public static final byte TRANSACTION_TYPE_ADD      = 0;
        public static final byte TRANSACTION_TYPE_SUBTRACT = 1;


        public static final byte CURRENCY_TURKISH_LIRA = 0;
        public static final byte CURRENCY_US_DOLLAR    = 1;
        public static final byte CURRENCY_EURO         = 2;

        public static final byte LAST_ONE_MONTH = 0;
        public static final byte LAST_SIX_MONTH = 1;
        public static final byte LAST_ONE_YEAR  = 2;

        public static final String LAST_ONE_MONTH_CLAUSE = "'start of month'";
        public static final String LAST_SIX_MONTH_CLAUSE = "'-6 months'";
        public static final String LAST_ONE_YEAR_CLAUSE  = "'start of year'";

        public static final byte SELECT_ALL_DATA  = 99;
}