package com.example.comeya;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Rest_adaptor extends RecyclerView.Adapter<Rest_adaptor.Holderrest>{
    Context context;
    ArrayList<restView> lista_rest;
    Rest_adaptor(Context context){
        this.context=context;
        lista_rest =new ArrayList<>();
    }
    void add(restView rest){
        lista_rest.add(rest);
        notifyItemInserted(lista_rest.indexOf(rest));
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
        Glide.with(context).load(it.getFotorest()).centerCrop().into(holder.fotorest);
        holder.setOnclickCardview();
    }

    @Override
    public int getItemCount() {
        return lista_rest.size();
    }

    public class Holderrest extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView titleRest, direccion, telefono;
        CardView viewrest;
        ImageView fotorest;
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
                    Intent intent=new Intent(context,realizar_pedido.class);
                    context.startActivity(intent);
                    break;
            }
        }
    }
}


