package com.example.comeya;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class confirmarpedido_adapter extends RecyclerView.Adapter<confirmarpedido_adapter.ViewHolder> {


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
        //variable context para acceder a otra activity
        Context context;
        private TextView titulo, cantidad, total;
        private CardView confirmar_pedido;

        public ViewHolder(View itemView){
            super(itemView);
            //acceder a activity
            context=itemView.getContext();
            titulo =(TextView)itemView.findViewById(R.id.confirmar_titulo);
            cantidad =(TextView)itemView.findViewById(R.id.confirmar_cantidad);
            total =(TextView)itemView.findViewById(R.id.confirmar_total);
            confirmar_pedido =(CardView) itemView.findViewById(R.id.listview_confirmarPed);
            confirmar_pedido.setOnClickListener(this);
        }
        public void onClick(View v){
            showPopMenu(v);
        }
        private void showPopMenu(View view){
            PopupMenu popupMenu=new PopupMenu(view.getContext(), view);
            popupMenu.inflate(R.menu.option_gestionarmenu);
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.show();
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()){
                case R.id.delete_menu:
                    Toast.makeText(context, "eliminaste este menu :( ", Toast.LENGTH_LONG).show();
                    return true;
            }
            return false;
        }
    }
    public List<confirmarpedidoView> listaconfirmarp;

    public confirmarpedido_adapter(List<confirmarpedidoView>listaconfirmar){
        this.listaconfirmarp =listaconfirmar;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.lisview_confirmarpedido,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.titulo.setText(listaconfirmarp.get(position).getTitulo());
        holder.cantidad.setText(listaconfirmarp.get(position).getCantidad());
        holder.total.setText(listaconfirmarp.get(position).getTotal());
        //eventos

    }
    @Override
    public int getItemCount() {
        return listaconfirmarp.size();
    }
}
