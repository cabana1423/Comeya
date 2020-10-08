package com.example.comeya;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.comeya.utils.MenuData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class Rest_adaptor extends RecyclerView.Adapter<Rest_adaptor.Holderrest>{
    Context context;
    private ArrayList<restView> lista_rest;
    private ArrayList<restView> originaList;
    Rest_adaptor(Context context){
        this.context=context;
        lista_rest =new ArrayList<>();
        originaList =new ArrayList<>();
    }
    void add(restView rest){
        lista_rest.add(rest);
        notifyItemInserted(lista_rest.indexOf(rest));
        originaList.addAll(lista_rest);
    }

    @NonNull
    @Override
    public Holderrest onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view   =LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_rest,parent,false);
        Holderrest holderrest=new Holderrest(view);
        return holderrest;
    }

    @Override
    public void onBindViewHolder(@NonNull Holderrest holder, int position) {
        holder.titleRest.setText(lista_rest.get(position).getTitleRest());
        holder.direccion.setText(lista_rest.get(position).getDireccion());
        holder.telefono.setText(lista_rest.get(position).getTelefono());
        restView it=lista_rest.get(position);
        holder.lat=it.getLat();
        holder.lon=it.getLon();
        holder.id_rest=it.getId_rest();
        Glide.with(context).load(it.getFotorest()).centerCrop().into(holder.fotorest);
        holder.setOnclickCardview();
    }

    @Override
    public int getItemCount() {
        return lista_rest.size();
    }

    public void filter(final String strSearch) {
        if(strSearch.length()==0){
            lista_rest.clear();
            lista_rest.addAll(originaList);
        }
        else{
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
                lista_rest.clear();
                List<restView> collect=originaList.stream()
                        .filter(i->i.getTitleRest().toLowerCase().contains(strSearch))
                        .collect(Collectors.toList());
                lista_rest.addAll(collect);
            }
            else{
                lista_rest.clear();
                for(restView i : originaList){
                    if(i.getTitleRest().toLowerCase().contains(strSearch)){
                        lista_rest.add(i);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    public class Holderrest extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView titleRest, direccion, telefono;
        CardView viewrest;
        ImageView fotorest;
        String lat, lon,sDate="",id_rest;
        public Holderrest(@NonNull View itemView) {
            super(itemView);
            titleRest=itemView.findViewById(R.id.Restviewtitulo);
            direccion=itemView.findViewById(R.id.Resyviewdireccion);
            telefono=itemView.findViewById(R.id.Restviewphone);
            fotorest=itemView.findViewById(R.id.Restviewimg);
            viewrest=itemView.findViewById(R.id.Restgroupview);
        }

        public void setOnclickCardview() {
            viewrest.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.Restgroupview:
                    MenuData.ID_AUX_rest=id_rest;
                    Calendar c = Calendar.getInstance();
                    sDate = c.get(Calendar.YEAR) + "-"
                            + c.get(Calendar.MONTH) + "-" + c.get(Calendar.DAY_OF_MONTH)
                            + " at " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE)+ ":" + c.get(Calendar.SECOND);
                    String sDate2 = sDate.replaceAll("\\s","");
                    MenuData.TOKER_ORDER=sDate2;
                    Intent intent=new Intent(context,realizar_pedido.class);
                    context.startActivity(intent);
                    break;
            }
        }
    }
}


