package com.itfun.calc.domain;

import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;

import com.itfun.calc.R;

public enum Theme {

    STANDARD(R.style.Theme_Calc, R.string.theme_stanadard, "standard"),
    NIGHT(R.style.Theme_Night, R.string.theme_night, "night"),
    BLUE_SKY(R.style.Theme_BlueSky, R.string.theme_blue_sky, "blue_sky");

    @StyleRes
    private final int theme;

    @StringRes
    private final int name;

    private String key;

    public int getTheme() {
        return theme;
    }

    public int getName() {
        return name;
    }


    Theme(int theme, int name, String key) {
        this.theme = theme;
        this.name = name;
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
