package com.senjapagi.BukberRanger.Adapter;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.senjapagi.BukberRanger.Model.JadwalModel;
import com.senjapagi.qrtomysql.R;

import java.util.ArrayList;

public class ListArrayAdapter extends ArrayAdapter<JadwalModel> {

    private ArrayList<JadwalModel> list;
    private LayoutInflater inflater;
    private int res;

    public ListArrayAdapter(Context context, int resource, ArrayList<JadwalModel> list) {
        super(context, resource, list);
        this.list = list;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.res = resource;
    }

    public void pasklik(TextView tv){

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        MyHolder holder = null;
        //Dah gih coba ulang
        if (convertView == null) {
            convertView = inflater.inflate(res, parent, false);
            holder = new MyHolder();
            holder.Kategori = (TextView) convertView.findViewById(R.id.tv_kategori);
            holder.Tanggal = (TextView) convertView.findViewById(R.id.tv_tanggal_bukber);
            holder.KuotaIkhwan = (TextView) convertView.findViewById(R.id.tv_porsi_ikhwan);
            holder.KuotaAkhwat = (TextView) convertView.findViewById(R.id.tv_porsi_akhwat);
            holder.Menu = (TextView) convertView.findViewById(R.id.tv_menu);
            holder.Catatan = (TextView) convertView.findViewById(R.id.tv_catatan);
            holder.Status = (TextView) convertView.findViewById(R.id.tv_status);
            holder.TagColor = ( LinearLayout) convertView.findViewById(R.id.colorTag);
            holder.itemView=( CardView) convertView.findViewById(R.id.card_item);
            holder.id=(TextView) convertView.findViewById(R.id.tv_id);
            convertView.setTag(holder);

        } else {
            holder = (MyHolder) convertView.getTag();
        }

        holder.Kategori.setText(list.get(position).getKategori());
        holder.Tanggal.setText(list.get(position).getTanggal());
        holder.KuotaIkhwan.setText(String.valueOf(list.get(position).getKuota_ikhwan()));
        holder.KuotaAkhwat.setText(String.valueOf(list.get(position).getKuota_akhwat()));
        holder.Menu.setText(list.get(position).getLauk());
        holder.id.setText(list.get(position).getId());
        String catatan = list.get(position).getCatatan();
        if(catatan.equals("")||catatan.equals("-")){
            holder.Catatan.setText("Tidak Ada Catatan");
        }else{
            holder.Catatan.setText(list.get(position).getCatatan());
        }

        holder.Status.setText(list.get(position).getStatus());
        String ambil = list.get(position).getStatus();
        if(list.get(position).getStatus().equals("selesai")){
            holder.TagColor.setBackgroundColor(Color.parseColor("#f67575"));
        }else if(list.get(position).getStatus().equals("tersedia")){
            holder.TagColor.setBackgroundColor(Color.parseColor("#1eb2a6"));
        }

        final TextView tvid=holder.id;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pasklik(tvid);
            }
        });
        return convertView;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public void remove(JadwalModel object) {
        super.remove(object);
    }

    @Override
    public void clear() {
        super.clear();
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    static class MyHolder {
        View semua;
        TextView Kategori;
        TextView Tanggal;
        TextView KuotaIkhwan;
        TextView KuotaAkhwat;
        TextView Menu;
        TextView Catatan;
        TextView id;
        TextView Status;
        LinearLayout TagColor;
        CardView itemView;

    }
}
