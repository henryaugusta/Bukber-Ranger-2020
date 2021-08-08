package com.senjapagi.BukberRanger.Main.Peserta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.senjapagi.BukberRanger.Main.General.dashboard_admin;
import com.senjapagi.BukberRanger.Main.General.dashboard_main;
import com.senjapagi.BukberRanger.Main.Webview.LihatPesertaWeb;
import com.senjapagi.BukberRanger.Model.LihatPesertaByPeserta;
import com.senjapagi.qrtomysql.R;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class PendaftaranBerhasil extends AppCompatActivity {
    TextView tpNama,tpUsername,tpJk;
    ImageView showQRContainer;
    Button btnHome,btnLihatPeserta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendaftaran_berhasil);

        btnHome=findViewById(R.id.btn_home);
        btnLihatPeserta=findViewById(R.id.btn_daftarPeserta);

        tpNama=findViewById(R.id.tv_namaPeserta);
        tpUsername=findViewById(R.id.tv_username);
        tpJk=findViewById(R.id.tv_jenisKelamin);
        showQRContainer=findViewById(R.id.show_qr_success);
        tpNama.setText(getIntent().getStringExtra("NAMA_PESERTA"));
        tpUsername.setText("Username : "+getIntent().getStringExtra("USERNAME"));
        tpJk.setText("Jenis Kelamin : "+getIntent().getStringExtra("JENIS_KELAMIN"));
        makeQR();

        btnLihatPeserta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PendaftaranBerhasil.this, Lihat_Peserta_Terdaftar.class);
                startActivity(intent);
                finish();
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PendaftaranBerhasil.this, dashboard_main.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void onBackPressed() {
        Intent setIntent = new Intent(PendaftaranBerhasil.this, dashboard_main.class);
        startActivity(setIntent);
        finish();
    }
    private void makeQR(){
        String token = getIntent().getStringExtra("TOKEN");
        final QRGEncoder qre = new QRGEncoder(token, null, QRGContents.Type.TEXT, 500);
        try {
            Handler pend = new Handler();
            Bitmap qrbm = qre.getBitmap();
            showQRContainer.setImageBitmap(qrbm);
        } catch (Exception t) {
            String error = t.toString();
            Toast.makeText(PendaftaranBerhasil.this, error, Toast.LENGTH_SHORT).show();
        }

    }

}
