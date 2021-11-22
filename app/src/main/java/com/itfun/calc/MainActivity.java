package com.itfun.calc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.itfun.calc.calculator.Calculator;

public class MainActivity extends AppCompatActivity {
    private Switch switchTheme;
    private TextView textView;
    private String numsStr = "";
    private String oper = "";
    private double num1 = 0;
    private double num2 = 0;
    private Calculator calculator;
    private String appThemeString = "Светлая";

    private final int MAX_INPUT_LENGTH = 15;
    private final String SHARED_PREFS = "shared_prefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(getAppTheme() ? R.style.AppThemeLight : R.style.AppThemeDark);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calculator = new Calculator();
        textView = findViewById(R.id.result_field);
        Button button0 = findViewById(R.id.button_0);
        Button button1 = findViewById(R.id.button_1);
        Button button2 = findViewById(R.id.button_2);
        Button button3 = findViewById(R.id.button_3);
        Button button4 = findViewById(R.id.button_4);
        Button button5 = findViewById(R.id.button_5);
        Button button6 = findViewById(R.id.button_6);
        Button button7 = findViewById(R.id.button_7);
        Button button8 = findViewById(R.id.button_8);
        Button button9 = findViewById(R.id.button_9);
        Button buttonC = findViewById(R.id.button_с);
        Button buttonPoint = findViewById(R.id.button_point);
        Button buttonPlus = findViewById(R.id.button_plus);
        Button buttonMinus = findViewById(R.id.button_minus);
        Button buttonMulty = findViewById(R.id.button_multy);
        Button buttonDiv = findViewById(R.id.button_div);
        Button buttonPercent = findViewById(R.id.button_percent);
        Button buttonEqual = findViewById(R.id.button_equal);
        switchTheme = (Switch) findViewById(R.id.switch_app_theme);
        switchTheme.setChecked(!getAppTheme());

        if (savedInstanceState != null) {
            numsStr = savedInstanceState.getString("NumsStr");
            oper = savedInstanceState.getString("Oper");
            num1 = savedInstanceState.getDouble("Num1");
            num2 = savedInstanceState.getDouble("Num2");
            textView.setText(numsStr);
        }

        switchTheme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                setAppTheme(!getAppTheme());

                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (numsStr.length() <= MAX_INPUT_LENGTH) {
                    if (!numsStr.startsWith("0") || numsStr.contains(".")) {
                        if (oper.isEmpty()) {
                            numsStr += view.getTag().toString();
                            num1 = Double.parseDouble(numsStr);
                            textView.setText(numsStr);
                        } else {
                            numsStr += view.getTag().toString();
                            num2 = Double.parseDouble(numsStr);
                            textView.setText(numsStr);
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            R.string.max_input_length,
                            Toast.LENGTH_SHORT).show();
                }
            }
        };

        button0.setOnClickListener(onClickListener);
        button1.setOnClickListener(onClickListener);
        button2.setOnClickListener(onClickListener);
        button3.setOnClickListener(onClickListener);
        button4.setOnClickListener(onClickListener);
        button5.setOnClickListener(onClickListener);
        button6.setOnClickListener(onClickListener);
        button7.setOnClickListener(onClickListener);
        button8.setOnClickListener(onClickListener);
        button9.setOnClickListener(onClickListener);

        buttonC.setOnClickListener(view -> {
            setEmptyText();
        });

        buttonPoint.setOnClickListener(view -> {
            if (numsStr.length() > 0 && !numsStr.contains(".")) {
                numsStr += view.getTag().toString();
                textView.setText(numsStr);
            }
        });

        buttonPlus.setOnClickListener(view -> {
            setMathOper("+");
        });

        buttonMinus.setOnClickListener(view -> {
            setMathOper("-");
        });

        buttonMulty.setOnClickListener(view -> {
            setMathOper("*");
        });

        buttonDiv.setOnClickListener(view -> {
            setMathOper("/");
        });

        buttonPercent.setOnClickListener(view -> {
            setMathOper("%");
        });

        buttonEqual.setOnClickListener(view -> {
            switch (oper) {
                case "+":
                    numsStr = String.valueOf(calculator.plus(num1, num2));
                    textView.setText(numsStr);
                    break;
                case "-":
                    numsStr = String.valueOf(calculator.minus(num1, num2));
                    textView.setText(numsStr);
                    break;
                case "*":
                    numsStr = String.valueOf(calculator.multy(num1, num2));
                    textView.setText(numsStr);
                    break;
                case "/":
                    numsStr = String.valueOf(calculator.div(num1, num2));
                    textView.setText(numsStr);
                    break;
                case "%":
                    numsStr = String.valueOf(calculator.percent(num1, num2));
                    textView.setText(numsStr);
                    break;
            }
            oper = "";
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("NumsStr", numsStr);
        outState.putString("Oper", oper);
        outState.putDouble("Num1", num1);
        outState.putDouble("Num2", num2);
    }

    private void setMathOper(String sym) {
        if (!numsStr.isEmpty()) {
            num1 = Double.parseDouble(numsStr);
        }
        oper = sym;
        setEmptyText();
    }

    private void setEmptyText() {
        numsStr = "";
        textView.setText(numsStr);
    }

    private void setAppTheme(boolean flag) {
        SharedPreferences sharedPref = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("AppTheme", flag);
        if (flag) {
            editor.putString("AppThemeStr", "Светлая");
        } else {
            editor.putString("AppThemeStr", "Темная");
        }
        editor.apply();
    }

    private boolean getAppTheme() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String a = sharedPreferences.getString("AppThemeStr", "Светлая");
        return sharedPreferences.getBoolean("AppTheme", true);
    }
}