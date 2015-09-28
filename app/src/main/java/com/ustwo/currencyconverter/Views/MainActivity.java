package com.ustwo.currencyconverter.Views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.ustwo.currencyconverter.R;
import com.ustwo.currencyconverter.models.Currency;
import com.ustwo.currencyconverter.tasks.ConverterTask;

public class MainActivity extends AppCompatActivity implements HorizontalPicker.OnItemSelected, HorizontalPicker.OnItemClicked {

    private EditText etAud;
    private Currency currency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etAud = (EditText) findViewById(R.id.etAud);
        etAud.addTextChangedListener(tw);

        HorizontalPicker picker = (HorizontalPicker) findViewById(R.id.picker);
        picker.setOnItemClickedListener(this);
        picker.setOnItemSelectedListener(this);

        ConverterTask fetchTask = new ConverterTask(MainActivity.this);
        fetchTask.execute();
    }

    @Override
    public void onItemSelected(int index)    {
        convertCurrency(index);
    }

    @Override
    public void onItemClicked(int index) {
        convertCurrency(index);
    }

    public void convertCurrency(int index) {
        try {
            float amount = Float.parseFloat(etAud.getText().toString().replace("$", ""));
            float conversionRate = 0f;
            switch (index) {
                case 0:
                    //CAD
                    conversionRate = Float.parseFloat(currency.getCAD());
                    break;
                case 1:
                    //EUR
                    conversionRate = Float.parseFloat(currency.getEUR());
                    break;
                case 2:
                    //GBP
                    conversionRate = Float.parseFloat(currency.getGBP());
                    break;
                case 3:
                    //JPY
                    conversionRate = Float.parseFloat(currency.getJPY());
                    break;
                case 4:
                    //USD
                    conversionRate = Float.parseFloat(currency.getUSD());
                    break;
            }

            float audAmount = amount * conversionRate;
            TextView tvCurrency = (TextView)findViewById(R.id.tvCurrency);
            tvCurrency.setText(String.valueOf(audAmount));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void updateView(Currency currency) {
        try {
           this.currency = currency;
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    TextWatcher tw = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {

            if (!s.toString().matches("^\\$(\\d{1,3}(\\,\\d{3})*|(\\d+))(\\.\\d{2})?$")) {
                String userInput = "" + s.toString().replaceAll("[^\\d]", "");
                StringBuilder cashAmountBuilder = new StringBuilder(userInput);

                while (cashAmountBuilder.length() > 3 && cashAmountBuilder.charAt(0) == '0') {
                    cashAmountBuilder.deleteCharAt(0);
                }
                while (cashAmountBuilder.length() < 3) {
                    cashAmountBuilder.insert(0, '0');
                }
                cashAmountBuilder.insert(cashAmountBuilder.length() - 2, '.');

                etAud.removeTextChangedListener(this);
                etAud.setText(cashAmountBuilder.toString());

                etAud.setTextKeepState("$" + cashAmountBuilder.toString());
                Selection.setSelection(etAud.getText(), cashAmountBuilder.toString().length() + 1);

                etAud.addTextChangedListener(this);
            }
        }
    };

}
