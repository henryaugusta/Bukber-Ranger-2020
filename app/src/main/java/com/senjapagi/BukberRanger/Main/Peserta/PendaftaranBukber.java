package com.senjapagi.BukberRanger.Main.Peserta;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.github.ybq.android.spinkit.SpinKitView;
import com.senjapagi.BukberRanger.Model.JadwalModel;
import com.senjapagi.BukberRanger.Model.PesertaBukberModel;
import com.senjapagi.BukberRanger.API.APIInterface;
import com.senjapagi.BukberRanger.API.apiClient;
import com.senjapagi.BukberRanger.Main.General.dashboard_main;
import com.senjapagi.qrtomysql.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendaftaranBukber extends AppCompatActivity {
    private Spinner jk;
    String idBukber;
    String jenisKelamin[] = {"Ikhwan", "Akhwat"};
    String fakultas[] = {"Pilih Fakultas","FTE","FRI","FIF","FIK","FEB","FIT","FKB"};
    APIInterface apiInterface;
    EditText etNama,etUsername,etPassword,etJurusan,etKontak;
    Button btnDaftar,btnRefresh,btnRefresh2;
    TextView tvKategori,tvTanggal,tvStatus,tvPorsiIkwan,tvPorsiAkhwat,tvMenu,tvCatatan,tvID;
    RadioGroup chooseGender;
    SpinKitView loadingIcon;

    LinearLayout loadingJadwal,jadwalKosong,kuotaHabis,containerPeserta,updateKuota,loadDaftar;
    LinearLayout koneksiError,ketentuanDaftar;
    TextView status;
    Spinner spFakultas;
    ShimmerFrameLayout sJadwal ;
    ShimmerFrameLayout sKuota,sMainContainer ;
    ScrollView scrollViewUtama;
    TextView txtTitle, txtDate,txtIkhwan, txtAkhwat, txtSisaIkhwan, txtSisaAkhwat ,txtMenu,txtCatatan;

    private RadioButton btn_gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendaftaran_bukber);
        init();
        final ArrayAdapter adapter = new ArrayAdapter(PendaftaranBukber.this, android.R.layout.simple_list_item_1, jenisKelamin);
        jk.setAdapter(adapter);
        ArrayAdapter adapter1 = new ArrayAdapter(PendaftaranBukber.this,android.R.layout.simple_spinner_item,fakultas);
        spFakultas.setAdapter(adapter1);

        getJadwal();
        startSplashTimer();





        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = etNama.getText().toString();
                String password = etPassword.getText().toString();
                String username = etUsername.getText().toString();
                int selectedGender = chooseGender.getCheckedRadioButtonId();
                btn_gender = (RadioButton) findViewById(selectedGender);
                String tanggal = txtDate.getText().toString();
                String jenisKelamin = btn_gender.getText().toString();
                String fakultasPendaftar = spFakultas.getSelectedItem().toString();
                String jurusan = etJurusan.getText().toString();
                String entitas = fakultasPendaftar+"-"+jurusan;
                String kontak = etKontak.getText().toString();

                String [] array = username.trim().split(" ");
                boolean error = true;
                if(array.length>1){
                    etUsername.setError("Username Hanya Boleh Terdiri dari 1 Kata");
                    error=true;
                }else if(nama.equals("")){
                    etNama.setError("Nama Masih Kosong");
                    error=true;
                }else if(password.equals("")){
                    etPassword.setError(("Password Masih Kosong"));
                    error=true;
                }else if(username.equals("")){
                    etUsername.setError("Username Masih Kosong");
                    error=true;
                }else{
                    scanPeserta(nama,jenisKelamin,username,password,idBukber,tanggal,entitas,kontak);
                }
            }
        });
    }


    private  void init(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        txtTitle=findViewById(R.id.textNamaJadwal);
        txtDate=findViewById(R.id.textDateSchedule);
        txtIkhwan=findViewById(R.id.jmlIkhwan);
        txtAkhwat=findViewById(R.id.jmlAkhwat);
        txtSisaIkhwan=findViewById(R.id.sisaIkhwan);
        txtSisaAkhwat=findViewById(R.id.sisaAkhwat);

        etNama=findViewById(R.id.etdb_nama_peserta);
        etUsername=findViewById(R.id.etdb_username);
        etPassword=findViewById(R.id.etdb_password);
        btnDaftar=findViewById(R.id.btndb_daftar);

        tvKategori=findViewById(R.id.tvdb_kategori_bukber);
        tvTanggal=findViewById(R.id.tvdb_tanggal_bukber);
        tvPorsiIkwan=findViewById(R.id.tvdb_porsi_ikhwan);
        tvPorsiAkhwat=findViewById(R.id.tvdb_porsi_akhwat);
        tvMenu=findViewById(R.id.tvdb_lauk);
        tvStatus=findViewById(R.id.tvdb_status_bukber);
        tvCatatan=findViewById(R.id.tvdb_catatan);
        tvID=findViewById(R.id.tvdb_id_kegiatan);

        chooseGender=findViewById(R.id.radioGender);

        scrollViewUtama=findViewById(R.id.scrollView2);
        status=findViewById(R.id.tv_status);
        jadwalKosong=findViewById(R.id.jadwal_kosong);
        kuotaHabis=findViewById(R.id.kuota_habis);
        containerPeserta=findViewById(R.id.container_peserta);
        koneksiError=findViewById(R.id.koneksiError);
        btnRefresh=findViewById(R.id.btn_refresh);
        btnRefresh2=findViewById(R.id.btn_refresh2);
        ketentuanDaftar=findViewById(R.id.ketentuanDaftar);
        spFakultas=findViewById(R.id.spinner_fakultas);
        etJurusan=findViewById(R.id.etdb_jurusan);
        etKontak=findViewById(R.id.etdb_kontak);


        sJadwal = findViewById((R.id.shimmer_view_jadwal));
        sMainContainer = findViewById((R.id.shimmer_view_mainContainer));
        sKuota = findViewById((R.id.shimmer_view_kuota));
        jk = findViewById(R.id.spinner_jk);

        txtMenu=findViewById(R.id.txtMenu);
        txtCatatan=findViewById(R.id.txtNote);
    }
    private void getJadwal(){
        sKuota.startShimmer();
        sJadwal.startShimmer();
        sMainContainer.startShimmer();
        AndroidNetworking.get(apiClient.URL_Jadwal)
                .setTag("getScheduler")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        sKuota.hideShimmer();
                        sJadwal.hideShimmer();
                        sMainContainer.hideShimmer();
                        try {
                            JSONObject data = response.getJSONObject(0);
                            txtDate.setText(data.getString("tanggal"));
                            txtTitle.setText(data.getString("kategori"));
                            txtIkhwan.setText(data.getString("jumlah_ikhwan_terdaftar"));
                            txtAkhwat.setText(data.getString("jumlah_akhwat_terdaftar"));
                            txtSisaIkhwan.setText(data.getString("sisa_ikhwan"));
                            txtSisaAkhwat.setText(data.getString("sisa_akhwat"));
                            txtCatatan.setText(data.getString("catatan"));
                            txtMenu.setText(data.getString("lauk"));
                            idBukber=data.getString("id");

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Gagal Euy", Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        sKuota.hideShimmer();
                        sJadwal.hideShimmer();
                        sMainContainer.hideShimmer();
                        Toast.makeText(getApplicationContext(), "Gagal Request Data", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(), error.getErrorBody(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void startSplashTimer() {
        final String connectionStat="success";
        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        if (connectionStat.equals("failure")) {
                            //DO NOTHING
                        } else {
                            int kuotaIkhwan = Integer.valueOf(txtSisaIkhwan.getText().toString());
                            int kuotaAkhwat = Integer.valueOf(txtSisaAkhwat.getText().toString());
                            if (kuotaIkhwan + kuotaAkhwat <= 0) {
                                //DO NOTHING
                            } else {
                                startSplashTimer();
                                getJadwal();
                            }
                        }
                    }
                }, 10000);
    }


    private void scanPeserta(final String nama, final String jk, final String username, String password, String id_bukber, String tanggal_bukber,String entitas,String kontak) {
        findViewById(R.id.loadingView).setVisibility(View.VISIBLE);
        btnDaftar.setEnabled(false);
        findViewById(R.id.loadingView).setVisibility(View.VISIBLE);
        String toke = MD5("Masjid Syamsul Ulum"+username+id_bukber);
        final String token = toke+"%"+id_bukber;
        apiInterface = apiClient.getClientAPI().create(APIInterface.class);
        Call <PesertaBukberModel> call = apiInterface.insertPeserta(nama, jk, username, password, id_bukber, tanggal_bukber,token,entitas,kontak);
        call.enqueue(new Callback<PesertaBukberModel>() {
            @Override
            public void onResponse(Call<PesertaBukberModel> call, Response<PesertaBukberModel> response) {
                findViewById(R.id.loadingView).setVisibility(View.INVISIBLE);
                String status = response.body().getSuccess();
                String message = response.body().getMessage();
                if(response.isSuccessful()){
                    if(status.equals("true")&&message.equals("Successfully")){
                        Intent intent = new Intent(PendaftaranBukber.this, PendaftaranBerhasil.class);
                        intent.putExtra("NAMA_PESERTA",nama);
                        intent.putExtra("USERNAME",username);
                        intent.putExtra("TOKEN",token);
                        intent.putExtra("JENIS_KELAMIN",jk);
                        Toast.makeText(PendaftaranBukber.this, "Pendaftaran Berhasil", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }else if(status.equals("false")&&message.equals("Failure")){
                        btnDaftar.setEnabled(true);
                        etUsername.setError("Username Sudah Digunakan User Lain");
                        Toast.makeText(PendaftaranBukber.this, "Username Sudah Digunakan,\nSilakan Ganti Username Anda", Toast.LENGTH_SHORT).show();
                    }else if(status.equals("false")&&message.equals("Kuota Habis")){
                        showWarning();
                        btnDaftar.setEnabled(true);
                        Toast.makeText(PendaftaranBukber.this, "Pendaftaran Gagal , Kuota Sudah Habis", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<PesertaBukberModel> call, Throwable t) {
                btnDaftar.setEnabled(true);
                Toast.makeText(PendaftaranBukber.this, "Gagal Terhubung Dengan Server", Toast.LENGTH_SHORT).show();
                Toast.makeText(PendaftaranBukber.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                scrollViewUtama.setVisibility(View.GONE);
                findViewById(R.id.loadingView).setVisibility(View.VISIBLE);
            }
        });
    }
    public String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes("UTF-8"));
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {

        } catch(UnsupportedEncodingException ex){

        }
        return null;

    }
    public void onBackPressed() {
        Intent setIntent = new Intent(PendaftaranBukber.this, dashboard_main.class);
        startActivity(setIntent);
        finish();
    }

    private void showWarning() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getApplicationContext());
        builder1.setMessage("Afwan , Kuota Pendaftaran Berbuka Sudah Habis");

        builder1.setPositiveButton(
                "Kembali Ke Menu Utama",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                       Intent intent = new Intent(PendaftaranBukber.this,dashboard_main.class);
                       startActivity(intent);
                    }
                });


        AlertDialog alert11 = builder1.create();
        alert11.show();

    }
}
