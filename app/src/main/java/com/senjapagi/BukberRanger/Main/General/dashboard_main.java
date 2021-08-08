package com.senjapagi.BukberRanger.Main.General;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.github.msarhan.ummalqura.calendar.UmmalquraCalendar;
import com.google.android.material.snackbar.Snackbar;
import com.senjapagi.BukberRanger.Main.Admin.login_admin;
import com.senjapagi.BukberRanger.Main.Peserta.AmbilBukber;
import com.senjapagi.BukberRanger.Main.Peserta.BeforeFeedback;
import com.senjapagi.BukberRanger.Main.Peserta.Lihat_Peserta_Terdaftar;
import com.senjapagi.BukberRanger.Main.Peserta.PendaftaranBukber;
import com.senjapagi.BukberRanger.Main.Tutorial;
import com.senjapagi.BukberRanger.Main.TentangAku;
import com.senjapagi.qrtomysql.R;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class dashboard_main extends AppCompatActivity {
    Button tambahBukber,lihatJadwalBukber,daftarBukber,ambilBukber,lihatPeserta, aturPeserta;
    LinearLayout btnLihatPeserta,btnDaftarBukber,btnAmbilBukber , btnTutorial , btnFeedback;
            ImageView btnAdmin;
    TextView shubuh,dhuhur,ashar,maghrib,isya,buka,masehi,lunar;
    ShimmerFrameLayout shimmerBuka,shimmerJadwal;
    String dn;
    RelativeLayout mainLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout btnAboutapp = (LinearLayout) findViewById(R.id.btn_aboutApp);

        mainLayout=findViewById(R.id.mainLayout);

        btnDaftarBukber=findViewById(R.id.btn_daftar_bukber);
        btnLihatPeserta=findViewById(R.id.btn_lihat_peserta);
        btnAmbilBukber=findViewById(R.id.btn_ambil_bukber);
        btnTutorial=findViewById(R.id.btn_tutorial);
        btnFeedback=findViewById(R.id.btn_feedback);
        btnAdmin=findViewById(R.id.btn_admin);

        shubuh=findViewById(R.id.txtTimeSubuh);
        dhuhur=findViewById(R.id.txtTimeDhuhur);
        ashar=findViewById(R.id.txtTimeAshar);
        maghrib=findViewById(R.id.txtTimeMagrib);
        isya=findViewById(R.id.txtTimeIsya);
        buka=findViewById(R.id.txtTimeBuka);
        masehi=findViewById(R.id.txtDateMasehi);
        lunar=findViewById(R.id.txtDateHijri);



       shimmerBuka = (ShimmerFrameLayout) findViewById(R.id.shimmer_view_buka);
       shimmerJadwal= (ShimmerFrameLayout) findViewById(R.id.shimmer_view_jadwal);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");//date format for APIut

        Date date = new Date();
        dn=formatter.format(date);


        UmmalquraCalendar cal = new UmmalquraCalendar();
        cal.get(Calendar.YEAR);         // 1436
        cal.get(Calendar.MONTH);        // 5 <=> Jumada al-Akhirah
        cal.get(Calendar.DAY_OF_MONTH); // 14
        lunar.setText(cal.get(Calendar.DAY_OF_MONTH)+"-"+cal.get(Calendar.MONTH)+"-"+cal.get(Calendar.YEAR));



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        getJadwal();


        btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(dashboard_main.this, dashboard_admin.class);
                startActivity(intent);
            }
        });



        btnAboutapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(dashboard_main.this, TentangAku.class);
                startActivity(intent);
            }
        });

        btnDaftarBukber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(dashboard_main.this, PendaftaranBukber.class);
                startActivity(intent);
            }
        });
        btnLihatPeserta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(dashboard_main.this, Lihat_Peserta_Terdaftar.class);
                startActivity(intent);
            }
        });
        btnAmbilBukber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(dashboard_main.this, AmbilBukber.class);
                startActivity(intent);
            }
        });
        btnTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(dashboard_main.this, Tutorial.class);
                startActivity(intent);
            }
        });
        btnFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(dashboard_main.this, BeforeFeedback.class);
                startActivity(intent);
            }
        });

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        ImageView ic_DaftarBukber = (ImageView) findViewById(R.id.iv_daftarbukber);
        Bitmap logoDaftarBukber = ((BitmapDrawable)getResources().getDrawable(R.drawable.rsz_ic_food)).getBitmap();
        ic_DaftarBukber.setImageBitmap(logoDaftarBukber);

        ImageView image = (ImageView) findViewById(R.id.iv_lihat_peserta);
        Bitmap logoLihatPeserta = ((BitmapDrawable)getResources().getDrawable(R.drawable.rsz_ic_registered)).getBitmap();
        image.setImageBitmap(logoLihatPeserta);

        ImageView ic_AmbilBukber = (ImageView) findViewById(R.id.iv_ambil_bukber);
        Bitmap logoAmbilBukber = ((BitmapDrawable)getResources().getDrawable(R.drawable.ic_qr)).getBitmap();
        ic_AmbilBukber.setImageBitmap(logoAmbilBukber);

        ImageView ic_Tutorial = (ImageView) findViewById(R.id.iv_tutorial);
        Bitmap logoTutorial = ((BitmapDrawable)getResources().getDrawable(R.drawable.rsz_ic_tutorial)).getBitmap();
        ic_Tutorial.setImageBitmap(logoTutorial);

        ImageView ic_KritikdanSaran = (ImageView) findViewById(R.id.iv_kritik_saran);
        Bitmap logoKritikSaran = ((BitmapDrawable)getResources().getDrawable(R.drawable.rsz_ic_suggest)).getBitmap();
        ic_KritikdanSaran.setImageBitmap(logoKritikSaran);

        ImageView ic_AboutApp = (ImageView) findViewById(R.id.iv_about_app);
        Bitmap logoAboutApp = ((BitmapDrawable)getResources().getDrawable(R.drawable.rsz_about_this_app)).getBitmap();
        ic_AboutApp.setImageBitmap(logoAboutApp);


        BitmapFactory.decodeResource(getResources(), R.id.iv_ambil_bukber, options);
        BitmapFactory.decodeResource(getResources(), R.id.iv_kritik_saran, options);
        BitmapFactory.decodeResource(getResources(), R.id.iv_tutorial, options);
        BitmapFactory.decodeResource(getResources(), R.id.iv_lihat_peserta, options);
        BitmapFactory.decodeResource(getResources(), R.id.iv_daftarbukber, options);
        BitmapFactory.decodeResource(getResources(), R.id.iv_about_app, options);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflaterx = getMenuInflater();
        inflaterx.inflate(R.menu.menu_admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.login_admin:
                Intent intent = new Intent(dashboard_main.this, login_admin.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }


    private void dialogFailed(){
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(dashboard_main.this, new String[]{android.Manifest.permission.CAMERA}, 50);
            Toast.makeText(this, "Permission belum diberi akses", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Permission Checked", Toast.LENGTH_SHORT).show();

        }
    }
    public void getJadwal(){
        shimmerBuka.startShimmer();
        shimmerJadwal.startShimmer();
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                . writeTimeout(12, TimeUnit.SECONDS)
                .build();

        String URL_API_SHOLAT ="https://api.banghasan.com/sholat/format/json/jadwal/kota/679/tanggal/"+dn;
        System.out.println(dn);
        AndroidNetworking.post(URL_API_SHOLAT)
                .setOkHttpClient(okHttpClient)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            shimmerBuka.stopShimmer();
                            shimmerBuka.hideShimmer();
                            shimmerJadwal.stopShimmer();
                            shimmerJadwal.hideShimmer();
                            String result = response.optJSONObject("query").getString("tanggal");
                            shubuh.setText(response.optJSONObject("jadwal").getJSONObject("data").getString("subuh"));
                            dhuhur.setText(response.optJSONObject("jadwal").getJSONObject("data").getString("dzuhur"));
                            ashar.setText(response.optJSONObject("jadwal").getJSONObject("data").getString("ashar"));
                            maghrib.setText(response.optJSONObject("jadwal").getJSONObject("data").getString("maghrib"));
                            isya.setText(response.optJSONObject("jadwal").getJSONObject("data").getString("isya"));
                            buka.setText(response.optJSONObject("jadwal").getJSONObject("data").getString("maghrib"));
                            masehi.setText(response.optJSONObject("jadwal").getJSONObject("data").getString("tanggal"));

                        }catch (Exception e){
                            e.printStackTrace();
                            SnackBarInternet();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        shimmerBuka.stopShimmer();
                        shimmerJadwal.stopShimmer();
                        anError.printStackTrace();
                        SnackBarInternet();
                        Toast.makeText(dashboard_main.this, "Gagal Terhubung Dengan Server", Toast.LENGTH_SHORT).show();

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
