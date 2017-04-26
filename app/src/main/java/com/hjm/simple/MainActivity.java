package com.hjm.simple;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements TextWatcher {

    private EditText wanEdt, qianEdt, baiEdt, shiEdt, geEdt;
    private TextView resultTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏  
        setContentView(R.layout.activity_main);

        wanEdt = (EditText) findViewById(R.id.wan_edt);
        qianEdt = (EditText) findViewById(R.id.qian_edt);
        baiEdt = (EditText) findViewById(R.id.bai_edt);
        shiEdt = (EditText) findViewById(R.id.shi_edt);
        geEdt = (EditText) findViewById(R.id.ge_edt);
        resultTv = (TextView) findViewById(R.id.result_tv);

        wanEdt.addTextChangedListener(this);
        qianEdt.addTextChangedListener(this);
        baiEdt.addTextChangedListener(this);
        shiEdt.addTextChangedListener(this);
        geEdt.addTextChangedListener(this);
    }

    private int getNumber() {
        return stringToInt(wanEdt.getText().toString()) * 10000 +
                stringToInt(qianEdt.getText().toString()) * 1000 +
                stringToInt(baiEdt.getText().toString()) * 100 +
                stringToInt(shiEdt.getText().toString()) * 10 +
                stringToInt(geEdt.getText().toString());
    }

    private int stringToInt(String s) {
        if (TextUtils.isEmpty(s))
            return 0;
        else
            return Integer.parseInt(s);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        resultTv.setText(getNumber() + "");
    }
}
