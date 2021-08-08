package com.senjapagi.BukberRanger.Main.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.senjapagi.BukberRanger.Model.EditStatusModel;
import com.senjapagi.BukberRanger.Model.JadwalModel;
import com.senjapagi.BukberRanger.Model.UpdateJadwalModel;
import com.senjapagi.BukberRanger.API.APIInterface;
import com.senjapagi.BukberRanger.API.apiClient;
import com.senjapagi.qrtomysql.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageJadwal extends AppCompatActivity {

    EditText etKategori, etkuotaIkhwan, etkuotaAkhwat, etCatatan, etTanggal, etMenu;
    TextView tvId, tvStatus, extDate,extCategory;

    Button btnUpdate,btnBukaTutup,btnBatal;

    LinearLayout loadingJadwal,loadingUpdate;


    String ID_Bukber ="9999";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_jadwal);
        loadingJadwal=findViewById(R.id.loadingJadwalManage);
        loadingUpdate=findViewById(R.id.loadingUpdateManage);
        ID_Bukber = getIntent().getStringExtra("ID_BUKBER");
        Toast.makeText(this, ID_Bukber, Toast.LENGTH_SHORT).show();

        btnBukaTutup=findViewById(R.id.btn_tutuppendaftaran);
        etKategori = findViewById(R.id.et_kategori);
        etTanggal = findViewById(R.id.et_tanggal_bukber);
        etkuotaAkhwat = findViewById(R.id.et_porsi_akhwat);
        etkuotaIkhwan = findViewById(R.id.et_porsi_ikhwan);
        etCatatan = findViewById(R.id.et_catatan);
        etMenu = findViewById(R.id.et_menu);
        tvId = findViewById(R.id.tv_id);
        tvStatus=findViewById(R.id.tv_status);
        btnUpdate=findViewById(R.id.btn_updateData);
        extDate=findViewById(R.id.existing_date);
        extCategory=findViewById(R.id.existing_category);
        getJadwalBukberOpen("");
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateJadwal();
            }
        });

        btnBatal=findViewById(R.id.btn_batalkan);
        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageJadwal.this, ListJadwalBukber.class);
                startActivity(intent);
                finish();
            }
        });

    btnBukaTutup.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            updateStatus();
        }
    });

    }
    @Override
    public void onBackPressed() {
       System.exit(0);
    }


    private void getJadwalBukberOpen(String param) {
        loadingJadwal.setVisibility(View.VISIBLE);
        APIInterface apiInterface = apiClient.getClientAPI().create(APIInterface.class);
        Call<List<JadwalModel>> calling = apiInterface.getJadwalDetailByID(ID_Bukber);
        calling.enqueue(new Callback<List<JadwalModel>>() {
            @Override
            public void onResponse(Call<List<JadwalModel>> call, Response<List<JadwalModel>> response) {
                loadingJadwal.setVisibility(View.GONE);
                    for (int i = 0; i < response.body().size(); i++) {
                        extDate.setText(response.body().get(i).getTanggal());
                        extCategory.setText(response.body().get(i).getKategori());
                        etKategori.setText(response.body().get(i).getKategori());
                        tvId.setText(String.valueOf(response.body().get(i).getId()));
                        etMenu.setText(response.body().get(i).getLauk());
                        etCatatan.setText(response.body().get(i).getCatatan());
                        etTanggal.setText(response.body().get(i).getTanggal());
                        etkuotaIkhwan.setText(String.valueOf(response.body().get(i).getKuota_ikhwan()));
                        etkuotaAkhwat.setText(String.valueOf(response.body().get(i).getKuota_akhwat()));
                        tvStatus.setText(response.body().get(i).getStatus());
                    }
                }
            @Override
            public void onFailure(Call<List<JadwalModel>> call, Throwable t) {
                loadingJadwal.setVisibility(View.GONE);
                Toast.makeText(ManageJadwal.this, "Tidak dapat terhubung dengan server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateJadwal(){
        loadingUpdate.setVisibility(View.VISIBLE);
        String id=tvId.getText().toString();
        String tanggal=extDate.getText().toString();
        String menu = etMenu.getText().toString();
        String catatan = etCatatan.getText().toString();
        String kategori=etKategori.getText().toString();
        String kuotaIkhwan=String.valueOf(etkuotaIkhwan.getText().toString());
        String kuotaAkhwat=String.valueOf(etkuotaAkhwat.getText().toString());
        if(kategori.equals("")){
            extCategory.setError("Harap isi bidang ini");
        }if(tanggal.equals("")){
            extDate.setError("Harap si Bidang ini");
        }if(kuotaIkhwan.equals("")){
            etkuotaIkhwan.setError("Harap si Bidang ini");
        }if(kuotaAkhwat.equals("")){
            etkuotaAkhwat.setError("Harap si Bidang ini");
        }else {
            APIInterface apiInterface = apiClient.getClientAPI().create(APIInterface.class);
            Call<UpdateJadwalModel> calling = apiInterface.updateJadwal(id, kategori, tanggal, kuotaIkhwan, kuotaAkhwat, menu, catatan);
            calling.enqueue(new Callback<UpdateJadwalModel>() {
                @Override
                public void onResponse(Call<UpdateJadwalModel> call, Response<UpdateJadwalModel> response) {
                    loadingUpdate.setVisibility(View.GONE);
                    Toast.makeText(ManageJadwal.this, "Berhasil Update", Toast.LENGTH_SHORT).show();
//                    Intent a = new Intent(ManageJadwal.this,ListJadwalBukber.class);
//                    startActivity(a);
//                    finish();
                    getJadwalBukberOpen("");
                }

                @Override
                public void onFailure(Call<UpdateJadwalModel> call, Throwable t) {
                    loadingUpdate.setVisibility(View.GONE);
                    getJadwalBukberOpen("");
                    Toast.makeText(ManageJadwal.this, "Berhasil Update", Toast.LENGTH_SHORT).show();
                    Toast.makeText(ManageJadwal.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                }
            });

        }

    }
    private void updateStatus(){
        APIInterface apiInterface = apiClient.getClientAPI().create(APIInterface.class);

        String id=tvId.getText().toString();
        String status = tvStatus.getText().toString();
        String statusUpdate="Default";

        if(status.equals("tersedia")){
            statusUpdate="selesai";
        }else if(status.equals("selesai")){
            statusUpdate="tersedia";
        }

        Call<EditStatusModel> calling = apiInterface.updateStatusBukber(id,statusUpdate);
        calling.enqueue(new Callback<EditStatusModel>() {
            @Override
            public void onResponse(Call<EditStatusModel> call, Response<EditStatusModel> response) {
                getJadwalBukberOpen("");
                Toast.makeText(ManageJadwal.this, "Berhasil Ubah Status", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<EditStatusModel> call, Throwable t) {
                getJadwalBukberOpen("");
                Toast.makeText(ManageJadwal.this, "Gagal Ubah Status", Toast.LENGTH_SHORT).show();
            }
        });

    }
}