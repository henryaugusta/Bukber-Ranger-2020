package com.senjapagi.BukberRanger.API;

import com.senjapagi.BukberRanger.Model.BukberModel;
import com.senjapagi.BukberRanger.Model.EditStatusModel;
import com.senjapagi.BukberRanger.Model.JadwalModel;
import com.senjapagi.BukberRanger.Model.LihatPesertaByPeserta;
import com.senjapagi.BukberRanger.Model.Model_Ambil_Bukber;
import com.senjapagi.BukberRanger.Model.PesertaBukberModel;
import com.senjapagi.BukberRanger.Model.UpdateJadwalModel;
import com.senjapagi.BukberRanger.Model.UpdateStatusAmbil;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIInterface {

@FormUrlEncoded
    @POST("scan_peserta.php")
    Call<BukberModel> scanPeserta(
            @Field("nama") String nama,
            @Field("jurusan") String jurusan,
            @Field("token") String token
);

    @FormUrlEncoded
    @POST("edit_jadwal.php")
    Call<UpdateJadwalModel> updateJadwal
            (       @Field("id")String id,
                    @Field("kategori")String kategori,
                    @Field("tanggal")String tanggal,
                    @Field("kuota_ikhwan")String kuota_ikhwan,
                    @Field("kuota_akhwat")String kuota_akhwat,
                    @Field("lauk")String lauk,
                    @Field("catatan")String catatan
            );
    @FormUrlEncoded
    @POST("edit_status_bukber.php")
    Call<EditStatusModel> updateStatusBukber
            (       @Field("id")String id,
                    @Field("status")String status
            );
    @FormUrlEncoded
    @POST("update_status_ambil.php")
    Call<List<UpdateStatusAmbil>> updateStatusAmbil
            (       @Field("token")String token,
                    @Field("id")String id
            );
@FormUrlEncoded
    @POST("insert_jadwal.php")
    Call<JadwalModel> insertJadwal(
            @Field("kategori")String kategori,
            @Field("tanggal")String tanggal,
            @Field("kuota_ikhwan")int kuota_ikhwan,
            @Field("kuota_akhwat")int kuota_akhwat,
            @Field("lauk")String lauk,
            @Field("catatan")String catatan,
            @Field("status")String status
);
@FormUrlEncoded
@POST("insert_peserta.php")
Call<PesertaBukberModel> insertPeserta(
        @Field("nama")String nama,
        @Field("jenis_kelamin")String jenis_kelamin,
        @Field("usernameps")String username,
        @Field("passwordps")String password,
        @Field("id_acara")String id_acara,
        @Field("tanggal")String tanggal,
        @Field("token")String token,
        @Field("entitas")String entitas,
        @Field("kontak")String kontak
);

    @GET("get_jadwal_bukber.php")
    Call<List<JadwalModel>> getJadwal();

    @GET("get_jadwal_by_status.php")
    Call<List<JadwalModel>> getJadwalDetailByStatus(@Query("status") String status);

    @GET("get_peserta_by_peserta.php")
    Call<List<LihatPesertaByPeserta>> getPesertabyPeserta(@Query("id_bukber") int id_bukber,
                                                          @Query("departure") String departure);

    @GET("ambil_bukber.php")
    Call<List<Model_Ambil_Bukber>> cekLogin(@Query("id_bukber") int id_bukber,
                                            @Query("username") String username,
                                            @Query("password") String password,
                                            @Query("token") String token);
    @GET("get_jadwal_by_id.php")
    Call<List<JadwalModel>> getJadwalDetailByID(@Query("id_bukber") String id_bukber);

}
