package com.example.comeya;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class Pedido_adapter extends RecyclerView.Adapter<Pedido_adapter.ViewHolder>{


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //variable context para acceder a otra activity
        Context context;
        private Context ctxContext;
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
        }

        void setOnClickListeners(){
            pedido.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.groupviewpedidoproducto:
                    openVentana();
                    break;
            }
        }
        private void openVentana() {
            Ventanarealizarpedido ventanarealizarpedido=new Ventanarealizarpedido();
            ventanarealizarpedido.show(((AppCompatActivity)context).getSupportFragmentManager(),"example dialogo");
        }
    }

    public List<PedidoView> listaProduc;

    public Pedido_adapter(List<PedidoView>listaPedido){
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
        holder.setOnClickListeners();
    }
    @Override
    public int getItemCount() {
        return listaProduc.size();
    }
}
