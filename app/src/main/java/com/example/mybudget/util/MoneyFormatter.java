package com.example.mybudget.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class MoneyFormatter {

    public static String formatVND(long amount) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');

        DecimalFormat formatter = new DecimalFormat("#,###", symbols);

        return formatter.format(amount) + " đ";
    }
}
