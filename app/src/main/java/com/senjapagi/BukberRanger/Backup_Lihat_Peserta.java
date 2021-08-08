//package com.senjapagi.BukberRanger;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.constraintlayout.widget.ConstraintLayout;
//import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.view.View;
//import android.widget.Button;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.facebook.shimmer.ShimmerFrameLayout;
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.senjapagi.BukberRanger.API.APIInterface;
//import com.senjapagi.BukberRanger.Adapter.AdapterPesertabyPeserta;
//import com.senjapagi.BukberRanger.Main.General.dashboard_main;
//import com.senjapagi.BukberRanger.Model.JadwalModel;
//import com.senjapagi.BukberRanger.Model.LihatPesertaByPeserta;
//import com.senjapagi.BukberRanger.API.apiClient;
//import com.senjapagi.qrtomysql.R;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class Backup_Lihat_Peserta extends AppCompatActivity {
//    TextView tvKategori,tvTanggal,tvStatus,tvPorsiIkwan,tvPorsiAkhwat,tvMenu,tvCatatan,tvID;
//    LinearLayout loadingIcon,LoadingIconJadwal;
//    APIInterface apiInterface;
//    SwipeRefreshLayout refreshLayout;
//    ArrayList<LihatPesertaByPeserta> dataPesertabyPeserta = new ArrayList<LihatPesertaByPeserta>();
//    ListView listView;
//    AdapterPesertabyPeserta adapter;
//    LinearLayout infoBelumAdaJadwal,infoTampilJadwalError,containerJadwalUtama;
//    TextView pesertaKosong,pesertaError;
//    LinearLayout loadingAll,koneksiError;
//    ConstraintLayout containerAll;
//    Button btnRefresh,btnMenuUtama;
//    FloatingActionButton btnRefreshonDemand;
//    Button btnHome;
//    ShimmerFrameLayout container;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_lihat_peserta_terdaftar);
//
//        btnMenuUtama=findViewById(R.id.btn_home);
//
//
//        infoBelumAdaJadwal=findViewById(R.id.jadwal_kosong);
//        infoTampilJadwalError=findViewById(R.id.koneksiError);
//        loadingIcon=findViewById(R.id.loadingContainer);
////        refreshLayout=findViewById(R.id.swipe_refresh);
//        listView=findViewById(R.id.listPesertabyPeserta);
//        LoadingIconJadwal=findViewById(R.id.loadingContainerAtas);
//        pesertaKosong=findViewById(R.id.keteranganPendaftaran0);
//        pesertaError=findViewById(R.id.pesertaError);
//        loadingAll=findViewById(R.id.loadingJadwalAll);
//
//
//        tvKategori=findViewById(R.id.tvdb_kategori_bukber);
//        tvTanggal=findViewById(R.id.tvdb_tanggal_bukber);
//        tvPorsiIkwan=findViewById(R.id.tvdb_porsi_ikhwan);
//        tvPorsiAkhwat=findViewById(R.id.tvdb_porsi_akhwat);
//        tvMenu=findViewById(R.id.tvdb_lauk);
//        tvStatus=findViewById(R.id.tvdb_status_bukber);
//        tvCatatan=findViewById(R.id.tvdb_catatan);
//        tvID=findViewById(R.id.tvdb_id_kegiatan);
//        containerAll=findViewById(R.id.container_All);
//        koneksiError=findViewById(R.id.koneksiError);
//
//        btnRefresh=findViewById(R.id.btn_refresh);
//        btnRefreshonDemand=findViewById(R.id.btn_refreshOnDemand);
////        container=findViewById(R.id.shimmer_view_container);
//
//        btnMenuUtama.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Lihat_Peserta_Terdaftar.this, dashboard_main.class);
//                startActivity(intent);
//                finish();
//            }
//        });
////        container.startShimmer();
//
//        getJadwalBukberOpen("");
//
////        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
////            @Override
////            public void onRefresh() {
////                refreshLayout.setRefreshing(true);
////                pesertaError.setVisibility(View.GONE);
////                pesertaKosong.setVisibility(View.GONE);
////                getJadwalBukberOpen("");
////            }
////        });
//
//        btnRefresh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getJadwalBukberOpen("");
//            }
//        });
//        btnRefreshonDemand.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getJadwalBukberOpen("");
//            }
//        });
//    }
//    @Override
//    public void onBackPressed() {
//        Intent setIntent = new Intent(Lihat_Peserta_Terdaftar.this, dashboard_main.class);
//        startActivity(setIntent);
//        finish();
//    }
//
//    private void getJadwalBukberOpen(String param) {
//        loadingAll.setVisibility(View.VISIBLE);
//        apiInterface = apiClient.getClientAPI().create(APIInterface.class);
//        Call<List<JadwalModel>> calling = apiInterface.getJadwalDetailByStatus("tersedia");
//        calling.enqueue(new Callback<List<JadwalModel>>() {
//            @Override
//            public void onResponse(Call<List<JadwalModel>> call, Response<List<JadwalModel>> response) {
//                int total = response.body().size();
//                if(total<1){
//                    infoBelumAdaJadwal.setVisibility(View.VISIBLE);
//                    loadingIcon.setVisibility(View.GONE);
//                    LoadingIconJadwal.setVisibility(View.GONE);
//                    loadingAll.setVisibility(View.GONE);
//                    infoBelumAdaJadwal.setVisibility(View.VISIBLE);
//                }else{
//                    if (response.isSuccessful()) {
//                        populateTablePeserta("");
//                        for (int i = 0; i < response.body().size(); i++) {
//                            tvKategori.setText(response.body().get(i).getKategori());
//                            tvID.setText(String.valueOf(response.body().get(i).getId()));
//                            tvMenu.setText(response.body().get(i).getLauk());
//                            tvCatatan.setText(response.body().get(i).getCatatan());
//                            tvTanggal.setText(response.body().get(i).getTanggal());
//                            tvPorsiIkwan.setText(String.valueOf(response.body().get(i).getKuota_ikhwan()));
//                            tvPorsiAkhwat.setText(String.valueOf(response.body().get(i).getKuota_akhwat()));
//                            tvStatus.setText(response.body().get(i).getStatus());
//                        }
//                    } else {
//                        Toast.makeText(Lihat_Peserta_Terdaftar.this, "Server Error UwawawawaW", Toast.LENGTH_SHORT).show();
//                        infoTampilJadwalError.setVisibility(View.VISIBLE);
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<JadwalModel>> call, Throwable t) {
//                Toast.makeText(Lihat_Peserta_Terdaftar.this, "Jadwal Bukber dan Peserta Tidak Dapat Ditampilkan", Toast.LENGTH_SHORT).show();
//                infoTampilJadwalError.setVisibility(View.VISIBLE);
//                loadingIcon.setVisibility(View.GONE);
//                LoadingIconJadwal.setVisibility(View.GONE);
//                koneksiError.setVisibility(View.VISIBLE);
//                loadingAll.setVisibility(View.GONE);
//            }
//        });
//    }
//
//    private void populateTablePeserta(final String statRefresh){
//        String ids= tvID.getText().toString();
//        String statJadwal=String.valueOf(LoadingIconJadwal.getVisibility());
//        if(ids.equals("-")||ids.equals("")||ids.equals(".")){
//            Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                public void run() {
//                    populateTablePeserta("");
//                    LoadingIconJadwal.setVisibility(View.GONE);
//                }
//            }, 2000);   //2 seconds
//
//            if(statJadwal.equals("VISIBLE")){
//                LoadingIconJadwal.setVisibility(View.GONE);
//            }
//        }else {
//            if (statRefresh.equals("rl")) {
//                refreshLayout.setRefreshing(true);
//            }
//
//            APIInterface apiInterface = apiClient.getClientAPI().create(APIInterface.class);
//
//            Call<List<LihatPesertaByPeserta>> call = apiInterface.getPesertabyPeserta(Integer.valueOf(ids),"peserta");
//            call.enqueue(new Callback<List<LihatPesertaByPeserta>>() {
//
//                @Override
//                public void onResponse(Call<List<LihatPesertaByPeserta>> call, Response<List<LihatPesertaByPeserta>> response) {
//                    loadingIcon.setVisibility(View.GONE);
//                    koneksiError.setVisibility(View.GONE);
//                    loadingAll.setVisibility(View.GONE);
////                    container.stopShimmer();
//                    dataPesertabyPeserta.clear();
//                    if (response == null) {
//                        Toast.makeText(Lihat_Peserta_Terdaftar.this, "Tidak ada Respon dari Server", Toast.LENGTH_SHORT).show();
//                    } else {
//                        try {
//                            int total = response.body().size();
//                            int x = 0;
//                            for (int a = 0; a < total; a++) {
//                                x++;
//                                LihatPesertaByPeserta model = new LihatPesertaByPeserta(
//                                        response.body().get(a).getNama_peserta(),
//                                        response.body().get(a).getUsername(),
//                                        response.body().get(a).getJenis_kelamin(),
//                                        response.body().get(a).getPassword(),
//                                        response.body().get(a).getStatus_ambil(),
//                                        response.body().get(a).getDeparture()
//                                );
//                                dataPesertabyPeserta.add(model);
//                            }
//                            adapter = new AdapterPesertabyPeserta(Lihat_Peserta_Terdaftar.this, R.layout.list_peserta, dataPesertabyPeserta);
//                            listView.setAdapter(adapter);
//                            if (adapter.getCount() < 1) {
////                                container.stopShimmer();
//                                pesertaError.setVisibility(View.GONE);
//                                loadingIcon.setVisibility(View.GONE);
//                                pesertaKosong.setVisibility(View.VISIBLE);
//                                infoBelumAdaJadwal.setVisibility(View.VISIBLE);
//                                Toast.makeText(Lihat_Peserta_Terdaftar.this, "Peserta Bukber Kosong", Toast.LENGTH_SHORT).show();
//                            } else {
////                                container.stopShimmer();
//                                infoBelumAdaJadwal.setVisibility(View.GONE);
//                                containerAll.setVisibility(View.VISIBLE);
//                                pesertaError.setVisibility(View.GONE);
//                                pesertaKosong.setVisibility(View.GONE);
//                            }
//                            Toast.makeText(Lihat_Peserta_Terdaftar.this, "Jumlah Peserta : " + String.valueOf(x), Toast.LENGTH_SHORT).show();
//                        } catch (Exception v) {
////                            container.stopShimmer();
//                            pesertaError.setVisibility(View.GONE);
//                            loadingIcon.setVisibility(View.GONE);
//                        }
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<List<LihatPesertaByPeserta>> call, Throwable t) {
//                    Toast.makeText(Lihat_Peserta_Terdaftar.this, "Data Peserta Gagal Ditampilkan", Toast.LENGTH_SHORT).show();
//                    loadingIcon.setVisibility(View.GONE);
//                    loadingAll.setVisibility(View.GONE);
//                    containerAll.setVisibility(View.GONE);
//                    pesertaError.setVisibility(View.VISIBLE);
////                    container.stopShimmer();
//                }
//            });
//
//        }
//    }
//}
