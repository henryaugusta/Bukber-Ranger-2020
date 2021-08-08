package com.senjapagi.BukberRanger.API;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class apiClient {
//    public static final String BASE_URL = "http://henryaugusta-25972.portmap.host:25972//prk1441/";
    public static final String BASE_URL = "http://henryaugusta-62370.portmap.io:62370//prk1441/";
    //ganti BASE URL SESUAI HOSTING YANG DIGUNAKAN
    public static final String URL_JADWAL = "http://henryaugusta-62370.portmap.io:62370//Bukber-Ranger/show_data.php";
    public static final String URL_MANAGE_PESERTA_AKTIF = "http://henryaugusta-62370.portmap.io:62370//Bukber-Ranger/show_data_admin.php";
    public static final String URL_FEEDBACK_PANITIA = "https://docs.google.com/forms/d/e/1FAIpQLSd3VeplYIBhzmtrbgJ6dkLKQkvXASWPlV9-cpR-2alshHqsXQ/viewform?usp=sf_link";
    public static final String URL_FEEDBACK_APLIKASI = "https://docs.google.com/forms/d/e/1FAIpQLSczmLjIUuCQvnodFxsLDsNYTaQTgwMSCoKWf4EngzED0VdASw/viewform?usp=sf_link";



    private static Retrofit retrofit;

    public  static  final String URL_Jadwal =BASE_URL+"get_jadwal_by_status.php";//Jadwal Aktif
    public  static  final String URL_Peserta1 =BASE_URL+"get_jadwal_by_status.php";//Lihat Peserta Oleh Peserta
    public  static  final String URL_ListPeserta =BASE_URL+"get_peserta_by_peserta.php";//Lihat Peserta Oleh Peserta

    public static Retrofit getClientAPI(){
        if(retrofit==null){
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit=new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
