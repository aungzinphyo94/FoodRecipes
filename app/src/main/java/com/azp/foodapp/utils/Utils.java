package com.azp.foodapp.utils;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import java.util.Random;

public class Utils
{
    public static ColorDrawable[] vibrantLightColorList = {
            new ColorDrawable(Color.parseColor("#FFEEAD")),
            new ColorDrawable(Color.parseColor("#93CFB3")),
            new ColorDrawable(Color.parseColor("#FD7A7A")),
            new ColorDrawable(Color.parseColor("#FACA5F")),
            new ColorDrawable(Color.parseColor("#1BA798")),
            new ColorDrawable(Color.parseColor("#6AA9AE")),
            new ColorDrawable(Color.parseColor("#FFBF27")),
            new ColorDrawable(Color.parseColor("#D93947"))
    };

    public static ColorDrawable getRandomDrawableColor() {
        int idx = new Random().nextInt(vibrantLightColorList.length);
        return vibrantLightColorList[idx];
    }

}
