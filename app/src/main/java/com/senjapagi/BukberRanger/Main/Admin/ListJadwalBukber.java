package com.senjapagi.BukberRanger.Main.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.senjapagi.BukberRanger.Adapter.ListArrayAdapter;
import com.senjapagi.BukberRanger.Main.General.dashboard_admin;
import com.senjapagi.BukberRanger.Model.JadwalModel;
import com.senjapagi.BukberRanger.API.APIInterface;
import com.senjapagi.BukberRanger.API.apiClient;
import com.senjapagi.qrtomysql.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListJadwalBukber extends AppCompatActivity {
    ListView listView;
    LinearLayout tagWarna;
    ArrayList<JadwalModel> dataJadwalBukber = new ArrayList<JadwalModel>();
    LinearLayout loadingIcon,LoadingJadwal;
    SwipeRefreshLayout refreshLayout;
    FloatingActionButton tambahJadwal;
    LinearLayout jadwalKosong;
    LinearLayout jadwalError ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_bukber);
        jadwalKosong=findViewById(R.id.jadwal_kosong);
        jadwalError=findViewById(R.id.jadwal_error);


        listView = findViewById(R.id.listJadwalBukber);
        tagWarna = findViewById(R.id.colorTag);
        loadingIcon = findViewById(R.id.loadingContainer);
        refreshLayout = findViewById(R.id.swipe_refresh);
        populateTable("");
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                populateTable("rl");
            }
        });





    }
    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(ListJadwalBukber.this, dashboard_admin.class);
        startActivity(setIntent);
        finish();
    }


    private void populateTable(final String statRefresh){
        if (statRefresh.equals("")){
            loadingIcon.setVisibility(View.VISIBLE);
            refreshLayout.setRefreshing(true);
        }
        APIInterface apiInterface = apiClient.getClientAPI().create(APIInterface.class);
        Call<List<JadwalModel>> call = apiInterface.getJadwal();
        call.enqueue(new Callback<List<JadwalModel>>() {
            @Override
            public void onResponse(Call<List<JadwalModel>> call, Response<List<JadwalModel>> response) {
                loadingIcon.setVisibility(View.GONE);
                refreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {
                    dataJadwalBukber.clear();
                    int total = response.body().size();
                    if(total<1){
                        jadwalKosong.setVisibility(View.VISIBLE);
                    }else{
                        jadwalKosong.setVisibility(View.GONE);
                        jadwalError.setVisibility(View.GONE);
                    for (int a = 0; a < total; a++) {
                        JadwalModel model = new JadwalModel(
                                response.body().get(a).getKategori(),
                                response.body().get(a).getTanggal(),
                                response.body().get(a).getKuota_ikhwan(),
                                response.body().get(a).getKuota_akhwat(),
                                response.body().get(a).getLauk(),
                                response.body().get(a).getCatatan(),
                                response.body().get(a).getStatus(),
                                response.body().get(a).getId());
                        String status = response.body().get(a).getStatus();
                        dataJadwalBukber.add(model);
                    }
                    }
                        ListArrayAdapter adapter = new ListArrayAdapter(ListJadwalBukber.this, R.layout.list_jadwal, dataJadwalBukber)
                        {
                            @Override
                            public void pasklik(TextView tv) {
                                Intent intent = new Intent(ListJadwalBukber.this, ManageJadwal.class);
                                intent.putExtra("ID_BUKBER", tv.getText().toString());
                                startActivity(intent);
                            }
                        };

                        listView.setAdapter(adapter);
                        if (adapter.getCount() < 1) {
                            loadingIcon.setVisibility(View.GONE);
                            refreshLayout.setRefreshing(false);
                            Toast.makeText(ListJadwalBukber.this, "Belum Ada Jadwal", Toast.LENGTH_SHORT).show();
                        } else {
                            loadingIcon.setVisibility(View.GONE);
                            refreshLayout.setRefreshing(false);
                            if (statRefresh.equals("")) {
                                Toast.makeText(ListJadwalBukber.this, "Jadwal Berhasil Ditampilkan", Toast.LENGTH_SHORT).show();
                            } else if (statRefresh.equals("rl")) {
                                Toast.makeText(ListJadwalBukber.this, "Berhasil Refresh Jadwal", Toast.LENGTH_SHORT).show();
                            }
                        }
                }else{
                    loadingIcon.setVisibility(View.GONE);
                    refreshLayout.setRefreshing(false);
                    Toast.makeText(ListJadwalBukber.this, "Server Error UwawawawaW", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<JadwalModel>> call, Throwable t) {
                loadingIcon.setVisibility(View.GONE);
                refreshLayout.setRefreshing(false);
                jadwalError.setVisibility(View.VISIBLE);
                Toast.makeText(ListJadwalBukber.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
