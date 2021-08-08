package com.senjapagi.BukberRanger.Main;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.senjapagi.BukberRanger.Main.General.dashboard_main;
import com.senjapagi.qrtomysql.R;

public class Tutorial extends AppCompatActivity {

    Button btnHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        btnHome=findViewById(R.id.btn_home);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tutorial.this, dashboard_main.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
