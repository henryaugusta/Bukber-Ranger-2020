package com.senjapagi.BukberRanger.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.senjapagi.BukberRanger.Model.Peserta;
import com.senjapagi.qrtomysql.R;

import java.util.ArrayList;

public class AdapterPeserta extends RecyclerView.Adapter<AdapterPeserta.HolderPeserta>{
    Context mContext;
    ArrayList<Peserta> dataPeserta;

    public AdapterPeserta(ArrayList<Peserta> peserta) {
        this.dataPeserta = peserta;
    }

    @NonNull
    @Override
    public HolderPeserta onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_peserta_fan,parent,false);
        return new HolderPeserta(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_peserta_fan,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull HolderPeserta holder, int position) {
        Peserta pesertaList = dataPeserta.get(position);
        holder.tv_Peserta.setText(pesertaList.getNama());
        holder.tv_jK.setText(pesertaList.getJenisKelamin());

        holder.rlPesertaList.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_transition_animation));
        holder.tv_Peserta.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_transition_animation));
    }

    public void filterList(ArrayList<Peserta> filteredList){
        dataPeserta = filteredList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return (dataPeserta==null)?0:dataPeserta.size();
    }

    public class HolderPeserta extends RecyclerView.ViewHolder{
        TextView tv_Peserta,tv_jK;
             RelativeLayout rlPesertaList;

        public HolderPeserta(@NonNull View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            tv_Peserta=itemView.findViewById(R.id.tv_namaPeserta);
            tv_jK=itemView.findViewById(R.id.tv_jenisKelamin);
            rlPesertaList=itemView.findViewById(R.id.rlPesertaList);


        }
    }
}
