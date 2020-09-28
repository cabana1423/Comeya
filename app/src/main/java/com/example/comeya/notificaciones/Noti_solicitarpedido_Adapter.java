package com.example.comeya.notificaciones;
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
import com.example.comeya.R;
import com.example.comeya.utils.FacData;

import java.util.ArrayList;

public class Noti_solicitarpedido_Adapter extends RecyclerView.Adapter<Noti_solicitarpedido_Adapter.Holdersolicitud_pedido>{
    Context context;
    ArrayList<Noti_solicitarpedido_View> lista_SolPedido;
    Noti_solicitarpedido_Adapter(Context context){
        this.context=context;
        lista_SolPedido =new ArrayList<>();
    }
    void add(Noti_solicitarpedido_View sol){
        lista_SolPedido.add(sol);
        notifyItemInserted(lista_SolPedido.indexOf(sol));
    }

    @NonNull
    @Override
    public Holdersolicitud_pedido onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view   =LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_notisolicitud,parent,false);
        Holdersolicitud_pedido holder_sol=new Holdersolicitud_pedido(view);
        return holder_sol;
    }

    @Override
    public void onBindViewHolder(@NonNull Holdersolicitud_pedido holder, int position) {
        holder.texto.setText(lista_SolPedido.get(position).getText());
        Noti_solicitarpedido_View it=lista_SolPedido.get(position);
        holder.id_fac=it.getId_fac();
        Glide.with(context).load(it.getImg()).centerCrop().into(holder.img);
        holder.setOnclickCardview();
    }

    @Override
    public int getItemCount() {
        return lista_SolPedido.size();
    }

    public class Holdersolicitud_pedido extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView texto;
        CardView Group_solicitud;
        ImageView img;
        String /*lat, lon,*/id_fac,toker;
        public Holdersolicitud_pedido(@NonNull View itemView) {
            super(itemView);
            texto=itemView.findViewById(R.id.Noti_solicitarpedido_text);
            img=itemView.findViewById(R.id.Noti_solicitarpedido_img);
            Group_solicitud=itemView.findViewById(R.id.Group_solicitud);
        }

        public void setOnclickCardview() {
            Group_solicitud.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.Group_solicitud:
                    FacData.ID_FAC =id_fac;
                    Intent intent=new Intent(context, Gestionar_Serv.class);
                    context.startActivity(intent);
                    break;
            }
        }
    }
}




