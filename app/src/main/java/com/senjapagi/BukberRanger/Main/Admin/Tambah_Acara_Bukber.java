package com.senjapagi.BukberRanger.Main.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.senjapagi.BukberRanger.Model.JadwalModel;
import com.senjapagi.BukberRanger.API.APIInterface;
import com.senjapagi.BukberRanger.API.apiClient;
import com.senjapagi.BukberRanger.Main.General.dashboard_admin;
import com.senjapagi.qrtomysql.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Tambah_Acara_Bukber extends AppCompatActivity {

    APIInterface apiInterface;
    Button btnInputJadwal;
    String kategori[] =
            {"Bukber Puasa Ramadhan",
            "Bukber Senin-Kamis",
            "Bukber Ayyamul Bidh",
            "Bukber Asyuro",
            "Bukber Puasa Syawwal",
            "Bukber Puasa Arafah",
            "Bukber Puasa Dzulhijjah",
            "Bukber Puasa Muharrom",
            "Makan Pagi Bersama",
            "Makan Siang Jumat"};
    EditText ktg;

    EditText tanggal,kuotaIkhwan,kuotaAkhwat,lauk,catatan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah__acara__bukber);
        ktg = findViewById(R.id.spinner_kategori);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item,list);
        ArrayAdapter adapter = new ArrayAdapter(Tambah_Acara_Bukber.this, android.R.layout.simple_list_item_1, kategori);
//        ktg.setAdapter(adapter);

        tanggal=findViewById(R.id.et_tanggal_bukber);
        kuotaAkhwat=findViewById(R.id.et_kuota_akhwat);
        kuotaIkhwan=findViewById(R.id.et_kuota_ikhwan);
        lauk=findViewById(R.id.et_menu_makanan);
        catatan=findViewById(R.id.et_catatan);
        btnInputJadwal=findViewById(R.id.btn_input);

        btnInputJadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String kategori = ktg.getText().toString();
                final String itanggal,iLauk,iCatatan;
                final int ikAkhwat,ikIkhwan;
                itanggal=tanggal.getText().toString();
                ikIkhwan=Integer.valueOf(kuotaIkhwan.getText().toString());
                ikAkhwat=Integer.valueOf(kuotaAkhwat.getText().toString());
                iCatatan=catatan.getText().toString();
                iLauk=lauk.getText().toString();
                scanPeserta(kategori,itanggal,ikIkhwan,ikAkhwat,iLauk,iCatatan);
            }
        });
    }
    private void scanPeserta(String kategori, String tanggal, int kuotaIkhwan, int kuotaAkhwat, String lauk, String catatan) {
        apiInterface = apiClient.getClientAPI().create(APIInterface.class);
        String status="tersedia";
        Call<JadwalModel> call = apiInterface.insertJadwal(kategori,tanggal,kuotaIkhwan,kuotaAkhwat,lauk,catatan,status);
       call.enqueue(new Callback<JadwalModel>() {
           @Override
           public void onResponse(Call<JadwalModel> call, Response<JadwalModel> response) {
               Toast.makeText(Tambah_Acara_Bukber.this, "Berhasil Tambah Jadwal", Toast.LENGTH_SHORT).show();
               Intent intent = new Intent(Tambah_Acara_Bukber.this, dashboard_admin.class);
               startActivity(intent);
           }
           @Override
           public void onFailure(Call<JadwalModel> call, Throwable t) {
               Toast.makeText(Tambah_Acara_Bukber.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
               Toast.makeText(Tambah_Acara_Bukber.this, t.getMessage(), Toast.LENGTH_SHORT).show();
           }
       });
    }
}
