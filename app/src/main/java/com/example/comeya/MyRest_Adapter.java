package com.example.comeya;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.comeya.utils.RestData;

import java.util.ArrayList;

public class MyRest_Adapter extends RecyclerView.Adapter<MyRest_Adapter.HolderMyrest> {
    Context context;
    ArrayList<restView> lista_myrest;
    MyRest_Adapter(Context context){
        this.context=context;
        lista_myrest =new ArrayList<>();
    }
    void add(restView myrest){
        lista_myrest.add(myrest);
        notifyItemInserted(lista_myrest.indexOf(myrest));
    }

    @NonNull
    @Override
    public HolderMyrest onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view   =LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_rest,parent,false);
        HolderMyrest  holderMyrest=new HolderMyrest(view);
        return holderMyrest;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderMyrest holder, int position) {
        holder.titleRest.setText(lista_myrest.get(position).getTitleRest());
        holder.direccion.setText(lista_myrest.get(position).getDireccion());
        holder.telefono.setText(lista_myrest.get(position).getTelefono());
        restView it=lista_myrest.get(position);
        holder.idrest=it.getId_rest();
        Glide.with(context).load(it.getFotorest()).centerCrop().into(holder.fotorest);

        //set events
        holder.setOnclickCardview();
    }

    @Override
    public int getItemCount() {
        return lista_myrest.size();
    }

    public class HolderMyrest extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView titleRest, direccion, telefono;
        CardView viewrest;
        ImageView fotorest;
        String idrest;
        public HolderMyrest(@NonNull View itemView) {
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
                    Intent intent=new Intent(context,administrar_rest.class);
                    intent.putExtra("headerCode",idrest);
                    RestData.ID_AUX_REST=idrest;
                    Toast.makeText(context, RestData.ID_AUX_REST, Toast.LENGTH_SHORT).show();
                    context.startActivity(intent);
                    break;
            }
        }
    }
}
