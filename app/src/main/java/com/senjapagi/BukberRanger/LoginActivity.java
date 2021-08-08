package com.senjapagi.BukberRanger;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.senjapagi.qrtomysql.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        UIBeautifier ui = new UIBeautifier();
        Window w = getWindow();
        int a = WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
//        ui.transparentStatusBar(a,w);
    }
}
