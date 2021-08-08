package com.senjapagi.BukberRanger.Main.General;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.senjapagi.BukberRanger.Main.Admin.ScanQR;
import com.senjapagi.BukberRanger.Main.Admin.Tambah_Acara_Bukber;
import com.senjapagi.BukberRanger.Main.Webview.LihatPesertaWebAdmin;
import com.senjapagi.BukberRanger.Main.Admin.ListJadwalBukber;
import com.senjapagi.qrtomysql.R;

public class dashboard_admin extends AppCompatActivity {

    LinearLayout manageJadwal,tambahBukber,scanPeserta,lihatPesertaAktif;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_admin);
        dialogCheckPermission();
        manageJadwal=findViewById(R.id.btn_manage_jadwal);
        tambahBukber=findViewById(R.id.btn_tambah_bukber);
        scanPeserta=findViewById(R.id.btn_scan_peserta);
        lihatPesertaAktif=findViewById(R.id.btn_lihat_peserta_aktif);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        scanPeserta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dashboard_admin.this, ScanQR.class);
                startActivity(intent);
            }
        });

        manageJadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dashboard_admin.this, ListJadwalBukber.class);
                startActivity(intent);
            }
        });

        tambahBukber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dashboard_admin.this, Tambah_Acara_Bukber.class);
                startActivity(intent);
            }
        });
        lihatPesertaAktif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dashboard_admin.this, LihatPesertaWebAdmin.class);
                startActivity(intent);
            }
        });
    }
    private void dialogCheckPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(dashboard_admin.this, new String[]{android.Manifest.permission.CAMERA}, 50);
            Toast.makeText(this, "Aktifkan Permission Camera", Toast.LENGTH_SHORT).show();
        } else {
         //Do Nothing
        }
    }
    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(dashboard_admin.this, dashboard_main.class);
        startActivity(setIntent);
        finish();
    }
}
