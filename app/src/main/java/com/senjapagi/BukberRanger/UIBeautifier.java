package com.senjapagi.BukberRanger;

import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class UIBeautifier extends AppCompatActivity {

    public final void transparentStatusBar(int a,Window w){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            w.setFlags(a,a);
        }
}

}
