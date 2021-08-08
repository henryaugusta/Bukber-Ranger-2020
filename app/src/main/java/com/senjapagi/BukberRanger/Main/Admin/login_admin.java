package com.senjapagi.BukberRanger.Main.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.senjapagi.BukberRanger.Main.General.dashboard_admin;
import com.senjapagi.qrtomysql.R;

public class login_admin extends AppCompatActivity {

    EditText username,password;
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);

        btn_login=findViewById(R.id.btnadm_login);

        username=findViewById(R.id.etdm_username);
        password=findViewById(R.id.etdm_password);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String usn = username.getText().toString();
                String pass = password.getText().toString();

                if(usn.equals("admin_bukber")&&pass.equals("123456")) {
                    Intent intent = new Intent(login_admin.this, dashboard_admin.class);
                    startActivity(intent);
                }else{
                  username.setError("Username atau Password Masih Salah");
                  password.setError("Username atau Password Masih Salah");
                }

            }
        });

    }
}
