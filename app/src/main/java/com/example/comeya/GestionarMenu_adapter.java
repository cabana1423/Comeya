package com.example.comeya;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.core.widget.PopupMenuCompat;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class GestionarMenu_adapter extends RecyclerView.Adapter<GestionarMenu_adapter.ViewHolder> {


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
        //variable context para acceder a otra activity
        Context context;
        private TextView titleprduc, precio, descripcion;
        private ImageView fotopruct;
        private CardView pedido;

        public ViewHolder(View itemView){
            super(itemView);
            //acceder a activity
            context=itemView.getContext();
            titleprduc =(TextView)itemView.findViewById(R.id.Produc_titulo);
            precio =(TextView)itemView.findViewById(R.id.produc_precio);
            descripcion =(TextView)itemView.findViewById(R.id.produc_descripcion);
            fotopruct =(ImageView) itemView.findViewById(R.id.produc_img);
            pedido =(CardView) itemView.findViewById(R.id.groupviewpedidoproducto);
            pedido.setOnClickListener(this);
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
                    Toast.makeText(context, "eliminaste este menu :(", Toast.LENGTH_LONG).show();
                    return true;
            }
            return false;
        }
    }
    public List<PedidoView> listaProduc;

    public GestionarMenu_adapter(List<PedidoView>listaPedido){
        this.listaProduc =listaPedido;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_pedido,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.titleprduc.setText(listaProduc.get(position).getTitleproducto());
        holder.precio.setText(listaProduc.get(position).getPrecio());
        holder.descripcion.setText(listaProduc.get(position).getDescripcion());
        holder.fotopruct.setImageResource(listaProduc.get(position).getFotoproducto());
        //eventos

    }
    @Override
    public int getItemCount() {
        return listaProduc.size();
    }
}
