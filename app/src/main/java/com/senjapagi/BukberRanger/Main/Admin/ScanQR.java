package com.senjapagi.BukberRanger.Main.Admin;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;
import com.senjapagi.BukberRanger.Model.JadwalModel;
import com.senjapagi.BukberRanger.Model.UpdateStatusAmbil;
import com.senjapagi.BukberRanger.API.APIInterface;
import com.senjapagi.BukberRanger.API.apiClient;
import com.senjapagi.qrtomysql.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ScanQR extends AppCompatActivity {
    private CodeScanner mCodeScanner;
    APIInterface apiInterface;
    Button tesQR;
    ArrayList<String> peserta = new ArrayList<String>();
    TextView tvID,tvNamaKegiatan,scanResultName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);
        tesQR=findViewById(R.id.tesQR);
        tvID=findViewById(R.id.id_bukber);
        tvNamaKegiatan=findViewById(R.id.nama_bukber);
        scanResultName=findViewById(R.id.scan_result_name);

        final CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.startPreview();


        getJadwalBukberOpen();

        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                ScanQR.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            MediaPlayer.create(ScanQR.this, R.raw.beep).start();
                            String token=String.valueOf(result);
                            final String metdat = token;
                            //Java Regex for seperating QR Scanned Data ( with "%" )
                            final List<String> regexID = Arrays.asList((metdat.split("%")));
                            String id_bukber = regexID.get(1);
                            String pairingToken=tvID.getText().toString();
                            if(id_bukber.equals(pairingToken)){
                                scanPeserta(token,id_bukber);
                            }else{
                                Toast.makeText(ScanQR.this, "QR Code Sudah Tidak Berlaku", Toast.LENGTH_SHORT).show();
                            }
//                    scanPeserta(token,id_bukber);
                        }catch (Exception x){
                            Toast.makeText(ScanQR.this, "ERROR RESULT :\n"+x.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
    }
    private void scanPeserta(String token,String idBukber){
        apiInterface = apiClient.getClientAPI().create(APIInterface.class);
        Call<List<UpdateStatusAmbil>> call = apiInterface.updateStatusAmbil(token,idBukber);
        call.enqueue(new Callback<List<UpdateStatusAmbil>>() {
            @Override
            public void onResponse(Call<List<UpdateStatusAmbil>> call, Response<List<UpdateStatusAmbil>> response) {
//                Toast.makeText(ScanQR.this, response.body().get(0).getStatus_ambil(), Toast.LENGTH_SHORT).show();
                String statusAmbil = response.body().get(0).getStatus_ambil();
                String namaPeserta = response.body().get(0).getNama_peserta();
                String jk = response.body().get(0).getJenis_kelamin();
                String message = response.body().get(0).getMessage();
                String x = scanResultName.getText().toString();
                    if(message.equals("Peserta Sudah Mengambil Bukber")) {
                        showFailed1();
                    }else{
                        showSucces();
                        int arSize = peserta.size()+1;
                        String dataFix = namaPeserta + "-" + jk;
                        peserta.add(dataFix);
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy", Locale.getDefault());
                        String currentDateandTime = sdf.format(new Date());
                        String a = scanResultName.getText().toString();
                        scanResultName.setText(a+arSize+". "+dataFix+"-"+currentDateandTime+"\n");
                        }
            }

            @Override
            public void onFailure(Call<List<UpdateStatusAmbil>> call, Throwable t) {
                showFailed2();
            }
        });
    }

    private void showFailed1() {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Upss")
                .setContentText("QR Code Peserta Tidak Berlaku atau Sudah Digunakan")
                .show();
    }
    private void showFailed2() {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Upss")
                .setContentText("Gagal Terhubung Dengan Server, Periksa Koneksi Internet Anda")
                .show();
    }

    private void getJadwalBukberOpen() {
        apiInterface = apiClient.getClientAPI().create(APIInterface.class);
        Call<List<JadwalModel>> calling = apiInterface.getJadwalDetailByStatus("tersedia");
        calling.enqueue(new Callback<List<JadwalModel>>() {
            @Override
            public void onResponse(Call<List<JadwalModel>> call, Response<List<JadwalModel>> response) {
                int total = response.body().size();
                if (total < 1) {
                } else {
                    Toast.makeText(ScanQR.this, "Berhasil Menampilkan Jadwal", Toast.LENGTH_SHORT).show();
                    for (int i = 0; i < response.body().size(); i++) {
                        tvID.setText(response.body().get(i).getId());
                        tvNamaKegiatan.setText(response.body().get(i).getKategori());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<JadwalModel>> call, Throwable t) {
                Toast.makeText(ScanQR.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void showSucces() {
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Alhamdulillah")
                .setContentText("Status QR Peserta Berhasil Diubah")
                .show();
    }

}
