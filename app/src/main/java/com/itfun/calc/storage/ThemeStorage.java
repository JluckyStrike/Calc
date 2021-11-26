package com.itfun.calc.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.itfun.calc.domain.Theme;

public class ThemeStorage {
    private static final String SAVED_THEME_APP = "SAVED_THEME_APP";

    private final Context context;

    public ThemeStorage(Context context) {
        this.context = context;
    }

    public void saveTheme(Theme theme) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Themes", Context.MODE_PRIVATE);

        sharedPreferences.edit()
                .putString(SAVED_THEME_APP, theme.getKey())
                .apply();
    }

    public Theme getSavedTheme() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Themes", Context.MODE_PRIVATE);

        String key = sharedPreferences.getString(SAVED_THEME_APP, Theme.STANDARD.getKey());

        for (Theme theme : Theme.values()) {
            if (key.equals(theme.getKey())) {
                return theme;
            }
        }

        return Theme.STANDARD;
    }
}
