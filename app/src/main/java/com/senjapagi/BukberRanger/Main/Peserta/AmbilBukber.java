package com.senjapagi.BukberRanger.Main.Peserta;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.senjapagi.BukberRanger.Model.JadwalModel;
import com.senjapagi.BukberRanger.Model.Model_Ambil_Bukber;
import com.senjapagi.BukberRanger.API.APIInterface;
import com.senjapagi.BukberRanger.API.apiClient;
import com.senjapagi.qrtomysql.R;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.UnsupportedEncodingException;
import java.util.List;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AmbilBukber extends AppCompatActivity {
    Button btnGenerateQR;
    ImageView showQRContainer;
    AVLoadingIndicatorView load;
    TextView replacement , tpJK,tpIDPeserta;
    APIInterface apiInterface;
    ScrollView scrollUtama;

    EditText username,password;

    LinearLayout loadingJadwal,koneksiError,jadwalKosong,loadingPeserta;
    Button refreshJadwal,refreshJadwal2;

    TextView tvID, tvNamaBukber, tvTanggalBukber,tvStatusAmbil;
    String statusJadwal="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambil_bukber);
//      ScrollView ScrollUtamax = (ScrollView) findViewById(R.id.scrollView2);
        scrollUtama=findViewById(R.id.scrollView2);
        load = findViewById(R.id.aviLoad);
        showQRContainer = findViewById(R.id.show_qr);
        btnGenerateQR = findViewById(R.id.btn_generateQR);
        replacement = findViewById(R.id.reptext);
        tvID = findViewById(R.id.id_bukber);
        tvTanggalBukber = findViewById(R.id.tanggal_bukber);
        tvNamaBukber = findViewById(R.id.nama_bukber);
        tvStatusAmbil=findViewById(R.id.tv_status_ambil);
        loadingJadwal=findViewById(R.id.loadingJadwalBukber_am);
        loadingPeserta=findViewById(R.id.loading_peserta);
        load.setVisibility(View.INVISIBLE);

        tpIDPeserta=findViewById(R.id.id_username);
        tpJK=findViewById(R.id.id_jk);

        username=findViewById(R.id.etdb_username);
        password=findViewById(R.id.etdb_password);

        koneksiError=findViewById(R.id.koneksiError);
        refreshJadwal=findViewById(R.id.btn_refresh_jadwal);
        refreshJadwal2=findViewById(R.id.btn_refresh2);
        jadwalKosong=findViewById(R.id.belumAdaJadwal);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        getJadwalBukberOpen("");

        refreshJadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getJadwalBukberOpen("");
            }
        });

        btnGenerateQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statusJadwal.equals(("0"))){
                    Toast.makeText(AmbilBukber.this, "Kuota Bukber Belum Tersedia", Toast.LENGTH_SHORT).show();
                }else{
                    checkDB();
                }

            }
        });

        refreshJadwal2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getJadwalBukberOpen("");
            }
        });
    }
    private void checkDB(){
        String username_check = username.getText().toString();
        String password_check = password.getText().toString();
        replacement.setText("QR Anda Akan Ditampilkan Disini");

        if(username_check.equals("")){
            username.setError("Username Masih Kosong");
        }if(password_check.equals("")){
            password.setError("Password Masih Kosong");
        }else {
            loadingPeserta.setVisibility(View.VISIBLE);

            int id_bukber = Integer.valueOf(tvID.getText().toString());
            String tokenInpu = MD5("Masjid Syamsul Ulum" + username_check+id_bukber);
            String tokenInput = tokenInpu+"%"+id_bukber;
            APIInterface apiInterface = apiClient.getClientAPI().create(APIInterface.class);
            Call<List<Model_Ambil_Bukber>> call = apiInterface.cekLogin(id_bukber, username_check, password_check, tokenInput);
            call.enqueue(new Callback<List<Model_Ambil_Bukber>>() {
                @Override
                public void onResponse(Call<List<Model_Ambil_Bukber>> call, Response<List<Model_Ambil_Bukber>> response) {
                    loadingPeserta.setVisibility(View.GONE);
                    int total = response.body().size();
                    if (total < 1) {
                        Toast.makeText(AmbilBukber.this, "Username atau Password Anda Salah", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AmbilBukber.this, "Data Peserta Ditemukan !!!", Toast.LENGTH_SHORT).show();
                        String username = response.body().get(0).getUsername();
                        String nama = response.body().get(0).getNama();
                        String token = response.body().get(0).getToken();
                        String jenis_kelamin = response.body().get(0).getJenis_kelamin();
                        String status_ambil = response.body().get(0).getStatus_ambil();

                        tvStatusAmbil.setText("Status : "+ status_ambil);
                        tpJK.setText(jenis_kelamin);
                        tpIDPeserta.setText(nama);

                        if(status_ambil.equals("Sudah Diambil")){
                            replacement.setText("Anda Sudah Mengambil Makanan Berat!!!");
                            Toast.makeText(AmbilBukber.this, "Anda Sudah Mengambil Bukber, QR Tidak Ditampilkan", Toast.LENGTH_LONG).show();


                        }else{
                            generateQR(token, nama, jenis_kelamin);
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<Model_Ambil_Bukber>> call, Throwable t) {
                    loadingPeserta.setVisibility(View.GONE);
                    Toast.makeText(AmbilBukber.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(AmbilBukber.this, "Tidak Dapat Terhubung Dengan Server", Toast.LENGTH_SHORT).show();
                }

            });


        }
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

    private void getJadwalBukberOpen(String param) {
        jadwalKosong.setVisibility(View.GONE);
        loadingJadwal.setVisibility(View.VISIBLE);
        apiInterface = apiClient.getClientAPI().create(APIInterface.class);
        Call<List<JadwalModel>> calling = apiInterface.getJadwalDetailByStatus("tersedia");
        calling.enqueue(new Callback<List<JadwalModel>>() {
            @Override
            public void onResponse(Call<List<JadwalModel>> call, Response<List<JadwalModel>> response) {
                load.setVisibility(View.GONE);
                loadingJadwal.setVisibility(View.GONE);
                koneksiError.setVisibility(View.GONE);
                int total = response.body().size();
                if (total < 1) {
                    jadwalKosong.setVisibility(View.VISIBLE);
                    Toast.makeText(AmbilBukber.this, "Kuota Bukber Belum Tersedia", Toast.LENGTH_SHORT).show();
                } else {
                        scrollUtama.setVisibility(View.VISIBLE);
                        statusJadwal="1";
                        for (int i = 0; i < response.body().size(); i++) {
                            tvNamaBukber.setText(response.body().get(i).getKategori());
                            tvID.setText(String.valueOf(response.body().get(i).getId()));
                            tvTanggalBukber.setText(response.body().get(i).getTanggal());
                        }
                }
            }

            @Override
            public void onFailure(Call<List<JadwalModel>> call, Throwable t) {
                Toast.makeText(AmbilBukber.this, "Tidak Dapat Terhubung Dengan Server", Toast.LENGTH_SHORT).show();
                Toast.makeText(AmbilBukber.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                statusJadwal="1";
                koneksiError.setVisibility(View.VISIBLE);
                load.setVisibility(View.GONE);
                loadingJadwal.setVisibility(View.GONE);
            }

        });
    }


    private void generateQR(String token,String nama,String jk) {
        final QRGEncoder qre = new QRGEncoder(token, null, QRGContents.Type.TEXT, 500);
        try {
            Handler pend = new Handler();
            Bitmap qrbm = qre.getBitmap();
            replacement.setVisibility(View.INVISIBLE);
            tpJK.setText(jk);
            tpIDPeserta.setText(nama);
            showQRContainer.setImageBitmap(qrbm);
        } catch (Exception t) {
            String error = t.toString();
            Toast.makeText(AmbilBukber.this, error, Toast.LENGTH_SHORT).show();
        }

    }
}




