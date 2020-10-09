package com.example.comeya;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.comeya.utils.MenuData;

import java.util.ArrayList;

public class Pedido_adapter extends RecyclerView.Adapter<Pedido_adapter.Holdermenu>{
    Context context;
    ArrayList<PedidoView> lista_menu;
    Pedido_adapter(Context context){
        this.context=context;
        lista_menu=new ArrayList<>();
    }
    void add(PedidoView menu){
        lista_menu.add(menu);
        notifyItemInserted(lista_menu.indexOf(menu));
    }
    @NonNull
    @Override
    public Holdermenu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view   =LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_pedido,parent,false);
        Holdermenu holdermenu=new Holdermenu(view);
        return holdermenu;
    }

    @Override
    public void onBindViewHolder(@NonNull Holdermenu holder, int position) {
        holder.titleprduc.setText(lista_menu.get(position).getTitleproducto());
        holder.precio.setText(lista_menu.get(position).getPrecio());
        holder.descripcion.setText(lista_menu.get(position).getDescripcion());
        PedidoView it=lista_menu.get(position);
        holder.id_menu=it.getId_menu();
        Glide.with(context).load(it.getFotoproducto()).centerCrop().into(holder.fotopruct);
        holder.setOnclickCardview();

    }

    @Override
    public int getItemCount() {
        return lista_menu.size();
    }

    class Holdermenu extends RecyclerView.ViewHolder implements View.OnClickListener, LifecycleOwner {
        Context context;
        Context context_om;
        private TextView titleprduc, precio, descripcion;
        private ImageView fotopruct;
        private CardView pedido;
        String id_menu;
        public Holdermenu(@NonNull View itemView) {
            super(itemView);
            context_om=itemView.getContext();
            titleprduc =(TextView)itemView.findViewById(R.id.Produc_titulo);
            precio =(TextView)itemView.findViewById(R.id.produc_precio);
            descripcion =(TextView)itemView.findViewById(R.id.produc_numimg);
            fotopruct =(ImageView) itemView.findViewById(R.id.produc_img);
            pedido =(CardView) itemView.findViewById(R.id.groupviewpedidoproducto);

        }
        void setOnclickCardview(){
            pedido.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.groupviewpedidoproducto:
                    MenuData.ID_AUX_IDMENU=id_menu;
                    openVentana();
                    break;
            }
        }

        private void openVentana() {
            Ventanarealizarpedido ventanarealizarpedido=new Ventanarealizarpedido();
            ventanarealizarpedido.show(((AppCompatActivity)context_om).getSupportFragmentManager(),"example dialogo");
        }

        @NonNull
        @Override
        public Lifecycle getLifecycle() {
            return null;
        }
    }

}
