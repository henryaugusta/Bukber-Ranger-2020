package com.senjapagi.BukberRanger.Main.Peserta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.senjapagi.qrtomysql.R;

public class BeforeFeedback extends AppCompatActivity {

    Button app,panitia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_feedback);

        app=findViewById(R.id.btn_feedback_aplikasi);
        panitia=findViewById(R.id.btn_kegiatan_bukber);

        app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BeforeFeedback.this, Feedback.class);
                intent.putExtra("URL","1");
                startActivity(intent);
            }
        });
        panitia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BeforeFeedback.this,Feedback.class);
                intent.putExtra("URL","2");
                startActivity(intent);
            }
        });






    }
}
