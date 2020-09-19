package com.example.comeya;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.comeya.utils.MenuData;

import java.util.ArrayList;

public class confirmarpedido_adapter extends RecyclerView.Adapter<confirmarpedido_adapter.Holderlistafinal>{
    Context context;
    ArrayList<confirmarpedidoView> lista_final;
    confirmarpedido_adapter(Context context){
        this.context=context;
        lista_final =new ArrayList<>();
    }
    void add(confirmarpedidoView listafinal){
        lista_final.add(listafinal);
        notifyItemInserted(lista_final.indexOf(listafinal));
    }
    @NonNull
    @Override
    public Holderlistafinal onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view   =LayoutInflater.from(parent.getContext()).inflate(R.layout.lisview_confirmarpedido,parent,false);
        Holderlistafinal holdermenu=new Holderlistafinal(view);
        return holdermenu;
    }

    @Override
    public void onBindViewHolder(@NonNull Holderlistafinal holder, int position) {
        holder.titulo.setText(lista_final.get(position).getTitulo());
        holder.cantidad.setText(lista_final.get(position).getCantidad());
        holder.total.setText(lista_final.get(position).getTotal());
        holder.setOnclickCardview();

    }

    @Override
    public int getItemCount() {
        return lista_final.size();
    }

    class Holderlistafinal extends RecyclerView.ViewHolder implements View.OnClickListener,PopupMenu.OnMenuItemClickListener {
        Context context;
        private TextView titulo, cantidad, total;
        private CardView confirmar_pedido;
        String id_menu;
        public Holderlistafinal(@NonNull View itemView) {
            super(itemView);
            titulo =(TextView)itemView.findViewById(R.id.confirmar_titulo);
            cantidad =(TextView)itemView.findViewById(R.id.confirmar_cantidad);
            total =(TextView)itemView.findViewById(R.id.confirmar_total);
            confirmar_pedido =(CardView) itemView.findViewById(R.id.listview_confirmarPed);

        }
        public void setOnclickCardview() {
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

}
