package com.example.weatherapp.utils;

import java.text.DecimalFormat;

public class NumberParser {
    public NumberParser() {
    }

    public static int convertDecimalToPercentage(double val) {
        return (int) Math.round(val * 100);
    }

    public static String convertNumberToTwoDecimalPLacesString(double val) {
        DecimalFormat decim = new DecimalFormat("0.00");
        return (String) decim.format(val);
    }
}
