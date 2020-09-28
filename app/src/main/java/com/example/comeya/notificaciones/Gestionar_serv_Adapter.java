package com.example.comeya.notificaciones;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.comeya.R;
import com.example.comeya.confirmarpedidoView;
import com.example.comeya.utils.EndPoints;
import com.example.comeya.utils.MenuData;
import com.example.comeya.utils.UserDataServer;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class Gestionar_serv_Adapter extends RecyclerView.Adapter<com.example.comeya.notificaciones.Gestionar_serv_Adapter.Holderlistafinal>{
    Context context;
    ArrayList<confirmarpedidoView> lista_final;
    Gestionar_serv_Adapter(Context context){
        this.context=context;
        lista_final =new ArrayList<>();
    }
    void add(confirmarpedidoView listafinal){
        lista_final.add(listafinal);
        notifyItemInserted(lista_final.indexOf(listafinal));
    }
    @NonNull
    @Override
    public com.example.comeya.notificaciones.Gestionar_serv_Adapter.Holderlistafinal onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view   =LayoutInflater.from(parent.getContext()).inflate(R.layout.lisview_confirmarpedido,parent,false);
        com.example.comeya.notificaciones.Gestionar_serv_Adapter.Holderlistafinal holdermenu=new com.example.comeya.notificaciones.Gestionar_serv_Adapter.Holderlistafinal(view);
        return holdermenu;
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.comeya.notificaciones.Gestionar_serv_Adapter.Holderlistafinal holder, int position) {
        holder.titulo.setText(lista_final.get(position).getTitulo());
        holder.cantidad.setText(lista_final.get(position).getCantidad());
        holder.total.setText(lista_final.get(position).getTotal());
        confirmarpedidoView it=lista_final.get(position);
        holder.id_order=it.getId_order();

    }

    @Override
    public int getItemCount() {
        return lista_final.size();
    }

    class Holderlistafinal extends RecyclerView.ViewHolder {
        Context context;
        private TextView titulo, cantidad, total;
        private CardView confirmar_pedido;
        String id_order;
        public Holderlistafinal(@NonNull View itemView) {
            super(itemView);
            titulo =(TextView)itemView.findViewById(R.id.confirmar_titulo);
            cantidad =(TextView)itemView.findViewById(R.id.confirmar_cantidad);
            total =(TextView)itemView.findViewById(R.id.confirmar_total);

        }
    }

}
