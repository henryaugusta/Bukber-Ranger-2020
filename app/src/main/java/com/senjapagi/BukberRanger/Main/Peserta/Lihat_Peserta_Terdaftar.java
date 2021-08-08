package com.senjapagi.BukberRanger.Main.Peserta;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.senjapagi.BukberRanger.Adapter.AdapterPeserta;
import com.senjapagi.BukberRanger.API.apiClient;
import com.senjapagi.BukberRanger.Model.Peserta;
import com.senjapagi.qrtomysql.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Lihat_Peserta_Terdaftar extends AppCompatActivity {

    Button btnHome,btnTest;
    TextView txtTitle,txtDate,txtIkhwan,txtAkhwat,txtSisaIkhwan,txtSisaAkhwat,
            txtId,txtMenu,txtNote,labelMenu,labelNote;
    ShimmerFrameLayout sJadwal,sKuota,sLoadPeserta;
    String id="0";
    RecyclerView recyclerView;
    EditText etSearch;
    private AdapterPeserta adapterPeserta;

    private  ArrayList<Peserta> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_peserta_terdaftar);
        init();
        etSearch.setEnabled(false);


        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());

            }
        });


        data = new ArrayList<>();
        recyclerView = findViewById(R.id.rv_peseta);
        recyclerView.setHasFixedSize(true); //agar recyclerView tergambar lebih cepat
        recyclerView.setLayoutManager(new LinearLayoutManager(Lihat_Peserta_Terdaftar.this,RecyclerView.VERTICAL,false));



        getJadwal();
        getPeserta();


    }

    private void filter(String text) {
        ArrayList<Peserta> filteredList = new ArrayList<>();

        for (Peserta ps : data){
             if(ps.getUsername().toLowerCase().contains(text.toLowerCase())
                     ||ps.getNama().toLowerCase().contains(text.toLowerCase())){
                 filteredList.add(ps);
             }
        }
        adapterPeserta.filterList(filteredList);


    }

    private void init(){
        etSearch = findViewById(R.id.txtSearch);
        sJadwal=findViewById((R.id.shimmer_view_jadwal));
        sKuota=findViewById((R.id.shimmer_view_kuota));
        sLoadPeserta=findViewById((R.id.shimmer_load_peserta));
        txtTitle=findViewById(R.id.textNamaJadwal);
        txtDate=findViewById(R.id.textDateSchedule);
        txtIkhwan=findViewById(R.id.jmlIkhwan);
        txtAkhwat=findViewById(R.id.jmlAkhwat);
        txtSisaIkhwan=findViewById(R.id.sisaIkhwan);
        txtSisaAkhwat=findViewById(R.id.sisaAkhwat);
        txtId=findViewById(R.id.id_bukber);
        recyclerView=findViewById(R.id.rv_peseta);

        findViewById(R.id.txtMenu).setVisibility(View.GONE);
        findViewById(R.id.txtNote).setVisibility(View.GONE);
        findViewById(R.id.labelMenu).setVisibility(View.GONE);
        findViewById(R.id.labelNote).setVisibility(View.GONE);
    }


    private void getJadwal(){
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                . writeTimeout(20, TimeUnit.SECONDS)
                .build();
        sKuota.startShimmer();
        sJadwal.startShimmer();
        sLoadPeserta.hideShimmer();
        AndroidNetworking.get(apiClient.URL_Jadwal)
                .setTag("getScheduler")
                .setPriority(Priority.HIGH)
                .setOkHttpClient(okHttpClient)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        sKuota.hideShimmer();
                        sJadwal.hideShimmer();
                      try {
                            JSONObject data = response.getJSONObject(0);
                            txtDate.setText(data.getString("tanggal"));
                            txtTitle.setText(data.getString("kategori"));
                            txtIkhwan.setText(data.getString("jumlah_ikhwan_terdaftar"));
                            txtAkhwat.setText(data.getString("jumlah_akhwat_terdaftar"));
                            txtSisaIkhwan.setText(data.getString("sisa_ikhwan"));
                            txtSisaAkhwat.setText(data.getString("sisa_akhwat"));
                            txtId.setText(data.getString("id"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Lihat_Peserta_Terdaftar.this, "Gagal Euy", Toast.LENGTH_SHORT).show();
                            Toast.makeText(Lihat_Peserta_Terdaftar.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        SnackBarInternet();
                        Toast.makeText(Lihat_Peserta_Terdaftar.this, "Gagal Request Data", Toast.LENGTH_SHORT).show();
                        Toast.makeText(Lihat_Peserta_Terdaftar.this, error.getErrorBody(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getPeserta(){
        sLoadPeserta.startShimmer();
        AndroidNetworking.post(apiClient.URL_ListPeserta)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        etSearch.setEnabled(true);
                        sLoadPeserta.setVisibility(View.GONE);
                        int total=response.length();
                        for (int i = 0; i<total;i++){
                            try {
                                JSONObject responseData = response.getJSONObject(i);
//                                menambah data dari json ke ArrayList
                                data.add(new Peserta(
                                        responseData.getString("username"),
                                        responseData.getString("nama"),
                                        responseData.getString("id_peserta"),
                                        responseData.getString("jenis_kelamin")));

                                recyclerView.setHasFixedSize(true); //agar recyclerView tergambar lebih cepat
                                recyclerView.setLayoutManager(new LinearLayoutManager(Lihat_Peserta_Terdaftar.this,RecyclerView.VERTICAL,false));
                                adapterPeserta = new AdapterPeserta(data);
                                recyclerView.setAdapter(adapterPeserta);
                            } catch (JSONException e) {
                                sLoadPeserta.setVisibility(View.GONE);
                                e.printStackTrace();
                                SnackBarInternet();
                            }
                        }
                    }
                    
                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(Lihat_Peserta_Terdaftar.this, "Tidak Menerima Respon", Toast.LENGTH_SHORT).show();
                        txtId.setText(anError.getLocalizedMessage());
                    }
                });
    }
    public void SnackBarInternet(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Gagal Terhubung Dengan Server");
        builder1.setCancelable(true);

        builder1.setNegativeButton(
                "Refresh",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getJadwal();
                        getPeserta();
                        dialog.cancel();
                    }
                });

        builder1.setPositiveButton(
                "Keluar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            finishAndRemoveTask();
                            System.exit(1);
                        }else{
                            finishAffinity();
                            System.exit(1);
                        }
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

}
