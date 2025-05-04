package com.example.k22411c_firstdegree;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText edtCoefficientA, edtCoefficientB;
    TextView txtResult;
    Button btnEnglish, btnVietnamese, btnFrench, btnSpanish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();  // Load ngôn ngữ đã lưu
        setContentView(R.layout.activity_main);

        addViews();
        addEvents();
    }

    private void addViews() {
        edtCoefficientA = findViewById(R.id.edtCoefficientA);
        edtCoefficientB = findViewById(R.id.edtCoefficientB);
        txtResult = findViewById(R.id.txtResult);

        btnEnglish = findViewById(R.id.button2);
        btnVietnamese = findViewById(R.id.button3);
        btnFrench = findViewById(R.id.button4);
        btnSpanish = findViewById(R.id.button5);
    }

    private void addEvents() {
        btnEnglish.setOnClickListener(v -> setLocale("en"));
        btnVietnamese.setOnClickListener(v -> setLocale("vi"));
        btnFrench.setOnClickListener(v -> setLocale("fr"));
        btnSpanish.setOnClickListener(v -> setLocale("es"));
    }

    private void setLocale(String langCode) {
        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        String currentLang = prefs.getString("My_Lang", "");

        if (currentLang.equals(langCode)) return;

        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("My_Lang", langCode);
        editor.apply();

        recreate(); // cập nhật lại giao diện sau khi đổi ngôn ngữ
    }

    private void loadLocale() {
        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        String lang = prefs.getString("My_Lang", "");
        if (!lang.isEmpty()) {
            Locale locale = new Locale(lang);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.setLocale(locale);
            getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        }
    }

    public void do_solution(View view) {
        String hsa = edtCoefficientA.getText().toString();
        String hsb = edtCoefficientB.getText().toString();

        if (hsa.isEmpty() || hsb.isEmpty()) {
            txtResult.setText("");
            return;
        }

        double a = Double.parseDouble(hsa);
        double b = Double.parseDouble(hsb);

        if (a == 0 && b == 0) {
            txtResult.setText(getResources().getText(R.string.title_infinity));
        } else if (a == 0) {
            txtResult.setText(getResources().getText(R.string.title_no_solution));
        } else {
            double x = -b / a;
            txtResult.setText("x=" + x);
        }
    }

    public void do_next(View view) {
        edtCoefficientA.setText("");
        edtCoefficientB.setText("");
        txtResult.setText("");
        edtCoefficientA.requestFocus();
    }

    public void do_exit(View view) {
        finish();
    }
}
